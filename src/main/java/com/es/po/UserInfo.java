package com.es.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * Created on 2021/12/12 0:25
 *
 * @author Marfack
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserInfo {
    /**
     * 用户id
     */
    private long userId;

    /**
     * 用户账号
     */
    private String userNo;

    /**
     * 用户密码
     */
    private String userPassword;

    /**
     * 用户类型，0为默认，1为需求者，2为供给者
     */
    private int userClass;

    /**
     * 上次登录时间
     */
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date lastLoginTime;
}
