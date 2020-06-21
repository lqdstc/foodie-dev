package com.imooc.pojo.vo;

import lombok.Data;

import java.util.List;

/**
 * vo是数据库查询出来的内容
 *
 * 二级分类VO
 */
@Data
public class CategoryVo {
    private Integer id;
    private String name;
    private String type;
    private Integer fatherId;




    /**
     * 三级分类 vo list
     */
    private List<SubCategoryVo> subCatList;
}
