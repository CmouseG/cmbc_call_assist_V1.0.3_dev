package com.guiji.billing.vo;

import java.io.Serializable;
import java.util.List;


public class ArrearageNotifyVo implements Serializable {

    /**
     * 企业组织Code
     */
    private List<String> userIdList;

    /**
     * 是否欠费  0：正常  -1：欠费
     */
    private Integer isArrearage;

    public List<String> getUserIdList() {
        return userIdList;
    }

    public void setUserIdList(List<String> userIdList) {
        this.userIdList = userIdList;
    }

    public Integer getIsArrearage() {
        return isArrearage;
    }

    public void setIsArrearage(Integer isArrearage) {
        this.isArrearage = isArrearage;
    }
}
