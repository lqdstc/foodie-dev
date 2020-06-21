package com.imooc.service.center;

import com.imooc.pojo.Users;
import com.imooc.pojo.contet.bo.CenterUsersBO;

/**
 * 用户中心 相关Service接口
 */
public interface CenterUserService {


    /**
     * 根据用户id 查询用户信息
     *
     * @param UserId 用户Id
     * @return Users实体类 用户信息
     */
    public Users queryUserInfo(String UserId);

    /**
     * 修改用户信息
     *  @param UserId
     * @param centerUsersBO
     * @return
     */
    public Users upDateUserInfo(String UserId, CenterUsersBO centerUsersBO);
}
