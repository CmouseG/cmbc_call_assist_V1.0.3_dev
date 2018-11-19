package ai.guiji.botsentence.vo;

import java.util.List;

public class DomainVO {

	private String name;
	private String text;
	private String url;
	private String level;
	private String domainId;
	private List<NextVO> next;
	private String mainFlow;
	private String mainFlowId;
	private List<RefuseBranchVO> refuses;
	private String type;
	private String startExplainText;
	private String startExplainUrl;
	private String startExplainKeywords;
	private String isMainFlow;
	private int xNum;
	private String content;
	private String lineName;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getLevel() {
		return level;
	}
	public void setLevel(String level) {
		this.level = level;
	}
	public List<NextVO> getNext() {
		return next;
	}
	public void setNext(List<NextVO> next) {
		this.next = next;
	}
	public String getDomainId() {
		return domainId;
	}
	public void setDomainId(String domainId) {
		this.domainId = domainId;
	}
	public String getMainFlow() {
		return mainFlow;
	}
	public void setMainFlow(String mainFlow) {
		this.mainFlow = mainFlow;
	}
	public String getMainFlowId() {
		return mainFlowId;
	}
	public void setMainFlowId(String mainFlowId) {
		this.mainFlowId = mainFlowId;
	}
	public List<RefuseBranchVO> getRefuses() {
		return refuses;
	}
	public void setRefuses(List<RefuseBranchVO> refuses) {
		this.refuses = refuses;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getStartExplainText() {
		return startExplainText;
	}
	public void setStartExplainText(String startExplainText) {
		this.startExplainText = startExplainText;
	}
	public String getStartExplainUrl() {
		return startExplainUrl;
	}
	public void setStartExplainUrl(String startExplainUrl) {
		this.startExplainUrl = startExplainUrl;
	}
	public String getStartExplainKeywords() {
		return startExplainKeywords;
	}
	public void setStartExplainKeywords(String startExplainKeywords) {
		this.startExplainKeywords = startExplainKeywords;
	}
	public String getIsMainFlow() {
		return isMainFlow;
	}
	public void setIsMainFlow(String isMainFlow) {
		this.isMainFlow = isMainFlow;
	}
	public int getxNum() {
		return xNum;
	}
	public void setxNum(int xNum) {
		this.xNum = xNum;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getLineName() {
		return lineName;
	}
	public void setLineName(String lineName) {
		this.lineName = lineName;
	}
	
}
