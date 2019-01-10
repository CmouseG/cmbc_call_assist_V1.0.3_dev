//package com.guiji.dispatch.job;
//
//import java.io.File;
//import java.io.FileInputStream;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Map;
//import java.util.regex.Matcher;
//import java.util.regex.Pattern;
//import org.apache.poi.hssf.usermodel.HSSFWorkbook;
//import org.apache.poi.ss.usermodel.Cell;
//import org.apache.poi.ss.usermodel.Row;
//import org.apache.poi.ss.usermodel.Sheet;
//import org.apache.poi.ss.usermodel.Workbook;
//import org.apache.poi.xssf.usermodel.XSSFWorkbook;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Component;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RestController;
//import com.guiji.auth.api.IAuth;
//import com.guiji.component.lock.DistributedLockHandler;
//import com.guiji.component.lock.Lock;
//import com.guiji.component.result.Result.ReturnData;
//import com.guiji.dispatch.dao.DispatchPlanBatchMapper;
//import com.guiji.dispatch.dao.DispatchPlanMapper;
//import com.guiji.dispatch.dao.FileErrorRecordsMapper;
//import com.guiji.dispatch.dao.FileRecordsMapper;
//import com.guiji.dispatch.dao.entity.BlackList;
//import com.guiji.dispatch.dao.entity.DispatchPlan;
//import com.guiji.dispatch.dao.entity.DispatchPlanBatch;
//import com.guiji.dispatch.dao.entity.FileErrorRecords;
//import com.guiji.dispatch.dao.entity.FileRecords;
//import com.guiji.dispatch.dao.entity.FileRecordsExample;
//import com.guiji.dispatch.util.Constant;
//import com.guiji.robot.api.IRobotRemote;
//import com.guiji.robot.model.CheckParamsReq;
//import com.guiji.robot.model.CheckResult;
//import com.guiji.robot.model.HsParam;
//import com.guiji.user.dao.entity.SysUser;
//import com.guiji.utils.DateUtil;
//import com.guiji.utils.IdGenUtil;
//import com.guiji.utils.NetFileDownUtil;
//import com.guiji.utils.RedisUtil;
//
//@Component
//@RestController
//public class FileTask {
//	private static final Logger logger = LoggerFactory.getLogger(FileTask.class);
//
//	@Autowired
//	DistributedLockHandler distributedLockHandler;
//	@Autowired
//	private FileRecordsMapper mapper;
//
//	@Value(value = "${excelDownLoadUrl}")
//	private String excelDownLoadUrl;
//
//	@Autowired
//	private IRobotRemote robotRemote;
//
//	@Autowired
//	private IAuth authService;
//
//	@Autowired
//	private RedisUtil redisUtil;
//
//	@Autowired
//	private FileErrorRecordsMapper errorMapper;
//
//	@Autowired
//	private DispatchPlanMapper dispatchPlanMapper;
//
//	@Autowired
//	private DispatchPlanBatchMapper batchMapper;
//
//	/**
//	 * 下载文件到指定文件夹内
//	 */
//	// @Scheduled(cron = "0 0/1 * * * ?")
//	@PostMapping("downLoadExcel")
//	public void downLoadExcel() {
//		logger.info("--------------------------downLoadExcel---------------------------------------");
//		logger.info("-----------------------------------------------------------------");
//		logger.info("-----------------------------------------------------------------");
//		logger.info("-----------------------------------------------------------------");
//		logger.info("-----------------------------------------------------------------");
//		logger.info("-----------------------------------------------------------------");
//		logger.info("-----------------------------------------------------------------");
//		logger.info("-----------------------------------------------------------------");
//		logger.info("-----------------------------------------------------------------");
//		logger.info("-----------------------------------------------------------------");
//		Lock lock = new Lock("analysisExcel", "analysisExcel");
//		try {
//			if (distributedLockHandler.tryLock(lock)) { // 默认锁设置
//				// 查询当前待解析的文件记录
//				FileRecordsExample ex = new FileRecordsExample();
//				// 等待解析
//				ex.createCriteria().andStatusEqualTo("0");
//				List<FileRecords> list = mapper.selectByExample(ex);
//
//				for (FileRecords records : list) {
//					try {
//						new NetFileDownUtil(records.getUrl(),
//								// new File(excelDownLoadUrl + "/" +
//								// records.getId() + ".xls")).downfile();
//								new File(excelDownLoadUrl + "/" + records.getFileName())).downfile();
//						// 已经完成
//						records.setStatus("1");
//						mapper.updateByExampleSelective(records, new FileRecordsExample());
//					} catch (Exception e) {
//						logger.error("调用TTS语音文件{}落地异常！", records.getUrl());
//					}
//				}
//			}
//		} catch (Exception e) {
//			logger.info("error", e);
//		} finally {
//			distributedLockHandler.releaseLock(lock);
//		}
//	}
//
//	@PostMapping("analysisFile")
//	public void analysisFile() {
//		logger.info("--------------------------analysisFile----------------------------");
//		logger.info("-----------------------------------------------------------------");
//		logger.info("-----------------------------------------------------------------");
//		logger.info("-----------------------------------------------------------------");
//		logger.info("-----------------------------------------------------------------");
//		logger.info("-----------------------------------------------------------------");
//		logger.info("-----------------------------------------------------------------");
//		logger.info("-----------------------------------------------------------------");
//		logger.info("-----------------------------------------------------------------");
//		logger.info("-----------------------------------------------------------------");
//		File file = new File(excelDownLoadUrl);
//		if (!file.exists()) {
//			logger.info("do not exit");
//			return;
//		}
//		File[] excels = file.listFiles();
//		int succCount = 0;
//		int failureCount = 0;
//		FileRecords fileRecord = null;
//		for (File excel : excels) {
//			boolean checkRes = true;
//			String name = excel.getName();
//			// 根据id查询出当前上传记录
//			fileRecord = getFileRecord(name);
//			if (fileRecord == null) {
//				continue;
//			}
//			if (name.contains("xls") || name.contains("xlsx")) {
//				try {
//					FileInputStream fis = new FileInputStream(excel);
//					boolean isExcel2003 = true;
//					if (name.matches("^.+\\.(?i)(xlsx)$")) {
//						isExcel2003 = false;
//						Workbook wb = null;
//						if (isExcel2003) {
//							wb = new HSSFWorkbook(fis);
//						} else {
//							wb = new XSSFWorkbook(fis);
//						}
//						Sheet sheet = wb.getSheetAt(0);
//						List<String> phones = new ArrayList<>();
//						for (int r = 1; r <= sheet.getLastRowNum(); r++) {
//							Row row = sheet.getRow(r);
//							if (row == null) {
//								continue;
//							}
//							String phone = null;
//							if (isNull(row.getCell(0))) {
//								row.getCell(0).setCellType(Cell.CELL_TYPE_STRING);
//								phone = row.getCell(0).getStringCellValue();
//								if (!isNumeric(phone) || phones.contains(phone)) {
//									failureCount = failureCount + 1;
//									checkRes = false;
//									// throw new Exception("号码格式有问题，请检查文件");
//								}
//							} else {
//								failureCount = failureCount + 1;
//								checkRes = false;
//							}
//
//							String params = "";
//							if (isNull(row.getCell(1))) {
//								row.getCell(1).setCellType(Cell.CELL_TYPE_STRING);
//								params = row.getCell(1).getStringCellValue();
//							}
//
//							String attach = "";
//							if (isNull(row.getCell(2))) {
//								row.getCell(2).setCellType(Cell.CELL_TYPE_STRING);
//								attach = row.getCell(2).getStringCellValue();
//							}
//
//							DispatchPlanBatch disBatch = organizationBatch(fileRecord);
//							DispatchPlan dispatchPlan = organizationDispatch(fileRecord, phone, attach, params);
//							if (!checkParams(dispatchPlan)) {
//								// 校验参数失败
//								failureCount = failureCount + 1;
//								checkRes = false;
//							}
//
//							if (!checkRes) {
//								FileErrorRecords error = new FileErrorRecords();
//								error.setCreateTime(DateUtil.getCurrent4Time());
//								error.setAttach(attach);
//								error.setParams(params);
//								error.setPhone(phone);
//								error.setFileName(name);
//								errorMapper.insert(error);
//								continue;
//							}
//							dispatchPlanMapper.insert(dispatchPlan);
//							batchMapper.insert(disBatch);
//							succCount = succCount + 1;
//						}
//						if (fileRecord != null) {
//							// 修改当前批次记录信息
//							fileRecord.setSuccessCount(Integer.valueOf(succCount));
//							fileRecord.setFailureCount(Integer.valueOf(failureCount));
//							// 已完成
//							fileRecord.setStatus("1");
//							mapper.updateByPrimaryKey(fileRecord);
//						}
//
//					}
//				} catch (Exception e) {
//					logger.info("error", e);
//				}
//			}
//		}
//	}
//
//	public boolean checkParams(DispatchPlan dispatchPlan) {
//		// 检查校验参数
//		ReturnData<List<CheckResult>> checkParams = check(dispatchPlan);
//		if (checkParams.success) {
//			if (checkParams.getBody() != null) {
//				List<CheckResult> body = checkParams.getBody();
//				CheckResult checkResult = body.get(0);
//				if (!checkResult.isPass()) {
//					logger.info("机器人合成" + checkResult.getCheckMsg());
//					return false;
//				}
//			}
//		} else {
//			logger.info("请求校验参数失败,请检查机器人的参数");
//			return false;
//		}
//		return true;
//	}
//
//	public ReturnData<List<CheckResult>> check(DispatchPlan dispatchPlan) {
//		CheckParamsReq req = new CheckParamsReq();
//		HsParam hsParam = new HsParam();
//		hsParam.setParams(dispatchPlan.getParams());
//		hsParam.setSeqid(dispatchPlan.getPlanUuid());
//		hsParam.setTemplateId(dispatchPlan.getRobot());
//		List<HsParam> list = new ArrayList<>();
//		list.add(hsParam);
//		req.setNeedResourceInit(false);
//		req.setCheckers(list);
//		ReturnData<List<CheckResult>> check = robotRemote.checkParams(req);
//		return check;
//	}
//
//	private DispatchPlan organizationDispatch(FileRecords fileRecord, String phone, String attach, String params) {
//		DispatchPlan dispatchPlan = new DispatchPlan();
//		// 查询用户名称
//		ReturnData<SysUser> SysUser = authService.getUserById(Long.valueOf(fileRecord.getUserId()));
//		if (SysUser != null) {
//			dispatchPlan.setUsername(SysUser.getBody().getUsername());
//		}
//		dispatchPlan.setUserId(fileRecord.getUserId());
//
//		dispatchPlan.setPhone(phone);
//		dispatchPlan.setAttach(attach);
//		dispatchPlan.setUserId(fileRecord.getUserId());
//		dispatchPlan.setParams(params);
//		dispatchPlan.setPlanUuid(IdGenUtil.uuid());
//		try {
//			dispatchPlan.setGmtModified(DateUtil.getCurrent4Time());
//			dispatchPlan.setGmtCreate(DateUtil.getCurrent4Time());
//		} catch (Exception e) {
//			logger.info("error", e);
//		}
//		dispatchPlan.setStatusPlan(Constant.STATUSPLAN_1);
//		dispatchPlan.setStatusSync(Constant.STATUS_SYNC_0);
//		dispatchPlan.setIsDel(Constant.IS_DEL_0);
//		dispatchPlan.setReplayType(Constant.REPLAY_TYPE_0);
//		dispatchPlan.setIsTts(Constant.IS_TTS_0);
//		dispatchPlan.setFlag(Constant.IS_FLAG_0);
//		dispatchPlan.setRobot(fileRecord.getRobot());
//		dispatchPlan.setLine(Integer.valueOf(fileRecord.getLineId()));
//		dispatchPlan.setClean(fileRecord.getIsClean());
//		dispatchPlan.setCallData(Integer.valueOf(fileRecord.getCallData()));
//		dispatchPlan.setCallHour(fileRecord.getCallHour());
//		return dispatchPlan;
//	}
//
//	private DispatchPlanBatch organizationBatch(FileRecords fileRecord) {
//		DispatchPlanBatch dispatchPlanBatch = new DispatchPlanBatch();
//		try {
//			dispatchPlanBatch.setGmtModified(DateUtil.getCurrent4Time());
//			dispatchPlanBatch.setGmtCreate(DateUtil.getCurrent4Time());
//			dispatchPlanBatch.setStatusNotify(Constant.STATUS_NOTIFY_0);
//			dispatchPlanBatch.setUserId(fileRecord.getUserId());
//			dispatchPlanBatch.setName(fileRecord.getBatchName());
//		} catch (Exception e) {
//			logger.info("error", e);
//		}
//		return dispatchPlanBatch;
//	}
//
//	public boolean checkBalckList(String phone) {
//		if (redisUtil.get("blackList") != null) {
//			Map<String, BlackList> base = (Map) redisUtil.get("blackList");
//			if (base.containsKey(phone)) {
//				return true;
//			} else {
//				return false;
//			}
//		}
//		return false;
//	}
//
//	public boolean isNull(Cell cell) {
//		if (cell == null) {
//			return false;
//		} else {
//			return true;
//		}
//	}
//
//	public boolean isNumeric(String str) {
//		Pattern pattern = Pattern.compile("[0-9]*");
//		Matcher isNum = pattern.matcher(str);
//		if (!isNum.matches()) {
//			return false;
//		}
//		return true;
//	}
//
//	public FileRecords getFileRecord(String name) {
//		FileRecordsExample ex = new FileRecordsExample();
//		ex.createCriteria().andFileNameEqualTo(name);
//		List<FileRecords> selectByExample = mapper.selectByExample(ex);
//		if (selectByExample.size() > 0) {
//			return selectByExample.get(0);
//		}
//		return null;
//	}
//
//}
