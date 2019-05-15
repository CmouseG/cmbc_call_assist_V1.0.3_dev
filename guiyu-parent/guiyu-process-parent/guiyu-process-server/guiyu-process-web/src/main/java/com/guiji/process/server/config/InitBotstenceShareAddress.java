package com.guiji.process.server.config;

import com.guiji.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ty on 2019/4/22.
 */
@Component
public class InitBotstenceShareAddress implements ApplicationRunner {
    @Value("${botstence.share.address.sellbot}")
    private String sellbotAddress;
    @Value("${botstence.share.address.freeswitch}")
    private String freeswitchAddress;
    @Value("${botstence.share.address.robot}")
    private String robotAddress;
    @Autowired
    private RedisUtil redisUtil;
    private static final String GY_PROCESS_SELLBOT = "GY_PROCESS_SELLBOT";
    private static final String GY_PROCESS_FREESWITCH = "GY_PROCESS_FREESWITCH";
    private static final String GY_PROCESS_SROBOT = "GY_PROCESS_ROBOT";

    @Override
    public void run(ApplicationArguments args) throws Exception {
        new Thread(() -> {
            List<String> sellbotAddressList = new ArrayList<String>();
            List<String> freeswitchAddressList = new ArrayList<String>();
            List<String> robotAddressList = new ArrayList<String>();
            sellbotAddressList.add(sellbotAddress);
            freeswitchAddressList.add(freeswitchAddress);
            robotAddressList.add(robotAddress);
            redisUtil.set(GY_PROCESS_SELLBOT,sellbotAddressList);
            redisUtil.set(GY_PROCESS_FREESWITCH,freeswitchAddressList);
            redisUtil.set(GY_PROCESS_SROBOT,robotAddressList);
        }).start();

    }
}
