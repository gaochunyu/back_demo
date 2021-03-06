package com.cennavi.tp.controller;

import com.cennavi.tp.beans.MenuSubtitleBean;
import com.cennavi.tp.beans.UserinfoBean;
import com.cennavi.tp.common.result.Result;
import com.cennavi.tp.common.result.ResultModel;
import com.cennavi.tp.service.ContentService;
import com.cennavi.tp.service.MenuDataService;
import com.cennavi.tp.utils.MyDateUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Controller
@RequestMapping("/menuData")
public class MenuDataController {

    @Resource
    private MenuDataService menuDataService;
    @Resource
    private ContentService contentService;

    /**
     * 获取菜单列表,供主页面左侧菜单使用
     * @Param uid 用户id
     * @Param model all获取所有 visit 普通访问，verify审核,mydata 我的内容
     * @return
     */
    @ResponseBody
    @RequestMapping("/getMenuList")
    public ResultModel getMenuList(String model,HttpServletRequest request){
        try{
            UserinfoBean user = (UserinfoBean) request.getSession().getAttribute("user");
            if(user==null)return Result.buildUnLogin();
            List list;
            if(model.equals("mydata")){
                List<MenuSubtitleBean> menuList = menuDataService.getMenuList(user.getId(),"publish");
                Map<Integer, List<MenuSubtitleBean>> map = new HashMap<>();
                for(MenuSubtitleBean menu : menuList){
                    if(map.get(menu.getStatus()) == null){
                        List<MenuSubtitleBean> mList = new ArrayList<>();
                        mList.add(menu);
                        map.put(menu.getStatus(),mList);
                    }else{
                        List<MenuSubtitleBean> mList = map.get(menu.getStatus());
                        mList.add(menu);
                        map.put(menu.getStatus(),mList);
                    }
                }
                list = menuDataService.getCategoryTree(menuDataService.getCategoryList(),map);
            }else{
                List<MenuSubtitleBean> menuList = menuDataService.getMenuList(0,model);
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
                list = menuDataService.getMenuTree(menuDataService.getParentMenuListByIndex(0),map);
            }
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
     * 获取菜单列表,供菜单管理使用
     * @return
     */
    @ResponseBody
    @RequestMapping("/getMenus")
    public ResultModel getMenus(HttpServletRequest request){
        UserinfoBean user = (UserinfoBean) request.getSession().getAttribute("user");
        String model;
        if(user==null)return Result.buildUnLogin();
        if(user.getRole()==0){//超级管理员
            model = "all";
        }else if(user.getRole()==1){//管理员
            model = "mydata";
        }else{
            return Result.fail("无权限访问");
        }
        Integer uid = model.equals("mydata")?user.getId():0;
        List<MenuSubtitleBean> menuList = menuDataService.getMenuList(uid,model);
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
            List list = menuDataService.getMenuAllAttrTree(menuDataService.getParentMenuListByIndex(0),map);
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
    public ResultModel deleteMenuSubtitle(@RequestParam(value = "id",required = false) Integer id,HttpServletRequest request){
        return menuDataService.deleteMenuSubtitleBeanById(id,request);
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

    /**
     * 菜单信息新增
     * @param name
     * @param parent
     * @param sort
     * @return
     */
    @ResponseBody
    @RequestMapping("/addMenuSubtitle")
    public ResultModel addMenuSubtitle(@RequestParam(value = "name") String name,
                                       @RequestParam(value = "parent") Integer parent,
                                       @RequestParam(value = "sort") Integer sort,
                                       @RequestParam(value = "level") Integer level,
                                       HttpServletRequest request){
        try{
            UserinfoBean user = (UserinfoBean) request.getSession().getAttribute("user");
            MenuSubtitleBean menuSubtitleBean = new MenuSubtitleBean();
            menuSubtitleBean.setName(name);
            menuSubtitleBean.setParent(parent);
            menuSubtitleBean.setSort(sort);
            menuSubtitleBean.setLevel(level);
            menuSubtitleBean.setUid(user.getId());
            String format = "yyyy-MM-dd HH:mm:ss";
            String time = MyDateUtils.format(new Date(),format);
            menuSubtitleBean.setCreateTime(time);
            menuSubtitleBean.setStatus(0);//编辑中
            menuDataService.addMenuSubtitleBean(menuSubtitleBean);
            return Result.success("新增成功",1);
        }catch (Exception e){
            e.getStackTrace();
            return Result.build500("出现异常");
        }
    }

    /**
     * 修改菜单信息
     * @param id
     * @param name
     * @param parent
     * @param sort
     * @return
     */
    @ResponseBody
    @RequestMapping("/updateMenuSubtitle")
    public ResultModel updateMenuSubtitle(@RequestParam(value = "id") Integer id,
                                          @RequestParam(value = "name") String name,
                                          @RequestParam(value = "parent") Integer parent,
                                          @RequestParam(value = "sort") Integer sort,
                                          @RequestParam(value = "level") Integer level){
        try{
            MenuSubtitleBean menuSubtitleBean = menuDataService.getMenuSubtitleBeanById(id);
            if(menuSubtitleBean==null){
                return Result.fail("id参数无效");
            }
            menuSubtitleBean.setName(name);
            menuSubtitleBean.setParent(parent);
            menuSubtitleBean.setSort(sort);
            menuSubtitleBean.setLevel(level);
            menuDataService.updateMenuSubtitleBean(menuSubtitleBean);
            return Result.success("修改成功",1);
        }catch (Exception e){
            e.getStackTrace();
            return Result.build500("出现异常");
        }
    }

    /**
     * 审核
     * @param id 菜单id
     * @param status 状态码：2通过 3拒绝
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping("/verify")
    public ResultModel verify(Integer id,Integer status,HttpServletRequest request){
        try{
            UserinfoBean user = (UserinfoBean) request.getSession().getAttribute("user");
            if(user==null)return Result.buildUnLogin();
            if(user.getRole()!=0)return Result.fail("无操作权限");
            MenuSubtitleBean menu = menuDataService.getMenuSubtitleBeanById(id);
            if(status==2 || status==3){
                menu.setStatus(status);
                Integer count = menuDataService.updateMenuSubtitleBean(menu);
                if(count>0){
                    return Result.success("操作成功");
                }else{
                    return Result.fail("操作失败");
                }
            }else{
                return Result.fail("状态码错误");
            }
        }catch (Exception e){
            e.printStackTrace();
            return Result.build500("出现异常");
        }
    }

}
