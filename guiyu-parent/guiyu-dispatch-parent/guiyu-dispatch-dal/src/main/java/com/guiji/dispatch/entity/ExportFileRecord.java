package com.guiji.dispatch.entity;

public class ExportFileRecord extends BaseBean {

    private static final long serialVersionUID = -8690128591225067637L;

    /**
     * 记录唯一标识
     */
    private String recordId;

    /**
     *  业务类型 02-任务中心  03-呼叫中心
     */
    private String busiType        ;

    /**
     * 关联唯一标识ID
     */
    private String busiId;

    /**
     *  文件类型 1-execl文件  2-音频文件 3-视频文件
     */
    private Integer fileType        ;

    /**
     *   文件大小
     */
    private String fileSize        ;

    /**
     *   文件原始路径
     */
    private String fileOriginalUrl;

    /**
     * 下载生成目录
     */
    private String fileGenerateUrl;


    private String batchId         ;

    private String batchName       ;

    /**
     * 总数量
     */
    private Integer totalNum        ;

    private Integer successNum      ;

    private Integer failNum         ;

    /**
     * 状态 0-进行中 1-已完成 2-取消  3-删除 4-失败
     */
    private Integer status           ;

    private String userId     ;

    /**
     * 用户名称
     */
    private String userName   ;

    private String orgCode;

    /**
     * 创建者
     */
    private String createName;

    /**
     * 创建日期
     */
    private String createTime;

    public String getRecordId() {
        return recordId;
    }

    public void setRecordId(String recordId) {
        this.recordId = recordId;
    }

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

    public String getFileSize() {
        return fileSize;
    }

    public void setFileSize(String fileSize) {
        this.fileSize = fileSize;
    }

    public String getFileOriginalUrl() {
        return fileOriginalUrl;
    }

    public void setFileOriginalUrl(String fileOriginalUrl) {
        this.fileOriginalUrl = fileOriginalUrl;
    }

    public String getFileGenerateUrl() {
        return fileGenerateUrl;
    }

    public void setFileGenerateUrl(String fileGenerateUrl) {
        this.fileGenerateUrl = fileGenerateUrl;
    }

    public String getBatchId() {
        return batchId;
    }

    public void setBatchId(String batchId) {
        this.batchId = batchId;
    }

    public String getBatchName() {
        return batchName;
    }

    public void setBatchName(String batchName) {
        this.batchName = batchName;
    }

    public Integer getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(Integer totalNum) {
        this.totalNum = totalNum;
    }

    public Integer getSuccessNum() {
        return successNum;
    }

    public void setSuccessNum(Integer successNum) {
        this.successNum = successNum;
    }

    public Integer getFailNum() {
        return failNum;
    }

    public void setFailNum(Integer failNum) {
        this.failNum = failNum;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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
