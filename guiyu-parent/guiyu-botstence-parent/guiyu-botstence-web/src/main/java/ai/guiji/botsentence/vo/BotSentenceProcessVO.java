package ai.guiji.botsentence.vo;

import java.io.Serializable;
import java.util.Date;

public class BotSentenceProcessVO implements Serializable {
    private String processId;

    private String templateId;

    private String templateName;

    private String version;

    private String industryId;
    
    private String industry;

    private String accountNo;

    private Date crtTime;
    
    private String crtTimeStr;

    private String crtUser;

    private Date lstUpdateTime;
    
    private String lstUpdateTimeStr;

    private String lstUpdateUser;

    private String state;
    
    private String stateName;

    private Date approveTime;
    
    private String approveTimeStr;

    private String approveUser;

    private String approveNotes;
    
    private String templateType;
    
    private String flag;//00-创建  01-更新
    
    private String host;//域名
    
    private String machineCode;//机器码
    
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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", processId=").append(processId);
        sb.append(", templateId=").append(templateId);
        sb.append(", templateName=").append(templateName);
        sb.append(", version=").append(version);
        sb.append(", industry=").append(industry);
        sb.append(", accountNo=").append(accountNo);
        sb.append(", crtTime=").append(crtTime);
        sb.append(", crtUser=").append(crtUser);
        sb.append(", lstUpdateTime=").append(lstUpdateTime);
        sb.append(", lstUpdateUser=").append(lstUpdateUser);
        sb.append(", state=").append(state);
        sb.append(", approveTime=").append(approveTime);
        sb.append(", approveUser=").append(approveUser);
        sb.append(", approveNotes=").append(approveNotes);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }

	public String getStateName() {
		return stateName;
	}

	public void setStateName(String stateName) {
		this.stateName = stateName;
	}

	public String getIndustryId() {
		return industryId;
	}

	public void setIndustryId(String industryId) {
		this.industryId = industryId;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public String getTemplateType() {
		return templateType;
	}

	public void setTemplateType(String templateType) {
		this.templateType = templateType;
	}

	public String getCrtTimeStr() {
		return crtTimeStr;
	}

	public void setCrtTimeStr(String crtTimeStr) {
		this.crtTimeStr = crtTimeStr;
	}

	public String getLstUpdateTimeStr() {
		return lstUpdateTimeStr;
	}

	public void setLstUpdateTimeStr(String lstUpdateTimeStr) {
		this.lstUpdateTimeStr = lstUpdateTimeStr;
	}

	public String getApproveTimeStr() {
		return approveTimeStr;
	}

	public void setApproveTimeStr(String approveTimeStr) {
		this.approveTimeStr = approveTimeStr;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getMachineCode() {
		return machineCode;
	}

	public void setMachineCode(String machineCode) {
		this.machineCode = machineCode;
	}

	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
    
}