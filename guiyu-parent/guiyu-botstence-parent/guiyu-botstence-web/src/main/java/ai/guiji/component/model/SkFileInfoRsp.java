/** 
 *@Copyright:Copyright (c) 2008 - 2100 
 *@Company:guojaing
 */  
package ai.guiji.component.model;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;

/** 
 *@Description: 
 *@Author:weiyunbo
 *@date:2018年6月26日 上午10:41:03
 *@history:
 *@Version:v1.0 
 */
public class SkFileInfoRsp implements Serializable{
	private static final long serialVersionUID = 1L;
	private String id;
	//文件名称
	private String fileName;
	//文件类型
	private String fileType;
	//文件业务编号
	private String busiId;
	//文件业务类型
	private String busiType;
	//文件大小
	private double fileSize;
	//文件系统URL
	private String skUrl;
	//缩略图URL
	private String skThumbImageUrl;
	//文件上传系统码
	private String sysCode;
	//文件创建人
	private String crtUser;
	//文件创建时间
	private String crtTime;
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
	 * @return the fileName 
	 */
	public String getFileName() {
	
		return fileName;
	}
	/** 
	 @param fileName the fileName to set 
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	/** 
	 * @return the fileType 
	 */
	public String getFileType() {
	
		return fileType;
	}
	/** 
	 @param fileType the fileType to set 
	 */
	public void setFileType(String fileType) {
		this.fileType = fileType;
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
	 * @return the fileSize 
	 */
	public double getFileSize() {
	
		return fileSize;
	}
	/** 
	 @param fileSize the fileSize to set 
	 */
	public void setFileSize(double fileSize) {
		this.fileSize = fileSize;
	}
	/** 
	 * @return the skUrl 
	 */
	public String getSkUrl() {
	
		return skUrl;
	}
	/** 
	 @param skUrl the skUrl to set 
	 */
	public void setSkUrl(String skUrl) {
		this.skUrl = skUrl;
	}
	/** 
	 * @return the skThumbImageUrl 
	 */
	public String getSkThumbImageUrl() {
	
		return skThumbImageUrl;
	}
	/** 
	 @param skThumbImageUrl the skThumbImageUrl to set 
	 */
	public void setSkThumbImageUrl(String skThumbImageUrl) {
		this.skThumbImageUrl = skThumbImageUrl;
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
	 * @return the crtUser 
	 */
	public String getCrtUser() {
	
		return crtUser;
	}
	/** 
	 @param crtUser the crtUser to set 
	 */
	public void setCrtUser(String crtUser) {
		this.crtUser = crtUser;
	}
	/** 
	 * @return the crtTime 
	 */
	public String getCrtTime() {
	
		return crtTime;
	}
	/** 
	 @param crtTime the crtTime to set 
	 */
	public void setCrtTime(String crtTime) {
		this.crtTime = crtTime;
	}
	/* (non-Javadoc) 
	 * @see java.lang.Object#toString() 
	 */
	@Override
	public String toString() {
		return "SkFileInfoRsp [id=" + id + ", fileName=" + fileName + ", fileType=" + fileType + ", busiId=" + busiId
				+ ", busiType=" + busiType + ", fileSize=" + fileSize + ", skUrl=" + skUrl + ", skThumbImageUrl="
				+ skThumbImageUrl + ", sysCode=" + sysCode + ", crtUser=" + crtUser + ", crtTime=" + crtTime + "]";
	}
	
}
  
