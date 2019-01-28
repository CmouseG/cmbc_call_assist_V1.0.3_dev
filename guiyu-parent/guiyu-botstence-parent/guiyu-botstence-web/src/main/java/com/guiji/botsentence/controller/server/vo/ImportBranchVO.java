package com.guiji.botsentence.controller.server.vo;

import java.util.List;

/**
 * positive
 * @Description:
 * @author liyang  
 * @date 2018年8月22日  
 *
 */
public class ImportBranchVO {

	private String next;
	private String keys;
	private String end;
	private List<String> response;
	private boolean is_special_limit_free;
	private String user_ask;
	
	public String getNext() {
		return next;
	}
	public void setNext(String next) {
		this.next = next;
	}
	public String getKeys() {
		return keys;
	}
	public void setKeys(String keys) {
		this.keys = keys;
	}
	public String getEnd() {
		return end;
	}
	public void setEnd(String end) {
		this.end = end;
	}
	public List<String> getResponse() {
		return response;
	}
	public void setResponse(List<String> response) {
		this.response = response;
	}
	public boolean isIs_special_limit_free() {
		return is_special_limit_free;
	}
	public void setIs_special_limit_free(boolean is_special_limit_free) {
		this.is_special_limit_free = is_special_limit_free;
	}
	public String getUser_ask() {
		return user_ask;
	}
	public void setUser_ask(String user_ask) {
		this.user_ask = user_ask;
	}
	
}
