package com.guiji.web.response;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class ApiResponse implements Serializable {
    protected boolean result = false;
    protected Integer code;
    protected String msg;
    protected Object data;
    protected List<Object> datas;
}
