package com.es.vo;

import lombok.Data;

/**
 * Created on 2021/12/17 15:14
 * 接收注册表单pojo类
 * @author Marfack
 */
@Data
public class RegisterVo {

    /**
     * 用户名/账号
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 发出验证码的时间
     */
    private long time;
}
