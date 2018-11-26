package com.guiji.process.agent.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Created by ty on 2018/11/26.
 */
@Component
public class ConfigInit {
    @Value("${server.port}")
    private int agentPort;

    @Value("${processServer.ip}")
    private String serverIp;

    @Value("${processServer.port}")
    private int serverPort;

    @Value("${agent.monitorDir}")
    private String monitorDir;

    @Value("${agent.monitorFile}")
    private String monitorFile;

    public int getAgentPort() {
        return agentPort;
    }

    public void setAgentPort(int agentPort) {
        this.agentPort = agentPort;
    }

    public String getServerIp() {
        return serverIp;
    }

    public void setServerIp(String serverIp) {
        this.serverIp = serverIp;
    }

    public int getServerPort() {
        return serverPort;
    }

    public void setServerPort(int serverPort) {
        this.serverPort = serverPort;
    }

    public String getMonitorDir() {
        return monitorDir;
    }

    public void setMonitorDir(String monitorDir) {
        this.monitorDir = monitorDir;
    }

    public String getMonitorFile() {
        return monitorFile;
    }

    public void setMonitorFile(String monitorFile) {
        this.monitorFile = monitorFile;
    }

    @Override
    public String toString() {
        return "ConfigInit{" +
                "agentPort=" + agentPort +
                ", serverIp='" + serverIp + '\'' +
                ", serverPort=" + serverPort +
                ", monitorDir='" + monitorDir + '\'' +
                ", monitorFile=" + monitorFile +
                '}';
    }
}
