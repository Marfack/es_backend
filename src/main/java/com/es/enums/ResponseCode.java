package com.es.enums;


import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;

/**
 * Created on 2021/12/15 16:58
 *
 * @author Marfack
 */
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ResponseCode implements Serializable {

    /**
     * 定义响应码枚举
     */
    SUCCESS("00000", "请求成功"),
    REGISTER_ERROR("A0100", "用户注册错误"),
    USER_NAME_EXIST("A0110", "用户名已被占用"),
    LOGIN_FAILURE("A0200", "登陆失败"),
    USER_NOT_FOUND_ERROR("A0201", "用户不存在"),
    USER_LOCKED_ERROR("A0202", "用户被冻结"),
    WRONG_PASSWORD("A0210", "用户密码错误"),
    TOKEN_ERROR("A0220", "Token失效"),
    VERIFICATION_CODE_ERROR("A0240", "用户验证码错误"),
    VERIFICATION_CODE_TLE("A0241", "用户验证码过期"),
    REQUEST_FAIL("A0300", "请求失败"),
    UNAUTHORIZED_ERROR("A0301", "访问未授权"),
    NOT_LOGIN_ERROR("A0310", "用户未登录"),
    REQUEST_PARAM_ERROR("A0400", "请求参数错误"),
    PARAM_OUT_OF_RANGE("A0410", "数值不在范围内"),
    ACCOUNT_BALANCE_NOT_ENOUGH("A0420", "账户余额不足"),
    STAFF_BUSY("A0451", "员工已有其他签约"),
    SERVER_ERROR("A0500", "服务器异常"),
    WEBSOCKET_DISCONNECTED("A0505", "WebSocket链接断开");

    private final String code;

    private final String msg;

    ResponseCode(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

}
