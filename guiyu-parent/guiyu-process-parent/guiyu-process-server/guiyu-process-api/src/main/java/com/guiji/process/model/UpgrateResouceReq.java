package com.guiji.process.model;

import com.guiji.common.model.process.ProcessTypeEnum;

public class UpgrateResouceReq {

    private ProcessTypeEnum processTypeEnum;

    private String file;

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

    @Override
    public String toString() {
        return "UpgrateResouceReq{" +
                "processTypeEnum=" + processTypeEnum +
                ", file='" + file + '\'' +
                '}';
    }
}
