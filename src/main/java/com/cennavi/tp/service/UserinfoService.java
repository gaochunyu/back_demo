package com.cennavi.tp.service;

import com.cennavi.tp.beans.UserinfoBean;

import java.util.List;

public interface UserinfoService {
    /**
     * 分页获取用户列表
     * @param page
     * @param pageSize
     * @return
     */
    List<UserinfoBean> getUserList(Integer page, Integer pageSize);

    /**
     * 根据id获取用户
     * @param id
     * @return
     */
    UserinfoBean getUserById(Integer id);
}
