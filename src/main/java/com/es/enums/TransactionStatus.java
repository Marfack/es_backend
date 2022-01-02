package com.es.enums;

/**
 * Created on 2021/12/13 20:06
 *
 * @author Marfack
 */
public enum TransactionStatus {
    /**
     * 订单状态枚举
     */
    CREATED(0), WORKING(1), COMPLETED(2), DROPPED(3);

    public final int status;

    TransactionStatus(int status) {
        this.status = status;
    }
}
