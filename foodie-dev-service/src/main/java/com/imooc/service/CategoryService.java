package com.imooc.service;


import com.imooc.pojo.Category;
import com.imooc.pojo.vo.CategoryVo;
import com.imooc.pojo.vo.NewItemsVO;

import java.util.List;

/**
 * 分类相关的Service 接口
 */
public interface CategoryService {

    /**
     * 查询第一级分类
     *
     * @return
     */
    public List<Category> queryAllRootLevelCat();


    /**
     * 根据一级分类id 查询子分类
     * 传入自定义mapper 类里的 rootCatId
     *
     * @param rootCatId 一级分类Id
     * @return
     */
    public List<CategoryVo> getSubCatList(Integer rootCatId);


    /**
     * 查询首页每个一级分类下的 最新6条商品数据
     *
     * @param rootCatId 一级分类Id
     * @return
     */
    public List<NewItemsVO> getSixNewItemsLazy(Integer rootCatId);
}
