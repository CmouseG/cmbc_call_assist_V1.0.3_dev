package com.guiji.botsentence.vo;

import java.util.List;

public class SaveNodeVO {

	private String processId;
	private List<FlowNode> nodes;
	private List<FlowEdge> edges;
	public String getProcessId() {
		return processId;
	}
	public void setProcessId(String processId) {
		this.processId = processId;
	}
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

	
}
