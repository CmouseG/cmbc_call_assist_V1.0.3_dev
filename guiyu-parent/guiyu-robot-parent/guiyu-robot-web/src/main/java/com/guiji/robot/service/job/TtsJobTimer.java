package com.guiji.robot.service.job;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.guiji.component.lock.DistributedLockHandler;
import com.guiji.component.lock.Lock;
import com.guiji.robot.constants.RobotConstants;
import com.guiji.robot.dao.TtsWavHisMapper;
import com.guiji.robot.dao.entity.TtsCallbackHis;
import com.guiji.robot.dao.entity.TtsWavHis;
import com.guiji.robot.dao.entity.TtsWavHisExample;
import com.guiji.robot.model.TtsCallback;
import com.guiji.robot.service.ITtsWavService;
import com.guiji.robot.service.impl.AiNewTransService;
import com.guiji.robot.util.ListUtil;
import com.guiji.utils.BeanUtil;
import com.guiji.utils.JsonUtils;
import com.guiji.utils.StrUtils;

/** 
* @ClassName: TtsJobTimer 
* @Description: TTS处理任务单元
* @date 2018年12月3日 上午9:28:33 
* @version V1.0  
*/
@Component
public class TtsJobTimer {
	private final Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	DistributedLockHandler distributedLockHandler;
	@Autowired
	TtsWavHisMapper ttsWavHisMapper;
	@Autowired
	ITtsWavService iTtsWavService;
	@Autowired
	AiNewTransService aiNewTransService;
	
	
	/**
	 * TTS查证服务
	 * 处理TTS合成超过5分钟仍是处理中的数据
	 */
	@Scheduled(cron="0 0/5 9-20 * * ?")
    public void ttsCheck(){
    	Lock lock = new Lock("LOCK_ROBOT_TTS_CHECK_JOB", "LOCK_ROBOT_TTS_CHECK_JOB");
    	if (distributedLockHandler.tryLock(lock)) { // 默认锁设置
    		long beginTime = System.currentTimeMillis();
            logger.info("定时任务，TTS查证...");
            //查询TTS数据超过5分钟的数据且状态仍为进行中的数据，主动发起查证
            
            long endTime = System.currentTimeMillis();
            logger.info("定时任务，用时{}S,[TTS查证]完成...",(endTime-beginTime)/1000);
            distributedLockHandler.releaseLock(lock);	//释放锁
    	}else {
    		logger.warn("定时任务[TTS查证]未能获取锁！！！");
    	}
    }
	
	
	
	/**
	 * TTS异常重试机制
	 * TTS合成状态为异常，且失败次数小于等于3次的服务重试下
	 * 此处只处理，本地合成失败的数据
	 */
	@Scheduled(cron="0 0/5 9-20 * * ?")
    public void ttsErrorRetry(){
		Lock lock = new Lock("LOCK_ROBOT_TTS_RETRY_JOB", "LOCK_ROBOT_TTS_RETRY_JOB");
    	if (distributedLockHandler.tryLock(lock)) { // 默认锁设置
    		long beginTime = System.currentTimeMillis();
            logger.info("定时任务，TTS查证...");
            //查询TTS数据状态为F，且异常类型为L（本地失败），且尝试次数<=3的数据
            TtsWavHisExample example = new TtsWavHisExample();
            example.createCriteria().andStatusEqualTo(RobotConstants.TTS_STATUS_F)
            	.andErrorTypeEqualTo(RobotConstants.TTS_ERROR_TYPE_L).andErrorTryNumLessThanOrEqualTo(3);
            //查询tts本地失败，且尝试次数小于<=3的数据重试
            List<TtsWavHis> ttsWavHisList = ttsWavHisMapper.selectByExample(example);
            if(ListUtil.isNotEmpty(ttsWavHisList)) {
            	List<TtsWavHis> errorTtsWavHisList = new ArrayList<TtsWavHis>();	//重新发起时校验没通过的数据
            	//需要重新发起异步本地处理的list
            	List<TtsCallback> ttsCallbackHistList = new ArrayList<TtsCallback>();
            	for(TtsWavHis ttsWavHis : ttsWavHisList) {
            		String busiId = ttsWavHis.getBusiId();	//调用TTS的业务编号
            		if(StrUtils.isNotEmpty(busiId)) {
            			//查询TTS回调本地历史表
            			TtsCallbackHis ttsCallbackHis = iTtsWavService.queryTtsCallbackHisByBusiId(busiId);
            			if(ttsCallbackHis != null) {
            				TtsCallback ttsCallback = new TtsCallback();
            				BeanUtil.copyProperties(ttsCallbackHis, ttsCallback);	//属性拷贝
            				if(StrUtils.isNotEmpty(ttsCallbackHis.getTtsJsonData())) {
            					//将JSON转对象
            					Map<String,String> audios = JsonUtils.json2Bean(ttsCallbackHis.getTtsJsonData(), Map.class);
            					ttsCallback.setAudios(audios);
            				}
            				ttsCallbackHistList.add(ttsCallback);
            			}else {
            				logger.error("TTS重新{}发起异常,查不到本地callback历史！",ttsWavHis);
            				errorTtsWavHisList.add(ttsWavHis);
            			}
            		}else {
            			logger.error("TTS重新{}发起异常,busiId为空！",ttsWavHis);
            			errorTtsWavHisList.add(ttsWavHis);
            		}
            	}
            	if(ListUtil.isNotEmpty(errorTtsWavHisList)) {
                	logger.info("重新发起TTS本地处理，失败的数据，共计{}条",errorTtsWavHisList.size());
                	for(TtsWavHis ttsWavHis : errorTtsWavHisList) {
                		ttsWavHis.setErrorTryNum((ttsWavHis.getErrorTryNum()==null?0:ttsWavHis.getErrorTryNum())+1);	//失败次数+1
                		aiNewTransService.recordTtsWav(ttsWavHis);
                	}
                }
            	if(ListUtil.isNotEmpty(ttsCallbackHistList)) {
            		logger.info("需要重新发起本地TTS处理的数据，共计{}条",ttsCallbackHistList.size());
            		iTtsWavService.asynTtsCallback(ttsCallbackHistList);
            	}
            }
            long endTime = System.currentTimeMillis();
            logger.info("定时任务，用时{}S,[TTS查证]完成...",(endTime-beginTime)/1000);
            distributedLockHandler.releaseLock(lock);	//释放锁
    	}else {
    		logger.warn("定时任务[TTS查证]未能获取锁！！！");
    	}
	}
}
