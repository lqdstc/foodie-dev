package com.imooc.pojo.vo;

import lombok.Data;

import java.util.List;


/**
 * 最新商品vo
 */
@Data
public class NewItemsVO {

    private Integer rootCatId;
    private String rootCatName;
    private String slogan;
    private String catImage;
    private String bgColor;

    //简单商品详情  list
    private List<SimpleItemVO> simpleItemList;

}
