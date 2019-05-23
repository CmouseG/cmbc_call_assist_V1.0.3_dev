package com.guiji.dispatch.service;

import java.util.List;

import com.guiji.common.model.Page;
import com.guiji.dispatch.dao.entity.FileErrorRecords;
import com.guiji.dispatch.dao.entity.FileRecords;
import com.guiji.dispatch.vo.FileRecordsListVo;

public interface FileInterface {

	//查询文件记录
	public Page<FileRecordsListVo> queryFileInterface(int pagenum, int pagesize,
													  String batchName, String startTime, String endTime,
													  String userId, String orgCode, Integer authLevel);

	//查询导入错误记录
	public 	List<FileErrorRecords> queryErrorRecords(String fileRecordId);

	boolean deleteFileRecordsById(Integer id);

	//查询文件导入记录
	FileRecords queryFileRecordById(Long id);

}
