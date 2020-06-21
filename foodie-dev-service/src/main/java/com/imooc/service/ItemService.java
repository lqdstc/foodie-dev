package com.imooc.service;

import com.imooc.pojo.Items;
import com.imooc.pojo.ItemsImg;
import com.imooc.pojo.ItemsParam;
import com.imooc.pojo.ItemsSpec;
import com.imooc.pojo.vo.CommentLevelCountsVo;
import com.imooc.pojo.vo.ShopCartVO;
import com.imooc.utils.PagedGridResult;

import java.util.List;

/**
 * 商品相关的Service接口
 */
public interface ItemService {

    /**
     * 根据商品 id查询详情
     *
     * @param itemId 商品ID
     * @return Items商品详情
     */
    public Items queryItemById(String itemId);

    /**
     * 根据商品 id查询商品图片列表
     *
     * @param itemId 商品ID
     * @return List <ItemsImg> 品图片列表
     */
    public List<ItemsImg> queryItemImgList(String itemId);


    /**
     * 根据商品Id 查询规格列表
     *
     * @param itemId 商品ID
     * @return 规格列表
     */
    public List<ItemsSpec> queryItemSpecList(String itemId);


    /**
     * 根据商品ID查询商品参数
     *
     * @param itemId 商品ID
     * @return ItemsParam商品参数
     */
    public ItemsParam queryItemsParam(String itemId);

    /**
     * 根据id查询商品评价数量
     *
     * @param itemId 商品id
     * @return CommentLevelCountsVo评价数量
     */
    public CommentLevelCountsVo queryCommentCounts(String itemId);


    /**
     * 根据商品ID 评价等级 查询商品的评价(分页)
     *
     * @param itemId   商品ID
     * @param level    评价等级
     * @param page     第几页
     * @param pageSize 每页显示多少条
     * @return PagedGridResult分页
     */
    public PagedGridResult queryPagedComments(String itemId, Integer level,
                                              Integer page, Integer pageSize);


    /**
     * 商品搜索(分页)
     *
     * @param keywords 搜索内容
     * @param sort     排序规则
     * @param page     第几页
     * @param pageSize 每页显示多少条
     * @return PagedGridResult分页
     */
    public PagedGridResult searchItems(String keywords, String sort,
                                       Integer page, Integer pageSize);

    /**
     * 根据首页三级分类id商品搜索(分页)
     *
     * @param catId    商品id
     * @param sort     排序规则
     * @param page     第几页
     * @param pageSize 每页显示多少条
     * @return
     */
    public PagedGridResult searchItems(Integer catId, String sort,
                                       Integer page, Integer pageSize);

    /**
     * 根据规格ids 查询最新的购物车中商品数据 (用于刷新渲染购物车中的数据)
     *
     * @param specIds 规格ids
     * @return 新的购物车中商品数据  List <ShopCartVO>
     */
    public List<ShopCartVO> queryItemsBySpecIds(String specIds);

    /**
     * 根据商品规格id获取规格对象的具体信息
     *
     * @param specId 商品规格id
     * @return ItemsSpec规格具体信息
     */
    public ItemsSpec queryItemSpecById(String specId);


    /**
     * 根据商品id获得的商品图片主图url
     *
     * @param itemId 商品id
     * @return String类型 商品图片主图url
     */
    public String queryItemMainImgById(String itemId);


    /**
     *  减少库存
     * @param specId 规格id
     * @param buyCounts 购买数量
     */
    public void decreaseItemSpecStock(String specId,int buyCounts);

}

