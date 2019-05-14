package com.guiji.fsmanager.entity;

public class FsLineInfoVO {
    private String fsIp;

    private String fsInPort;

    private String fsOutPort;

    private String codec;

    public String getFsIp() {
        return fsIp;
    }

    public void setFsIp(String fsIp) {
        this.fsIp = fsIp;
    }

    public String getFsInPort() {
        return fsInPort;
    }

    public void setFsInPort(String fsInPort) {
        this.fsInPort = fsInPort;
    }

    public String getFsOutPort() {
        return fsOutPort;
    }

    public void setFsOutPort(String fsOutPort) {
        this.fsOutPort = fsOutPort;
    }

    public String getCodec() {
        return codec;
    }

    public void setCodec(String codec) {
        this.codec = codec;
    }

    @Override
    public String toString() {
        return "FsLineInfoVO{" +
                "fsIp='" + fsIp + '\'' +
                ", fsInPort='" + fsInPort + '\'' +
                ", fsOutPort='" + fsOutPort + '\'' +
                ", codec='" + codec + '\'' +
                '}';
    }
}
