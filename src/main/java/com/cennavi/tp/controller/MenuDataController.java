package com.cennavi.tp.controller;

import com.cennavi.tp.beans.MenuSubtitleBean;
import com.cennavi.tp.common.result.Result;
import com.cennavi.tp.common.result.ResultModel;
import com.cennavi.tp.service.MenuDataService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/menuData")
public class MenuDataController {

    @Resource
    private MenuDataService menuDataService;

    /**
     * 获取菜单列表
     * @return
     */
    @ResponseBody
    @RequestMapping("/getMenuList")
    public ResultModel getMenuList(){
        List<MenuSubtitleBean> menuList = menuDataService.getMenuList();
        Map<Integer, List<MenuSubtitleBean>> map = new HashMap<>();
        for(MenuSubtitleBean menu : menuList){
            if(map.get(menu.getParent()) == null){
                List<MenuSubtitleBean> mList = new ArrayList<>();
                mList.add(menu);
                map.put(menu.getParent(),mList);
            }else{
                List<MenuSubtitleBean> mList = map.get(menu.getParent());
                mList.add(menu);
                map.put(menu.getParent(),mList);
            }
        }
        List list = menuDataService.getMenuTree(menuDataService.getParentMenuListByIndex(0),map);
        return Result.success("成功获取数据",list);
    }

}
