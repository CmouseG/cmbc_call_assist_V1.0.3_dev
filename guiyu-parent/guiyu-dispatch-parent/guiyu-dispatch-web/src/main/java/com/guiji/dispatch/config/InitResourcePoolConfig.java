package com.guiji.dispatch.config;

import com.guiji.dispatch.service.IResourcePoolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * Created by ty on 2019/1/9.
 */
@Component
public class InitResourcePoolConfig {
    @Autowired
    private IResourcePoolService resourcePoolService;
    @Bean
    public boolean init() {
        return resourcePoolService.initResourcePool();
    }
}
