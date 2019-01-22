package com.guiji.dispatch.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.guiji.common.model.Page;
import com.guiji.dispatch.dao.FileErrorRecordsMapper;
import com.guiji.dispatch.dao.FileRecordsMapper;
import com.guiji.dispatch.dao.entity.DispatchPlan;
import com.guiji.dispatch.dao.entity.FileErrorRecords;
import com.guiji.dispatch.dao.entity.FileErrorRecordsExample;
import com.guiji.dispatch.dao.entity.FileRecords;
import com.guiji.dispatch.dao.entity.FileRecordsExample;

@Service
public class FileInterfaceImpl implements FileInterface {

	@Autowired
	private FileRecordsMapper recordMapper;
	@Autowired
	private FileErrorRecordsMapper errorMapper;

	@Override
	public Page<FileRecords> queryFileInterface(int pagenum, int pagesize) {
		Page<FileRecords> page = new Page<>();
		page.setPageNo(pagenum);
		page.setPageSize((pagesize));
		FileRecordsExample example = new FileRecordsExample();
		example.setLimitStart((pagenum - 1) * pagesize);
		example.setLimitEnd(pagesize);
		example.setOrderByClause("`gmt_create` DESC");
		List<FileRecords> selectByExample = recordMapper.selectByExample(example);
		int count = recordMapper.countByExample(example);
		page.setRecords(selectByExample);
		page.setTotal(count);
		return page;
	}

	@Override
	public 	List<FileErrorRecords> queryErrorRecords(String fileRecordId) {
		FileErrorRecordsExample ex = new FileErrorRecordsExample();
		ex.createCriteria().andFileRecordsIdEqualTo(Long.valueOf(fileRecordId));
		List<FileErrorRecords> selectByExample = errorMapper.selectByExample(ex);
		return selectByExample;
	}

}
