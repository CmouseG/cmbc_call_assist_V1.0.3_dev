package com.guiji.robot.service.job;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.guiji.ai.api.ITts;
import com.guiji.ai.vo.TtsRspVO;
import com.guiji.component.lock.DistributedLockHandler;
import com.guiji.component.lock.Lock;
import com.guiji.component.result.Result.ReturnData;
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
	@Autowired
	ITts iTts;
	
	
	/**
	 * TTS查证服务
	 * 处理TTS合成超过10分钟仍是处理中的数据
	 */
	@Scheduled(cron="0 0/10 9-20 * * ?")
    public void ttsCheck(){
    	Lock lock = new Lock("LOCK_ROBOT_TTS_CHECK_JOB", "LOCK_ROBOT_TTS_CHECK_JOB");
    	if (distributedLockHandler.tryLock(lock)) { // 默认锁设置
    		long beginTime = System.currentTimeMillis();
            logger.info("定时任务，TTS查证...");
            //查询TTS数据超过10分钟的数据且状态仍为进行中的数据，主动发起查证
            TtsWavHisExample example = new TtsWavHisExample();
            //获取10分钟前时间
            Date tenMinBeforeDate = this.getMinFromCurrent(-10);
            example.createCriteria().andStatusEqualTo(RobotConstants.TTS_STATUS_P).andErrorTryNumLessThanOrEqualTo(3).andCrtTimeLessThanOrEqualTo(tenMinBeforeDate);
            //查询tts本地失败，且尝试次数小于<=3的数据重试
            List<TtsWavHis> ttsWavHisList = ttsWavHisMapper.selectByExample(example);
            if(ListUtil.isNotEmpty(ttsWavHisList)) {
            	//需要TTS内部处理的数据
            	List<TtsCallback> ttsCallbackList = new ArrayList<TtsCallback>();
            	for(TtsWavHis ttsWavHis : ttsWavHisList) {
            		String busiId = ttsWavHis.getBusiId();
            		if(StrUtils.isNotEmpty(busiId)) {
            			//业务编号不为空，开始到AI服务进行TTS查证
            			ReturnData<TtsRspVO> ttsRspData = iTts.getTtsResultByBusId(busiId);
            			if(ttsRspData != null && RobotConstants.RSP_CODE_SUCCESS.equals(ttsRspData.getCode()) && ttsRspData.getBody()!=null){
            				TtsRspVO ttsRspVO = ttsRspData.getBody();
            				String status = ttsRspVO.getStatus();	//TTS查证接口返回状态
            				//查证接口只处理终态，成功、失败
            				if(RobotConstants.TTS_INTERFACE_DOING.equals(status)) {
            					//将状态转为内部完成状态
            					status = RobotConstants.TTS_STATUS_S;
            				}else if(RobotConstants.TTS_INTERFACE_FAIL.equals(status)) {
            					//将状态转为内部失败状态
            					status = RobotConstants.TTS_STATUS_F;
            				}else {
            					logger.info("数据{}调研TTS接口状态{}不是终态,continue",busiId,status);
            					continue;
            				}
            				/** 开始保存TTS返回结果  **/
            				TtsCallbackHis ttsCallbackHis = new TtsCallbackHis();
            				ttsCallbackHis.setBusiId(busiId);
            				ttsCallbackHis.setTemplateId(ttsWavHis.getTemplateId());
            				if(ttsRspVO.getAudios()!=null) {
            					//将消息转未JSON报文
            					String jsonData = JSON.toJSONString(ttsRspVO.getAudios());
            					ttsCallbackHis.setTtsJsonData(jsonData);
            				}
            				ttsCallbackHis.setStatus(status);
            				String errorMsg = ttsRspVO.getErrorMsg();
            				if(StrUtils.isNotEmpty(errorMsg) && errorMsg.length()>1024) {
            					//如果异常信息超长，截取下
            					errorMsg = errorMsg.substring(0, 1024);
            				}
            				ttsCallbackHis.setErrorMsg(errorMsg);
            				//新开事务保存
            				aiNewTransService.recordTtsCallback(ttsCallbackHis);
            				/** 开始TTS本次处理  **/
            				TtsCallback ttsCallback = new TtsCallback();
            				BeanUtil.copyProperties(ttsCallbackHis, ttsCallback);
            				ttsCallback.setAudios(ttsRspVO.getAudios());
            				ttsCallbackList.add(ttsCallback);
            			}
            		}
            		ttsWavHis.setErrorTryNum((ttsWavHis.getErrorTryNum()==null?0:ttsWavHis.getErrorTryNum())+1);	//重试次数+1
            		aiNewTransService.recordTtsWav(ttsWavHis);
            	}
            	if(ListUtil.isNotEmpty(ttsWavHisList)) {
            		logger.info("本次需要异步处理的TTS数据量为{}条",ttsWavHisList.size());
            		//异步处理TTS数据
        			iTtsWavService.asynTtsCallback(ttsCallbackList);
            	}
            }
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
            logger.info("定时任务，TTS重试...");
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
    		logger.warn("定时任务[TTS重试]未能获取锁！！！");
    	}
	}
	
	/**
	 * 获取当前时间的N分钟前或者后的日期
	 * getMinFromCurrent
	 * @param min
	 * @return
	 */
	private Date getMinFromCurrent(int min) {
		Calendar beforeTime = Calendar.getInstance();
		beforeTime.add(Calendar.MINUTE, min);// 当前时间几分钟前或者后
		Date beforeD = beforeTime.getTime();
		return beforeD;
	}
}
