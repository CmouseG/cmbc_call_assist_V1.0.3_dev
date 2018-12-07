package com.guiji.ai.tts.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.guiji.ai.dao.TtsModelMapper;
import com.guiji.ai.dao.entity.TtsModel;
import com.guiji.ai.dao.entity.TtsModelExample;
import com.guiji.ai.dao.entity.TtsModelExample.Criteria;
import com.guiji.ai.tts.service.IModelService;
import com.guiji.ai.vo.TtsGpuReqVO;
import com.guiji.ai.vo.TtsGpuRspVO;
import com.guiji.ai.vo.TtsGpuVO;
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
		modelMapper.insert(ttsModel);
	}

	@Override
	@Transactional
	public TtsGpuRspVO getGpuList(TtsGpuReqVO ttsGpuReqVO)
	{
		TtsGpuRspVO ttsGpuRsp = new TtsGpuRspVO();
		
		List<TtsGpuVO> ttsGpuList = new ArrayList<>();
		List<TtsModel> modelList = new ArrayList<>();
		
		TtsModelExample example = new TtsModelExample();
		
		if(ttsGpuReqVO == null){
			modelList = modelMapper.selectByExample(example);
		}else
		{
			Criteria criteria = example.createCriteria();
			if(ttsGpuReqVO.getModel() != null){
				criteria.andModelEqualTo(ttsGpuReqVO.getModel());
			}
			if(ttsGpuReqVO.getIp() != null){
				criteria.andTtsIpEqualTo(ttsGpuReqVO.getIp());
			}
			if(ttsGpuReqVO.getPort() != null){
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
				TtsGpuVO gpuVO = new TtsGpuVO();
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

}
