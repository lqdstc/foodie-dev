package com.imooc.pojo.vo;


import com.imooc.pojo.Items;
import com.imooc.pojo.ItemsImg;
import com.imooc.pojo.ItemsParam;
import com.imooc.pojo.ItemsSpec;
import lombok.Data;

import java.util.List;

/**
 * 商品详情 数据VO 数据组装
 */
@Data
public class ItemInfoVO {

    //商品详情
    Items item;

    //商品图片列表
    List<ItemsImg> itemImgList;

    //商品规格
    List<ItemsSpec> itemSpecList;

    //商品参数
    ItemsParam itemParams ;

}
