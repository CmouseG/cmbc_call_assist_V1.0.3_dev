package com.guiji.ccmanager.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;


/**
 * @Auther: 黎阳
 * @Date: 2018/10/30 0030 14:30
 * @Description:
 */
@Data
@ApiModel(description= "对话详情")
public class CallOutDetailVO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String callDetailId;
    private BigInteger callId;
    @ApiModelProperty(value = "意向")
    private String accurateIntent;
    @ApiModelProperty(value = "客户说的话")
    private String agentAnswerText;
    private Date agentAnswerTime;
    private Integer aiDuration;
    private Integer asrDuration;
    @ApiModelProperty(value = "机器人说的话")
    private String botAnswerText;
    private Date botAnswerTime;
    private Integer callDetailType;
    private String customerSayText;
    private Date customerSayTime;
    private String reason;
    private Integer totalDuration;


    private String agentRecordFile;
    private String agentRecordUrl;
    private String botRecordFile;
    private String botRecordUrl;
    private String customerRecordFile;
    private String customerRecordUrl;

    private String wordSegmentResult;
    private String keywords;
    private Integer isupdate;
}
