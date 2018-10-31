package com.guiji.auth.cache.dao;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.CachingSessionDAO;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

//@Component
public class RedisSessionDAO extends CachingSessionDAO {
	
	private DefaultCacheDao sessionCache=null;
	private DefaultCacheDao timeCache=null;
	
	@SuppressWarnings("rawtypes")
	@Resource(name="redisTemplateWrap")
	private RedisTemplate template;
	
	@PostConstruct
	public void init(){
		sessionCache=new DefaultCacheDao(template,"shiro.session",1800L);
		timeCache=new DefaultCacheDao(template,"shiro.time",1200L);
	}
	
	@Override
	protected void doUpdate(Session session) {
//		String seesionId=session.getId().toString();
//		Object obj=timeCache.get(seesionId);
//		if(obj==null){
//			sessionCache.set(seesionId, session);
//			timeCache.set(seesionId, 1);
//			System.out.println("doUpdate");
//		}
		sessionCache.set(session.getId().toString(), session);
		System.out.println("doUpdate");
	}

	@Override
	protected void doDelete(Session session) {
		String sessionId=session.getId().toString();
		sessionCache.del(sessionId);
		timeCache.del(sessionId);
		System.out.println("doDelete");
	}

	@Override
	protected Serializable doCreate(Session session) {
		Serializable sessionId=session.getId();
		if(sessionId==null){
			sessionId = generateSessionId(session);
	        assignSessionId(session, sessionId);
	        sessionCache.set((String)sessionId, session);
			timeCache.set((String)sessionId, 1);
		}
		System.out.println("doCreate");
        return sessionId;
	}

	@Override
	protected Session doReadSession(Serializable sessionId) {
		System.out.println("doReadSession");
		return (Session) sessionCache.get(sessionId.toString());
	}
	
}