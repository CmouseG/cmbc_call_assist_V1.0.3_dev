package com.guiji.robot.service.listener;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.guiji.robot.cfg.RobotMqConfig;
import com.guiji.robot.dao.entity.TtsCallbackHis;
import com.guiji.robot.model.TtsCallback;
import com.guiji.robot.service.ITtsWavService;
import com.guiji.robot.service.impl.AiNewTransService;
import com.guiji.utils.BeanUtil;
import com.guiji.utils.JsonUtils;
import com.rabbitmq.client.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RabbitListener(queues = RobotMqConfig.TTS_CALLBACK_QUEUE, containerFactory = "batchImportRabbitFactory")
public class TtsCallbackListener {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    AiNewTransService aiNewTransService;

    @Autowired
    ITtsWavService iTtsWavService;

    @RabbitHandler
    public void process(String message, Channel channel, Message message2) {
        try {
            TtsCallback ttsCallback = JsonUtils.json2Bean(message, TtsCallback.class);
            TtsCallbackHis ttsCallbackHis = new TtsCallbackHis();
            BeanUtil.copyProperties(ttsCallback, ttsCallbackHis);
            if(ttsCallback.getAudios()!=null) {
                //将消息转为JSON报文
                String jsonData = JSON.toJSONString(ttsCallback.getAudios());
                ttsCallbackHis.setTtsJsonData(jsonData);
            }
            //新开事务保存
            aiNewTransService.recordTtsCallback(ttsCallbackHis);
            //异步处理TTS数据
            iTtsWavService.syncTtsCallBack(Lists.newArrayList(ttsCallback));
            logger.info("接收TTS-业务ID[{}]回调，数据落地完成..", ttsCallback.getBusiId());
        } catch (Exception e) {
            //这次消息，我已经接受并消费掉了，不会再重复发送消费
            logger.info("error",e);
            try {
                logger.info("robot-tts合成-callback消费的数据有问题---------------------------------------"+message);
                channel.basicAck(message2.getMessageProperties().getDeliveryTag(), false);
            } catch (IOException e1) {
                logger.info("已经接受并消费掉了，不会再重复发送消费有问题了");
            }
        }
    }


}
