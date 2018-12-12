package com.guiji.robot.service.vo;

import lombok.Data;

/** 
* @ClassName: AiFlowSentenceCache 
* @Description: Ai消息流数据
* @date 2018年12月11日 下午2:11:43 
* @version V1.0  
*/
@Data
public class AiFlowSentenceCache {
	//用户号
	private String userId;
	//机器人编号
	private String aiNo;
	//会话ID
	private String seqId;
	//客户话
	private String sentence;
	//时间戳
	private Long timestamp;
	
	/**
	 * @param userId
	 * @param aiNo
	 * @param seqId
	 * @param sentence
	 * @param timestamp
	 */
	public AiFlowSentenceCache(String userId, String aiNo, String seqId, String sentence, Long timestamp) {
		super();
		this.userId = userId;
		this.aiNo = aiNo;
		this.seqId = seqId;
		this.sentence = sentence;
		this.timestamp = timestamp;
	}


	/**
	 * 
	 */
	public AiFlowSentenceCache() {
		super();
	}
	
}
