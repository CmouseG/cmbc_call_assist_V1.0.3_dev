package com.guiji.calloutserver.fs;

import com.google.common.base.Strings;
import com.google.common.eventbus.AsyncEventBus;
import com.guiji.callcenter.dao.entity.CallOutPlan;
import com.guiji.calloutserver.constant.Constant;
import com.guiji.calloutserver.enm.EslEventType;
import com.guiji.calloutserver.entity.InnerAsrResponse;
import com.guiji.calloutserver.eventbus.event.*;
import com.guiji.calloutserver.config.FsBotConfig;
import com.guiji.calloutserver.service.CallOutPlanService;
import com.guiji.calloutserver.util.CommonUtil;
import com.guiji.calloutserver.util.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.freeswitch.esl.client.transport.event.EslEvent;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Map;

@Slf4j
@Service
public class FsEventHandler {
    @Autowired
    CallOutPlanService callOutPlanService;

    @Autowired
    AsyncEventBus asyncEventBus;

    @Autowired
    FsBotConfig fsBotConfig;

    @Autowired
    LocalFsServer localFsServer;

    public void handleEvent(EslEvent eslEvent) {
        String eventName = eslEvent.getEventName();
        Map<String, String> eventHeaders = eslEvent.getEventHeaders();
        String subEventName = eventHeaders.get("Event-Subclass");

        if (eventName.equalsIgnoreCase("custom")) {
            eventName = subEventName;
        }

        log.info("收到事件[{}]", eslEvent);

        try {
            EslEventType eventType;
            try {
                eventType = EslEventType.getByValue(eventName);
            } catch (Exception ex) {
                log.info("该事件目前不用处理，跳过" + eventName);
                throw new Exception("invalid event " + eventName);
            }

            if (eventType == EslEventType.EV_ALIASR) {
                log.info("开始处理EV_ALIASR事件[{}]", eslEvent);
                postAliAsrEvent(eventHeaders);
            } else if (eventType == EslEventType.CHANNEL_ANSWER) {
                log.info("开始处理CHANNEL_ANSWER事件[{}]", eslEvent);
                postChannelAnswerEvent(eventHeaders);
            } else if (eventType == EslEventType.CHANNEL_HANGUP_COMPLETE) {
                log.info("开始处理CHANNEL_HANGUP事件[{}]", eslEvent);
                postHangupEvent(eventHeaders);
            } else if (eventType == EslEventType.CALLCENTER_INFO) {
                log.info("开始处理CALLCENTER_INFO事件[{}]", eslEvent);
                postCallCenterEvent(eslEvent);
            } else if (eventType == EslEventType.CHANNEL_PROGRESS_MEDIA || eventType == EslEventType.CHANNEL_PROGRESS) {
                log.info("开始处理CHANNEL_PROGRESS事件[{}]", eslEvent);
                postChannelProgressEvent(eventHeaders);
            }else if (eventType == EslEventType.EV_ALIASR_E) {
                log.info("开始处理EV_ALIASR_E事件[{}]", eslEvent);
                postAliAsrErrorEvent(eventHeaders);
            }
            log.info("事件处理结束");
        } catch (Exception ex) {
            log.warn("处理事件出现异常", ex);
        }
    }

    private void postChannelProgressEvent(Map<String, String> eventHeaders) {
        ChannelProgressEvent event = new ChannelProgressEvent();
        event.setUuid(eventHeaders.get("Unique-ID"));

        log.info("构建好ChannelProgressEvent[{}]，等待后续处理", event);
        asyncEventBus.post(event);
    }

    private void postCallCenterEvent(EslEvent eslEvent) {
        Map<String, String> eventHeaders = eslEvent.getEventHeaders();
        String action = eventHeaders.get("CC-Action");
        if (action.equals("bridge-agent-start")) {    //座席应答事件
            AgentAnswerEvent event = new AgentAnswerEvent();
            event.setAgentId(eventHeaders.get("CC-Agent"));
            event.setAgentUuid(eventHeaders.get("CC-Agent-UUID"));
            event.setAgentAnswerTime(DateUtil.getCurrentDateTime());
            event.setCustomerNum(eventHeaders.get("CC-Member-CID-Number"));
            event.setCustomerUuid(eventHeaders.get("CC-Member-Session-UUID"));
            event.setAgentGroupId(eventHeaders.get("CC-Queue"));
            log.info("构建好AgentAnswerEvent[{}], 等待后续处理", event);
            asyncEventBus.post(event);
        }
    }

    private void postChannelAnswerEvent(Map<String, String> eventHeaders) {
        ChannelAnswerEvent event = new ChannelAnswerEvent();
        event.setUuid(eventHeaders.get("Unique-ID"));
        event.setCallerNum(eventHeaders.get("Caller-Caller-ID-Number"));
        event.setCalledNum(eventHeaders.get("Caller-Destination-Number"));
        event.setAccessNum(eventHeaders.get("variable_AccessNum"));

        log.info("构建好ChannelAnswerEvent[{}]，等待后续处理", event);
        asyncEventBus.post(event);
    }


    private void postHangupEvent(Map<String, String> eventHeaders) {

        Date startStamp = DateUtil.getDateByDateAndFormat(eventHeaders.get("variable_start_stamp"), DateUtil.FORMAT_YEARMONTHDAY_HOURMINSEC);
        Date endStamp = DateUtil.getDateByDateAndFormat(eventHeaders.get("variable_end_stamp"), DateUtil.FORMAT_YEARMONTHDAY_HOURMINSEC);

        Date answerStamp = null;
        String answerTime = eventHeaders.get("variable_answer_stamp");
        if(!Strings.isNullOrEmpty(answerTime)){
            answerStamp = DateUtil.getDateByDateAndFormat(answerTime, DateUtil.FORMAT_YEARMONTHDAY_HOURMINSEC);
        }
//        Date progressStamp = DateUtil.getDateByDateAndFormat(eventHeaders.get("variable_progress_media_stamp"), DateUtil.FORMAT_YEARMONTHDAY_HOURMINSEC);

        ChannelHangupEvent event = ChannelHangupEvent.builder()
                .uuid(eventHeaders.get("Unique-ID"))
                .billSec(Integer.parseInt(eventHeaders.get("variable_billsec")))
                .duration(Integer.parseInt(eventHeaders.get("variable_duration")))
                .startStamp(startStamp)
//                .progressStamp(progressStamp)
                .answerStamp(answerStamp)
                .hangupStamp(endStamp)
                .hangupDisposition(eventHeaders.get("variable_sip_hangup_disposition"))
                .sipHangupCause(eventHeaders.get("variable_sip_term_status"))
                .rtpAudioInMediaPacketCount(eventHeaders.get("rtp_audio_in_media_packet_count"))
                .build();

        log.info("构建好ChannelHangupEvent[{}], 等待后续处理", event);
        asyncEventBus.post(event);
    }


    private void postAliAsrEvent(Map<String, String> eventHeaders) {
        AsrBaseEvent event = new AsrBaseEvent();
        event.setAnswered(eventHeaders.get("Answered"));
        event.setChannel(eventHeaders.get("Channel"));
        event.setTimestamp(eventHeaders.get("Timestamp"));

        String fileName = eventHeaders.get("FileName");
        if (!Strings.isNullOrEmpty(fileName)) {
            fileName = fileName.trim();
            event.setFileName(fileName);
            event.setFilePath(fsBotConfig.getRecordingsDir() + fileName);
        }

        InnerAsrResponse innerAsrResponse = CommonUtil.jsonToJavaBean(eventHeaders.get("ASR-Response"), InnerAsrResponse.class);
        //event.setInnerAsrResponse(innerAsrResponse);
        if (innerAsrResponse != null) {
            event.setAsrText(innerAsrResponse.getAsrText());
            event.setAsrStartTime(innerAsrResponse.getBeginTime());
            event.setAsrEndTime(innerAsrResponse.getEndTime());
            event.setAsrDuration(innerAsrResponse.getDuration());
        }

        String uuidLong = eventHeaders.get("UUID");
        String uuid;
        Integer orgId;
        BigInteger bigIntegerId = null;
        try {
            String[] arr = uuidLong.split(Constant.UUID_SEPARATE);
            uuid = arr[0];
            orgId = Integer.valueOf(arr[1]);
            bigIntegerId = new BigInteger(uuid);
        }catch (Exception e){
            return;
        }

        CallOutPlan callPlan = callOutPlanService.findByCallId(bigIntegerId, orgId);
        if (callPlan != null) {
            event.setUuid(uuidLong);
            AsrCustomerEvent asrCustomerEvent = new AsrCustomerEvent();
            BeanUtils.copyProperties(event, asrCustomerEvent);
            asrCustomerEvent.setCallPlan(callPlan);
            log.warn("[{},{},{},{}]", callPlan.getPhoneNum(),Constant.MODULE_CALLOUTSERVER, "收到asr事件", event);
            asyncEventBus.post(asrCustomerEvent);
//            event.setUuid(callPlan.getAgentChannelUuid());
        } else {
            log.warn("收到的Asr事件不属于当前系统(没有根据agentChannelUuid查到计划)，跳过处理，eventHeaders:[{}]", eventHeaders);
        }
    }

    private void postAliAsrErrorEvent(Map<String, String> eventHeaders) {
        //{"header":{"namespace":"Default","name":"TaskFailed","status":40000005,"message_id":"40a3ea36b8ec48d594c9f526719fabbe",
        //"task_id":"8baa37799e4746249e850bead2f62e4a","status_text":"Gateway:TOO_MANY_REQUESTS:Too many requests!"}}

        log.info("收到asr错误事件 [{}]",eventHeaders);
        String uuid = eventHeaders.get("UUID");
        Integer orgId;
        BigInteger callId = null;
        try {
            String[] arr = uuid.split(Constant.UUID_SEPARATE);
            callId = new BigInteger(arr[0]);
            orgId = Integer.valueOf(arr[1]);
        }catch (Exception e){
            return;
        }

        String status = eventHeaders.get("status");
        String reason = null;
        if(status.equals("40000001")){
            reason = "ASR异常4001";
        }else if(status.equals("40000002")){
            reason = "ASR异常4002";
        }else if(status.equals("40000003")){
            reason = "ASR异常4003";
        }else if(status.equals("40000004")){
            reason = "ASR异常4004";
        }else if(status.equals("40000005")){
            reason = "ASR异常4005";
        }else if(status.equals("40020105")){
            reason = "ASR异常4006";
        }
        if(reason!=null){
            log.info("asr错误识别 callId[{}],reason[{}]",callId,reason);
            localFsServer.hangup(uuid); //挂断电话
            CallOutPlan callOutPlan = new CallOutPlan();
            callOutPlan.setCallId(callId);
            callOutPlan.setOrgId(orgId);
            callOutPlan.setAccurateIntent("N");
            callOutPlan.setReason(reason);
            callOutPlanService.update(callOutPlan);
        }
    }
}
