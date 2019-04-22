package com.guiji.calloutserver.listener;

import com.guiji.callcenter.dao.entity.CallOutDetailRecord;
import com.guiji.callcenter.dao.entity.CallOutRecord;
import com.guiji.calloutserver.service.CallOutDetailRecordService;
import com.guiji.calloutserver.service.CallOutRecordService;
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

import java.math.BigInteger;

/**
 * author:liyang
 * Date:2019/3/25 9:48
 * Description:
 */
@Component
public class UploadFileMqListener {

    private final Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    CallOutRecordService callOutRecordService;
    @Autowired
    CallOutDetailRecordService callOutDetailRecordService;

    /**
     * 监听ROBOT队列消息，目前主要处理：
     * 1、
     * @param message
     */
    @RabbitListener(queues = "record_update_mq")
    @RabbitHandler
    public void process(String message) {
        try {
            PublishBotstenceResultMsgVO publishBotstenceResultMsgVO = JsonUtils.json2Bean(message, PublishBotstenceResultMsgVO.class);

            logger.info("文件上传MQ监听消息{}", publishBotstenceResultMsgVO);

            CallOutRecord callOutRecord = new CallOutRecord();
            callOutRecord.setCallId(new BigInteger("xxxx"));
            callOutRecord.setRecordUrl("xxxxxxxxxxx");
            callOutRecordService.update(callOutRecord);


            CallOutDetailRecord detailRecord = new CallOutDetailRecord();
            detailRecord.setCallId(new BigInteger("xxxxx"));
            detailRecord.setCustomerRecordUrl("xxxxx");
            detailRecord.setAgentRecordUrl("xxxxx");
            detailRecord.setBotRecordUrl("xxxxx");
            callOutDetailRecordService.update(detailRecord);

        }catch (Exception e){
            logger.error("文件上传队列数据出现异常",e);
        }
    }
}

