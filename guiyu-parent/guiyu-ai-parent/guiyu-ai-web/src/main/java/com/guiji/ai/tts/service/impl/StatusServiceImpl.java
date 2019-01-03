package com.guiji.ai.tts.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.guiji.ai.dao.TtsStatusMapper;
import com.guiji.ai.dao.TtsStatusMapperExt;
import com.guiji.ai.dao.entity.TtsStatus;
import com.guiji.ai.dao.entity.TtsStatusExample;
import com.guiji.ai.dao.entity.TtsStatusExample.Criteria;
import com.guiji.ai.tts.constants.AiConstants;
import com.guiji.ai.tts.constants.GuiyuAIExceptionEnum;
import com.guiji.ai.tts.handler.SaveTtsStatusHandler;
import com.guiji.ai.tts.service.IStatusService;
import com.guiji.ai.vo.RatioReqVO;
import com.guiji.ai.vo.RatioRspVO;
import com.guiji.ai.vo.TaskLineNumVO;
import com.guiji.ai.vo.TaskLineRspVO;
import com.guiji.ai.vo.TaskListReqVO;
import com.guiji.ai.vo.TaskListRspVO;
import com.guiji.ai.vo.TaskNumVO;
import com.guiji.ai.vo.TaskReqVO;
import com.guiji.ai.vo.TaskRspVO;
import com.guiji.ai.vo.TtsReqVO;
import com.guiji.common.exception.GuiyuException;
import com.guiji.utils.DateUtil;

@Service
public class StatusServiceImpl implements IStatusService
{
	@Autowired
	TtsStatusMapper ttsStatusMapper;
	@Autowired
	TtsStatusMapperExt ttsStatusMapperExt;

	@Override
	public Integer getTransferStatusByBusId(String busId)
	{
		Integer status = null;
		try
		{
			status = ttsStatusMapperExt.getReqStatusByBusId(busId);
		} catch (Exception e)
		{
			throw new GuiyuException(GuiyuAIExceptionEnum.EXCP_AI_CHANGE_TTS.getErrorCode(), e);
		}
		return status;

	}

	/**
	 * ttsReqVO入库
	 */
	@Override
	public void saveTtsStatus(TtsReqVO ttsReqVO)
	{
		TtsStatus ttsStatus = new TtsStatus();
		ttsStatus.setBusId(ttsReqVO.getBusId());
		ttsStatus.setModel(ttsReqVO.getModel());
		ttsStatus.setStatus(AiConstants.UNTREATED); //未处理
		ttsStatus.setCreateTime(new Date());
		ttsStatus.setTextCount(ttsReqVO.getContents().size());
		SaveTtsStatusHandler.getInstance().add(ttsStatus, ttsStatusMapper);

	}

	@Override
	@Transactional
	public void updateStatusByBusId(String busId, int status)
	{
		ttsStatusMapperExt.updateStatusByBusId(busId, status);

	}

	@Override
	public TaskListRspVO getTaskList(TaskListReqVO taskListReqVO)
	{
		// 返回结果对象
		TaskListRspVO taskListRspVO = new TaskListRspVO();
		// 数据库查询结果集
		List<TtsStatus> ttsStatusList = new ArrayList<>();
		// 条件查询
		TtsStatusExample example = new TtsStatusExample();
		Criteria criteria = example.createCriteria();
		if (StringUtils.isNotEmpty(taskListReqVO.getBusId())){
			criteria.andBusIdEqualTo(taskListReqVO.getBusId());
		}
		if (StringUtils.isNotEmpty(taskListReqVO.getModel())){
			criteria.andModelEqualTo(taskListReqVO.getModel());
		}
		if (taskListReqVO.getStatus() != null){
			criteria.andStatusEqualTo(taskListReqVO.getStatus());
		}
		if (taskListReqVO.getStartTime() != null){
			criteria.andCreateTimeGreaterThanOrEqualTo(taskListReqVO.getStartTime());
		}
		if (taskListReqVO.getEndTime() != null){
			criteria.andCreateTimeLessThanOrEqualTo(taskListReqVO.getEndTime());
		}
		ttsStatusList = ttsStatusMapper.selectByExample(example);
		taskListRspVO.setTotalNum(ttsStatusList.size()); // 不分页总条数

		example.setLimitStart((taskListReqVO.getPageNum() - 1) * taskListReqVO.getPageSize());
		example.setLimitEnd(taskListReqVO.getPageSize());
		ttsStatusList = ttsStatusMapper.selectByExample(example);

		taskListRspVO.setTtsStatusList(ttsStatusList);
		return taskListRspVO;

	}


	@Override
	@Transactional
	public void updateJumpFlagByBusId(String busId)
	{
		ttsStatusMapperExt.updateJumpFlagByBusId(busId);
	}

	//处理返回结果
	private List<TaskLineNumVO> setTaskLineVO(List<Map<String, Object>> mapList, String dimension)
	{
		List<TaskLineNumVO> taskNumVOList = new ArrayList<>();
		TaskLineNumVO taskLineNumVO = null;

		for(Map<String, Object> map : mapList){
			taskLineNumVO = new TaskLineNumVO();
			taskLineNumVO.setCount((long) map.get("countNum"));
			taskLineNumVO.setCompleteCount(((BigDecimal)map.get("countCompleteNum")).longValue());
			taskLineNumVO.setDate((String) map.get("date"));
			taskNumVOList.add(taskLineNumVO);
		}
		return taskNumVOList;
	}


	@Override
	public TaskRspVO getWaitTasks()
	{
		TaskRspVO taskRspVO = new TaskRspVO();
		List<TaskNumVO> taskNumVOList = new ArrayList<>();
		List<Map<String, Object>> mapList = new ArrayList<>();

		mapList = ttsStatusMapperExt.getWaitTasks();
		for(Map<String, Object> map : mapList){
			TaskNumVO taskNumVO = new TaskNumVO();
			taskNumVO.setModel((String) map.get("model"));
			taskNumVO.setCount((Long) map.get("countNum"));
			taskNumVOList.add(taskNumVO);
		}

		taskRspVO.setTotalNum(taskNumVOList.size());
		taskRspVO.setTaskNumsList(taskNumVOList);
		return taskRspVO;
	}


	@Override
	public RatioRspVO getRatio(RatioReqVO ratioReqVO)
	{
		RatioRspVO ratioRspVO = new RatioRspVO();
		// 条件查询
		TtsStatusExample example1 = new TtsStatusExample();
		TtsStatusExample example2 = new TtsStatusExample();
		Criteria criteria1 = example1.createCriteria();
		Criteria criteria2 = example2.createCriteria();
		Date startTime = null;
		Date endTime = null;
		if (StringUtils.isNotEmpty(ratioReqVO.getStartTime())){
			if (ratioReqVO.getStartTime().length() > 8) {
				startTime = DateUtil.stringToDatePattern(ratioReqVO.getStartTime(),"yyyy-MM-dd");
			} else {
				startTime = DateUtil.stringToDatePattern(ratioReqVO.getStartTime(),"yyyy-MM");
			}
			criteria1.andCreateTimeGreaterThanOrEqualTo(startTime);
			criteria2.andCreateTimeGreaterThanOrEqualTo(startTime);
		}
		if (StringUtils.isNotEmpty(ratioReqVO.getEndTime())){
			if (ratioReqVO.getEndTime().length() > 8) {
				endTime = DateUtil.stringToDatePattern(ratioReqVO.getEndTime(),"yyyy-MM-dd");
			} else {
				endTime = DateUtil.stringToDatePattern(ratioReqVO.getEndTime(),"yyyy-MM");
			}
			criteria1.andCreateTimeLessThanOrEqualTo(endTime);
			criteria2.andCreateTimeLessThanOrEqualTo(endTime);
		}

		criteria1.andStatusEqualTo(2); //2已完成
		int successCount = ttsStatusMapper.selectByExample(example1).size();
		criteria2.andStatusEqualTo(3); //3处理失败
		int failCount = ttsStatusMapper.selectByExample(example2).size();

		int successRatio = (int) Math.round(100.0 * successCount / (successCount + failCount));
		int failRatio = (int) Math.round(100.0 * failCount / (successCount + failCount));

		ratioRspVO.setSuccessRatio(String.valueOf(successRatio)+ "%");
		ratioRspVO.setFailRatio(String.valueOf(failRatio)+ "%");
		return ratioRspVO;
	}

	/**
	 * 累计任务
	 * 根据returnFlag区分返回结果
	 */
	@Override
	public TaskLineRspVO getTaskLine(TaskReqVO taskReqVO)
	{
		TaskLineRspVO taskLineRspVO = new TaskLineRspVO();
		List<TaskLineNumVO> taskNumVOTotalList = new ArrayList<>();

		String dimension = taskReqVO.getDimension(); //维度
		List<Map<String, Object>> mapList = new ArrayList<>();

		if("days".equals(dimension)) //按天统计
		{
			mapList = ttsStatusMapperExt.getTasksByDays(DateUtil.stringToDatePattern(taskReqVO.getStartTime(),"yyyy-MM-dd"), DateUtil.stringToDatePattern(taskReqVO.getEndTime(),"yyyy-MM-dd"));
		}
		else if("months".equals(dimension)) //按月统计
		{
			mapList = ttsStatusMapperExt.getTasksByMonths(DateUtil.stringToDatePattern(taskReqVO.getStartTime(),"yyyy-MM"), DateUtil.stringToDatePattern(taskReqVO.getEndTime(),"yyyy-MM"));
		}
		//处理返回结果
		taskNumVOTotalList = setTaskLineVO(mapList, dimension);

		taskLineRspVO.setTotalNum(taskNumVOTotalList.size());
		taskLineRspVO.setTaskLineNumsList(taskNumVOTotalList);
		return taskLineRspVO;
	}

}
