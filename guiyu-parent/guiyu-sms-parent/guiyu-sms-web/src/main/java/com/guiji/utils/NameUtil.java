package com.guiji.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.guiji.auth.api.IAuth;
import com.guiji.component.result.Result;
import com.guiji.user.dao.entity.SysUser;

@Component
public class NameUtil
{
	private static final Logger logger = LoggerFactory.getLogger(NameUtil.class);

	@Autowired
	IAuth auth;
	@Autowired
	RedisUtil redisUtil;

	public String getUserName(String userId) {
        String cacheName = (String) redisUtil.get(userId);
        if (cacheName != null) {
            return cacheName;
        } else {
            try {
                Result.ReturnData<SysUser> result = auth.getUserById(Long.valueOf(userId));
                if(result!=null && result.getBody()!=null) {
                    String userName = result.getBody().getUsername();
                    if (userName != null) {
                    	redisUtil.set(userId, userName);
                        return userName;
                    }
                }
            } catch (Exception e) {
            	logger.error(" auth.getUserName error :" + e);
            }
        }
        return "";
    }
}
