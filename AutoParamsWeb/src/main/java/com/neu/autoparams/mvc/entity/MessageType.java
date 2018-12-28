package com.neu.autoparams.mvc.entity;

public enum MessageType {

    USER_STOP("用户主动停止任务"),
    INFO_LOG("正常任务信息"),
    ERR_LOG("异常任务信息"),
    SERVER_DONE("服务器已完成任务，无需停止"),
    SERVER_STOP("服务器已停止任务"),
    SERVER_ERROR("服务器发生异常"),
    TASK_FINISH("任务已结束");

    private String label;

    MessageType(String label){
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
