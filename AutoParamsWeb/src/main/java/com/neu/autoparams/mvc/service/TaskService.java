package com.neu.autoparams.mvc.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.neu.autoparams.entity.*;
import com.neu.autoparams.mvc.dao.TaskDao;
import com.neu.autoparams.mvc.handler.TaskWebSocketHandler;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.concurrent.*;


@Service
public class TaskService {


    @Resource
    TaskDao taskDao;

    private static Logger logger = LoggerFactory.getLogger(TaskService.class);
    private static ExecutorService executorService = Executors.newCachedThreadPool();
    private static Map<Integer, TaskStatus> taskStatusMap = new ConcurrentHashMap<>();
    private static Map<Integer, TaskRunnable> taskRunnableMap = new ConcurrentHashMap<>();
    private static Map<Integer, Integer> userTaskMap = new ConcurrentHashMap<>();  // 主要为了快速查看用户目前是否存在任务以及任务的id

    class TaskRunnable implements Runnable {
        private Integer userId;
        private Integer taskId;
        private String algoLib;
        private String algoName;
        private String algoParams;
        private String otherParams;
        private String optAlgoName;
        private String optAlgoParams;

        private Deque<TaskDetail> allTaskDetail;
        private StreamCallable infoRunnable;
        private StreamCallable errRunnable;

        public TaskRunnable(Integer userId, Integer taskId, String algoLib, String algoName, String algoParams,
                            String otherParams, String optAlgoName, String optAlgoParams) {
            this.userId = userId;
            this.taskId = taskId;
            this.algoLib = algoLib;
            this.algoName = algoName;
            this.algoParams = algoParams;
            this.otherParams = otherParams;
            this.optAlgoName = optAlgoName;
            this.optAlgoParams = optAlgoParams;
        }

        public void run() {
            try {
                if (algoLib.equals("sklearn") || algoLib.equals("xgboost")) {
                    String pythonScriptPath = "/Users/enbo/IdeaProjects/AutoParams/AutoSklearn/src/main.py";
                    String pythonParams = algoName + " " + algoParams + " " + otherParams + " " + optAlgoName + " " + optAlgoParams;

                    Process pr = Runtime.getRuntime().exec("python2 " + pythonScriptPath + " " + pythonParams);
                    TaskWebSocketHandler.setTaskRunning(userId, taskId);

                    allTaskDetail = new ConcurrentLinkedDeque<>();
                    infoRunnable = new StreamCallable(new BufferedReader(new InputStreamReader(pr.getInputStream())), TaskDetailType.INFO_LOG, this);
                    errRunnable = new StreamCallable(new BufferedReader(new InputStreamReader(pr.getErrorStream())), TaskDetailType.ERR_LOG, this);

                    Future<String> latestInfo = executorService.submit(infoRunnable);
                    Future<String> latestErr = executorService.submit(errRunnable);

                    // 由于 get() 方法也是阻塞的，所以不加这句话也行
                    // 上面说法有误。。。为了两个子线程能及时返回，这句话不能加，加了阻塞。。。
                    // pr.waitFor();

                    String latestInfoString = latestInfo.get();
                    String latestErrString = latestErr.get();
                    if (pr.isAlive()){
                        pr.destroy();
                    }

                    TaskWebSocketHandler.setTaskFinish(userId, taskId);
                    TaskWebSocketHandler.addTaskProcessMessage(taskId, new Message(MessageType.TASK_FINISH, MessageType.TASK_FINISH.getLabel()).JsonString());

                    if (taskStatusMap.get(taskId) != TaskStatus.USER_STOP) {
                        TaskDetail finalResult = allTaskDetail.peekLast();
                        if (latestErrString != null) {
                            taskStatusMap.put(taskId, TaskStatus.FINISH_ERR);
                            taskDao.updateFullTask(TaskStatus.FINISH_ERR, finalResult.getDetailText(), new Date().getTime(), taskId);
                        } else if (latestInfoString != null) {
                            taskStatusMap.put(taskId, TaskStatus.FINISH);
                            taskDao.updateFullTask(TaskStatus.FINISH, finalResult.getDetailText(), new Date().getTime(), taskId);
                        } else {
                            taskStatusMap.put(taskId, TaskStatus.UNKNOWN_STATUS);
                            taskDao.updateFullTask(TaskStatus.UNKNOWN_STATUS, finalResult.getDetailText(), new Date().getTime(), taskId);
                        }
                    }
                    taskDao.batchInsertTaskDetail(new ArrayList<>(allTaskDetail));
                    // pr.destroy();
                }
            } catch (IOException | InterruptedException | ExecutionException e) {
                e.printStackTrace();
                taskStatusMap.put(taskId, TaskStatus.UNKNOWN_STATUS);
                taskDao.updateFullTask(TaskStatus.UNKNOWN_STATUS, "运行任务期间发生异常", new Date().getTime(), taskId);
            } finally {
                taskStatusMap.remove(taskId);
                taskRunnableMap.remove(taskId);
                // 这块有的时候更新不及时，或许用户已经开始运行下一任务了，因此移除userTaskMap的时候需要多加一些条件
                // 防止将用户其他的正在运行的任务的状态改了
                synchronized (userTaskMap){
                    if (userTaskMap.get(userId).intValue() == this.taskId.intValue()){
                        userTaskMap.remove(userId);
                    }
                }
            }
        }

        public void userStopTask() {
            if (infoRunnable != null) {
                infoRunnable.setIsUserStop(true);
            }
            if (errRunnable != null) {
                errRunnable.setIsUserStop(true);
            }
        }

        public void processTaskOutput(String content, TaskDetailType taskDetailType, long detailTime, int msgOrder){
            if (taskDetailType.equals(TaskDetailType.INFO_LOG)){
                TaskWebSocketHandler.addTaskProcessMessage(taskId, new Message(MessageType.INFO_LOG, content, detailTime, msgOrder).JsonString());
            }else {
                TaskWebSocketHandler.addTaskProcessMessage(taskId, new Message(MessageType.ERR_LOG, content, detailTime, msgOrder).JsonString());
            }
            allTaskDetail.add(new TaskDetail(taskId, content, taskDetailType, detailTime));
        }
    }

    class StreamCallable implements Callable<String> {
        private BufferedReader bufferedReader;
        private TaskDetailType taskDetailType;
        private boolean isUserStop;
        private TaskRunnable taskRunnable;
        private int order;

        public StreamCallable(BufferedReader bufferedReader, TaskDetailType taskDetailType, TaskRunnable taskRunnable) {
            this.bufferedReader = bufferedReader;
            this.taskDetailType = taskDetailType;
            this.isUserStop = false;
            this.taskRunnable = taskRunnable;
            order = 0;
        }
        @Override
        public String call() {
            String line;
            String latestLine = null;
            try {
                while ((line = bufferedReader.readLine()) != null && !isUserStop) {
                    taskRunnable.processTaskOutput(latestLine = line, taskDetailType, new Date().getTime(), order++);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return latestLine;
            }
        }

        public void setIsUserStop(boolean userStop) {
            isUserStop = userStop;
        }

    }

    public String stopTask(Integer taskId) {
        synchronized (taskStatusMap) {
            String replyString = null;
            try {
                TaskStatus taskStatus = taskStatusMap.get(taskId);
                if (taskStatus == TaskStatus.RUNNING) {
                    // 先立马更新一下任务状态
                    taskStatusMap.put(taskId, TaskStatus.USER_STOP);
                    taskRunnableMap.get(taskId).userStopTask();
                    taskDao.updateFullTask(TaskStatus.USER_STOP, TaskStatus.USER_STOP.getLabel(), new Date().getTime(), taskId);
                    replyString = new Message(MessageType.SERVER_STOP, MessageType.SERVER_STOP.getLabel()).JsonString();
                } else {
                    replyString = new Message(MessageType.SERVER_DONE, MessageType.SERVER_DONE.getLabel()).JsonString();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return replyString;
        }
    }

    public int getRunningTaskId(Integer userID){
        // 数据库慢
        // return taskDao.getRunningTaskId(userId);
        Integer runningTaskId = userTaskMap.get(userID);
        if (runningTaskId == null  || taskStatusMap.get(runningTaskId) != TaskStatus.RUNNING){
            runningTaskId  = -1;
        }
        return runningTaskId;
    }

    public void getOfflineTaskDetail(Integer userId, Integer taskId){
        TaskWebSocketHandler.sendOfflineTaskProcessMessage(userId, taskId);
    }

    public int startTask(JSONObject submitTaskParam, Integer userId) throws DataAccessException, IOException {
        // 如果有正在运行的任务，停止原任务
        Integer runningTaskId = userTaskMap.get(userId);
        if (runningTaskId != null){
            stopTask(runningTaskId);
        }

        JsonNode root = new ObjectMapper().readTree(submitTaskParam.toString());
        int algoId = root.path("algoId").asInt();
        String algoLib = root.path("algoLib").asText();
        String algoName = root.path("algoName").asText();
        String algoParams = root.path("algoParams").toString();

        String evaType = root.path("evaType").asText();

        int optAlgoId = root.path("optAlgoId").asInt();
        String optAlgoName = root.path("optAlgoName").asText();
        String optAlgoParams = root.path("optAlgoParams").toString();

        JsonNode fileInfo = root.path("fileInfo");
        Integer fileId = fileInfo.path("fileId").asInt();

        OtherParams otherParams = new OtherParams();
        otherParams.setFileType(fileInfo.path("fileType").asText());
        otherParams.setCluLabel(fileInfo.path("cluLabel").asBoolean());
        otherParams.setColName(fileInfo.path("colName").asBoolean());
        otherParams.setFilePath(fileInfo.path("filePath").asText());
        ObjectMapper objectMapper = new ObjectMapper();
        // 先插入数据库取得任务 id
        int taskId = taskDao.insertTask(userId, fileId, TaskStatus.RUNNING, algoId, algoParams, optAlgoId, optAlgoParams, evaType);

        TaskRunnable taskRunnable = new TaskRunnable(userId, taskId, algoLib, algoName, algoParams,
                objectMapper.writeValueAsString(otherParams), optAlgoName, optAlgoParams);
        userTaskMap.put(userId, taskId);
        taskStatusMap.put(taskId, TaskStatus.RUNNING);
        taskRunnableMap.put(taskId, taskRunnable);
        executorService.submit(taskRunnable);
        return taskId;
    }

    public SubmitParams getSubmitParams(int taskId){
        return taskDao.getSubmitParams(taskId);
    }

    public List<Task> getUserHistoryTask(int userId) {
        return taskDao.getUserHistoryTask(userId);
    }

    public List<TaskDetail> getTaskDetail(int taskId) {
        return taskDao.getTaskDetail(taskId);
    }

    public List<Integer> deleteTask(int taskId) {
        return taskDao.deleteTask(taskId);
    }

    public List<Integer> batchDeleteTask(List<Integer> taskIds){
        return taskDao.batchDeleteTask(taskIds);
    }

}
