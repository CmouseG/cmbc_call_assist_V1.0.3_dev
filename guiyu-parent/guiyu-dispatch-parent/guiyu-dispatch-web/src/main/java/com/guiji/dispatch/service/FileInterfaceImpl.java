package com.guiji.dispatch.service;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.guiji.dispatch.dto.QueryFileRecordsDto;
import com.guiji.dispatch.enums.AuthLevelEnum;
import com.guiji.dispatch.sys.ResultPage;
import com.guiji.dispatch.util.DateTimeUtils;
import com.guiji.dispatch.vo.FileRecordsListVo;
import org.apache.commons.lang3.StringUtils;
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

	@Autowired
	private GetAuthUtil getAuthUtil;

	/**
	 * 查询文件记录
	 * @param pagenum
	 * @param pagesize
	 * @param batchName
	 * @param startTime
	 * @param endTime
	 * @param orgCode
	 * @return
	 */
	@Override
	public Page<FileRecordsListVo> queryFileInterface(int pagenum, int pagesize,
												String batchName, String startTime, String endTime,
												String userId, String orgCode, Integer authLevel) {
		Page<FileRecordsListVo> page = new Page<>();
		page.setPageNo(pagenum);
		page.setPageSize((pagesize));
		/*
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
		*/
		QueryFileRecordsDto queryRecord = new QueryFileRecordsDto();
		queryRecord.setStatus(Constant.FILE_SHOW);
		queryRecord.setBatchName(batchName);

		Date beginDate = null, endDate = null;
		if (!StringUtils.isEmpty(startTime) && !StringUtils.isEmpty(endTime)) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			try {
				beginDate = DateTimeUtils.getDateByString(startTime, DateTimeUtils.DEFAULT_DATE_FORMAT_PATTERN_FULL);
				endDate = DateTimeUtils.getDateByString(endTime, DateTimeUtils.DEFAULT_DATE_FORMAT_PATTERN_FULL);
			} catch (Exception e) {
				logger.error("queryFileInterface 转换失败");
			}
		}

		//过滤权限
		userId = getAuthUtil.getUserIdByAuthLevel(authLevel, userId);//获取用户ID
		orgCode = getAuthUtil.getOrgCodeByAuthLevel(authLevel, orgCode);//获取企业组织编码
		queryRecord.setUserId(null != userId?Integer.valueOf(userId):null);
		queryRecord.setOrgCode(orgCode);
		queryRecord.setAuthLevel(authLevel);

		ResultPage<FileRecords> pageRes = new ResultPage<FileRecords>(pagenum, pagesize);
		pageRes.setOrderBy("create_time");
		pageRes.setSort("DESC");
		List<FileRecordsListVo> list = recordMapper.queryFileRecordListPage(queryRecord,
				beginDate, endDate, pageRes);
		int count = recordMapper.queryFileRecordCount(queryRecord, beginDate, endDate);

		page.setRecords(list);
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

	@Override
	public FileRecords queryFileRecordById(Long id) {
		return recordMapper.queryFileRecordById(id);
	}
}