package com.es.po;

import lombok.Data;

/**
 * Created on 2021/12/13 13:33
 *
 * @author Marfack
 */
@Data
public class Staff {

    // 员工id
    private long staffId;

    // 所属公司id
    private long userId;

    // 所在城市id
    private int cityId;

    // 员工描述
    private String staffDescription;

    // 员工姓名
    private String staffName;

    // 员工年龄
    private int staffAge;

    // 员工性别
    private int staffSex;

    // 期望工资
    private int expectedSalary;

    // 员工状态（是否正在工作）
    private int staffStatus;
}
