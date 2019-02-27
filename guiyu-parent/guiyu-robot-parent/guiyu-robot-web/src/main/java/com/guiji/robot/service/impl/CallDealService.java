package com.guiji.robot.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.guiji.robot.constants.RobotConstants;
import com.guiji.robot.model.AiCallStartReq;
import com.guiji.robot.model.AiFlowMsgPushReq;
import com.guiji.robot.service.vo.CallInfo;
import com.guiji.robot.service.vo.CallSentence;
import com.guiji.robot.util.DataLocalCacheUtil;
import com.guiji.user.dao.entity.SysOrganization;
import com.guiji.utils.BeanUtil;
import com.guiji.utils.JsonUtils;
import com.guiji.utils.RedisUtil;
import com.guiji.utils.StrUtils;

import lombok.extern.slf4j.Slf4j;

/** 
* @ClassName: CallAsynDealService 
* @Description: 通话处理服务
* @date 2019年2月21日 上午10:31:35 
* @version V1.0  
*/
@Slf4j
@Service
public class CallDealService {
	private static final String INCR_KEY = "ROBOT_CALL_INCR";	//电话编号
	private static final String NEXT_INCR_KEY = "ROBOT_CALL_NEXT_INCR_";	//下个可以使用的计数
	@Autowired
	AiCacheService aiCacheService;
	@Autowired
	RedisUtil redisUtil;
	@Autowired
	DataLocalCacheUtil dataLocalCacheUtil;
	
	/**
	 * 记录restore接口开始通话数据
	 * @param aiCallStartReq
	 * @param sellbotRsp
	 */
	@Async
	public void aiCallStart(AiCallStartReq aiCallStartReq,String sellbotRsp) {
		CallSentence callSentence = JsonUtils.json2Bean(sellbotRsp, CallSentence.class); //sellbot返回数据组装
		callSentence.setRecordTime(new Date());	//记录时间
		callSentence.setDiaType(RobotConstants.DIA_TYPE_AI); //对话主体类型：AI说的话
		List<CallSentence> sentenceList = new ArrayList<CallSentence>();
		sentenceList.add(callSentence);
		CallInfo callInfo = new CallInfo();
		BeanUtil.copyProperties(aiCallStartReq, callInfo);	//属性拷贝
		callInfo.setCallStartTime(new Date());	//呼叫开始时间
		callInfo.setIntentLevel(callSentence.getIntent()); //意向
		callInfo.setCurrent_domain(callSentence.getAnswered_domain());	//当前域
		callInfo.setIncr(this.getNextIncrNo(aiCallStartReq)); //获取新电话编号
		callInfo.setSentenceList(sentenceList);	//开场白
		aiCacheService.cacheUserCalls(aiCallStartReq.getUserId(), callInfo);	//放入cache
	}
	
	
	/**
	 * 同步客户推送的消息流
	 * @param aiFlowMsgPushReq
	 */
	@Async
	public void flowMsgPush(AiFlowMsgPushReq aiFlowMsgPushReq) {
		//获取到通话记录
		CallInfo callInfo = aiCacheService.queryUserCall(aiFlowMsgPushReq.getUserId(),aiFlowMsgPushReq.getSeqId());
		CallSentence callSentence = new CallSentence();
		callSentence.setSentence(aiFlowMsgPushReq.getSentence());
		callSentence.setDiaType(RobotConstants.DIA_TYPE_CUST); //对话主体类型：客户说的话
		callSentence.setRecordTime(new Date());	//记录时间
		callInfo.getSentenceList().add(callSentence);
		aiCacheService.cacheUserCalls(aiFlowMsgPushReq.getUserId(), callInfo);	//放入cache
	}
	
	/**
	 * 客户、AI交互
	 * @param aiFlowMsgPushReq
	 * @param sellbotRsp
	 */
	@Async
	public void aiCallNext(boolean interruptFlag,String userId,String seqId,String sellbotRsp) {
		CallSentence callSentence = JsonUtils.json2Bean(sellbotRsp, CallSentence.class); //sellbot返回数据组装
		callSentence.setRecordTime(new Date());	//记录时间
		callSentence.setDiaType(RobotConstants.DIA_TYPE_AI); //对话主体类型：AI说的话
		callSentence.setInterruptFlag(interruptFlag); //是否打断
		//查询用户的某通电话缓存数据
		CallInfo callInfo = aiCacheService.queryUserCall(userId,seqId);
		callInfo.setDialogCount(callInfo.getDialogCount()+1); //对话轮数 
		callInfo.setIntentLevel(callSentence.getIntent()); //意向
		callInfo.setCurrent_domain(callSentence.getAnswered_domain());	//当前域
		//如果交互时sellbot返回的数据，有客户的话，那么需要把前边推送的消息中同一句话删除掉，否则会重复显示
		if(StrUtils.isNotEmpty(callSentence.getSentence())) {
			//删除最后一条数据
			for(int i=callInfo.getSentenceList().size()-1;i>=0;i--){
				CallSentence existSentence = callInfo.getSentenceList().get(i);
				if(RobotConstants.DIA_TYPE_CUST == existSentence.getDiaType() 
						&& callSentence.getSentence().equals(existSentence.getSentence())){
					callInfo.getSentenceList().remove(i);
					break;
				}
			}
		}
		callInfo.getSentenceList().add(callSentence);
		aiCacheService.cacheUserCalls(userId, callInfo);	//放入cache
	}
	
	public static void main(String[] args) {
		
	}
	
	/**
	 * 清除一通电话
	 * @param userId
	 * @param seqId
	 */
	@Async
	public void delUserCall(String userId,String seqId) {
		CallInfo callInfo = aiCacheService.queryUserCall(userId, seqId);
		if(callInfo!=null) {
			//将此通电话编号入队给其他电话使用
			//获取机构
			SysOrganization org = dataLocalCacheUtil.queryUserRealOrg(userId);
			redisUtil.rightPush(NEXT_INCR_KEY+org.getCode(),callInfo.getIncr());
			//清除电话cache
			aiCacheService.delUserCall(userId, seqId);
		}
		
		//TODO 提交da数据落地
	}
	
	/**
	 * 清除该用户下全部电话
	 * @param userId
	 */
	@Async
	public void delUserCalls(String userId) {
		aiCacheService.delUserCalls(userId);
	}
	
	
	/**
	 * 获取下个电话编号值
	 * 为了在前端长链接时保持顺序，电话挂断删除时将原编号按企业入队，使用时从redis中出队使用，这样可以再前端展示时尽量维持原顺序，如原顺序A/B/C，A打完后，将编号给D，这样顺序是D/B/C，其他不变的数据顺序尽量不动
	 * @param aiCallStartReq
	 * @return
	 */
	private long getNextIncrNo(AiCallStartReq aiCallStartReq) {
		//获取机构
		SysOrganization org = dataLocalCacheUtil.queryUserRealOrg(aiCallStartReq.getUserId());
		Object obj = redisUtil.lrightPop(NEXT_INCR_KEY+org.getCode());
		if(obj!=null) {
			if(obj instanceof Long) {
				return (Long)obj;
			}else if(obj instanceof Integer) {
				return (Integer)obj;
			}
		}
		//如果队中没有了，那么计数器+1
		return redisUtil.incr(INCR_KEY, 1);
	}
}
