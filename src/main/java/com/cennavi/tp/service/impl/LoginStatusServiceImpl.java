package com.cennavi.tp.service.impl;

import com.cennavi.tp.beans.LoginStatusBean;
import com.cennavi.tp.beans.UserinfoBean;
import com.cennavi.tp.dao.LoginStatusDao;
import com.cennavi.tp.dao.UserinfoDao;
import com.cennavi.tp.service.LoginStatusService;
import com.cennavi.tp.utils.MyDateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * <p>
 * 用户登录状态 服务实现类
 * </p>
 *
 * @author MaLiWei
 * @since 2020-02-07
 */
@Service
public class LoginStatusServiceImpl implements LoginStatusService {

    @Autowired
    private LoginStatusDao loginStatusDao;
    @Autowired
    private UserinfoDao userinfoDao;

    /**
     * 通过token检查是否已登录
     * @param token
     * @return
     */
    public UserinfoBean checkUserLoginByToken(String token){
        try{
            LoginStatusBean userLoginStatus = loginStatusDao.checkUserLoginByToken(token);
            if(userLoginStatus==null){//无登录信息
                return null;
            }else {//有登录信息
                //更新数据库登录信息
                String format = "yyyy-MM-dd HH:mm:ss";
                String time = MyDateUtils.format(new Date(),format);
                loginStatusDao.updateLastVisitTime(token,time);
                //获取用户信息
                UserinfoBean user = userinfoDao.findById(userLoginStatus.getUid());
                return user;
            }
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void insert(LoginStatusBean loginStatusBean) {
        loginStatusDao.save(loginStatusBean);
    }

}
