package com.zy.gongzhonghao.management.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

//配置类，将拦截器添加到xml文件中
@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer {

    //自动注入拦截器
    @Autowired
    private UserInterceptor userInterceptor;

    //添加拦截器需要的方法
/*    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(userInterceptor) .excludePathPatterns("/erci/**","/thy/**","/css/**","/file/**","/font/**","/fonts/**","/img/**","/js/**","/libs/**","/ruoyi/**","/phone/**").addPathPatterns("/**");
    } */

    //添加拦截器需要的方法
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(userInterceptor).addPathPatterns("/admin/**");
    }

}
