package com.guiji.voipgateway.model;

/** 
* @Description: 网关设备数据
* @Author: weiyunbo
* @date 2019年1月25日 下午5:18:06 
* @version V1.0  
*/
public class GwDevtbl {
	private Integer DevId;	//设备编号
	private String SeqNum;	//设备序列号
	private Integer CompanyId; //公司ID
	private String DevName; //设备名称
	private String DevVersion;	//设备型号(设备类型表-型号代码)
	private String SoftwareVersion;	//设备软件号
	private Integer IdleChNum;	//设备空闲通道数
	private Integer ChUseNum;	//设备在忙的通道数
	private Integer ChPutNum;	//可用通道数（闲+忙）
	private String NodeId;	//节点号
	private Integer WorkStatusId;	//设备工作状态(枚举)
	private Boolean BeManage;	//设备是否受管理
	private Integer SipTrunkNum;	//设备SIP中继/注册服务器数量
	private Boolean BeEnable;	//设备是否启用 
	private String Description;	//设备描述（重要字段，和CRM系统中设备只能根据描述绑定）
	/**
	 * @return the devId
	 */
	public Integer getDevId() {
		return DevId;
	}
	/**
	 * @param devId the devId to set
	 */
	public void setDevId(Integer devId) {
		DevId = devId;
	}
	/**
	 * @return the seqNum
	 */
	public String getSeqNum() {
		return SeqNum;
	}
	/**
	 * @param seqNum the seqNum to set
	 */
	public void setSeqNum(String seqNum) {
		SeqNum = seqNum;
	}
	/**
	 * @return the companyId
	 */
	public Integer getCompanyId() {
		return CompanyId;
	}
	/**
	 * @param companyId the companyId to set
	 */
	public void setCompanyId(Integer companyId) {
		CompanyId = companyId;
	}
	/**
	 * @return the devName
	 */
	public String getDevName() {
		return DevName;
	}
	/**
	 * @param devName the devName to set
	 */
	public void setDevName(String devName) {
		DevName = devName;
	}
	/**
	 * @return the devVersion
	 */
	public String getDevVersion() {
		return DevVersion;
	}
	/**
	 * @param devVersion the devVersion to set
	 */
	public void setDevVersion(String devVersion) {
		DevVersion = devVersion;
	}
	/**
	 * @return the softwareVersion
	 */
	public String getSoftwareVersion() {
		return SoftwareVersion;
	}
	/**
	 * @param softwareVersion the softwareVersion to set
	 */
	public void setSoftwareVersion(String softwareVersion) {
		SoftwareVersion = softwareVersion;
	}
	/**
	 * @return the idleChNum
	 */
	public Integer getIdleChNum() {
		return IdleChNum;
	}
	/**
	 * @param idleChNum the idleChNum to set
	 */
	public void setIdleChNum(Integer idleChNum) {
		IdleChNum = idleChNum;
	}
	/**
	 * @return the chUseNum
	 */
	public Integer getChUseNum() {
		return ChUseNum;
	}
	/**
	 * @param chUseNum the chUseNum to set
	 */
	public void setChUseNum(Integer chUseNum) {
		ChUseNum = chUseNum;
	}
	/**
	 * @return the chPutNum
	 */
	public Integer getChPutNum() {
		return (IdleChNum==null?0:IdleChNum)+(ChUseNum==null?0:ChUseNum);
	}
	/**
	 * @param chPutNum the chPutNum to set
	 */
	public void setChPutNum(Integer chPutNum) {
		ChPutNum = chPutNum;
	}
	/**
	 * @return the nodeId
	 */
	public String getNodeId() {
		return NodeId;
	}
	/**
	 * @param nodeId the nodeId to set
	 */
	public void setNodeId(String nodeId) {
		NodeId = nodeId;
	}
	/**
	 * @return the workStatusId
	 */
	public Integer getWorkStatusId() {
		return WorkStatusId;
	}
	/**
	 * @param workStatusId the workStatusId to set
	 */
	public void setWorkStatusId(Integer workStatusId) {
		WorkStatusId = workStatusId;
	}
	/**
	 * @return the beManage
	 */
	public Boolean getBeManage() {
		return BeManage;
	}
	/**
	 * @param beManage the beManage to set
	 */
	public void setBeManage(Boolean beManage) {
		BeManage = beManage;
	}
	/**
	 * @return the sipTrunkNum
	 */
	public Integer getSipTrunkNum() {
		return SipTrunkNum;
	}
	/**
	 * @param sipTrunkNum the sipTrunkNum to set
	 */
	public void setSipTrunkNum(Integer sipTrunkNum) {
		SipTrunkNum = sipTrunkNum;
	}
	/**
	 * @return the beEnable
	 */
	public Boolean getBeEnable() {
		return BeEnable;
	}
	/**
	 * @param beEnable the beEnable to set
	 */
	public void setBeEnable(Boolean beEnable) {
		BeEnable = beEnable;
	}
	/**
	 * @return the description
	 */
	public String getDescription() {
		return Description;
	}
	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		Description = description;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "GwDevtbl [DevId=" + DevId + ", SeqNum=" + SeqNum + ", CompanyId=" + CompanyId + ", DevName=" + DevName
				+ ", DevVersion=" + DevVersion + ", SoftwareVersion=" + SoftwareVersion + ", IdleChNum=" + IdleChNum
				+ ", ChUseNum=" + ChUseNum + ", ChPutNum=" + ChPutNum + ", NodeId=" + NodeId + ", WorkStatusId="
				+ WorkStatusId + ", BeManage=" + BeManage + ", SipTrunkNum=" + SipTrunkNum + ", BeEnable=" + BeEnable
				+ ", Description=" + Description + "]";
	}
}
