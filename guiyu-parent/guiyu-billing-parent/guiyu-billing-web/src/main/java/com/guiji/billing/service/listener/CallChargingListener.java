package com.guiji.billing.service.listener;

import com.guiji.billing.dto.CallChargingNotifyDto;
import com.guiji.billing.service.ChargingService;
import com.guiji.billing.utils.IdWorker;
import com.guiji.utils.JsonUtils;
import com.rabbitmq.client.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 监听通话计费通知
 */
@Component
public class CallChargingListener {

    private Logger logger = LoggerFactory.getLogger(CallChargingListener.class);

    @Autowired
    private ChargingService chargingService;

    @Autowired
    private IdWorker idWorker;

    @RabbitListener(queues = "billing.ACCTCHARGING")
    @RabbitHandler
    public void process(String message, Channel channel, Message message2) {
        String logId = idWorker.nextId();
        try {
            logger.info("监听通话计费通知- begin:logId:{},入参:{}", logId,  message);
            CallChargingNotifyDto callChargingNotifyDto = JsonUtils.json2Bean(message, CallChargingNotifyDto.class);
            boolean bool = chargingService.charging(callChargingNotifyDto);
            logger.info("监听通话计费通知- end:logId:{},结果:{}", logId, bool);
        }catch(Exception e){
            logger.error("监听接收通话计费处理异常,logId:" + logId, e);
        }
    }
}
