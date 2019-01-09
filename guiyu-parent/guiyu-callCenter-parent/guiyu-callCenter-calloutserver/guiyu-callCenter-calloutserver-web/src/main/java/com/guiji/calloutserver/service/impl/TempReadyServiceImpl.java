package com.guiji.calloutserver.service.impl;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.guiji.calloutserver.manager.FsAgentManager;
import com.guiji.calloutserver.service.TempReadyService;
import com.guiji.component.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Map;

@Slf4j
@Service
public class TempReadyServiceImpl implements TempReadyService {

    @Autowired
    FsAgentManager fsAgentManager;

    private Cache<String, Boolean> tempOkCache;

    @PostConstruct
    public void init(){
        tempOkCache = CacheBuilder.newBuilder().build();
    }

    @Override
    public boolean isTempOk(String tempId) {
        if(tempOkCache.getIfPresent(tempId)!=null){
            return tempOkCache.getIfPresent(tempId);
        }
        boolean isTempOk = initTemp(tempId);
        tempOkCache.put(tempId,isTempOk);
        return isTempOk;
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
            Map<String, Double> map = fsAgentManager.getwavlength(tempId);
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
