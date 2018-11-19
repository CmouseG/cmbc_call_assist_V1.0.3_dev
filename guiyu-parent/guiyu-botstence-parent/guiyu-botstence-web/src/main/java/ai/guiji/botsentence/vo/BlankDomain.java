package ai.guiji.botsentence.vo;

import java.util.List;

public class BlankDomain {

	private String id;
	private String mainFlow;
	private String branchName;
	private String keyWords;
	private String next;
	private String processId;
	private String isInterrupt;
	private String level;
	private String domainName;
	private String content;
	private String to;
	private String type;
	private String startExplainText;
	private String startExplainUrl;
	private List<FlowPositionVO> list;
	private int positionX;
	private int positionY;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	public String getMainFlow() {
		return mainFlow;
	}
	public void setMainFlow(String mainFlow) {
		this.mainFlow = mainFlow;
	}
	public String getBranchName() {
		return branchName;
	}
	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}
	public String getKeyWords() {
		return keyWords;
	}
	public void setKeyWords(String keyWords) {
		this.keyWords = keyWords;
	}
	public String getNext() {
		return next;
	}
	public void setNext(String next) {
		this.next = next;
	}
	public String getProcessId() {
		return processId;
	}
	public void setProcessId(String processId) {
		this.processId = processId;
	}
	public String getIsInterrupt() {
		return isInterrupt;
	}
	public void setIsInterrupt(String isInterrupt) {
		this.isInterrupt = isInterrupt;
	}
	public String getLevel() {
		return level;
	}
	public void setLevel(String level) {
		this.level = level;
	}
	public String getDomainName() {
		return domainName;
	}
	public void setDomainName(String domainName) {
		this.domainName = domainName;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getTo() {
		return to;
	}
	public void setTo(String to) {
		this.to = to;
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
	public List<FlowPositionVO> getList() {
		return list;
	}
	public void setList(List<FlowPositionVO> list) {
		this.list = list;
	}
	public int getPositionX() {
		return positionX;
	}
	public void setPositionX(int positionX) {
		this.positionX = positionX;
	}
	public int getPositionY() {
		return positionY;
	}
	public void setPositionY(int positionY) {
		this.positionY = positionY;
	}
	
	
}
