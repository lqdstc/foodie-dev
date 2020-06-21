package com.imooc.controller;

import com.imooc.enums.PayMethod;
import com.imooc.pojo.bo.SubmitOrderBo;
import com.imooc.service.OrderService;
import com.imooc.utils.CookieUtils;
import com.imooc.utils.JSONResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@Api(value = "订单相关", tags = {"订单相关的api"})
@RequestMapping("orders")
@RestController
public class OrdersController extends BaseController {

    @Autowired
    private OrderService orderService;


    @ApiOperation(value = "用户下单", notes = "用户下单", httpMethod = "POST")
    @PostMapping("/create")
    public JSONResult create(
            @RequestBody SubmitOrderBo submitOrderBo,
            HttpServletRequest request,
            HttpServletResponse response) {

        //验证支付方式
        if (submitOrderBo.getPayMethod() != PayMethod.WEIXINPAY.type
                && submitOrderBo.getPayMethod() != PayMethod.ALIPAY.type) {
            return JSONResult.errorMsg("支付方式错误");
        }


        System.out.println(submitOrderBo.toString());

        //1.创建订单
        String orderId = orderService.createOrder(submitOrderBo);

        //2.创建订单以后，移除购物车中已结算的商品
        // TODO 整合Redis之后，完善购物车中的已结算商品清除，并且同步到前端的cookie
        CookieUtils.setCookie(request, response, FOODIE_SHOPCART, "", true);

        //3.向支付中心 发送 当前订单,用于保存支付中心的订单

        return JSONResult.ok(orderId);
    }


}
