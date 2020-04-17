package com.cennavi.tp.service;

import com.cennavi.tp.beans.User;

import java.util.List;

public interface UserService {
    /**
     * 分页获取用户列表
     * @param page
     * @param pageSize
     * @return
     */
    List<User> getUserList(Integer page, Integer pageSize);
}
