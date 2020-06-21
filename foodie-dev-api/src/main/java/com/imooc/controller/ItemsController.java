package com.imooc.controller;

import com.imooc.pojo.Items;
import com.imooc.pojo.ItemsImg;
import com.imooc.pojo.ItemsParam;
import com.imooc.pojo.ItemsSpec;
import com.imooc.pojo.vo.CommentLevelCountsVo;
import com.imooc.pojo.vo.ItemInfoVO;
import com.imooc.pojo.vo.ShopCartVO;
import com.imooc.service.ItemService;
import com.imooc.utils.JSONResult;
import com.imooc.utils.PagedGridResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(value = "商品详情接口", tags = {"商品详情页相关接口"})
@RestController
@RequestMapping("items")
public class ItemsController extends BaseController {
    @Autowired
    private ItemService itemService;

    @ApiOperation(value = "查询商品详情", notes = "查询商品详情", httpMethod = "GET")
    @GetMapping("/info/{itemId}")
    public JSONResult info(
            @ApiParam(name = "itemId", value = "商品id", required = true)
            @PathVariable String itemId) {  //@PathVariable路径参数
        if (StringUtils.isAllBlank(itemId)) {  //判断字符串是否为空
            return JSONResult.errorMsg("商品不存在");
        }

        //商品详情
        Items item = itemService.queryItemById(itemId);

        //商品图片列表
        List<ItemsImg> itemsImgList = itemService.queryItemImgList(itemId);

        //商品规格
        List<ItemsSpec> itemsSpecList = itemService.queryItemSpecList(itemId);

        //商品参数
        ItemsParam itemsParam = itemService.queryItemsParam(itemId);

        ItemInfoVO itemInfoVO = new ItemInfoVO();

        itemInfoVO.setItem(item);
        itemInfoVO.setItemImgList(itemsImgList);
        itemInfoVO.setItemSpecList(itemsSpecList);
        itemInfoVO.setItemParams(itemsParam);

        return JSONResult.ok(itemInfoVO);
    }


    @ApiOperation(value = "查询商品评价等级", notes = "查询商品评价等级", httpMethod = "GET")
    @GetMapping("/commentLevel")
    public JSONResult commentLevel(
            @ApiParam(name = "itemId", value = "商品ID", required = true)
            @RequestParam String itemId)  //请求参数@RequestParam   commentLevel?itemId=
    {
        if (StringUtils.isBlank(itemId)) {
            return JSONResult.errorMsg(null);
        }

        CommentLevelCountsVo countsVo = itemService.queryCommentCounts(itemId);

        return JSONResult.ok(countsVo);
    }

    @ApiOperation(value = "查询商品评论带分页", notes = "查询商品评论", httpMethod = "GET")
    @GetMapping("/comments")
    public JSONResult comments(
            @ApiParam(name = "itemId", value = "商品ID", required = true)
            @RequestParam String itemId,  //请求参数@RequestParam   commentLevel?itemId=
            @ApiParam(name = "level", value = "评价等级", required = false)
            @RequestParam Integer level,
            @ApiParam(name = "page", value = "查询下一页的第几页", required = false)
            @RequestParam Integer page,
            @ApiParam(name = "pageSize", value = "查询下一页的第几页", required = false)
            @RequestParam Integer pageSize) {

        if (StringUtils.isBlank(itemId)) {
            return JSONResult.errorMsg(null);
        }

        if (page == null) {
            page = 1;
        }

        if (pageSize == null) {
            pageSize = COMMENT_PAGE_SIZE;
        }

        //分页结果 返回 PagedGridResult

        PagedGridResult grid = itemService.queryPagedComments(itemId, level, page, pageSize);

        return JSONResult.ok(grid);
    }

    @ApiOperation(value = "搜索商品列表", notes = "搜索商品列表", httpMethod = "GET")
    @GetMapping("/search")
    public JSONResult search(
            @ApiParam(name = "keywords", value = "关键字", required = true)
            @RequestParam String keywords,  //请求参数@RequestParam   commentLevel?itemId=
            @ApiParam(name = "sort", value = "排序规则", required = false)
            @RequestParam String sort,
            @ApiParam(name = "page", value = "查询下一页的第几页", required = false)
            @RequestParam Integer page,
            @ApiParam(name = "pageSize", value = "查询下一页的第几页", required = false)
            @RequestParam Integer pageSize) {

        if (StringUtils.isBlank(keywords)) {
            return JSONResult.errorMsg(null);
        }

        if (page == null) {
            page = 1;
        }

        if (pageSize == null) {
            pageSize = PAGE_SIZE;
        }

        //分页结果 返回 PagedGridResult
        PagedGridResult grid = itemService.searchItems(keywords, sort, page, pageSize);

        return JSONResult.ok(grid);
    }

    @ApiOperation(value = "据首页三级分类id商品搜索(分页)", notes = "据首页三级分类id商品搜索(分页)", httpMethod = "GET")
    @GetMapping("/catItems")
    public JSONResult search(
            @ApiParam(name = "catId", value = "三级分类商品id", required = true)
            @RequestParam Integer catId,  //请求参数@RequestParam   commentLevel?itemId=
            @ApiParam(name = "sort", value = "排序规则", required = false)
            @RequestParam String sort,
            @ApiParam(name = "page", value = "查询下一页的第几页", required = false)
            @RequestParam Integer page,
            @ApiParam(name = "pageSize", value = "查询下一页的第几页", required = false)
            @RequestParam Integer pageSize) {

        if (catId == null) {
            return JSONResult.errorMsg(null);
        }

        if (page == null) {
            page = 1;
        }

        if (pageSize == null) {
            pageSize = PAGE_SIZE;
        }

        //分页结果 返回 PagedGridResult
        PagedGridResult grid = itemService.searchItems(catId, sort, page, pageSize);

        return JSONResult.ok(grid);
    }


    @ApiOperation(value = "根据规格ids 查询最新的购物车中商品数据 (用于刷新渲染购物车中的数据)", notes = "根据规格ids 查询最新的购物车中商品数据 (用于刷新渲染购物车中的数据)", httpMethod = "GET")
    @GetMapping("/refresh")
    public JSONResult refresh(
            @ApiParam(name = "itemSpecIds", value = "拼接的规格ids", required = true, example = "1003,1003,1005")
            @RequestParam String itemSpecIds)  //请求参数@RequestParam   commentLevel?itemId=
    {
        if (StringUtils.isBlank(itemSpecIds)) {
            return JSONResult.ok();
        }
        List<ShopCartVO> list = itemService.queryItemsBySpecIds(itemSpecIds);

        return JSONResult.ok(list);
    }

}
