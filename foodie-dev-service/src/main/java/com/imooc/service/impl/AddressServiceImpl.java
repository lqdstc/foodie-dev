package com.imooc.service.impl;


import com.imooc.enums.YesOrNo;
import com.imooc.mapper.UserAddressMapper;
import com.imooc.pojo.UserAddress;
import com.imooc.pojo.bo.AddressBo;
import com.imooc.service.AddressService;
import org.n3r.idworker.Sid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class AddressServiceImpl implements AddressService {

    @Autowired
    private UserAddressMapper userAddressMapper;

    //随机生成id
    @Autowired
    private Sid sid;

    /**
     * 根据用户id 查询用户的收货地址
     *
     * @param userId
     * @return
     */
    //查询事务
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<UserAddress> queryAll(String userId) {
        UserAddress ua = new UserAddress();
        ua.setUserId(userId);
        //   根据用户id 查询用户的收货地址
        return userAddressMapper.select(ua);
    }

    /**
     * 用户新增收货修改地址
     *
     * @param addressBo 用户新增货修改地址Bo
     */
    //新增事务
    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void addNewUserAddress(AddressBo addressBo) {
        //1. 判断当前用户是否存在地址，如果没有，则新增为默认地址
        Integer isDefault = 0; //默认地址
        List<UserAddress> addressList = this.queryAll(addressBo.getUserId());
        if (addressList == null || addressList.isEmpty() || addressList.size() == 0) {
            isDefault = 1;
        }

        //生成一个随机id
        String addressId = sid.nextShort();

        //2.保存到数据库
        //实例化实体类
        UserAddress newAddress = new UserAddress();
        //将原来对象拷贝到 现在的对象里
        BeanUtils.copyProperties(addressBo, newAddress);

        //设置属性
        newAddress.setId(addressId);
        newAddress.setIsDefault(isDefault);
        newAddress.setCreatedTime(new Date());
        newAddress.setUpdatedTime(new Date());

        userAddressMapper.insert(newAddress);


    }

    /**
     * 修改用户的地址
     *
     * @param addressBo
     */
    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void updateUserAddress(AddressBo addressBo) {
        //获得用户地址id
        String addressId = addressBo.getAddressId();

        //实例化实体类
        UserAddress pendingAddress = new UserAddress();
        //拷贝进实体类
        BeanUtils.copyProperties(addressBo, pendingAddress);
        //设置地址id  前面不一样拷贝不进去
        pendingAddress.setId(addressId);
        pendingAddress.setUpdatedTime(new Date());

        //根据主键进行更新
        userAddressMapper.updateByPrimaryKeySelective(pendingAddress);

    }

    /**
     * 根据用户的id 和地址id 删除对应的用户地址信息
     *
     * @param userId
     * @param addressId
     */
    @Override
    public void deleteUserAddress(String userId, String addressId) {
        //实例化实体类
        UserAddress address = new UserAddress();
        address.setId(addressId);
        address.setUserId(userId);

        userAddressMapper.delete(address);

    }

    /**
     * 根据用户的id 和地址id 设定为默认地址
     *
     * @param userId
     * @param addressId
     */
    @Override
    public void setDefaultAddress(String userId, String addressId) {

        //1.查找默认地址,设置为不默认
        //实例化实体类
        UserAddress queryAddress = new UserAddress();
        queryAddress.setUserId(userId);
        queryAddress.setIsDefault(YesOrNo.YES.type);
        //查找出默认的收货地址
        List<UserAddress> list = userAddressMapper.select(queryAddress);
        for (UserAddress userAddress : list) {
            //设置为不是默认
            userAddress.setIsDefault(YesOrNo.NO.type);
            userAddressMapper.updateByPrimaryKeySelective(userAddress);
        }

        //2.根据地址id 修改为默认地址
        UserAddress defaultAddress = new UserAddress();
        defaultAddress.setId(addressId);
        defaultAddress.setUserId(userId);
        defaultAddress.setIsDefault(YesOrNo.YES.type);
        userAddressMapper.updateByPrimaryKeySelective(defaultAddress);


    }

    /**
     * 根据用户id  和地址id  查询用户地址对象信息
     *
     * @param userId    用户id
     * @param addressId 地址id
     * @return 地址对象信息
     */
    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public UserAddress queryUserAddres(String userId, String addressId) {
        UserAddress address = new UserAddress();
        address.setId(addressId);
        address.setUserId(userId);

        return userAddressMapper.selectOne(address);
    }


}
