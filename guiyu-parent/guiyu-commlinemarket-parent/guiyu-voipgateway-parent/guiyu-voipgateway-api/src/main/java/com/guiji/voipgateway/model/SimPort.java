package com.guiji.voipgateway.model;

/** 
* @Description: 网关端口信息
* @Author: weiyunbo
* @date 2019年1月25日 下午5:18:06 
* @version V1.0  
*/
public class SimPort {
	private Integer PortId;
	private Integer PortNumber;	//端口编号
	private Integer CompanyId;	//公司编号
	private Integer ParentDevId;	//设备编号
	private Integer RegStatusId;	//端口注册状态
	private Integer WorkStatusId;	//端口工作状态
	private Integer LoadType;	//端口负荷情况（sim卡的话是信号）
	private String NodeId;	//设备节点编号
	private Integer ConnectionStatus;	//基站连接状态
	private String PhoneNumber;	//手机号码
	/**
	 * @return the portNumber
	 */
	public Integer getPortNumber() {
		return PortNumber;
	}
	/**
	 * @param portNumber the portNumber to set
	 */
	public void setPortNumber(Integer portNumber) {
		PortNumber = portNumber;
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
	 * @return the parentDevId
	 */
	public Integer getParentDevId() {
		return ParentDevId;
	}
	/**
	 * @param parentDevId the parentDevId to set
	 */
	public void setParentDevId(Integer parentDevId) {
		ParentDevId = parentDevId;
	}
	/**
	 * @return the regStatusId
	 */
	public Integer getRegStatusId() {
		return RegStatusId;
	}
	/**
	 * @param regStatusId the regStatusId to set
	 */
	public void setRegStatusId(Integer regStatusId) {
		RegStatusId = regStatusId;
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
	 * @return the loadType
	 */
	public Integer getLoadType() {
		return LoadType;
	}
	/**
	 * @param loadType the loadType to set
	 */
	public void setLoadType(Integer loadType) {
		LoadType = loadType;
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
	 * @return the connectionStatus
	 */
	public Integer getConnectionStatus() {
		return ConnectionStatus;
	}
	/**
	 * @param connectionStatus the connectionStatus to set
	 */
	public void setConnectionStatus(Integer connectionStatus) {
		ConnectionStatus = connectionStatus;
	}
	/**
	 * @return the phoneNumber
	 */
	public String getPhoneNumber() {
		return PhoneNumber;
	}
	/**
	 * @param phoneNumber the phoneNumber to set
	 */
	public void setPhoneNumber(String phoneNumber) {
		PhoneNumber = phoneNumber;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "SimPort [PortNumber=" + PortNumber + ", CompanyId=" + CompanyId + ", ParentDevId=" + ParentDevId
				+ ", RegStatusId=" + RegStatusId + ", WorkStatusId=" + WorkStatusId + ", LoadType=" + LoadType
				+ ", NodeId=" + NodeId + ", ConnectionStatus=" + ConnectionStatus + ", PhoneNumber=" + PhoneNumber
				+ "]";
	}
}
