package com.es.po;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * Created on 2021/12/13 20:47
 *
 * @author Marfack
 */
@Data
public class TransactionDetails {

    // 交易id
    private long transactionId;

    // 交易金额
    private double transactionAmount;

    // 交易时间
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date transactionTime;
}
