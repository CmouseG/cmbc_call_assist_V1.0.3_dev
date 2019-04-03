package com.guiji.calloutserver.config;

import com.guiji.calloutserver.enm.ECallDirection;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix="fsbot")
public class FsBotConfig {
    private String homeDir;
    private String recordingsDir;
    private ECallDirection callDirection;

    private String machineCode;

    /**
     * 是否允许打断
     */
    private boolean allowDisturbed;

    /**
     * 机器人播放媒体后，等待客户说话的最大超时时长
     */
    private Integer maxMediaTimeout;
}
