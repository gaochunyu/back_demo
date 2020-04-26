package com.cennavi.tp.service;

import com.cennavi.tp.beans.MenuSubtitleBean;
import com.cennavi.tp.common.result.ResultModel;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

public interface MenuDataService {
    /**
     * 查询菜单列表
     * @Param uid 用户id
     * @Param model visit 普通访问，verify审核
     * @return
     */
    List<MenuSubtitleBean> getMenuList(Integer uid,String model);

    /**
     * 查询菜单列表tree结构
     * @param menuSubtitleBeans 一级菜单list
     * @param myMap  根据父级id分类的map
     * @return
     */
    List<MenuSubtitleBean> getMenuTree(List<MenuSubtitleBean> menuSubtitleBeans,Map<Integer,List<MenuSubtitleBean>> myMap);

    /**
     * 查询菜单列表  用于菜单管理使用
     * @param menuSubtitleBeans
     * @param myMap
     * @return
     */
    List getMenuAllAttrTree(List<MenuSubtitleBean> menuSubtitleBeans, Map<Integer, List<MenuSubtitleBean>> myMap);

    /**
     * 查询 父级id是0的一级菜单
     * @param rootValue
     * @return
     */
    List<MenuSubtitleBean> getParentMenuListByIndex(Integer rootValue);

    /**
     * 根据id查询 menu对象
     * @param id
     * @return
     */
    MenuSubtitleBean getMenuSubtitleBeanById(Integer id);

    /**
     * 根据id删除 menu对象
     * @param id
     * @return
     */
    ResultModel deleteMenuSubtitleBeanById(Integer id, HttpServletRequest request);

    /**
     * 新增一条菜单信息
     * @return
     */
    void addMenuSubtitleBean(MenuSubtitleBean menuSubtitleBean);

    /**
     * 修改菜单信息
     * @param menuSubtitleBean
     */
    Integer updateMenuSubtitleBean(MenuSubtitleBean menuSubtitleBean);

    // 更新菜单表的状态信息
    int updateMenuStatus(int id, int status);
}

