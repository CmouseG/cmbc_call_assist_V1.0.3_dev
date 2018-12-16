package ai.guiji.botsentence.dao.entity;

import java.io.Serializable;
import java.util.Date;

public class BotSentenceProcess implements Serializable {
    private String processId;

    private String templateId;

    private String templateName;

    private String templateType;

    private String version;

    private String industry;

    private String accountNo;

    private String oldProcessId;

    private Date crtTime;

    private String crtUser;

    private Date lstUpdateTime;

    private String lstUpdateUser;

    private String state;

    private Date approveTime;

    private String approveUser;

    private String approveNotes;

    private String soundType;
    
    private String needTTs;
	
	private String orgCode;
    private String orgName;

    private String userName;

    private static final long serialVersionUID = 1L;

    public String getProcessId() {
        return processId;
    }

    public void setProcessId(String processId) {
        this.processId = processId == null ? null : processId.trim();
    }

    public String getTemplateId() {
        return templateId;
    }

    public void setTemplateId(String templateId) {
        this.templateId = templateId == null ? null : templateId.trim();
    }

    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName == null ? null : templateName.trim();
    }

    public String getTemplateType() {
        return templateType;
    }

    public void setTemplateType(String templateType) {
        this.templateType = templateType == null ? null : templateType.trim();
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version == null ? null : version.trim();
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry == null ? null : industry.trim();
    }

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo == null ? null : accountNo.trim();
    }

    public String getOldProcessId() {
        return oldProcessId;
    }

    public void setOldProcessId(String oldProcessId) {
        this.oldProcessId = oldProcessId == null ? null : oldProcessId.trim();
    }

    public Date getCrtTime() {
        return crtTime;
    }

    public void setCrtTime(Date crtTime) {
        this.crtTime = crtTime;
    }

    public String getCrtUser() {
        return crtUser;
    }

    public void setCrtUser(String crtUser) {
        this.crtUser = crtUser == null ? null : crtUser.trim();
    }

    public Date getLstUpdateTime() {
        return lstUpdateTime;
    }

    public void setLstUpdateTime(Date lstUpdateTime) {
        this.lstUpdateTime = lstUpdateTime;
    }

    public String getLstUpdateUser() {
        return lstUpdateUser;
    }

    public void setLstUpdateUser(String lstUpdateUser) {
        this.lstUpdateUser = lstUpdateUser == null ? null : lstUpdateUser.trim();
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state == null ? null : state.trim();
    }

    public Date getApproveTime() {
        return approveTime;
    }

    public void setApproveTime(Date approveTime) {
        this.approveTime = approveTime;
    }

    public String getApproveUser() {
        return approveUser;
    }

    public void setApproveUser(String approveUser) {
        this.approveUser = approveUser == null ? null : approveUser.trim();
    }

    public String getApproveNotes() {
        return approveNotes;
    }

    public void setApproveNotes(String approveNotes) {
        this.approveNotes = approveNotes == null ? null : approveNotes.trim();
    }

    public String getSoundType() {
        return soundType;
    }

    public void setSoundType(String soundType) {
        this.soundType = soundType == null ? null : soundType.trim();
    }

    public String getNeedTTs() {
		return needTTs;
	}

	public void setNeedTTs(String needTTs) {
		this.needTTs = needTTs;
	}
	
	public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode == null ? null : orgCode.trim();
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName == null ? null : orgName.trim();
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

	@Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", processId=").append(processId);
        sb.append(", templateId=").append(templateId);
        sb.append(", templateName=").append(templateName);
        sb.append(", templateType=").append(templateType);
        sb.append(", version=").append(version);
        sb.append(", industry=").append(industry);
        sb.append(", accountNo=").append(accountNo);
        sb.append(", oldProcessId=").append(oldProcessId);
        sb.append(", crtTime=").append(crtTime);
        sb.append(", crtUser=").append(crtUser);
        sb.append(", lstUpdateTime=").append(lstUpdateTime);
        sb.append(", lstUpdateUser=").append(lstUpdateUser);
        sb.append(", state=").append(state);
        sb.append(", approveTime=").append(approveTime);
        sb.append(", approveUser=").append(approveUser);
        sb.append(", approveNotes=").append(approveNotes);
        sb.append(", soundType=").append(soundType);
        sb.append(", orgCode=").append(orgCode);
        sb.append(", orgName=").append(orgName);
        sb.append(", userName=").append(userName);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}