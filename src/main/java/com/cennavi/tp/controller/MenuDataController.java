package com.cennavi.tp.controller;

import com.cennavi.tp.beans.MenuSubtitleBean;
import com.cennavi.tp.beans.UserinfoBean;
import com.cennavi.tp.common.result.Result;
import com.cennavi.tp.common.result.ResultModel;
import com.cennavi.tp.service.MenuDataService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
        try{
            List list = menuDataService.getMenuTree(menuDataService.getParentMenuListByIndex(0),map);
            if(list == null && list.size() == 0){
                return Result.fail("暂无数据",new ArrayList<>()); //1000
            }else{
                return Result.success("成功获取数据",list);  //200
            }
        }catch (Exception e){
            e.getStackTrace();
            return Result.build500("出现异常");
        }
    }

    /**
     * 根据id 删除 菜单
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping("/deleteMenuSubtitle")
    public ResultModel deleteMenuSubtitle(@RequestParam(value = "id") Integer id){
        try{
            Integer index = menuDataService.deleteMenuSubtitleBeanById(id);
            if(index == 0){
                return Result.fail("输入的id无对应数据",index);
            }else{
                return Result.success("删除成功",index);
            }
        }catch (Exception e){
            e.getStackTrace();
            return Result.build500("出现异常");
        }
    }

    /**
     * 根据id查询菜单
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping("/getMenuSubtitleById")
    public ResultModel getMenuSubtitleById(@RequestParam(value = "id") Integer id){
        try{
            MenuSubtitleBean menuSubtitleBean = menuDataService.getMenuSubtitleBeanById(id);
            if(menuSubtitleBean == null){
                return Result.fail("输入的id无对应数据",new Object());
            }else{
                return Result.success("成功获取菜单数据",menuSubtitleBean);
            }
        }catch (Exception e){
            e.getStackTrace();
            return Result.build500("出现异常");
        }
    }

    @ResponseBody
    @RequestMapping("/addMenuSubtitle")
    public ResultModel addMenuSubtitle(@RequestParam(value = "menuSubtitleObj") MenuSubtitleBean menuSubtitleObj){
        try{
            menuDataService.addMenuSubtitleBean(menuSubtitleObj);
            return Result.success("新增成功");
        }catch (Exception e){
            e.getStackTrace();
            return Result.build500("出现异常");
        }
    }

}
