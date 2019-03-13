package com.guiji.botsentence.api.entity;

import java.io.Serializable;

public class KeywordsAuditListReqVO implements Serializable {

    private static final long serialVersionUID = -6621748117569862648L;

    private int keywordAuditStatus = 0;

    private String speechName;

    private int pageNum;

    private int pageSize;

    public int getKeywordAuditStatus() {
        return keywordAuditStatus;
    }

    public void setKeywordAuditStatus(int keywordAuditStatus) {
        this.keywordAuditStatus = keywordAuditStatus;
    }

    public String getSpeechName() {
        return speechName;
    }

    public void setSpeechName(String speechName) {
        this.speechName = speechName;
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public Long getOffset(){
        long num = (long)pageNum - 1;
        if(num <= 0L){
            return 0L;
        }
        return num * pageSize;
    }
}
