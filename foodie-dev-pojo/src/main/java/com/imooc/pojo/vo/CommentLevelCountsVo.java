package com.imooc.pojo.vo;


import lombok.Data;

/**
 * 用于展示商品评价数的VO
 */
@Data
public class CommentLevelCountsVo {
    /**
     * 所有的 好评数量
     */
    public Integer totalCounts;

    /**
     * 好评
     */
    public Integer goodCounts;

    /**
     * 中评
     */
    public Integer normalCounts;

    /**
     * 差评
     */
    public Integer badCounts;
}
