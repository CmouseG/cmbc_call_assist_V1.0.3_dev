package com.guiji.dispatch.model;

import java.io.Serializable;
import java.util.List;

public class IPlanThirdBatchPhoneVo implements Serializable {

    private static final long serialVersionUID = 3942037476073797979L;

    /**
     * 批次名称
     */
    private String batchName;

    /**
     * 接收数量
     */
    private Integer batchCount;

    /**
     * 数据列表
     */
    private List<PhoneVo> mobileList;

    /**
     * 总页数
     */
    private int totalPage;

    /**
     * 当前页码
     */
    private int pageNo;

    /**
     * 每页条数
     */
    private int pageNum;

    public String getBatchName() {
        return batchName;
    }

    public void setBatchName(String batchName) {
        this.batchName = batchName;
    }

    public Integer getBatchCount() {
        return batchCount;
    }

    public void setBatchCount(Integer batchCount) {
        this.batchCount = batchCount;
    }

    public List<PhoneVo> getMobileList() {
        return mobileList;
    }

    public void setMobileList(List<PhoneVo> mobileList) {
        this.mobileList = mobileList;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }
}