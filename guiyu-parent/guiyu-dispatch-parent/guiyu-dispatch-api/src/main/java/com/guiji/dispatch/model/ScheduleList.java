package com.guiji.dispatch.model;

import java.util.List;

public class ScheduleList {

    //任务id集合
    private List<String> planUuid;

    //计划状态;0未计划1计划中2计划完成3暂停计划4停止计划',
    private String statusPlan;

    //同步状态;0未同步1已同步',
    private String statusSync;

    //重播条件;重播次数json格式
    private String result;

    public List<String> getPlanUuid() {
        return planUuid;
    }

    public void setPlanUuid(List<String> planUuid) {
        this.planUuid = planUuid;
    }

    public String getStatusPlan() {
        return statusPlan;
    }

    public void setStatusPlan(String statusPlan) {
        this.statusPlan = statusPlan;
    }

    public String getStatusSync() {
        return statusSync;
    }

    public void setStatusSync(String statusSync) {
        this.statusSync = statusSync;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "ScheduleList{" +
                "planUuid=" + planUuid +
                ", statusPlan='" + statusPlan + '\'' +
                ", statusSync='" + statusSync + '\'' +
                ", result='" + result + '\'' +
                '}';
    }
}