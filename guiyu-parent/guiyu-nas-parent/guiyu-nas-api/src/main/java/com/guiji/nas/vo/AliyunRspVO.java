package com.guiji.nas.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * Created by ty on 2018/10/31.
 * 文件上传到文件服务器后返回的文件信息
 */
@ApiModel(value="AliyunRspVO对象",description="文件上传到文件服务器后返回的文件信息")
public class AliyunRspVO implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @ApiModelProperty(value="上传的影像文件关联的业务ID")
    private String busiId;
    @ApiModelProperty(value="上传的影像文件url")
    private String sourceUrl;
    @ApiModelProperty(value="上传的影像文件阿里云url")
    private String aliyunUrl;
    
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

	/**
	 * @return the aliyunUrl
	 */
	public String getAliyunUrl() {
		return aliyunUrl;
	}

	/**
	 * @param aliyunUrl the aliyunUrl to set
	 */
	public void setAliyunUrl(String aliyunUrl) {
		this.aliyunUrl = aliyunUrl;
	}

	@Override
	public String toString() {
		return "AliyunRspVO [busiId=" + busiId + ", aliyunUrl=" + aliyunUrl + ", sourceUrl=" + sourceUrl + "]";
	}
}
