package com.guiji.calloutserver.manager.impl;

import com.google.common.base.Preconditions;
import com.guiji.callcenter.dao.entity.FsBind;
import com.guiji.calloutserver.manager.EurekaManager;
import com.guiji.calloutserver.manager.FsManager;
import com.guiji.calloutserver.helper.RequestHelper;
import com.guiji.component.result.Result;
import com.guiji.fsmanager.api.IFsResource;
import com.guiji.fsmanager.entity.ServiceTypeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Auther: 魏驰
 * @Date: 2018/11/5 11:15
 * @Project：guiyu-parent
 * @Description:
 */
@Slf4j
@Service
public class FsManagerImpl implements FsManager {
    @Autowired
    IFsResource iFsResourceApi;

    @Autowired
    EurekaManager eurekaManager;

    @Override
    public FsBind applyfs() {
        Result.ReturnData returnData = null;
        try{
            returnData = RequestHelper.loopRequest(new RequestHelper.RequestApi() {
                @Override
                public Result.ReturnData execute() {
                    return iFsResourceApi.applyfs(eurekaManager.getInstanceId(), ServiceTypeEnum.calloutserver);
                }

                @Override
                public void onErrorResult(Result.ReturnData result) {
                    //TODO: 报警
                    log.warn("请求fs资源失败，错误码是[{}][{}]", result.getCode(), result.getMsg());
                }
                @Override
                public boolean trueBreakOnCode(String code) {
                    return false;
                }
            }, -1, 1, 1, 60);
        }catch (Exception ex){
            log.warn("申请fs资源出现异常", ex);
            //TODO: 报警，申请fs资源出现异常
        }

        Preconditions.checkNotNull(returnData, "申请fs失败，返回结果为空");
        return (FsBind) returnData.getBody();
    }

    @Override
    public void releasefs() {
        try{
            RequestHelper.loopRequest(new RequestHelper.RequestApi() {
                @Override
                public Result.ReturnData execute() {
                    return iFsResourceApi.releasefs(eurekaManager.getInstanceId());
                }

                @Override
                public void onErrorResult(Result.ReturnData result) {
                    //TODO: 报警
                    log.warn("释放fs资源失败，错误码是[{}][{}]", result.getCode(), result.getMsg());
                }
                @Override
                public boolean trueBreakOnCode(String code) {
                    return false;
                }
            }, -1, 1, 1, 60);
        }catch (Exception ex){
            log.warn("释放fs资源出现异常", ex);
            //TODO: 报警，申请fs资源出现异常
        }
    }
}
