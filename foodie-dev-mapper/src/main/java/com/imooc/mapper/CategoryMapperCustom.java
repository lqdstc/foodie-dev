package com.imooc.mapper;

import com.imooc.pojo.vo.CategoryVo;
import com.imooc.pojo.vo.NewItemsVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 自定义 查询SQL 接口
 */
public interface CategoryMapperCustom {

    /**
     * 根据父id 查询子id
     *
     * @param rootCatId 父id
     * @return
     */
    public List<CategoryVo> getSubCatList(Integer rootCatId);

    /**
     * 查询首页每个一级分类下的 最新6条商品数据
     * @param map
     * @return
     */
    public List<NewItemsVO> getSixNewItemsLazy(@Param("paramsMap") Map<String, Object> map);



}