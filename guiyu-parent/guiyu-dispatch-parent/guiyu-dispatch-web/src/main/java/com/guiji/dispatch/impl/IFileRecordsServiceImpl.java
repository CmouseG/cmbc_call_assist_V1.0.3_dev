package com.guiji.dispatch.impl;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.guiji.common.model.SysFileReqVO;
import com.guiji.common.model.SysFileRspVO;
import com.guiji.dispatch.batchimport.IFileRecordsService;
import com.guiji.dispatch.dao.FileRecordsMapper;
import com.guiji.dispatch.dao.entity.FileRecords;
import com.guiji.dispatch.dao.entity.FileRecordsExample;
import com.guiji.utils.DateUtil;
import com.guiji.utils.JsonUtils;
import com.guiji.utils.NasUtil;

@Service
public class IFileRecordsServiceImpl implements IFileRecordsService {

	@Autowired
	private FileRecordsMapper  filerecordsMapper;
	
	@Override
	public boolean update(FileRecords records) {
		int result = filerecordsMapper.updateByExample(records, new FileRecordsExample());
		return result > 0 ? true : false;
	}


	@Override
	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public boolean insert(Long userId, File file, String strrecords) throws Exception {
		String fileName = file.getName();
		if (!fileName.matches("^.+\\.(?i)(xls)$") && !fileName.matches("^.+\\.(?i)(xlsx)$")) {
			throw new Exception("上传文件格式不正确");
		}

		FileRecords records = JsonUtils.json2Bean(strrecords, FileRecords.class);
		records.setCreateTime(DateUtil.getCurrent4Time());
		records.setUserId(userId.intValue());
		//未开始
		records.setStatus("0");
		records.setFileName(fileName);
		filerecordsMapper.insert(records);
		
		SysFileReqVO sysFileReqVO = new SysFileReqVO();
		sysFileReqVO.setBusiId(records.getId().toString());
		sysFileReqVO.setBusiType("dispatch"); // 上传的影像文件业务类型
		sysFileReqVO.setSysCode("02"); // 文件上传系统码
		sysFileReqVO.setThumbImageFlag("0"); // 是否需要生成缩略图,0-无需生成，1-生成，默认不生成缩略图
		SysFileRspVO sysFileRsp = new NasUtil().uploadNas(sysFileReqVO, file);
		
		FileRecords url = new FileRecords();
		url.setId(records.getId());
		url.setUrl(sysFileRsp.getSkUrl());
		filerecordsMapper.updateByPrimaryKeySelective(url);
		return true;
	}

}
