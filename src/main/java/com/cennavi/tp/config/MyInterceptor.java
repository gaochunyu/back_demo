package com.cennavi.tp.config;


import com.cennavi.tp.beans.UserinfoBean;
import com.cennavi.tp.service.LoginStatusService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Controller拦截器(检查登录)
 **/
@Component
public class MyInterceptor implements HandlerInterceptor {
    private final static Logger logger = LoggerFactory.getLogger(MyInterceptor.class);
    @Resource
    public LoginStatusService checkUserLoginByToken;

    /**
     * Controller 调用之前拦截
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        String context =  request.getContextPath();
        String uri = request.getRequestURI();
        //带token,客户端请求
        logger.info("client request："+uri);
        String token= request.getParameter("token");
        UserinfoBean user = checkUserLoginByToken.checkUserLoginByToken(token);
        if (user!=null){
            //已登录
            logger.info("login："+uri);
            //访问延迟失效时间
            HttpSession session = request.getSession();
            session.setMaxInactiveInterval(600);//单位：秒
            session.setAttribute("user",user);
            return true;
        }else{
            logger.info("unlogin："+uri);
            response.sendRedirect(context+"/error/unlogin");
            return false;
        }

    }

    /**
     * Controller之后渲染视图之前回调
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {
    }

    /**
     * Controller 调用之后拦截
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
    }

}
