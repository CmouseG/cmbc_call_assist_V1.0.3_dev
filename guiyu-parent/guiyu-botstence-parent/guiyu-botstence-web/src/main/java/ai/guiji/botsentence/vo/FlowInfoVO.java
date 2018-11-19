package ai.guiji.botsentence.vo;

import java.util.List;

public class FlowInfoVO {

	private List<FlowNode> nodes;
	
	private List<FlowEdge> edges;
	
	private String processId;
	
	private String domainId;
	
	private String branchId;
	
	public List<FlowNode> getNodes() {
		return nodes;
	}

	public void setNodes(List<FlowNode> nodes) {
		this.nodes = nodes;
	}

	public List<FlowEdge> getEdges() {
		return edges;
	}

	public void setEdges(List<FlowEdge> edges) {
		this.edges = edges;
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

	public String getBranchId() {
		return branchId;
	}

	public void setBranchId(String branchId) {
		this.branchId = branchId;
	}

}
