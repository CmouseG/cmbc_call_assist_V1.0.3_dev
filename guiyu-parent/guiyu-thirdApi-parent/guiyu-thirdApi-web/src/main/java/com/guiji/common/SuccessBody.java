package com.guiji.common;

import lombok.Data;

/**
 * 通用成功的返回结果类
 */
@Data
public class SuccessBody implements Encryptable {

    private String res = "success";

}
