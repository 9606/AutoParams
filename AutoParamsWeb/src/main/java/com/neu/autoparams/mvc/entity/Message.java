package com.neu.autoparams.mvc.entity;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Date;

public class Message {

    private MessageType type;
    private String content;
    private long msgTime;
    private int msgOrder;
    private static ObjectMapper objectMapper = new ObjectMapper();

    public Message(MessageType type) {
        this.type = type;
        this.content = "";
        this.msgTime = new Date().getTime();

    }

    public Message(MessageType type, String content) {
        this.type = type;
        this.content = content;
        this.msgTime = new Date().getTime();
    }

    public Message(MessageType type, String content, long msgTime, int msgOrder) {
        this.type = type;
        this.content = content;
        this.msgTime = msgTime;
        this.msgOrder = msgOrder;
    }

    public int getType() {
        return type.ordinal();
    }

    public String getContent() {
        return content;
    }

    public long getMsgTime() {
        return msgTime;
    }

    public void setMsgTime(long msgTime) {
        this.msgTime = msgTime;
    }

    public int getMsgOrder() {
        return msgOrder;
    }

    public void setMsgOrder(int msgOrder) {
        this.msgOrder = msgOrder;
    }

    public String JsonString(){
        String jsonResult = "";
        try{
            jsonResult = objectMapper.writeValueAsString(this);
        }catch (Exception e){
            e.printStackTrace();
        }
        return jsonResult;
    }

}
