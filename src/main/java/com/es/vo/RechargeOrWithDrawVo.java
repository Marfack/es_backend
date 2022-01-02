package com.es.vo;

import lombok.Data;

/**
 * Created on 2021/12/21 21:49
 *
 * @author Marfack
 */
@Data
public class RechargeOrWithDrawVo {

    /**
     * 充值金额
     */
    private double amount;

    /**
     * 充值支付方式
     */
    private int payment;
}
