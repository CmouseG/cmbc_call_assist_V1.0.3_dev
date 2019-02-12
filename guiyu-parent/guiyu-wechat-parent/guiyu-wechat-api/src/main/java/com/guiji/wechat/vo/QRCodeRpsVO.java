package com.guiji.wechat.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

@ApiModel(value = "QRCodeRpsVO对象", description = "二维码返回参数对象")
public class QRCodeRpsVO implements Serializable {

    private static final long serialVersionUID = 7491756040578181728L;
    /**
     * 获取的二维码ticket，凭借此ticket可以在有效时间内换取二维码。
     */
    @ApiModelProperty(value = "ticket获取的二维码ticket，凭借此ticket可以在有效时间内换取二维码。")
    private String ticket;

    /**
     * 该二维码有效时间，以秒为单位。 最大不超过2592000（即30天）。
     */
    @ApiModelProperty(value = "expire_seconds该二维码有效时间，以秒为单位。 最大不超过2592000（即30天）。")
    private Long expire_seconds;

    /**
     * 二维码图片解析后的地址，开发者可根据该地址自行生成需要的二维码图片
     */
    @ApiModelProperty(value = "url二维码图片解析后的地址")
    private String url;

    @ApiModelProperty(value = "qrCodeBytes二维码图片字节流")
    private byte[] qrCodeBytes;

    public String getTicket() {
        return ticket;
    }

    public void setTicket(String ticket) {
        this.ticket = ticket;
    }

    public Long getExpire_seconds() {
        return expire_seconds;
    }

    public void setExpire_seconds(Long expire_seconds) {
        this.expire_seconds = expire_seconds;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public byte[] getQrCodeBytes() {
        return qrCodeBytes;
    }

    public void setQrCodeBytes(byte[] qrCodeBytes) {
        this.qrCodeBytes = qrCodeBytes;
    }
}
