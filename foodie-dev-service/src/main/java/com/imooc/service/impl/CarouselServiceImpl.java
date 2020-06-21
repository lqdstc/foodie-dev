package com.imooc.service.impl;

import com.imooc.mapper.CarouselMapper;
import com.imooc.pojo.Carousel;
import com.imooc.service.CarouselService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * 轮播图实现类
 */
@Service
public class CarouselServiceImpl implements CarouselService {

    //注入mapper
    @Autowired
    private CarouselMapper carouselMapper;

    //查询事务
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<Carousel> queryAll(Integer isShow) {
        //条件查询
        Example example = new Example(Carousel.class);
        //查询条件 按sort 倒叙
        example.orderBy("sort").desc();
//        创建条件
        Example.Criteria criteria = example.createCriteria();
        //查询条件是  是否展示
        criteria.andEqualTo("isShow", isShow);

        //查询
        List<Carousel> result = carouselMapper.selectByExample(example);
        return result;
    }
}
