package com.guiji.guiyu.message.model;

import java.io.Serializable;

/**
 * Created by ty on 2018/12/5.
 */
public class RestoreModelResultMsgVO implements Serializable {
    private static final long serialVersionUID = 1L;
    private String ip;
    private Integer port;
    private Integer from;
    private Integer to;
    private Integer result;

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public Integer getFrom() {
        return from;
    }

    public void setFrom(Integer from) {
        this.from = from;
    }

    public Integer getTo() {
        return to;
    }

    public void setTo(Integer to) {
        this.to = to;
    }

    public Integer getResult() {
        return result;
    }

    public void setResult(Integer result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "RestoreModelResultMsgVO{" +
                "ip='" + ip + '\'' +
                ", port=" + port +
                ", from=" + from +
                ", to=" + to +
                ", result=" + result +
                '}';
    }
}
