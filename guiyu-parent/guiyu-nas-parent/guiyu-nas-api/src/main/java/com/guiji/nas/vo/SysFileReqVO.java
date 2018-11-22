package com.guiji.nas.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * Created by ty on 2018/10/31.
 * 文件上传文件服务器时，除文件流外，其他附带信息
 */
@ApiModel(value="SysFileReqVO对象",description="文件上传文件服务器时，除文件流外，其他附带信息")
public class SysFileReqVO implements Serializable {

    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value="文件上传系统码",example="如：guiyu.robot")
    private String sysCode;
    @ApiModelProperty(value="上传的影像文件关联的业务ID")
    private String busiId;
    @ApiModelProperty(value="上传的影像文件业务类型")
    private String busiType;
    @ApiModelProperty(value="是否需要生成缩略图,0-无需生成，1-生成，默认不生成缩略图")
    private String thumbImageFlag;
    @ApiModelProperty(value="用户ID")
    private Long userId;
    
    /**
     * @return the sysCode
     */
    public String getSysCode() {
        return sysCode;
    }
    /**
     * @param sysCode the sysCode to set
     */
    public void setSysCode(String sysCode) {
        this.sysCode = sysCode;
    }
    /**
     * @return the thumbImageFlag
     */
    public String getThumbImageFlag() {
        return thumbImageFlag;
    }
    /**
     * @param thumbImageFlag the thumbImageFlag to set
     */
    public void setThumbImageFlag(String thumbImageFlag) {
        this.thumbImageFlag = thumbImageFlag;
    }
    /**
     * @return the busiId
     */
    public String getBusiId() {

        return busiId;
    }
    /**
     @param busiId the busiId to set
     */
    public void setBusiId(String busiId) {
        this.busiId = busiId;
    }
    /**
     * @return the busiType
     */
    public String getBusiType() {

        return busiType;
    }
    /**
     @param busiType the busiType to set
     */
    public void setBusiType(String busiType) {
        this.busiType = busiType;
    }
    /**
	 * @return the userId
	 */
	public Long getUserId() {
		return userId;
	}
	/**
	 * @param userId the userId to set
	 */
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "SysFileReqVO [sysCode=" + sysCode + ", busiId=" + busiId + ", busiType=" + busiType
				+ ", thumbImageFlag=" + thumbImageFlag + ", userId=" + userId + "]";
	}
}
