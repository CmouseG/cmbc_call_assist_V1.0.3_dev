package com.guiji.botsentence.service.impl;

public class DomainIndex {

	private String domainName;
	private String domainId;
	private int index;
	public String getDomainName() {
		return domainName;
	}
	public void setDomainName(String domainName) {
		this.domainName = domainName;
	}
	public String getDomainId() {
		return domainId;
	}
	public void setDomainId(String domainId) {
		this.domainId = domainId;
	}
	public int getIndex() {
		return index;
	}
	public void setIndex(int index) {
		this.index = index;
	}
	@Override
	public String toString() {
		return "DomainIndex [domainName=" + domainName + ", domainId=" + domainId + ", index=" + index + "]";
	}
	
	
}
