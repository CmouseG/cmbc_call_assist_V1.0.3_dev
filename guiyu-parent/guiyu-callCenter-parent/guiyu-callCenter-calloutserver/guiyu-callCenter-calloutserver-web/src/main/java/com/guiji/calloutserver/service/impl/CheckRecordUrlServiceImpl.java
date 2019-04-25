package com.guiji.calloutserver.service.impl;

import com.guiji.callcenter.dao.CallOutPlanMapper;
import com.guiji.callcenter.dao.entity.CallOutDetailRecord;
import com.guiji.callcenter.dao.entity.CallOutRecord;
import com.guiji.calloutserver.manager.EurekaManager;
import com.guiji.calloutserver.manager.FsAgentManager;
import com.guiji.calloutserver.service.CheckRecordUrlService;
import com.guiji.calloutserver.util.DateUtils;
import com.guiji.fsagent.entity.RecordType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * author:liyang
 * Date:2019/4/24 15:51
 * Description:
 */
@Service
@Slf4j
public class CheckRecordUrlServiceImpl implements CheckRecordUrlService {

    @Autowired
    CallOutPlanMapper callOutPlanMapper;
    @Autowired
    FsAgentManager fsAgentManager;
    @Autowired
    EurekaManager eurekaManager;

    @Override
    public void checkRecordUrl() {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        Date startDate = DateUtils.getDaysAgo(3); //3天之前
        Date endDate = DateUtils.getHoursAgoDate(5); //5个小时之前

        String startTime = sdf.format(startDate);
        String endTime = sdf.format(endDate);
        String serverId = eurekaManager.getInstanceId();


        log.info("查询未上传的全程录音，startTime[{}], endTime[{}]", startTime, endTime);

        List<CallOutRecord> list = callOutPlanMapper.getUnuploadCall(startTime, endTime, serverId);

        if (list != null && list.size() > 0) {
            log.info("查询未上传的录音，list大小[{}]", list.size());

            for (CallOutRecord callOutRecord : list) {
                BigInteger callId = callOutRecord.getCallId();
                fsAgentManager.uploadRecord(callId.toString(), callId.toString(), callOutRecord.getRecordFile(), "mainrecord",
                        null, RecordType.TOTAL_RECORD);
                //查询明细
                List<CallOutDetailRecord> detailList = callOutPlanMapper.getUnuploadDetailByCallId(callId);
                if (detailList != null && detailList.size() > 0) {
                    for (CallOutDetailRecord callOutDetailRecord : detailList) {

                        BigInteger callDetailId = callOutDetailRecord.getCallDetailId();
                        if (callOutDetailRecord.getCustomerRecordFile() != null && callOutDetailRecord.getCustomerRecordUrl() == null) {
                            String busiId = "customer_" + callId + "_" + callDetailId;
                            fsAgentManager.uploadRecord(callDetailId.toString(),
                                    busiId, callOutDetailRecord.getCustomerRecordFile(), "detailrecord", null, RecordType.CUSTOMER_RECORD);
                        } else if (callOutDetailRecord.getAgentRecordFile() != null && callOutDetailRecord.getAgentRecordUrl() == null) {
                            String busiId = "agent_" + callId + "_" + callDetailId;
                            fsAgentManager.uploadRecord(callDetailId.toString(),
                                    busiId, callOutDetailRecord.getAgentRecordFile(), "detailrecord", null, RecordType.AGENT_RECORD);
                        }

                    }
                }
            }
        }
    }



}
