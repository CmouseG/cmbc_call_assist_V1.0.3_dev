package com.guiji.fsagent.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "path")
public class PathConfig {
    private String recordPath;
    private String tempPath;
    private String tempRecordPath;
    private String ttsPath;

    public String getRecordPath() {
        return recordPath;
    }

    public void setRecordPath(String recordPath) {
        this.recordPath = recordPath;
    }

    public String getTempPath() {
        return tempPath;
    }

    public void setTempPath(String tempPath) {
        this.tempPath = tempPath;
    }

    public String getTempRecordPath() {
        return tempRecordPath;
    }

    public void setTempRecordPath(String tempRecordPath) {
        this.tempRecordPath = tempRecordPath;
    }

    public String getTtsPath() {
        return ttsPath;
    }

    public void setTtsPath(String ttsPath) {
        this.ttsPath = ttsPath;
    }
}
