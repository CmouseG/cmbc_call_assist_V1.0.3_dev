package com.guiji.botsentence.service;

import java.util.List;

import com.guiji.botsentence.dao.entity.BotSentenceProcess;

/**
 * 
* @ClassName: IBotSentenceDomainService
* @Description: domain相关服务类
* @author: 张朋
* @date 2018年8月22日 下午4:36:02
* @version V1.0
 */
public interface IBotSentenceDomainService {

	public List<BotSentenceProcess> queryDomainList(String processId, String category); 
	
}
