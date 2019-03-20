package com.guiji.callcenter.dao.entityext;

/**
 * @Auther: 黎阳
 * @Date: 2018/12/11 0011 10:12
 * @Description:
 */
public class IntentCount {

    private int callCount;
    private String intent;
    private String callDate;

    public int getCallCount() {
        return callCount;
    }

    public void setCallCount(int callCount) {
        this.callCount = callCount;
    }

    public String getIntent() {
        return intent;
    }

    public void setIntent(String intent) {
        this.intent = intent;
    }

    public String getCallDate() {
        return callDate;
    }

    public void setCallDate(String callDate) {
        this.callDate = callDate;
    }
}
