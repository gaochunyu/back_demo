package com.cennavi.tp.service;

import com.cennavi.tp.beans.MenuSubtitleBean;

import java.util.List;
import java.util.Map;

public interface MenuDataService {
    List<MenuSubtitleBean> getMenuList();
    List<MenuSubtitleBean> getMenuTree(List<MenuSubtitleBean> menuSubtitleBeans,Map<Integer,List<MenuSubtitleBean>> myMap);
    List<MenuSubtitleBean> getParentMenuListByIndex(Integer rootValue);
}

