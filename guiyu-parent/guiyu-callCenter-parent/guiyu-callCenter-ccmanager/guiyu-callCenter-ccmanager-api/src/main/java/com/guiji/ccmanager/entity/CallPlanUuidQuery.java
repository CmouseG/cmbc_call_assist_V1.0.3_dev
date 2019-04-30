package com.guiji.ccmanager.entity;

import java.util.List;

/**
 * author:liyang
 * Date:2019/4/19 17:39
 * Description:
 */
public class CallPlanUuidQuery {

    private List<String> callIds;
    //查询人的authLevel
    private Integer authLevel;
    //查询人的orgId
    private Integer orgId;

    public List<String> getCallIds() {
        return callIds;
    }

    public void setCallIds(List<String> callIds) {
        this.callIds = callIds;
    }

    public Integer getAuthLevel() {
        return authLevel;
    }

    public void setAuthLevel(Integer authLevel) {
        this.authLevel = authLevel;
    }

    public Integer getOrgId() {
        return orgId;
    }

    public void setOrgId(Integer orgId) {
        this.orgId = orgId;
    }

    @Override
    public String toString() {
        return "CallPlanUuidQuery{" +
                "callIds=" + callIds +
                ", authLevel=" + authLevel +
                ", orgId=" + orgId +
                '}';
    }
}
