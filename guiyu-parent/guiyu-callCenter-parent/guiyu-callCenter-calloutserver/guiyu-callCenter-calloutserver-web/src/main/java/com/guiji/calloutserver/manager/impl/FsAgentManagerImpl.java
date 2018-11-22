package com.guiji.calloutserver.manager.impl;

import com.google.common.base.Preconditions;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.guiji.calloutserver.entity.Channel;
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
import org.springframework.web.bind.annotation.PathVariable;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

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
    public RecordVO uploadRecord(String callId, String fileName, String busiType){
        log.info("开始上传文件，callId[{}], fileName[{}], busiType[{}]", callId, fileName, busiType);
        RecordReqVO request = new RecordReqVO();
        request.setBusiId(callId);
        request.setFileName(fileName);
        request.setSysCode(eurekaManager.getAppName());
        request.setBusiType(busiType);
        System.out.println(eurekaManager.getAppName());
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
                    log.warn("上传文件失败，错误码是[{}][{}]", result.getCode(), result.getMsg());
                }
            }, -1, 1, 1, 60);
        }catch (Exception ex){
            log.warn("上传文件出现异常", ex);
            //TODO: 报警，上传文件异常
        }

        Preconditions.checkNotNull(returnData, "上传录音失败，返回结果为空");
        return (RecordVO) returnData.getBody();
    }

    @Override
    public Boolean istempexist(String tempId) {
        Result.ReturnData<Boolean>  result = iTemplate.istempexist(tempId);
        return result.getBody();
    }

    @Override
    public void getwavlength(String tempId){

        if(wavCaches.getIfPresent(tempId)==null){
            Result.ReturnData<List<WavLengthVO>> result = iTemplate.getwavlength(tempId.replace("_en","_rec"));
            if(result.success){
                List<WavLengthVO> list = result.getBody();
                if(list!=null && list.size()>0){
                    Map<String,Double> map =new HashMap<String,Double>();
                    for(WavLengthVO wavLengthVO:list){
                        map.put(wavLengthVO.getFileName(),wavLengthVO.getLength());
                    }
                    wavCaches.put(tempId,map);
                }
            }

        }
    }

    @Override
    public Cache<String, Map<String, Double>> getWavCaches() {
        return wavCaches;
    }
}
