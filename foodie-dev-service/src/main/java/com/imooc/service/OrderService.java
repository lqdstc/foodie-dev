package com.imooc.service;

import com.imooc.pojo.bo.SubmitOrderBo;

public interface OrderService {

    /**
     * 创建订单相关信息
     *
     * @param submitOrderBo
     * @return String 订单id
     */
    public String createOrder(SubmitOrderBo submitOrderBo);

}
