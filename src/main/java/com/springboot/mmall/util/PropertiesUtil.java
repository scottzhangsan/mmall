package com.springboot.mmall.util;

import java.io.IOException;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 获取文件信息的工具
 * @author yzhang98
 *
 */
public class PropertiesUtil {
	
	private static Logger logger = LoggerFactory.getLogger(PropertiesUtil.class) ;
	
	private static Properties propersties = new Properties() ;
	
	public static String getValue(String key){
		try {
			propersties.load(PropertiesUtil.class.getClassLoader().getResourceAsStream("mmall.properties"));
		} catch (IOException e) {
			logger.error("获取文件信息失败",e);
		}
		return propersties.getProperty(key.trim()).trim() ;
	}
	

}
