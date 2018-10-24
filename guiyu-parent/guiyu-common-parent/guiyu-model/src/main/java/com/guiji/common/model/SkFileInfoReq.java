/** 
 *@Copyright:Copyright (c) 2008 - 2100 
 *@Company:guojaing
 */  
package com.guiji.common.model;

import java.io.Serializable;

/** 
 *@Description: 文件上传请求参数
 *@Author:weiyunbo
 *@date:2018年6月26日 上午10:48:25
 *@history:
 *@Version:v1.0 
 */
public class SkFileInfoReq implements Serializable{
	private static final long serialVersionUID = 1L;
	//文件上传系统码
	private String sysCode;
	//上传的影像文件关联的业务ID
	private String busiId;
	//上传的影像文件业务类型
	private String busiType;
	//是否需要生成缩略图,00-无需生成，01-生成，默认不生成缩略图
	private String thumbImageFlag;
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
	/** 
	 * @return the thumbImageFlag 
	 */
	public String getThumbImageFlag() {
	
		return thumbImageFlag;
	}
	/** 
	 @param thumbImageFlag the thumbImageFlag to set 
	 */
	public void setThumbImageFlag(String thumbImageFlag) {
		this.thumbImageFlag = thumbImageFlag;
	}
	/* (non-Javadoc) 
	 * @see java.lang.Object#toString() 
	 */
	@Override
	public String toString() {
		return "SkFileInfoReq [sysCode=" + sysCode + ", busiId=" + busiId + ", busiType=" + busiType
				+ ", thumbImageFlag=" + thumbImageFlag + "]";
	}
	
}
  
