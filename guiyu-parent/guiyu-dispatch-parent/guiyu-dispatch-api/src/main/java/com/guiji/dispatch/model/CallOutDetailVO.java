package com.guiji.dispatch.model;

import java.math.BigInteger;
import java.util.Date;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class CallOutDetailVO {
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

}
