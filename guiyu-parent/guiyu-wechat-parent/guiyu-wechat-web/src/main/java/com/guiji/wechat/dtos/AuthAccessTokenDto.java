package com.guiji.wechat.dtos;

public class AuthAccessTokenDto {
    /**
     * 网页授权接口调用凭证,注意：此access_token与基础支持的access_token不同
     */
    private String access_token;

    /**
     * 凭证有效时间，单位：秒
     */
    private long expires_in;

    /**
     * 用户的标识，对当前公众号唯一
     */
    private String openid;

    /**
     * 用户刷新access_token
     */
    private String refresh_token;

    /**
     * 用户授权的作用域，使用逗号（,）分隔
     */
    private String scope;

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public long getExpires_in() {
        return expires_in;
    }

    public void setExpires_in(long expires_in) {
        this.expires_in = expires_in;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getRefresh_token() {
        return refresh_token;
    }

    public void setRefresh_token(String refresh_token) {
        this.refresh_token = refresh_token;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }
}
