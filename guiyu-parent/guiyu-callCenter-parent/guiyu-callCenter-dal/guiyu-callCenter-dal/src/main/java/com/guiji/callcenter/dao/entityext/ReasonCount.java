package com.guiji.callcenter.dao.entityext;

/**
 * @Auther: 黎阳
 * @Date: 2018/12/11 0011 10:12
 * @Description:
 */
public class ReasonCount {

    private int callCount;
    private String reason;
    private String callDate;

    public int getCallCount() {
        return callCount;
    }

    public void setCallCount(int callCount) {
        this.callCount = callCount;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getCallDate() {
        return callDate;
    }

    public void setCallDate(String callDate) {
        this.callDate = callDate;
    }
}
