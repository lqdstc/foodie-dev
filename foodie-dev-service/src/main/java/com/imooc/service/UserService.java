package com.imooc.service;

import com.imooc.pojo.Users;
import com.imooc.pojo.bo.UserBO;

/**
 * 用户登录 注册退出相关Service接口
 */
public interface UserService {

    /**
     * 判断用户名是否存在
     *
     * @return 布尔
     */
    public boolean queryUsernameIsExist(String username);

    /**
     * 注册用户
     *
     * @return 用户信息
     */
    public Users createUser(UserBO userBo);

    /**
     * 用户登录 检索用户名和密码是否匹配 用于登录
     *
     * @param username 用户名
     * @param password 密码
     * @return
     */
    public Users queryUserForLogin(String username, String password);
}
