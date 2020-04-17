package com.cennavi.tp.dao;

import com.cennavi.tp.beans.User;

import java.util.List;

public interface UserDao {
    /**
     * 分页获取用户列表
     * @param page
     * @param pageSize
     * @return
     */
    List<User> getUserList(Integer page,Integer pageSize);
}
