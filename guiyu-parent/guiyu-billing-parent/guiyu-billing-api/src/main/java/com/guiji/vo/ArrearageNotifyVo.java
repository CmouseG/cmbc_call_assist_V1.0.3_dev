package com.guiji.vo;

import java.io.Serializable;
import java.util.List;


public class ArrearageNotifyVo implements Serializable {

    private static final long serialVersionUID = -3596604304958935194L;
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
