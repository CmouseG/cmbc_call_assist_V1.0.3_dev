package com.guiji.dispatch.model;

import java.io.Serializable;

public class IPlanThirdBatchDialVo implements Serializable {

    private static final long serialVersionUID = 3942037476073797979L;

    /**
     * 批次名称
     */
    private String batchName;

    /**
     * 接收号码
     */
    private Integer acceptCount;

    /**
     * 呼叫完成数量
     */
    private Integer endCount;

    /**
     * 计划中数量
     */
    private Integer planCount;

    public String getBatchName() {
        return batchName;
    }

    public void setBatchName(String batchName) {
        this.batchName = batchName;
    }

    public Integer getAcceptCount() {
        return acceptCount;
    }

    public void setAcceptCount(Integer acceptCount) {
        this.acceptCount = acceptCount;
    }

    public Integer getEndCount() {
        return endCount;
    }

    public void setEndCount(Integer endCount) {
        this.endCount = endCount;
    }

    public Integer getPlanCount() {
        return planCount;
    }

    public void setPlanCount(Integer planCount) {
        this.planCount = planCount;
    }
}
