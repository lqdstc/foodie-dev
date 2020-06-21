package com.imooc.controller;

import com.imooc.pojo.bo.ShopCartBo;
import com.imooc.utils.JSONResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Api(value = "购物车接口Controller", tags = {"购物车接口相关的api"})
@RequestMapping("shopcart")
@RestController
public class ShopCatController {


    @ApiOperation(value = "添加商品到购物车", notes = "添加商品到购物车", httpMethod = "POST")
    @PostMapping("/add")
    public JSONResult add(@ApiParam(name = "userId", value = "用户Id", required = true)
                          @RequestParam String userId,  //请求参数@RequestParam   commentLevel?userId=
                          @RequestBody ShopCartBo shopcartBo,  //从前端购物车传入的 @RequestBody的作用其实是将json格式的数据转为java对象
                          HttpServletRequest request,
                          HttpServletResponse response) {

        //判断用户名是否为空
        if (StringUtils.isBlank(userId)) {
            return JSONResult.errorMsg("");
        }

        System.out.println(shopcartBo);

        // TODO  前端用户在登录的情况下，添加商品到购物车，会同时在后端同步购物车在redis;


        return JSONResult.ok();
    }

    @ApiOperation(value = "从购物车中删除商品", notes = "从购物车中删除商品", httpMethod = "POST")
    @PostMapping("/del")
    public JSONResult del(@RequestParam String userId,
                          @RequestParam String itemSpecId, //前端传入的 商品规格id
                          HttpServletRequest request,
                          HttpServletResponse response) {

        if (StringUtils.isBlank(userId) || StringUtils.isBlank(itemSpecId)) {
            return JSONResult.errorMsg("参数不能为空");
        }

        // TODO 用户在页面删除购物车中的商品数据，如果此时用户已经登录，则需要同步删除后端购物车中的商品

        return JSONResult.ok();

    }

}
