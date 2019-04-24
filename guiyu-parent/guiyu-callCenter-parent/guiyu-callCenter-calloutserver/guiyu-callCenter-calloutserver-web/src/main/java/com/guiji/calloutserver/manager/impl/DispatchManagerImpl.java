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
    public void successSchedule(String planUuid, String phoneNo, String intent, Integer userId ,Integer lineId, String tempId, Boolean isNeedPlan) {
        log.info("=========startSchedule:planUuid[{}],phoneNo[{}],intent[{}],lineId[{}]",planUuid,phoneNo,intent,lineId);
        if(StringUtils.isBlank(planUuid)){
            log.info("---startSchedule planUuid is null or intnet is null:planUuid[{}],phoneNo[{}],intent[{}],lineId[{}]",planUuid,phoneNo,intent,lineId);
            return;
        }
        MQSuccPhoneDto mqSuccPhoneDto = new MQSuccPhoneDto();
        mqSuccPhoneDto.setPlanuuid(planUuid);
        if(intent!=null){
            mqSuccPhoneDto.setLabel(intent);
        }
        if(userId!=null){
            mqSuccPhoneDto.setUserId(userId);
        }
        if(lineId!=null){
            mqSuccPhoneDto.setLineId(lineId);
        }
        if(tempId!=null){
            mqSuccPhoneDto.setTempId(tempId);
        }
        rabbitTemplate.convertAndSend("dispatch.SuccessPhoneMQ", JsonUtils.bean2Json(mqSuccPhoneDto)); // 用于判断计划是否完成，可以重复调用
        if(isNeedPlan){
            rabbitTemplate.convertAndSend("dispatch.CallBackEvent", JsonUtils.bean2Json(mqSuccPhoneDto));//这个队列用于控制是否会有下一个计划过来，机器人申请失败无需调用
        }
        log.info("========successSchedule:planUuid[{}],phoneNo[{}],intent[{}]",planUuid,phoneNo,intent);
    }
}
