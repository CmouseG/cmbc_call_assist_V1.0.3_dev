package com.guiji.botsentence.vo;

public class RequestTtsVO {

	private String task_id;
	private String sentence;
	public String getTask_id() {
		return task_id;
	}
	public void setTask_id(String task_id) {
		this.task_id = task_id;
	}
	public String getSentence() {
		return sentence;
	}
	public void setSentence(String sentence) {
		this.sentence = sentence;
	}
	@Override
	public String toString() {
		return "RequestTtsVO [task_id=" + task_id + ", sentence=" + sentence + "]";
	}
	
	
}
