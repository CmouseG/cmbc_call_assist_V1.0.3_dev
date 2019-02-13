package com.guiji.auth.model;

import java.io.Serializable;

/**
 * Created by ty on 2019/1/30.
 */
public class UserIdVo implements Serializable {
    private Long userId;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
