package com.guiji.dispatch.dto;

import java.io.Serializable;
import java.util.List;

public class DelExportFileRecordDto implements Serializable {

    private static final long serialVersionUID = -1299825505646013701L;

    private List<String> recordIdList;

    public List<String> getRecordIdList() {
        return recordIdList;
    }

    public void setRecordIdList(List<String> recordIdList) {
        this.recordIdList = recordIdList;
    }
}
