package com.es.po;

import lombok.Data;

/**
 * Created on 2021/12/13 17:55
 *
 * @author Marfack
 */
@Data
public class CareerInfo {

    // 职业id
    private int careerId;

    // 职业名
    private String careerName;

    // 职业方向id
    private int classId;
}
