package com.guiji.manager;

import com.google.common.base.Preconditions;

import com.guiji.component.result.Result;
import com.guiji.fsmanager.api.ILineOperation;
import com.guiji.fsmanager.entity.FsLineInfoVO;
import com.guiji.helper.RequestHelper;
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
public class FsLineManager {
    @Autowired
    ILineOperation lineOperation;

    /**
     * 获取线路信息
     * @return
     */
    public FsLineInfoVO getFsLine(int lineId) {
        Result.ReturnData returnData = null;
        try{
            returnData = RequestHelper.loopRequest(new RequestHelper.RequestApi() {
                @Override
                public Result.ReturnData execute() {
                    return lineOperation.getFsInfoByLineId(lineId);
                }

                @Override
                public void onErrorResult(Result.ReturnData result) {
                    //TODO: 报警
                    log.warn("根据lineId请求线路失败，lineId:[{}],错误码是:[{}][{}]", lineId,result.getCode(), result.getMsg());
                }
                @Override
                public boolean trueBreakOnCode(String code) {
                    return false;
                }
            }, 3, 1, 1, 60, true);
        }catch (Exception ex){
            log.warn("根据lineId请求线路出现异常", ex);
        }

        Preconditions.checkNotNull(returnData, "根据lineId请求线路失败，返回结果为空");
        return (FsLineInfoVO) returnData.getBody();
    }
}
