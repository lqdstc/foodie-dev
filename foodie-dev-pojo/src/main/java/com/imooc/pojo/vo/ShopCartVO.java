package com.imooc.pojo.vo;

import lombok.Data;

@Data
public class ShopCartVO {

    private String itemId;
    private String itemImgUrl;
    private String itemName;
    private String specId; //规格id
    private String specName; //规格名称
    private String priceDiscount;
    private String priceNormal;


}
