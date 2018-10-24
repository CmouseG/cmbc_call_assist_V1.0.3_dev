package com.guiji.common.model;

/** 
* @ClassName: DateRange 
* @Description: 日期范围
* @author: weiyunbo
* @date 2018年7月9日 下午3:14:48 
* @version V1.0  
*/
public class DateRange {
	//开始时间
	private String beginDate;
	//结束时间
	private String endDate;
	/**
	 * @return the beginDate
	 */
	public String getBeginDate() {
		return beginDate;
	}
	/**
	 * @param beginDate the beginDate to set
	 */
	public void setBeginDate(String beginDate) {
		this.beginDate = beginDate;
	}
	/**
	 * @return the endDate
	 */
	public String getEndDate() {
		return endDate;
	}
	/**
	 * @param endDate the endDate to set
	 */
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "DateStatRange [beginDate=" + beginDate + ", endDate=" + endDate + "]";
	}
	
}
