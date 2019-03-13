package com.guiji.botsentence.message.listener;

import com.alibaba.fastjson.JSON;
import com.guiji.botsentence.constant.SpeechMqConstant;
import com.guiji.botsentence.message.SpeechAuditMessage;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class SpeechAuditListener {

    private Logger logger = LoggerFactory.getLogger(SpeechAuditListener.class);

    @Resource
    private com.guiji.botsentence.service.IKeywordsAuditService IKeywordsAuditService;

    @RabbitListener(queues = SpeechMqConstant.SPEECH_AUDIT_QUEUE)
    public void handleMessage(String message){

        logger.info("handle speech audit message:{}", message);

        if(StringUtils.isBlank(message)){
            return;
        }

        try{
            SpeechAuditMessage auditMessage = JSON.parseObject(message, SpeechAuditMessage.class);
            IKeywordsAuditService.initiateKeywordsAudit(auditMessage.getProcessId(), auditMessage.getUserId());
        }catch (Exception e){
            logger.error("handle speech audit error!", e);
        }
    }
}
