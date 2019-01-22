package com.guiji.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.common.cache.Cache;
import com.guiji.auth.api.IAuth;
import com.guiji.component.result.Result;
import com.guiji.user.dao.entity.SysUser;

public class NameUtil
{
	private static final Logger log = LoggerFactory.getLogger(NameUtil.class);

	@Autowired
	IAuth auth;

	private Cache<Integer, String> userCache;

	public String getUserName(Integer userId) {
        String cacheName = userCache.getIfPresent(userId);
        if (cacheName != null) {
            return cacheName;
        } else {
            try {
                Result.ReturnData<SysUser> result = auth.getUserById(Long.valueOf(userId));
                if(result!=null && result.getBody()!=null) {
                    String userName = result.getBody().getUsername();
                    if (userName != null) {
                        userCache.put(userId, userName);
                        return userName;
                    }
                }
            } catch (Exception e) {
                log.error(" auth.getUserName error :" + e);
            }
        }
        return "";
    }
}
