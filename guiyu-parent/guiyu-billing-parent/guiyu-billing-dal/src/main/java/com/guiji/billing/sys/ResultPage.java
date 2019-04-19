package com.guiji.billing.sys;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.List;

/**
 * 分页处理类
 * @author pc-6
 *
 * @param <T>
 */
public class ResultPage<T> implements Serializable {

	private Logger logger = LoggerFactory.getLogger(ResultPage.class);

	/**
	 * 
	 */
	private static final long serialVersionUID = 7140582745283213760L;

	/**
	 * 数据结果集列表
	 */
	private List<T> list;

	/**
	 * 当前页数
	 */
	private int pageNo;

	/**
	 * 每页多少条数
	 */
	private int pageSize;

	/**
	 * 每页默认条数 默认10条
	 */
	public final static int defaultPageSize = 10;

	/**
	 * 排序字段
	 */
	private String orderBy;

	/**
	 * 排列顺序，升序:ASC,降序:DESC 默认降序
	 */
	private String sort = "desc";

	/**
	 * 总条数
	 */
	private long totalItemNumber;

	/**
	 * 总页数
	 */
	private int totalPageNumber;

	/**
	 * 是否有上一页
	 */
	private boolean hasPrePage;

	/**
	 * 是否有下一页
	 */
	private boolean hasNextPage;

	/**
	 * 上一页码
	 */
	private long prePage;

	/**
	 * 下一页码
	 */
	private long nextPage;

	/**
	 * 开始数据上标
	 */
	private long indexStart;

	/**
	 * 判断是否需要分页 true:分页 false:不分页
	 */
	private boolean isPage;
	
	public ResultPage(){
		super();
	}

	/**
	 * 当前页数
	 * 
	 * @param pageNo
	 */
	public ResultPage(int pageNo) {
		super();
		this.isPage = pageNo > 0 ? true : false; // 不传当前页数，则判断为不需要分页
		this.pageNo = pageNo > 0 ? pageNo : 1; // 当前页数
		this.pageSize = defaultPageSize; // 没有设置每页条数，采用默认每页条数
		this.indexStart = (long)((this.pageNo - 1) * this.pageSize); // 计算当前开始上标位置
	}

	/**
	 * 当前页数、每页条数
	 * 
	 * @param pageNo
	 * @param setPageSize
	 */
	public ResultPage(int pageNo, int setPageSize) {
		super();
		this.isPage = pageNo > 0 ? true : false; // 不传当前页数，则判断为不需要分页
		this.pageNo = pageNo > 0 ? pageNo : 1; // 当前页数
		this.pageSize = setPageSize > 0 ? setPageSize : defaultPageSize; // 设置每页条数
		this.indexStart = (long)((this.pageNo - 1) * this.pageSize); // 计算当前开始上标位置
	}

	/**
	 * 当前页数、排序字段、排序方式
	 * 
	 * @param pageNo
	 * @param orderBy
	 * @param sort
	 */
	public ResultPage(int pageNo, String orderBy, String sort) {
		super();
		this.isPage = pageNo > 0 ? true : false; // 不传当前页数，则判断为不需要分页
		this.pageNo = pageNo > 0 ? pageNo : 1; // 当前页数
		this.pageSize = defaultPageSize; // 没有设置每页条数，采用默认每页条数
		this.indexStart = (long)((this.pageNo - 1) * this.pageSize); // 计算当前开始上标位置
		this.orderBy = orderBy; // 排序字段
		this.sort = sort; // 排列顺序，升序:ASC,降序:DESC
	}

	/**
	 * 当前页数、每页条数、排序字段、排序方式
	 * 
	 * @param pageNo
	 * @param setPageSize
	 * @param orderBy
	 * @param sort
	 */
	public ResultPage(int pageNo, int setPageSize, String orderBy, String sort) {
		super();
		this.isPage = pageNo > 0 ? true : false; // 不传当前页数，则判断为不需要分页
		this.pageNo = pageNo > 0 ? pageNo : 1; // 当前页数
		this.pageSize = setPageSize > 0 ? setPageSize : defaultPageSize; // 设置每页条数
		this.indexStart = (long)((this.pageNo - 1) * this.pageSize); // 计算当前开始上标位置
		this.orderBy = orderBy; // 排序字段
		this.sort = sort; // 排列顺序，升序:ASC,降序:DESC
	}

	/**
	 * 入参构造方法
	 * 
	 * @param pageDto
	 */
	public ResultPage(PageDto pageDto) {
		super();
		try {
			if (null != pageDto) {
				this.isPage = pageDto.getPageNo() > 0 ? true : false; // 不传当前页数，则判断为不需要分页
				this.pageNo = pageDto.getPageNo() > 0 ? pageDto.getPageNo() : 1; // 当前页数
				this.pageSize = pageDto.getPageSize() > 0 ? pageDto.getPageSize() : defaultPageSize; // 设置每页条数
				this.indexStart = (long)((this.pageNo - 1) * this.pageSize); // 计算当前开始上标位置
				if(null != pageDto.getOrderBy()){
					this.orderBy = pageDto.getOrderBy(); //排序字段
					this.sort = pageDto.getSort(); //排列顺序，升序:ASC,降序:DESC
				}
			}

		} catch (Exception e) {
			this.isPage = false;
			logger.error("com.guiji.billing.sys.ResultPage.ResultPage", e);
			e.printStackTrace();
		}
	}

	public List<T> getList() {
		return list;
	}

	public void setList(List<T> list) {
		this.list = list;
	}

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

	public long getTotalItemNumber() {
		return totalItemNumber;
	}

	public void setTotalItemNumber(long totalItemNumber) {
		this.totalItemNumber = totalItemNumber;
	}

	public int getTotalPageNumber() {
		return totalPageNumber;
	}

	public void setTotalPageNumber(int totalPageNumber) {
		this.totalPageNumber = totalPageNumber;
	}

	public long getIndexStart() {
		return indexStart;
	}

	public void setIndexStart(long indexStart) {
		this.indexStart = indexStart;
	}

	public boolean getIsPage() {
		return isPage;
	}

	public void setIsPage(boolean isPage) {
		this.isPage = isPage;
	}

	/**
	 * 设置计算总条数、总页数
	 * 
	 * @param totalItemNumber
	 */
	public void setTotalItemAndPageNumber(long totalItemNumber) {
		if (totalItemNumber > 0) {
			this.setTotalItemNumber(totalItemNumber);
			// 计算总页数
			int totalPageNumber = 1;
			if (this.isPage) {// 是否需要分页
				totalPageNumber = (int) totalItemNumber / this.pageSize;
				if (totalItemNumber % this.pageSize != 0) { // 不能整除，尾页+1
					totalPageNumber++;
				}

				this.isHasPrePage();
				this.isHasNextPage();
				this.getPrePage();
				this.getNextPage();
			}
			this.setTotalPageNumber(totalPageNumber);
		}
	}

	/**
	 * 是否有上一页
	 * 
	 * @return
	 */
	public boolean isHasPrePage() {
		this.hasPrePage = false;
		if (this.getPageNo() > 1) {
			hasPrePage = true;
		}
		return hasPrePage;
	}

	public void setHasPrePage(boolean hasPrePage) {
		this.hasPrePage = hasPrePage;
	}

	/**
	 * 是否有下一页
	 * 
	 * @return
	 */
	public boolean isHasNextPage() {
		this.hasNextPage = false;
		if (this.getPageNo() < this.getTotalPageNumber()) {
			this.hasNextPage = true;
		}
		return hasNextPage;
	}

	public void setHasNextPage(boolean hasNextPage) {
		this.hasNextPage = hasNextPage;
	}

	public void setPrePage(long prePage) {
		this.prePage = prePage;
	}

	/**
	 * 获取下一页
	 *
	 * @return
	 */
	public long getNextPage() {
		nextPage = this.getPageNo();
		if (this.isHasNextPage()) {
			nextPage = (long)(this.getPageNo() + 1);
		}
		return nextPage;
	}

	/**
	 * 获取上一页
	 *
	 * @return
	 */
	public long getPrePage() {
		prePage = this.getPageNo();
		if (this.isHasPrePage()) {
			prePage = (long)(this.getPageNo() - 1);
		}
		return prePage;
	}

	public void setNextPage(long nextPage) {
		this.nextPage = nextPage;
	}

}
