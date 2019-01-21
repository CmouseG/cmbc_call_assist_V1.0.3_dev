package com.guiji.calloutserver.scheduler;

import com.guiji.calloutserver.fs.LocalFsServer;
import com.guiji.calloutserver.manager.EurekaManager;
import com.guiji.calloutserver.manager.FsAgentManager;
import com.guiji.calloutserver.service.CallStateService;
import com.guiji.component.lock.Lock;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @Auther: 黎阳
 * @Date: 2018/12/26 0026 21:42
 * @Description:
 */
@Component
@Slf4j
//@EnableScheduling
public class CheckCallStateScheduler {

    @Autowired
    CallStateService callStateService;

//    @Scheduled(cron = "0 0/5 * * * ?") // 每5分钟运行一次
    public void checkCallState(){

        log.info("----------- start checkCallState -----------");

        callStateService.updateCallState();

        log.info("----------- end checkCallState -----------");

    }
}
