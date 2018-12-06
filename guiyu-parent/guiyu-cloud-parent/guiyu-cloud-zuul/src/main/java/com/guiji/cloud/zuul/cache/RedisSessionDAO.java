package com.guiji.cloud.zuul.cache;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.CachingSessionDAO;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.io.Serializable;

@Component
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
		sessionCache.set(session.getId().toString(), session);
		//System.out.println("doUpdate");
	}

	@Override
	protected void doDelete(Session session) {
		String sessionId=session.getId().toString();
		sessionCache.del(sessionId);
		timeCache.del(sessionId);
		//System.out.println("doDelete");
	}

	@Override
	protected Serializable doCreate(Session session) {
		Serializable sessionId=session.getId();
		if(sessionId==null){
			sessionId = generateSessionId(session);
	        assignSessionId(session, sessionId);
			timeCache.set((String)sessionId, 1);
		}
		//System.out.println("doCreate");
        return sessionId;
	}

	@Override
	protected Session doReadSession(Serializable sessionId) {
		//System.out.println("doReadSession");
		return (Session) sessionCache.get(sessionId.toString());
	}
	
}
