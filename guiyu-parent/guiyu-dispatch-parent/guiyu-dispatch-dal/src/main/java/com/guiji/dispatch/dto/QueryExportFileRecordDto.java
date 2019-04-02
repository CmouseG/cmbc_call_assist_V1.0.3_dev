package com.guiji.dispatch.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.guiji.dispatch.sys.PageDto;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class QueryExportFileRecordDto extends PageDto {

    private static final long serialVersionUID = -4756419065477693025L;

    /**
     * 记录ID唯一标识
     */
    private String recordId;

    /**
     * 业务唯一标识ID
     */
    private String busiId;

    /**
     * 业务类型  02-任务中心  03-呼叫中心
     */
    private String busiType;

    /**
     * 文件类型  1-execl文件 2-音频文件 3-视频文件
     */
    private Integer fileType;

    /**
     * 状态 0-进行中 1-已完成 2-取消  3-删除 4-失败
     */
    private Integer status;

    /**
     * 用户ID
     */
    private String userId;

    /**
     * 用户名称
     */
    private String userName;

    /**
     * 企业组织Code
     */
    private String orgCode;

    /**
     * 创建日期
     */
    @JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT+8")
    private String addTime;

    public String getRecordId() {
        return recordId;
    }

    public void setRecordId(String recordId) {
        this.recordId = recordId;
    }

    public String getBusiId() {
        return busiId;
    }

    public void setBusiId(String busiId) {
        this.busiId = busiId;
    }

    public String getBusiType() {
        return busiType;
    }

    public void setBusiType(String busiType) {
        this.busiType = busiType;
    }

    public Integer getFileType() {
        return fileType;
    }

    public void setFileType(Integer fileType) {
        this.fileType = fileType;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getAddTime() {
        return addTime;
    }

    public void setAddTime(String addTime) {
        this.addTime = addTime;
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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
