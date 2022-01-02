package com.es.vo;

import lombok.Data;

/**
 * Created on 2021/12/27 15:16
 *
 * @author Marfack
 */
@Data
public class UserDetailsVo {

    /**
     * 用户昵称
     */
    private String userName;

    /**
     * 用户权限等级
     */
    private int userClass;

    /**
     * 所在城市ID
     */
    private int cityId;

    /**
     * 所在城市名
     */
    private String cityName;

    /**
     * 所在省份ID
     */
    private int provinceId;

    /**
     * 所在省份名
     */
    private String provinceName;

    /**
     * 营业执照编号
     */
    private String businessNo;

    /**
     * 联系电话
     */
    private String userPhoneNo;

    /**
     * 绑定邮箱
     */
    private String userEmail;
}
