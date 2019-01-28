package com.guiji.botsentence.service;

/**
 * 操作日志服务
 * @author 张朋
 *
 */
public interface IBotSentenceLogService {

	public void saveLog(String operator, String userId);
}
