package com.cennavi.tp.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;

/**
 * Created by sunpengyan on 2018/12/14.
 */
@Configuration
public class MyWebConfig implements WebMvcConfigurer {

    @Resource
    MyInterceptor myInterceptor;
    @Value("${file_path}")
    private String fileSavePath;

    /**
     * 设置过滤路径
     */
    @Override
    public  void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(myInterceptor)
                .excludePathPatterns("/logout","/userinfo/login","/userinfo/code","/error/**","/resources/**","/file/**")
//                .excludePathPatterns("/**")
                .addPathPatterns("/**");
    }

    /**
     * 配置资源过滤
     * 注：配置外部的资源要使用file声明，配置jar包内部的使用classpath声明。
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        registry.addResourceHandler("/resources/**").addResourceLocations("classpath:/resources/");
        //访问jar包尾部资源
        registry.addResourceHandler("/file/**").addResourceLocations("file:"+fileSavePath);
    }

    /**
     * 过滤器两种方案 1.过滤器注册配置类：如下即可
     * 2.注解方式配置过滤器：在自定义过滤器上添加注解 @WebFilter(urlPatterns = {"/*"})  在，Application中添加@ServletComponentScan
     * @return
     */
    @Bean
    public FilterRegistrationBean filterRegistration() {
        FilterRegistrationBean registration = new FilterRegistrationBean();//新建过滤器注册类
        registration.setFilter(new WebContextFilter());// 添加我们写好的过滤器
        registration.addUrlPatterns("/*");// 设置过滤器的URL模式
        return registration;
    }

}
