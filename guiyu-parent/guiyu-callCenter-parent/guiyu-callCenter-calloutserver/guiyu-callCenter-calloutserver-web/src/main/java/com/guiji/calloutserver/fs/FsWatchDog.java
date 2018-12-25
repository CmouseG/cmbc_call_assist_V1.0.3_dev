package com.guiji.calloutserver.fs;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 用于检测FreeSWITCH的ESL连接是否终端，如果终端则自动重连
 */
@Slf4j
@Service
public class FsWatchDog {
    private LocalFsServer fsManager;

    public void monitor(LocalFsServer fsManager){
        this.fsManager = fsManager;

        log.info("开始启动FsWatchDog，防止esl连接断开");
        Thread thread = new Thread(() -> {
            while (true){
                try {
                    if (!fsManager.isConnect()) {
                        try {
                            //TODO:报警，freeswitch失败失败,需要重连
                            log.info("重连FreeSWITCH....");
                            fsManager.reConnect();
                        }catch (Exception ex){
                            //TODO:报警，重连fs出现异常
                            log.warn("重连FreeSWTICH出现异常" + ex.getMessage());

                        }
                        //尝试连接，需等待时间长一点，在进行判断
                        Thread.sleep(2000L );
                    }
                }catch (Exception ex){
                    log.warn("重连出现异常",ex.getMessage());
                }

                try {
                    Thread.sleep( 1000L);
                } catch (InterruptedException e) {
                    log.warn("sleep出现异常", e);
                }
            }
        });

        thread.start();
    }
}
