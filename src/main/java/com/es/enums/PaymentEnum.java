package com.es.enums;

/**
 * Created on 2021/12/20 14:09
 * 支付方式枚举类
 * @author Marfack
 */
public enum PaymentEnum {
    /**
     * ALIPAY支付宝，WE_CHAT微信支付，ONLINE_BANKING网银支付，PAY_PAL paypal支付
     */
    ALIPAY(0, "支付宝"),
    WE_CHAT(1, "微信支付"),
    ONLINE_BANKING(2, "网银支付"),
    PAY_PAL(3, "Paypal支付");

    private final int index;

    private final String name;

    PaymentEnum(int index, String name) {
        this.index = index;
        this.name = name;
    }

    public int getIndex() {
        return index;
    }

    public String getName() {
        return name;
    }
}
