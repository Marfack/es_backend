package com.es.handler;

import com.es.dao.UserInfoDao;
import com.es.enums.ResponseCode;
import com.es.po.UserInfo;
import com.es.util.JsonUtils;
import com.es.util.JwtUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.DateFormat;

/**
 * Created on 2021/12/15 16:53
 * 用于处理登陆成功之后的操作，覆盖默认的处理器
 * 将重定向改为向Response中添加返回Json值
 * 向响应头中添加token
 *
 * @author Marfack
 */
@Component
public final class SecurityLoginSuccessHandler implements AuthenticationSuccessHandler {

    @Resource
    UserInfoDao userInfoDao;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        Object id = userInfoDao.getUserIdAndPasswordByNo(request.getParameter("username")).get("user_id");
        long id1 = id instanceof Long ? (long) id : (int) id;
        UserInfo userInfo = userInfoDao.fetchUserInfoById(id1);
        JsonUtils
                .createJson()
                .put("code", ResponseCode.SUCCESS.getCode())
                .put("msg", ResponseCode.SUCCESS.getMsg())
                .put("user_id", id)
                .put("token", JwtUtils.createToken(userInfo))
                .put("last_login_time", DateFormat.getDateTimeInstance().format(userInfo.getLastLoginTime()))
                .writeJsonTo(response);
        userInfoDao.updateLoginTime(id1, new Timestamp(System.currentTimeMillis()));
    }
}
