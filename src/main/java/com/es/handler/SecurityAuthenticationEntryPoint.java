package com.es.handler;

import com.es.enums.ResponseCode;
import com.es.util.JsonUtils;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created on 2021/12/16 19:27
 *
 * @author Marfack
 */
@Component
public final class SecurityAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        JsonUtils
                .createJson()
                .put("code", ResponseCode.UNAUTHORIZED_ERROR.getCode())
                .put("msg", ResponseCode.UNAUTHORIZED_ERROR.getMsg())
                .put("exception", authException.getMessage())
                .writeJsonTo(response);
    }
}
