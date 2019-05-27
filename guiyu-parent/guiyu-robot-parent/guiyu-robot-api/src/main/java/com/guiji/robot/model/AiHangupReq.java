package com.guiji.robot.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/** 
* @ClassName: AiHangupReq 
* @Description: AI电话挂断请求信息
* @date 2018年11月15日 下午2:23:48 
* @version V1.0  
*/
//@Data
@ApiModel(value="AiHangupReq对象",description="电话挂断后调用释放机器人")
public class AiHangupReq {
	@ApiModelProperty(value="会话ID，该电话整个会话过程中唯一编号",required=true)
	private String seqId;
	@ApiModelProperty(value="用户编号",required=true)
	private String userId;
	@ApiModelProperty(value="号码",required=true)
	private String phoneNo;
	@ApiModelProperty(value="机器人编号",required=true)
	private String aiNo;
	@ApiModelProperty(value="话术模板编号",required=true)
	private String templateId;

	/**
	 * 挂断方HANGUP_NO = 0
	 *     HANGUP_HUMAN = 1 客户挂断
	 *     HANGUP_AI = 2 ai挂断
 	*/
	private Integer hangUpType;

	/**
	 * 播放完毕		0
	 * 未播放完毕	1
	 * 未知			255
	 */
	private Integer hangUpHalfway;

	/**
	 * 通话时长
	 */
	private Integer totalCallTime;


	public String getSeqId() {
		return seqId;
	}

	public void setSeqId(String seqId) {
		this.seqId = seqId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getPhoneNo() {
		return phoneNo;
	}

	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}

	public String getAiNo() {
		return aiNo;
	}

	public void setAiNo(String aiNo) {
		this.aiNo = aiNo;
	}

	public String getTemplateId() {
		return templateId;
	}

	public void setTemplateId(String templateId) {
		this.templateId = templateId;
	}

	public Integer getHangUpType() {
		return hangUpType;
	}

	public void setHangUpType(Integer hangUpType) {
		this.hangUpType = hangUpType;
	}

	public Integer getHangUpHalfway() {
		return hangUpHalfway;
	}

	public void setHangUpHalfway(Integer hangUpHalfway) {
		this.hangUpHalfway = hangUpHalfway;
	}

	public Integer getTotalCallTime() {
		return totalCallTime;
	}

	public void setTotalCallTime(Integer totalCallTime) {
		this.totalCallTime = totalCallTime;
	}

	@Override
	public String toString() {
		return "AiHangupReq{" +
				"seqId='" + seqId + '\'' +
				", userId='" + userId + '\'' +
				", phoneNo='" + phoneNo + '\'' +
				", aiNo='" + aiNo + '\'' +
				", templateId='" + templateId + '\'' +
				", hangUpType=" + hangUpType +
				", hangUpHalfway=" + hangUpHalfway +
				", totalCallTime=" + totalCallTime +
				'}';
	}
}