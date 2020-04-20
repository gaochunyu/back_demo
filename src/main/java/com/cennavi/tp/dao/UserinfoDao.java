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
    List<UserinfoBean> getUserList(Integer start, Integer pageSize);

    /**
     * 根据id获取用户
     * @param id
     * @return
     */
    UserinfoBean getUserById(Integer id);
}
