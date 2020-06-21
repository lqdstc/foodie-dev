package com.imooc.pojo.vo;

import lombok.Data;

import java.util.Date;

/**
 * 用于展示商品评价的Vo
 */
@Data
public class ItemCommentVo {
    private Integer commentLevel;
    private String content;
    private String specName;
    private Date  createdTime;
    private String userFace;
    private String nickname;



}
