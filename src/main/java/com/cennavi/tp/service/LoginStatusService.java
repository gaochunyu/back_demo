package com.cennavi.tp.service;

import com.cennavi.tp.beans.LoginStatusBean;
import com.cennavi.tp.beans.UserinfoBean;

/**
 * <p>
 * 用户登录状态 服务类
 * </p>
 *
 * @author MaLiWei
 * @since 2020-04-20
 */
public interface LoginStatusService{

    /**
     * 通过token检查是否已登录
     * @param token
     * @return
     */
    UserinfoBean checkUserLoginByToken(String token);

    /**
     * 新增登录状态
     * @param loginStatusBean
     */
    void insert(LoginStatusBean loginStatusBean);
}
