package com.guiji.component.client.config;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix="sellbot.mechine")
public class SellbotMechine {

	private List<Map<String, String>> mechineList = new ArrayList<>();

	public List<Map<String, String>> getMechineList() {
		return mechineList;
	}

	public void setMechineList(List<Map<String, String>> mechineList) {
		this.mechineList = mechineList;
	}
	
}
