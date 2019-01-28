package com.guiji.eventbus.handler;

import com.google.common.base.Strings;
import com.google.common.eventbus.AllowConcurrentEvents;
import com.google.common.eventbus.Subscribe;
import com.guiji.entity.CallDetail;
import com.guiji.entity.CallPlan;
import com.guiji.entity.ECallDirection;
import com.guiji.eventbus.SimpleEventSender;
import com.guiji.eventbus.event.UploadRecordEvent;
import com.guiji.fsagent.entity.RecordVO;
import com.guiji.manager.EurekaManager;
import com.guiji.manager.FsAgentManager;
import com.guiji.service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @Auther: 魏驰
 * @Date: 2019/1/24 20:51
 * @Project：guiyu-parent
 * @Description: 用于上传录音，并将录音信息保存到数据库中
 */
@Slf4j
@Component
public class CallRecordHandler {
    @Autowired
    EurekaManager eurekaManager;

    @Autowired
    FsAgentManager fsAgentManager;

    @Autowired
    CallPlanService callPlanService;

    @Autowired
    CallOutDetailRecordService callOutDetailRecordService;

    @Autowired
    CallInDetailRecordService callInDetailRecordService;

    @Autowired
    SimpleEventSender simpleEventSender;

    @PostConstruct
    public void init(){
        simpleEventSender.register(this);
    }

    @AllowConcurrentEvents
    @Subscribe
    public void handleCallRecord(UploadRecordEvent uploadRecordEvent){
        CallDetail callDetail = uploadRecordEvent.getCallDetail();
        log.info("开始处理录音文件，callDetail[{}]", callDetail);

        CallPlan callPlan = callPlanService.findByCallId(callDetail.getCallId().toString(), uploadRecordEvent.getCallDirection());
        uploadDetailsRecord(callDetail, uploadRecordEvent.getCallDirection(), Long.valueOf(callPlan.getCustomerId()));
    }

    /**
     * 上传通话各个环节录音
     * @param callDirection
     * @param userId
     */
    public void uploadDetailsRecord(CallDetail callDetail, ECallDirection callDirection, Long userId) {
        String busiType = "detailrecord";
        log.info("开始上传客户录音,callId[{}][{}]", callDetail.getCallId(), callDetail.getCallDetailId());
        //上传客户说话录音
        if (!Strings.isNullOrEmpty(callDetail.getCustomerRecordFile())) {
            String fileId = "customer_" + callDetail.getCallId() + "_" + callDetail.getCallDetailId();
            RecordVO recordVO = fsAgentManager.uploadRecord(fileId, callDetail.getCustomerRecordFile(), busiType, userId);
            log.info("上传1客户说话录音[{}][{}]，返回结果为[{}]", fileId, callDetail.getCustomerRecordFile(), recordVO);
            callDetail.setCustomerRecordUrl(recordVO.getFileUrl());
        }else{
            log.info("忽略1客户说话录音，因文件为空");
        }

        log.info("开始上传座席录音， callId[{}][{}]", callDetail.getCallId(), callDetail.getCallDetailId());
        //上传座席说话录音
        if (!Strings.isNullOrEmpty(callDetail.getAgentRecordFile())) {
            String fileId = "agent_" + callDetail.getCallId() + "_" + callDetail.getCallDetailId();
            RecordVO recordVO = fsAgentManager.uploadRecord(fileId, callDetail.getAgentRecordFile(), busiType, userId);
            log.info("上传座席说话录音[{}][{}]，返回结果为[{}]", fileId, callDetail.getAgentRecordFile(), recordVO);
            callDetail.setAgentRecordUrl(recordVO.getFileUrl());
        }

        if(callDirection == ECallDirection.OUTBOUND){
            callOutDetailRecordService.save(callDetail.toCallOutDetailRecord());
        }else{
            callInDetailRecordService.save(callDetail.toCallInDetailRecord());
        }
    }
}
