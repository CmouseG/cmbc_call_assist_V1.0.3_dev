package com.guiji.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * FreeSWITCH的配置选项
 */
@Data
@Component
@ConfigurationProperties(prefix="fs")
public class FsConfig {
    private Integer reconnectSleepTime;
    private String wssUrl;
}
