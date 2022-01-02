package com.es.service.impl;

import com.es.dao.UserAccountDao;
import com.es.dao.UserDetailsDao;
import com.es.dao.UserInfoDao;
import com.es.enums.ResponseCode;
import com.es.enums.UserClass;
import com.es.po.UserInfo;
import com.es.service.UserInfoService;
import com.es.util.JsonUtils;
import com.es.util.JwtUtils;
import com.es.util.LocalMailSender;
import com.es.util.StringUtils;
import com.es.vo.RegisterVo;
import com.es.vo.ResetPasswordVo;
import com.es.vo.RetrievePasswordVo;
import com.es.vo.UserDetailsVo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.Future;

/**
 * Created on 2021/12/14 20:00
 * 登录验证服务
 *
 * @author Marfack
 */
@Service
public class UserInfoServiceImpl implements UserDetailsService, UserInfoService {

    @Resource
    private UserInfoDao userInfoDao;

    @Resource
    private UserDetailsDao userDetailsDao;

    @Resource
    private UserAccountDao userAccountDao;

    /**
     * 密码加密Bean
     */
    @Resource
    private PasswordEncoder passwordEncoder;

    /**
     * 邮件发送封装类
     */
    @Resource
    private LocalMailSender localMailSender;

    /**
     * 线程池注入
     */
    @Resource
    private Executor executor;

    /**
     * 实现UserDetailsService的loadUserByUsername接口，供Spring Security内部调用检验登录正确性
     *
     * @param username 用户账号
     * @return 返回SpringSecurity验证体系中的UserDetails对象，用来给proxy filters提供验证方法
     * @throws UsernameNotFoundException 账号不存在
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Map<String, Object> entry = userInfoDao.getUserIdAndPasswordByNo(username);
        if (entry == null) {
            throw new UsernameNotFoundException("用户名不存在");
        }
        Object id = entry.get("user_id");
        long id1 = id instanceof Long ? (long) id : (int) id;
        UserInfo userInfo = userInfoDao.fetchUserInfoById(id1);
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        UserClass.getAuthorities(authorities, userInfo.getUserClass());
        return new User(userInfo.getUserNo(), passwordEncoder.encode(userInfo.getUserPassword()), authorities);
    }

    @Async
    @Transactional(rollbackFor = Exception.class)
    @Override
    public Future<String> register(RegisterVo registerVo) throws JsonProcessingException {
        String res;
        ObjectMapper out = new ObjectMapper();
        long timeLimit = 5 * 60 * 1000;
        if (registerVo.getTime() > System.currentTimeMillis()) {
            res = out.writeValueAsString(ResponseCode.REQUEST_PARAM_ERROR);
        } else if (System.currentTimeMillis() - registerVo.getTime() > timeLimit) {
            res = out.writeValueAsString(ResponseCode.VERIFICATION_CODE_TLE);
        } else if (userInfoDao.getUserIdAndPasswordByNo(registerVo.getUsername()) != null) {
            res = out.writeValueAsString(ResponseCode.USER_NAME_EXIST);
        } else {
            try {
                userInfoDao.insertUserInfo(registerVo.getUsername(), registerVo.getPassword());
                res = out.writeValueAsString(ResponseCode.SUCCESS);
            } catch (Exception e) {
                e.printStackTrace();
                res = out.writeValueAsString(ResponseCode.REGISTER_ERROR);
            }
        }
        return new AsyncResult<>(res);
    }

    @Async
    @Override
    public Future<String> mailVerify(String email) {
        String code = StringUtils.randomVerificationCode();
        String title = "注册业务安全验证码";
        String content = String.format("您好！\n\n欢迎注册使用企享，您的验证码为%s，祝您使用愉快！\n\n此致\n企享", code);
        executor.execute(() -> localMailSender.send(email, title, content));
        String res = null;
        try {
            res = JsonUtils
                    .createJson()
                    .put("code", ResponseCode.SUCCESS.getCode())
                    .put("msg", ResponseCode.SUCCESS.getMsg())
                    .put("verificationCode", code)
                    .returnJson();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return new AsyncResult<>(res);
    }

    @Async
    @Override
    public Future<String> retrievePasswordMailVerify(String email) throws JsonProcessingException {
        Long id = userDetailsDao.fetchUserIdByEmail(email);
        if (id == null) {
            return new AsyncResult<>(new ObjectMapper().writeValueAsString(ResponseCode.USER_NOT_FOUND_ERROR));
        }
        String code = StringUtils.randomVerificationCode();
        String title = "找回密码";
        String content = String.format("您好！\n\n您的验证码为%s，验证码有效期为5分钟，请及时提交。如果并非您本人操作，请迅速联系后台管理人员。\n\n此致\n企享", code);
        executor.execute(() -> localMailSender.send(email, title, content));
        String res = null;
        try {
            res = JsonUtils
                    .createJson()
                    .put("code", ResponseCode.SUCCESS.getCode())
                    .put("msg", ResponseCode.SUCCESS.getMsg())
                    .put("verificationCode", code)
                    .put("userId", id)
                    .returnJson();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return new AsyncResult<>(res);
    }

    @Async
    @Transactional(rollbackFor = Exception.class)
    @Override
    public Future<String> retrievePassword(RetrievePasswordVo vo) throws JsonProcessingException {
        ObjectMapper out = new ObjectMapper();
        long timeLimit = 5 * 60 * 1000;
        if (vo.getTime() > System.currentTimeMillis()) {
            return new AsyncResult<>(out.writeValueAsString(ResponseCode.REQUEST_PARAM_ERROR));
        } else if (System.currentTimeMillis() - vo.getTime() > timeLimit) {
            return new AsyncResult<>(out.writeValueAsString(ResponseCode.VERIFICATION_CODE_TLE));
        }
        UserInfo userInfo = userInfoDao.fetchUserByNo(vo.getUserId());
        System.out.println(vo);
        if (userInfo == null) {
            return new AsyncResult<>(out.writeValueAsString(ResponseCode.REQUEST_PARAM_ERROR));
        }
        long id = userInfo.getUserId();
        com.es.po.UserDetails userDetails = userDetailsDao.fetchByUserId(id);
        if (!userDetails.getUserEmail().equals(vo.getEmail())) {
            return new AsyncResult<>(out.writeValueAsString(ResponseCode.REQUEST_PARAM_ERROR));
        }
        try {
            userInfoDao.updatePassword(id, vo.getPassword());
            return new AsyncResult<>(out.writeValueAsString(ResponseCode.SUCCESS));
        } catch (Exception e) {
            e.printStackTrace();
            return new AsyncResult<>(out.writeValueAsString(ResponseCode.REQUEST_FAIL));
        }
    }

    @Async
    @Transactional(rollbackFor = Exception.class)
    @Override
    public Future<String> resetPassword(ResetPasswordVo vo, long id, String originPsw) throws JsonProcessingException {
        String res;
        ObjectMapper out = new ObjectMapper();
        UserInfo user = userInfoDao.fetchUserInfoById(id);
        if (user == null) {
            res = out.writeValueAsString(ResponseCode.REQUEST_FAIL);
        } else if (originPsw.equals(vo.getOriginPsw()) && user.getUserPassword().equals(vo.getOriginPsw())) {
            user.setUserPassword(vo.getNewPsw());
            userInfoDao.updatePassword(user.getUserId(), vo.getNewPsw());
            String token = JwtUtils.createToken(user);
            res = JsonUtils
                    .createJson()
                    .put("code", ResponseCode.SUCCESS.getCode())
                    .put("msg", ResponseCode.SUCCESS.getMsg())
                    .put("token", token)
                    .returnJson();
        } else {
            res = out.writeValueAsString(ResponseCode.REQUEST_FAIL);
        }
        return new AsyncResult<>(res);
    }

    @Async
    @Transactional(rollbackFor = Exception.class)
    @Override
    public Future<String> completeInfo(com.es.po.UserDetails userDetails) throws JsonProcessingException {
        long id = userDetails.getUserId();
        UserInfo userInfo = userInfoDao.fetchUserInfoById(id);
        if (userInfo == null) {
            return new AsyncResult<>(new ObjectMapper().writeValueAsString(ResponseCode.USER_NOT_FOUND_ERROR));
        }
        userDetailsDao.insertUserDetailsObject(userDetails);
        userAccountDao.insertUserAccount(id);
        userInfo.setUserClass(UserClass.DEMANDER.CLS);
        userInfoDao.updateUserStatus(id, UserClass.DEMANDER.CLS);
        String token = JwtUtils.createToken(userInfo);
        String res = JsonUtils
                .createJson()
                .put("code", ResponseCode.SUCCESS.getCode())
                .put("msg", ResponseCode.SUCCESS.getMsg())
                .put("token", token)
                .returnJson();
        return new AsyncResult<>(res);
    }

    @Async
    @Transactional(rollbackFor = Exception.class)
    @Override
    public Future<String> alterInfo(com.es.po.UserDetails userDetails) throws JsonProcessingException {
        System.out.println(userDetails);
        userDetailsDao.updateUserDetails(userDetails);
        UserInfo userInfo = userInfoDao.fetchUserInfoById(userDetails.getUserId());
        String token = JwtUtils.createToken(userInfo);
        String res = JsonUtils
                .createJson()
                .put("code", ResponseCode.SUCCESS.getCode())
                .put("msg", ResponseCode.SUCCESS.getMsg())
                .put("token", token)
                .returnJson();

        return new AsyncResult<>(res);
    }

    @Async
    @Override
    public Future<String> becomeProvider(long id) throws JsonProcessingException {
        UserInfo userInfo = userInfoDao.fetchUserInfoById(id);
        userInfo.setUserClass(UserClass.PROVIDER.CLS);
        userInfoDao.updateUserStatus(id, UserClass.PROVIDER.CLS);
        String token = JwtUtils.createToken(userInfo);
        String res = JsonUtils
                .createJson()
                .put("code", ResponseCode.SUCCESS.getCode())
                .put("msg", ResponseCode.SUCCESS.getMsg())
                .put("token", token)
                .returnJson();
        return new AsyncResult<>(res);
    }

    @Async
    @Override
    public Future<String> getUserName(long id) throws JsonProcessingException {
        UserInfo userInfo = userInfoDao.fetchUserInfoById(id);
        if (userInfo == null) {
            return new AsyncResult<>(new ObjectMapper().writeValueAsString(ResponseCode.USER_NOT_FOUND_ERROR));
        }
        String res = JsonUtils
                .createJson()
                .put("code", ResponseCode.SUCCESS.getCode())
                .put("msg", ResponseCode.SUCCESS.getMsg())
                .put("userName", userDetailsDao.getUsername(id))
                .returnJson();
        return new AsyncResult<>(res);
    }

    @Async
    @Override
    public Future<String> verify(long id, String psw) throws JsonProcessingException {
        UserInfo userInfo = userInfoDao.fetchUserInfoById(id);
        if (!userInfo.getUserPassword().equals(psw)) {
            return new AsyncResult<>(new ObjectMapper().writeValueAsString(ResponseCode.WRONG_PASSWORD));
        }
        return new AsyncResult<>(new ObjectMapper().writeValueAsString(ResponseCode.SUCCESS));
    }

    @Override
    public Future<String> getUserInfo(long id) throws JsonProcessingException {
        UserDetailsVo vo = userDetailsDao.fetchUserDetails(id);
        String res = JsonUtils
                .createJson()
                .put("code", ResponseCode.SUCCESS.getCode())
                .put("msg", ResponseCode.SUCCESS.getMsg())
                .put("data", vo)
                .returnJson();
        return new AsyncResult<>(res);
    }
}
