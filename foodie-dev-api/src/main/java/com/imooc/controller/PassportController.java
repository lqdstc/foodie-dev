package com.imooc.controller;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.DynamicParameter;
import com.github.xiaoymin.knife4j.annotations.DynamicResponseParameters;
import com.github.xiaoymin.knife4j.spring.annotations.EnableKnife4j;
import com.imooc.pojo.Users;
import com.imooc.pojo.bo.UserBO;
import com.imooc.service.UserService;
import com.imooc.utils.CookieUtils;
import com.imooc.utils.JSONResult;
import com.imooc.utils.JsonUtils;
import com.imooc.utils.MD5Utils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Api(value = "注册登录", tags = {"用于注册登录的相关接口"})
@RestController
@RequestMapping("passport")
@EnableKnife4j
public class PassportController {

    @Autowired
    private UserService userService;


    /**
     * 判断用户名是否存在
     *
     * @param username 用户名
     * @return status
     */
    @ApiOperation(value = "用户名是否存在", notes = "用户名是否存在", httpMethod = "GET")
    @GetMapping("/usernameIsExist")
    //@RequestParam  代表是请求类型的参数
    public JSONResult usernameIsExist(@RequestParam String username) {
        //判断字符串是否为空
        if (StringUtils.isBlank(username)) {
            return JSONResult.errorMsg("用户名不能为空");
        }
//        2 查找注册的用户名是否存在
        boolean isExist = userService.queryUsernameIsExist(username);
        if (isExist) {
            return JSONResult.errorMsg("用户名已经存在");
        }

        //请求成功,用户名没有重复
        return JSONResult.ok();
    }


    /**
     * 用户注册
     *
     * @param userBO   前端传来的 bo数据
     * @param request
     * @param response
     * @return
     */
    @ApiOperation(value = "用户注册", notes = "用户注册", httpMethod = "POST")
    @PostMapping("/regist")
    public JSONResult regist(@RequestBody UserBO userBO,
                             HttpServletRequest request,
                             HttpServletResponse response) {

        //获取参数
        String username = userBO.getUsername();
        String password = userBO.getPassword();
        String confirmPassword = userBO.getConfirmPassword();

        //0.判断用户名和密码 必须不为空
        if (StringUtils.isAllBlank(username) ||
                StringUtils.isAllBlank(password) ||
                StringUtils.isAllBlank(confirmPassword)) {
            return JSONResult.errorMsg("用户名或密码不能为空");
        }
        //1.查询用户名是否存在
        boolean isExist = userService.queryUsernameIsExist(username);
        if (isExist) {
            return JSONResult.errorMsg("用户名已经存在");
        }

        //2.密码长度不能少于6位
        if (password.length() < 6) {
            return JSONResult.errorMsg("密码长度不能少于6");
        }
        //3.判断两次密码是否一致
        if (!password.equals(confirmPassword)) {
            return JSONResult.errorMsg("两次密码不一致");
        }
        //4.实现注册
        Users userResult = userService.createUser(userBO);

        //返回的cookie 设置为空
        userResult = setNullProperty(userResult);

//        使用cookie  使用JsonUtils  把对象转换为json
        CookieUtils.setCookie(request, response, "user",
                JsonUtils.objectToJson(userResult), true);


        // TODO 生成用户token 存入redis会话
        // TODO 同步购物车数据

        return JSONResult.ok();
    }


    /**
     * 用户登录
     *
     * @param userBO   用户对象
     * @param request
     * @param response
     * @return
     * @throws Exception
     */

    @ApiOperation(value = "用户登录", notes = "用户登录", httpMethod = "POST")
    @PostMapping("/login")
    public JSONResult login(@RequestBody UserBO userBO,
                            HttpServletRequest request,
                            HttpServletResponse response) throws Exception {

        //获取参数
        String username = userBO.getUsername();
        String password = userBO.getPassword();


        //0.判断用户名和密码 必须不为空
        if (StringUtils.isAllBlank(username) ||
                StringUtils.isAllBlank(password)) {
            return JSONResult.errorMsg("用户名或密码不能为空");
        }

        //.实现登录
        Users userResult = userService.queryUserForLogin(username,
                MD5Utils.getMD5Str(password));

        //如果用户名 和密码 是空
        if (userResult == null) {
            return JSONResult.errorMsg("用户名或密码不正确");
        }

        //返回的cookie 设置为空
        userResult = setNullProperty(userResult);

//        使用cookie
        CookieUtils.setCookie(request, response, "user",
                JsonUtils.objectToJson(userResult), true);

        return JSONResult.ok(userResult);
    }

    /**
     * 返回的cookie 设置为空
     *
     * @param userResult 用户信息状态
     * @return
     */
    private Users setNullProperty(Users userResult) {
        //返回的cookie 设置为空
        userResult.setPassword(null);
        userResult.setMobile(null);
        userResult.setCreatedTime(null);
        userResult.setUpdatedTime(null);
        userResult.setBirthday(null);

        return userResult;
    }

    @ApiOperation(value = "用户退出登录", notes = "用户退出登录", httpMethod = "POST")
    @PostMapping("/logout")
    //@RequestParam  代表是请求类型的参数
    public JSONResult logout(@RequestParam String userId,
                             HttpServletRequest request,
                             HttpServletResponse response) {

        //清除user的cookie信息
        CookieUtils.deleteCookie(request, response, "user");

        //TODO 用户退出登录，需要清空购物车
        //TODO  分布式会话中需要清除用户数据

        return JSONResult.ok();
    }
}
