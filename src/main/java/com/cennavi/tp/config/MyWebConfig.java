package com.cennavi.tp.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Created by sunpengyan on 2018/12/14.
 */
@Configuration
public class MyWebConfig implements WebMvcConfigurer {

//    @Autowired
//    private CommonInterceptor commonInterceptor;
//
//    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(commonInterceptor).addPathPatterns("/**").excludePathPatterns();
//        //registry.addInterceptor(new CommonInterceptor2());
//    }

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
