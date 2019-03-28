package com.guiji.dispatch.model;

import java.io.Serializable;

public class ExportFileDto implements Serializable {

    /**
     *  业务类型 02-任务中心  03-呼叫中心
     */
    private String busiType        ;

    /**
     * 业务类型唯一标识ID
     */
    private String busiId;

    /**
     *  文件类型 1-execl文件  2-音频文件 3-视频文件
     */
    private Integer fileType        ;

    /**
     *  总数量
     */
    private Integer totalNum;

    /**
     * 文件原始地址
     */
    private String fileOriginalUrl;

    /**
     * 导出操作人ID
     */
    private String userId;

    /**
     * 导出操作人所属orgCode
     */
    private String orgCode;

    /**
     * 创建者
     */
    private String createName;

    /**
     * 创建日期
     */
    private String createTime;

    public String getBusiType() {
        return busiType;
    }

    public void setBusiType(String busiType) {
        this.busiType = busiType;
    }

    public String getBusiId() {
        return busiId;
    }

    public void setBusiId(String busiId) {
        this.busiId = busiId;
    }

    public Integer getFileType() {
        return fileType;
    }

    public void setFileType(Integer fileType) {
        this.fileType = fileType;
    }

    public Integer getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(Integer totalNum) {
        this.totalNum = totalNum;
    }

    public String getFileOriginalUrl() {
        return fileOriginalUrl;
    }

    public void setFileOriginalUrl(String fileOriginalUrl) {
        this.fileOriginalUrl = fileOriginalUrl;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    public String getCreateName() {
        return createName;
    }

    public void setCreateName(String createName) {
        this.createName = createName;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
