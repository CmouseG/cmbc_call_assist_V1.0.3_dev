package com.guiji.dispatch.service;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.guiji.common.model.Page;
import com.guiji.dispatch.dao.FileErrorRecordsMapper;
import com.guiji.dispatch.dao.FileRecordsMapper;
import com.guiji.dispatch.dao.entity.FileErrorRecords;
import com.guiji.dispatch.dao.entity.FileErrorRecordsExample;
import com.guiji.dispatch.dao.entity.FileRecords;
import com.guiji.dispatch.dao.entity.FileRecordsExample;
import com.guiji.dispatch.dao.entity.FileRecordsExample.Criteria;
import com.guiji.dispatch.util.Constant;

@Service
public class FileInterfaceImpl implements FileInterface {
	static Logger logger = LoggerFactory.getLogger(FileInterfaceImpl.class);

	@Autowired
	private FileRecordsMapper recordMapper;
	@Autowired
	private FileErrorRecordsMapper errorMapper;

	@Override
	public Page<FileRecords> queryFileInterface(int pagenum, int pagesize, String batchName, String startTime,
			String endTime) {
		Page<FileRecords> page = new Page<>();
		page.setPageNo(pagenum);
		page.setPageSize((pagesize));
		FileRecordsExample example = new FileRecordsExample();
		example.setLimitStart((pagenum - 1) * pagesize);
		example.setLimitEnd(pagesize);
		example.setOrderByClause("`create_time` DESC");

		Criteria andStatusEqualTo = example.createCriteria().andStatusEqualTo(Constant.FILE_SHOW);
		if (batchName != null && batchName != "") {
			andStatusEqualTo.andBatchNameEqualTo(batchName);
		}
		if (startTime != null && startTime != "" && endTime != null && endTime != "") {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			try {
				andStatusEqualTo.andCreateTimeBetween(new Timestamp(sdf.parse(startTime).getTime()),
						new Timestamp(sdf.parse(endTime).getTime()));
			} catch (ParseException e) {
				logger.error("queryFileInterface 转换失败");
			}
		}
		List<FileRecords> selectByExample = recordMapper.selectByExample(example);
		int count = recordMapper.countByExample(example);
		page.setRecords(selectByExample);
		page.setTotal(count);
		return page;
	}

	@Override
	public List<FileErrorRecords> queryErrorRecords(String fileRecordId) {
		FileErrorRecordsExample ex = new FileErrorRecordsExample();
		ex.createCriteria().andFileRecordsIdEqualTo(Long.valueOf(fileRecordId));
		List<FileErrorRecords> selectByExample = errorMapper.selectByExample(ex);
		return selectByExample;
	}

	@Override
	public boolean deleteFileRecordsById(Integer id) {
		FileRecords record = new FileRecords();
		record.setId(Long.valueOf(id));
		record.setStatus(Constant.FILE_NO);
		int result = recordMapper.updateByPrimaryKeySelective(record);
		return result > 0 ? true : false;
	}

}
