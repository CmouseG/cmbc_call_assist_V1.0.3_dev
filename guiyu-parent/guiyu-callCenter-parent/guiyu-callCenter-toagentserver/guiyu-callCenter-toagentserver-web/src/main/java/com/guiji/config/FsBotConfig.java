package com.guiji.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix="fsbot")
public class FsBotConfig {
    private String homeDir;
    private String recordingsDir;
    private String recordingsDirZip;
    private boolean noAuth;
}
