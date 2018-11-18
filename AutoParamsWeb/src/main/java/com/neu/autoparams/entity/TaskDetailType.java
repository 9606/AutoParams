package com.neu.autoparams.entity;

public enum TaskDetailType {

    INFO_LOG("正常任务信息"),
    ERR_LOG("异常任务信息");

    private String label;

    TaskDetailType(String label){
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
