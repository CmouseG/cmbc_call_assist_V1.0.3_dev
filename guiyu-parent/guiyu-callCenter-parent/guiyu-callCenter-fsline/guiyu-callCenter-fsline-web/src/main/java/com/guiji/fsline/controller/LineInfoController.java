package com.guiji.fsline.controller;

import com.guiji.component.result.Result;
import com.guiji.fsline.api.IFsLine;
import com.guiji.fsline.entity.FsLineVO;
import com.guiji.fsline.helper.RequestHelper;
import com.guiji.fsline.helper.RequestHelper.RequestApi;
import com.guiji.fsmanager.api.IFsResource;
import com.guiji.fsmanager.entity.FsBindVO;
import com.guiji.fsmanager.entity.ServiceTypeEnum;
import com.guiji.utils.ServerUtil;
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
    IFsResource fsResourceApi;

    FsBindVO fsBind;

    /**
     * 在启动的时候，到fsmanager申请线路
     */
    @EventListener(ApplicationReadyEvent.class)
    public void initLine() {
        Result.ReturnData returnData = null;
        try {
            returnData = RequestHelper.loopRequest(new RequestApi() {
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

        if(returnData!=null && RequestHelper.isSuccess(returnData)){
            fsBind = (FsBindVO)returnData.getBody();
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