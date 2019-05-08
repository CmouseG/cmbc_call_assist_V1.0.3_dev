package com.guiji.fsagent.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "fs")
public class FsConfig {
    private String homeDir;
    private String role;
    private boolean internalUse;

    public String getHomeDir() {
        return homeDir;
    }

    public void setHomeDir(String homeDir) {
        this.homeDir = homeDir;
    }

    public boolean isInternalUse() {
        return internalUse;
    }

    public void setInternalUse(boolean internalUse) {
        this.internalUse = internalUse;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
