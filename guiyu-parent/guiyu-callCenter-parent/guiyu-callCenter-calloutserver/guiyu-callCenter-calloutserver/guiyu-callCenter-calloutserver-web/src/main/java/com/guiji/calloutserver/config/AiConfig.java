package com.guiji.calloutserver.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@ConfigurationProperties(prefix = "ai")
@Component
public class AiConfig {
    /**
     * sellbot的服务器地址
     */
    private String url;

    /**
     * sellbot的开始端口
     */
    private Integer startPort;

    /**
     * sellbot的端口数量
     */
    private Integer portCount;

    /**
     * docker中AI应答的录音文件存储路径
     */
    private String wavFileHome;

    /**
     * 文件在实体机上面的真实保存路径
     */
    private String realWavFileHome;
}
