package com.guiji.botsentence.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
@AllArgsConstructor
public class AIHelloRequestVO {
    /**
     * 对应的FreeSWITCH通道ID
     */
    private String seqid;

    /**
     * 客户说话文本
     */
    private String sentence;

    private String state;
}
