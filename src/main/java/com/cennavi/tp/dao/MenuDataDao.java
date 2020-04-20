package com.cennavi.tp.dao;


import com.cennavi.tp.beans.MenuSubtitleBean;

import java.util.List;

public interface MenuDataDao {

    List<MenuSubtitleBean> getMenuSubtitles();
    List<MenuSubtitleBean> getParentMenuList(Integer rootValue);
}
