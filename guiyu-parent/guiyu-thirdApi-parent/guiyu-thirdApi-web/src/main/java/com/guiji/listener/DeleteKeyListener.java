package com.guiji.listener;

import com.guiji.cache.ThirdApiUser;
import com.guiji.cache.UserCacheUtil;
import com.guiji.cfg.ThirdApiMqConfig;
import com.guiji.common.GenericResponse;
import com.guiji.delay.DelayMessage;
import com.guiji.delay.RedisDelayService;
import com.guiji.exception.ThirdApiExceptionEnum;
import com.guiji.sign.Md5Utils;
import com.guiji.sign.SignRo;
import com.guiji.utils.BeanUtil;
import com.guiji.utils.JsonUtils;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @version V1.0
 * @ClassName: DeleteKeyListener
 * @Description: 删除用户缓存信息
 */
@Component
@RabbitListener(queues = ThirdApiMqConfig.NOTIFY_UPDATE_USER_KEY)
public class DeleteKeyListener {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    UserCacheUtil userCacheUtil;

    /**
     * 删除用户缓存信息
     * @param message
     */
    @RabbitHandler
    public void process(String message) {

        userCacheUtil.deleteByKey(message);

    }

}
