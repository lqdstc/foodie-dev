package com.imooc.controller;

import com.imooc.pojo.UserAddress;
import com.imooc.pojo.bo.AddressBo;
import com.imooc.service.AddressService;
import com.imooc.utils.JSONResult;
import com.imooc.utils.MobileEmailUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Api(value = "地址相关", tags = {"地址相关的api"})
@RequestMapping("address")
@RestController
public class AddressController {
    @Autowired
    private AddressService addressService;

    /**
     * 用户在确认订单页面， 可以针对收货地址做如下操作；
     * 1.查询用户所有的收货地址
     * 2.新增收货地址
     * 3.删除收货地址
     * 4.修改收货地址
     * 5.设置默认地址
     */

    /**
     * 查询用户所有的收货地址
     *
     * @return
     */
    @ApiOperation(value = "根据用户ID查询用户所有的收货地址", notes = "根据用户ID查询用户所有的收货地址", httpMethod = "POST")
    @PostMapping("/list")
    public JSONResult list(
            @ApiParam(name = "userId", value = "用户id", required = true)
            @RequestParam String userId) {
        if (StringUtils.isBlank(userId)) {
            return JSONResult.errorMsg("");
        }

        List<UserAddress> list = addressService.queryAll(userId);
        return JSONResult.ok(list);

    }


    @ApiOperation(value = "用户新增 收货地址的", notes = "根据用户ID查询用户所有的收货地址", httpMethod = "POST")
    @PostMapping("/add")
    public JSONResult add(
            @RequestBody AddressBo addressBo) {

        JSONResult checkRes = checkAddress(addressBo);
        if (checkRes.getStatus() != 200) {
            return checkRes;
        }

        addressService.addNewUserAddress(addressBo);
        return JSONResult.ok();

    }

    @ApiOperation(value = "用户修改 收货地址", notes = "用户修改 收货地址", httpMethod = "POST")
    @PostMapping("/update")
    public JSONResult update(
            @RequestBody AddressBo addressBo) {

        //用户地址id不能为空
        if (StringUtils.isBlank(addressBo.getAddressId())) {
            return JSONResult.errorMsg("用户不能为空");
        }


        JSONResult checkRes = checkAddress(addressBo);
        if (checkRes.getStatus() != 200) {
            return checkRes;
        }

        addressService.updateUserAddress(addressBo);
        return JSONResult.ok();

    }


    //简单判断收货地址信息
    private JSONResult checkAddress(AddressBo addressBo) {
        //判断收货人
        String receiver = addressBo.getReceiver();
        if (StringUtils.isBlank(receiver)) {
            return JSONResult.errorMsg("收货人不能为空");
        }
        if (receiver.length() > 12) {
            return JSONResult.errorMsg("收货人姓名不能太长");
        }

        String mobile = addressBo.getMobile();
        if (StringUtils.isBlank(mobile)) {
            return JSONResult.errorMsg("手机号不能为空");
        }
        if (mobile.length() != 11) {
            return JSONResult.errorMsg("手机号长度不对");
        }

        boolean isMobileOk = MobileEmailUtils.checkMobileIsOk(mobile);
        if (!isMobileOk) {
            return JSONResult.errorMsg("手机号格式不对");
        }

        String province = addressBo.getProvince();
        String city = addressBo.getCity();
        String district = addressBo.getDistrict();
        String detail = addressBo.getDetail();
        if (StringUtils.isBlank(province) ||
                StringUtils.isBlank(city) ||
                StringUtils.isBlank(district) ||
                StringUtils.isBlank(detail)) {
            return JSONResult.errorMsg("收货地址信息不能为空");
        }
        return JSONResult.ok();

    }


    @ApiOperation(value = "用户删除 收货地址", notes = "用户删除 收货地址", httpMethod = "POST")
    @PostMapping("/delete")
    public JSONResult delete(
            @ApiParam(name = "userId", value = "用户id", required = true)
            @RequestParam String userId,
            @RequestParam String addressId) {

        //用户地址id不能为空
        if (StringUtils.isBlank(userId) || StringUtils.isBlank(addressId)) {
            return JSONResult.errorMsg("");
        }


        addressService.deleteUserAddress(userId, addressId);
        return JSONResult.ok();

    }

    @ApiOperation(value = "根据用户的id 和地址id 设定为默认地址", notes = "根据用户的id 和地址id 设定为默认地址", httpMethod = "POST")
    @PostMapping("/setDefalut")
    public JSONResult setDefalut(
            @ApiParam(name = "userId", value = "用户id", required = true)
            @RequestParam String userId,
            @RequestParam String addressId) {

        //用户地址id不能为空
        if (StringUtils.isBlank(userId) || StringUtils.isBlank(addressId)) {
            return JSONResult.errorMsg("");
        }


        addressService.setDefaultAddress(userId, addressId);
        return JSONResult.ok();

    }


}
