package com.guiji.fs;

import com.guiji.component.result.Result;
import com.guiji.fsmanager.api.IFsResource;
import com.guiji.fsmanager.entity.FsBindVO;
import com.guiji.fsmanager.entity.ServiceTypeEnum;
import com.guiji.helper.RequestHelper;
import com.guiji.manager.EurekaManager;
import com.guiji.manager.FsAgentManager;
import com.guiji.service.AgentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * @Auther: 魏驰
 * @Date: 2018/11/9 13:48
 * @Project：guiyu-parent
 * @Description:
 */
@Slf4j
@Component
public class ApplicationInit {
    @Autowired
    IFsResource iFsResource;

    @Autowired
    EurekaManager eurekaManager;

    @Autowired
    FsManager fsManager;

    @Autowired
    FsAgentManager fsAgentManager;

    @Autowired
    AgentService agentService;

    /**
     * 在系统启动完成，需要进行初始化，包括以下内容：
     * 1、申请freeswitch资源
     * 2、使用申请的资源初始化fsmanager
     */
    @EventListener(ApplicationReadyEvent.class)
        public void init() {
        try {
            FsBindVO fsBindVO = applyFsResource();
            log.info("ApplicationInit init fsBindVO [{}]", fsBindVO );
            fsManager.init(fsBindVO);
            fsAgentManager.init(fsBindVO);
            log.info("连接完freeswitch后加载全局callcenter.xml文件");
            agentService.initCallcenter();
        } catch (Exception e) {
            log.warn("初始化toagentserver出现异常", e);
            //TODO: 报警
        }
    }

    /**
     * 申请freeswitch资源
     * @return
     */
    private FsBindVO applyFsResource() {
        Result.ReturnData returnData = null;
        try {
            returnData = RequestHelper.loopRequest(new RequestHelper.RequestApi() {
                @Override
                public Result.ReturnData execute() {
                    return iFsResource.applyfs(eurekaManager.getInstanceId(), ServiceTypeEnum.callcenter);
                }

                @Override
                public void onErrorResult(Result.ReturnData result) {
                    //TODO: 报警
                    log.warn("toagentserver申请freeswitch资源失败, 错误码为[{}]，错误信息[{}]", result.getCode(), result.getMsg());
                }

                @Override
                public boolean trueBreakOnCode(String code) {
                    return false;
                }
            }, -1, 1, 1,60,true);

        } catch (Exception e) {
            log.warn("在初始化toagentserver时出现异常", e);
        }

        if(returnData!=null){
            return (FsBindVO) returnData.getBody();
        }else{
            return null;
        }
    }
}
