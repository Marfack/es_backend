package com.es.handler;

import com.es.enums.ResponseCode;
import com.es.util.JsonUtils;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created on 2021/12/16 19:22
 *
 * @author Marfack
 */
@Component
public final class SecurityAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException {
        JsonUtils
                .createJson()
                .put("code", ResponseCode.UNAUTHORIZED_ERROR.getCode())
                .put("msg", ResponseCode.UNAUTHORIZED_ERROR.getMsg())
                .put("exception", accessDeniedException.getMessage())
                .writeJsonTo(response);
    }
}
