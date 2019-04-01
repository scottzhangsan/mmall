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
	
	private static DateFormat dateFormat = new SimpleDateFormat(DEFAULT_FORMAT);
	
	
	
	public static Date str2Date(String value,String format){
		if (format != null ) {
			dateFormat = new SimpleDateFormat(format) ;
		}
		try {
			return dateFormat.parse(value) ;
		} catch (ParseException e) {
			logger.error("时间转换错误",e);
			return null ;
		}
	}
	
	public static String date2String(Date value,String format){
		if (format != null) {
			dateFormat = new SimpleDateFormat(format) ;
		}
		return dateFormat.format(value);
	}

}
