package com.guiji.cloud.zuul.cache;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.util.concurrent.TimeUnit;


public class DefaultCacheDao {

	@SuppressWarnings("rawtypes")
	private RedisTemplate redisTemplate;
	
	private ValueOperations<String,Object> ope;
	
	private String cacheName="";
	
	private long timeOut=0;
	
	public DefaultCacheDao() {
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public DefaultCacheDao(RedisTemplate redisTemplate, String cacheName, long timeOut){
		this.redisTemplate=redisTemplate;
		this.ope=redisTemplate.opsForValue();
		this.cacheName=cacheName+".";
		this.timeOut=timeOut;
	}
	
	public void set(String key, Object value) {
		if(timeOut!=0){
			ope.set(cacheName+key, value,timeOut,TimeUnit.SECONDS);
		}else{
			ope.set(cacheName+key, value);
		}
	}
	
	public Object get(String key){
		return ope.get(cacheName+key);
	}
	
	@SuppressWarnings("unchecked")
	public void del(String key){
		redisTemplate.delete(cacheName+key);
	}
	
	
	
}
