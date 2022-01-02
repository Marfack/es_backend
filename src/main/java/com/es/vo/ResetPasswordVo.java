package com.es.vo;

import lombok.Data;

/**
 * Created on 2021/12/19 11:28
 * 用于接收改密码业务数据的实体类
 * @author Marfack
 */
@Data
public class ResetPasswordVo {

    /**
     * 原密码
     */
    private String originPsw;

    /**
     * 新密码
     */
    private String newPsw;
}
