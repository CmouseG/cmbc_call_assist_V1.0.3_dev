package com.guiji.common;

import lombok.Data;

/**
 * 通用返回报文结构
 *
 * @param <T>
 */
@Data
public class GenericResponse<T> {

    private Integer code = 1;

    private String msg = "";

    private Integer success;

    private T body;

    private String sign;

}
