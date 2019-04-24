package com.guiji.nas.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * Created by ty on 2018/10/31.
 * 文件上传文件服务器时，附带信息
 */
@ApiModel(value="AliyunReqVO对象",description="文件上传文件服务器时，除文件流外，其他附带信息")
public class AliyunReqVO implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @ApiModelProperty(value="上传的影像文件关联的业务ID")
    private String busiId;
    @ApiModelProperty(value="上传的影像文件Url")
    private String sourceUrl;
    
	/**
	 * @return the busiId
	 */
	public String getBusiId() {
		return busiId;
	}

	/**
	 * @param busiId the busiId to set
	 */
	public void setBusiId(String busiId) {
		this.busiId = busiId;
	}

	/**
	 * @return the sourceUrl
	 */
	public String getSourceUrl() {
		return sourceUrl;
	}

	/**
	 * @param sourceUrl the sourceUrl to set
	 */
	public void setSourceUrl(String sourceUrl) {
		this.sourceUrl = sourceUrl;
	}

	@Override
	public String toString() {
		return "AliyunReqVO [busiId=" + busiId + ", sourceUrl=" + sourceUrl + "]";
	}
}
