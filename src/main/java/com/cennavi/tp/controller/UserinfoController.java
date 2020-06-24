package com.cennavi.tp.controller;

import com.alibaba.fastjson.JSONObject;
import com.cennavi.tp.beans.UserinfoBean;
import com.cennavi.tp.common.result.Result;
import com.cennavi.tp.common.result.ResultModel;
import com.cennavi.tp.service.UserinfoService;
import com.cennavi.tp.utils.MyDateUtils;
import com.cennavi.tp.utils.Tools;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.wf.captcha.ArithmeticCaptcha;
import org.apache.commons.lang.StringUtils;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
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
    public ResultModel login(String username, String password,String code,HttpSession session, HttpServletRequest request) {
        if(StringUtils.isBlank(username))
            return Result.fail("用户名不能为空");
        else if(StringUtils.isBlank(password))
            return Result.fail("密码不能为空");
        else if(StringUtils.isBlank(code))
            return Result.fail("验证码不能为空");
        else if(request.getSession().getAttribute("code")==null)
            return Result.fail("验证码已失效");
        return userService.login(username,password,code,request);
    }

    @GetMapping(value = "/code")
    public ResponseEntity<Object> getCode(HttpSession session,HttpServletRequest request){
        ArithmeticCaptcha captcha = new ArithmeticCaptcha(111, 36);
        // 几位数运算，默认是两位
        captcha.setLen(2);
        // 获取运算的结果
        String result = captcha.text();
        session.setAttribute("code",result);
        // 验证码信息
        Map<String,Object> imgResult = new HashMap<String,Object>(2){{
            put("img", captcha.toBase64());
        }};
        return ResponseEntity.ok(imgResult);
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
    public ResultModel getUsers(Integer page,Integer pageSize,String keyword, HttpServletRequest request){
        try {
            if (keyword == null)
                keyword = "";
            Map<String,Object> map = new HashMap<>();
            List<UserinfoBean> list = userService.getUsers(page,pageSize,keyword);
            int count = userService.getUsersCount(page,pageSize,keyword);
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
    public ResultModel updateUser(Integer id,String username,Integer enable,Integer role,String model,String date) {
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
            user.setExpireTime(date);
            user.setModel(Tools.getModelsMap(model));
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
    public ResultModel saveUser(Integer id,String username,Integer enable,Integer role,String model,String date) {
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
            user.setExpireTime(date);
            user.setModel(Tools.getModelsMap(model));
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


    @ResponseBody
    @RequestMapping("/updatePass")
    public ResultModel updatePass(Integer id,String oldPass,String newPass, HttpServletRequest request){
        try{
            UserinfoBean user = (UserinfoBean) request.getSession().getAttribute("user");
            if(user.getPassword().equals(oldPass)){
                user.setPassword(newPass);
                request.getSession().setAttribute("user",user);
                userService.updateUser(user);
            }else{
                return Result.fail("旧密码输入不正确");
            }
            return Result.success("密码修改成功");
        }catch (Exception e){
            return Result.fail("出现异常");
        }
    }

}
