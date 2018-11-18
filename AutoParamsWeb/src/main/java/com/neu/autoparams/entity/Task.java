package com.neu.autoparams.entity;

import org.springframework.jdbc.core.RowMapper;

public class Task {

    public static final String TASK_ID = "t_id";
    public static final String USER_ID = "u_id";
    public static final String FILE_ID = "f_id";
    public static final String FILE_NAME = "origin_name";
    public static final String TASK_STATUS = "t_status";
    public static final String ALGO_ID = "algo_id";
    public static final String ALGO_LIB = "algo_lib";
    public static final String ALGO_NAME = "algo_name";
    public static final String ALGO_PARAMS = "algo_params";
    public static final String OPT_ALGO_ID = "opt_algo_id";
    public static final String OPT_ALGO_NAME = "opt_algo_name";
    public static final String OPT_ALGO_PARAMS = "opt_algo_params";
    public static final String TASK_RESULT = "task_result";
    public static final String SUBMIT_TIME = "submit_time";
    public static final String DONE_TIME = "done_time";

    private int taskId;
    private int userId;
    private int fileId;
    private String fileName;
    private TaskStatus taskStatus;
    private String taskStatusDesc;
    private int algoId;
    private String algoLib;
    private String algoName;
    private String algoParams;
    private int optAlgoId;
    private String optAlgoName;
    private String optAlgoParams;
    private long submitTime;
    private long doneTime;
    private String taskResult;



    private static final RowMapper<Task> rowMapper = (resultSet, i) -> {
        Task task = new Task();
        task.setTaskId(resultSet.getInt(TASK_ID));
        task.setUserId(resultSet.getInt(USER_ID));
        task.setFileId(resultSet.getInt(FILE_ID));
        task.setFileName(resultSet.getString(FILE_NAME));
        task.setTaskStatus(TaskStatus.values()[resultSet.getInt(TASK_STATUS)]);
        task.setTaskStatusDesc(task.getTaskStatus().getLabel());
        task.setAlgoId(resultSet.getInt(ALGO_ID));
        task.setAlgoLib(resultSet.getString(ALGO_LIB));
        task.setAlgoName(resultSet.getString(ALGO_NAME));
        task.setAlgoParams(resultSet.getString(ALGO_PARAMS));
        task.setOptAlgoId(resultSet.getInt(OPT_ALGO_ID));
        task.setOptAlgoName(resultSet.getString(OPT_ALGO_NAME));
        task.setOptAlgoParams(resultSet.getString(OPT_ALGO_PARAMS));
        task.setTaskResult(resultSet.getString(TASK_RESULT));
        task.setSubmitTime(resultSet.getTimestamp(SUBMIT_TIME).getTime());
        task.setDoneTime(resultSet.getTimestamp(DONE_TIME).getTime());
        return task;
    };

    public static RowMapper<Task> getRowMapper() {
        return rowMapper;
    }

    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public TaskStatus getTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(TaskStatus taskStatus) {
        this.taskStatus = taskStatus;
    }

    public String getTaskResult() {
        return taskResult;
    }

    public void setTaskResult(String taskResult) {
        this.taskResult = taskResult;
    }

    public long getSubmitTime() {
        return submitTime;
    }

    public void setSubmitTime(long submitTime) {
        this.submitTime = submitTime;
    }

    public long getDoneTime() {
        return doneTime;
    }

    public void setDoneTime(long doneTime) {
        this.doneTime = doneTime;
    }

    public int getFileId() {
        return fileId;
    }

    public void setFileId(int fileId) {
        this.fileId = fileId;
    }

    public String getTaskStatusDesc() {
        return taskStatusDesc;
    }

    public void setTaskStatusDesc(String taskStatusDesc) {
        this.taskStatusDesc = taskStatusDesc;
    }

    public int getAlgoId() {
        return algoId;
    }

    public void setAlgoId(int algoId) {
        this.algoId = algoId;
    }

    public String getAlgoParams() {
        return algoParams;
    }

    public void setAlgoParams(String algoParams) {
        this.algoParams = algoParams;
    }

    public int getOptAlgoId() {
        return optAlgoId;
    }

    public void setOptAlgoId(int optAlgoId) {
        this.optAlgoId = optAlgoId;
    }

    public String getOptAlgoParams() {
        return optAlgoParams;
    }

    public void setOptAlgoParams(String optAlgoParams) {
        this.optAlgoParams = optAlgoParams;
    }

    public String getAlgoLib() {
        return algoLib;
    }

    public void setAlgoLib(String algoLib) {
        this.algoLib = algoLib;
    }

    public String getAlgoName() {
        return algoName;
    }

    public void setAlgoName(String algoName) {
        this.algoName = algoName;
    }

    public String getOptAlgoName() {
        return optAlgoName;
    }

    public void setOptAlgoName(String optAlgoName) {
        this.optAlgoName = optAlgoName;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
