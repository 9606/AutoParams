package com.neu.autoparams.mvc.handler;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.neu.autoparams.mvc.entity.Message;
import com.neu.autoparams.mvc.entity.MessageType;
import com.neu.autoparams.mvc.controller.SubmitTaskHandShakeInterceptor;
import com.neu.autoparams.mvc.service.TaskService;
import org.apache.commons.collections.keyvalue.DefaultMapEntry;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.*;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;


@Component
public class TaskWebSocketHandler implements WebSocketHandler {

    @Resource
    TaskService taskService;

    private static Logger logger = Logger.getLogger(SubmitTaskHandShakeInterceptor.class);
    private static final Map<Integer, WebSocketSession> userSocketSessionMap;
    private static final Map<Integer, WebSocketSession> taskSocketSessionMap;
    private static final ConcurrentLinkedQueue<Map.Entry<WebSocketSession, String>> taskProcessQueue;
    private static final ConcurrentHashMap<Integer, Queue<String>> taskMessageMap;
    private static final ObjectMapper mapper;


    static {
        userSocketSessionMap = new ConcurrentHashMap<>();
        taskSocketSessionMap = new ConcurrentHashMap<>();
        taskProcessQueue = new ConcurrentLinkedQueue<>();
        taskMessageMap = new ConcurrentHashMap<>();
        mapper = new ObjectMapper();
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession webSocketSession) throws Exception {
        Integer userId = (Integer) webSocketSession.getAttributes().get("uid");
        userSocketSessionMap.put(userId, webSocketSession);
    }

    @Override
    public void handleMessage(WebSocketSession webSocketSession, WebSocketMessage<?> webSocketMessage) throws Exception {
        JsonNode root = mapper.readTree(((TextMessage) webSocketMessage).getPayload());
        JsonNode type = root.path("type");
        JsonNode taskId = root.path("content");
        Integer userId = (Integer) webSocketSession.getAttributes().get("uid");
        if (type.asInt() == MessageType.USER_STOP.ordinal()){
            webSocketSession.getAttributes().put("userStop", true);
            webSocketSession.sendMessage(new TextMessage(taskService.stopTask(taskId.asInt())));
            webSocketSession.sendMessage(new TextMessage(new Message(MessageType.TASK_FINISH, MessageType.TASK_FINISH.getLabel()).JsonString()));
            // webSocketSession.close();
        }
        logger.info("Receive message, session id:" + webSocketSession.getId());
    }

    @Override
    public void handleTransportError(WebSocketSession webSocketSession, Throwable throwable) throws Exception {
        Integer userId = (Integer) webSocketSession.getAttributes().get("uid");
        userSocketSessionMap.remove(userId);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession webSocketSession, CloseStatus closeStatus) throws Exception {
        logger.info("Connection close, session id:" + webSocketSession.getId());
        // Integer userId = (Integer) webSocketSession.getAttributes().get("uid");
        // userSocketSessionMap.remove(userId);
    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }

    public static void closeUserWebSocketSession(int userId){
        WebSocketSession webSocketSession = userSocketSessionMap.get(userId);
        if (webSocketSession != null && webSocketSession.isOpen()){
            try {
                webSocketSession.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void addTaskProcessMessage(Integer taskId, String message) {
        WebSocketSession webSocketSession = taskSocketSessionMap.get(taskId);
        if (webSocketSession != null){
            Map.Entry<WebSocketSession, String> entry = new DefaultMapEntry(webSocketSession, message);
            taskProcessQueue.add(entry);
        }

    }

    public static void setTaskRunning(Integer userId, Integer taskId) {
        WebSocketSession webSocketSession = userSocketSessionMap.get(userId);
        webSocketSession.getAttributes().put("stop", false);
        webSocketSession.getAttributes().put("taskId", taskId);
        taskSocketSessionMap.put(taskId, webSocketSession);
        if (!taskMessageMap.containsKey(taskId)){
            Queue<String> messages = new ConcurrentLinkedQueue<>();
            taskMessageMap.put(taskId, messages);
        }
    }

    public static void setTaskFinish(Integer userId, Integer taskId) {
        taskSocketSessionMap.get(taskId).getAttributes().put("stop", true);
        taskMessageMap.remove(taskId);
    }

    public static void sendTaskProcessMessage(int sendNums) {
        for (int i = 0; i < sendNums; i++) {
            if (taskProcessQueue.isEmpty()){
                break;
            }
            Map.Entry<WebSocketSession, String> entry = taskProcessQueue.poll();
            WebSocketSession webSocketSession = entry.getKey();
            if (webSocketSession != null){
                if (webSocketSession.isOpen() && !(Boolean) webSocketSession.getAttributes().get("userStop")){
                    try {
                        webSocketSession.sendMessage(new TextMessage(entry.getValue()));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                // 用户没有主动停止任务或任务没有停止之前之前，为用户暂存消息
                if (!(Boolean) webSocketSession.getAttributes().get("stop") &&
                        !(Boolean) webSocketSession.getAttributes().get("userStop")){
                    taskMessageMap.get(webSocketSession.getAttributes().get("taskId")).offer(entry.getValue());
                }else {
                    taskMessageMap.remove(webSocketSession.getAttributes().get("taskId"));
                }
            }
        }
    }

    /**
     * 将用户离线期间消息加入消息队列
     *
     * @param userId
     */
    public static void sendOfflineTaskProcessMessage(Integer userId, Integer taskId) {
        WebSocketSession webSocketSession = userSocketSessionMap.get(userId);
        webSocketSession.getAttributes().put("stop", false);
        webSocketSession.getAttributes().put("taskId", taskId);
        taskSocketSessionMap.put(taskId, webSocketSession);
        synchronized (taskMessageMap){
            Iterator<String> iterator = taskMessageMap.get(taskId).iterator();
            while (iterator.hasNext()){
                addTaskProcessMessage(taskId, iterator.next());
            }
        }
    }
}
