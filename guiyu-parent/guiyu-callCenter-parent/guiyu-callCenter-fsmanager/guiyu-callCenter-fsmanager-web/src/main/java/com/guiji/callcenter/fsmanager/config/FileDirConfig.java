package com.guiji.callcenter.fsmanager.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
@Component
@Data
@ConfigurationProperties(prefix = "filedir")
public class FileDirConfig {
    private String lineDir;
}