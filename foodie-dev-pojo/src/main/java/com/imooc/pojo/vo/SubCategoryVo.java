package com.imooc.pojo.vo;

import lombok.Data;

/**
 * 三级分类Vo
 * 所需要查询出来的内容
 */
@Data
public class SubCategoryVo {


    private Integer subId;
    private String subName;
    private String subType;
    private Integer subFatherId;

}
