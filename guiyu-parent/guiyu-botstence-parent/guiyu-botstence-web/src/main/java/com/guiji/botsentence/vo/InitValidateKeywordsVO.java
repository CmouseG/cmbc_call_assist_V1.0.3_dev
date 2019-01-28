package com.guiji.botsentence.vo;

import java.util.List;

public class InitValidateKeywordsVO {

	private String parendTradeName;//父级行业
	private String tradeName;//所在行业
	private int num;//出现次数
	private List<String> intentNameList;//出现在哪些意图
	private int id;
	public String getParendTradeName() {
		return parendTradeName;
	}
	public void setParendTradeName(String parendTradeName) {
		this.parendTradeName = parendTradeName;
	}
	public String getTradeName() {
		return tradeName;
	}
	public void setTradeName(String tradeName) {
		this.tradeName = tradeName;
	}
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public List<String> getIntentNameList() {
		return intentNameList;
	}
	public void setIntentNameList(List<String> intentNameList) {
		this.intentNameList = intentNameList;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	
}
