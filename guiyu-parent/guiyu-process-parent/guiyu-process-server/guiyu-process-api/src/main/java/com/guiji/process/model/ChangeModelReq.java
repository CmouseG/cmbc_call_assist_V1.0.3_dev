package com.guiji.process.model;

/**
 * Created by ty on 2018/12/6.
 */
public class ChangeModelReq {
    private String fromModel;

    private String toModel;

    private String ip;

    private int port;

    private Long userId;

    public String getFromModel() {
        return fromModel;
    }

    public void setFromModel(String fromModel) {
        this.fromModel = fromModel;
    }

    public String getToModel() {
        return toModel;
    }

    public void setToModel(String toModel) {
        this.toModel = toModel;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "ChangeModelReq{" +
                "fromModel='" + fromModel + '\'' +
                ", toModel='" + toModel + '\'' +
                ", ip='" + ip + '\'' +
                ", port=" + port +
                ", userId=" + userId +
                '}';
    }
}
