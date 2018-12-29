package com.guiji.calloutserver.manager.impl;

import com.google.common.base.Preconditions;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.guiji.calloutserver.helper.RequestHelper;
import com.guiji.calloutserver.manager.EurekaManager;
import com.guiji.calloutserver.manager.FsAgentManager;
import com.guiji.component.result.Result;
import com.guiji.fsagent.api.ITemplate;
import com.guiji.fsagent.entity.RecordReqVO;
import com.guiji.fsagent.entity.RecordVO;
import com.guiji.fsagent.entity.WavLengthVO;
import com.guiji.fsmanager.entity.FsBindVO;
import com.guiji.utils.FeignBuildUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Auther: 魏驰
 * @Date: 2018/11/8 20:10
 * @Project：guiyu-parent
 * @Description:
 */
@Slf4j
@Service
public class FsAgentManagerImpl implements FsAgentManager {

    private Cache<String, Map<String,Double>> wavCaches;

    @PostConstruct
    public void init(){
        wavCaches = CacheBuilder.newBuilder().build();
    }

    private ITemplate iTemplate;

    @Autowired
    EurekaManager eurekaManager;

    @Override
    public void init(FsBindVO fsBindVO) {
        log.info("开始初始化FsAgentManager，地址为[{}]", fsBindVO.getFsAgentAddr());
        iTemplate = FeignBuildUtil.feignBuilderTarget(ITemplate.class,"http://" + fsBindVO.getFsAgentAddr());
    }

    @Override
    public RecordVO uploadRecord(String callId, String fileName, String busiType, Long userId){
        log.info("开始上传文件，callId[{}], fileName[{}], busiType[{}]", callId, fileName, busiType);
        RecordReqVO request = new RecordReqVO();
        request.setBusiId(callId);
        request.setFileName(fileName);
        request.setSysCode(eurekaManager.getAppName());
        request.setBusiType(busiType);
        request.setUserId(userId);
        Result.ReturnData returnData = null;
        try{
            returnData = RequestHelper.loopRequest(new RequestHelper.RequestApi() {
                @Override
                public Result.ReturnData execute() {
                    return iTemplate.uploadrecord(request);
                }

                @Override
                public void onErrorResult(Result.ReturnData result) {
                    //TODO: 报警
                    log.warn("上传文件失败，错误码是[{}][{}],request[{}]", result.getCode(), result.getMsg(),request);
                }
            }, 3, 1, 120, 6000);
        }catch (Exception ex){
            log.warn("上传文件出现异常", ex);
            //TODO: 报警，上传文件异常
        }

        Preconditions.checkNotNull(returnData, "上传录音失败，返回结果为空");
        return (RecordVO) returnData.getBody();
    }

    @Override
    public Result.ReturnData<Boolean> istempexist(String tempId) {

        Result.ReturnData<Boolean> returnData = null;
        try{
            returnData = RequestHelper.loopRequest(new RequestHelper.RequestApi() {
                @Override
                public Result.ReturnData execute() {
                    return  iTemplate.istempexist(tempId);
                }

                @Override
                public void onErrorResult(Result.ReturnData result) {
                    log.warn("判断模板是否存在，错误码是[{}][{}]", result.getCode(), result.getMsg());
                }
            }, 5, 1, 2, 60,true);
        }catch (Exception ex){
            log.warn("判断模板是否存在出现异常", ex);
        }

        return returnData;
    }

    @Override
    public Map<String, Double> getwavlength(String tempId){

        if(wavCaches.getIfPresent(tempId)==null){
            Result.ReturnData<List<WavLengthVO>> result = null;
            try{
                result = RequestHelper.loopRequest(new RequestHelper.RequestApi() {
                    @Override
                    public Result.ReturnData execute() {
                        return  iTemplate.getwavlength(tempId.replace("_en","_rec"));
                    }

                    @Override
                    public void onErrorResult(Result.ReturnData result) {
                        log.warn("判断模板是否存在，错误码是[{}][{}]", result.getCode(), result.getMsg());
                    }
                }, 5, 1, 2, 60,true);
            }catch (Exception ex){
                log.warn("判断模板是否存在出现异常", ex);
            }

            if(result!=null && result.success){
                List<WavLengthVO> list = result.getBody();
                if(list!=null && list.size()>0){
                    Map<String,Double> map =new HashMap<String,Double>();
                    for(WavLengthVO wavLengthVO:list){
                        map.put(wavLengthVO.getFileName(),wavLengthVO.getLength());
                    }
                    wavCaches.put(tempId,map);
                    return map;
                }
            }

        }else{
            return wavCaches.getIfPresent(tempId);
        }
        return null;
    }

    @Override
    public Double getWavDruation(String tempId, String filename){

        Map<String, Double> map = wavCaches.getIfPresent(tempId);
        if(map==null){
            map = getwavlength(tempId);
        }
        if(filename.contains("/")){
            String[] arr = filename.split("/");
            String result = arr[arr.length-1];
            return map.get(result);
        }else{
            return map.get(filename);
        }

    }
}
