package com.guiji.callcenter.dao.entityext;

import java.io.Serializable;

public class LineMonitorRreport implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer lineId;
    private Integer answerNum;
    private Integer totalNum;
    private Float low;
    private Float high;
    private Float rate;
    private Float history;
    private String status;
    private String sip_ip;
    private String sip_port;
    private String lineName;

    public Integer getLineId() {
        return lineId;
    }

    public void setLineId(Integer lineId) {
        this.lineId = lineId;
    }

    public Integer getAnswerNum() {
        return answerNum;
    }

    public void setAnswerNum(Integer answerNum) {
        this.answerNum = answerNum;
    }

    public Integer getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(Integer totalNum) {
        this.totalNum = totalNum;
    }

    public Float getLow() {
        return low;
    }

    public void setLow(Float low) {
        this.low = low;
    }

    public Float getHigh() {
        return high;
    }

    public void setHigh(Float high) {
        this.high = high;
    }

    public Float getRate() {
        return rate;
    }

    public void setRate(Float rate) {
        this.rate = rate;
    }

    public Float getHistory() {
        return history;
    }

    public void setHistory(Float history) {
        this.history = history;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSip_ip() {
        return sip_ip;
    }

    public void setSip_ip(String sip_ip) {
        this.sip_ip = sip_ip;
    }

    public String getSip_port() {
        return sip_port;
    }

    public void setSip_port(String sip_port) {
        this.sip_port = sip_port;
    }

    public String getLineName() {
        return lineName;
    }

    public void setLineName(String lineName) {
        this.lineName = lineName;
    }
}
