package com.springboot.mmall.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.springboot.mmall.interceptor.LoginInterceptor;

@Configuration
public class SpringMvcConfig extends WebMvcConfigurerAdapter {
	/**
	 * 注册拦截器,过滤掉登录的路径
	 */
	/*@Autowired
	private LoginInterceptor loginInterceptor ;
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(loginInterceptor).addPathPatterns("/user/**").excludePathPatterns("/user/login") ;
	}*/

}
