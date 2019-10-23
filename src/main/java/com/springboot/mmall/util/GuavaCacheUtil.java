package com.springboot.mmall.util;

import java.util.concurrent.TimeUnit;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.cache.RemovalListener;
import com.google.common.cache.RemovalNotification;

/**
 * guava缓存
 * 
 * @author yzhang98
 *
 */
public class GuavaCacheUtil {

	public static LoadingCache<String, String> getLoadCahe() {
		// CacheBuilder的构造函数是私有的，只能通过其静态方法newBuilder()来获得CacheBuilder的实例
		LoadingCache<String, String> cache = CacheBuilder.newBuilder()
				// 设置并发级别为8，并发级别是指可以同时写缓存的线程数
				.concurrencyLevel(8)
				// 设置写缓存后180秒钟过期
				.expireAfterWrite(180, TimeUnit.SECONDS)
				// 设置缓存容器的初始容量为10
				.initialCapacity(10)
				// 设置缓存最大容量为100，超过100之后就会按照LRU最近虽少使用算法来移除缓存项
				.maximumSize(100)
				// 设置要统计缓存的命中率
				.recordStats()
				// 设置缓存的移除通知
				.removalListener(new RemovalListener<Object, Object>() {
					@Override
					public void onRemoval(RemovalNotification<Object, Object> notification) {
						System.out.println(notification.getKey() + " was removed, cause is " + notification.getCause());
					}
				})
				// build方法中可以指定CacheLoader，在缓存不存在时通过CacheLoader的实现自动加载缓存
				.build(new CacheLoader<String, String>() {
					@Override
					public String load(String key) throws Exception {
						System.out.println("load student " + key);
						return "null";
					}
				});

		return cache;

	}
	
	public static void  test (){
		throw new RuntimeException() ;
	}

	public static void main(String[] args) throws Exception {
		LoadingCache<String, String> cache = GuavaCacheUtil.getLoadCahe();
		  
		cache.put("key", "value");
		try {
			//test();
			int i =5/0 ;
			System.out.println(cache.get("key"));
			//TimeUnit.SECONDS.sleep(8);
			System.out.println(cache.get("key"));
			//TimeUnit.SECONDS.sleep(2);
			System.out.println(cache.get("key"));
		} finally {
			cache.invalidate("key");
			System.out.println(cache.get("key"));
		}
		
		
		

	}

}
