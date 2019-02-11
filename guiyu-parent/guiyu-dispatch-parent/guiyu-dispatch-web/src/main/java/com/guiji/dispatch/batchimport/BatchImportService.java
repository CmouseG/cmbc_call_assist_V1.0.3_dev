package com.guiji.dispatch.batchimport;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.guiji.dispatch.dao.FileRecordsMapper;
import com.guiji.dispatch.dao.entity.DispatchPlan;
import com.guiji.dispatch.dao.entity.FileErrorRecords;
import com.guiji.dispatch.dao.entity.FileRecords;
import com.guiji.dispatch.impl.DispatchPlanServiceImpl;
import com.guiji.dispatch.service.IBlackListService;
import com.guiji.dispatch.service.IPhoneRegionService;
import com.guiji.dispatch.util.Constant;
import com.guiji.utils.BeanUtil;
import com.guiji.utils.DateUtil;
import com.guiji.utils.IdGenUtil;

@Service
public class BatchImportService implements IBatchImportService {

	private static Logger logger = LoggerFactory.getLogger(DispatchPlanServiceImpl.class);

	@Autowired
	private IBatchImportFieRecordErrorService fileRecordErrorService;
	@Autowired
	private IBatchImportQueueHandlerService BatchImportQueueHandler;
	@Autowired
	private FileRecordsMapper fileRecordsMapper;
	@Autowired
	private IBlackListService blackService;
	@Autowired
	private IPhoneRegionService phoneRegionService;

	@Override
	public void batchImport(Sheet sheet, int batchId, DispatchPlan dispatchPlanParam, Long userId, String orgCode) {
		DispatchPlan dispatchPlan = new DispatchPlan();
		Integer fileRecordId = dispatchPlanParam.getFileRecordId();
		BeanUtil.copyProperties(dispatchPlanParam, dispatchPlan);
		// 校验
		List<String> phones = new ArrayList<>();
		int count = 0;
		for (int r = 1; r <= sheet.getLastRowNum(); r++) {
			try {
				Row row = sheet.getRow(r);
				if (row == null) {
					continue;
				}
				dispatchPlan = doWithOneRow(row, dispatchPlanParam);
				if (dispatchPlan == null) {
					dispatchPlan = new DispatchPlan();
					dispatchPlan.setFileRecordId(fileRecordId);
					dispatchPlan.setPhone(getCellValue(row, 0));
					dispatchPlan.setParams(getCellValue(row, 1));
					dispatchPlan.setAttach(getCellValue(row, 2));
					saveFileErrorRecords(dispatchPlan, BatchImportErrorCodeEnum.UNKNOWN, row.getRowNum() + 1);
					continue;
				}

				if (phones.contains(dispatchPlan.getPhone())) {
					// 当前号码存在重复的数据
					phones.add(dispatchPlan.getPhone());
					saveFileErrorRecords(dispatchPlan, BatchImportErrorCodeEnum.DUPLICATE, row.getRowNum() + 1);
					logger.info("导入失败, 第{}行,电话号码{}存在重复的数据", row.getRowNum() + 1, dispatchPlan.getPhone());
					continue;
				}

				dispatchPlan.setBatchId(batchId);
				dispatchPlan.setUserId(userId.intValue());
				dispatchPlan.setOrgCode(orgCode);
				//查询号码归属地
				String cityName = phoneRegionService.queryPhoneRegion(dispatchPlan.getPhone());
				dispatchPlan.setCityName(cityName);

				// 校验黑名单逻辑
				if (blackService.checkPhoneInBlackList(dispatchPlan.getPhone(),orgCode)) {
					count = count + 1;
					blackService.setBlackPhoneStatus(dispatchPlan);
					phones.add(dispatchPlan.getPhone());
					continue;
				}

				// 放入队列
				BatchImportQueueHandler.add(dispatchPlan);
				count = count + 1;
				phones.add(dispatchPlan.getPhone());
			} catch (Exception e) {
				saveFileErrorRecords(dispatchPlan, BatchImportErrorCodeEnum.UNKNOWN);
				logger.debug("导入失败, 第{}行发生异常", r + 1, e);
			}
		}
		// 更新导入记录
		FileRecords fileRecord = new FileRecords();
		fileRecord.setSuccessCount(count);
		fileRecord.setId(Long.valueOf(dispatchPlanParam.getFileRecordId().intValue()));
		fileRecordsMapper.updateByPrimaryKeySelective(fileRecord);
	}

	private void saveFileErrorRecords(DispatchPlan vo, BatchImportErrorCodeEnum errorCodeEnum, Integer errorLine) {
		FileErrorRecords records = new FileErrorRecords();
		records.setAttach(vo.getAttach());
		try {
			records.setCreateTime(DateUtil.getCurrent4Time());
		} catch (Exception e) {
			e.printStackTrace();
		}
		records.setParams(vo.getParams());
		records.setPhone(vo.getPhone());
		records.setFileRecordsId(Long.valueOf(vo.getFileRecordId()));
		records.setErrorType(errorCodeEnum.getValue());
		records.setErrorLine(errorLine);
		records.setDataType(Constant.IMPORT_DATA_TYPE_PAGE);
		records.setBatchId(vo.getBatchId());
		records.setBatchName(vo.getBatchName());
		fileRecordErrorService.save(records);
	}

	private void saveFileErrorRecords(DispatchPlan vo, BatchImportErrorCodeEnum errorCodeEnum) {
		FileErrorRecords records = new FileErrorRecords();
		records.setAttach(vo.getAttach());
		try {
			records.setCreateTime(DateUtil.getCurrent4Time());
		} catch (Exception e) {
			e.printStackTrace();
		}
		records.setParams(vo.getParams());
		records.setPhone(vo.getPhone());
		records.setFileRecordsId(Long.valueOf(vo.getFileRecordId()));
		records.setErrorType(errorCodeEnum.getValue());
		records.setDataType(Constant.IMPORT_DATA_TYPE_PAGE);
		records.setBatchId(vo.getBatchId());
		records.setBatchName(vo.getBatchName());
		fileRecordErrorService.save(records);
	}

	private DispatchPlan doWithOneRow(Row row, DispatchPlan dispatchPlanParam) throws Exception {
		String phone = getCellValue(row, 0);
		if (!isNumLegal(phone)) {
			// 非手机号 导入失败(第" + (r + 1) + "行,电话号码格式不正确
			logger.debug("非手机号 导入失败, 第{}行,电话号码{}格式不正确", row.getRowNum() + 1, phone);
			return null;
		}

		String params = getCellValue(row, 1);
		String attach = getCellValue(row, 2);

		DispatchPlan dispatchPlan = new DispatchPlan();
		BeanUtil.copyProperties(dispatchPlanParam, dispatchPlan);

		dispatchPlan.setPhone(phone);
		dispatchPlan.setAttach(attach);
		dispatchPlan.setParams(params);
		dispatchPlan.setPlanUuid(IdGenUtil.uuid());
		dispatchPlan.setGmtModified(DateUtil.getCurrent4Time());
		dispatchPlan.setGmtCreate(DateUtil.getCurrent4Time());

		dispatchPlan.setStatusPlan(Constant.STATUSPLAN_1);
		dispatchPlan.setStatusSync(Constant.STATUS_SYNC_0);
		dispatchPlan.setIsDel(Constant.IS_DEL_0);
		dispatchPlan.setReplayType(Constant.REPLAY_TYPE_0);
		dispatchPlan.setIsTts(Constant.IS_TTS_0);
		dispatchPlan.setFlag(Constant.IS_FLAG_0);

		return dispatchPlan;
	}

	private String getCellValue(Row row, int index) {
		if (row.getCell(index) != null) {
			row.getCell(index).setCellType(Cell.CELL_TYPE_STRING);
			return row.getCell(index).getStringCellValue();
		}

		return "";
	}

	/**
	 * 正则表达 手机号码由11位数字组成， 匹配格式：前三位固定格式+后8位任意数 此方法中前三位格式有： 13+任意数 15+除4的任意数
	 * 18+除1和4的任意数 17+除9的任意数 147
	 */
	private boolean isNumLegal(String str) {
		// String regExp =
		// "^((13[0-9])|(15[^4])|(18[0,2,3,5-9])|(17[0-8])|(147))\\d{8}$";
		String regExp = "^(?!11)\\d{11}$";
		Pattern p = Pattern.compile(regExp);
		Matcher m = p.matcher(str);
		return m.matches();
	}
}
