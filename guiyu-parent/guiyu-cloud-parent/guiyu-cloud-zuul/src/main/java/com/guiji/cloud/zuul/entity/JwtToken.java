package com.guiji.cloud.zuul.entity;

import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;

/**
 * 鉴权用的token vo ，实现 AuthenticationToken
 */
public class JwtToken implements AuthenticationToken {
    private String token;
    private Long userId;
    public JwtToken(String token) {
        this.token = token;
    }

    @Override
    public Object getPrincipal() {
        return null;
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    public String getToken() {
        return token;
    }
    public void setToken(String token) {
        this.token = token;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
