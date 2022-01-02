package com.es.vo;

import lombok.Data;

/**
 * Created on 2021/12/20 11:52
 *
 * @author Marfack
 */
@Data
public class RetrievePasswordVo {

    /**
     * 用户邮箱
     */
    private String email;

    /**
     * 用户新密码
     */
    private String password;

    /**
     * 时间
     */
    private long time;

    /**
     * 用户id
     */
    private String userId;
}
