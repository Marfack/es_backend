package com.es.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * Created on 2021/12/22 8:48
 *
 * @author Marfack
 */
@Data
public class StaffRegisterVo implements Serializable {

    /**
     * 员工id
     */
    private long staffId;

    /**
     * 城市id
     */
    private int cityId;

    /**
     * 描述
     */
    private String staffDescription;

    /**
     * 员工姓名
     */
    private String staffName;

    /**
     * 员工年龄
     */
    private int staffAge;

    /**
     * 员工性别
     */
    private int staffSex;

    /**
     * 期望薪资
     */
    private int expectedSalary;

    /**
     * 员工职业id列表
     */
    private List<Integer> careers;

    /**
     * 员工状态
     */
    private int status;
}
