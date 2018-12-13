package com.guiji.ai.tts.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.guiji.ai.dao.TtsStatusMapper;
import com.guiji.ai.dao.entity.TtsStatus;
import com.guiji.ai.dao.entity.TtsStatusExample;
import com.guiji.ai.dao.entity.TtsStatusExample.Criteria;
import com.guiji.ai.tts.constants.AiConstants;
import com.guiji.ai.tts.constants.GuiyuAIExceptionEnum;
import com.guiji.ai.tts.handler.SaveTtsStatusHandler;
import com.guiji.ai.tts.service.IStatusService;
import com.guiji.ai.vo.TaskListReqVO;
import com.guiji.ai.vo.TaskListRspVO;
import com.guiji.ai.vo.TaskNumVO;
import com.guiji.ai.vo.TaskReqVO;
import com.guiji.ai.vo.TaskRspVO;
import com.guiji.ai.vo.TtsReqVO;
import com.guiji.ai.vo.TtsStatusReqVO;
import com.guiji.ai.vo.TtsStatusRspVO;
import com.guiji.common.exception.GuiyuException;

@Service
public class StatusServiceImpl implements IStatusService
{
	@Autowired
	TtsStatusMapper ttsStatusMapper;

	@Override
	public String getTransferStatusByBusId(String busId)
	{
		String status = "";
		try
		{
			status = ttsStatusMapper.getReqStatusByBusId(busId);
		} catch (Exception e)
		{
			throw new GuiyuException(GuiyuAIExceptionEnum.EXCP_AI_CHANGE_TTS.getErrorCode(), e);
		}
		return status;

	}

	
	@Override
	public List<TtsStatusRspVO> getTtsStatusList(TtsStatusReqVO ttsStatusReqVO)
	{
		//结果集
		List<TtsStatusRspVO> ttsStatusRspList = new ArrayList<>();
		List<TtsStatus> ttsStatusList = new ArrayList<>();
		
		TtsStatusExample example = new TtsStatusExample();
		if(ttsStatusReqVO == null)
		{
			ttsStatusList = ttsStatusMapper.selectByExample(example);
		}else
		{
			Criteria criteria = example.createCriteria();
			if(StringUtils.isNotEmpty(ttsStatusReqVO.getModel())){
				criteria.andModelEqualTo(ttsStatusReqVO.getModel());
			}
			if(StringUtils.isNotEmpty(ttsStatusReqVO.getStatus())){
				criteria.andStatusEqualTo(ttsStatusReqVO.getStatus());
			}
			if(ttsStatusReqVO.getStartTime() != null){
				criteria.andCreateTimeGreaterThanOrEqualTo(ttsStatusReqVO.getStartTime());
			}
			if(ttsStatusReqVO.getEndTime() != null){
				criteria.andCreateTimeLessThanOrEqualTo(ttsStatusReqVO.getEndTime());
			}
			ttsStatusList = ttsStatusMapper.selectByExample(example);
		}
		
		for(TtsStatus ttsStatus : ttsStatusList)
		{
			TtsStatusRspVO ttsStatusRsp = new TtsStatusRspVO();
			ttsStatusRsp.setBusId(ttsStatus.getBusId());
			ttsStatusRsp.setModel(ttsStatus.getModel());
			ttsStatusRsp.setCount(ttsStatus.getTextCount());
			ttsStatusRsp.setCreateTime(ttsStatus.getCreateTime());
			ttsStatusRspList.add(ttsStatusRsp);
		}
		
		return ttsStatusRspList;
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
	public void updateStatusByBusId(String busId, String status)
	{
		ttsStatusMapper.updateStatusByBusId(busId, status);
		
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
		if (StringUtils.isNotEmpty(taskListReqVO.getStatus())){
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
		ttsStatusMapper.updateJumpFlagByBusId(busId);
	}


	/**
	 * 累计任务
	 * 根据returnFlag区分返回结果
	 */
	@Override
	public TaskRspVO getTasks(TaskReqVO taskReqVO)
	{
		TaskRspVO taskRspVO = new TaskRspVO();
		List<TaskNumVO> taskNumVOList = new ArrayList<>();
		
		String dimension = taskReqVO.getDimension(); //维度
		List<Map<String, Object>> mapList = new ArrayList<>();
		if("0".equals(taskReqVO.getReturnFlag())) //累计接受任务
		{
			if("days".equals(dimension)) //按天统计
			{
				mapList = ttsStatusMapper.getAcceptTasksByDays(taskReqVO.getStartTime(), taskReqVO.getEndTime());
			}
			else if("months".equals(dimension)) //按月统计
			{
				mapList = ttsStatusMapper.getAcceptTasksByMonths(taskReqVO.getStartTime(), taskReqVO.getEndTime());
			}
		}
		else if("1".equals(taskReqVO.getReturnFlag())) //累计完成任务
		{
			if("days".equals(dimension)) //按天统计
			{
				mapList = ttsStatusMapper.getCompleteTasksByDays(taskReqVO.getStartTime(), taskReqVO.getEndTime());
			}
			else if("months".equals(dimension)) //按月统计
			{
				mapList = ttsStatusMapper.getCompleteTasksByMonths(taskReqVO.getStartTime(), taskReqVO.getEndTime());
			}
		}
		
		//处理返回结果
		taskNumVOList = setTaskVO(mapList, dimension);
		
		taskRspVO.setTotalNum(taskNumVOList.size());
		taskRspVO.setTaskNumsList(taskNumVOList);
		return taskRspVO;
	}

	//处理返回结果
	private List<TaskNumVO> setTaskVO(List<Map<String, Object>> mapList, String dimension)
	{
		List<TaskNumVO> taskNumVOList = new ArrayList<>();
		TaskNumVO taskNumVO = null;
		
		for(Map<String, Object> map : mapList){
			taskNumVO = new TaskNumVO();
			taskNumVO.setCount((long) map.get("countNum"));
			taskNumVO.setDate((String) map.get("date"));
			taskNumVOList.add(taskNumVO);
		}
		return taskNumVOList;		
	}


	@Override
	public TaskRspVO getWaitTasks()
	{
		TaskRspVO taskRspVO = new TaskRspVO();
		List<TaskNumVO> taskNumVOList = new ArrayList<>();
		List<Map<String, Object>> mapList = new ArrayList<>();
		
		mapList = ttsStatusMapper.getWaitTasks();
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

}
