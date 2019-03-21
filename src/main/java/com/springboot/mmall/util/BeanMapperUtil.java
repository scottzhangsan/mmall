package com.springboot.mmall.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;

public class BeanMapperUtil {
	
	private static Logger logger = LoggerFactory.getLogger(BeanMapperUtil.class) ;
	
	public static <T> T map(Object source, Class<T> target){
		if (source == null ) {
			return null ;
		}
		
		try {
			T t = target.newInstance() ;
			BeanUtils.copyProperties(source, t);
			
			return t ;
		} catch (Exception e) {
			logger.error("对象转换出错",e);
			throw new RuntimeException("对象转换出错");
		}
	}
	
	

}
