package com.guiji.ai.tts.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

import com.guiji.ai.dao.TtsModelMapper;
import com.guiji.ai.tts.constants.AiConstants;
import com.guiji.ai.tts.constants.GuiyuAIExceptionEnum;
import com.guiji.ai.tts.vo.GpuCountVO;
import com.guiji.ai.tts.vo.TtsGpuVO;
import com.guiji.common.exception.GuiyuException;
import com.guiji.component.lock.DistributedLockHandler;
import com.guiji.component.lock.Lock;
import com.guiji.component.result.Result.ReturnData;
import com.guiji.process.api.IProcessSchedule;
import com.guiji.utils.RedisUtil;

public class DistributeTimerTask {
	private static Logger logger = LoggerFactory.getLogger(DistributeTimerTask.class);

	@Autowired
	IProcessSchedule iProcessSchedule;
	@Autowired
	DistributedLockHandler distributedLockHandler;
	@Autowired
	TtsModelMapper ttsModelMapper;
	@Autowired
    private RedisUtil redisUtil;

	// 定时任务，启动时运行（每3分钟执行一次）
	@Scheduled(fixedRate = 1000*60*3)
	public void task() throws InterruptedException {
		Lock lock = new Lock("LOCK_NAME", "LOCK_VALUE");
		if (distributedLockHandler.tryLock(lock, 30*1000, 50, 3*60*1000)) { // 尝试30s,每30ms尝试一次，持锁时间为3分钟
			try {
				logger.info("查询前10分钟内各模型对应GPU请求情况...");
				List<Map<String, Object>> resultList = ttsModelMapper.selectTenMinutesBefore(new Date()); // resultList中是<A,3>，<B，5>的格式
				if(resultList != null){
					logger.info("请求结果：" + resultList);
					//重新分配策略
					distributionStrategy(resultList);
					
				}else logger.info("前10分钟内没有请求");
			} catch (Exception e) {
				logger.error("分配失败！");
			}
			distributedLockHandler.releaseLock(lock);
		} else {
			logger.warn("未能获取锁！！！");
		}
	}

	// 分配策略
	private void distributionStrategy(List<Map<String, Object>> mapList) throws Exception {
		int requestSum = 0; // 所有模型请求总次数
		int availableGpuSum = 0; // 所有模型可用GPU总数
		List<GpuCountVO> gpuCountList = new ArrayList<>();
		List<GpuCountVO> subGpuCountVOList = new ArrayList<>(); //待回收对象列表
		List<GpuCountVO> addGpuCountVOList = new ArrayList<>(); //待分配对象列表
		List<TtsGpuVO> gpuSumList = new ArrayList<>(); //回收的GUP暂存列表

		//获取所有计算公式需要的值
		for (int i = 0; i < mapList.size(); i++) { //mapList（<model,reqCount>）
			int availableGpuCount = 0; // 模型对应可用GPU数量
			String model = (String) mapList.get(i).get(AiConstants.MODEL);
			List<Object> avaliableGpuList = redisUtil.lGet(AiConstants.GUIYUTTS + model + AiConstants.AVALIABLE, 0, -1); // 获取对应模型可用list
			if(avaliableGpuList != null){
				availableGpuCount = avaliableGpuList.size(); // 单个模型对应可用GPU数量
			}
			availableGpuSum += availableGpuCount;
			int requestCount = (int) mapList.get(i).get(AiConstants.COUNT); // 单个模型请求次数
			requestSum += requestCount;
			gpuCountList.add(new GpuCountVO(model, requestCount, availableGpuCount, 0, avaliableGpuList));
		}

		//计算出changeCount
		Map<String, List<GpuCountVO>> resMap = calChangeCount(requestSum, availableGpuSum, gpuCountList);
		
		subGpuCountVOList = (List<GpuCountVO>) resMap.get(AiConstants.SUB);
		addGpuCountVOList = (List<GpuCountVO>) resMap.get(AiConstants.ADD);
		//对需要增加GPU的模型进行按需排序，优先分配
		Collections.sort(addGpuCountVOList, new Comparator<GpuCountVO>() {
			@Override
			public int compare(GpuCountVO o1, GpuCountVO o2) {
				return o2.getChangeCount() - o1.getChangeCount();
			}
		});
		
		//回收GPU
		gpuSumList = recoverGpu(subGpuCountVOList);
		
		//GPU转移（分配）
		distributeGpu(addGpuCountVOList, gpuSumList);
	}

	//GPU转移（分配）
	private void distributeGpu(List<GpuCountVO> addGpuCountVOList, List<TtsGpuVO> gpuSumList) throws Exception{
		for(int i = 0; i < addGpuCountVOList.size(); i++){
			String model = addGpuCountVOList.get(i).getModel();
			int changeCount = addGpuCountVOList.get(i).getChangeCount();
			int k = 0;
			for(int j = 0; j < changeCount; j++){
				if(!gpuSumList.isEmpty()){
					String fromModel = gpuSumList.get(k++).getModel();
					String ip = gpuSumList.get(k++).getIp();
					String port = gpuSumList.get(k++).getPort();
					//调进程管理接口-模型切换
					ReturnData<Boolean> returnData = iProcessSchedule.changeTTS(fromModel,model,ip,Integer.parseInt(port));
					if(returnData != null && returnData.getBody()){
						//将指定gpu添加到指定model的可用列表中
						addToRedisAvaliableList(model, ip, port);
					}else{
						throw new GuiyuException(GuiyuAIExceptionEnum.EXCP_AI_CHANGE_TTS);
					}
				}else{
					logger.warn("没有可分配的GPU了！");
				}
			}
		}
	}

	//GPU转移（回收）
	private List<TtsGpuVO> recoverGpu(List<GpuCountVO> subGpuCountVOList) throws Exception{
		List<TtsGpuVO> gpuSumList = new ArrayList<>();
		for (int i = 0; i < subGpuCountVOList.size(); i++) {
			String model = subGpuCountVOList.get(i).getModel();
			int changeCount = subGpuCountVOList.get(i).getChangeCount();
			List<Object> gpuList = new ArrayList<>();
			gpuList = subGpuCountVOList.get(i).getGpuList(); //获取对应模型所有可用GPU
			for (int j = 0; j < (Math.abs(changeCount)); j++) {
				String ip = ((GuiyuTtsGpu) gpuList.get(j)).getIp();
				String port = ((GuiyuTtsGpu) gpuList.get(j)).getPort();
				TtsGpuVO ttsGpuVO = new TtsGpuVO(ip, port, model);
				gpuSumList.add(ttsGpuVO);
				// 从可用列表中移除指定GPU
				removeFromRedisAvaliableList(model, ip, port);
			}
		}
		return gpuSumList;
	}

	//将指定gpu添加到指定model的可用列表中
	private void addToRedisAvaliableList(String model, String ip, String port) throws Exception{
		List<Object> avaliableGpuList = redisUtil.lGet(AiConstants.GUIYUTTS + model + AiConstants.AVALIABLE, 0 , -1); // 获取对应模型可用list
		avaliableGpuList.add(new GuiyuTtsGpu(ip, port));
		redisUtil.lSet(AiConstants.GUIYUTTS + model + AiConstants.AVALIABLE, avaliableGpuList);
	}

	//从可用列表中移除指定GPU
	private void removeFromRedisAvaliableList(String model, String ip, String port) throws Exception{
		List<Object> avaliableGpuList = redisUtil.lGet(AiConstants.GUIYUTTS + model + AiConstants.AVALIABLE, 0 , -1); // 获取对应模型可用list
		int i = 0;
		for(Object gpu : avaliableGpuList){
			if(ip.equals(((GuiyuTtsGpu) gpu).getIp()) && port.equals(((GuiyuTtsGpu) gpu).getPort())){
				i = avaliableGpuList.indexOf(gpu);
			}
		}
		avaliableGpuList.remove(i);
		redisUtil.lSet(AiConstants.GUIYUTTS + model + AiConstants.AVALIABLE, avaliableGpuList);
	}

	//计算出changeCount 并根据正负值分组
	private Map<String, List<GpuCountVO>> calChangeCount(int requestSum, int availableGpuSum, List<GpuCountVO> gpuChangeList) throws Exception{
		Map<String, List<GpuCountVO>> returnMap = new HashMap<>();
		List<GpuCountVO> subGpuCountVOList = new ArrayList<>();
		List<GpuCountVO> addGpuCountVOList = new ArrayList<>();
		for (int i = 0; i < gpuChangeList.size(); i++) {
			String model = gpuChangeList.get(i).getModel();
			int requestCount = gpuChangeList.get(i).getRequestCount();
			int availableGpuCount = gpuChangeList.get(i).getAvailableGpuCount();
			// changeCount对应增减数量
			int changeCount = (requestCount * availableGpuSum / requestSum) - availableGpuCount;
			if(availableGpuCount + changeCount == 0){ //确保每个模型至少有一个GPU
				changeCount += 1;
			}
			if(changeCount < 0){
				subGpuCountVOList.add(new GpuCountVO(model, requestCount, availableGpuCount, changeCount, gpuChangeList.get(i).getGpuList()));
			}
			if(changeCount > 0){
				addGpuCountVOList.add(new GpuCountVO(model, requestCount, availableGpuCount, changeCount, gpuChangeList.get(i).getGpuList()));
			}
		}
		returnMap.put(AiConstants.SUB, subGpuCountVOList);
		returnMap.put(AiConstants.ADD, addGpuCountVOList);
		return returnMap;
	}
}
