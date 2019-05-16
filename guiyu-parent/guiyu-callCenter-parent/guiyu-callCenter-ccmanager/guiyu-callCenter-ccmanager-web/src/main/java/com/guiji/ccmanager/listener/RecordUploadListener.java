package com.guiji.ccmanager.listener;

import com.guiji.callcenter.dao.CallOutDetailRecordMapper;
import com.guiji.callcenter.dao.CallOutRecordMapper;
import com.guiji.callcenter.dao.entity.CallOutDetailRecord;
import com.guiji.callcenter.dao.entity.CallOutRecord;
import com.guiji.guiyu.message.component.QueueSender;
import com.guiji.nas.vo.AliyunReqVO;
import com.guiji.nas.vo.AliyunRspVO;
import com.guiji.utils.JsonUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigInteger;

/**
 * author:liyang
 * Date:2019/4/23 9:48
 * Description:
 */
@Component
public class RecordUploadListener {

    private final Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    CallOutRecordMapper callOutRecordMapper;
    @Autowired
    CallOutDetailRecordMapper callOutDetailRecordMapper;
    @Autowired
    QueueSender queueSender;

    /**
     * 阿里云上传成功通知队列
     *
     * @param message
     */
    @RabbitListener(queues = "aliyunNoticeQueue")
    @RabbitHandler
    public void process(String message) {
        logger.info("阿里云上传成功Mq，收到消息{}", message);
        try {
            AliyunRspVO aliyunRspVO = JsonUtils.json2Bean(message, AliyunRspVO.class);
            String busId = aliyunRspVO.getBusiId();
            String sourceUrl = aliyunRspVO.getSourceUrl();
            String aliyunUrl = aliyunRspVO.getAliyunUrl();

            if (StringUtils.isNotEmpty(busId) && StringUtils.isNotEmpty(aliyunUrl)) {

                if (busId.startsWith("customer_")) { // 客户说话明细

                    String callDetailId = busId.split("_")[2];
                    CallOutDetailRecord callOutDetailrecord = new CallOutDetailRecord();
                    callOutDetailrecord.setCallDetailId(new BigInteger(callDetailId));
                    callOutDetailrecord.setCustomerRecordUrl(aliyunUrl);
                    callOutDetailRecordMapper.updateByPrimaryKeySelective(callOutDetailrecord);

                } else if (busId.startsWith("agent_")) { //坐席说话

                    String callDetailId = busId.split("_")[2];
                    CallOutDetailRecord callOutDetailrecord = new CallOutDetailRecord();
                    callOutDetailrecord.setCallDetailId(new BigInteger(callDetailId));
                    callOutDetailrecord.setAgentRecordUrl(aliyunUrl);
                    callOutDetailRecordMapper.updateByPrimaryKeySelective(callOutDetailrecord);

                } else { //全程录音
                    BigInteger callId = new BigInteger(busId);
                    CallOutRecord record = new CallOutRecord();
                    record.setCallId(callId);
                    record.setRecordUrl(aliyunUrl);
                    callOutRecordMapper.updateByPrimaryKeySelective(record);
                }
                AliyunReqVO aliyunReqVO = new AliyunReqVO();
                aliyunReqVO.setBusiId(busId);
                aliyunReqVO.setSourceUrl(sourceUrl);
                logger.debug("发送到删除队列 [{}]",aliyunReqVO);
                queueSender.send("nasFileDeleteQueue", JsonUtils.bean2Json(aliyunReqVO));

            }

        } catch (Exception e) {
            logger.error("阿里云上传成功Mq，处理队列数据出现异常", e);
        }
    }
}
