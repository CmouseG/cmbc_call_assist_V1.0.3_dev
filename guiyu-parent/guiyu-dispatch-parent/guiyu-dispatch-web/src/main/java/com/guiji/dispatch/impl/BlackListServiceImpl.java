package com.guiji.dispatch.impl;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.guiji.dispatch.dto.QueryBlackListDto;
import com.guiji.dispatch.service.GetAuthUtil;
import com.guiji.dispatch.sys.ResultPage;
import com.guiji.utils.JsonUtils;
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

	@Autowired
	private GetAuthUtil getAuthUtil;

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
				phone = null != phone?phone.trim():null;
				if (phone == null || phone == "") {
					saveErrorRecords(phone, Constant.BLACK_LIST_IMPORT_UNIDENTIFIED, userId, orgCode);
					continue;
				}
				if (!isNumLegal(phone)) {
					saveErrorRecords(phone, Constant.BLACK_LIST_IMPORT_WRONGFUL, userId, orgCode);
					continue;
				}
				if (phone.length() != 11) {
					saveErrorRecords(phone, Constant.BLACK_LIST_IMPORT_LENGTH, userId, orgCode);
					continue;
				}
				if (phones.contains(phone)) {
					saveErrorRecords(phone, Constant.BLACK_LIST_IMPORT_DUPLICATE, userId, orgCode);
					continue;
				}
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
			// 判断当前数据库中是否存在当前号码
			if (checkPhoneInDB(blackListDto)) {
				saveErrorRecords(phone, Constant.BLACK_LIST_IMPORT_INDB, userId, orgCode);
				continue;
			}
			// 写入mq中
			blackListMQ.add(blackListDto);
			phones.add(phone);
		}
	}

	private boolean isNumLegal(String str) {
		// String regExp =
		// "^((13[0-9])|(15[^4])|(18[0,2,3,5-9])|(17[0-8])|(147))\\d{8}$";
		String regExp = "^(?!11)\\d{11}$";
		Pattern p = Pattern.compile(regExp);
		Matcher m = p.matcher(str);
		return m.matches();
	}

	private boolean checkPhoneInDB(BlackList blackListDto) {
		BlackListExample ex = new BlackListExample();
		ex.createCriteria().andPhoneEqualTo(blackListDto.getPhone()).andStatusEqualTo(Constant.BLACKSTATUSOK).andOrgCodeLike(blackListDto.getOrgCode()+"%");
		int countByExample = blackListMapper.countByExample(ex);
		if (countByExample > 0) {
			return true;
		} else {
			return false;
		}
	}

	private void saveErrorRecords(String phone, Integer blackListImportUnidentified, Long userId, String orgCode) {
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
		if(checkPhoneInDB(blackList)){
			return false;
		}
		if(!isNumLegal(blackList.getPhone())){
			return false;
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
	public Page<BlackList> queryBlackListByParams(int pagenum, int pagesize,
												  String phone, String orgCode,Integer isDesensitization, Long userId, Integer authLevel) {
		Page<BlackList> page = new Page<>();
		page.setPageNo(pagenum);
		page.setPageSize((pagesize));

		String operUserId = getAuthUtil.getUserIdByAuthLevel(authLevel, userId+"");//获取用户ID
		orgCode = getAuthUtil.getOrgCodeByAuthLevel(authLevel, orgCode);//获取企业组织编码

		QueryBlackListDto blackParam = new QueryBlackListDto();
		blackParam.setPhone(phone);
		blackParam.setUserId(null != operUserId?Integer.valueOf(operUserId):null);
		blackParam.setOrgCode(orgCode);
		blackParam.setAuthLevel(authLevel);
		blackParam.setStatus(Constant.BATCH_STATUS_SHOW);
		logger.info("com.guiji.dispatch.impl.BlackListServiceImpl.queryBlackListByParams:{}", JsonUtils.bean2Json(blackParam));
		ResultPage<BlackList> pageRes = new ResultPage<BlackList>(pagenum, pagesize);
		pageRes.setOrderBy("gmt_create");
		pageRes.setSort("DESC");
		List<BlackList> result = blackListMapper.queryBlackList(blackParam, pageRes);
		if (isDesensitization.equals(0)) {
			for (BlackList black : result) {
				if (black.getPhone().length() <= 7) {
					continue;
				}
//				if (userId == black.getUserId().longValue()) {
//					continue;
//				}
				String phoneNumber = black.getPhone().substring(0, 3) + "****"
						+ black.getPhone().substring(7, black.getPhone().length());
				black.setPhone(phoneNumber);
			}
		}
		/*
		int countByExample = blackListMapper.countByExample(example);
		*/
		int countByExample = blackListMapper.queryBlackCount(blackParam);
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
	public boolean checkPhoneInBlackList(String phone,String orgCode) {
		BlackListExample ex = new BlackListExample();
		ex.createCriteria().andPhoneEqualTo(phone).andStatusEqualTo(Constant.BLACKSTATUSOK).andOrgCodeLike(orgCode + "%");
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

	/**
	 * 查询黑名单记录列表
	 * @param queryBlackParam
	 * @return
	 */
	@Override
	public ResultPage<BlackListRecords> queryBlackListRecords(QueryBlackListDto queryBlackParam) {
		/*
		BlackListRecordsExample example = new BlackListRecordsExample();
		example.setLimitStart((pagenum - 1) * pagesize);
		example.setLimitEnd(pagesize);
		example.setOrderByClause("`create_time` DESC");
		com.guiji.dispatch.dao.entity.BlackListRecordsExample.Criteria andOrgCodeEqualTo = example.createCriteria()
				.andOrgCodeEqualTo(orgCode).andOrgCodeLike(orgCode + ".%");
		// if (userName != null && !userName.equals("")) {
		// andOrgCodeEqualTo.andUserNameEqualTo(userName);
		// }
		List<BlackListRecords> result = blackRecordsMapper.selectByExample(example);
		int countByExample = blackRecordsMapper.countByExample(example);
		*/
		/*BlackListRecords blackRecord = new BlackListRecords();
		blackRecord.setOrgCode(orgCode);*/

		ResultPage<BlackListRecords> pageRes = new ResultPage<BlackListRecords>(queryBlackParam);
		pageRes.setOrderBy("create_time");
		pageRes.setSort("DESC");
		List<BlackListRecords> result = blackRecordsMapper.queryBlackListRecords(queryBlackParam, pageRes);
		int countByExample = blackRecordsMapper.queryBlackRecordsCount(queryBlackParam);
		pageRes.setList(result);
		pageRes.setTotalItemAndPageNumber(countByExample);
		return pageRes;
	}

}
