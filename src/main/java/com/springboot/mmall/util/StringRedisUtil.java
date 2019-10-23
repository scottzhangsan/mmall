package com.springboot.mmall.util;

import java.util.concurrent.TimeUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.fasterxml.jackson.core.type.TypeReference;

/**
 * Redis工具类
 * @author yzhang98
 *
 */
@Component
public class StringRedisUtil {
	
	private Logger logger = LoggerFactory.getLogger(StringRedisUtil.class) ;
	
	@Autowired
	private StringRedisTemplate redisTemplate ;
	
	/**
	 * 保存String类型的数据
	 * @param key
	 * @param object
	 * @return
	 */
	public boolean put(String key,Object object){
		try {
			redisTemplate.opsForValue().set(key, JsonUtil.object2String(object));
			return true ;
		} catch (Exception e) {
			logger.error("保存Redis数据失败",e);
			return false ;
		}
	}
	
	/**
	 * 判断是否存在key
	 * @param key
	 * @return
	 */
	public boolean hasKey(String key){
		try {
			return redisTemplate.hasKey(key);
		} catch (Exception e) {
			logger.error("判断是否存在键异常{}",key,e);
			return false ;
		}
	}
	
	/**
	 * 获取值
	 * @param key
	 * @param t
	 * @return
	 */
	public <T> T get(String key,TypeReference<T> t){
		T value =null ;
		try {
			String tempValue = redisTemplate.opsForValue().get(key) ;
			value = JsonUtil.json2Object(tempValue, t) ;
		} catch (Exception e) {
			logger.error("Redis获取值出错",e);	
		}
		return value ; 
	}
	
	/**
	 * redis获取值得字符串
	 * @param key
	 * @return
	 */
	public String getValue(String key){
		String value = redisTemplate.opsForValue().get(key);
		return value ;
	}
	
	
	/**
	 * 保存key值设置有效时间,单位 秒
	 * @param key
	 * @param object
	 * @param time
	 * @return
	 */
	public boolean putKeyWithTime(String key,Object object,long time){
		try {
			redisTemplate.opsForValue().set(key, JsonUtil.object2String(object), time, TimeUnit.SECONDS);
			return true ;
		} catch (Exception e) {
			logger.error("Redis保存值，并设置有效时间出错",e);
			return false ;
		}
	}
	
	/**删除key 
	 * ，可以批量删除多个key
	 * @param strings
	 */
	@SuppressWarnings("unchecked")
	public void deleteKey(String ...strings ){
		if (strings != null && strings.length >0) {
			if (strings.length==1) {
				redisTemplate.delete(strings[0]);
			}else{
				redisTemplate.delete(CollectionUtils.arrayToList(strings));
			}
		}
	}
	
	

}
