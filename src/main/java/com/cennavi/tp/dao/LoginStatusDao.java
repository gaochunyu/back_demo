package com.cennavi.tp.dao;

import com.cennavi.tp.beans.LoginStatusBean;
import com.cennavi.tp.beans.UserinfoBean;
import com.cennavi.tp.common.base.dao.BaseDao;

import java.util.List;

public interface LoginStatusDao extends BaseDao<LoginStatusBean> {
    /**
     * 通过token检查是否已登录
     * @param token
     * @return
     */
    LoginStatusBean checkUserLoginByToken(String token);

    /**
     * 根据token修改最后访问时间（相当于延期）
     * @param token
     */
    void updateLastVisitTime(String token,String time);

    /**
     * 根据用户id删除登录记录
     * @param uid
     */
    void removeByUid(int uid);
}
