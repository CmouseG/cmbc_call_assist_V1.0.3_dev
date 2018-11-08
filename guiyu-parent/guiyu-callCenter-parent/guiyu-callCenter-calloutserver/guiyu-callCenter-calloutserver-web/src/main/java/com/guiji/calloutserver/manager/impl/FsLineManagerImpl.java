package com.guiji.calloutserver.manager.impl;

import com.guiji.calloutserver.manager.FsLineManager;
import com.guiji.calloutserver.helper.RequestHelper;
import com.guiji.component.result.Result;
import com.guiji.fsline.api.IFsLine;
import com.guiji.fsline.entity.FsLineVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Auther: 魏驰
 * @Date: 2018/11/4 17:58
 * @Project：guiyu-parent
 * @Description:
 */
@Slf4j
@Service
public class FsLineManagerImpl implements FsLineManager {
    @Autowired
    IFsLine iFsLine;

    /**
     * 获取线路信息
     * @return
     */
    @Override
    public FsLineVO getFsLine() {
        Result.ReturnData returnData = null;
        try{
            returnData = RequestHelper.loopRequest(new RequestHelper.RequestApi() {
                @Override
                public Result.ReturnData execute() {
                    return iFsLine.getFsInfo();
                }

                @Override
                public void onErrorResult(Result.ReturnData result) {
                    //TODO: 报警
                    log.warn("请求fsline失败，错误码是[{}][{}]", result.getCode(), result.getMsg());
                }
            }, -1, 1, 1, 60);
        }catch (Exception ex){
            log.warn("获取fsline出现异常", ex);
            //TODO: 报警，获取fsline异常
        }

        return (FsLineVO) returnData.getBody();
    }
}
