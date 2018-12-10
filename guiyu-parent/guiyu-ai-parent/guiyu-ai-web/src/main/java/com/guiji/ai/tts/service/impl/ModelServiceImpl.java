package com.guiji.ai.tts.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.guiji.ai.dao.TtsModelMapper;
import com.guiji.ai.dao.entity.TtsModel;
import com.guiji.ai.dao.entity.TtsModelExample;
import com.guiji.ai.dao.entity.TtsModelExample.Criteria;
import com.guiji.ai.tts.constants.AiConstants;
import com.guiji.ai.tts.service.IModelService;
import com.guiji.ai.tts.vo.ModelGpuNumVO;
import com.guiji.ai.vo.TtsGpuReqVO;
import com.guiji.ai.vo.TtsGpuRspVO;
import com.guiji.ai.vo.TtsModelGpuVO;
import com.guiji.common.model.process.ProcessInstanceVO;

@Service
public class ModelServiceImpl implements IModelService
{

	@Autowired
	TtsModelMapper modelMapper;

	@Override
	@Transactional
	public void saveModel(ProcessInstanceVO processInstance)
	{
		TtsModel ttsModel = new TtsModel();
		ttsModel.setModel(processInstance.getProcessKey());
		ttsModel.setTtsIp(processInstance.getIp());
		ttsModel.setTtsPort(String.valueOf(processInstance.getPort()));
		ttsModel.setCreateTime(new Date());
		modelMapper.insertSelective(ttsModel);
	}

	@Override
	@Transactional
	public TtsGpuRspVO getGpuList(TtsGpuReqVO ttsGpuReqVO)
	{
		TtsGpuRspVO ttsGpuRsp = new TtsGpuRspVO();
		
		List<TtsModelGpuVO> ttsGpuList = new ArrayList<>();
		List<TtsModel> modelList = new ArrayList<>();
		
		TtsModelExample example = new TtsModelExample();
		
		if(ttsGpuReqVO == null){
			modelList = modelMapper.selectByExample(example);
		}else
		{
			Criteria criteria = example.createCriteria();
			if(StringUtils.isNotEmpty(ttsGpuReqVO.getModel())){
				criteria.andModelEqualTo(ttsGpuReqVO.getModel());
			}
			if(StringUtils.isNotEmpty(ttsGpuReqVO.getIp())){
				criteria.andTtsIpEqualTo(ttsGpuReqVO.getIp());
			}
			if(StringUtils.isNotEmpty(ttsGpuReqVO.getPort())){
				criteria.andTtsPortEqualTo(ttsGpuReqVO.getPort());
			}
			if(ttsGpuReqVO.getPageNum() != null && ttsGpuReqVO.getPageSize() != null){
				example.setLimitStart((ttsGpuReqVO.getPageNum() - 1) * ttsGpuReqVO.getPageSize());
				example.setLimitEnd(ttsGpuReqVO.getPageSize());
			}
			
			modelList = modelMapper.selectByExample(example);
		}
		
		if(modelList != null){
			for(TtsModel ttsMolde : modelList)
			{
				TtsModelGpuVO gpuVO = new TtsModelGpuVO();
				gpuVO.setModel(ttsMolde.getModel());
				gpuVO.setIp(ttsMolde.getTtsIp());
				gpuVO.setPort(ttsMolde.getTtsPort());
				ttsGpuList.add(gpuVO);
			}
		}
		
		ttsGpuRsp.setTotalNum(ttsGpuList.size());
		ttsGpuRsp.setTtsGpuList(ttsGpuList);
		
		return ttsGpuRsp;
	}

	@Override
	public List<ModelGpuNumVO> getModelGpus()
	{
		//结果集 
		List<ModelGpuNumVO> modelGpuNumVOList = new ArrayList<>();
		
		List<Map<String, Object>> resultList = modelMapper.selectModelGpuCount();
		if(resultList == null || resultList.isEmpty()){
			return null;
		}
		for(Map<String, Object> map : resultList){
			ModelGpuNumVO modelGpuNumVO = new ModelGpuNumVO();
			modelGpuNumVO.setModel((String) map.get(AiConstants.MODEL));
			modelGpuNumVO.setGpuNums((int) map.get(AiConstants.COUNT));
			modelGpuNumVOList.add(modelGpuNumVO);
		}
		return null;
	}

}
