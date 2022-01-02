package com.es.service;

import com.es.po.UserDetails;
import com.es.vo.RegisterVo;
import com.es.vo.ResetPasswordVo;
import com.es.vo.RetrievePasswordVo;
import com.fasterxml.jackson.core.JsonProcessingException;

import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.Future;

/**
 * Created on 2021/12/17 14:59
 *
 * @author Marfack
 */
public interface UserInfoService {

    /**
     * 用户注册服务
     * @param registerVo 注册表单信息
     * @return Json字符串，通知是否注册成功
     * @throws JsonProcessingException 数据解析转化为Json出错
     */
    Future<String> register(RegisterVo registerVo) throws JsonProcessingException;

    /**
     * 通过邮箱发送验证码来验证
     * @param email 验证邮箱
     * @return 返回包含验证码和时间的Json字符串
     */
    Future<String> mailVerify(String email);

    /**
     * 找回密码的邮箱验证服务，需要通过邮箱检索id
     * @param email 账号绑定邮箱
     * @return 是否成功，若成功返回验证码和id。
     * @throws JsonProcessingException 数据解析转化为Json出错
     */
    Future<String> retrievePasswordMailVerify(String email) throws JsonProcessingException;

    /**
     * 通过前端验证码校验后，身份已经验证，只需处理修改密码的请求即可。
     * @param vo 接收表单数据的实体类
     * @return 是否成功
     * @throws JsonProcessingException 数据解析转化为Json出错
     */
    Future<String> retrievePassword(RetrievePasswordVo vo) throws JsonProcessingException;

    /**
     * 改密码业务
     * @param vo 接收改密码业务提交表单的实体对象
     * @param id 客户端token中携带的id，用于校验。
     * @param originPsw 客户端token中携带的password，用于校验。
     * @return 是否修改成功，若修改成功返回新token。
     * @throws JsonProcessingException 数据解析转化为Json出错
     */
    Future<String> resetPassword(ResetPasswordVo vo, long id, String originPsw) throws JsonProcessingException;

    /**
     * 完善信息业务，同时会创建用户账户，获得Demander权限
     * @param userDetails 用户细节po实体类
     * @return 返回是否修改成功
     * @throws JsonProcessingException 数据解析转化为Json出错
     */
    Future<String> completeInfo(UserDetails userDetails) throws JsonProcessingException;

    /**
     * 修改用户信息
     * @param userDetails 用户信息接收类
     * @return 返回是否成功
     * @throws JsonProcessingException 数据解析转化为Json出错
     */
    Future<String> alterInfo(UserDetails userDetails) throws JsonProcessingException;

    /**
     * 成为供给者，获得provider权限
     * @param id 用户id
     * @return 是否获取成功
     * @throws JsonProcessingException 数据解析转化为Json出错
     */
    Future<String> becomeProvider(long id) throws JsonProcessingException;

    /**
     * 获取用户昵称
     * @param id 用户id
     * @return 返回用户昵称
     * @throws JsonProcessingException 数据解析转化为Json出错
     */
    Future<String> getUserName(long id) throws JsonProcessingException;

    /**
     * 验证密码
     * @param id 用户id
     * @param psw 用户密码
     * @return 返回是否正确
     * @throws JsonProcessingException 数据解析转化为Json出错
     */
    Future<String> verify(long id, String psw) throws JsonProcessingException;

    /**
     * 获取用户信息页视图数据
     * @param id 用户id
     * @return 返回用户信息页所需信息
     * @throws JsonProcessingException 数据解析转化为Json出错
     */
    Future<String> getUserInfo(long id) throws JsonProcessingException;
}
