package com.neu.autoparams.mvc.controller;

import org.apache.log4j.Logger;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import javax.servlet.http.HttpSession;
import java.util.Map;


public class SubmitTaskHandShakeInterceptor implements HandshakeInterceptor {

    private static Logger logger = Logger.getLogger(SubmitTaskHandShakeInterceptor.class);

    @Override
    public boolean beforeHandshake(ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse,
                                   WebSocketHandler webSocketHandler, Map<String, Object> map) throws Exception {

        logger.debug("Before Handshake");
        if (serverHttpRequest instanceof ServletServerHttpRequest) {
            HttpSession session = ((ServletServerHttpRequest) serverHttpRequest).getServletRequest().getSession(false);
            // 标记用户
            logger.debug("Websocket:用户[UserName:" + session.getAttribute("username") + "]已经建立连接");
            Object userId =  session.getAttribute("userId");
            if (userId != null) {
                map.put("uid", userId);
                map.put("userStop", false);
                logger.debug("用户id：" + userId + " 被加入");
            } else {
                logger.debug("Handshake Fail:user is null.");
                return false;
            }
        }
        logger.debug("Handshake Success.");
        return true;
    }
    @Override
    public void afterHandshake(ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse, WebSocketHandler webSocketHandler, Exception e) {

    }
}