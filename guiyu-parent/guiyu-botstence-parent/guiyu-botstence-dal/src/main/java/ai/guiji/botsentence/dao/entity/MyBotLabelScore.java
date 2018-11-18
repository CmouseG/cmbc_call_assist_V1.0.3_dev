package ai.guiji.botsentence.dao.entity;

public class MyBotLabelScore {
	
	private static final long serialVersionUID = 1L;

	private String labelId;
    private String labelName;
    private Double scoreUp;

    private Double scoreLow;


	public String getLabelId() {
		return labelId;
	}

	public void setLabelId(String labelId) {
		this.labelId = labelId;
	}

	public String getLabelName() {
		return labelName;
	}

	public void setLabelName(String labelName) {
		this.labelName = labelName;
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
 
	
}
