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
import com.guiji.fsagent.entity.TtsWav;
import com.guiji.fsagent.entity.WavLengthVO;
import com.guiji.fsmanager.entity.FsBindVO;
import com.guiji.utils.FeignBuildUtil;
import com.guiji.utils.RedisUtil;
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

    @Autowired
    RedisUtil redisUtil;

    private ITemplate iTemplate;

    @Autowired
    EurekaManager eurekaManager;

    @Override
    public void init(FsBindVO fsBindVO) {
        log.info("开始初始化FsAgentManager，地址为[{}]", fsBindVO.getFsAgentAddr());
        iTemplate = FeignBuildUtil.feignBuilderTarget(ITemplate.class,"http://" + fsBindVO.getFsAgentAddr());
    }

    @Override
    public RecordVO uploadRecord(String callId, String fileName, String busiType, Long userId, Integer billsec, Integer duration){
        log.info("开始上传文件，callId[{}], fileName[{}], busiType[{}]", callId, fileName, busiType);
        RecordReqVO request = new RecordReqVO();
        request.setBusiId(callId);
        request.setFileName(fileName);
        request.setSysCode(eurekaManager.getAppName());
        request.setBusiType(busiType);
        request.setUserId(userId);
        if(billsec!=null){
            request.setBillsec(billsec);
        }
        if(duration!=null){
            request.setDuration(duration);
        }
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
                @Override
                public boolean trueBreakOnCode(String code) {
                    if(code.equals("0300006") || code.equals("0300001") || code.equals("0300009")){
                        return true;
                    }
                    return false;
                }
            }, 10, 1, 30, 600);
        }catch (Exception ex){
            log.warn("上传文件出现异常", ex);
            //TODO: 报警，上传文件异常
        }

        Preconditions.checkNotNull(returnData, "上传录音失败，返回结果为空");
        return (RecordVO) returnData.getBody();
    }

    @Override
    public Result.ReturnData<Boolean> istempexist(String tempId) {
        return  iTemplate.istempexist(tempId);
       /* Result.ReturnData<Boolean> returnData = null;
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

        return returnData;*/
    }

    @Override
    public Map<String, Double> refreshWavLength(String tempId) {

        String key = "calloutserver_"+eurekaManager.getInstanceId()+"_wavlength_"+tempId;

        Result.ReturnData<List<WavLengthVO>> result = iTemplate.getwavlength(tempId.replace("_en","_rec"));
        if(result!=null && result.success){
            List<WavLengthVO> list = result.getBody();
            if(list!=null && list.size()>0){
                Map<String,Double> map =new HashMap<String,Double>();
                for(WavLengthVO wavLengthVO:list){
                    map.put(wavLengthVO.getFileName(),wavLengthVO.getLength());
                }
                redisUtil.set(key,map);
                return map;
            }
        }

        if(redisUtil.get(key)!=null){
            return (Map<String, Double>) redisUtil.get(key);
        }
        return null;
    }

    @Override
    public Map<String, Double> getwavlength(String tempId){

        String key = "calloutserver_"+eurekaManager.getInstanceId()+"_wavlength_"+tempId;

        if(redisUtil.get(key)==null){
            Result.ReturnData<List<WavLengthVO>> result = iTemplate.getwavlength(tempId.replace("_en","_rec"));
            if(result!=null && result.success){
                List<WavLengthVO> list = result.getBody();
                if(list!=null && list.size()>0){
                    Map<String,Double> map =new HashMap<String,Double>();
                    for(WavLengthVO wavLengthVO:list){
                        map.put(wavLengthVO.getFileName(),wavLengthVO.getLength());
                    }
                    redisUtil.set(key,map);
                    return map;
                }
            }

        }else{
            return (Map<String, Double>) redisUtil.get(key);
        }
        return null;
    }

    @Override
    public Double getWavDruation(String tempId, String filename, String callId){

        if(filename.contains("/")){
            String[] arr = filename.split("/");
            filename = arr[arr.length-1];
        }
        if(!filename.endsWith(".wav")){
            filename =filename+".wav";
        }

        //先从redis中查询tts文件
        Object value = redisUtil.get("callOutServer_ttsFile_"+callId);
        if(value!=null){
            List<TtsWav> list = (List<TtsWav>) value;
            if(list.size()>0){
                for(TtsWav ttsWav:list){
                    if(ttsWav.getFileName().equals(filename)){
                        return ttsWav.getFileDuration();
                    }
                }
            }
        }

//        Map<String, Double> map = wavCaches.getIfPresent(tempId);
        Object map = redisUtil.get("calloutserver_"+eurekaManager.getInstanceId()+"_wavlength_"+tempId);
        if(map==null){
            map = getwavlength(tempId);
        }
        return ((Map<String, Double>)map).get(filename);

    }

    @Override
    public void downloadTtsWav(String tempId, String planUuid, String callId) {
        Result.ReturnData<List<TtsWav>> returnData =iTemplate.downloadttswav(tempId,planUuid,callId);
        log.info("下载tts合成语音 ,returnData[{}]",returnData);
        List<TtsWav> list = returnData.getBody();
        //将文件名称，和文件时长缓存到redis中
        if(list!=null && list.size()>0){
            redisUtil.set("callOutServer_ttsFile_"+callId,list,60*60); //1个小时
        }
    }
}
