package com.guiji.calloutserver.entity;

public class CallEndIntent {

    //是否结束
    private boolean isEnd;
    //意向
    private String intent;

    public boolean isEnd() {
        return isEnd;
    }

    public void setEnd(boolean end) {
        isEnd = end;
    }

    public String getIntent() {
        return intent;
    }

    public void setIntent(String intent) {
        this.intent = intent;
    }

    @Override
    public String toString() {
        return "CallEndIntent{" +
                "isEnd=" + isEnd +
                ", intent='" + intent + '\'' +
                '}';
    }
}
