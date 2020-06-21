package com.imooc.service.impl;

import com.imooc.enums.Sex;
import com.imooc.mapper.UsersMapper;
import com.imooc.pojo.Users;
import com.imooc.pojo.bo.UserBO;
import com.imooc.service.UserService;
import com.imooc.utils.DateUtil;
import com.imooc.utils.MD5Utils;
import org.n3r.idworker.Sid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;


//实现类
@Service
public class UserServiceImpl implements UserService {


    //
    @Autowired
    private UsersMapper usersMapper;

    //默认头像
    public static final String USER_FACE = "http://tupian.qqjay.com/tou2/2018/1106/60bdf5b88754650e51ccee32bb6ac8ae.jpg";

    //id生成器
    @Autowired
    private Sid sid;


    /**
     * 判断用户名是否存在
     *
     * @param username 用户名
     * @return   布尔
     */
    //事务
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public boolean queryUsernameIsExist(String username) {
        //通过条件进行查询
        Example userExample = new Example(Users.class);
        Example.Criteria userCriteria = userExample.createCriteria();
        //andEqualTo添加xxx字段等于value条件
        userCriteria.andEqualTo("username", username);

        //通过Example 查询一条记录
        Users result = usersMapper.selectOneByExample(userExample);

        //如果等于null 返回false  否则返回true
        return result == null ? false : true;
    }

    /**
     * 创建用户
     *
     * @param userBo 用户表
     * @return 用户信息
     */
    //事务
    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public Users createUser(UserBO userBo) {

        //获得id 使用id生成器
        String userId = sid.nextShort();
        //实例化 用户
        Users user = new Users();
        //用户Id
        user.setId(userId);
        //用户名
        user.setUsername(userBo.getUsername());
        //密码
        try {
            user.setPassword(MD5Utils.getMD5Str(userBo.getPassword()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        //默认用户昵称跟用户名
        user.setNickname(userBo.getUsername());
        //默认头像
        user.setFace(USER_FACE);
        //默认生日
        user.setBirthday(DateUtil.stringToDate("1900-01-01"));
        //默认性别为保密
        user.setSex(Sex.SECRET.type);
        //注册时间
        user.setCreatedTime(new Date());
        user.setUpdatedTime(new Date());
        //保存
        usersMapper.insert(user);

        return user;
    }


    /**
     * 登录
     *
     * @param username 用户名
     * @param password 密码
     * @return 用户信息
     */
    @Transactional(propagation = Propagation.SUPPORTS)  //事务
    @Override
    public Users queryUserForLogin(String username, String password) {



        //通过条件进行查询
        Example userExample = new Example(Users.class);
        Example.Criteria userCriteria = userExample.createCriteria();
        //andEqualTo添加xxx字段等于value条件
        userCriteria.andEqualTo("username", username);
        userCriteria.andEqualTo("password", password);

        //通过Example 查询一条记录
        Users result = usersMapper.selectOneByExample(userExample);

        //返回结果
        return result;
    }
}
