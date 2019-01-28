package com.guiji.botsentence.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.guiji.botsentence.dao.BotSentenceTradeMapper;
import com.guiji.botsentence.dao.entity.BotSentenceTrade;
import com.guiji.botsentence.dao.entity.BotSentenceTradeExample;

@Component
public class IndustryUtil {

	public static Map<String, String> map = new HashMap<>();
	
	@Autowired
	private BotSentenceTradeMapper botSentenceTradeMapper;
	
	@PostConstruct
	public void initIndustry() {
		BotSentenceTradeExample example = new BotSentenceTradeExample();
		List<BotSentenceTrade> list = botSentenceTradeMapper.selectByExample(example);
		if(null != list && list.size() > 0) {
			for(BotSentenceTrade trade : list) {
				map.put(trade.getIndustryId(), trade.getIndustryName());
			}
		}
	}
}
