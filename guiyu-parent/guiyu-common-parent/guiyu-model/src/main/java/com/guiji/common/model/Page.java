/**
 * 
 */
package com.guiji.common.model;

import java.util.List;

/**
 * @author administrator
 *
 */
public class Page<T> implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static int DEFAULT_COUNT = 20;
	
	private int pageSize = DEFAULT_COUNT;//current page records count
	private int pageNo = 1;//current page number
	private int totalPage = 1;//total page number
	private int totalRecord;//the record number
	
	private Long userId;
	private Integer authLevel;
	private String orgCode="";
	private String orgName="";
	
	private List<T> records;

	public Page(){}
	public Page(int pageSize){
		this();
		setPageSize(pageSize);
	}
	
	public Page(int pageSize,int recordNumber){
		setPageNo(pageSize);
		setTotal(recordNumber);
	}
	
	public Page(int pageSize,int pageNo,int recordNumber){
		setPageSize(pageSize);
		setPageNo(pageNo);
		setTotal(recordNumber);
	}
	
	public Page(int pageSize,int recordNumber,List<T> records){
		this(pageSize,recordNumber);
		this.records = records;
	}
	
	public void setPageSize(int pageSize) {
		//this.count = DEFAULT_COUNT;
		if(pageSize > 0){
			this.pageSize = pageSize;
		}
	}

	public void setPageNo(int pageNo) {
		if(pageNo < 1){
			this.pageNo = 1;
		}
			this.pageNo = pageNo;
	}
	
	public void setTotal(int totalRecord) {
		if(totalRecord > 0)
		{
			int t = totalRecord/this.pageSize;
			this.totalPage = totalRecord%this.pageSize!=0?++t:t;
			this.totalRecord = totalRecord;
		}
	}
	
	public void setTotalRecord(int totalRecord) {
		if(totalRecord > 0)
		{
			int t = totalRecord/this.pageSize;
			this.totalPage = totalRecord%this.pageSize!=0?++t:t;
			this.totalRecord = totalRecord;
		}
	}
	
	public int getCurrent(){
		return this.pageNo>this.totalPage?this.totalPage:this.pageNo;
	}
	
	public int getPageNo() {
		return pageNo;
	}
	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}
	
	public int getTotalPage(){
		return this.totalPage;
	}
	
	public int getTotalRecord(){
		return this.totalRecord;
	}
	
	public int getPageSize(){
		return this.pageSize;
	}
	
	public int getPrePage(){
		return this.pageNo>2?this.pageNo-1:1;
	}

	public int getNextPage(){
		return this.pageNo<this.totalPage?this.pageNo+1:this.totalPage;
	}
	
	public int getStartIndex(){
		return (this.pageNo-1)*this.pageSize + 1;
	}
	
	public void setRecords(List<T> obj){
		this.records = obj;
	}
	public List<T> getRecords() {
		return records;
	}
	public Long getUserId()
	{
		return userId;
	}
	public void setUserId(Long userId)
	{
		this.userId = userId;
	}
	public Integer getAuthLevel()
	{
		return authLevel;
	}
	public void setAuthLevel(Integer authLevel)
	{
		this.authLevel = authLevel;
	}
	public String getOrgCode()
	{
		return orgCode;
	}
	public void setOrgCode(String orgCode)
	{
		this.orgCode = orgCode;
	}
	
	public String getOrgName()
	{
		return orgName;
	}
	public void setOrgName(String orgName)
	{
		this.orgName = orgName;
	}
	
}
