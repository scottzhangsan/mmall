package com.springboot.mmall.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 时间转换的工具
 * @author yzhang98
 *
 */
public class DateUtil {
	
	private static Logger logger =LoggerFactory.getLogger(DateUtil.class) ;
	
	private static String DEFAULT_FORMAT = "yyyy-MM-dd HH:mm:ss" ;
	
	
	
	public static Date str2Date(String value,String format){
		String pattern ="" ;
		DateFormat dateFormat = new SimpleDateFormat(pattern = format==null?DEFAULT_FORMAT:format) ;
		try {
			return dateFormat.parse(value) ;
		} catch (ParseException e) {
			logger.error("时间转换错误",e);
			return null ;
		}
	}

}
