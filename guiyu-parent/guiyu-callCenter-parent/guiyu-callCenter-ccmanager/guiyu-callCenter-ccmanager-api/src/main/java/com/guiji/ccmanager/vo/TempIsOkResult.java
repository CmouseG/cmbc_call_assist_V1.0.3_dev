package com.guiji.ccmanager.vo;

import java.io.Serializable;
import java.util.List;

public class TempIsOkResult implements Serializable {

    private Boolean allTempOk;
    private List<TempServerList> tempServerList;

    public Boolean getAllTempOk() {
        return allTempOk;
    }

    public void setAllTempOk(Boolean allTempOk) {
        this.allTempOk = allTempOk;
    }

    public List<TempServerList> getTempServerList() {
        return tempServerList;
    }

    public void setTempServerList(List<TempServerList> tempServerList) {
        this.tempServerList = tempServerList;
    }

    @Override
    public String toString() {
        return "TempIsOkResult{" +
                "allTempOk=" + allTempOk +
                ", tempServerList=" + tempServerList +
                '}';
    }
}
