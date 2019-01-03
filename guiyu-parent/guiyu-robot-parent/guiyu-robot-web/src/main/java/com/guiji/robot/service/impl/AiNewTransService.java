package com.guiji.robot.service.impl;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.guiji.auth.api.IAuth;
import com.guiji.component.result.Result.ReturnData;
import com.guiji.da.api.IDaRemote;
import com.guiji.da.model.RobotCallHis;
import com.guiji.robot.dao.TtsCallbackHisMapper;
import com.guiji.robot.dao.TtsWavHisMapper;
import com.guiji.robot.dao.entity.TtsCallbackHis;
import com.guiji.robot.dao.entity.TtsWavHis;
import com.guiji.utils.StrUtils;

/** 
* @ClassName: AiNewTransService 
* @Description: 独立事务服务类，在一个service中独立另一个事务同个service没有效果的，此处单拉一个service用来处理这个问题
* @date 2018年11月21日 下午4:51:17 
* @version V1.0  
*/
@Service
public class AiNewTransService {
	private final Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	TtsWavHisMapper ttsWavHisMapper;
	@Autowired
	TtsCallbackHisMapper ttsCallbackHisMapper;
	@Autowired
	IAuth iAuth;
	@Autowired
	IDaRemote iDaRemote; 
	
	/**
	 * 保存或者更新一TTS合成信息
	 * 独立事物
	 * @param ttsWavHis
	 * @return
	 */
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	public TtsWavHis recordTtsWav(TtsWavHis ttsWavHis) {
		if(ttsWavHis != null) {
			if(StrUtils.isEmpty(ttsWavHis.getId())) {
				//如果主键为空，那么新增一条信息
				ttsWavHis.setCrtTime(new Date());
				if(ttsWavHis.getErrorTryNum()==null) {
					ttsWavHis.setErrorTryNum(0);
				}
				ttsWavHisMapper.insert(ttsWavHis);
			}else {
				//主键不为空，更新信息
				ttsWavHisMapper.updateByPrimaryKeyWithBLOBs(ttsWavHis);
			}
		}
		return ttsWavHis;
	}
	
	
	/**
	 * 保存一条TTS合成的回call数据
	 * 独立事务
	 * @param ttsCallbackHis
	 */
//	@Transactional(propagation=Propagation.REQUIRES_NEW)
	public TtsCallbackHis recordTtsCallback(TtsCallbackHis ttsCallbackHis) {
		if(ttsCallbackHis != null) {
			if(StrUtils.isEmpty(ttsCallbackHis.getId())) {
				//如果主键为空，那么新增一条信息
				ttsCallbackHis.setCrtTime(new Date());
				ttsCallbackHisMapper.insert(ttsCallbackHis);
			}else {
				//主键不为空，更新信息
				ttsCallbackHisMapper.updateByPrimaryKeyWithBLOBs(ttsCallbackHis);
			}
		}
		return ttsCallbackHis;
	}
	
	
	/**
	 * 保存或更新一个通话记录，并将通话记录送到DA做量化分析使用
	 * 独立事物（删）
	 * 注意：此新事务类作废（在大并发场景下不适用new新事务，可能会导致数据库连接不够或者其他原因，导致等待超时的问题
*               大并发场景下，不适合new新事物，不适合异步，一定要注意）
	 * @param ttsWavHis
	 * @return
	 */
//	@Transactional(propagation=Propagation.REQUIRES_NEW)
	public RobotCallHis recordRobotCallHis(RobotCallHis robotCallHis) {
		if(robotCallHis != null) {
			try {
				iDaRemote.recordRobotCallHis(robotCallHis);
			} catch (Exception e) {
				//不抛出异常,不能影响正常通话
				logger.error("保存通话"+robotCallHis.getSeqId()+"记录发生异常",e);
			}
		}
		return robotCallHis;
	}
	
	/**
	 * 更新通话记录状态
	 * @param seqId
	 * @param callStatus
	 */
	public void updateRobotCallStatus(String seqId,int callStatus) {
		if(StrUtils.isNotEmpty(seqId)) {
			try {
				iDaRemote.updateCallStatus(seqId, callStatus);
			} catch (Exception e) {
				//不抛出异常,不能影响正常通话
				logger.error("更新通话状态"+seqId+"记录发生异常",e);
			}
		}
	}
	
}
