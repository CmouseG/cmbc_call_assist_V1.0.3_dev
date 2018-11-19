package ai.guiji.botsentence.vo;

import java.util.List;

public class ProcessInfo {

	private String processId;
	private List<DomainVO> domins;
	private List<LevelVO> levelList;
	
	public String getProcessId() {
		return processId;
	}
	public void setProcessId(String processId) {
		this.processId = processId;
	}
	public List<DomainVO> getDomins() {
		return domins;
	}
	public void setDomins(List<DomainVO> domins) {
		this.domins = domins;
	}
	public List<LevelVO> getLevelList() {
		return levelList;
	}
	public void setLevelList(List<LevelVO> levelList) {
		this.levelList = levelList;
	}
	
	
}
