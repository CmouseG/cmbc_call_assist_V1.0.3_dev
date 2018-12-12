package com.guiji.component.aspect;

import java.io.Serializable;
import java.util.Date;

public class SysUserAction implements Serializable {
    private Long id;

    private String operatype;

    private String operatarget;

    private Long userId;

    private Date operateTime;

    private String data;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOperatype() {
        return operatype;
    }

    public void setOperatype(String operatype) {
        this.operatype = operatype == null ? null : operatype.trim();
    }

    public String getOperatarget() {
        return operatarget;
    }

    public void setOperatarget(String operatarget) {
        this.operatarget = operatarget == null ? null : operatarget.trim();
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Date getOperateTime() {
        return operateTime;
    }

    public void setOperateTime(Date operateTime) {
        this.operateTime = operateTime;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data == null ? null : data.trim();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", operatype=").append(operatype);
        sb.append(", operatarget=").append(operatarget);
        sb.append(", userId=").append(userId);
        sb.append(", operateTime=").append(operateTime);
        sb.append(", data=").append(data);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}