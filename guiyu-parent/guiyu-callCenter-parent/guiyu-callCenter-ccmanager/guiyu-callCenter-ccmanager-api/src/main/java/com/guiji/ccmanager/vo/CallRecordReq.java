package com.guiji.ccmanager.vo;

import java.io.Serializable;

public class CallRecordReq  implements Serializable {

    private Integer pageSize;
    private Integer pageNo;
    private Integer time;
    private String accurateIntent;
    private Long userId;
    private Boolean isSuperAdmin;
    private String orgCode;

    private static final long serialVersionUID = 1L;


    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getPageNo() {
        return pageNo;
    }

    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }

    public Integer getTime() {
        return time;
    }

    public void setTime(Integer time) {
        this.time = time;
    }

    public String getAccurateIntent() {
        return accurateIntent;
    }

    public void setAccurateIntent(String accurateIntent) {
        this.accurateIntent = accurateIntent;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Boolean getSuperAdmin() {
        return isSuperAdmin;
    }

    public void setSuperAdmin(Boolean superAdmin) {
        isSuperAdmin = superAdmin;
    }

    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    @Override
    public String toString() {
        return "CallRecordReq{" +
                "pageSize=" + pageSize +
                ", pageNo=" + pageNo +
                ", time='" + time + '\'' +
                ", accurateIntent='" + accurateIntent + '\'' +
                ", userId=" + userId +
                ", isSuperAdmin=" + isSuperAdmin +
                ", orgCode='" + orgCode + '\'' +
                '}';
    }
}