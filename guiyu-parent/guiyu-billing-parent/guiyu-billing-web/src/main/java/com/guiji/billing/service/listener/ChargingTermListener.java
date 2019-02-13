package com.guiji.billing.service.listener;

import com.guiji.billing.dto.ChargingTermNotifyDto;
import com.guiji.billing.entity.BillingAcctChargingTerm;
import com.guiji.billing.service.BillingUserAcctService;
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
 * 监听接收企业用户计费项
 */
@Component
public class ChargingTermListener {

    private Logger logger = LoggerFactory.getLogger(ChargingTermListener.class);

    @Autowired
    private BillingUserAcctService billingUserAcctService;

    @Autowired
    private IdWorker idWorker;

    @RabbitListener(queues = "billing.CHARGINGTERMNOTIFY")
    @RabbitHandler
    public void process(String message) {
        String logId = idWorker.nextId();
        try {
            logger.info("监听接收企业用户计费项- begin:logId:{},入参:{}", logId,  message);
            ChargingTermNotifyDto chargingTermNotifyDto = JsonUtils.json2Bean(message, ChargingTermNotifyDto.class);
            BillingAcctChargingTerm acctTerm = billingUserAcctService.receiveAcctUserChargingTerm(chargingTermNotifyDto);
            logger.info("监听接收企业用户计费项- end:logId:{},结果:{}", logId, JsonUtils.bean2Json(acctTerm));
        }catch(Exception e){
            logger.error("监听接收处理企业用户计费项异常,logId:" + logId, e);
        }
    }

}
