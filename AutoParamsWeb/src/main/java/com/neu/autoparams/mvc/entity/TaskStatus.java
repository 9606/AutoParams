package com.neu.autoparams.mvc.entity;

public enum TaskStatus {

    RUNNING("任务正在进行"),
    FINISH("任务完成无错误"),
    FINISH_ERR("任务完成但有错误"),
    USER_STOP("任务中途被用户中断"),
    UNKNOWN_STATUS("未知状态");

    private String label;

    TaskStatus(String label){
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
