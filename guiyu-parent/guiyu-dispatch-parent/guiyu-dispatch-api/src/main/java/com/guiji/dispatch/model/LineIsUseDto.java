package com.guiji.dispatch.model;

import java.io.Serializable;
import java.util.List;

public class LineIsUseDto implements Serializable {

    private static final long serialVersionUID = 6409867136566071528L;

    private Integer lineId;

    private List<Integer> userIdList;

    public Integer getLineId() {
        return lineId;
    }

    public void setLineId(Integer lineId) {
        this.lineId = lineId;
    }

    public List<Integer> getUserIdList() {
        return userIdList;
    }

    public void setUserIdList(List<Integer> userIdList) {
        this.userIdList = userIdList;
    }
}
