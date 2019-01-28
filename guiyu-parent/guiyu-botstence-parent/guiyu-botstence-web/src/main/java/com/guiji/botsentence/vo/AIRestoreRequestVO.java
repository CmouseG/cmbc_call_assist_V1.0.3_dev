package com.guiji.botsentence.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AIRestoreRequestVO {
    /**
     * FreeSWITCH的通道id
     */
    private String seqid;

    /**
     * 通道使用的模板id
     */
    private String cfg;

    /**
     * 客户号码
     */
    private String phonenum;
}
