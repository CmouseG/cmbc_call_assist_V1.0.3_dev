package com.guiji.calloutserver.controller;

import com.google.common.eventbus.AsyncEventBus;
import com.guiji.callcenter.dao.CallOutPlanMapper;
import com.guiji.callcenter.dao.entity.CallOutPlan;
import com.guiji.callcenter.dao.entity.CallOutPlanExample;
import com.guiji.calloutserver.enm.ECallDirection;
import com.guiji.calloutserver.enm.ECallState;
import com.guiji.calloutserver.eventbus.event.*;
import com.guiji.calloutserver.eventbus.handler.CallResourceChecker;
import com.guiji.calloutserver.fs.FsEventHandler;
import com.guiji.calloutserver.manager.EurekaManager;
import com.guiji.calloutserver.service.CallOutPlanService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigInteger;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * @Auther: 魏驰
 * @Date: 2018/11/23 14:21
 * @Project：guiyu-parent
 * @Description:
 */

/**
 * 用于插入测试数据，发起外呼
 */
@Slf4j
@RestController
public class TestController {
    @Autowired
    CallOutPlanService callOutPlanService;

    @Autowired
    EurekaManager eurekaManager;

    @Autowired
    AsyncEventBus asyncEventBus;

    @Autowired
    FsEventHandler fsEventHandler;
    @Autowired
    CallResourceChecker callResourceChecker;
    @Autowired
    CallOutPlanMapper callOutPlanMapper;


    @GetMapping("/testcall")
    public CallOutPlan testcall(){

        CallOutPlanExample example = new CallOutPlanExample();
        CallOutPlanExample.Criteria criteria = example.createCriteria();
        //转人工对话可能会比较长，不包括转人工的状态
        criteria.andCallStateLessThanOrEqualTo(ECallState.to_agent.ordinal());

        Calendar c = Calendar.getInstance();
        c.add(Calendar.MINUTE, -7);
        Date endTime  = c.getTime();

        criteria.andCreateTimeLessThan(endTime);
        log.info("------>>>endTime[{}]", endTime);

        List<CallOutPlan> list = callOutPlanMapper.selectByExample(example);

        System.out.println(list.size());

        return null;
    }

    @GetMapping("/testAsr")
    public void testAsr() {

        CallOutPlan callPlan = callOutPlanService.findByCallId(new BigInteger("287619318777118720"), 1);
        if (callPlan != null) {

            log.info("构建好ALI_ASR事件[{}]，等待后续处理");
            AsrCustomerEvent asrCustomerEvent = new AsrCustomerEvent();
            asrCustomerEvent.setUuid("287619318777118720");
            asrCustomerEvent.setAsrText("什么价格？");
            asrCustomerEvent.setTimestamp("20181227093617817093");
            asrCustomerEvent.setFilePath("/Users/toolwiz.com/freeswitch/fsbot/recordings/13382992370@172.16.167.214:51601-20181227093617817093-1208320.wav");
            asrCustomerEvent.setFileName("13382992370@172.16.167.214:51601-20181227093617817093-1208320.wav");
            asrCustomerEvent.setAnswered("true");
            asrCustomerEvent.setAsrStartTime("1970-01-01 08:01:13");
            asrCustomerEvent.setAsrEndTime("1970-01-01 08:01:14");
            asrCustomerEvent.setAsrDuration(1330l);
            asrCustomerEvent.setGenerated(false);
            asyncEventBus.post(asrCustomerEvent);
        } else {
            log.warn("收到的Asr事件不属于当前系统(没有根据agentChannelUuid查到计划)，跳过处理，eventHeaders:[{}]");
        }
    }

    @GetMapping("/testChannelAnswer")
    public void testChannelAnswer() {

        ChannelAnswerEvent event = new ChannelAnswerEvent();
        event.setUuid("287619318777118720");
        event.setCallerNum("18600397859");
        event.setCalledNum("110");
        event.setAccessNum("112");

        log.info("构建好ChannelAnswerEvent[{}]，等待后续处理", event);
        asyncEventBus.post(event);
    }

    @GetMapping("/testHangUp")
    public void testHangUp() {

        ChannelHangupEvent event = ChannelHangupEvent.builder()
                .uuid("287619318777118720")
                .billSec(60)
                .duration(60)
                .startStamp(new Date())
                .answerStamp(new Date())
                .hangupStamp(new Date())
                .hangupDisposition("send_bye")
                .sipHangupCause("200")
                .build();

        log.info("构建好ChannelHangupEvent[{}], 等待后续处理", event);
        asyncEventBus.post(event);
    }
}
