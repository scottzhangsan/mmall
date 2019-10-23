package com.springboot.mmall.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
/**
 * Bean转换工具
 * @author yzhang98
 *
 */
public class BeanMapperUtil {
	
	private static Logger logger = LoggerFactory.getLogger(BeanMapperUtil.class) ;
	
	/**
	 * 相同属性Bean之间的转换
	 * @param source
	 * @param target
	 * @return
	 */
	public static <T> T map(Object source, Class<T> target){
		if (source == null ) {
			return null ;
		}
		
		try {
			//得到泛型对象
			T t = target.newInstance() ;
			BeanUtils.copyProperties(source, t);
			
			return t ;
		} catch (Exception e) {
			logger.error("对象转换出错",e);
			throw new RuntimeException("对象转换出错");
		}
	}
	
	/**
	 * List的转换
	 * @param source
	 * @param target
	 * @return
	 */
	public static <T> List<T> mapList(Collection<?> source,Class<T> target){
		List<T> targetList = new ArrayList<T>() ;
		if (source == null) {
			return null ;
		}else if(source.size() <= 0){
			return targetList ;
		}
		for (Object t : source) {
			targetList.add(map(t, target)) ;
		}
		return targetList ;
	}
	
	

}
