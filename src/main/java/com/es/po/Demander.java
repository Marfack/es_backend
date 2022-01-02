package com.es.po;

import lombok.Data;

/**
 * Created on 2021/12/13 11:50
 *
 * @author Marfack
 */
@Data
public class Demander {

    // 用户id
    private long userId;

    // 租用次数
    private int hireTimes;

    // 需求方企业描述
    private String enterpriseDescription;
}
