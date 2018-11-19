package ai.guiji.botsentence.vo;

import java.util.List;

public class TtsBackupVO {

	private String processId;
	private String templateId;
	private List<TtsBackup> backups;
	
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
	public List<TtsBackup> getBackups() {
		return backups;
	}
	public void setBackups(List<TtsBackup> backups) {
		this.backups = backups;
	}
	
	
	
}
