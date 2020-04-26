package com.cennavi.tp.dao;


import com.cennavi.tp.beans.MenuSubtitleBean;
import com.cennavi.tp.common.base.dao.BaseDao;
import org.omg.PortableInterceptor.INACTIVE;

import java.util.List;

public interface MenuDataDao extends BaseDao<MenuSubtitleBean> {

    /**
     * 获取所有菜单
     * @Param uid 用户id
     * @Param model visit 普通访问，verify审核
     * @return
     */
    List<MenuSubtitleBean> getMenuSubtitles(Integer uid,String model);

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

    /**
     * 更新菜单信息，默认的update好像有问题
     * @param menu
     */
    Integer updateMenu(MenuSubtitleBean menu);
}
