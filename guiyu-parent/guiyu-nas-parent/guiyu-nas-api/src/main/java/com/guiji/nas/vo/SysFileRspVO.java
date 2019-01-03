package com.guiji.nas.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * Created by ty on 2018/10/31.
 * 文件上传到文件服务器后返回的文件信息
 */
@ApiModel(value="SysFileRspVO对象",description="文件上传到文件服务器后返回的文件信息")
public class SysFileRspVO implements Serializable {

    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value="ID",required=true)
    private Long id;
    @ApiModelProperty(value="文件名称",required=true)
    private String fileName;
    @ApiModelProperty(value="文件类型",example="JPG,JPEG,DOC")
    private String fileType;
    @ApiModelProperty(value="文件业务编号")
    private String busiId;
    @ApiModelProperty(value="文件业务类型")
    private String busiType;
    @ApiModelProperty(value="文件大小")
    private double fileSize;
    @ApiModelProperty(value="文件系统URL",required=true)
    private String skUrl;
    @ApiModelProperty(value="缩略图URL")
    private String skThumbImageUrl;
    @ApiModelProperty(value="文件上传系统码")
    private String sysCode;
    @ApiModelProperty(value="文件创建人")
    private Integer crtUser;
    @ApiModelProperty(value="文件创建时间")
    private String crtTime;
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
     * @return the fileName
     */
    public String getFileName() {

        return fileName;
    }
    /**
     @param fileName the fileName to set
     */
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
    /**
     * @return the fileType
     */
    public String getFileType() {

        return fileType;
    }
    /**
     @param fileType the fileType to set
     */
    public void setFileType(String fileType) {
        this.fileType = fileType;
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
     * @return the fileSize
     */
    public double getFileSize() {

        return fileSize;
    }
    /**
     @param fileSize the fileSize to set
     */
    public void setFileSize(double fileSize) {
        this.fileSize = fileSize;
    }
    /**
     * @return the skUrl
     */
    public String getSkUrl() {

        return skUrl;
    }
    /**
     @param skUrl the skUrl to set
     */
    public void setSkUrl(String skUrl) {
        this.skUrl = skUrl;
    }
    /**
     * @return the skThumbImageUrl
     */
    public String getSkThumbImageUrl() {

        return skThumbImageUrl;
    }
    /**
     @param skThumbImageUrl the skThumbImageUrl to set
     */
    public void setSkThumbImageUrl(String skThumbImageUrl) {
        this.skThumbImageUrl = skThumbImageUrl;
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
     * @return the crtUser
     */
    public Integer getCrtUser() {

        return crtUser;
    }
    /**
     @param crtUser the crtUser to set
     */
    public void setCrtUser(Integer crtUser) {
        this.crtUser = crtUser;
    }
    /**
     * @return the crtTime
     */
    public String getCrtTime() {

        return crtTime;
    }
    /**
     @param crtTime the crtTime to set
     */
    public void setCrtTime(String crtTime) {
        this.crtTime = crtTime;
    }
    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "SysFileRspVO [id=" + id + ", fileName=" + fileName + ", fileType=" + fileType + ", busiId=" + busiId
                + ", busiType=" + busiType + ", fileSize=" + fileSize + ", skUrl=" + skUrl + ", skThumbImageUrl="
                + skThumbImageUrl + ", sysCode=" + sysCode + ", crtUser=" + crtUser + ", crtTime=" + crtTime + "]";
    }
}
