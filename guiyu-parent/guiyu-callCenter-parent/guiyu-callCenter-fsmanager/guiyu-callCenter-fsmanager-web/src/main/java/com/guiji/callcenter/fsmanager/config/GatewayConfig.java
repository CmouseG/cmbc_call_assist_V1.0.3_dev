package com.guiji.callcenter.fsmanager.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Data
@ConfigurationProperties(prefix = "gateway")
public class GatewayConfig {
    private String username;
    private String password;
}
