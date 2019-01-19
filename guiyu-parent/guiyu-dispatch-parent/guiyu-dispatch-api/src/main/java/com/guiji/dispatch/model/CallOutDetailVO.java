package com.guiji.dispatch.model;

import lombok.Data;

@Data
public class CallOutDetailVO {

    private static final long serialVersionUID = 1L;

    private String id;
    private String callid;
    private String answer;
    private String content;
    private String file;

}
