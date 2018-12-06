package com.guiji.process.model;

import com.guiji.common.model.process.ProcessTypeEnum;

public class UpgrateResouceReq {

    private ProcessTypeEnum processTypeEnum;

    private String file;

    private String tmplId;

    private Long userId;

    public ProcessTypeEnum getProcessTypeEnum() {
        return processTypeEnum;
    }

    public void setProcessTypeEnum(ProcessTypeEnum processTypeEnum) {
        this.processTypeEnum = processTypeEnum;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public String getTmplId() {
        return tmplId;
    }

    public void setTmplId(String tmplId) {
        this.tmplId = tmplId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "UpgrateResouceReq{" +
                "processTypeEnum=" + processTypeEnum +
                ", file='" + file + '\'' +
                ", tmplId='" + tmplId + '\'' +
                ", userId=" + userId +
                '}';
    }
}
