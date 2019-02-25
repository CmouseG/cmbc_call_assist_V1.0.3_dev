package com.guiji.dispatch.vo;

import java.io.Serializable;

/**
 * 统计计划数量
 */
public class TotalPlanCountVo implements Serializable {

    private static final long serialVersionUID = -3834803184189453254L;
    /**
     * 总数
     */
    private Integer totalCount;

    /**
     * 进行中计划数量
     */
    private Integer doingCount;

    /**
     * 已完成计划数量
     */
    private Integer finishCount;

    /**
     * 暂停计划数量
     */
    private Integer suspendCount;

    /**
     * 停止计划数
     */
    private Integer stopCount;

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    public Integer getDoingCount() {
        return doingCount;
    }

    public void setDoingCount(Integer doingCount) {
        this.doingCount = doingCount;
    }

    public Integer getFinishCount() {
        return finishCount;
    }

    public void setFinishCount(Integer finishCount) {
        this.finishCount = finishCount;
    }

    public Integer getSuspendCount() {
        return suspendCount;
    }

    public void setSuspendCount(Integer suspendCount) {
        this.suspendCount = suspendCount;
    }

    public Integer getStopCount() {
        return stopCount;
    }

    public void setStopCount(Integer stopCount) {
        this.stopCount = stopCount;
    }
}
