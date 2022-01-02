package com.es.po;

import lombok.Data;

/**
 * Created on 2021/12/13 12:52
 *
 * @author Marfack
 */
@Data
public class Provider {

    // 用户id
    private long userId;

    // 外包次数
    private int provideTimes;

    // 总评分
    private double totalScore;

    // 供给方描述
    private String providerDescription;
}
