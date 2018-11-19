package ai.guiji.botsentence.vo;

public class TtsBackup {

	private String processId;
	private String templateId;
	private String voliceId;
	private String backup;
	private String content;
	private String url;
	
	public String getVoliceId() {
		return voliceId;
	}
	public void setVoliceId(String voliceId) {
		this.voliceId = voliceId;
	}
	public String getBackup() {
		return backup;
	}
	public void setBackup(String backup) {
		this.backup = backup;
	}
	public String getProcessId() {
		return processId;
	}
	public void setProcessId(String processId) {
		this.processId = processId;
	}
	public String getTemplateId() {
		return templateId;
	}
	public void setTemplateId(String templateId) {
		this.templateId = templateId;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	
	
	
}
