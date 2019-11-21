package com.springboot.mmall.util;

import java.util.Map;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
/**
 * 
 * @author yzhang98
 *SpringContextUtil初始化先执行，然后在执行ApplicationContextAware接口的回调方法
 */
@Component
public class SpringContextUtil implements ApplicationContextAware{
	
	
	private static ApplicationContext context ;
    /*
     * 实现ApplicationContextAware接口的回调方法
     * @see org.springframework.context.ApplicationContextAware#setApplicationContext(org.springframework.context.ApplicationContext)
     */
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
	       context = applicationContext ;
		
	}
	/*
	 * 获取Application对象
	 */
	public static ApplicationContext getApplicationContext(){
		return context ;
	}
	
	/**
	 * 根据beanName获取bean的实列
	 * @param beanName
	 * @return
	 */
	public static Object getBean(String beanName){
		return context.getBean(beanName);
	}
	/**根据bean的类型获取bean的实列
	 * 
	 * @param classType
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static Object getBean(Class classType){
		return context.getBean(classType) ;
	}
	/**
	 * 根据类型，获取bean及其子类
	 * @param classType
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static Map<String, Object> getBeansOfType(Class classType){
		return context.getBeansOfType(classType) ;
	}
	

}
