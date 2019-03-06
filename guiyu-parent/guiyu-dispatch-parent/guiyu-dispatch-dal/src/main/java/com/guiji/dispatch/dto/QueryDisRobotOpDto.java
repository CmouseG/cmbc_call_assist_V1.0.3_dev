package com.guiji.dispatch.dto;

import com.guiji.dispatch.sys.PageDto;

public class QueryDisRobotOpDto extends PageDto {

    private static final long serialVersionUID = 5153237403382931446L;
    /**
     * 话术用户ID
     */
    private String botstenceUserId;

    /**
     * 话术模板ID
     */
    private String botstenceId;

    public String getBotstenceUserId() {
        return botstenceUserId;
    }

    public void setBotstenceUserId(String botstenceUserId) {
        this.botstenceUserId = botstenceUserId;
    }

    public String getBotstenceId() {
        return botstenceId;
    }

    public void setBotstenceId(String botstenceId) {
        this.botstenceId = botstenceId;
    }
}
