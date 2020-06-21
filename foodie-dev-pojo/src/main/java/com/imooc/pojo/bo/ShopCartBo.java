package com.imooc.pojo.bo;

import lombok.Data;

@Data
/**
 * 购物车BO
 */
public class ShopCartBo {

    private String itemId;
    private String itemImgUrl;
    private String itemName;
    private String specId; //规格id
    private String specName; //规格名称
    private Integer buyCounts; //购买数量
    private String priceDiscount;
    private String priceNormal;

    @Override
    public String toString() {
        return "ShopCartBo{" +
                "itemId='" + itemId + '\'' +
                ", itemImgUrl='" + itemImgUrl + '\'' +
                ", itemName='" + itemName + '\'' +
                ", specId='" + specId + '\'' +
                ", specName='" + specName + '\'' +
                ", buyCounts=" + buyCounts +
                ", priceDiscount='" + priceDiscount + '\'' +
                ", priceNormal='" + priceNormal + '\'' +
                '}';
    }
}
