package com.es.vo;

import com.es.po.CareerInfo;
import lombok.Data;

import java.util.List;

/**
 * Created on 2021/12/18 17:39
 * 用于将员工列表信息返回视图层
 * @author Marfack
 */
@Data
public class StaffInfoVo {

    /**
     * 员工id
     */
    private long staffId;

    /**
     * 员工姓名
     */
    private String staffName;

    /**
     * 所属公司id
     */
    private long enterpriseId;

    /**
     * 所属公司名称
     */
    private String enterpriseName;

    /**
     * 期望薪资
     */
    private int expectedSalary;

    /**
     * 员工职位列表
     */
    private List<CareerInfo> careers;

    /**
     * 员工状态
     */
    private int status;
}
