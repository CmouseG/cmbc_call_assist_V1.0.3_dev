package com.guiji.callcenter.dao.entity;

import java.io.Serializable;

public class ReportCallCount implements Serializable {
    private Long id;

    private String callDate;

    private Integer countA;

    private Integer countB;

    private Integer countC;

    private Integer countD;

    private Integer countE;

    private Integer countF;

    private Integer countU;

    private Integer countV;

    private Integer countW;

    private Integer countAll;

    private String accurateIntent;

    private String customerId;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCallDate() {
        return callDate;
    }

    public void setCallDate(String callDate) {
        this.callDate = callDate == null ? null : callDate.trim();
    }

    public Integer getCountA() {
        return countA;
    }

    public void setCountA(Integer countA) {
        this.countA = countA;
    }

    public Integer getCountB() {
        return countB;
    }

    public void setCountB(Integer countB) {
        this.countB = countB;
    }

    public Integer getCountC() {
        return countC;
    }

    public void setCountC(Integer countC) {
        this.countC = countC;
    }

    public Integer getCountD() {
        return countD;
    }

    public void setCountD(Integer countD) {
        this.countD = countD;
    }

    public Integer getCountE() {
        return countE;
    }

    public void setCountE(Integer countE) {
        this.countE = countE;
    }

    public Integer getCountF() {
        return countF;
    }

    public void setCountF(Integer countF) {
        this.countF = countF;
    }

    public Integer getCountU() {
        return countU;
    }

    public void setCountU(Integer countU) {
        this.countU = countU;
    }

    public Integer getCountV() {
        return countV;
    }

    public void setCountV(Integer countV) {
        this.countV = countV;
    }

    public Integer getCountW() {
        return countW;
    }

    public void setCountW(Integer countW) {
        this.countW = countW;
    }

    public Integer getCountAll() {
        return countAll;
    }

    public void setCountAll(Integer countAll) {
        this.countAll = countAll;
    }

    public String getAccurateIntent() {
        return accurateIntent;
    }

    public void setAccurateIntent(String accurateIntent) {
        this.accurateIntent = accurateIntent == null ? null : accurateIntent.trim();
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId == null ? null : customerId.trim();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", callDate=").append(callDate);
        sb.append(", countA=").append(countA);
        sb.append(", countB=").append(countB);
        sb.append(", countC=").append(countC);
        sb.append(", countD=").append(countD);
        sb.append(", countE=").append(countE);
        sb.append(", countF=").append(countF);
        sb.append(", countU=").append(countU);
        sb.append(", countV=").append(countV);
        sb.append(", countW=").append(countW);
        sb.append(", countAll=").append(countAll);
        sb.append(", accurateIntent=").append(accurateIntent);
        sb.append(", customerId=").append(customerId);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}