package com.guiji.botsentence.dao.ext;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.guiji.botsentence.dao.entity.BotSentenceShareAuth;

public interface BotSentenceShareAuthExtMapper {

	public List<BotSentenceShareAuth> queryBotSentenceShareMarket(@Param("orgCode") String orgCode, @Param("templateName") String templateName, @Param("nickName") String nickName);
}
