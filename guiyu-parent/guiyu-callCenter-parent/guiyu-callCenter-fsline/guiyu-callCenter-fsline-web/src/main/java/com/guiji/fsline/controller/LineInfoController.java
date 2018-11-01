package com.guiji.fsline.controller;

import com.guiji.common.result.Result;
import com.guiji.fsline.api.IFsLine;
import com.guiji.fsline.entity.FsLineVO;
import com.guiji.fsline.util.RequestUtil;
import com.guiji.fsline.util.RequestUtil.RequestApi;
import com.guiji.fsmanager.api.FsResourceApi;
import com.guiji.fsmanager.entity.FsBind;
import com.guiji.fsmanager.entity.ServiceTypeEnum;
import com.guiji.utils.ServerUtil;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.cloud.client.serviceregistry.Registration;
import org.springframework.context.event.EventListener;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LineInfoController implements IFsLine {
    private final Logger log = LoggerFactory.getLogger(LineInfoController.class);
    @Autowired
    Registration registration;

    @Autowired
    FsResourceApi fsResourceApi;

    FsBind fsBind;

    /**
     * 在启动的时候，到fsmanager申请线路
     */
    @EventListener(ApplicationReadyEvent.class)
    public void initLine() {
        Result.ReturnData returnData = null;
        try {
            returnData = RequestUtil.loopRequest(new RequestApi() {
                @Override
                public Result.ReturnData execute() {
                    return fsResourceApi.applyfs(ServerUtil.getInstanceId(registration), ServiceTypeEnum.fsline);
                }

                @Override
                public void onErrorResult(Result.ReturnData result) {
                    //TODO: 报警
                    log.warn("申请freeswitch资源失败, 错误码为[{}]，错误信息[{}]", result.getCode(), result.getMsg());
                }
            }, -1, 1, 1,60);
        } catch (Exception e) {
            log.warn("在初始化fsline时出现异常", e);
        }

        if(returnData!=null && RequestUtil.isSuccess(returnData)){
            fsBind = (FsBind)returnData.getBody();
        }else{
            log.warn("系统启动失败，因未获取到系统信息");
            System.exit(-1);
        }
    }


    @Override
    public Result.ReturnData<FsLineVO> getFsInfo() {
        String instanceId = ServerUtil.getInstanceId(registration);

        FsLineVO info = new FsLineVO();
        info.setFsLineId(instanceId);
        info.setFsInPort(fsBind.getFsInPort());
        info.setFsOutPort(fsBind.getFsOutPort());
        info.setFsIp(fsBind.getFsAgentAddr());

        return Result.ok(info);
    }
}
