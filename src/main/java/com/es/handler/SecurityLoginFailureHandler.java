package com.es.handler;

import com.es.enums.ResponseCode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created on 2021/12/15 17:24
 * 用于处理登陆失败之后的操作，覆盖默认的处理器，将重定向改为向Response中添加返回Json值
 * @author Marfack
 */
@Component
public final class SecurityLoginFailureHandler implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        response.setContentType("application/json;charset=utf-8");
        PrintWriter pw = response.getWriter();
        String res;
        if (exception instanceof LockedException) {
            res = new ObjectMapper().writeValueAsString(ResponseCode.USER_LOCKED_ERROR);
        } else if (exception instanceof BadCredentialsException) {
            exception.printStackTrace();
            res = new ObjectMapper().writeValueAsString(ResponseCode.WRONG_PASSWORD);
        } else {
            res = new ObjectMapper().writeValueAsString(ResponseCode.LOGIN_FAILURE);
        }
        pw.write(res);
        pw.flush();
        pw.close();
    }
}
