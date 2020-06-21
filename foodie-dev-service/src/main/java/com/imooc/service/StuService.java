package com.imooc.service;

import com.imooc.pojo.Stu;


public interface StuService {

    //    查询 stu表
    public Stu getStuInfo(int id);

    //保存
    public void saveStu();

    //修改
    public void updateStu(int id);

    public void deleteStu(int id);

}
