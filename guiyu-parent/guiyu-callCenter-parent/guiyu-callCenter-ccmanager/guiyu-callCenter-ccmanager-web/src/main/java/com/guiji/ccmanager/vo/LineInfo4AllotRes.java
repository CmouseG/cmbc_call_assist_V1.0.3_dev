package com.guiji.ccmanager.vo;

/**
 * @Auther: 黎阳
 * @Date: 2018/12/18 0018 10:58
 * @Description:
 */
public class LineInfo4AllotRes {

    private Integer lineId;

    private Boolean isAlloted;

    private String lineName;


    public Integer getLineId() {
        return lineId;
    }

    public void setLineId(Integer lineId) {
        this.lineId = lineId;
    }

    public Boolean getAlloted() {
        return isAlloted;
    }

    public void setAlloted(Boolean alloted) {
        isAlloted = alloted;
    }

    public String getLineName() {
        return lineName;
    }

    public void setLineName(String lineName) {
        this.lineName = lineName;
    }
}
