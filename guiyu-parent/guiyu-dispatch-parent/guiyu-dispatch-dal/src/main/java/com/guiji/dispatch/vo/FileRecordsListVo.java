package com.guiji.dispatch.vo;

import com.guiji.dispatch.dao.entity.FileRecords;

public class FileRecordsListVo extends FileRecords {

    private static final long serialVersionUID = -749642259223397654L;

    private Integer errorCount;

    public Integer getErrorCount() {
        return errorCount;
    }

    public void setErrorCount(Integer errorCount) {
        this.errorCount = errorCount;
    }
}
