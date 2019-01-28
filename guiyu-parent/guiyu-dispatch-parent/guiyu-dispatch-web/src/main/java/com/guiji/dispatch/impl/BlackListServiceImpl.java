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
import com.guiji.dispatch.dao.BlackListMapper;
import com.guiji.dispatch.dao.DispatchPlanMapper;
import com.guiji.dispatch.dao.entity.BlackList;
import com.guiji.dispatch.dao.entity.BlackListExample;
import com.guiji.dispatch.dao.entity.BlackListExample.Criteria;
import com.guiji.dispatch.dao.entity.DispatchPlan;
import com.guiji.dispatch.dao.entity.DispatchPlanExample;
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
		List<BlackList> list = new ArrayList<>();
		for (int r = 1; r <= sheet.getLastRowNum(); r++) {
			Row row = sheet.getRow(r);
			if (row == null) {
				continue;
			}
			// 重复校验
			List phones = new ArrayList<>();
			String phone;
			if (isNull(row.getCell(0))) {
				row.getCell(0).setCellType(Cell.CELL_TYPE_STRING);
				phone = row.getCell(0).getStringCellValue();
				if (phone == null || phone == "") {
					throw new Exception("导入失败(第" + (r + 1) + "行,号码内容有问题，请检查文件");
				}
				if (!isNumeric(phone)) {
					throw new Exception("导入失败(第" + (r + 1) + "行,号码格式有问题，请检查文件");
				} else if (phone.length() != 11) {
					throw new Exception("导入失败(第" + (r + 1) + "行,号码格式有问题，请检查文件");
				}
				if (phones.contains(phone)) {
					throw new Exception("导入失败(第" + (r + 1) + "行,号码有重复，请检查文件");
				}
				phones.add(phone);
			} else {
				throw new Exception("导入失败(第" + (r + 1) + "行,电话未填写)");
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
			ReturnData<SysUser> userById = auth.getUserById(userId);
			if (userById.success && userById.getBody() != null) {
				blackListDto.setCreateUserName(userById.getBody().getUsername());
				blackListDto.setUpdateUserName(userById.getBody().getUsername());
			}
			list.add(blackListDto);
		}
		blackListMapper.BatchinsertBlackList(list);
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
		ex.createCriteria().andPhoneEqualTo(phone);
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

}
