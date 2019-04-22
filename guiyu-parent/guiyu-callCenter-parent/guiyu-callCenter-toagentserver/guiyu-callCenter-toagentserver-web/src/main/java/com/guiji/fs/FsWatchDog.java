package com.guiji.fs;

import com.guiji.config.FsConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 用于检测FreeSWITCH的ESL连接是否终断，如果终断则自动重连
 */
@Service
public class FsWatchDog {
    @Autowired
    FsConfig fsConfig;

    private final Logger logger = LoggerFactory.getLogger(FsWatchDog.class);
    private FsManager fsManager;

    @Autowired
    public FsWatchDog(FsManager fsManager){
        this.fsManager = fsManager;
    }

    public void monitor(){
        Thread thread = new Thread(() -> {
            while (true){
                try {
                    fsManager.reconnect();
                }catch (Exception e){
                    logger.warn("重连FreeSWTICH出现异常" + e.getMessage());
                }
                try {
                    Thread.sleep( fsConfig.getReconnectSleepTime());
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        });
        thread.start();
    }
}
