package com.guiji.calloutserver.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "btows")
public class BtowsConfig {
    private ApiUrl apiUrl;
    private String managerSystemAddr;
    private String aiSerialNumber;
    private String aiCode;
    private String aiKey;
    private String aiMkey;
    private boolean canPullCallJob;
    private Long connectionTimeout;
    private Long socketTimeOut;

    @Data
    public static class ApiUrl{
        private String checkCode;
        private String getCallJob;
        private String pushCallResult;
        private String pushInboundCallResult;
        private String getAiTemp;
    }
}
