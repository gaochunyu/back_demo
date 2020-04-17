package com.cennavi.tp.service.impl;

import com.cennavi.tp.beans.*;
import com.cennavi.tp.dao.BaseDataDao;
import com.cennavi.tp.dao.UserDao;
import com.cennavi.tp.service.BaseDataService;
import com.cennavi.tp.service.UserService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;


    @Override
    public List<User> getUserList(Integer page, Integer pageSize) {
        return userDao.getUserList(page,pageSize);
    }
}
