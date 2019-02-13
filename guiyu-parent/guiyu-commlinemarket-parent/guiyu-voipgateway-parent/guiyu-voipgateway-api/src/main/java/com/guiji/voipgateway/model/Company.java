package com.guiji.voipgateway.model;

/** 
* @Description: 公司信息
* @Author: weiyunbo
* @date 2019年1月25日 下午5:18:06 
* @version V1.0  
*/
public class Company {
	private Integer CompanyId;	//公司编号
	private String CompanyIdName;	//公司名称
	
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
	 * @return the companyIdName
	 */
	public String getCompanyIdName() {
		return CompanyIdName;
	}
	/**
	 * @param companyIdName the companyIdName to set
	 */
	public void setCompanyIdName(String companyIdName) {
		CompanyIdName = companyIdName;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Company [CompanyId=" + CompanyId + ", CompanyIdName=" + CompanyIdName + "]";
	}
	
}
