package com.cennavi.tp.service;

import com.cennavi.tp.beans.UserinfoBean;
import com.cennavi.tp.common.result.ResultModel;

import javax.servlet.http.HttpServletRequest;
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

    /**
     * 登录
     * @param username
     * @return
     */
    ResultModel login(String username, String password, HttpServletRequest request);
}
