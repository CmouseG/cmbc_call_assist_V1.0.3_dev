package com.guiji.sysoperalog.vo;

import java.io.Serializable;
import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * sys_user_action请求对象
 * Created by ty on 2018/11/13.
 */
@ApiModel(value="SysUserActionVO对象",description="sys_user_action请求对象")
public class SysUserActionVO implements Serializable
{
	
	@ApiModelProperty(value="行为名称")
    private String actionName;

	@ApiModelProperty(value="操作用户ID")
    private Long userId;

	@ApiModelProperty(value="操作时间")
    private Date operateTime;

	@ApiModelProperty(value="操作URL")
    private String url;

	@ApiModelProperty(value="用户提交的数据")
    private String data;

    private static final long serialVersionUID = 1L;

    public String getActionName() {
        return actionName;
    }

    public void setActionName(String actionName) {
        this.actionName = actionName == null ? null : actionName.trim();
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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data == null ? null : data.trim();
    }
    
}
