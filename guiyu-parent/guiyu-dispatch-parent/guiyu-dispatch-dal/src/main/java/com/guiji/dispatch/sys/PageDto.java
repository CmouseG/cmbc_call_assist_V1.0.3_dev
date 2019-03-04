package com.guiji.dispatch.sys;

import com.guiji.dispatch.sys.BaseDto;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

/**
 * 分页bean
 * 
 * @author pc-6
 */
public class PageDto extends BaseDto {


	/**
	 * 当前页数
	 */
	private int pageNo;

	/**
	 * 每页条数
	 */
	private int pageSize = 10;

	/**
	 * 开始数据上标
	 */
	private long indexStart;

	/**
	 * 判断是否需要分页 true:分页 false:不分页
	 */
	private boolean isPage;

	private static List<String> limitNameList = Arrays.asList("pageSize", "indexStart");

	/**
	 * 分页初始化
	 */
	public void init() {
		this.isPage = true; // 不传当前页数，则判断为不需要分页
		this.pageNo = pageNo > 0 ? pageNo : 1; // 当前页数
		this.pageSize = pageSize > 0 ? pageSize : 10; // 没有设置每页条数，采用默认每页条数
		this.indexStart = (this.pageNo - 1) * this.pageSize; // 计算当前开始上标位置
	}

	 /**
	 * 排序字段
	 */
	 private String orderBy;
	
	 /**
	 * 排序方式
	 */
	 private String sort;

	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public long getIndexStart() {
		return indexStart;
	}

	public void setIndexStart(long indexStart) {
		this.indexStart = indexStart;
	}

	public boolean isPage() {
		return isPage;
	}

	public void setPage(boolean isPage) {
		this.isPage = isPage;
	}

	public static List<String> getLimitNameList() {
		return limitNameList;
	}

	 public String getOrderBy() {
	 return orderBy;
	 }
	
	 public void setOrderBy(String orderBy) {
	 this.orderBy = orderBy;
	 }
	
	 public String getSort() {
	 return sort;
	 }
	
	 public void setSort(String sort) {
	 this.sort = sort;
	 }

}
