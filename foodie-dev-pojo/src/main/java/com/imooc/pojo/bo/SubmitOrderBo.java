package com.imooc.pojo.bo;


import lombok.Data;

/**
 * 用于创建订单的BO对象
 */
@Data
public class SubmitOrderBo {

    /**
     * 用户id
     */
    private String UserId;
    /**
     * 规格ids
     */
    private String itemSpecIds;
    /**
     * 地址id
     */
    private String addressId;
    /**
     * 支付方式
     */
    private Integer PayMethod;
    /**
     * 买家的备注
     */
    private String leftMsg;
}
