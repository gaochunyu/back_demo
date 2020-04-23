package com.cennavi.tp.controller;

import com.cennavi.tp.beans.UserinfoBean;
import com.cennavi.tp.common.result.Result;
import com.cennavi.tp.common.result.ResultModel;
import com.cennavi.tp.service.UserinfoService;
import com.cennavi.tp.utils.MyDateUtils;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/userinfo")
public class UserinfoController {

    @Resource
    private UserinfoService userService;

    /**
     * 用户登录
     * @param username 用户名
     * @param password 密码
     * @return
     */
    @ResponseBody
    @GetMapping(value = "/login",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResultModel login(String username, String password, HttpServletRequest request) {
        return userService.login(username,password,request);
    }

    /**
     * 获取用户列表
     * @param page
     * @param pageSize
     * @param request
     * @return
     */
    @ResponseBody
    @GetMapping("getUsers")
    public ResultModel getUsers(Integer page,Integer pageSize,HttpServletRequest request){
        try {
            Map<String,Object> map = new HashMap<>();
            List<UserinfoBean> list = userService.getUsers(page,pageSize);
            int count = userService.getUsersCount(page,pageSize);
            map.put("list",list);
            map.put("total",count);
            ResultModel resultModel = Result.success("查询成功",map);
            resultModel.putExcludes("password");
            return resultModel;
        }catch (Exception e){
            e.printStackTrace();
            return Result.build500("查询出现异常");
        }
    }

    /**
     * 根据id查询用户信息
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping("/getUserById")
    public ResultModel getUserById(int id) {
        try{
            UserinfoBean user = userService.getUserById(id);
            return Result.success("成功获取用户数据",user);
        }catch (Exception e){
            e.getStackTrace();
            return Result.build500("出现异常");
        }
    }


    /**
     * 修改用户信息
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping("/updateUser")
    public ResultModel updateUser(Integer id,String username,Integer enable,Integer role) {
        try{
            int count = userService.getUsersCountByIdAndUserName(id,username);
            if(count>0){
                return Result.fail("该用户名已存在，请更换其它用户名");
            }
            UserinfoBean user = userService.getUserById(id);
            if(user==null){
                return Result.fail("id参数无效");
            }
            user.setUsername(username);
            user.setEnable(enable);
            user.setRole(role);
            boolean flag = userService.updateUser(user);
            if(flag){
                return Result.success("修改成功");
            }else{
                return Result.fail("修改失败");
            }
        }catch (Exception e){
            e.getStackTrace();
            return Result.build500("出现异常");
        }
    }

    /**
     * 删除用户信息
     * @return
     */
    @ResponseBody
    @RequestMapping("/removeUser")
    public ResultModel removeUser(Integer id) {
        boolean flag = userService.removeUser(id);
        if(flag){
            return Result.success("删除成功");
        }else{
            return Result.fail("删除失败");
        }
    }

    /**
     * 添加用户信息
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping("/saveUser")
    public ResultModel saveUser(Integer id,String username,Integer enable,Integer role) {
        try{
            int count = userService.getUsersCountByUserName(username);
            if(count>0){
                return Result.fail("该用户名已存在，请更换其它用户名");
            }
            UserinfoBean user = new UserinfoBean();
            user.setUsername(username);
            user.setPassword("123456");
            user.setEnable(enable);
            user.setRole(role);
            String format = "yyyy-MM-dd HH:mm:ss";
            String time = MyDateUtils.format(new Date(),format);
            user.setCreateTime(time);
            boolean flag = userService.addUser(user);
            if(flag){
                return Result.success("添加成功");
            }else{
                return Result.fail("添加失败");
            }
        }catch (Exception e){
            e.getStackTrace();
            return Result.build500("出现异常");
        }
    }

}
