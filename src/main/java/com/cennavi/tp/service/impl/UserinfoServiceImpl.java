package com.cennavi.tp.service.impl;

import com.alibaba.fastjson.JSON;
import com.cennavi.tp.beans.*;
import com.cennavi.tp.common.result.Result;
import com.cennavi.tp.common.result.ResultModel;
import com.cennavi.tp.dao.UserinfoDao;
import com.cennavi.tp.service.LoginStatusService;
import com.cennavi.tp.service.UserinfoService;
import com.cennavi.tp.utils.MyDateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;


@Service
public class UserinfoServiceImpl implements UserinfoService {

    @Autowired
    private UserinfoDao userinfoDao;
    @Autowired
    private LoginStatusService loginStatusService;

    @Override
    public ResultModel login(String username, String password,String code,HttpServletRequest request) {
        Object obj = request.getSession().getAttribute("code");
        String saveCode = (String) request.getSession().getAttribute("code");
        if(!code.equals(saveCode)){
            return Result.fail("验证码不正确");
        }
        List<UserinfoBean> list = userinfoDao.login(username);
        if(list.isEmpty()){
            return Result.fail("用户名不存在");
        }else if(list.size()>1){
            return Result.fail("登录数据异常");
        }else if(list.size()==1){
            UserinfoBean user = list.get(0);
            if(!user.getPassword().equals(password)){
                return Result.fail("用户名或密码错误");
            }
            //用户数据保持到session
            HttpSession session = request.getSession();
            session.setMaxInactiveInterval(3600);
            session.setAttribute("user",user);
            //清空之前的登录状态（不允许多人登录）
//            loginStatusService.removeByUid(user.getId());
            //记录登录状态
            LoginStatusBean loginStatus = new LoginStatusBean();
            String token = UUID.randomUUID().toString().replace("-","");
            loginStatus.setToken(token);
            loginStatus.setUid(user.getId());
            String format = "yyyy-MM-dd HH:mm:ss";
            String time = MyDateUtils.format(new Date(),format);
            loginStatus.setLogin_time(time);
            loginStatus.setFlag(1);
            loginStatusService.insert(loginStatus);
            //处理返回数据
            Map<String,Object> map = new HashMap<>();
            map.put("token",token);
            map.put("id",user.getId());
            map.put("role",user.getRole());
            map.put("model",user.getModel());
            map.put("expireTime",user.getExpireTime());
            ResultModel resultModel = Result.success("登录成功",map);
            resultModel.putExcludes("username","password","createTime");
            return resultModel;
        }else{
            return Result.fail("登录失败");
        }
    }

    @Override
    public List<UserinfoBean> getUsers(Integer page, Integer pageSize, String keyword) {
        int start = (page-1)*pageSize;
        return userinfoDao.getUsers(start,pageSize,keyword);
    }

    @Override
    public int getUsersCount(Integer start, Integer pageSize, String keyword) {
        return userinfoDao.getUsersCount(start,pageSize,keyword);
    }

    @Override
    public UserinfoBean getUserById(Integer id) {
        return userinfoDao.findById(id);
    }

    @Override
    public boolean updateUser(UserinfoBean userinfoBean) {
        try{
            userinfoDao.update(userinfoBean);
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public boolean removeUser(Integer id) {
        try{
            userinfoDao.delete(id);
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public boolean addUser(UserinfoBean userinfoBean) {
        try{
            userinfoDao.save(userinfoBean);
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public int getUsersCountByUserName(String username) {
        return userinfoDao.getUsersCountByUserName(username);
    }

    @Override
    public int getUsersCountByIdAndUserName(Integer id, String username) {
        return userinfoDao.getUsersCountByIdAndUserName(id,username);
    }

    public static void main(String[] args) {
        Map<String,String> map = new HashMap<>();
        map.put("a","d");
        map.put("c","a");
        String jsonString= JSON.toJSONString(map);
        System.out.println(jsonString);
    }

}
