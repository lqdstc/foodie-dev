package com.imooc.service.impl;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.imooc.enums.CommentLevel;
import com.imooc.enums.YesOrNo;
import com.imooc.mapper.*;
import com.imooc.pojo.*;
import com.imooc.pojo.vo.CommentLevelCountsVo;
import com.imooc.pojo.vo.ItemCommentVo;
import com.imooc.pojo.vo.SearchItemsVO;
import com.imooc.pojo.vo.ShopCartVO;
import com.imooc.service.ItemService;
import com.imooc.utils.DesensitizationUtil;
import com.imooc.utils.PagedGridResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.*;

@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    private ItemsMapper itemsMapper;

    @Autowired
    private ItemsImgMapper itemsImgMapper;

    @Autowired
    private ItemsSpecMapper itemsSpecMapper;

    @Autowired
    private ItemsParamMapper itemsParamMapper;

    @Autowired
    private ItemsCommentsMapper itemsCommentsMapper;

    @Autowired
    private ItemsMapperCustom itemsMapperCustom;

    /**
     * 根据商品 id查询详情
     *
     * @param itemId 商品ID
     * @return
     */
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public Items queryItemById(String itemId) {

        //根据主键查询
        return itemsMapper.selectByPrimaryKey(itemId);
    }

    /**
     * 根据商品 id查询商品图片列表
     *
     * @param itemId 商品ID
     * @return
     */
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<ItemsImg> queryItemImgList(String itemId) {

        Example itemsImgExp = new Example(ItemsImg.class);

        Example.Criteria criteria = itemsImgExp.createCriteria();
        criteria.andEqualTo("itemId", itemId);

        return itemsImgMapper.selectByExample(itemsImgExp);
    }

    /**
     * 根据商品Id 查询规格列表
     *
     * @param itemId 商品ID
     * @return
     */
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<ItemsSpec> queryItemSpecList(String itemId) {
        Example itemSpecExp = new Example(ItemsSpec.class);

        Example.Criteria criteria = itemSpecExp.createCriteria();
        criteria.andEqualTo("itemId", itemId);

        return itemsSpecMapper.selectByExample(itemSpecExp);
    }

    /**
     * 根据商品ID查询商品参数
     *
     * @param itemId 商品ID
     * @return
     */
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public ItemsParam queryItemsParam(String itemId) {
        Example itemsParamExp = new Example(ItemsParam.class);

        Example.Criteria criteria = itemsParamExp.createCriteria();
        criteria.andEqualTo("itemId", itemId);

        //不是list 用selectOne
        return itemsParamMapper.selectOneByExample(itemsParamExp);
    }

    /**
     * 根据id查询商品评价数量
     *
     * @param itemId 商品ID
     */
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public CommentLevelCountsVo queryCommentCounts(String itemId) {
        Integer goodCounts = getCommentCounts(itemId, CommentLevel.GOOD.type);
        Integer normalCounts = getCommentCounts(itemId, CommentLevel.NORMAL.type);
        Integer badCounts = getCommentCounts(itemId, CommentLevel.BAD.type);

        //评价总数
        Integer totalCounts = goodCounts + normalCounts + badCounts;

        //放进VO里
        CommentLevelCountsVo countsVo = new CommentLevelCountsVo();
        countsVo.setTotalCounts(totalCounts);
        countsVo.setGoodCounts(goodCounts);
        countsVo.setNormalCounts(normalCounts);
        countsVo.setBadCounts(badCounts);

        return countsVo;
    }


    //查询好评数量
    @Transactional(propagation = Propagation.SUPPORTS)
    Integer getCommentCounts(String itemId, Integer level) {
        ItemsComments condition = new ItemsComments();

        condition.setItemId(itemId);
        //如果评论不为空  就设定评论等级
        if (level != null) {
            condition.setCommentLevel(level);
        }
        //根据实体中的属性查询总数
        return itemsCommentsMapper.selectCount(condition);


    }

    /**
     * 根据商品ID 评价等级 查询商品的评价(分页)
     *
     * @param itemId 商品ID
     * @param level  评价等级
     * @return
     */
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public PagedGridResult queryPagedComments(String itemId, Integer level,
                                              Integer page, Integer pageSize) {
        Map<String, Object> map = new HashMap<>();
        map.put("itemId", itemId);
        map.put("level", level);

        //mybatis-pagehelper 分页插件
        /** page: 第几页 * pageSize: 每页显示条数 */
        PageHelper.startPage(page, pageSize);

        List<ItemCommentVo> list = itemsMapperCustom.queryItemComments(map);
        //匿名设置
        for (ItemCommentVo vo : list) {
            vo.setNickname(DesensitizationUtil.commonDisplay(vo.getNickname()));
        }


        //分页处理
        return setterPagedGrid(list, page);
    }

    /**
     * 商品搜索(分页)
     *
     * @param keywords 搜索内容
     * @param sort     排序规则
     * @param page     第几页
     * @param pageSize 每页显示多少条
     * @return
     */
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public PagedGridResult searchItems(String keywords, String sort, Integer page, Integer pageSize) {

        Map<String, Object> map = new HashMap<>();
        map.put("keywords", keywords);
        map.put("sort", sort);

        //mybatis-pagehelper 分页插件
        /** page: 第几页 * pageSize: 每页显示条数 */
        PageHelper.startPage(page, pageSize);

        //查询
        List<SearchItemsVO> list = itemsMapperCustom.searchItems(map);

        return setterPagedGrid(list, page);
    }

    /**
     * 根据首页三级分类id商品搜索(分页)
     *
     * @param catId    商品id
     * @param sort     排序规则
     * @param page     第几页
     * @param pageSize 每页显示多少条
     * @return
     */
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public PagedGridResult searchItems(Integer catId, String sort, Integer page, Integer pageSize) {
        Map<String, Object> map = new HashMap<>();
        map.put("catId", catId);
        map.put("sort", sort);

        //mybatis-pagehelper 分页插件
        /** page: 第几页 * pageSize: 每页显示条数 */
        PageHelper.startPage(page, pageSize);

        //查询
        List<SearchItemsVO> list = itemsMapperCustom.searchItemsByThirdCat(map);

        return setterPagedGrid(list, page);
    }

    //封装 分页方法
    private PagedGridResult setterPagedGrid(List<?> list, Integer page) {
        //分页处理
        PageInfo<?> pageList = new PageInfo<>(list);
        PagedGridResult grid = new PagedGridResult();
        grid.setPage(page);
        grid.setRows(list);
        grid.setTotal(pageList.getPages());
        grid.setRecords(pageList.getTotal());
        return grid;

    }

    /**
     * 根据规格ids 查询最新的购物车中商品数据 (用于刷新渲染购物车中的数据)
     *
     * @param specIds 规格ids
     * @return
     */
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<ShopCartVO> queryItemsBySpecIds(String specIds) {
        //拆分 specIds 将字符串 4,5,6 拆分为数组   按逗号进行分割
        String ids[] = specIds.split(",");
        //新建LIST集合
        List<String> specIdsList = new ArrayList<>();
        //将原数据 添加到list里
        Collections.addAll(specIdsList, ids);

        return itemsMapperCustom.queryItemsBySpecIds(specIdsList);


    }

    /**
     * 根据商品规格id获取规格对象的具体信息
     *
     * @param specId 商品规格id
     * @return 规格具体信息
     */
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public ItemsSpec queryItemSpecById(String specId) {
        return itemsSpecMapper.selectByPrimaryKey(specId);
    }

    /**
     * 根据商品id获得的商品图片主图url
     *
     * @param itemId 商品id
     * @return String类型 商品图片主图url
     */
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public String queryItemMainImgById(String itemId) {
        ItemsImg itemsImg = new ItemsImg();
        itemsImg.setItemId(itemId);
        itemsImg.setIsMain(YesOrNo.YES.type);//是否是主图

        ItemsImg res = itemsImgMapper.selectOne(itemsImg);
        //是否为空
        return res != null ? res.getUrl() : "";
    }

    /**
     * 减少库存
     *
     * @param specId    规格id
     * @param buyCounts 购买数量
     */
    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void decreaseItemSpecStock(String specId, int buyCounts) {
        //减库存操作
        int result = itemsMapperCustom.decreaseItemSpecStock(specId, buyCounts);
        if (result != 1) {
            throw new RuntimeException("订单创建失败,库存不足");

        }

    }

}
