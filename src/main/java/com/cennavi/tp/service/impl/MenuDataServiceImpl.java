package com.cennavi.tp.service.impl;

import com.cennavi.tp.beans.MenuSubtitleBean;
import com.cennavi.tp.dao.MenuDataDao;
import com.cennavi.tp.service.MenuDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Service
public class MenuDataServiceImpl implements MenuDataService {

    @Autowired
    private MenuDataDao menuDataDao;

    @Override
    public List<MenuSubtitleBean> getMenuList() {
        return menuDataDao.getMenuSubtitles();
    }

    @Override
    public List getMenuTree(List<MenuSubtitleBean> menuSubtitleBeans, Map<Integer, List<MenuSubtitleBean>> myMap) {
        List<Map<String,Object>> list = new LinkedList<>();
        menuSubtitleBeans.forEach(menu -> {
            if(menu != null){
                List<MenuSubtitleBean> menuList = myMap.get(menu.getId());
                Map<String,Object> map = new HashMap<>();
                map.put("id",menu.getId());
                map.put("label",menu.getName());
                if(menuList != null && menuList.size()!=0){
                    map.put("children",getMenuTree(menuList,myMap));
                }
                list.add(map);
            }
        });
        return list;
    }


    @Override
    public List<MenuSubtitleBean> getParentMenuListByIndex(Integer rootValue) {
        return menuDataDao.getParentMenuList(rootValue);
    }

    @Override
    public MenuSubtitleBean getMenuSubtitleBeanById(Integer id) {
        return menuDataDao.getMenuSubtitleBeanById(id);
    }

    @Override
    public Integer deleteMenuSubtitleBeanById(Integer id) {
        return menuDataDao.deleteMenuSubtitleBeanById(id);
    }

    @Override
    public void addMenuSubtitleBean(MenuSubtitleBean menuSubtitleBean) {
        menuDataDao.save(menuSubtitleBean);
    }
}
