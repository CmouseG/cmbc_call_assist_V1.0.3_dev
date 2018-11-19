package ai.guiji.botsentence.dao.entity;

public class BusinessAnswerTaskExt {
	//流程id
	private String processId;
	//用户问题
	private String userAsk;
	//文案
	private String content;
	//关键字
	private String keyWords;
	
	private String voliceId;
	
	private String voliceUrl;
	
	private String intentId;
	
	private String branchId;
	
	private String no;
	
	private int index;
	
	public String getProcessId() {
		return processId;
	}

	public void setProcessId(String processId) {
		this.processId = processId;
	}

	public String getUserAsk() {
		return userAsk;
	}

	public void setUserAsk(String userAsk) {
		this.userAsk = userAsk;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getKeyWords() {
		return keyWords;
	}

	public void setKeyWords(String keyWords) {
		this.keyWords = keyWords;
	}

	public String getVoliceId() {
		return voliceId;
	}

	public void setVoliceId(String voliceId) {
		this.voliceId = voliceId;
	}

	public String getVoliceUrl() {
		return voliceUrl;
	}

	public void setVoliceUrl(String voliceUrl) {
		this.voliceUrl = voliceUrl;
	}

	public String getIntentId() {
		return intentId;
	}

	public void setIntentId(String intentId) {
		this.intentId = intentId;
	}

	public String getBranchId() {
		return branchId;
	}

	public void setBranchId(String branchId) {
		this.branchId = branchId;
	}

	public String getNo() {
		return no;
	}

	public void setNo(String no) {
		this.no = no;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}
	
	
}