package ai.guiji.botsentence.vo;

public class BusinessAnswerVo {
	//流程id
	private String processId;
	//用户问题
	private String userAsk;
	//文案
	private String content;
	//关键字
	private String keyWords;
	//意图id
	private String intentId;
	//音频id
	private String voliceId;
	
	private String templateId;
	
	private String branchId;
	
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

	public String getIntentId() {
		return intentId;
	}

	public void setIntentId(String intentId) {
		this.intentId = intentId;
	}

	public String getVoliceId() {
		return voliceId;
	}

	public void setVoliceId(String voliceId) {
		this.voliceId = voliceId;
	}

	public String getTemplateId() {
		return templateId;
	}

	public void setTemplateId(String templateId) {
		this.templateId = templateId;
	}

	public String getBranchId() {
		return branchId;
	}

	public void setBranchId(String branchId) {
		this.branchId = branchId;
	}

}