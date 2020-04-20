package com.cennavi.tp.service.impl;

import com.cennavi.tp.beans.*;
import com.cennavi.tp.dao.UserinfoDao;
import com.cennavi.tp.service.UserinfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class UserinfoServiceImpl implements UserinfoService {

    @Autowired
    private UserinfoDao userinfoDao;


    @Override
    public List<UserinfoBean> getUserList(Integer page, Integer pageSize) {
        int start = (page-1)*pageSize;
        return userinfoDao.getUserList(start,pageSize);
    }

    @Override
    public UserinfoBean getUserById(Integer id) {
        //userDao.getUserById(id);//自己手写实现
        return userinfoDao.findById(id);//调用已经封装好的通用方法
    }
}
