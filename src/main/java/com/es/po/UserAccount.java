package com.es.po;

import lombok.Data;

/**
 * Created on 2021/12/12 20:20
 *
 * @author Marfack
 */
@Data
public class UserAccount {

    // 账户所属用户id
    private long id;

    // 账户余额
    private double accountBalance;

    // 账户冻结资金（交易中）
    private double freezingMoney;
}
