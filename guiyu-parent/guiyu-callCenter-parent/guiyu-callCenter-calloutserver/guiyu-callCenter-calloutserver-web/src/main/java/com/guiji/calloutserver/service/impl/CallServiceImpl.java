package com.guiji.calloutserver.service.impl;

import com.guiji.callcenter.dao.entity.CallOutPlan;
import com.guiji.calloutserver.config.AliAsrConfig;
import com.guiji.calloutserver.constant.Constant;
import com.guiji.calloutserver.eventbus.handler.CallPlanDispatchHandler;
import com.guiji.calloutserver.fs.LocalFsServer;
import com.guiji.calloutserver.manager.CallLineAvailableManager;
import com.guiji.calloutserver.manager.FsLineManager;
import com.guiji.calloutserver.service.CallService;
import com.guiji.component.result.Result;
import com.guiji.dict.api.ISysDict;
import com.guiji.dict.vo.SysDictVO;
import com.guiji.fsmanager.entity.FsLineInfoVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @Auther: 魏驰
 * @Date: 2018/11/4 17:12
 * @Project：guiyu-parent
 * @Description:
 */
@Slf4j
@Service
public class CallServiceImpl implements CallService {
    @Autowired
    AliAsrConfig aliAsrConfig;

    @Autowired
    FsLineManager fsLineManager;

    @Autowired
    LocalFsServer fsManager;
    @Autowired
    CallLineAvailableManager callLineAvailableManager;
    @Autowired
    ISysDict iSysDict;
    @Autowired
    CallPlanDispatchHandler callPlanDispatchHandler;

    ScheduledExecutorService makecallScheduledExecutor = Executors.newScheduledThreadPool(20);

    /**
     * 调用底层FreeSWITCH接口，发起外呼
     */
    @Override
    public void makeCall(CallOutPlan callplan, String recordFile) {
        //获取线路服务器
        FsLineInfoVO fsLine = null;
        try {
            fsLine = fsLineManager.getFsLine(callplan.getLineId());
        }catch (Exception e){
            log.error("获取呼叫线路出现异常",e);
            callPlanDispatchHandler.readyFail(callplan,"605",false);
            return;
        }
        if(fsLine==null){
            log.error("获取呼叫线路出现异常");
            callPlanDispatchHandler.readyFail(callplan,"605",false);
            return;
        }
        String codec = fsLine.getCodec();

        String ip = fsLine.getFsIp();
        if(ip.contains(":")){
            ip = ip.split(":")[0];
        }
        String callid = callplan.getCallId().toString();
        Integer orgId = callplan.getOrgId();
        //构建外呼命令
        String cmd = String.format("originate {" +
                        (StringUtils.isNotEmpty(codec) ? "absolute_codec_string="+codec+"," : "") +
                        "origination_uuid=%s,origination_caller_id_name=%s}" +
                        "sofia/internal/%s@%s:%s 'start_asr:%s %s" +
                        ", record_session:/usr/local/freeswitch/recordings/%s, park' inline",
                callid+Constant.UUID_SEPARATE+orgId,
                callplan.getLineId(),
                callplan.getPhoneNum(),
                ip,
                fsLine.getFsInPort(),
                aliAsrConfig.getAccessId(),
                aliAsrConfig.getAccessSecret(),
                recordFile);

        log.info("执行呼叫命令前sleep,callId[{}]",callid);
        synchronized (this){
            try {
                Thread.sleep(50);
            } catch (Exception e) {
               log.error("makeCall线程sleep出现异常",e);
            }
        }
        log.info("开始..执行呼叫命令[{}]", cmd);
        fsManager.executeAsync(cmd);

        Result.ReturnData<List<SysDictVO>> returnData = iSysDict.getDictValueByTypeKey("bell_time","bell_time");
        String value = returnData.getBody().get(0).getDictValue();
        makecallScheduledExecutor.schedule(() -> {
            log.info("时间已到，去检查电话是否已经接听,callId[{}]",callid);
            if(!callLineAvailableManager.isChannelAnswer(callid)){
                log.info("时间已到，电话没有接听，手动挂断,callId[{}]",callid);
                fsManager.hangup(callid+Constant.UUID_SEPARATE+orgId);
            }

        }, Integer.valueOf(value), TimeUnit.SECONDS);
    }
}
