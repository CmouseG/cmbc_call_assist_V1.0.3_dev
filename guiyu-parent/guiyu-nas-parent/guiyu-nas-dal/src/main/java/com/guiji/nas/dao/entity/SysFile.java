package com.guiji.nas.dao.entity;

import java.io.Serializable;
import java.util.Date;

public class SysFile implements Serializable {
    private Long id;

    private String fileName;

    private String busiId;

    private String busiType;

    private String fileType;

    private Double fileSize;

    private String skUrl;

    private String skThumbImageUrl;

    private String sysCode;

    private Long crtUser;

    private Date crtTime;

    private Long lstUpdateUser;

    private Date lstUpdateTime;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName == null ? null : fileName.trim();
    }

    public String getBusiId() {
        return busiId;
    }

    public void setBusiId(String busiId) {
        this.busiId = busiId == null ? null : busiId.trim();
    }

    public String getBusiType() {
        return busiType;
    }

    public void setBusiType(String busiType) {
        this.busiType = busiType == null ? null : busiType.trim();
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType == null ? null : fileType.trim();
    }

    public Double getFileSize() {
        return fileSize;
    }

    public void setFileSize(Double fileSize) {
        this.fileSize = fileSize;
    }

    public String getSkUrl() {
        return skUrl;
    }

    public void setSkUrl(String skUrl) {
        this.skUrl = skUrl == null ? null : skUrl.trim();
    }

    public String getSkThumbImageUrl() {
        return skThumbImageUrl;
    }

    public void setSkThumbImageUrl(String skThumbImageUrl) {
        this.skThumbImageUrl = skThumbImageUrl == null ? null : skThumbImageUrl.trim();
    }

    public String getSysCode() {
        return sysCode;
    }

    public void setSysCode(String sysCode) {
        this.sysCode = sysCode == null ? null : sysCode.trim();
    }

    public Long getCrtUser() {
        return crtUser;
    }

    public void setCrtUser(Long crtUser) {
        this.crtUser = crtUser;
    }

    public Date getCrtTime() {
        return crtTime;
    }

    public void setCrtTime(Date crtTime) {
        this.crtTime = crtTime;
    }

    public Long getLstUpdateUser() {
        return lstUpdateUser;
    }

    public void setLstUpdateUser(Long lstUpdateUser) {
        this.lstUpdateUser = lstUpdateUser;
    }

    public Date getLstUpdateTime() {
        return lstUpdateTime;
    }

    public void setLstUpdateTime(Date lstUpdateTime) {
        this.lstUpdateTime = lstUpdateTime;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", fileName=").append(fileName);
        sb.append(", busiId=").append(busiId);
        sb.append(", busiType=").append(busiType);
        sb.append(", fileType=").append(fileType);
        sb.append(", fileSize=").append(fileSize);
        sb.append(", skUrl=").append(skUrl);
        sb.append(", skThumbImageUrl=").append(skThumbImageUrl);
        sb.append(", sysCode=").append(sysCode);
        sb.append(", crtUser=").append(crtUser);
        sb.append(", crtTime=").append(crtTime);
        sb.append(", lstUpdateUser=").append(lstUpdateUser);
        sb.append(", lstUpdateTime=").append(lstUpdateTime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}