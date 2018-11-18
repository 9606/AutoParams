package com.neu.autoparams.mvc.handler;

import com.neu.autoparams.mvc.filter.CheckCodeFilter;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LoginFailureHandler implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException {
        Integer failureTime = (Integer) request.getSession().getAttribute("failure-time");
        Boolean needCheck;
        request.getSession().setAttribute("failure-time", ++failureTime);
        request.getSession().setAttribute("need-check", needCheck = CheckCodeFilter.needCheck(failureTime));
        String type = request.getHeader("X-Requested-With") == null ? "" : request.getHeader("X-Requested-With");
        if ("XMLHttpRequest".equals(type)) {
            response.setHeader("REDIRECT", "ERROR");
            response.setHeader("CONTEXT-CONTENT", e.getMessage());
            response.setHeader("NEED-CHECK", String.valueOf(needCheck));
        }else{
            response.sendRedirect("/sign_in");
        }
    }
}
