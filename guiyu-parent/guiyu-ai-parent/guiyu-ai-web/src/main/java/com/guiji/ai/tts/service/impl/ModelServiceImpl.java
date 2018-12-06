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
import com.guiji.ai.tts.service.IModelService;
import com.guiji.ai.vo.TtsGpuReqVO;
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
	public List<TtsGpuVO> getAllGpuByPage(TtsGpuReqVO ttsGpuReqVO)
	{
		List<TtsGpuVO> TtsGpuList = new ArrayList<>();
		
		int pageSize = ttsGpuReqVO.getPageSize();
		int pageNum = ttsGpuReqVO.getPageNum();
		
		TtsModelExample ttsModelExample = new TtsModelExample();
		ttsModelExample.setLimitStart((pageNum - 1) * pageSize);
		ttsModelExample.setLimitEnd(pageSize);
		
		List<TtsModel> modelList = modelMapper.selectByExample(ttsModelExample);
		
		for(TtsModel ttsMolde : modelList)
		{
			TtsGpuVO gpuVO = new TtsGpuVO();
			gpuVO.setModel(ttsMolde.getModel());
			gpuVO.setIp(ttsMolde.getTtsIp());
			gpuVO.setPort(ttsMolde.getTtsPort());
			TtsGpuList.add(gpuVO);
		}
		return TtsGpuList;
	}

}
