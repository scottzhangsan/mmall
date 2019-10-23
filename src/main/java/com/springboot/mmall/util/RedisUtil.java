package com.springboot.mmall.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

/**
 * Redis工具类
 * 
 * @author yzhang98
 *
 */
@Component
public class RedisUtil {

	@Autowired
	private RedisTemplate<String, Object> redisTemplate;

	/**
	 * 
	 * 普通缓存放入
	 * 
	 * @param key
	 *            键
	 * @param value
	 *            值
	 * 
	 * 
	 */
	public boolean set(String key, Object value) {

		try {

			redisTemplate.opsForValue().set(key, value);

			return true;

		} catch (Exception e) {

			e.printStackTrace();
			return false;

		}

	}

}
