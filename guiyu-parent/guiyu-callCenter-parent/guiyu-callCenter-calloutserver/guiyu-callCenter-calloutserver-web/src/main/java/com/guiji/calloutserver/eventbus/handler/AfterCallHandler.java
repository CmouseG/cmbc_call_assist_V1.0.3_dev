package com.guiji.calloutserver.eventbus.handler;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.google.common.eventbus.Subscribe;
import com.guiji.callcenter.dao.entity.CallOutDetailRecord;
import com.guiji.callcenter.dao.entity.CallOutPlan;
import com.guiji.callcenter.dao.entity.CallOutRecord;
import com.guiji.calloutserver.eventbus.event.AfterCallEvent;
import com.guiji.calloutserver.manager.FsAgentManager;
import com.guiji.calloutserver.service.CallOutDetailRecordService;
import com.guiji.calloutserver.service.CallOutRecordService;
import com.guiji.dispatch.api.IDispatchPlanOut;
import com.guiji.fsagent.entity.RecordVO;
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

    @Autowired
    FsAgentManager fsAgentManager;

    @Autowired
    IDispatchPlanOut  dispatchPlanOut;

    @Subscribe
    public void handleAfterCall(AfterCallEvent afterCallEvent) {
        try {
            CallOutPlan callPlan = afterCallEvent.getCallPlan();
            Preconditions.checkArgument(callPlan != null, "null callPlan");

            //调度中心
            dispatchPlanOut.successSchedule(callPlan.getCallId());

            //上传呼叫时长不为空的文件
            if (callPlan.getDuration() > 0) {
                //调用fsagent上传主录音文件
                CallOutRecord callRecord = callOutRecordService.findByCallId(callPlan.getCallId());
                uploadMainRecord(callRecord);

                List<CallOutDetailRecord> callOutDetailRecords = callOutDetailRecordService.findByCallId(callPlan.getCallId());
                uploadDetailsRecord(callOutDetailRecords);
            }
        } catch (Exception ex) {
            //TODO: 报警，上传录音文件失败
            log.warn("在处理呼叫结果时出现异常", ex);
        }
    }

    /**
     * 上传主录音文件
     *
     * @param callOutRecord
     */
    private void uploadMainRecord(CallOutRecord callOutRecord) {
        RecordVO recordVO = fsAgentManager.uploadRecord(callOutRecord.getCallId(), callOutRecord.getRecordFile(), "mainrecord");
        callOutRecord.setRecordUrl(recordVO.getFileUrl());
        callOutRecordService.save(callOutRecord);
    }

    /**
     * 上传通话各个环节录音
     *
     * @param callOutDetailRecords
     */
    private void uploadDetailsRecord(List<CallOutDetailRecord> callOutDetailRecords) {
        String busiType = "detailrecord";
        boolean isEdit = false;
        for (CallOutDetailRecord detailRecord : callOutDetailRecords) {
            //上传客户说话录音
            if (!Strings.isNullOrEmpty(detailRecord.getCustomerRecordFile())) {
                String fileId = "customer_" + detailRecord.getCallId() + "_" + detailRecord.getCallDetailId();
                RecordVO recordVO = fsAgentManager.uploadRecord(fileId, detailRecord.getCustomerRecordFile(), busiType);
                detailRecord.setCustomerRecordUrl(recordVO.getFileUrl());
                isEdit = true;
            }

            //上传座席说话录音
            if (!Strings.isNullOrEmpty(detailRecord.getAgentRecordFile())) {
                String fileId = "agent_" + detailRecord.getCallId() + "_" + detailRecord.getCallDetailId();
                RecordVO recordVO = fsAgentManager.uploadRecord(fileId, detailRecord.getAgentRecordFile(), busiType);
                detailRecord.setAgentRecordUrl(recordVO.getFileUrl());
                isEdit = true;
            }

            if (isEdit) {
                callOutDetailRecordService.save(detailRecord);
            }
        }
    }
}
