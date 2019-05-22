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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @version V1.0
 * @ClassName: ThirdApiMqListener
 * @Description: 异步回调信息监听
 */
@Component
@RabbitListener(queues = ThirdApiMqConfig.NOTIFY_QUEUE)
public class ThirdApiMqListener {

    @Autowired
    RedisDelayService redisDelayService;

    @Autowired
    UserCacheUtil userCacheUtil;


    private final Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 监听ROBOT队列消息，目前主要处理：
     * 1、
     *
     * @param message
     */
    @RabbitHandler
    public void process(String message) {

        try {
            MqMessage mqMessage = JsonUtils.json2Bean(message, MqMessage.class);

            DelayMessage msg = getMsg(mqMessage);

            redisDelayService.putToQueue(msg);
        } catch (Exception ex) {
            logger.info("accept message: {}", message);
            logger.error("error message: {}", ex.getMessage());
        }



    }

    private DelayMessage getMsg(MqMessage mqMessage) {

        Long userId = mqMessage.getUserId();

        ThirdApiUser apiUser = userCacheUtil.getUserInfoByUserId(userId);

        GenericResponse response = new GenericResponse();

        response.setSuccess(ThirdApiExceptionEnum.SUCCESS.getSuccess());
        response.setCode(0);
        response.setMsg(ThirdApiExceptionEnum.SUCCESS.getMsg());
        response.setBody(mqMessage.getBody());

        SignRo signRo = new SignRo();

        signRo.setParams(BeanUtil.bean2Map(response));
        signRo.setPrivateKey(apiUser.getClientSecret());

        response.setSign(Md5Utils.sign(signRo));

        DelayMessage delayMessage = new DelayMessage();

        delayMessage.setBody(JsonUtils.bean2Json(response));
        delayMessage.setNotifyUrl(mqMessage.getNotifyUrl());

        return delayMessage;
    }
}
