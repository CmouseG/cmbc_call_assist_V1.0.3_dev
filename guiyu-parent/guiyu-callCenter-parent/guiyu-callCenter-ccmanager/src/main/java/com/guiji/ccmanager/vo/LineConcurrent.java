package com.guiji.ccmanager.vo;

/**
 * @Auther: 黎阳
 * @Date: 2018/10/29 0029 16:38
 * @Description:
 */
public class LineConcurrent {

    private String lineId;
    private int concurrent;

    public String getLineId() {
        return lineId;
    }

    public void setLineId(String lineId) {
        this.lineId = lineId;
    }

    public int getConcurrent() {
        return concurrent;
    }

    public void setConcurrent(int concurrent) {
        this.concurrent = concurrent;
    }

    @Override
    public String toString() {
        return "LineConcurrent{" +
                "lineId='" + lineId + '\'' +
                ", concurrent=" + concurrent +
                '}';
    }
}
