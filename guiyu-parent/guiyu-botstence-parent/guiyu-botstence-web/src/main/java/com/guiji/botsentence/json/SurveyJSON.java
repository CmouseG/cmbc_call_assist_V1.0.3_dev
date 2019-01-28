package com.guiji.botsentence.json;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.guiji.botsentence.util.BotSentenceUtil;

/**
 * @author 张朋 
 * @description 话术模板，回访需要该 文件，survey.json对象
 *
 */
public class SurveyJSON {

	// 需要采集的域
	private String domain;

	//固定字段
	private Survey_info info;

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	public Survey_info getInfo() {
		return info;
	}

	public void setInfo(Survey_info info) {
		this.info = info;
	}
	
	public static void main(String[] args) throws Exception {
		
	}
	
}
