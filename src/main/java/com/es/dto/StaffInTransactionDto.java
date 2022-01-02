package com.es.dto;

import lombok.Data;

/**
 * Created on 2021/12/20 19:11
 *
 * @author Marfack
 */
@Data
public class StaffInTransactionDto {

    /**
     * 员工id
     */
    private long id;

    /**
     * 员工姓名
     */
    private String name;

    /**
     * 所属公司id
     */
    private long enterpriseId;

    /**
     * 所属公司名
     */
    private String enterpriseName;

    /**
     * 实付薪酬
     */
    private int salary;

    /**
     * 员工状态
     */
    private int status;
}
