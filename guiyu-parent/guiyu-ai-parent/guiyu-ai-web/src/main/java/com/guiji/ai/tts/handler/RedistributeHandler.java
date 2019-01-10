package com.guiji.ai.tts.handler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.guiji.ai.tts.constants.AiConstants;
import com.guiji.ai.tts.constants.GuiyuAIExceptionEnum;
import com.guiji.ai.tts.service.IModelService;
import com.guiji.ai.tts.service.IResultService;
import com.guiji.ai.tts.service.impl.func.TtsGpu;
import com.guiji.ai.tts.vo.ModelChangeNumVO;
import com.guiji.ai.tts.vo.ModelGpuNumVO;
import com.guiji.ai.tts.vo.ModelRecoverGpuVO;
import com.guiji.ai.tts.vo.ModelRequestNumVO;
import com.guiji.ai.vo.TtsModelGpuVO;
import com.guiji.common.exception.GuiyuException;
import com.guiji.component.lock.DistributedLockHandler;
import com.guiji.component.lock.Lock;
import com.guiji.component.result.Result.ReturnData;
import com.guiji.process.api.IProcessSchedule;
import com.guiji.process.model.ChangeModelReq;
import com.guiji.utils.RedisUtil;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHandler;
import com.xxl.job.core.log.XxlJobLogger;

@JobHandler(value="redistributeTaskHandler")
@Component
public class RedistributeHandler extends IJobHandler
{

	@Autowired
	RedisUtil redisUtil;
	@Autowired
	IModelService modelService;
	@Autowired
	IResultService resultService;
	@Autowired
	IProcessSchedule iProcessSchedule;
	@Autowired
	DistributedLockHandler distributedLockHandler;

	/**
	 * 定时任务-重新分配GPU
	 */
	@Override
	public ReturnT<String> execute(String param) throws Exception
	{
		Lock lock = new Lock("LOCK_NAME", "LOCK_VALUE");
		if (distributedLockHandler.tryLock(lock, 5*1000L, 100L, 3*60*1000L)) // 尝试5s,每100ms尝试一次，持锁时间为3分钟
		{
			try
			{
				XxlJobLogger.log("查询前10分钟内各模型请求情况...");
				List<ModelRequestNumVO> resultList = resultService.selectTenMinutesBefore(new Date()); // <A,3>，<B，5>
				if (resultList == null || resultList.isEmpty())
				{
					XxlJobLogger.log("前10分钟内没有请求。");
					return SUCCESS;
				}
				// 重新分配
				distributionStrategy(resultList);

			} 
			catch (Exception e)
			{
				XxlJobLogger.log("分配失败！", e);
				return FAIL;
			}
			finally 
			{
				distributedLockHandler.releaseLock(lock); // 释放锁
			}
		} 
		else
		{
			XxlJobLogger.log("未能获取锁！！！");
			return FAIL;
		}
		return SUCCESS;
	}
	
	// 分配策略
	private void distributionStrategy(List<ModelRequestNumVO> modelRequestList)
	{
		// 计算出各模型需要切换的数量
		List<ModelChangeNumVO> modelChangeList = calChangeNum(modelRequestList);

		List<ModelChangeNumVO> subModelChangeList = new ArrayList<>();
		List<ModelChangeNumVO> addModelChangeList = new ArrayList<>();

		// 根据changeNum分组
		for (ModelChangeNumVO modelChangeVO : modelChangeList)
		{
			int changeNum = modelChangeVO.getChangeNum();

			if (changeNum < 0) // 回收组
			{
				subModelChangeList.add(modelChangeVO);
			}
			if (changeNum > 0) // 分配组
			{
				addModelChangeList.add(modelChangeVO);
			}
		}
		// 回收
		List<ModelRecoverGpuVO> recoverGpuVOList = recoverGpu(subModelChangeList);
		// 分配
		distributeGpu(addModelChangeList, recoverGpuVOList);
	}
	
	
	/*
	 * 分配
	 */
	private void distributeGpu(List<ModelChangeNumVO> addModelChangeList, List<ModelRecoverGpuVO> recoverGpuVOList)
	{
		/*
		 * recoverGpuVOList是按模型分类存放回收的GPU 因为分配时数量不一定对等，所以，对回收的GPU进行汇总
		 * 用一个总的list存放，不需要按模型分类存放，总list记录(model,ip,port)
		 * 这样在分配的时候就不需要关心各个模型的list进行是否为空，直接对总list判断
		 */
		List<TtsModelGpuVO> ttsModelGpuList = new ArrayList<>(); // 总回收的GUP列表

		for (ModelRecoverGpuVO modelRecoverGpuVO : recoverGpuVOList)
		{
			String model = modelRecoverGpuVO.getModel();
			List<TtsGpu> gpuList = modelRecoverGpuVO.getRecoverGpuList();
			for (TtsGpu ttsGpu : gpuList)
			{
				TtsModelGpuVO ttsModelGpuVO = new TtsModelGpuVO();
				ttsModelGpuVO.setModel(model);
				ttsModelGpuVO.setIp(ttsGpu.getIp());
				ttsModelGpuVO.setPort(ttsGpu.getPort());
				ttsModelGpuList.add(ttsModelGpuVO);
			}
		}

		// 对需要分配GPU的模型进行按需求量大小排序，优先分配
		Collections.sort(addModelChangeList, new Comparator<ModelChangeNumVO>()
		{
			@Override
			public int compare(ModelChangeNumVO o1, ModelChangeNumVO o2)
			{
				return o2.getChangeNum() - o1.getChangeNum();
			}
		});

		// 循环每一个需要分配的模型
		for (int i = 0; i < addModelChangeList.size(); i++)
		{
			String model = addModelChangeList.get(i).getModel();
			int changeCount = addModelChangeList.get(i).getChangeNum();
			// 循环每个模型对应需要分配的数量
			for (int j = 0; j < changeCount; j++)
			{
				// 比率小数点可能会造成的回收数量和分配数量不相等，故需要对回收列表进行非空判断
				if (ttsModelGpuList.isEmpty())
				{
					XxlJobLogger.log("GPU分配完了，没有可分配的GPU了!");
					return;
				}

				ChangeModelReq changeModelReq = new ChangeModelReq();
				String fromModel = ttsModelGpuList.get(0).getModel();
				String ip = ttsModelGpuList.get(0).getIp();
				String port = ttsModelGpuList.get(0).getPort();
				changeModelReq.setFromModel(fromModel);
				changeModelReq.setToModel(model);
				changeModelReq.setIp(ip);
				changeModelReq.setPort(Integer.parseInt(port));
				XxlJobLogger.log("调进程管理接口-模型切换...");
				ReturnData<Boolean> returnData = iProcessSchedule.changeTTS(changeModelReq);
				if (returnData == null || !returnData.getBody())
				{
					throw new GuiyuException(GuiyuAIExceptionEnum.EXCP_AI_CHANGE_TTS);
				}
				redisUtil.lSet(AiConstants.GUIYUTTS + fromModel + AiConstants.CHANGING, new TtsGpu(ip, port)); // 更改中
			}
		}
		XxlJobLogger.log("GPU重新分配完成!");
	}
	
	/*
	 * 回收
	 */
	private List<ModelRecoverGpuVO> recoverGpu(List<ModelChangeNumVO> subModelChangeList)
	{
		// 结果集
		List<ModelRecoverGpuVO> recoverGpuVOList = new ArrayList<>();

		// 循环每一个需要回收的模型
		for (ModelChangeNumVO modelChangeVO : subModelChangeList)
		{
			String model = modelChangeVO.getModel();
			int changeNum = Math.abs(modelChangeVO.getChangeNum()); // 负数取绝对值（该模型需要回收的数量）

			// 存放回收的GPU
			List<TtsGpu> recoverGpuList = new ArrayList<>();

			// 获取该模型所有空闲GPU
			List<Object> avaliableGpuList = redisUtil.lGet(AiConstants.GUIYUTTS + model + AiConstants.AVALIABLE, 0, -1);
			// 如果没取到 或者 取到空闲GPU的数量小于changeNum
			if (avaliableGpuList == null || avaliableGpuList.size() < changeNum)
			{
				// 循环取，取到changeNum个为止
				do
				{
					Object obj = redisUtil.lGetIndex(AiConstants.GUIYUTTS + model + AiConstants.AVALIABLE, 0);
					if (obj != null)
					{
						recoverGpuList.add((TtsGpu) obj);
						redisUtil.lRemove(AiConstants.GUIYUTTS + model + AiConstants.AVALIABLE, 1, obj);
					}
				} while (recoverGpuList.size() == changeNum);
			} 
			else // avaliableGpuList.size() >= changeNum （空闲的GPU数量大于等于changeNum）
			{
				// 直接从空闲中取changeNum个
				for (int i = 0; i < changeNum; i++)
				{
					Object obj = avaliableGpuList.get(i);
					recoverGpuList.add((TtsGpu) obj);
					redisUtil.lRemove(AiConstants.GUIYUTTS + model + AiConstants.AVALIABLE, 1, obj);
				}
			}

			ModelRecoverGpuVO recoverGpuVO = new ModelRecoverGpuVO(model, recoverGpuList);
			recoverGpuVOList.add(recoverGpuVO);
		}

		return recoverGpuVOList;
	}
	
	/*
	 * 计算出各模型需要切换的数量
	 */
	private List<ModelChangeNumVO> calChangeNum(List<ModelRequestNumVO> modelRequestList)
	{
		// 请求次数总和
		int RequestSum = 0;
		for (ModelRequestNumVO modelRequest : modelRequestList)
		{
			RequestSum += modelRequest.getRequestNum();
		}

		// 比率map <A, 0.5>, <B, 0.3>
		Map<String, Float> probabilityMap = new HashMap<>();
		for (ModelRequestNumVO modelRequest : modelRequestList)
		{
			float probability = modelRequest.getRequestNum() / RequestSum;
			probabilityMap.put(modelRequest.getModel(), probability);
		}

		/*
		 * 获取所有模型及gpu数量 （若某模型在时间段内没有请求，modelRequestList中则没有该模型，所以需要去数据中中查询所有模型）
		 */
		List<ModelGpuNumVO> modelGpusList = modelService.getModelGpus();

		// GPU总数量
		int gpuSum = 0;
		for (ModelGpuNumVO modelGpu : modelGpusList)
		{
			gpuSum += modelGpu.getGpuNums();
		}

		// 计算出各模型的changeNum，存入modelChangeList
		List<ModelChangeNumVO> modelChangeList = new ArrayList<>();
		for (ModelGpuNumVO modelGpu : modelGpusList)
		{
			String model = modelGpu.getModel(); // 模型名称
			int gpuNum = modelGpu.getGpuNums(); // gpu数量
			int requireNum = 0; // 应分配数量
			if (probabilityMap.containsKey(model)) // 如果比率map中含有此模型，则计算出它的应分配数量
			{
				requireNum = (int) (probabilityMap.get(model) * gpuSum); // 小数点问题，有待继续优化
			} else // 否则比率map中不含此模型，则它需要的gpu数量为1（至少保证1个）
			{
				requireNum = 1;
			}
			// 改变数量
			int changeNum = requireNum - gpuNum;

			ModelChangeNumVO modelChangeVO = new ModelChangeNumVO(model, changeNum);
			modelChangeList.add(modelChangeVO);
		}

		return modelChangeList;
	}

}
