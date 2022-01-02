package com.es.vo;

import lombok.Data;

import java.util.List;

/**
 * Created on 2021/12/21 22:05
 *
 * @author Marfack
 */
@Data
public class PayVo {
    /**
     * 员工id列表
     */
    private List<Long> staffs;

    /**
     * 订单总价格
     */
    private double amount;
}
