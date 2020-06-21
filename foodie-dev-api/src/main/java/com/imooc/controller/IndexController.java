package com.imooc.controller;


import com.imooc.enums.YesOrNo;
import com.imooc.pojo.Carousel;
import com.imooc.pojo.Category;
import com.imooc.pojo.vo.CategoryVo;
import com.imooc.pojo.vo.NewItemsVO;
import com.imooc.service.CarouselService;
import com.imooc.service.CategoryService;
import com.imooc.utils.JSONResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(value = "首页", tags = {"首页展示的相关接口"})
@RestController
@RequestMapping("index")
public class IndexController {

    @Autowired
    private CarouselService carouselService;

    //注入 service接口
    @Autowired
    private CategoryService categoryService;

    /**
     * 首页轮播图列表
     *
     * @return 首页轮播图列表
     */
    @ApiOperation(value = "获取首页轮播图列表", notes = "List<Carousel>", httpMethod = "GET")
    @GetMapping("/carousel")
    public JSONResult carousel() {
        List<Carousel> list = carouselService.queryAll(YesOrNo.YES.type);

        System.out.println(list.toArray());

        //返回list 会变成json 数组
        return JSONResult.ok(list);

    }

    /**
     * 首页分类展示需求：
     * 1.第一次刷新主页查询大分类，渲染展示到首页
     * 2.如果鼠标移到大分类，则加载其子分类的内容，如果已经存在子分类，则不需要加载
     */
    @ApiOperation(value = "获取商品分类(一级分类)", notes = "获取商品分类(一级分类)", httpMethod = "GET")
    @GetMapping("/cats")
    public JSONResult cates() {

        List<Category> list = categoryService.queryAllRootLevelCat();

        return JSONResult.ok(list);
    }

    /**
     * 获取商品子分类
     */
    @ApiOperation(value = "获取商品子分类", notes = "获取商品子分类", httpMethod = "GET")
    @GetMapping("/subCat/{rootCatId}")
    public JSONResult subCat(
            @ApiParam(name = "rootCatId", value = "一级分类id", required = true) //接口注解
            @PathVariable Integer rootCatId) {

        if (rootCatId == null) {
            return JSONResult.errorMsg("请求错误");
        }

        List<CategoryVo> list = categoryService.getSubCatList(rootCatId);

        return JSONResult.ok(list);
    }



    /**
     * 查询每个一级分类下的6条商品数据
     */
    @ApiOperation(value = "查询每个一级分类下的6条商品数据", notes = "查询每个一级分类下的6条商品数据", httpMethod = "GET")
    @GetMapping("/sixNewItems/{rootCatId}")
    public JSONResult sixNewItems(
            @ApiParam(name = "rootCatId", value = "一级分类id", required = true) //接口注解
            @PathVariable Integer rootCatId) {

        if (rootCatId == null) {
            return JSONResult.errorMsg("分类不存在");
        }

        List<NewItemsVO> list = categoryService.getSixNewItemsLazy(rootCatId);

        return JSONResult.ok(list);
    }


}
