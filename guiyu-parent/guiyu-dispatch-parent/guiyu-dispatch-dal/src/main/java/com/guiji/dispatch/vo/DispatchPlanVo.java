package com.guiji.dispatch.vo;

import com.guiji.dispatch.dao.entity.DispatchPlan;

public class DispatchPlanVo extends DispatchPlan {

    private static final long serialVersionUID = 5693597267671696877L;

    private String lineIdArr;

    private String lineNameArr;

    private String phoneDes;

    public String getLineIdArr() {
        return lineIdArr;
    }

    public void setLineIdArr(String lineIdArr) {
        this.lineIdArr = lineIdArr;
    }

    public String getLineNameArr() {
        return lineNameArr;
    }

    public void setLineNameArr(String lineNameArr) {
        this.lineNameArr = lineNameArr;
    }

    public String getPhoneDes() {
        return phoneDes;
    }

    public void setPhoneDes(String phoneDes) {
        this.phoneDes = phoneDes;
    }
}
