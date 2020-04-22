package com.cennavi.tp.dao;


import com.cennavi.tp.beans.MenuSubtitleBean;
import com.cennavi.tp.common.base.dao.BaseDao;
import org.omg.PortableInterceptor.INACTIVE;

import java.util.List;

public interface MenuDataDao extends BaseDao<MenuSubtitleBean> {

    /**
     * 获取所有菜单
     * @return
     */
    List<MenuSubtitleBean> getMenuSubtitles();

    /**
     * 获取一级菜单
     * @param rootValue 上级值
     * @return
     */
    List<MenuSubtitleBean> getParentMenuList(Integer rootValue);

    /**
     * 根据id获取一个菜单信息
     * @param id 菜单id
     * @return
     */
    MenuSubtitleBean getMenuSubtitleBeanById(Integer id);

    /**
     * 根据id删除一个菜单信息
     * @param id 菜单id
     * @return
     */
    Integer deleteMenuSubtitleBeanById(Integer id);
}
