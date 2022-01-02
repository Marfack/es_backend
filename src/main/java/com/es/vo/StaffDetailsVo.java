package com.es.vo;

import com.es.po.CareerInfo;
import lombok.Data;

import java.util.List;

/**
 * Created on 2021/12/19 10:27
 * 员工详细信息视图层实体类
 * @author Marfack
 */
@Data
public class StaffDetailsVo {

    /**
     * 员工id
     */
    private long staffId;

    /**
     * 员工姓名
     */
    private String staffName;

    /**
     * 所在城市
     */
    private int cityId;

    /**
     * 城市名
     */
    private String cityName;

    /**
     * 省份id
     */
    private int provinceId;

    /**
     * 省份名
     */
    private String provinceName;

    /**
     * 员工性别
     */
    private int staffSex;

    /**
     * 员工年龄
     */
    private int staffAge;

    /**
     * 期望薪资
     */
    private int expectedSalary;

    /**
     * 员工描述
     */
    private String description;

    /**
     * 员工所在公司id
     */
    private long enterpriseId;

    /**
     * 公司名
     */
    private String enterpriseName;

    /**
     * 员工状态
     */
    private int staffStatus;

    /**
     * 可胜任的职位
     */
    private List<CareerInfo> careers;
}
