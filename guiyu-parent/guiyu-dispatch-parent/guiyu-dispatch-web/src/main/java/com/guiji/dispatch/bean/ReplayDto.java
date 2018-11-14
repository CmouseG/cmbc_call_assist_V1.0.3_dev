package com.guiji.dispatch.bean;

/**
 * 重播标签类型dto
 * @author Administrator
 *
 */
public class ReplayDto {
	private String label;
	
	private String labelType;
	//立即重播  1 立即重播 0不立即重播
	private String isReplay;
	//重播时间
	private String replayTime;
	
	private String times;
	
	private String temp;

	
	
	public String getLabelType() {
		return labelType;
	}

	public void setLabelType(String labelType) {
		this.labelType = labelType;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getIsReplay() {
		return isReplay;
	}

	public void setIsReplay(String isReplay) {
		this.isReplay = isReplay;
	}

	public String getReplayTime() {
		return replayTime;
	}

	public void setReplayTime(String replayTime) {
		this.replayTime = replayTime;
	}

	public String getTimes() {
		return times;
	}

	public void setTimes(String times) {
		this.times = times;
	}

	public String getTemp() {
		return temp;
	}

	public void setTemp(String temp) {
		this.temp = temp;
	}
	
	
	
	

}
