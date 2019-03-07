package com.guiji.dispatch.batchimport.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.guiji.dispatch.batchimport.BatchImportErrorCodeEnum;
import com.guiji.dispatch.batchimport.IBatchImportFieRecordErrorService;
import com.guiji.dispatch.batchimport.IBatchImportQueueHandlerService;
import com.guiji.dispatch.dao.FileRecordsMapper;
import com.guiji.dispatch.dao.entity.DispatchPlan;
import com.guiji.dispatch.dao.entity.FileErrorRecords;
import com.guiji.dispatch.dao.entity.FileRecords;
import com.guiji.dispatch.service.IBlackListService;
import com.guiji.dispatch.service.IPhoneRegionService;
import com.guiji.dispatch.util.Constant;
import com.guiji.utils.BeanUtil;
import com.guiji.utils.DateUtil;
import com.guiji.utils.IdGenUtil;
import com.guiji.utils.JsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BatchImportExcelListener extends AnalysisEventListener<Object>
{
	private static final Logger logger = LoggerFactory.getLogger(BatchImportExcelListener.class);
	
	//传递过来的参数
	private DispatchPlan dispatchPlanParam;
	private int batchId;
	private Long userId;
	private String orgCode;	
	//自建参数
	DispatchPlan dispatchPlan = null;
	Integer fileRecordId = null;
	List<String> phones = new ArrayList<>();
	int count;
	//构造
	public BatchImportExcelListener(DispatchPlan dispatchPlanParam, int batchId, Long userId, String orgCode)
	{
		this.dispatchPlanParam = dispatchPlanParam;
		this.batchId = batchId;
		this.userId = userId;
		this.orgCode = orgCode;
		
		dispatchPlan = new DispatchPlan();
		fileRecordId = dispatchPlanParam.getFileRecordId();
		BeanUtil.copyProperties(dispatchPlanParam, dispatchPlan);
		phones = new ArrayList<>();
		count = 0;
	}
	
	private IBatchImportFieRecordErrorService fileRecordErrorService;
	private IPhoneRegionService phoneRegionService;
	private IBlackListService blackService;
	private IBatchImportQueueHandlerService BatchImportQueueHandler;
	private FileRecordsMapper fileRecordsMapper;

	private Long i = new Long(0); //记录行数
	@Override
	public void invoke(Object object, AnalysisContext context)
	{
		i++;
		BatchImportExcelModel row = (BatchImportExcelModel) object;

		try
		{
			logger.info("获取的数据行是：{}", row);
			dispatchPlan = doWithOneRow(row, dispatchPlanParam);

			logger.info("获取的dispatchPlan是：{}", JsonUtils.bean2Json(dispatchPlan));

			if (dispatchPlan == null) {
				dispatchPlan = new DispatchPlan();
				dispatchPlan.setFileRecordId(fileRecordId);
				dispatchPlan.setPhone(row.getPhone());
				dispatchPlan.setParams(row.getParamaters());
				dispatchPlan.setAttach(row.getAttach());
				saveFileErrorRecords(dispatchPlan, BatchImportErrorCodeEnum.UNKNOWN, i.intValue());
				return;
			}
			
			if (phones.contains(dispatchPlan.getPhone())) {
				// 当前号码存在重复的数据
				phones.add(dispatchPlan.getPhone());
				saveFileErrorRecords(dispatchPlan, BatchImportErrorCodeEnum.DUPLICATE, i.intValue());
				logger.info("导入失败, 第{}行,电话号码{}存在重复的数据", i, dispatchPlan.getPhone());
				return;
			}
			
			dispatchPlan.setBatchId(batchId);
			dispatchPlan.setUserId(userId.intValue());
			dispatchPlan.setOrgCode(orgCode);
			//查询号码归属地
//			String cityName = phoneRegionService.queryPhoneRegion(dispatchPlan.getPhone());
//			dispatchPlan.setCityName(cityName);
			
			// 校验黑名单逻辑
//			if (blackService.checkPhoneInBlackList(dispatchPlan.getPhone(),orgCode)) {
//				count = count + 1;
//				blackService.setBlackPhoneStatus(dispatchPlan);
//				phones.add(dispatchPlan.getPhone());
//				return;
//			}
			
			// 放入队列
			BatchImportQueueHandler.add(dispatchPlan);
			count = count + 1;
			phones.add(dispatchPlan.getPhone());
		} catch (Exception e) {
			saveFileErrorRecords(dispatchPlan, BatchImportErrorCodeEnum.UNKNOWN);
			logger.error("导入失败, 第{}行发生异常", i, e);
		}
		
	}

	private DispatchPlan doWithOneRow(BatchImportExcelModel row, DispatchPlan dispatchPlanParam)
	{
		String phone = row.getPhone();
		if (!isNumLegal(phone)) {
			// 非手机号 导入失败(第" + (r + 1) + "行,电话号码格式不正确
			logger.debug("非手机号 导入失败, 第{}行,电话号码{}格式不正确", i, phone);
			return null;
		}

		String params = row.getParamaters();
		String attach = row.getAttach();

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
	
	@Override
	public void doAfterAllAnalysed(AnalysisContext context)
	{
		// 更新导入记录
		FileRecords fileRecord = new FileRecords();
		fileRecord.setSuccessCount(count);
		fileRecord.setId(Long.valueOf(dispatchPlanParam.getFileRecordId().intValue()));
		fileRecordsMapper.updateByPrimaryKeySelective(fileRecord);
	}
	
	
	// set
	public void setFileRecordErrorService(IBatchImportFieRecordErrorService fileRecordErrorService) {
		this.fileRecordErrorService = fileRecordErrorService;
	}
	public void setPhoneRegionService(IPhoneRegionService phoneRegionService) {
		this.phoneRegionService = phoneRegionService;
	}
	public void setBlackService(IBlackListService blackService) {
		this.blackService = blackService;
	}
	public void setBatchImportQueueHandler(IBatchImportQueueHandlerService batchImportQueueHandler) {
		BatchImportQueueHandler = batchImportQueueHandler;
	}
	public void setFileRecordsMapper(FileRecordsMapper fileRecordsMapper) {
		this.fileRecordsMapper = fileRecordsMapper;
	}
	
}
