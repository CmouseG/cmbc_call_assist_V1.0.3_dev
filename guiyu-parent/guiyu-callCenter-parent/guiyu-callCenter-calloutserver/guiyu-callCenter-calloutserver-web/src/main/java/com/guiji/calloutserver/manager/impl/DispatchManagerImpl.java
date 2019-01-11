package com.guiji.calloutserver.manager.impl;

import com.guiji.calloutserver.entity.MQSuccPhoneDto;
import com.guiji.calloutserver.manager.DispatchManager;
import com.guiji.utils.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Auther: 魏驰
 * @Date: 2018/11/4 17:19
 * @Project：guiyu-parent
 * @Description:
 */
@Slf4j
@Service
public class DispatchManagerImpl implements DispatchManager {

    @Autowired
    private AmqpTemplate rabbitTemplate;

    /**
     *  回掉调度中心结果
     */
    @Override
    public void successSchedule(String callId, String phoneNo, String intent) {
        log.info("=========startSchedule:callId[{}],phoneNo[{}],intent[{}]",callId,phoneNo,intent);
        if(StringUtils.isBlank(callId) || StringUtils.isBlank(intent)){
            log.info("---startSchedule callid is null or intnet is null:callId[{}],phoneNo[{}],intent[{}]",callId,phoneNo,intent);
            return;
        }
        MQSuccPhoneDto mqSuccPhoneDto = new MQSuccPhoneDto();
        mqSuccPhoneDto.setPlanuuid(callId);
        if(intent!=null){
            mqSuccPhoneDto.setLabel(intent);
        }
        rabbitTemplate.convertAndSend("dispatch.SuccessPhoneMQ", JsonUtils.bean2Json(mqSuccPhoneDto));
        rabbitTemplate.convertAndSend("dispatch.CallBackEvent", JsonUtils.bean2Json(mqSuccPhoneDto));
        log.info("========successSchedule:callId[{}],phoneNo[{}],intent[{}]",callId,phoneNo,intent);
    }
}
