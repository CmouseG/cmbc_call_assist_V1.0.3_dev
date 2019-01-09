package com.guiji.ccmanager.vo;

import java.io.Serializable;
import java.util.List;

public class TempServerList implements Serializable {

    private String tempId;
    private List<String> OkServerList;
    private List<String> NotOkServerList;

    public String getTempId() {
        return tempId;
    }

    public void setTempId(String tempId) {
        this.tempId = tempId;
    }

    public List<String> getOkServerList() {
        return OkServerList;
    }

    public void setOkServerList(List<String> okServerList) {
        OkServerList = okServerList;
    }

    public List<String> getNotOkServerList() {
        return NotOkServerList;
    }

    public void setNotOkServerList(List<String> notOkServerList) {
        NotOkServerList = notOkServerList;
    }

    public TempServerList(String tempId, List<String> okServerList, List<String> notOkServerList) {
        this.tempId = tempId;
        OkServerList = okServerList;
        NotOkServerList = notOkServerList;
    }

    @Override
    public String toString() {
        return "TempServerList{" +
                "tempId='" + tempId + '\'' +
                ", OkServerList=" + OkServerList +
                ", NotOkServerList=" + NotOkServerList +
                '}';
    }
}
