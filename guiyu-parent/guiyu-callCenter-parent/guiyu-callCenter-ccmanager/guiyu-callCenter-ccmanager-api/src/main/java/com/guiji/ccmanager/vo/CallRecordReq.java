package com.guiji.ccmanager.vo;

import java.io.Serializable;

public class CallRecordReq  implements Serializable {

    private Integer pageSize;
    private Integer pageNo;
    private Integer time;
    private String accurateIntent;
    private Long userId;
    private Integer isDesensitization;  //用于确定是否对电话号码进行加密
    private Boolean isSuperAdmin;
    private String orgCode;
    private Integer authLevel;
    private Integer orgId;

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

    public Integer getIsDesensitization() {
        return isDesensitization;
    }

    public void setIsDesensitization(Integer isDesensitization) {
        this.isDesensitization = isDesensitization;
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
        return "CallRecordReq{" +
                "pageSize=" + pageSize +
                ", pageNo=" + pageNo +
                ", time=" + time +
                ", accurateIntent='" + accurateIntent + '\'' +
                ", userId=" + userId +
                ", secretId=" + isDesensitization +
                ", isSuperAdmin=" + isSuperAdmin +
                ", authLevel=" + authLevel +
                ", orgId=" + orgId +
                ", orgCode='" + orgCode + '\'' +
                '}';
    }
}
