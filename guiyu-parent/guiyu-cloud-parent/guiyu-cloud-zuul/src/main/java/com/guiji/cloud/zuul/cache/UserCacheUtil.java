package com.guiji.cloud.zuul.cache;

import com.guiji.auth.api.IAuth;
import com.guiji.component.result.Result;
import com.guiji.user.dao.entity.SysUser;
import com.guiji.utils.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class UserCacheUtil {

    @Autowired
    RedisUtil redisUtil;

    @Autowired
    IAuth iAuth;

    private final static String USER_INFO_MAP_KEY = "third_api_user_info_map";

    public Long getUserInfoByClientId(String clientId) {

        Object object = redisUtil.hget(USER_INFO_MAP_KEY, clientId);

        if (object == null) {

            // TODO: 2019/4/10 此处需要根据clientId查询用户信息
            Result.ReturnData<SysUser> data = iAuth.getUserById(Long.valueOf(clientId));

            if (data == null || data.getBody() == null) return null;

            SysUser sysUser = data.getBody();

            redisUtil.hset(USER_INFO_MAP_KEY, clientId, sysUser.getId());

            return sysUser.getId();
        } else {
            return (Long) object;
        }
    }
}
