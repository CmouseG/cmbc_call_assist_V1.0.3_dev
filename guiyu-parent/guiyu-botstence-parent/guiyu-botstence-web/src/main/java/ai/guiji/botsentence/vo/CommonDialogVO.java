package ai.guiji.botsentence.vo;

/**
 * 通用对话前台显示
 * @author 张朋
 *
 */
public class CommonDialogVO {

	 private String branchId;

	 private String branchName;
	 
	 private String processId;

	 private String templateId;
	 
	 private String yujin;
	 
	 private String huashu;
	 
	 private String luoji;
	 
	 private long voliceId;
	 
	 private String voliceUrl;

	 private String domain;
	 
	 private String title;
	 
	 private String voliceName;
	 
	 private String flag;
	 
	public String getBranchId() {
		return branchId;
	}

	public void setBranchId(String branchId) {
		this.branchId = branchId;
	}

	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
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

	public String getYujin() {
		return yujin;
	}

	public void setYujin(String yujin) {
		this.yujin = yujin;
	}

	public String getHuashu() {
		return huashu;
	}

	public void setHuashu(String huashu) {
		this.huashu = huashu;
	}

	public String getLuoji() {
		return luoji;
	}

	public void setLuoji(String luoji) {
		this.luoji = luoji;
	}
	
	

	public String getVoliceUrl() {
		return voliceUrl;
	}

	public void setVoliceUrl(String voliceUrl) {
		this.voliceUrl = voliceUrl;
	}

	
	
	public long getVoliceId() {
		return voliceId;
	}

	public void setVoliceId(long voliceId) {
		this.voliceId = voliceId;
	}

	
	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	

	public String getVoliceName() {
		return voliceName;
	}

	public void setVoliceName(String voliceName) {
		this.voliceName = voliceName;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	@Override
	public String toString() {
		return "CommonDialogVO [branchId=" + branchId + ", branchName=" + branchName + ", processId=" + processId
				+ ", templateId=" + templateId + ", yujin=" + yujin + ", huashu=" + huashu + ", luoji=" + luoji
				+ ", voliceId=" + voliceId + ", voliceUrl=" + voliceUrl + ", domain=" + domain + ", title=" + title
				+ ", voliceName=" + voliceName + ", flag=" + flag + "]";
	}

	
	 
	 
}
