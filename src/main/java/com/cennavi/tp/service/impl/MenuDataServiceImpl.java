package com.cennavi.tp.service.impl;

import com.cennavi.tp.beans.MenuSubtitleBean;
import com.cennavi.tp.beans.UserinfoBean;
import com.cennavi.tp.common.result.Result;
import com.cennavi.tp.common.result.ResultModel;
import com.cennavi.tp.dao.ContentDao;
import com.cennavi.tp.dao.MenuDataDao;
import com.cennavi.tp.service.ContentService;
import com.cennavi.tp.service.MenuDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Service
public class MenuDataServiceImpl implements MenuDataService {

    @Autowired
    private MenuDataDao menuDataDao;
    @Autowired
    private ContentService contentService;

    @Override
    public List<MenuSubtitleBean> getMenuList(Integer uid,String model) {
        return menuDataDao.getMenuSubtitles(uid,model);
    }

    @Override
    public List getMenuTree(List<MenuSubtitleBean> menuSubtitleBeans, Map<Integer, List<MenuSubtitleBean>> myMap) {
        List<Map<String,Object>> list = new LinkedList<>();
        menuSubtitleBeans.forEach(menu -> {
            if(menu != null){
                List<MenuSubtitleBean> menuList = myMap.get(menu.getId());
                Map<String,Object> map = new HashMap<>();
                map.put("id",menu.getId());
                map.put("name",menu.getName());
                map.put("status",menu.getStatus());
                map.put("level",menu.getLevel());
                if(menuList != null && menuList.size()!=0){
                    map.put("children",getMenuTree(menuList,myMap));
                }
                list.add(map);
            }
        });
        return list;
    }

    @Override
    public List getMenuAllAttrTree(List<MenuSubtitleBean> menuSubtitleBeans, Map<Integer, List<MenuSubtitleBean>> myMap) {
        List<Map<String,Object>> list = new LinkedList<>();
        menuSubtitleBeans.forEach(menu -> {
            if(menu != null){
                List<MenuSubtitleBean> menuList = myMap.get(menu.getId());
                Map<String,Object> map = new HashMap<>();
                map.put("id",menu.getId());
                map.put("name",menu.getName());
                map.put("parent",menu.getParent());
                map.put("sort",menu.getSort());
                map.put("uid",menu.getUid());
                map.put("create_time",menu.getCreateTime());
                map.put("status",menu.getStatus());
                map.put("level",menu.getLevel());
                map.put("username",menu.getUsername());
                if(menuList != null && menuList.size()!=0){
                    map.put("children",getMenuAllAttrTree(menuList,myMap));
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
    public ResultModel deleteMenuSubtitleBeanById(Integer id, HttpServletRequest request) {
        try{
            //获取当前登录账号
            UserinfoBean user = (UserinfoBean) request.getSession().getAttribute("user");
            if(user==null) Result.buildUnLogin();
            //获取菜单
            MenuSubtitleBean menu = menuDataDao.getMenuSubtitleBeanById(id);
            //菜单属于当前账号或者超级管理员才可以删除
            if((menu.getUid()==user.getId() && menu.getStatus()!=1 && menu.getStatus()!=2) || user.getRole()==0){
                //删除内容
                contentService.deleteItemById(id);
                //删除菜单
                Integer index = menuDataDao.deleteMenuSubtitleBeanById(id);
                if(index == 0){
                    return Result.fail("输入的id无对应数据",index);
                }else{
                    return Result.success("删除成功",index);
                }
            }else{
                return Result.fail("无删除权限");
            }
        }catch (Exception e){
            e.getStackTrace();
            return Result.build500("出现异常");
        }
    }

    @Override
    public void addMenuSubtitleBean(MenuSubtitleBean menuSubtitleBean) {
        menuDataDao.save(menuSubtitleBean);
    }

    @Override
    public Integer updateMenuSubtitleBean(MenuSubtitleBean menuSubtitleBean) {
        return menuDataDao.updateMenu(menuSubtitleBean);
    }

    // 更新菜单表的状态信息
    @Override
    public int updateMenuStatus(int id, int status){
        return menuDataDao.updateMenuStatus(id, status);
    }

    @Override
    public List<MenuSubtitleBean> getCategoryList(){
        String[] titles = {"录入中","待审核","已通过","被拒绝",};
        List<MenuSubtitleBean> list = new ArrayList<>();
        for(int i=0;i<titles.length;i++){
            String info = titles[i];
            MenuSubtitleBean menu = new MenuSubtitleBean();
            menu.setId(i);
            menu.setUid(0);
            menu.setName(info);
            menu.setParent(0);
            menu.setSort(0);
            menu.setStatus(0);
            list.add(menu);
        }
        return list;
    }

    @Override
    public List getCategoryTree(List<MenuSubtitleBean> menuSubtitleBeans, Map<Integer, List<MenuSubtitleBean>> myMap) {
        List<Map<String,Object>> list = new LinkedList<>();
        menuSubtitleBeans.forEach(menu -> {
            if(menu != null){
                List<MenuSubtitleBean> menuList = myMap.get(menu.getId());
                Map<String,Object> map = new HashMap<>();
                map.put("id",menu.getId());
                map.put("name",menu.getName());
                map.put("status",menu.getStatus());
                if(menuList != null && menuList.size()!=0){
                    map.put("children",getCategoryTree(menuList,myMap));
                }
                list.add(map);
            }
        });
        return list;
    }
}
