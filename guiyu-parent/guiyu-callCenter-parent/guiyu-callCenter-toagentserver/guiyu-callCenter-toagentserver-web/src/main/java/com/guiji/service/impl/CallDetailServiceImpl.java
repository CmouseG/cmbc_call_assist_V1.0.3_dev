package com.guiji.service.impl;

import com.guiji.callcenter.dao.entity.CallInDetail;
import com.guiji.callcenter.dao.entity.CallInDetailRecord;
import com.guiji.callcenter.dao.entity.CallOutDetail;
import com.guiji.callcenter.dao.entity.CallOutDetailRecord;
import com.guiji.entity.CallDetail;
import com.guiji.entity.ECallDirection;
import com.guiji.entity.ExcelData;
import com.guiji.eventbus.SimpleEventSender;
import com.guiji.eventbus.event.UploadRecordEvent;
import com.guiji.service.*;
import com.guiji.util.CommonUtil;
import com.guiji.util.DateUtil;
import com.guiji.util.ExportExcelUtils;
import com.guiji.web.request.DetailsVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @Auther: 魏驰
 * @Date: 2019/1/16 17:32
 * @Project：guiyu-parent
 * @Description:
 */
@Slf4j
@Service
public class CallDetailServiceImpl implements CallDetailService {
    @Autowired
    CallOutDetailService callOutDetailService;

    @Autowired
    CallOutDetailRecordService callOutDetailRecordService;

    @Autowired
    CallInDetailService callInDetailService;

    @Autowired
    CallInDetailRecordService callInDetailRecordService;

    @Autowired
    SimpleEventSender simpleEventSender;

    @Override
    public void insert(CallDetail callDetail, ECallDirection callDirection) {
        log.debug("开始insert, callDirection[{}]", callDirection);

        if(callDirection == ECallDirection.OUTBOUND){
            CallOutDetail callOutDetail = callOutDetailService.insert(callDetail.toCallOutDetail());
            callDetail.setCallDetailId(callOutDetail.getCallDetailId());
            if(callDetail.isUpdateRecord()){
                simpleEventSender.sendEvent(new UploadRecordEvent(callDetail, ECallDirection.OUTBOUND));
            }
        }else if(callDirection == ECallDirection.INBOUND){
            CallInDetail callInDetail = callInDetailService.insert(callDetail.toCallInDetail());
            //callDetail.setCallDetailId(callInDetail.getCallDetailId());
            if(callDetail.isUpdateRecord()){
                simpleEventSender.sendEvent(new UploadRecordEvent(callDetail, ECallDirection.INBOUND));
            }
        }
    }

    public void saveAll(List<CallDetail> callDetails, ECallDirection callDirection) {
        log.debug("开始saveAll, callDirection[{}]", callDirection);

        if(callDirection == ECallDirection.OUTBOUND){
            List<CallOutDetail> callOutDetails = new ArrayList<>(callDetails.size());
            List<CallOutDetailRecord> callOutDetailRecords = new ArrayList<>(callDetails.size());

            for (CallDetail callDetail : callDetails) {
                callOutDetails.add(callDetail.toCallOutDetail());
                if(callDetail.isUpdateRecord()){
                    callOutDetailRecordService.save(callDetail.toCallOutDetailRecord());
                }
            }

            callOutDetailService.insertAll(callOutDetails);
        }else if(callDirection == ECallDirection.INBOUND){
            List<CallInDetail> callInDetails = new ArrayList<>(callDetails.size());
            List<CallInDetailRecord> callInDetailRecords = new ArrayList<>(callDetails.size());

            for (CallDetail callDetail : callDetails) {
                callInDetails.add(callDetail.toCallInDetail());
                if(callDetail.isUpdateRecord()){
                    callInDetailRecordService.save(callDetail.toCallInDetailRecord());
                }
            }

            callInDetailService.saveAll(callInDetails);
        }
    }

    @Override
    public List<CallDetail> findByCallPlanId(String callPlanId, ECallDirection callDirection,Integer orgId) {
        List<CallDetail> callDetails = new ArrayList<>();
        if(callDirection == ECallDirection.OUTBOUND){
            List<CallOutDetail> callOutDetails = callOutDetailService.findByCallPlanId(callPlanId, orgId);
            List<CallOutDetailRecord> callOutDetailRecords = callOutDetailRecordService.findByCallId(new BigInteger(callPlanId));

            //读取录音文件，拼装到结果中
            for (CallOutDetail callOutDetail : callOutDetails) {
                CallDetail mapDetail = new CallDetail();
                BeanUtils.copyProperties(callOutDetail, mapDetail);
                callDetails.add(mapDetail);

                for (CallOutDetailRecord record : callOutDetailRecords) {
                    if(record.getCallDetailId().equals(callOutDetail.getCallDetailId())){
                        BeanUtils.copyProperties(record, mapDetail, CommonUtil.getNullPropertyNames(record));
                        break;
                    }
                }
            }

            return callDetails;
        }else if(callDirection == ECallDirection.INBOUND){
            List<CallInDetail> callInDetails = callInDetailService.findByCallPlanId(callPlanId);
            List<CallInDetailRecord> callInDetailRecords = callInDetailRecordService.findByCallId(new BigInteger(callPlanId));

            //读取录音文件，拼装到结果中
            for (CallInDetail callInDetail : callInDetails) {
                CallDetail mapDetail = new CallDetail();
                BeanUtils.copyProperties(callInDetail, mapDetail);
                callDetails.add(mapDetail);

                for (CallInDetailRecord record : callInDetailRecords) {
                    if(record.getCallDetailId().equals(callInDetail.getCallDetailId())){
                        BeanUtils.copyProperties(record, mapDetail, CommonUtil.getNullPropertyNames(record));
                        break;
                    }
                }
            }
        }

        return callDetails;
    }

    @Override
    public void getExportCalldetails(String callPlanId, HttpServletResponse response, Integer orgId) {
        List<CallOutDetail> callOutDetails = callOutDetailService.findByCallPlanId(callPlanId, orgId);

        List<DetailsVO> detailsVOList = new ArrayList<>();
        for (CallOutDetail callDetail : callOutDetails) {
            DetailsVO detailsVO = new DetailsVO();
            if (!StringUtils.isBlank(callDetail.getBotAnswerText())) {
                detailsVO.setWho("机器人：");
                detailsVO.setSay(callDetail.getBotAnswerText());
                detailsVO.setDate(DateUtil.getStrDate(callDetail.getBotAnswerTime(), DateUtil.FORMAT_YEARMONTHDAY_HOURMINSEC));
            } else if (!StringUtils.isBlank(callDetail.getAgentAnswerText())) {
                detailsVO.setWho("坐席：");
                detailsVO.setSay(callDetail.getAgentAnswerText());
                detailsVO.setDate(DateUtil.getStrDate(callDetail.getAgentAnswerTime(), DateUtil.FORMAT_YEARMONTHDAY_HOURMINSEC));

            } else if (!StringUtils.isBlank(callDetail.getCustomerSayText())) {
                detailsVO.setWho("客户：");
                detailsVO.setSay(callDetail.getCustomerSayText());
                detailsVO.setDate(DateUtil.getStrDate(callDetail.getCustomerSayTime(), DateUtil.FORMAT_YEARMONTHDAY_HOURMINSEC));

            }
            if (detailsVO.getWho() != null) {
                detailsVOList.add(detailsVO);
            }
        }
        Collections.sort(detailsVOList);
        ExcelData data = new ExcelData();
        data.setName("通话详情信息");
        List<String> titles = new ArrayList();
        titles.add("编号");
        titles.add("说话者");
        titles.add("说话内容");
        titles.add("说话时间");
        data.setTitles(titles);
        List<List<Object>> rows = new ArrayList();
        int i = 1;
        for (DetailsVO detailsVO : detailsVOList) {
            List<Object> row = new ArrayList();
            row.add(i);
            row.add(detailsVO.getWho());
            row.add(detailsVO.getSay());
            row.add(detailsVO.getDate());
            rows.add(row);
            i++;
        }
        data.setRows(rows);
        String fileName = "通话详情" + DateUtil.getCurrentDateTimeChina() + ".xlsx";
        ExportExcelUtils.exportExcel(response, fileName, data);
    }
}
