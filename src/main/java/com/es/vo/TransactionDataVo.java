package com.es.vo;

import lombok.Data;

import java.util.Date;

/**
 * Created on 2021/12/27 20:47
 *
 * @author Marfack
 */
@Data
public class TransactionDataVo {

    /**
     * 交易方id
     */
    private long id;

    /**
     * 交易方名
     */
    private String name;

    /**
     * 交易金额
     */
    private double amount;

    /**
     * 交易时间
     */
    private Date time;
}
