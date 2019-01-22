package com.guiji.dispatch.batchimport;

import com.guiji.common.model.Page;
import com.guiji.dispatch.dao.FileErrorRecordsMapper;
import com.guiji.dispatch.dao.entity.FileErrorRecords;
import com.guiji.dispatch.dao.entity.FileErrorRecordsExample;
import com.guiji.dispatch.dao.entity.FileRecords;
import com.guiji.utils.StrUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class BatchImportFieRecordErrorService implements IBatchImportFieRecordErrorService {
	@Autowired
	private FileErrorRecordsMapper fileErrorRecordsMapper;

	@Override
	public boolean save(FileErrorRecords fileErrorRecords) {
		if (fileErrorRecords == null) {
			return false;
		}
		int result = fileErrorRecordsMapper.insert(fileErrorRecords);
		return result > 0 ? true : false;
	}

	@Override
	public boolean batchSave(List<FileErrorRecords> fileErrorRecords) {
		if (fileErrorRecords == null || fileErrorRecords.size() <= 0) {
			return false;
		}

		for (FileErrorRecords fileErrorRecord : fileErrorRecords) {
			fileErrorRecordsMapper.insert(fileErrorRecord);
		}

		return true;
	}

	@Override
	public boolean update(FileErrorRecords fileErrorRecords) {
		if (fileErrorRecords != null) {
			int result = fileErrorRecordsMapper.updateByPrimaryKeySelective(fileErrorRecords);
			return result > 0 ? true : false;
		} else {
			return false;
		}
	}

	@Override
	public List<FileErrorRecords> list(FileErrorRecords fileErrorRecords) {
		FileErrorRecordsExample example = this.getExampleByCondition(fileErrorRecords);
		if (example == null)
			example = new FileErrorRecordsExample();
		List<FileErrorRecords> list = fileErrorRecordsMapper.selectByExample(example);
		return list;
	}

	@Override
	public Page<FileErrorRecords> queryFileErrorRecordsPage(int pageNo, int pageSize,
			FileErrorRecords fileErrorRecords) {
		Page<FileErrorRecords> page = new Page<FileErrorRecords>();
		FileErrorRecordsExample example = this.getExampleByCondition(fileErrorRecords);
		if (example == null)
			example = new FileErrorRecordsExample();
		int totalRecord = fileErrorRecordsMapper.countByExample(example); // 总数
		example.setLimitStart((pageNo - 1) * pageSize); // 起始条数
		example.setLimitEnd(pageSize); // 结束条数
		example.setOrderByClause("create_time desc");// 按时间倒序显示
		// 分页查询
		List<FileErrorRecords> list = fileErrorRecordsMapper.selectByExample(example);
		if (list != null && !list.isEmpty()) {
			List<FileErrorRecords> rspFileErrorRecordsList = new ArrayList<FileErrorRecords>();
			for (FileErrorRecords fileErrorRecord : list) {
				rspFileErrorRecordsList.add(fileErrorRecord);
			}
			page.setRecords(rspFileErrorRecordsList);
		}
		page.setPageNo(pageNo);
		page.setPageSize(pageSize);
		page.setTotal(totalRecord);
		return page;
	}

	@Override
	public boolean delete(Long id) {
		int result = fileErrorRecordsMapper.deleteByPrimaryKey(id);
		return result > 0 ? true : false;
	}

	private FileErrorRecordsExample getExampleByCondition(FileErrorRecords fileErrorRecords) {
		if (fileErrorRecords != null) {
			Long id = fileErrorRecords.getId();
			String phone = fileErrorRecords.getPhone();
			String attach = fileErrorRecords.getAttach();
			String params = fileErrorRecords.getParams();
			Date createTime = fileErrorRecords.getCreateTime();
			Long fileRecordsId = fileErrorRecords.getFileRecordsId();
			Integer errorType = fileErrorRecords.getErrorType();

			Integer dataType = fileErrorRecords.getDataType();
			Integer batchId = fileErrorRecords.getBatchId();
			String batchName = fileErrorRecords.getBatchName();
			FileErrorRecordsExample example = new FileErrorRecordsExample();
			FileErrorRecordsExample.Criteria criteria = example.createCriteria();
			if (id != null) {
				criteria.andIdEqualTo(id);
			}
			if (StrUtils.isNotEmpty(phone)) {
				criteria.andPhoneEqualTo(phone);
			}
			if (StrUtils.isNotEmpty(attach)) {
				criteria.andAttachEqualTo(attach);
			}
			if (StrUtils.isNotEmpty(params)) {
				criteria.andParamsEqualTo(params);
			}
			if (createTime != null) {
				criteria.andCreateTimeEqualTo(createTime);
			}
			if (fileRecordsId != null) {
				criteria.andFileRecordsIdEqualTo(fileRecordsId);
			}
			if (errorType != null) {
				criteria.andErrorTypeEqualTo(errorType);
			}
			if (dataType != null) {
				criteria.andDataTypeEqualTo(dataType);
			}
			if (batchId != null) {
				criteria.andBatchIdEqualTo(batchId);
			}
			if (StrUtils.isNotEmpty(batchName)) {
				criteria.andBatchNameEqualTo(batchName);
			}

			return example;
		}
		return null;
	}
}
