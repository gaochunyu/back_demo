package com.cennavi.tp.dao;


import com.cennavi.tp.beans.MenuSubtitleBean;
import com.cennavi.tp.common.base.dao.BaseDao;
import org.omg.PortableInterceptor.INACTIVE;

import java.util.List;

public interface MenuDataDao extends BaseDao<MenuSubtitleBean> {

    List<MenuSubtitleBean> getMenuSubtitles();
    List<MenuSubtitleBean> getParentMenuList(Integer rootValue);
    MenuSubtitleBean getMenuSubtitleBeanById(Integer id);
    Integer deleteMenuSubtitleBeanById(Integer id);
}
