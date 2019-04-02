package com.guiji.wechat.util.properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource(value = "classpath:properties/wechat.properties")
public class WeChatUrlProperty {

    @Value("${access_token_url}")
    private String accessTokenUrl;

    @Value("${qrcode_create_url}")
    private String qrCodeCreateUrl;

    @Value("${qrcode_show_url}")
    private String qrCodeShowUrl;

    @Value("${template_message_url}")
    private String templateMessageUrl;

    @Value("${single_wechat_user_info_url}")
    private String singleWeChatUserInfoUrl;

    @Value("${custom_menu_create_url}")
    private String customMenuCreateUrl;

    @Value("${custom_menu_get_url}")
    private String customMenuGetUrl;

    @Value("${custom_menu_delete_url}")
    private String customMenuDeleteUrl;

    @Value("${auth_url}")
    private String authUrl;

    @Value("${auth_access_token_url}")
    private String authAccessTokenUrl;

    public String getAccessTokenUrl() {
        return accessTokenUrl;
    }

    public void setAccessTokenUrl(String accessTokenUrl) {
        this.accessTokenUrl = accessTokenUrl;
    }

    public String getQrCodeCreateUrl() {
        return qrCodeCreateUrl;
    }

    public void setQrCodeCreateUrl(String qrCodeCreateUrl) {
        this.qrCodeCreateUrl = qrCodeCreateUrl;
    }

    public String getQrCodeShowUrl() {
        return qrCodeShowUrl;
    }

    public void setQrCodeShowUrl(String qrCodeShowUrl) {
        this.qrCodeShowUrl = qrCodeShowUrl;
    }

    public String getTemplateMessageUrl() {
        return templateMessageUrl;
    }

    public void setTemplateMessageUrl(String templateMessageUrl) {
        this.templateMessageUrl = templateMessageUrl;
    }

    public String getSingleWeChatUserInfoUrl() {
        return singleWeChatUserInfoUrl;
    }

    public void setSingleWeChatUserInfoUrl(String singleWeChatUserInfoUrl) {
        this.singleWeChatUserInfoUrl = singleWeChatUserInfoUrl;
    }

    public String getCustomMenuCreateUrl() {
        return customMenuCreateUrl;
    }

    public void setCustomMenuCreateUrl(String customMenuCreateUrl) {
        this.customMenuCreateUrl = customMenuCreateUrl;
    }

    public String getCustomMenuGetUrl() {
        return customMenuGetUrl;
    }

    public void setCustomMenuGetUrl(String customMenuGetUrl) {
        this.customMenuGetUrl = customMenuGetUrl;
    }

    public String getCustomMenuDeleteUrl() {
        return customMenuDeleteUrl;
    }

    public void setCustomMenuDeleteUrl(String customMenuDeleteUrl) {
        this.customMenuDeleteUrl = customMenuDeleteUrl;
    }

    public String getAuthUrl() {
        return authUrl;
    }

    public void setAuthUrl(String authUrl) {
        this.authUrl = authUrl;
    }

    public String getAuthAccessTokenUrl() {
        return authAccessTokenUrl;
    }

    public void setAuthAccessTokenUrl(String authAccessTokenUrl) {
        this.authAccessTokenUrl = authAccessTokenUrl;
    }
}
