package com.guiji.dispatch.batchimport;

import java.io.File;

import com.guiji.dispatch.dao.entity.FileRecords;

public interface IFileRecordsService {


	boolean  update(FileRecords records);

	boolean insert(Long userId, File file, String strrecords) throws Exception;

}
