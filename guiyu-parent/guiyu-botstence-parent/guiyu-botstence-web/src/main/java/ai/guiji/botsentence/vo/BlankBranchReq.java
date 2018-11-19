package ai.guiji.botsentence.vo;

import java.util.List;

public class BlankBranchReq {

	private List<BlankBranch> list;
	private String processId;
	private String domainId;
	
	public List<BlankBranch> getList() {
		return list;
	}
	public void setList(List<BlankBranch> list) {
		this.list = list;
	}
	public String getProcessId() {
		return processId;
	}
	public void setProcessId(String processId) {
		this.processId = processId;
	}
	public String getDomainId() {
		return domainId;
	}
	public void setDomainId(String domainId) {
		this.domainId = domainId;
	}
	
	
}
