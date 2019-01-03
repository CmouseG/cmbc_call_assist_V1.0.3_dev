package com.guiji.nas.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * 文件信息查询请求对象
 * Created by ty on 2018/10/31.
 */
@ApiModel(value="SysFileQueryReqVO对象",description="文件查询请求对象")
public class SysFileQueryReqVO implements Serializable {

    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value="文件ID")
    private Long id;
    @ApiModelProperty(value="文件上传系统码",example="如：guiyu.robot")
    private String sysCode;
    @ApiModelProperty(value="上传的影像文件关联的业务ID")
    private String busiId;
    @ApiModelProperty(value="上传的影像文件业务类型")
    private String busiType;
    /**
     * @return the id
     */
    public Long getId() {

        return id;
    }
    /**
     @param id the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }
    /**
     * @return the sysCode
     */
    public String getSysCode() {

        return sysCode;
    }
    /**
     @param sysCode the sysCode to set
     */
    public void setSysCode(String sysCode) {
        this.sysCode = sysCode;
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
    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "SysFileQueryReqVO [id=" + id + ", sysCode=" + sysCode + ", busiId=" + busiId + ", busiType=" + busiType
                + "]";
    }
}
