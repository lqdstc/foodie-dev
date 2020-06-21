package com.imooc.service;

import com.imooc.pojo.UserAddress;
import com.imooc.pojo.bo.AddressBo;

import java.util.List;

/**
 * 收货地址Service
 */
public interface AddressService {

    /**
     * 根据用户id 查询用户的收货地址
     *
     * @param userId 用户id
     * @return
     */
    public List<UserAddress> queryAll(String userId);


    /**
     * 用户新增货修改地址
     *
     * @param addressBo 用户新增收货修改地址Bo
     */
    public void addNewUserAddress(AddressBo addressBo);


    /**
     * 修改用户的地址
     *
     * @param addressBo 地址bo
     */
    public void updateUserAddress(AddressBo addressBo);


    /**
     * 根据用户的id 和地址id 删除对应的用户地址信息
     *
     * @param userId    用户id
     * @param addressId 地址id
     */
    public void deleteUserAddress(String userId, String addressId);

    /**
     * 根据用户的id 和地址id 设定为默认地址
     *
     * @param userId    用户id
     * @param addressId 地址id
     */
    public void setDefaultAddress(String userId, String addressId);


    /**
     *  根据用户id  和地址id  查询用户地址对象信息
     * @param userId  用户id
     * @param addressId  地址id
     * @return  地址对象信息
     */
     public UserAddress queryUserAddres(String userId, String addressId);

}
