package com.guiji.calloutserver.manager.impl;

import com.google.common.base.Preconditions;
import com.guiji.calloutserver.helper.RequestHelper;
import com.guiji.calloutserver.manager.EurekaManager;
import com.guiji.calloutserver.manager.FsAgentManager;
import com.guiji.component.result.Result;
import com.guiji.fsagent.api.ITemplate;
import com.guiji.fsagent.entity.RecordReqVO;
import com.guiji.fsagent.entity.RecordVO;
import com.guiji.fsmanager.entity.FsBindVO;
import com.guiji.utils.FeignBuildUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Auther: 魏驰
 * @Date: 2018/11/8 20:10
 * @Project：guiyu-parent
 * @Description:
 */
@Slf4j
@Service
public class FsAgentManagerImpl implements FsAgentManager {
    private ITemplate iTemplate;

    @Autowired
    EurekaManager eurekaManager;

    @Override
    public void init(FsBindVO fsBindVO) {
        log.debug("开始初始化FsAgentManager，地址为[{}]", fsBindVO.getFsAgentAddr());
        iTemplate = FeignBuildUtil.feignBuilderTarget(ITemplate.class,"http://" + fsBindVO.getFsAgentAddr());
    }

    @Override
    public RecordVO uploadRecord(String callId, String fileName, String busiType){
        log.debug("开始上传文件，callId[{}], fileName[{}], busiType[{}]", callId, fileName, busiType);
        RecordReqVO request = new RecordReqVO();
        request.setBusiId(callId);
        request.setFileName(fileName);
        request.setSysCode(eurekaManager.getAppName());
        request.setBusiType(busiType);

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
}
