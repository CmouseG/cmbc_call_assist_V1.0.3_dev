package com.guiji.listener;

import com.guiji.cache.UserCacheUtil;
import com.guiji.cfg.ThirdApiMqConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @version V1.0
 * @ClassName: DeleteUserCacheListener
 * @Description: 删除缓存中用户信息
 */
@Component
@RabbitListener(queues = ThirdApiMqConfig.NOTIFY_UPDATE_USER)
public class DeleteUserCacheListener {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    UserCacheUtil userCacheUtil;

    /**
     * 删除缓存中用户信息
     * @param message
     */
    @RabbitHandler
    public void process(String message) {

        userCacheUtil.deleteByUserId(Long.valueOf(message));

    }

}
