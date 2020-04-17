package com.cennavi.tp.dao;

import com.cennavi.tp.beans.User;
import com.cennavi.tp.common.base.dao.BaseDao;

import java.util.List;

public interface UserDao extends BaseDao<User> {
    /**
     * 分页获取用户列表
     * @param start
     * @param pageSize
     * @return
     */
    List<User> getUserList(Integer start,Integer pageSize);

    /**
     * 根据id获取用户
     * @param id
     * @return
     */
    User getUserById(Integer id);
}
