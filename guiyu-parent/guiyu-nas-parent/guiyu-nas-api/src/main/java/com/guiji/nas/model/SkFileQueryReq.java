/** 
 *@Copyright:Copyright (c) 2008 - 2100 
 *@Company:guojaing
 */  
package com.guiji.nas.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/** 
 *@Description: 文件信息查询请求对象
 *@Author:weiyunbo
 *@date:2018年6月25日 下午10:11:06
 *@history:
 *@Version:v1.0 
 */
@ApiModel(value="SkFileQueryReq对象",description="文件查询请求对象")
public class SkFileQueryReq  implements Serializable{
	private static final long serialVersionUID = 1L;
	@ApiModelProperty(value="文件ID")
	private String id;
	@ApiModelProperty(value="文件上传系统码",example="如：aiSale.customer")
	private String sysCode;
	@ApiModelProperty(value="上传的影像文件关联的业务ID")
	private String busiId;
	@ApiModelProperty(value="上传的影像文件业务类型")
	private String busiType;
	/** 
	 * @return the id 
	 */
	public String getId() {
	
		return id;
	}
	/** 
	 @param id the id to set 
	 */
	public void setId(String id) {
		this.id = id;
	}
	/** 
	 * @return the sysCode 
	 */
	public String getSysCode() {
	
		return sysCode;
	}
	/** 
	 @param sysCode the sysCode to set 
	 */
	public void setSysCode(String sysCode) {
		this.sysCode = sysCode;
	}
	/** 
	 * @return the busiId 
	 */
	public String getBusiId() {
	
		return busiId;
	}
	/** 
	 @param busiId the busiId to set 
	 */
	public void setBusiId(String busiId) {
		this.busiId = busiId;
	}
	/** 
	 * @return the busiType 
	 */
	public String getBusiType() {
	
		return busiType;
	}
	/** 
	 @param busiType the busiType to set 
	 */
	public void setBusiType(String busiType) {
		this.busiType = busiType;
	}
	/* (non-Javadoc) 
	 * @see java.lang.Object#toString() 
	 */
	@Override
	public String toString() {
		return "SkFileQueryReq [id=" + id + ", sysCode=" + sysCode + ", busiId=" + busiId + ", busiType=" + busiType
				+ "]";
	}
}
  
