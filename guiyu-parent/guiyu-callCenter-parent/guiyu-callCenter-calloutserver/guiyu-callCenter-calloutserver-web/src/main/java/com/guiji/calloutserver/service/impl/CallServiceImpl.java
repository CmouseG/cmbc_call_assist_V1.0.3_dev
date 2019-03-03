package com.guiji.calloutserver.service.impl;

import com.guiji.callcenter.dao.entity.CallOutPlan;
import com.guiji.callcenter.dao.entity.CallOutRecord;
import com.guiji.calloutserver.fs.LocalFsServer;
import com.guiji.calloutserver.manager.CallLineAvailableManager;
import com.guiji.calloutserver.manager.FsLineManager;
import com.guiji.calloutserver.config.AliAsrConfig;
import com.guiji.calloutserver.service.CallService;
import com.guiji.component.result.Result;
import com.guiji.dict.api.ISysDict;
import com.guiji.dict.vo.SysDictVO;
import com.guiji.fsline.entity.FsLineVO;
import lombok.Synchronized;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;
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

    ScheduledExecutorService makecallScheduledExecutor = Executors.newScheduledThreadPool(20);

    /**
     * 调用底层FreeSWITCH接口，发起外呼
     */
    @Override
    public void makeCall(CallOutPlan callplan, String recordFile) {
        //获取线路服务器
        FsLineVO fsLine = fsLineManager.getFsLine();

        String ip = fsLine.getFsIp();
        if(ip.contains(":")){
            ip = ip.split(":")[0];
        }
        String callid = callplan.getCallId().toString();
        //构建外呼命令
        String cmd = String.format("originate {origination_uuid=%s,origination_caller_id_name=%s}" +
                        "sofia/internal/%s@%s:%s 'start_asr:%s %s" +
                        ", record_session:/usr/local/freeswitch/recordings/%s" +
                        ", park' inline",
                callid,
                callplan.getLineId(),
                callplan.getPhoneNum(),
                ip,
                fsLine.getFsInPort(),
                aliAsrConfig.getAccessId(),
                aliAsrConfig.getAccessSecret(),
                recordFile);

        synchronized (this){
            try {
                Thread.sleep(30);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log.info("开始执行呼叫命令[{}]", cmd);
            fsManager.executeAsync(cmd);
        }


        Result.ReturnData<List<SysDictVO>> returnData = iSysDict.getDictValueByTypeKey("bell_time","bell_time");
        String value = returnData.getBody().get(0).getDictValue();
        makecallScheduledExecutor.schedule(() -> {
            log.info("时间已到，去检查电话是否已经接听,callId[{}]",callid);
            if(!callLineAvailableManager.isChannelAnswer(callid)){
                log.info("时间已到，电话没有接听，手动挂断,callId[{}]",callid);
                fsManager.hangup(callplan.getCallId());
            }

        }, Integer.valueOf(value), TimeUnit.SECONDS);
    }
}
