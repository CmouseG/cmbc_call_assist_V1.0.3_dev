package com.guiji.auth.model;

import java.io.Serializable;

/**
 * Created by ty on 2019/1/30.
 */
public class UserIdVo implements Serializable {
    private String userId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
