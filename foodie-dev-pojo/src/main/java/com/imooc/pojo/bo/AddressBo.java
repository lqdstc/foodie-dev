package com.imooc.pojo.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 用户新增收货修改地址的BO
 */
@Data
@ApiModel(value = "用户新增收货修改地址的BO", description = "从客户端，由用户传入的数据封装在此entity中")
public class AddressBo {

    @ApiModelProperty(value = "用户地址id", name = "addressId", example = "190825CG3AA14Y3C", required = false)
    private String addressId;

    @ApiModelProperty(value = "用户id", name = "userId", example = "1908189H7TNWDTXP", required = true)
    private String userId;

    @ApiModelProperty(value = "收件人姓名", name = "receiver", example = "彭于晏", required = true)
    private String receiver;

    @ApiModelProperty(value = "收件人手机号", name = "mobile", example = "15605555555", required = true)
    private String mobile;

    @ApiModelProperty(value = "省份", name = "province", example = "北京", required = true)
    private String province;

    @ApiModelProperty(value = "城市", name = "city", example = "北京", required = true)
    private String city;

    @ApiModelProperty(value = "区县", name = "district", example = "东城区", required = true)
    private String district;

    @ApiModelProperty(value = "详细地址", name = "detail", example = "345", required = true)
    private String detail;



}
