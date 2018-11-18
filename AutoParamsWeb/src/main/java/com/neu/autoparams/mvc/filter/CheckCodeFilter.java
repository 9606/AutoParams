package com.neu.autoparams.mvc.filter;

import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class CheckCodeFilter extends UsernamePasswordAuthenticationFilter{
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response) throws AuthenticationException {
        String inputValidateCode = request.getParameter("validateCode");
        String generateCode = (String)request.getSession().getAttribute("validateCode");
        if((Boolean) request.getSession().getAttribute("need-check") && !inputValidateCode.equals(generateCode)){
            throw new AuthenticationServiceException("Error CheckCode.");
        }

        return super.attemptAuthentication(request, response);
    }
    // 暂时的策略是验证次数大于3次则显示验证码
    public static boolean needCheck(Integer failureTime){
        return failureTime > 3 ? true : false;
    }

}
