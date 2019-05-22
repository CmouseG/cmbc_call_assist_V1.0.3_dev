package com.guiji.cache;

import com.guiji.auth.api.IAuth;
import com.guiji.cfg.RedisKey;
import com.guiji.component.result.Result;
import com.guiji.exception.ThirdApiException;
import com.guiji.exception.ThirdApiExceptionEnum;
import com.guiji.user.dao.entity.SysUser;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class UserCacheUtil {

    @Autowired
    RedissonClient redissonClient;

    @Autowired
    IAuth iAuth;

    /**
     * 通过clientId查询用户信息
     *
     * @param clientId
     * @return
     */
    public ThirdApiUser getUserInfoByClientId(String clientId) {

        Object object = redissonClient.getMap(RedisKey.USER_INFO_MAP).get(clientId);

        if (object == null) {

            // TODO: 2019/4/10 此处需要根据clientId查询用户信息
            Result.ReturnData<SysUser> data = iAuth.getUserByAccessKey(clientId);

            if (data == null || data.getBody() == null) throw new ThirdApiException(ThirdApiExceptionEnum.NO_THIS_USER);

            SysUser sysUser = data.getBody();

            ThirdApiUser thirdApiUser = new ThirdApiUser();

            thirdApiUser.setClientId(sysUser.getAccessKey());
            thirdApiUser.setClientSecret(sysUser.getSecretKey());
            thirdApiUser.setUserId(sysUser.getId());
            thirdApiUser.setOrgCode(sysUser.getOrgCode());

            redissonClient.getMap(RedisKey.USER_INFO_MAP).put(clientId, thirdApiUser);

            return thirdApiUser;
        } else {
            return (ThirdApiUser) object;
        }
    }

    /**
     * 通过userId查询用户信息
     *
     * @param userId
     * @return
     */
    public ThirdApiUser getUserInfoByUserId(Long userId) {

        Object object = redissonClient.getMap(RedisKey.USER_ID_INFO_MAP).get(userId);

        if (object == null) {

            Result.ReturnData<SysUser> data = iAuth.getUserById(userId);

            if (data == null || data.getBody() == null) throw new ThirdApiException(ThirdApiExceptionEnum.NO_THIS_USER);

            SysUser sysUser = data.getBody();

            ThirdApiUser thirdApiUser = new ThirdApiUser();

            thirdApiUser.setClientId(sysUser.getAccessKey());
            thirdApiUser.setClientSecret(sysUser.getSecretKey());
            thirdApiUser.setUserId(sysUser.getId());
            thirdApiUser.setOrgCode(sysUser.getOrgCode());

            redissonClient.getMap(RedisKey.USER_ID_INFO_MAP).put(userId.toString(), thirdApiUser);

            return thirdApiUser;
        } else {
            return (ThirdApiUser) object;
        }
    }

    public void deleteByUserId(Long userId) {
        redissonClient.getMap(RedisKey.USER_ID_INFO_MAP).remove(userId);
    }

    public void deleteByKey(String key) {
        redissonClient.getMap(RedisKey.USER_INFO_MAP).remove(key);
    }
}
