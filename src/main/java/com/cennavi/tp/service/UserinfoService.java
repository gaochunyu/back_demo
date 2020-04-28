package com.cennavi.tp.service;

import com.cennavi.tp.beans.UserinfoBean;
import com.cennavi.tp.common.result.ResultModel;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface UserinfoService {

    /**
     * 登录
     * @param username
     * @return
     */
    ResultModel login(String username, String password, String code, HttpServletRequest request);

    /**
     * 分页获取用户列表
     * @param page
     * @param pageSize
     * @return
     */
    List<UserinfoBean> getUsers(Integer page, Integer pageSize);

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
     * 修改用户信息
     * @param userinfoBean
     */
    boolean updateUser(UserinfoBean  userinfoBean);

    /**
     * 根据id删除用户
     * @param id
     * @return
     */
    boolean removeUser(Integer id);

    /**
     * 添加用户信息
     * @param userinfoBean
     * @return
     */
    boolean addUser(UserinfoBean userinfoBean);

    /**
     * 根据用户名查询用户信息
     * @param username
     * @return
     */
    int getUsersCountByUserName(String username);

    /**
     * 根据id和用户名查询用户信息
     * @param id
     * @param username
     * @return
     */
    int getUsersCountByIdAndUserName(Integer id,String username);
}
