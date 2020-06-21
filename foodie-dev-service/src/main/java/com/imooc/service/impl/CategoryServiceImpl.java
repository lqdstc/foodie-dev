package com.imooc.service.impl;


import com.imooc.mapper.CategoryMapper;
import com.imooc.mapper.CategoryMapperCustom;
import com.imooc.pojo.Category;
import com.imooc.pojo.vo.CategoryVo;
import com.imooc.pojo.vo.NewItemsVO;
import com.imooc.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 首页分类
 */
@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    //注入自定义mapper
    @Autowired
    private CategoryMapperCustom categoryMapperCustom;

    //事务
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<Category> queryAllRootLevelCat() {
        //根据条件查询
        Example example = new Example(Category.class);
        //        创建条件
        Example.Criteria criteria = example.createCriteria();
        //查询条件是 type是1的   就是一级分类
        criteria.andEqualTo("type", 1);
        //查询
        List<Category> result = categoryMapper.selectByExample(example);

        return result;
    }

    //查询事务
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<CategoryVo> getSubCatList(Integer rootCatId) {

        return categoryMapperCustom.getSubCatList(rootCatId);


    }

    /**
     * 查询首页每个一级分类下的 最新6条商品数据
     * @param rootCatId 一级分类Id
     * @return
     */
    //查询事务
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<NewItemsVO> getSixNewItemsLazy(Integer rootCatId) {
        //接口是map类型  新建一个MAP 放入值
        Map<String, Object> map = new HashMap<>();
        map.put("rootCatId", rootCatId);

        return categoryMapperCustom.getSixNewItemsLazy(map);
    }
}
