package com.guiji.dispatch.service;

import java.util.List;

import com.guiji.common.model.Page;
import com.guiji.dispatch.dao.entity.FileErrorRecords;
import com.guiji.dispatch.dao.entity.FileRecords;

public interface FileInterface {
	public Page<FileRecords> queryFileInterface( int pagenum, int pagesize, String batchName, String startTime, String endTime);
	
	public 	List<FileErrorRecords> queryErrorRecords(String fileRecordId);

	boolean deleteFileRecordsById(Integer id);
}
