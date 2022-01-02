package com.es.vo;

import lombok.Data;

/**
 * Created on 2021/12/27 21:42
 *
 * @author Marfack
 */
@Data
public class WalletVo {
    /**
     * 用户名
     */
    private String name;

    /**
     * 账户金额
     */
    private double amount;
}
