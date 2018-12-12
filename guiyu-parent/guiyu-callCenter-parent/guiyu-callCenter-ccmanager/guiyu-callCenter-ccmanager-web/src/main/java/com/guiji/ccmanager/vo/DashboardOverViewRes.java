package com.guiji.ccmanager.vo;

/**
 * @Auther: 黎阳
 * @Date: 2018/12/11 0011 10:12
 * @Description:
 */
public class DashboardOverViewRes {

    private int allCalls;
    private float connectRate;
    private int notConnect;
    private int connect;
    private int duration5;
    private int duration10;
    private int duration30;
    private int durationAll;

    public int getNotConnect() {
        return notConnect;
    }

    public void setNotConnect(int notConnect) {
        this.notConnect = notConnect;
    }

    public int getConnect() {
        return connect;
    }

    public void setConnect(int connect) {
        this.connect = connect;
    }

    public int getDuration5() {
        return duration5;
    }

    public void setDuration5(int duration5) {
        this.duration5 = duration5;
    }

    public int getDuration10() {
        return duration10;
    }

    public void setDuration10(int duration10) {
        this.duration10 = duration10;
    }

    public int getDuration30() {
        return duration30;
    }

    public void setDuration30(int duration30) {
        this.duration30 = duration30;
    }

    public int getDurationAll() {
        return durationAll;
    }

    public void setDurationAll(int durationAll) {
        this.durationAll = durationAll;
    }

    public int getAllCalls() {
        return allCalls;
    }

    public void setAllCalls(int allCalls) {
        this.allCalls = allCalls;
    }

    public float getConnectRate() {
        return connectRate;
    }

    public void setConnectRate(float connectRate) {
        this.connectRate = connectRate;
    }
}
