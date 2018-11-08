package com.guiji.calloutserver.eventbus.handler;

import com.google.common.base.Preconditions;
import com.google.common.eventbus.Subscribe;
import com.guiji.callcenter.dao.entity.CallOutDetailRecord;
import com.guiji.callcenter.dao.entity.CallOutPlan;
import com.guiji.callcenter.dao.entity.CallOutRecord;
import com.guiji.calloutserver.eventbus.event.AfterCallEvent;
import com.guiji.calloutserver.service.CallOutDetailRecordService;
import com.guiji.calloutserver.service.CallOutRecordService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class AfterCallHandler {
    @Autowired
    CallOutDetailRecordService callOutDetailRecordService;

    @Autowired
    CallOutRecordService callOutRecordService;

    @Subscribe
    public void handleAfterCall(AfterCallEvent afterCallEvent){
        try{
            CallOutPlan callPlan = afterCallEvent.getCallPlan();
            Preconditions.checkArgument(callPlan!=null, "null callPlan");

            //上传呼叫时长不为空的文件
            if(callPlan.getDuration()>0){
                //调用fsagent上传文件
                CallOutRecord callRecord = callOutRecordService.findByCallId(callPlan.getCallId());
                uploadMainRecord(callRecord);

                List<CallOutDetailRecord> callOutDetailRecords = callOutDetailRecordService.findByCallId(callPlan.getCallId());
                uploadDetailsRecord(callOutDetailRecords);
            }
        }catch (Exception ex){
            //TODO: 报警，上传录音文件失败
            log.warn("在处理呼叫结果时出现异常", ex);
        }
    }

    /**
     * 上传通话各个环节录音
     * @param callOutDetailRecords
     */
    private void uploadDetailsRecord(List<CallOutDetailRecord> callOutDetailRecords) {

    }

    /**
     * 上传主录音文件
     * @param callRecord
     */
    private void uploadMainRecord(CallOutRecord callRecord) {
    }
}
