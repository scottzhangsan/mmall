package com.springboot.mmall.common;

import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

/**
 * 本地Cache的缓存
 * @author yzhang98
 *
 */
public class TokenCache {
	
	private static Logger logger = LoggerFactory.getLogger(TokenCache.class) ;
	private static final String NULL_OBJECT = "null" ; //null字符串
	private static final Integer maxCacheNum = 10000 ; //最大的缓存的个数,超过最大的个数，采用LRU算法移除
	
	/**
	 * 有效期12小时
	 */
	private static LoadingCache<String, String> localCache = CacheBuilder.newBuilder().initialCapacity(1000).maximumSize(maxCacheNum)
			.expireAfterAccess(12, TimeUnit.HOURS).build(new CacheLoader<String,String>() {
               //默认的数据加载实现，当调用get取值的时候，如果key没有对应的值，就调用这个方法进行加载
				//如果key没有对应的value值返回 空的"null" 
				@Override
				public String load(String arg0) throws Exception {
					return NULL_OBJECT;
				}
				
			}) ;
	
	/**
	 * 放置缓存
	 * @param key
	 * @param value
	 */
	public static void setKey(String key,String value){
		localCache.put(key, value);
	}
	/**
	 * 获取缓存
	 * @return
	 */
	public static String getValue(String key){
		String value = null ;
		try {
			
			value = localCache.get(key) ;
			if (NULL_OBJECT.equals(value)) {
				return null ;
			}
			
			return value ;
		} catch (Exception e) {
			logger.error("获取缓存值失败",e);
		}
		return null ;
	}
}
