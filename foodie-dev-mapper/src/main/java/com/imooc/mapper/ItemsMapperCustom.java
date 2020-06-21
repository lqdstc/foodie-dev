package com.imooc.mapper;

import com.imooc.pojo.vo.ItemCommentVo;
import com.imooc.pojo.vo.SearchItemsVO;
import com.imooc.pojo.vo.ShopCartVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;


/**
 * 自定义 查询评价SQL 接口
 */
public interface ItemsMapperCustom {


    /**
     * 评价sql
     *
     * @param map
     * @return
     */
    public List<ItemCommentVo> queryItemComments(@Param("paramsMap") Map<String, Object> map);

    /**
     * 搜索商品sql
     *
     * @param map
     * @return
     */
    public List<SearchItemsVO> searchItems(@Param("paramsMap") Map<String, Object> map);

    /**
     * 首页的三级分类搜索商品sql
     *
     * @param map
     * @return
     */
    public List<SearchItemsVO> searchItemsByThirdCat(@Param("paramsMap") Map<String, Object> map);


    /**
     * 刷新购物车数据sql 接口
     *
     * @param specIdsList specId集合
     * @return
     */
    public List<ShopCartVO> queryItemsBySpecIds(@Param("paramsList") List specIdsList);


    /**
     * 购车减库存 乐观锁 sql
     *
     * @param specId        规格id
     * @param pendingCounts 购买数量
     * @return 减库存的数量 int
     */
    public int decreaseItemSpecStock(@Param("specId") String specId,
                                     @Param("pendingCounts") int pendingCounts);


}