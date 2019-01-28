package com.guiji.entity;

import com.guiji.callcenter.dao.entity.CallOutPlan;
import lombok.Data;

/**
 * 用于在CallInDetail和CallOutDetail上提供一个抽象对象
 */
@Data
public class CallPlan extends CallOutPlan{
    private String recordUrl;
    private String recordFile;

    private boolean isUpdateRecord = false;

    public void setRecordUrl(String recordUrl) {
        this.recordUrl = recordUrl;
        this.isUpdateRecord = true;
    }

    public void setRecordFile(String recordFile) {
        this.recordFile = recordFile;
        this.isUpdateRecord = true;
    }
}