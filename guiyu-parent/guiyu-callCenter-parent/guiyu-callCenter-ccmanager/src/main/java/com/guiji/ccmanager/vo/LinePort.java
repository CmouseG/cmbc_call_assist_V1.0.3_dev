package com.guiji.ccmanager.vo;

/**
 * @Auther: 黎阳
 * @Date: 2018/10/25 0025 16:52
 * @Description: freeswitch对外端口
 */
public class LinePort {

    private String fslineId;
    private String ipport;

    public LinePort(String fslineId, String ipport) {
        this.fslineId = fslineId;
        this.ipport = ipport;
    }

    public String getFslineId() {
        return fslineId;
    }

    public void setFslineId(String fslineId) {
        this.fslineId = fslineId;
    }

    public String getIpport() {
        return ipport;
    }

    public void setIpport(String ipport) {
        this.ipport = ipport;
    }
}
