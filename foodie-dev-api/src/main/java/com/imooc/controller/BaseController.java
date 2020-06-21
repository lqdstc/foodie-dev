package com.imooc.controller;

import org.springframework.stereotype.Controller;

import java.io.File;

@Controller
public class BaseController {
    //评论默认显示条数
    public static final Integer COMMENT_PAGE_SIZE = 10;

    //搜索默认
    public static final Integer PAGE_SIZE = 20;


    //购物车 Cookie  跟前端一个
    public static final String FOODIE_SHOPCART = "shopcart";


    //用户上传头像的位置
    public static final String IMAGE_USER_FACE_LOCATION = "Z:" + File.separator + "ojb" +
            File.separator + "foodie-dev" +
            File.separator + "workspaces" +
            File.separator + "images";


}
