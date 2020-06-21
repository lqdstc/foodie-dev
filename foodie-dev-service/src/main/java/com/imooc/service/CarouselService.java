package com.imooc.service;

import com.imooc.pojo.Carousel;

import java.util.List;

/**
 * 轮播图Service接口
 */
public interface CarouselService {

    /**
     * 查询所有轮播图列表
     *     List<Carousel>  所有是多个所以用List
     * @param isShow 是否展示
     * @return 所有轮播图
     */
    public List<Carousel> queryAll(Integer isShow);
}
