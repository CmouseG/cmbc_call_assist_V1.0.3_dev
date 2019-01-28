package com.guiji.botsentence.vo;

public class RequestCrmVO {

	private String username;
	private String name;
	private String des;
	private String key_str;
	private String is_tts;
	private String is_visit;
	private String file;
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDes() {
		return des;
	}
	public void setDes(String des) {
		this.des = des;
	}
	public String getKey_str() {
		return key_str;
	}
	public void setKey_str(String key_str) {
		this.key_str = key_str;
	}
	public String getIs_tts() {
		return is_tts;
	}
	public void setIs_tts(String is_tts) {
		this.is_tts = is_tts;
	}
	public String getIs_visit() {
		return is_visit;
	}
	public void setIs_visit(String is_visit) {
		this.is_visit = is_visit;
	}
	public String getFile() {
		return file;
	}
	public void setFile(String file) {
		this.file = file;
	}
	@Override
	public String toString() {
		return "RequestCrmVO [username=" + username + ", name=" + name + ", des=" + des + ", key_str=" + key_str
				+ ", is_tts=" + is_tts + ", is_visit=" + is_visit + ", file=" + file + "]";
	}
	
	
}
