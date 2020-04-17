package com.cennavi.tp.controller;

import com.cennavi.tp.beans.User;
import com.cennavi.tp.common.result.ResultModel;
import com.cennavi.tp.common.result.Result;
import com.cennavi.tp.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/hello")
public class HelloWorld {

    @Resource
    private UserService userService;

    @ResponseBody
    @RequestMapping("/getUsers")
    public ResultModel getUsers() {
        List<User> userList = userService.getUserList(0,10);
        return Result.success("成功获取数据",userList);
    }

    @ResponseBody
    @RequestMapping("/test")
    public ResultModel test() {
        Map<String,String> map = new HashMap<>();
        map.put("name","mage");
        map.put("age","18");
        map.put("sex","男");
        return Result.success("成功获取数据",map);
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
