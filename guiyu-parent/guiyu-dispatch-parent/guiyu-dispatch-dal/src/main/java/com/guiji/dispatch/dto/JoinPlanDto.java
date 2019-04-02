package com.guiji.dispatch.dto;

import com.guiji.dispatch.dao.entity.DispatchPlan;
import com.guiji.dispatch.sys.BaseDto;

public class JoinPlanDto extends BaseDto {

    private static final long serialVersionUID = 5071774609764290676L;

    /**
     * 条件参数
     */
    private OptPlanDto optPlan;

    /**
     * 导入计划参数
     */
    private DispatchPlan dispatchPlan;

    public OptPlanDto getOptPlan() {
        return optPlan;
    }

    public void setOptPlan(OptPlanDto optPlan) {
        this.optPlan = optPlan;
    }

    public DispatchPlan getDispatchPlan() {
        return dispatchPlan;
    }

    public void setDispatchPlan(DispatchPlan dispatchPlan) {
        this.dispatchPlan = dispatchPlan;
    }
}
