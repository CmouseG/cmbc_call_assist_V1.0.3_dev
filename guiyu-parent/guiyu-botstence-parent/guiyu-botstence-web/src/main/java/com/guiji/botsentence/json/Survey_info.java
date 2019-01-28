package com.guiji.botsentence.json;

import java.util.List;
import java.util.Map;

public class Survey_info {

	//在采集域自定义的问题，相当于模板中的enter
	private String question;
	
	//type分三种，分别是INTENT, SENTENCE和KEY
	private String type;
	
	//固定字段，表示被叫的回答
	//private Survey_info_answer answer;
	
	private Map<String, Map<String, Object>> answer;
	
	//如果关键词不匹配上述关键词，用default
	private String default_to_format;

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Map<String, Map<String, Object>> getAnswer() {
		return answer;
	}

	public void setAnswer(Map<String, Map<String, Object>> answer) {
		this.answer = answer;
	}

	public String getDefault_to_format() {
		return default_to_format;
	}

	public void setDefault_to_format(String default_to_format) {
		this.default_to_format = default_to_format;
	}


	
	
	
}
