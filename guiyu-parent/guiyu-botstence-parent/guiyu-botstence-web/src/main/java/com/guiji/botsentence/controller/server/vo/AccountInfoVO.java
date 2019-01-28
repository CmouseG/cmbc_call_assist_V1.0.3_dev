package com.guiji.botsentence.controller.server.vo;

import java.util.List;

import com.guiji.botsentence.dao.entity.BotSentenceIndustry;

public class AccountInfoVO {

	private String accountNo;//账号
	private String userNo;//所属用户
	private String accountName;//账号名称
	private String machineCode;//机器码
	private String host;//域名
	private List<BotSentenceTemplateIndustryVO> list;//行业清单
	private String groupId;//权限
	private String fullHost;//完整域名
	
	public String getAccountNo() {
		return accountNo;
	}
	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}

	public String getUserNo() {
		return userNo;
	}
	public void setUserNo(String userNo) {
		this.userNo = userNo;
	}
	public String getAccountName() {
		return accountName;
	}
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
	public String getMachineCode() {
		return machineCode;
	}
	public void setMachineCode(String machineCode) {
		this.machineCode = machineCode;
	}
	
	public List<BotSentenceTemplateIndustryVO> getList() {
		return list;
	}
	public void setList(List<BotSentenceTemplateIndustryVO> list) {
		this.list = list;
	}
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}
	
	public String getGroupId() {
		return groupId;
	}
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
	public String getFullHost() {
		return fullHost;
	}
	public void setFullHost(String fullHost) {
		this.fullHost = fullHost;
	}
	@Override
	public String toString() {
		return "AccountInfoVO [accountNo=" + accountNo + ", userNo=" + userNo + ", accountName=" + accountName
				+ ", machineCode=" + machineCode + ", host=" + host + ", list=" + list + ", groupId=" + groupId
				+ ", fullHost=" + fullHost + "]";
	}
	
	
}
