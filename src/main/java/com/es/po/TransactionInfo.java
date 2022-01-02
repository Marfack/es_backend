package com.es.po;

import lombok.Data;

/**
 * Created on 2021/12/13 20:04
 *
 * @author Marfack
 */
@Data
public class TransactionInfo {

    // 交易id
    private long transactionId;

    // 买方id
    private long buyerId;

    // 交易状态
    private int transactionStatus;
}
