package com.guiji.dispatch.impl;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.guiji.auth.api.IAuth;
import com.guiji.common.model.Page;
import com.guiji.component.result.Result.ReturnData;
import com.guiji.dispatch.blacklistmq.BlackListImportQueueHandler;
import com.guiji.dispatch.dao.BlackListMapper;
import com.guiji.dispatch.dao.BlackListRecordsMapper;
import com.guiji.dispatch.dao.DispatchPlanMapper;
import com.guiji.dispatch.dao.entity.BlackList;
import com.guiji.dispatch.dao.entity.BlackListExample;
import com.guiji.dispatch.dao.entity.BlackListExample.Criteria;
import com.guiji.dispatch.dao.entity.BlackListRecords;
import com.guiji.dispatch.dao.entity.BlackListRecordsExample;
import com.guiji.dispatch.dao.entity.DispatchPlan;
import com.guiji.dispatch.service.IBlackListService;
import com.guiji.dispatch.util.Constant;
import com.guiji.user.dao.entity.SysUser;
import com.guiji.utils.DateUtil;

@Service
public class BlackListServiceImpl implements IBlackListService {
	static Logger logger = LoggerFactory.getLogger(BlackListServiceImpl.class);
	@Autowired
	private BlackListMapper blackListMapper;

	@Autowired
	private DispatchPlanMapper dispatchMapper;

	@Autowired
	private IAuth auth;

	@Autowired
	private BlackListImportQueueHandler blackListMQ;
	@Autowired
	private BlackListRecordsMapper blackRecordsMapper;

	@Override
	public void batchPlanImport(String fileName, Long userId, MultipartFile file, String orgCode) throws Exception {
		if (!fileName.matches("^.+\\.(?i)(xls)$") && !fileName.matches("^.+\\.(?i)(xlsx)$")) {
			throw new Exception("上传文件格式不正确");
		}
		boolean isExcel2003 = true;
		if (fileName.matches("^.+\\.(?i)(xlsx)$")) {
			isExcel2003 = false;
		}
		InputStream is = file.getInputStream();
		Workbook wb = null;
		if (isExcel2003) {
			wb = new HSSFWorkbook(is);
		} else {
			wb = new XSSFWorkbook(is);
		}
		Sheet sheet = wb.getSheetAt(0);
		if (sheet == null) {
			throw new Exception("模板文件不正确。");
		}
		// 重复校验
		List phones = new ArrayList<>();
		for (int r = 1; r <= sheet.getLastRowNum(); r++) {
			Row row = sheet.getRow(r);
			if (row == null) {
				continue;
			}
			String phone = "";
			if (isNull(row.getCell(0))) {
				row.getCell(0).setCellType(Cell.CELL_TYPE_STRING);
				phone = row.getCell(0).getStringCellValue();
				if (phone == null || phone == "" || !isNumeric(phone) || phone.length() != 11) {
					saveErrorRecords(phone, Constant.BLACK_LIST_IMPORT_UNIDENTIFIED, userId,orgCode);
				}
				if (phones.contains(phone)) {
					saveErrorRecords(phone, Constant.BLACK_LIST_IMPORT_DUPLICATE, userId,orgCode);
				}
			}
			if (phone == "") {
				continue;
			}
			String remark = "";
			if (isNull(row.getCell(1))) {
				row.getCell(1).setCellType(Cell.CELL_TYPE_STRING);
				remark = row.getCell(1).getStringCellValue();
			}
			BlackList blackListDto = new BlackList();
			blackListDto.setGmtModified(DateUtil.getCurrent4Time());
			blackListDto.setGmtCreate(DateUtil.getCurrent4Time());
			blackListDto.setRemark(remark);
			blackListDto.setPhone(phone);
			blackListDto.setUserId(userId.intValue());
			blackListDto.setUpdateUserId(userId.intValue());
			blackListDto.setOrgCode(orgCode);
			blackListDto.setStatus(Constant.BLACKSTATUSOK);
			blackListDto.setOrgCode(orgCode);
			ReturnData<SysUser> userById = auth.getUserById(userId);
			if (userById.success && userById.getBody() != null) {
				blackListDto.setCreateUserName(userById.getBody().getUsername());
				blackListDto.setUpdateUserName(userById.getBody().getUsername());
			}
			// 写入mq中
			blackListMQ.add(blackListDto);
			phones.add(phone);
		}
	}

	private void saveErrorRecords(String phone, Integer blackListImportUnidentified, Long userId,String orgCode) {
		BlackListRecords record = new BlackListRecords();
		record.setCreateTime(DateUtil.getCurrent4Time());
		record.setPhone(phone);
		record.setType(blackListImportUnidentified);
		record.setUserId(userId.intValue());
		record.setOrgCode(orgCode);
		ReturnData<SysUser> userById = auth.getUserById(userId);
		record.setUserName(userById.getBody().getUsername());
		blackRecordsMapper.insert(record);
	}

	@Override
	public boolean save(BlackList blackList, Long userId, String orgCode) {
		try {
			blackList.setGmtCreate(DateUtil.getCurrent4Time());
			blackList.setGmtModified(DateUtil.getCurrent4Time());
		} catch (Exception e) {
			logger.error("error", e);
		}
		blackList.setUserId(userId.intValue());
		blackList.setUpdateUserId(userId.intValue());
		blackList.setOrgCode(orgCode);
		blackList.setStatus(Constant.BLACKSTATUSOK);
		ReturnData<SysUser> userById = auth.getUserById(userId);
		if (userById.success && userById.getBody() != null) {
			blackList.setCreateUserName(userById.getBody().getUsername());
			blackList.setUpdateUserName(userById.getBody().getUsername());
		}
		int result = blackListMapper.insert(blackList);
		return result > 0 ? true : false;
	}

	@Override
	public boolean delete(String id) {
		BlackList black = new BlackList();
		black.setId(Integer.valueOf(id));
		black.setStatus(Constant.BLACKSTATUSNO);
		int result = blackListMapper.updateByPrimaryKeySelective(black);
		return result > 0 ? true : false;
	}

	@Override
	public boolean update(BlackList blackList, Long userId) {
		blackList.setGmtModified(DateUtil.getCurrent4Time());
		blackList.setUpdateUserId(userId.intValue());
		ReturnData<SysUser> userById = auth.getUserById(userId);
		if (userById.success && userById.getBody() != null) {
			blackList.setUpdateUserName(userById.getBody().getUsername());
		}
		int result = blackListMapper.updateByPrimaryKeySelective(blackList);
		return result > 0 ? true : false;
	}

	@Override
	public Page<BlackList> queryBlackListByParams(int pagenum, int pagesize, String phone, String orgCode) {
		Page<BlackList> page = new Page<>();
		page.setPageNo(pagenum);
		page.setPageSize((pagesize));
		BlackListExample example = new BlackListExample();
		example.setLimitStart((pagenum - 1) * pagesize);
		example.setLimitEnd(pagesize);
		example.setOrderByClause("`gmt_create` DESC");
		Criteria andStatusEqualTo = example.createCriteria().andOrgCodeLike(orgCode + "%")
				.andStatusEqualTo(Constant.BATCH_STATUS_SHOW);
		if (phone != null && !phone.equals("")) {
			andStatusEqualTo.andPhoneEqualTo(phone);
		}
		List<BlackList> result = blackListMapper.selectByExample(example);
		int countByExample = blackListMapper.countByExample(example);
		page.setRecords(result);
		page.setTotal(countByExample);
		return page;
	}

	public boolean isNumeric(String str) {
		Pattern pattern = Pattern.compile("[0-9]*");
		Matcher isNum = pattern.matcher(str);
		if (!isNum.matches()) {
			return false;
		}
		return true;
	}

	public boolean isNull(Cell cell) {
		if (cell == null) {
			return false;
		} else {
			return true;
		}
	}

	@Override
	public boolean checkPhoneInBlackList(String phone) {
		BlackListExample ex = new BlackListExample();
		ex.createCriteria().andPhoneEqualTo(phone).andStatusEqualTo(Constant.BLACKSTATUSOK);
		int countByExample = blackListMapper.countByExample(ex);
		return countByExample > 0 ? true : false;
	}

	@Override
	public boolean setBlackPhoneStatus(DispatchPlan dispatchPlan) {
		// 已完成
		dispatchPlan.setStatusPlan(Constant.STATUSPLAN_2);
		dispatchPlan.setStatusSync(Constant.STATUS_SYNC_1);
		dispatchPlan.setResult("X");
		int insert = dispatchMapper.insert(dispatchPlan);
		return insert > 0 ? true : false;
	}

	@Override
	public Page<BlackListRecords> queryBlackListRecords(int pagenum, int pagesize, String orgCode) {
		Page<BlackListRecords> page = new Page<>();
		page.setPageNo(pagenum);
		page.setPageSize((pagesize));
		BlackListRecordsExample example = new BlackListRecordsExample();
		example.setLimitStart((pagenum - 1) * pagesize);
		example.setLimitEnd(pagesize);
		example.setOrderByClause("`create_time` DESC");
		com.guiji.dispatch.dao.entity.BlackListRecordsExample.Criteria andOrgCodeEqualTo = example.createCriteria()
				.andOrgCodeEqualTo(orgCode);
//		if (userName != null && !userName.equals("")) {
//			andOrgCodeEqualTo.andUserNameEqualTo(userName);
//		}
		List<BlackListRecords> result = blackRecordsMapper.selectByExample(example);
		int countByExample = blackRecordsMapper.countByExample(example);
		page.setRecords(result);
		page.setTotal(countByExample);
		return page;
	}

}
