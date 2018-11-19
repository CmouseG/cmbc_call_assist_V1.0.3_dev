package ai.guiji.botsentence.vo;

import java.util.List;

public class SaveEdgeVO {

	private String processId;
	private String label;
	private String keyWords;
	private List<FlowNode> nodes;
	private List<FlowEdge> edges;
	public String getProcessId() {
		return processId;
	}
	public void setProcessId(String processId) {
		this.processId = processId;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public String getKeyWords() {
		return keyWords;
	}
	public void setKeyWords(String keyWords) {
		this.keyWords = keyWords;
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
