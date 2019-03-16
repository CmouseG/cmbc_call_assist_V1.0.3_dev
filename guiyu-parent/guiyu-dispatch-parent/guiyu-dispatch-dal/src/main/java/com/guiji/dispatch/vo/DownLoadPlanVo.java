package com.guiji.dispatch.vo;

import com.guiji.dispatch.dao.entity.DispatchPlan;

public class DownLoadPlanVo extends DispatchPlan {

    private static final long serialVersionUID = -4940688693573354764L;

    private String addTime;

    public String getAddTime() {
        return addTime;
    }

    public void setAddTime(String addTime) {
        this.addTime = addTime;
    }
}
