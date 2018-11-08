package com.guiji.calloutserver.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@ConfigurationProperties(prefix = "aliasr")
@Component
public class AliAsrConfig {
    private String accessId;
    private String accessSecret;
}
