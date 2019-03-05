package com.guiji.calloutserver.service.impl;

import com.guiji.calloutserver.manager.EurekaManager;
import com.guiji.calloutserver.manager.FsAgentManager;
import com.guiji.calloutserver.service.TempReadyService;
import com.guiji.component.result.Result;
import com.guiji.utils.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Slf4j
@Service
public class TempReadyServiceImpl implements TempReadyService {

    @Autowired
    FsAgentManager fsAgentManager;
    @Autowired
    EurekaManager eurekaManager;
    @Autowired
    RedisUtil redisUtil;

    @Override
    public boolean isTempOk(String tempId) {

        String key = "callCenter_isTempOk_"+tempId+eurekaManager.getInstanceId();
        Object value =  redisUtil.get(key);
        if(value!=null){
            return (boolean) value;
        }else{//缓存没有取到

            synchronized (this){//防止缓存击穿
                Object object =  redisUtil.get(key);
                if(object!=null){
                    return (boolean) object;
                }else{
                    boolean isTempOk = initTemp(tempId);
                    if(isTempOk){ //模板存在缓存一天
                        redisUtil.set(key,isTempOk,24*60*60);
                    }else{ //模板不存在缓存5分钟
                        redisUtil.set(key,isTempOk,5*60);
                    }
                    return isTempOk;
                }
            }
        }
    }


    public boolean initTemp(String tempId) {
        try{
            Result.ReturnData<Boolean> result =  fsAgentManager.istempexist(tempId);
            if(!result.getBody()){
                log.warn("启动呼叫计划失败，模板不存在[{}]", tempId);
                return false;
            }else{
                log.info("模板检查通过，返回结果为[{}]", result.getBody());
            }
        }catch (Exception e){
            log.warn("启动呼叫计划失败，出现异常,模板不存在[{}]", tempId);
            return false;
        }

        log.info("开始获取模板录音时长[{}]", tempId);
        try {
            Map<String, Double> map = fsAgentManager.refreshWavLength(tempId);
            if(map==null || map.size()==0){
                log.warn("启动呼叫计划失败，录音不存在，下载录音文件时长失败[{}]", tempId);
                return false;
            }else{
                log.info("模板录音获取成功，获取的数量为[{}]", map.size());
            }
        }catch (Exception e){
            log.warn("启动呼叫计划失败，下载录音文件时长失败[{}]", tempId);
            return false;
        }

        return true;
    }

}
