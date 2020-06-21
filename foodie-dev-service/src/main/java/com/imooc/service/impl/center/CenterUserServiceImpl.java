package com.imooc.service.impl.center;

import com.imooc.mapper.UsersMapper;
import com.imooc.pojo.Users;
import com.imooc.pojo.contet.bo.CenterUsersBO;
import com.imooc.service.center.CenterUserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;


//实现类
@Service
public class CenterUserServiceImpl implements CenterUserService {


    //
    @Autowired
    private UsersMapper usersMapper;

    //默认头像
    public static final String USER_FACE = "http://tupian.qqjay.com/tou2/2018/1106/60bdf5b88754650e51ccee32bb6ac8ae.jpg";


    /**
     * 根据用户id 查询用户信息
     *
     * @param UserId 用户Id
     * @return Users实体类 用户信息
     */
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public Users queryUserInfo(String UserId) {
        Users users = usersMapper.selectByPrimaryKey(UserId);
        //不把密码传到前端
        users.setPassword(null);
        return users;
    }

    /**
     * 修改用户信息
     *
     * @param CenterUsersBO
     * @param UserId
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public Users upDateUserInfo(String UserId, CenterUsersBO centerUsersBO) {
        Users updateUsers = new Users();
        //把前端传来的 值拷贝到  Users里
        BeanUtils.copyProperties(centerUsersBO, updateUsers);
        //设置主键
        updateUsers.setId(UserId);
        updateUsers.setUpdatedTime(new Date());

        //根据主键更新
        usersMapper.updateByPrimaryKeySelective(updateUsers);

        return queryUserInfo(UserId);

    }


}
