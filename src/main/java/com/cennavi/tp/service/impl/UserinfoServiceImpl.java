package com.cennavi.tp.service.impl;

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
import java.util.Date;
import java.util.List;
import java.util.UUID;


@Service
public class UserinfoServiceImpl implements UserinfoService {

    @Autowired
    private UserinfoDao userinfoDao;
    @Autowired
    private LoginStatusService loginStatusService;


    @Override
    public List<UserinfoBean> getUserList(Integer page, Integer pageSize) {
        int start = (page-1)*pageSize;
        return userinfoDao.getUserList(start,pageSize);
    }

    @Override
    public UserinfoBean getUserById(Integer id) {
        //userDao.getUserById(id);//自己手写实现
        return userinfoDao.findById(id);//调用已经封装好的通用方法
    }

    @Override
    public ResultModel login(String username, String password,HttpServletRequest request) {
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
            session.setMaxInactiveInterval(600);
            session.setAttribute("user",user);
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
            ResultModel resultModel = Result.success("登录成功");
            resultModel.putExcludes("password","enable");
            return resultModel;
        }else{
            return Result.fail("登录失败");
        }
    }
}
