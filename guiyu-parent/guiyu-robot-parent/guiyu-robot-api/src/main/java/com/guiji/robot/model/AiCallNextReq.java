package com.guiji.robot.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/** 
* @ClassName: AiCallNextReq 
* @Description: AI电话客户语音响应请求信息
* @date 2018年11月15日 下午2:05:18 
* @version V1.0  
*/
//@Data
@ApiModel(value="AiCallNextReq对象",description="AI电话客户语音响应请求信息")
public class AiCallNextReq {
	@ApiModelProperty(value="会话ID，该电话整个会话过程中唯一编号",required=true)
	private String seqId;
	@ApiModelProperty(value="用户编号",required=true)
	private String userId;
	@ApiModelProperty(value="机器人编号，开始拨打电话时分配的机器人",required=true)
	private String aiNo;
	@ApiModelProperty(value="话术模板编号")
	private String templateId;
	@ApiModelProperty(value="号码",required=false)
	private String phoneNo;
	@ApiModelProperty(value="播音状态（0-播音结束;1-播音中;999-开场白）",required=true)
	private String status;
	@ApiModelProperty(value="播音状态时(status=1)，time_stamp表示播音开始的时间，播音结束状态时(status=0),表示播音结束的时间",required=true)
	private long timestamp;
	@ApiModelProperty(value="等待秒数",required=false)
	private int waitCnt;

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

	public String getPhoneNo() {
		return phoneNo;
	}

	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

	public int getWaitCnt() {
		return waitCnt;
	}

	public void setWaitCnt(int waitCnt) {
		this.waitCnt = waitCnt;
	}

	@Override
	public String toString() {
		return "AiCallNextReq{" +
				"seqId='" + seqId + '\'' +
				", userId='" + userId + '\'' +
				", aiNo='" + aiNo + '\'' +
				", templateId='" + templateId + '\'' +
				", phoneNo='" + phoneNo + '\'' +
				", status='" + status + '\'' +
				", timestamp=" + timestamp +
				", waitCnt=" + waitCnt +
				'}';
	}
}