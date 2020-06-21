package com.imooc.enums;

/**
 * 支付 枚举
 */
public enum PayMethod {
    WEIXINPAY(1, "微信支付"),
    ALIPAY(2, "支付宝支付"),
    ;


    public final Integer type;
    public final String value;

    PayMethod(Integer type, String value) {
        this.type = type;
        this.value = value;
    }
}
