package com.guiji.dispatch.dto;

public class QueryDownloadPlanListDto extends QueryPlanListDto {

    /**
     * 开始索引
     */
    private int startIdx;

    /**
     * 结束索引
     */
    private int endIdx;

    public int getStartIdx() {
        return startIdx;
    }

    public void setStartIdx(int startIdx) {
        this.startIdx = startIdx;
    }

    public int getEndIdx() {
        return endIdx;
    }

    public void setEndIdx(int endIdx) {
        this.endIdx = endIdx;
    }
}
