package com.guiji.manager.impl;

import com.google.common.base.Preconditions;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.guiji.component.result.Result;
import com.guiji.fsagent.api.ITemplate;
import com.guiji.fsagent.entity.RecordReqVO;
import com.guiji.fsagent.entity.RecordVO;
import com.guiji.fsmanager.entity.FsBindVO;
import com.guiji.helper.RequestHelper;
import com.guiji.manager.EurekaManager;
import com.guiji.manager.FsAgentManager;
import com.guiji.utils.FeignBuildUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
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
    EurekaManager eurekaManager;

    @Autowired
    ITemplate iTemplate;

    @Override
    public void init(FsBindVO fsBindVO) {
        log.info("开始初始化FsAgentManager，地址为[{}]", fsBindVO.getFsAgentAddr());
        iTemplate = FeignBuildUtil.feignBuilderTarget(ITemplate.class,"http://" + fsBindVO.getFsAgentAddr());
    }

    @Override
    public RecordVO uploadRecord(String callId, String fileName, String busiType, Long userId){
        log.info("开始上传录音文件，callId[{}], fileName[{}], busiType[{}]", callId, fileName, busiType);
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
                    log.warn("上传录音文件失败，错误码是[{}][{}],request[{}]", result.getCode(), result.getMsg(),request);
                }
                @Override
                public boolean trueBreakOnCode(String code) {
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
}
