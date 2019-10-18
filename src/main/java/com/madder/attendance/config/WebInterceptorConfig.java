package com.madder.attendance.config;

import com.madder.attendance.interceptor.LoginInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Author wangqian
 * @Date 2019-09-18 16:48
 **/

@Configuration
public class WebInterceptorConfig implements WebMvcConfigurer {

    //自定义拦截器
    @Bean
    public LoginInterceptor getLoginInterceptor(){
        LoginInterceptor loginInterceptor = new LoginInterceptor();
        return loginInterceptor;
    }


    //添加拦截器
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //登录校验，添加不用拦截的路径
        registry.addInterceptor(getLoginInterceptor()).addPathPatterns("/**").excludePathPatterns("/","/index/toLogin","/index/login",
                "/index/toResetPwd","/index/updatePwd","/resources/**","/attendance/**","/index/index","/overtime/**","/vacation/**");
    }
}
