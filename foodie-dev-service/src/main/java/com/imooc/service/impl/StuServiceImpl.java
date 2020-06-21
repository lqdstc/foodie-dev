package com.imooc.service.impl;

import com.imooc.mapper.StuMapper;
import com.imooc.pojo.Stu;
import com.imooc.service.StuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


//实现类
@Service
public class StuServiceImpl implements StuService {

    //注入接口
    @Autowired
    private StuMapper stuMapper;


    Stu stu = new Stu();


    //事务
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public Stu getStuInfo(int id) {
        //传入id
        return stuMapper.selectByPrimaryKey(id);
    }


    //保存操作
    //事务
    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void saveStu() {
        Stu stu = new Stu();
        stu.setName("jackMa");
        stu.setAge(55);
        stuMapper.insert(stu);

    }

    //更新操作
    //事务
    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void updateStu(int id) {

        stu.setId(id);
        stu.setName("dd");
        stu.setAge(18);
        //根据主键更新
        stuMapper.updateByPrimaryKey(stu);
    }


    //事务
    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void deleteStu(int id) {
        stuMapper.deleteByPrimaryKey(id);

    }
}
