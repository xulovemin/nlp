package com.jc.common.utils;

import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.support.SimpleValueWrapper;

/**
* @Description: spring缓存工具类
* @author 高研 
* @date 2015年9月11日 上午9:26:19
 */
public class CacheUtils {
	
	private static CacheManager cacheManager =  null;

	private static final String SYS_CACHE = "system";

	/**
	* Description:	从缓存中获取值
	* @param key	key值
	* @return Object	返回获取到的对象	没有为null
	 */
	public static Object get(String key) {
		return get(SYS_CACHE, key);
	}

	/**
	* Description: 把值放到缓存中
	* @param key	key值
	* @param value 要放到缓存的对象
	* @return void
	 */
	public static void put(String key, Object value) {
		put(SYS_CACHE, key, value);
	}

	/**
	* Description: 从缓存中删除对象
	* @param key key值
	* @return void 
	 */
	public static void remove(String key) {
		remove(SYS_CACHE, key);
	}
	
	/**
	* Description: 根据缓存名称和key值获取对象
	* @param cacheName	缓存名称
	* @param key	key值
	* @return Object 获取到的对象
	 */
	public static Object get(String cacheName, String key) {
		if(getCache(cacheName) != null){
			try{
				SimpleValueWrapper wrapper = (SimpleValueWrapper) getCache(cacheName).get(key);
				if(wrapper != null){
					return wrapper.get();
				} else {
					return null;
				}
			} catch(Throwable e){
				return null;
			}
		} else {
			return null;
		}
	}

	/**
	* Description: 根据缓存名称和key值把对象存到缓存中
	* @param cacheName	缓存名称
	* @param key	key值
	* @param value 	要缓存的值
	* @return void
	 */
	public static void put(String cacheName, String key, Object value) {
		try{
			getCache(cacheName).put(key,value);
		} catch(Exception e){
			e.printStackTrace();
		}
	}

	/**
	* Description: 根据缓存名称和key从缓存中删除对象
	* @param cacheName 缓存名称
	* @param key key值
	* @return void
	 */
	public static void remove(String cacheName, String key) {
		getCache(cacheName).evict(key);
	}
	
	/**
	 * 获得一个Cache，没有则创建一个
	 * @param cacheName 缓存名称
	 * @return Cache对象
	 */
	private static Cache getCache(String cacheName){
		if(cacheManager == null){
			cacheManager = SpringContextHolder.getBean("cacheManager");
		}
		try{
			return cacheManager.getCache(cacheName);
		} catch(Throwable e){
			return null;
		}
	}
}
