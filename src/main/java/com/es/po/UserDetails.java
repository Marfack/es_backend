package com.es.po;

import lombok.Data;

/**
 * Created on 2021/12/12 10:08
 *
 * @author Marfack
 */
@Data
public class UserDetails {

    // 用户id
    private long userId;

    // 所在城市id
    private int cityId;

    // 企业号
    private String businessNo;

    // 用户名
    private String userName;

    // 联系电话
    private long userPhoneNo;

    // 邮箱
    private String userEmail;
}
