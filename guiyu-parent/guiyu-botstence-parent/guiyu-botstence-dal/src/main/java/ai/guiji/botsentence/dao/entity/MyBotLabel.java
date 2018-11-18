package ai.guiji.botsentence.dao.entity;

public class MyBotLabel {
	
	private static final long serialVersionUID = 1L;

	private String labelId;
    
    private String processId;

    private String labelName;

    private String displayKeywords;

    private Double scoreUp;

    private Double scoreLow;
    
    private String tips;
    
    private String keywords;



	public String getLabelId() {
		return labelId;
	}

	public void setLabelId(String labelId) {
		this.labelId = labelId;
	}

	public String getProcessId() {
		return processId;
	}

	public void setProcessId(String processId) {
		this.processId = processId;
	}

	public String getLabelName() {
		return labelName;
	}

	public void setLabelName(String labelName) {
		this.labelName = labelName;
	}

	public String getDisplayKeywords() {
		return displayKeywords;
	}

	public void setDisplayKeywords(String displayKeywords) {
		this.displayKeywords = displayKeywords;
	}

	public Double getScoreUp() {
		return scoreUp;
	}

	public void setScoreUp(Double scoreUp) {
		this.scoreUp = scoreUp;
	}

	public Double getScoreLow() {
		return scoreLow;
	}

	public void setScoreLow(Double scoreLow) {
		this.scoreLow = scoreLow;
	}

	public String getTips() {
		return tips;
	}

	public void setTips(String tips) {
		this.tips = tips;
	}

	public String getKeywords() {
		return keywords;
	}

	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}
	

}
