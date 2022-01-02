package com.es.dto;

import lombok.Data;

import java.sql.Timestamp;
import java.util.List;

/**
 * Created on 2021/12/13 20:52
 *
 * @author Marfack
 */
@Data
public class TransactionDto {

    /**
     * 交易id
     */
    private long transactionId;

    /**
     * 买家id
     */
    private long buyerId;

    /**
     * 交易状态
     */
    private int transactionStatus;

    /**
     * 交易金额
     */
    private double transactionAmount;

    /**
     * 交易时间
     */
    private Timestamp transactionTime;

    /**
     * 交易中的员工信息列表
     */
    private List<StaffInTransactionDto> staffs;
}
