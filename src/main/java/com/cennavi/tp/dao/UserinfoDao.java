package com.cennavi.tp.dao;

import com.cennavi.tp.beans.UserinfoBean;
import com.cennavi.tp.common.base.dao.BaseDao;

import java.util.List;

public interface UserinfoDao extends BaseDao<UserinfoBean> {
    /**
     * 分页获取用户列表
     * @param start
     * @param pageSize
     * @return
     */
    List<UserinfoBean> getUsers(Integer start, Integer pageSize);

    /**
     * 获取用户数量
     * @param start
     * @param pageSize
     * @return
     */
    int getUsersCount(Integer start, Integer pageSize);

    /**
     * 根据id获取用户
     * @param id
     * @return
     */
    UserinfoBean getUserById(Integer id);

    /**
     * 登录
     * @param username
     * @return
     */
    List<UserinfoBean> login(String username);

    /**
     * 根据用户名查询用户信息
     * @param username
     * @return
     */
    public int getUsersCountByUserName(String username);

    /**
     * 根据id和用户名查询用户信息
     * @param id
     * @param username
     * @return
     */
    public int getUsersCountByIdAndUserName(Integer id,String username);
}
