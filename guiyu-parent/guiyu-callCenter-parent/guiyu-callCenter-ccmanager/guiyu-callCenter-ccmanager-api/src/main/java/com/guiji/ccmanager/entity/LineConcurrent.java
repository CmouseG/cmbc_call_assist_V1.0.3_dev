package com.guiji.ccmanager.entity;

/**
 * @Auther: 黎阳
 * @Date: 2018/10/29 0029 16:38
 * @Description:
 */
public class LineConcurrent {

    private String lineId;
    private int concurrent;
    private String lineName;

    public String getLineId() {
        return lineId;
    }

    public void setLineId(String lineId) {
        this.lineId = lineId;
    }

    public int getConcurrent() {
        return concurrent;
    }

    public String getLineName() {
        return lineName;
    }

    public void setLineName(String lineName) {
        this.lineName = lineName;
    }

    public void setConcurrent(int concurrent) {
        this.concurrent = concurrent;
    }

}
