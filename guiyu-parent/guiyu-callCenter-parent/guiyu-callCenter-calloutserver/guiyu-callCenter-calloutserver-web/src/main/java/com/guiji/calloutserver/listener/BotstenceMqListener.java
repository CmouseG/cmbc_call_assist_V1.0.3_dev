package com.guiji.calloutserver.listener;

import com.guiji.common.model.process.ProcessTypeEnum;
import com.guiji.guiyu.message.model.PublishBotstenceResultMsgVO;
import com.guiji.utils.JsonUtils;
import com.guiji.utils.RedisUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * author:liyang
 * Date:2019/3/25 9:48
 * Description:
 */
@Component
public class BotstenceMqListener {

    private final Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    RedisUtil redisUtil;

    /**
     * 监听ROBOT队列消息，目前主要处理：
     * 1、
     * @param message
     */
    @RabbitListener(queues = "fanoutPublishBotstence.FREESWITCH")
    @RabbitHandler
    public void process(String message) {
        try {
            PublishBotstenceResultMsgVO publishBotstenceResultMsgVO = JsonUtils.json2Bean(message, PublishBotstenceResultMsgVO.class);
            if (publishBotstenceResultMsgVO.getProcessTypeEnum() == ProcessTypeEnum.FREESWITCH) {
                logger.info("接收MQ监听消息{}", publishBotstenceResultMsgVO);
                //话术模板发布，如果在内存中已经有的模板，需要将缓存中模板清空，重新获取
                String tempId = publishBotstenceResultMsgVO.getTmplId();
                logger.info("开始清楚缓存...[{}]", tempId);

                String key = "calloutserver_wavlength_" + tempId;
                redisUtil.del(key);
            }
        }catch (Exception e){
            logger.error("处理队列数据出现异常",e);
        }
    }
}

