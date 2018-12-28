package com.neu.autoparams.mvc.entity;

import org.springframework.jdbc.core.RowMapper;

public class TaskDetail {

    public static final String DETAIL_ID = "detail_id";
    public static final String TASK_ID = "task_id";
    public static final String DETAIL = "detail";
    public static final String DETAIL_TIME = "detail_time";
    public static final String DETAIL_TYPE = "detail_type";

    private int detailId;
    private int taskId;
    private String detailText;
    private long detailTime;
    private TaskDetailType taskDetailType;
    private boolean isInfo;

    private static final RowMapper<TaskDetail> rowMapper = (resultSet, i) -> {
        TaskDetail taskDetail = new TaskDetail();
        taskDetail.setDetailId(resultSet.getInt(DETAIL_ID));
        taskDetail.setDetailText(resultSet.getString(DETAIL));
        taskDetail.setDetailTime(resultSet.getTimestamp(DETAIL_TIME).getTime());
        taskDetail.setIsInfo(TaskDetailType.values()[resultSet.getInt(DETAIL_TYPE)]==TaskDetailType.INFO_LOG?true:false);
        return taskDetail;
    };

    public TaskDetail() {
    }

    public TaskDetail(int taskId, String detailText, TaskDetailType taskDetailType, long detailTime) {
        this.taskId = taskId;
        this.detailText = detailText;
        this.taskDetailType = taskDetailType;
        this.detailTime = detailTime;
    }



    public int getDetailId() {
        return detailId;
    }

    public void setDetailId(int detailId) {
        this.detailId = detailId;
    }

    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    public long getDetailTime() {
        return detailTime;
    }

    public void setDetailTime(long detailTime) {
        this.detailTime = detailTime;
    }

    public String getDetailText() {
        return detailText;
    }

    public void setDetailText(String detailText) {
        this.detailText = detailText;
    }

    public TaskDetailType getTaskDetailType() {
        return taskDetailType;
    }

    public void setTaskDetailType(TaskDetailType taskDetailType) {
        this.taskDetailType = taskDetailType;
    }

    public boolean getIsInfo() {
        return isInfo;
    }

    public void setIsInfo(boolean isInfo) {
        this.isInfo = isInfo;
    }

    public static RowMapper<TaskDetail> getRowMapper() {
        return rowMapper;
    }
}

