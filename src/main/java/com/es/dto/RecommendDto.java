package com.es.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created on 2021/12/31 1:19
 *
 * @author Marfack
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecommendDto {
    /**
     * 员工id
     */
    private long staffId;

    /**
     * 员工姓名
     */
    private String staffName;

    /**
     * 企业id
     */
    private long enterpriseId;

    /**
     * 企业名
     */
    private String enterpriseName;

    /**
     * 推荐岗位id
     */
    private int careerId;

    /**
     * 推荐岗位
     */
    private String careerName;

    /**
     * 期望薪资
     */
    private double expectedSalary;
}
