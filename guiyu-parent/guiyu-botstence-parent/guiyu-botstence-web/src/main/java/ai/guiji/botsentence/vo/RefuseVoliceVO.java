package ai.guiji.botsentence.vo;

import java.util.List;

public class RefuseVoliceVO {

	private String processId;
	private String content;
	private String voliceId;
	private String name;
	private List<RefuseVoliceVO> list;
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getVoliceId() {
		return voliceId;
	}
	public void setVoliceId(String voliceId) {
		this.voliceId = voliceId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getProcessId() {
		return processId;
	}
	public void setProcessId(String processId) {
		this.processId = processId;
	}
	public List<RefuseVoliceVO> getList() {
		return list;
	}
	public void setList(List<RefuseVoliceVO> list) {
		this.list = list;
	}
	
	
}
