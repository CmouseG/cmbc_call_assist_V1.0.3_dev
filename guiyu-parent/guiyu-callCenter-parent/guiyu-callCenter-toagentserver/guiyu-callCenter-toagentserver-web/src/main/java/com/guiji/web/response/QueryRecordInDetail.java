package com.guiji.web.response;

import lombok.Data;

@Data
public class QueryRecordInDetail {
    private String callrecordId;
    private String phone;
    private String answerTime;
    private String area;
    /**
     * 计费时长
     */
    private Integer billSec;
    private String label;

    private String remark;

}
