package com.guiji.wechat.util.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "wechat")
public class WeChatEnvProperties {

    private String appId;

    private String appSecret;

    private String authRedirectUrl;

    private String keFuUrl;

    private String keFuBindUrl;

    private String keFuBindAccessToken;

    private String userLoginUrl;

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getAppSecret() {
        return appSecret;
    }

    public void setAppSecret(String appSecret) {
        this.appSecret = appSecret;
    }

    public String getAuthRedirectUrl() {
        return authRedirectUrl;
    }

    public void setAuthRedirectUrl(String authRedirectUrl) {
        this.authRedirectUrl = authRedirectUrl;
    }

    public String getKeFuUrl() {
        return keFuUrl;
    }

    public void setKeFuUrl(String keFuUrl) {
        this.keFuUrl = keFuUrl;
    }

    public String getKeFuBindUrl() {
        return keFuBindUrl;
    }

    public void setKeFuBindUrl(String keFuBindUrl) {
        this.keFuBindUrl = keFuBindUrl;
    }

    public String getKeFuBindAccessToken() {
        return keFuBindAccessToken;
    }

    public void setKeFuBindAccessToken(String keFuBindAccessToken) {
        this.keFuBindAccessToken = keFuBindAccessToken;
    }

    public String getUserLoginUrl() {
        return userLoginUrl;
    }

    public void setUserLoginUrl(String userLoginUrl) {
        this.userLoginUrl = userLoginUrl;
    }
}
