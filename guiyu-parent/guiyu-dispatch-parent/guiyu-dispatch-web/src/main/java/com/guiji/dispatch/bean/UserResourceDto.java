package com.guiji.dispatch.bean;

/**
 * Created by ty on 2019/1/9.
 */
public class UserResourceDto {
    private String userId;//用户id

    private int count;//资源数量

    private int lineId;//线路id

    public UserResourceDto() {

    }

    public UserResourceDto(String userId,int count) {
        this.userId = userId;
        this.count = count;
    }

    public UserResourceDto(String userId,int lineId,int count) {
        this.userId = userId;
        this.lineId = lineId;
        this.count = count;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getLineId() {
        return lineId;
    }

    public void setLineId(int lineId) {
        this.lineId = lineId;
    }
}
