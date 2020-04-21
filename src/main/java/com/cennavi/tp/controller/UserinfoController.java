package com.cennavi.tp.controller;

import com.cennavi.tp.beans.UserinfoBean;
import com.cennavi.tp.common.result.Result;
import com.cennavi.tp.common.result.ResultModel;
import com.cennavi.tp.service.UserinfoService;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/userinfo")
public class UserinfoController {

    @Resource
    private UserinfoService userService;

    /**
     * 获取用户列表
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

    //返回结果json中，去除不需要的属性信息
    @ResponseBody
    @RequestMapping("/test2")
    public ResultModel test2() {
        Map<String,String> map = new HashMap<>();
        map.put("name","mage");
        map.put("age","18");
        map.put("sex","男");
        ResultModel resultModel = Result.success("success",map);
        //name属性信息不会在返回结果中出现，过滤掉了
        resultModel.putExcludes("name");
        return resultModel;
    }

}
