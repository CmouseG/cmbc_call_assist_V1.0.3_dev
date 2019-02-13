package com.guiji.ccmanager.manager;


import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.guiji.auth.api.IAuth;
import com.guiji.botsentence.api.IBotSentenceProcess;
import com.guiji.botsentence.api.entity.BotSentenceProcess;
import com.guiji.botsentence.api.entity.ServerResult;
import com.guiji.component.result.Result;
import com.guiji.user.dao.entity.SysUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @Auther: 黎阳
 * @Date: 2018/12/6 0006 17:16
 * @Description:
 */
@Slf4j
@Component
public class CacheManager {

    @Autowired
    IBotSentenceProcess iBotSentenceProcess;
    @Autowired
    IAuth auth;

    private Cache<String, String> tempCache;
    private Cache<Integer, String> userCache;

    @PostConstruct
    public void init() {
        tempCache = CacheBuilder.newBuilder().expireAfterWrite(12, TimeUnit.HOURS).build();
        userCache = CacheBuilder.newBuilder().expireAfterWrite(12, TimeUnit.HOURS).build();
    }


    public String getTempName(String temId) {
        String cacheName = tempCache.getIfPresent(temId);
        if (cacheName != null) {
            return cacheName;
        } else {
            try {
                ServerResult<List<BotSentenceProcess>> result = iBotSentenceProcess.getTemplateById(temId);
                String temName = result.getData().get(0).getTemplateName();
                if (temName != null) {
                    tempCache.put(temId, temName);
                    return temName;
                }
            } catch (Exception e) {
                log.error(" iBotSentenceProcess.getTemplateById error :" + e);
            }
        }
        return "";
    }

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
