package com.guiji.simagent.manager;

import com.guiji.simagent.config.FsConfig;
import com.guiji.simagent.entity.FreeSWITCH;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * 初始化fsagent服务，连接esl
 */
@Slf4j
@Component
public class ApplicationInit {
    @Autowired
    EurekaManager eurekaManager;
    @Autowired
    FsConfig fsConfig;
    private FreeSWITCH freeSwitch;
    /**
     * 在系统启动完成，需要进行初始化，包括以下内容：
     * 1、连接freeswitch ESL
     */
    @EventListener(ApplicationReadyEvent.class)
    public void init() {
        try {
            String serviceId = eurekaManager.getInstanceId();
            serviceId = serviceId.substring(0, serviceId.lastIndexOf(":"));
            freeSwitch = new FreeSWITCH(fsConfig.getHomeDir(), serviceId);
        } catch (Exception e) {
            log.warn("初始化fsagent出现异常", e);
            //TODO: 报警
        }
    }

    public FreeSWITCH getFreeSwitch() {
        return freeSwitch;
    }


}
