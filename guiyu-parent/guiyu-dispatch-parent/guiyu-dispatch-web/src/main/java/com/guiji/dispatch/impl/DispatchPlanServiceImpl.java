package com.guiji.dispatch.impl;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.guiji.auth.api.IAuth;
import com.guiji.ccmanager.api.ICallManagerOut;
import com.guiji.ccmanager.api.ICallPlanDetail;
import com.guiji.ccmanager.entity.LineConcurrent;
import com.guiji.ccmanager.vo.CallPlanDetailRecordVO;
import com.guiji.common.model.Page;
import com.guiji.component.result.Result.ReturnData;
import com.guiji.dispatch.bean.IdsDto;
import com.guiji.dispatch.bean.MessageDto;
import com.guiji.dispatch.bean.sendMsgDto;
import com.guiji.dispatch.dao.DispatchHourMapper;
import com.guiji.dispatch.dao.DispatchPlanBatchMapper;
import com.guiji.dispatch.dao.DispatchPlanMapper;
import com.guiji.dispatch.dao.SendMsgRecordsMapper;
import com.guiji.dispatch.dao.SmsTunnelMapper;
import com.guiji.dispatch.dao.ThirdInterfaceRecordsMapper;
import com.guiji.dispatch.dao.UserSmsConfigMapper;
import com.guiji.dispatch.dao.entity.BlackList;
import com.guiji.dispatch.dao.entity.DispatchHourExample;
import com.guiji.dispatch.dao.entity.DispatchPlan;
import com.guiji.dispatch.dao.entity.DispatchPlanBatch;
import com.guiji.dispatch.dao.entity.DispatchPlanBatchExample;
import com.guiji.dispatch.dao.entity.DispatchPlanExample;
import com.guiji.dispatch.dao.entity.DispatchPlanExample.Criteria;
import com.guiji.dispatch.dao.entity.SendMsgRecords;
import com.guiji.dispatch.dao.entity.SmsTunnel;
import com.guiji.dispatch.dao.entity.ThirdInterfaceRecords;
import com.guiji.dispatch.dao.entity.UserSmsConfig;
import com.guiji.dispatch.dao.entity.UserSmsConfigExample;
import com.guiji.dispatch.service.IDispatchPlanService;
import com.guiji.dispatch.service.IMessageService;
import com.guiji.dispatch.util.Base64MD5Util;
import com.guiji.dispatch.util.Constant;
import com.guiji.robot.api.IRobotRemote;
import com.guiji.robot.model.CheckParamsReq;
import com.guiji.robot.model.CheckResult;
import com.guiji.robot.model.HsParam;
import com.guiji.user.dao.entity.SysUser;
import com.guiji.utils.DateUtil;
import com.guiji.utils.HttpClientUtil;
import com.guiji.utils.IdGenUtil;
import com.guiji.utils.JsonUtils;
import com.guiji.utils.RedisUtil;

@Service
public class DispatchPlanServiceImpl implements IDispatchPlanService {
	static Logger logger = LoggerFactory.getLogger(DispatchPlanServiceImpl.class);

	@Autowired
	private DispatchPlanMapper dispatchPlanMapper;

	@Autowired
	private DispatchPlanBatchMapper dispatchPlanBatchMapper;

	@Autowired
	private ICallManagerOut callManagerFeign;

	@Autowired
	private DispatchHourMapper dispatchHourMapper;

	@Autowired
	private IAuth authService;

	@Autowired
	private RedisUtil redisUtil;

	@Autowired
	private IRobotRemote robotRemote;

	@Autowired
	private IAuth auth;

	@Autowired
	private ICallPlanDetail callPlanDetail;

	@Autowired
	private ThirdInterfaceRecordsMapper thirdInterfaceRecordsMapper;

	@Autowired
	private UserSmsConfigMapper userSmsConfigMapper;

	@Autowired
	private SmsTunnelMapper smsTunnerMapper;
	@Autowired
	private IMessageService msgService;

	@Override
	public MessageDto addSchedule(DispatchPlan dispatchPlan, Long userId) throws Exception {
		boolean result = checkBalckList(dispatchPlan);
		MessageDto dto = new MessageDto();
		dispatchPlan.setPlanUuid(IdGenUtil.uuid());
		// 检查参数
		ReturnData<List<CheckResult>> checkParams = checkParams(dispatchPlan);
		if (checkParams.success) {
			if (checkParams.getBody() != null) {
				List<CheckResult> body = checkParams.getBody();
				CheckResult checkResult = body.get(0);
				if (!checkResult.isPass()) {
					dto.setMsg(checkResult.getCheckMsg());
					dto.setResult(false);
					return dto;
				}
			}
		} else {
			dto.setMsg(checkParams.getMsg());
			dto.setResult(false);
			return dto;
		}

		// 查询用户名称
		ReturnData<SysUser> SysUser = authService.getUserById(userId);
		if (SysUser != null) {
			dispatchPlan.setUsername(SysUser.getBody().getUsername());
		}
		DispatchPlanBatch dispatchPlanBatch = new DispatchPlanBatch();
		dispatchPlanBatch.setName(dispatchPlan.getBatchName());
		// 通知状态;通知状态1等待2失败3成功
		dispatchPlanBatch.setStatusNotify(Constant.STATUSNOTIFY_0);
		dispatchPlanBatch.setGmtModified(DateUtil.getCurrent4Time());
		dispatchPlanBatch.setGmtCreate(DateUtil.getCurrent4Time());
		dispatchPlanBatch.setStatusShow(dispatchPlan.getStatusShow());
		dispatchPlanBatch.setUserId(userId.intValue());
		dispatchPlanBatchMapper.insert(dispatchPlanBatch);

		// dispatchPlan.setPlanUuid(IdGenUtil.uuid());
		dispatchPlan.setUserId(userId.intValue());
		if (result) {
			// 当前黑名单
			logger.info("当前号码添加处于黑名单状态:" + dispatchPlan.getPhone());
			dispatchPlan.setStatusPlan(Constant.STATUSPLAN_2);
			dispatchPlan.setStatusSync(Constant.STATUS_SYNC_1);
		} else {
			dispatchPlan.setStatusPlan(Constant.STATUSPLAN_1);
			dispatchPlan.setStatusSync(Constant.STATUS_SYNC_0);
		}
		dispatchPlan.setGmtModified(DateUtil.getCurrent4Time());
		dispatchPlan.setGmtCreate(DateUtil.getCurrent4Time());
		dispatchPlan.setReplayType(Constant.REPLAY_TYPE_0);
		dispatchPlan.setIsDel(Constant.IS_DEL_0);
		dispatchPlan.setBatchId(dispatchPlanBatch.getId());
		dispatchPlan.setIsTts(Constant.IS_TTS_0);
		dispatchPlan.setFlag(Constant.IS_FLAG_0);

		dispatchPlanMapper.insert(dispatchPlan);
		return dto;
	}

	public ReturnData<List<CheckResult>> checkParams(DispatchPlan dispatchPlan) {
		CheckParamsReq req = new CheckParamsReq();
		HsParam hsParam = new HsParam();
		hsParam.setParams(dispatchPlan.getParams());
		hsParam.setSeqid(dispatchPlan.getPlanUuid());
		hsParam.setTemplateId(dispatchPlan.getRobot());
		List<HsParam> list = new ArrayList<>();
		list.add(hsParam);
		req.setNeedResourceInit(false);
		req.setCheckers(list);
		ReturnData<List<CheckResult>> checkParams = robotRemote.checkParams(req);
		return checkParams;
	}

	@Override
	public Page<DispatchPlan> querySchedules(Integer userId, int pagenum, int pagesize) {
		Page<DispatchPlan> page = new Page<>();
		page.setPageNo(pagenum);
		page.setPageSize((pagesize));
		DispatchPlanExample ex = new DispatchPlanExample();
		ex.setLimitStart((pagenum - 1) * pagesize);
		ex.setLimitEnd(pagesize);
		if (userId != 0) {
			ex.createCriteria().andUserIdEqualTo(userId);
		}
		ex.createCriteria().andIsDelEqualTo(Constant.IS_DEL_0);
		List<DispatchPlan> selectByExample = dispatchPlanMapper.selectByExample(ex);
		int count = dispatchPlanMapper.countByExample(ex);
		page.setRecords(selectByExample);
		page.setTotal(count);
		return page;
	}

	@Override
	public boolean pauseSchedule(String planUuid) {
		DispatchPlan dispatchPlan = new DispatchPlan();
		dispatchPlan.setPlanUuid(planUuid);
		dispatchPlan.setStatusPlan(Constant.STATUSPLAN_3);
		int result = dispatchPlanMapper.updateByExampleSelective(dispatchPlan, new DispatchPlanExample());
		return result > 0 ? true : false;
	}

	@Override
	public boolean resumeSchedule(String planUuid) {
		DispatchPlan dispatchPlan = new DispatchPlan();
		dispatchPlan.setPlanUuid(planUuid);
		dispatchPlan.setStatusPlan(Constant.STATUSPLAN_1);
		int result = dispatchPlanMapper.updateByExampleSelective(dispatchPlan, new DispatchPlanExample());
		return result > 0 ? true : false;
	}

	@Override
	public boolean cancelSchedule(String planUuid) {
		DispatchPlan dispatchPlan = new DispatchPlan();
		dispatchPlan.setPlanUuid(planUuid);
		dispatchPlan.setStatusPlan(Constant.STATUSPLAN_4);
		int result = dispatchPlanMapper.updateByExampleSelective(dispatchPlan, new DispatchPlanExample());
		return result > 0 ? true : false;
	}

	@Override
	public boolean OperationAllDispatchByUserId(Integer userId, Integer status) {
		DispatchPlan dispatchPlan = new DispatchPlan();
		dispatchPlan.setUserId(userId);
		dispatchPlan.setStatusPlan(status);
		logger.info("OperationAllDispatchByUserId updateByExample");
		int result = dispatchPlanMapper.updateByExample(dispatchPlan, new DispatchPlanExample());
		logger.info("OperationAllDispatchByUserId end");
		return result > 0 ? true : false;
	}

	@Override
	public List<DispatchPlan> queryExecuteResult(String planUuid) {
		return null;
	}

	@Override
	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public boolean batchImport(String fileName, Long userId, MultipartFile file, String str) throws Exception {
		boolean result = false;
		List<DispatchPlan> succ = new ArrayList<>();

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
		DispatchPlanBatch dispatchPlanBatch = JSONObject.parseObject(str, DispatchPlanBatch.class);

		dispatchPlanBatch.setGmtModified(DateUtil.getCurrent4Time());
		dispatchPlanBatch.setGmtCreate(DateUtil.getCurrent4Time());
		dispatchPlanBatch.setStatusNotify(Constant.STATUS_NOTIFY_0);
		dispatchPlanBatch.setUserId(userId.intValue());

		// 查询用户名称
		ReturnData<SysUser> SysUser = authService.getUserById(userId);

		// 重复校验
		List phones = new ArrayList<>();
		for (int r = 1; r <= sheet.getLastRowNum(); r++) {
			DispatchPlan dispatchPlan = JSONObject.parseObject(str, DispatchPlan.class);
			dispatchPlanBatch.setName(dispatchPlan.getBatchName());
			if (SysUser != null) {
				dispatchPlan.setUsername(SysUser.getBody().getUsername());
			}
			Row row = sheet.getRow(r);
			if (row == null) {
				continue;
			}
			String phone;
			if (isNull(row.getCell(0))) {
				row.getCell(0).setCellType(Cell.CELL_TYPE_STRING);
				phone = row.getCell(0).getStringCellValue();
				if (!isNumeric(phone)) {
					throw new Exception("号码格式有问题，请检查文件");
				}
				phones.add(phone);
			} else {
				throw new Exception("导入失败(第" + (r + 1) + "行,电话未填写)");
			}

			String params = "";
			if (isNull(row.getCell(1))) {
				row.getCell(1).setCellType(Cell.CELL_TYPE_STRING);
				params = row.getCell(1).getStringCellValue();
			}

			String attach = "";
			if (isNull(row.getCell(2))) {
				row.getCell(2).setCellType(Cell.CELL_TYPE_STRING);
				attach = row.getCell(2).getStringCellValue();
			}

			HashSet<Integer> hashSet = new HashSet<>(phones);
			if (phones.size() != hashSet.size()) {
				throw new Exception("当前号码存在重复的数据,请检查文件");
			}
			dispatchPlan.setPhone(phone);
			dispatchPlan.setAttach(attach);
			dispatchPlan.setUserId(userId.intValue());
			dispatchPlan.setParams(params);
			dispatchPlan.setPlanUuid(IdGenUtil.uuid());
			dispatchPlan.setGmtModified(DateUtil.getCurrent4Time());
			dispatchPlan.setGmtCreate(DateUtil.getCurrent4Time());
			dispatchPlan.setBatchId(dispatchPlanBatch.getId());
			dispatchPlan.setStatusPlan(Constant.STATUSPLAN_1);
			dispatchPlan.setStatusSync(Constant.STATUS_SYNC_0);
			dispatchPlan.setIsDel(Constant.IS_DEL_0);
			dispatchPlan.setReplayType(Constant.REPLAY_TYPE_0);
			dispatchPlan.setIsTts(Constant.IS_TTS_0);
			dispatchPlan.setFlag(Constant.IS_FLAG_0);
			succ.add(dispatchPlan);
		}
		// 检查校验参数
		// ReturnData<List<CheckResult>> checkParams =
		// checkParams(dispatchPlan);
		// if (checkParams.success) {
		// if (checkParams.getBody() != null) {
		// List<CheckResult> body = checkParams.getBody();
		// CheckResult checkResult = body.get(0);
		// if (!checkResult.isPass()) {
		// throw new Exception("机器人合成" + checkResult.getCheckMsg());
		// }
		// }
		// } else {
		// throw new Exception("请求校验参数失败,请检查机器人的参数");
		// }
		List<List<DispatchPlan>> averageAssign = averageAssign(succ, 10);
		dispatchPlanBatch.setId(null);
		dispatchPlanBatchMapper.insert(dispatchPlanBatch);
		for (List<DispatchPlan> tmpList : averageAssign) {
			for (DispatchPlan dis : tmpList) {
				dis.setBatchId(dispatchPlanBatch.getId());
			}
			logger.info("批量插入开始--------------");
			if (tmpList.size() > 0) {
				dispatchPlanMapper.insertDispatchPlanList(tmpList);
			}
			logger.info("批量插入结束-----------------");
		}

		return result;
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
	public boolean successSchedule(String planUuid, String label) {
		logger.info("----------------------------successSchedule-------------------------------------");
		logger.info("----------------------------successSchedule-------------------------------------");
		logger.info("----------------------------successSchedule-------------------------------------");
		logger.info("----------------------------successSchedule-------------------------------------");
		logger.info("----------------------------successSchedule-------------------------------------");
		logger.info("----------------------------successSchedule-------------------------------------");
		logger.info("----------------------------successSchedule-------------------------------------");
		logger.info("----------------------------successSchedule-------------------------------------");
		logger.info("回调完成通知planUuid:" + planUuid);
		DispatchPlanExample ex = new DispatchPlanExample();
		ex.createCriteria().andPlanUuidEqualTo(planUuid);
		List<DispatchPlan> list = dispatchPlanMapper.selectByExample(ex);
		DispatchPlan sendSMsDispatchPlan = null;
		logger.info("回调完成通知查询结果:" + list.size());
		if (list.size() <= 0) {
			logger.info("回调完成通知查询结果 uuid错误！");
			return false;
		} else {
			sendSMsDispatchPlan = list.get(0);
		}

		boolean checkRes = checkLastNum(list.get(0));
		if (checkRes) {
			// 回调批次拨打结束通知。
			logger.info("回调批次拨打结束通知开始 ");
			ReturnData<SysUser> user = auth.getUserById(list.get(0).getUserId().longValue());
			if (user.getBody() != null) {
				String batchRecordUrl = user.getBody().getBatchRecordUrl();
				if (batchRecordUrl != null && batchRecordUrl != "") {
					JSONObject jsonObject = new JSONObject();
					jsonObject.put("batch_number", list.get(0).getBatchName());
					jsonObject.put("operate", user.getBody().getUsername());
					String sendHttpPost = "";
					try {
						sendHttpPost = HttpClientUtil.doPostJson(batchRecordUrl, jsonObject.toString());
					} catch (Exception e) {
						if (insertThirdInterface(batchRecordUrl, jsonObject)) {
							logger.info("回调错误记录新增成功...");
						}
						logger.error("error", e);
					}
					logger.info("回调批次拨打结束通知结果 :" + sendHttpPost);
				}
			} else {
				logger.info("successSchedule 用户不存在");
			}
		}

		List<String> ids = new ArrayList<>();
		ids.add(list.get(0).getPlanUuid());
		ReturnData<List<CallPlanDetailRecordVO>> callPlanDetailRecord = callPlanDetail.getCallPlanDetailRecord(ids);
		ReturnData<SysUser> user = auth.getUserById(list.get(0).getUserId().longValue());
		if (user.getBody() != null) {
			if (user.getBody().getCallRecordUrl() != null && user.getBody().getCallRecordUrl() != "") {
				logger.info("通话记录通知开始");
				JSONObject jsonObject = new JSONObject();
				jsonObject.put("data", callPlanDetailRecord.getBody());
				boolean insertThirdInterface = insertThirdInterface(user.getBody().getCallRecordUrl(), jsonObject);
				logger.info("通话记录通知结果 :" + insertThirdInterface);
			}
		}

		if (list.size() > 0) {
			DispatchPlan dispatchPlan = list.get(0);
			dispatchPlan.setStatusPlan(Constant.STATUSPLAN_2);// 2计划完成
			// 0不重播非0表示重播次数
			// if (dispatchPlan.getRecall() > 0) {
			// // 获取重播条件
			// String recallParams = dispatchPlan.getRecallParams();
			// ReplayDto replayDto = JSONObject.parseObject(recallParams,
			// ReplayDto.class);
			// // 查询语音记录
			// ReturnData<CallOutPlan> callRecordById =
			// callManagerFeign.getCallRecordById(planUuid);
			//
			// if (callRecordById.getBody() != null) {
			// // 意图
			// if
			// (replayDto.getLabel().contains(callRecordById.getBody().getAccurateIntent()))
			// {
			// if (callRecordById.getBody().getAccurateIntent().equals("F")) {
			// // F类判断挂断类型
			// if
			// (replayDto.getLabelType().contains(callRecordById.getBody().getReason()))
			// {
			// // 创建
			// }
			// } else {
			// // 创建
			// }
			// }
			// }
			// }
			int result = dispatchPlanMapper.updateByExampleSelective(dispatchPlan, ex);
			logger.info("回调完成通知修改结果" + result);

			logger.info("---------------------发送短信------------------------");
			logger.info("---------------------发送短信------------------------");
			logger.info("---------------------发送短信------------------------");
			logger.info("---------------------发送短信------------------------");
			boolean resultmsg = SendSms(sendSMsDispatchPlan, label);
			logger.info("---------------------发送短信------------------------");
			logger.info("---------------------发送短信------------------------");
			logger.info("---------------------发送短信------------------------");
			logger.info("---------------------发送短信------------------------");

			// 判断下一批计算是否还有相同的推送任务，如果没有则设置redis失效时间为0
			List<DispatchPlan> queryAvailableSchedules = queryAvailableSchedules(dispatchPlan.getUserId(), 1,
					dispatchPlan.getLine(), new DispatchPlan(), false);
			if (queryAvailableSchedules.size() <= 0) {
				String key = dispatchPlan.getUserId() + "-" + dispatchPlan.getRobot() + "-" + dispatchPlan.getLine();
				redisUtil.del(key);
				logger.info("当前计划没有下一批任务:" + key + "删除了redis缓存");
			}

		}

		return true;
	}

	/**
	 * 请求短信参数
	 * 
	 * @param url
	 * @param json
	 * @return
	 */
	public static String doPostJson(String url, String json, String Authorization) {
		// 创建Httpclient对象
		CloseableHttpClient httpClient = HttpClients.createDefault();
		CloseableHttpResponse response = null;
		String resultString = "";
		try {
			// 创建Http Post请求
			HttpPost httpPost = new HttpPost(url);
			// httpPost.setEntity(new UrlEncodedFormEntity(pairList, "utf-8"));
			httpPost.setHeader("Authorization", Authorization);
			// 创建请求内容
			StringEntity entity = new StringEntity(json, ContentType.APPLICATION_JSON);
			httpPost.setEntity(entity);
			// 执行http请求
			response = httpClient.execute(httpPost);
			resultString = EntityUtils.toString(response.getEntity(), "utf-8");
		} catch (Exception e) {
			logger.error("调用接口异常！", e);
		} finally {
			try {
				response.close();
			} catch (IOException e) {
				logger.error("关闭连接异常！", e);
			}
		}
		return resultString;
	}

	/**
	 * 发送短信
	 * 
	 * @param sendSMsDispatchPlan
	 * @param label
	 * @return
	 */
	private boolean SendSms(DispatchPlan sendSMsDispatchPlan, String label) {
		UserSmsConfigExample ex = new UserSmsConfigExample();
		ex.createCriteria().andUserIdEqualTo(sendSMsDispatchPlan.getUserId()).andCallResultEqualTo(label);
		List<UserSmsConfig> selectByExample = userSmsConfigMapper.selectByExample(ex);
		UserSmsConfig userConf = null;
		if (selectByExample.size() > 0) {
			sendMsgDto msg = new sendMsgDto();
			userConf = selectByExample.get(0);
			// 手机
			msg.setMobile(sendSMsDispatchPlan.getPhone());
			SmsTunnel tunnel = smsTunnerMapper.selectByPrimaryKey(userConf.getSmsTunnelId());
			// 短信模板
			msg.setTemplateId(userConf.getTemplateId());
			// json 授权信息
			sendMsgDto copyBean = JsonUtils.json2Bean(tunnel.getPlatformConfig(), sendMsgDto.class);

			String date = null;
			try {
				date = getCurrent4Time();
			} catch (ParseException e) {
				logger.error("error", e);
			}
			// author
			// String authorization =
			// Base64MD5Util.decodeData(copyBean.getAccountSID() + "|" + date);
			String auth = copyBean.getAccountSID() + "|" + date;
			String authorization = Base64.getEncoder().encodeToString(auth.getBytes());
			// MD5加密（账户Id + 账户授权令牌 +时间戳)
			String sign = Base64MD5Util.encryption(copyBean.getAccountSID() + copyBean.getAuthToken() + date);
			copyBean.setSign(sign);
			copyBean.setAuthorization(authorization);
			copyBean.setPhone(sendSMsDispatchPlan.getPhone());
			copyBean.setSmsTemplateId(userConf.getSmsTemplateId());
			// 发送到mq
			boolean insertMessMq = msgService.insertMessMq(copyBean);

			// String url = "http://api.ytx.net/" + "201512/sid/" +
			// copyBean.getAccountSID() + "/"
			// + "sms/TemplateSMS.wx?Sign=" + sign;
			// // + "sms/TemplateSMS.wx";
			// JSONObject json = new JSONObject();
			// json.put("action", "templateSms");
			// json.put("mobile", sendSMsDispatchPlan.getPhone());
			// json.put("appid", copyBean.getAppid());
			// json.put("templateId", userConf.getSmsTemplateId());
			// String doPostJson = doPostJson(url, json.toJSONString(),
			// authorization);
			// JSONObject msgRes = JSONObject.parseObject(doPostJson);
			// // String test = "{\"statusCode\": \"0\",\"statusMsg\":
			// // \"提交成功\",\"requestId\": \"20181235962383254861905920103\"}";
			// String statusCode = (String) msgRes.get("statusCode");
			// String statusMsg = (String) msgRes.get("statusMsg");
			// String requestId = (String) msgRes.get("requestId");
			//
			// if (statusCode.equals("0")) {
			// // 发送状态
			// logger.info("发送成功:statusCode:{},statusMsg:{},requestId:{}",
			// statusCode, statusMsg, requestId);
			// // 数据库记录数据
			// } else {
			// logger.info("发送失败:statusCode:{},statusMsg:{},requestId:{}",
			// statusCode, statusMsg, requestId);
			// }
			// SendMsgRecords msgRe = new SendMsgRecords();
			// try {
			// msgRe.setCreateTime(DateUtil.getCurrent4Time());
			// } catch (Exception e) {
			// logger.info("error", e);
			// }
			// msgRe.setPhone(sendSMsDispatchPlan.getPhone());
			// msgRe.setRequestid(requestId);
			// msgRe.setStatuscode(statusCode);
			// msgRe.setStatusmsg(statusMsg);
			// int insert = sendMsgMapper.insert(msgRe);
			// logger.info("-----------------记录短信记录-------------------");

		} else {
			logger.info("当前用户没信息");
		}

		return true;
	}

	public static String getCurrent4Time() throws ParseException {
		SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyyMMddHHmmss");
		return dateFormatter.format(new Date());
	}

	/**
	 * 记录第三方接口记录详情
	 * 
	 * @param url
	 * @param jsonObject
	 * @return
	 */
	private boolean insertThirdInterface(String url, JSONObject jsonObject) {
		ThirdInterfaceRecords record = new ThirdInterfaceRecords();
		try {
			record.setCreateTime(DateUtil.getCurrent4Time());
		} catch (Exception e2) {
			logger.error("error", e2);
		}
		record.setUrl(url);
		record.setParams(jsonObject.toJSONString());
		record.setTimes(Constant.THIRD_INTERFACE_RETRYTIMES);
		logger.info("调用第三方接口异常，记录失败记录");
		int res = thirdInterfaceRecordsMapper.insert(record);
		return res > 0 ? true : false;
	}

	/**
	 * 检查是否完成号码通知了
	 * 
	 * @param dispatchPlan
	 * @return
	 */
	private boolean checkLastNum(DispatchPlan dispatchPlan) {
		// DispatchPlanExample dis = new DispatchPlanExample();
		// dis.createCriteria().andBatchNameEqualTo(dispatchPlan.getBatchName())
		// .andStatusPlanEqualTo(Constant.STATUSPLAN_2);
		// int countByExample = dispatchPlanMapper.countByExample(dis);
		// if (countByExample <= 0) {
		// return true;
		// } else {
		// return false;
		// }
		return true;
	}

	public static String getTimeByMinute(int minute) {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.MINUTE, minute);
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(calendar.getTime());
	}

	@Override
	public boolean dispatchPlanBatch(DispatchPlanBatch dispatchPlanBatch) {
		int insert = dispatchPlanBatchMapper.insert(dispatchPlanBatch);
		return insert > 0 ? true : false;
	}

	@Override
	public Page<DispatchPlan> queryDispatchPlanByBatchId(Integer batchId, int pagenum, int pagesize) {
		Page<DispatchPlan> page = new Page<>();
		page.setPageNo(pagenum);
		page.setPageSize((pagesize));
		DispatchPlanExample example = new DispatchPlanExample();
		example.setLimitStart((pagenum - 1) * pagesize);
		example.setLimitEnd(pagesize);
		example.createCriteria().andBatchIdEqualTo(batchId).andIsDelEqualTo(Constant.IS_DEL_0);
		List<DispatchPlan> selectByExample = dispatchPlanMapper.selectByExample(example);
		int count = dispatchPlanMapper.countByExample(example);
		page.setRecords(selectByExample);
		page.setTotal(count);
		return page;
	}

	public Page<DispatchPlan> queryDispatchPlanByParams(String phone, String planStatus, String startTime,
			String endTime, Integer batchId, String replayType, int pagenum, int pagesize, Long userId,
			boolean isSuperAdmin, Integer selectUserId, String robotName) {
		Page<DispatchPlan> page = new Page<>();
		page.setPageNo(pagenum);
		page.setPageSize((pagesize));
		DispatchPlanExample example = new DispatchPlanExample();
		example.setLimitStart((pagenum - 1) * pagesize);
		example.setLimitEnd(pagesize);
		example.setOrderByClause("`gmt_create` DESC");
		Criteria createCriteria = example.createCriteria();
		if (phone != null && phone != "") {
			createCriteria.andPhoneEqualTo(phone);
		}

		if (planStatus != null && planStatus != "") {
			List<Integer> ids = new ArrayList<>();
			if (planStatus.contains(",")) {
				String[] split = planStatus.split(",");
				for (String sp : split) {
					ids.add(Integer.valueOf(sp));
				}
				createCriteria.andStatusPlanIn(ids);
			} else {
				createCriteria.andStatusPlanEqualTo(Integer.valueOf(planStatus));
			}
		}
		if (startTime != null && startTime != "" && endTime != null && endTime != "") {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			try {
				createCriteria.andGmtCreateBetween(new Timestamp(sdf.parse(startTime).getTime()),
						new Timestamp(sdf.parse(endTime).getTime()));
			} catch (ParseException e) {
				logger.error("error", e);
			}
		}

		if (batchId != null && batchId != 0) {
			createCriteria.andBatchIdEqualTo(batchId);
		}

		if (replayType != null && replayType != "") {
			List<Integer> ids = new ArrayList<>();
			if (replayType.contains(",")) {
				String[] split = replayType.split(",");
				for (String sp : split) {
					ids.add(Integer.valueOf(sp));
				}
				createCriteria.andReplayTypeIn(ids);
			} else {
				createCriteria.andReplayTypeEqualTo(Integer.valueOf(replayType));
			}
		}

		if (!isSuperAdmin) {
			createCriteria.andUserIdEqualTo(userId.intValue());
		} else {
			// 超级用户
			if (selectUserId != null) {
				createCriteria.andUserIdEqualTo(selectUserId.intValue());
			}
			if (robotName != "" && robotName != null) {
				createCriteria.andRobotEqualTo(robotName);
			}
		}
		createCriteria.andIsDelEqualTo(Constant.IS_DEL_0);
		List<DispatchPlan> selectByExample = dispatchPlanMapper.selectByExample(example);

		// 转换userName
		for (DispatchPlan dis : selectByExample) {
			ReturnData<SysUser> user = auth.getUserById(Long.valueOf(dis.getUserId()));
			if (user.getBody() != null) {
				dis.setUserName(user.getBody().getUsername());
			}
		}

		// 如果是超级用户可以查看全部数据
		if (isSuperAdmin) {
			for (DispatchPlan dis : selectByExample) {
				if (dis.getPhone().length() <= 7) {
					continue;
				}
				if (userId == dis.getUserId().longValue()) {
					continue;
				}
				String phoneNumber = dis.getPhone().substring(0, 3) + "****"
						+ dis.getPhone().substring(7, dis.getPhone().length());
				dis.setPhone(phoneNumber);
			}
		}

		int count = dispatchPlanMapper.countByExample(example);
		page.setRecords(selectByExample);
		page.setTotal(count);
		return page;
	}

	// private void getBatchNames(List<DispatchPlan> selectByExample) {
	// DispatchPlanBatchExample ex = new DispatchPlanBatchExample();
	// List<Integer> ids = new ArrayList<>();
	// for (DispatchPlan dispatchPlan : selectByExample) {
	// ids.add(dispatchPlan.getBatchId());
	// }
	// ex.createCriteria().andIdIn(ids);
	//
	// if (ids.size() > 0) {
	// List<DispatchPlanBatch> Batch =
	// dispatchPlanBatchMapper.selectByExample(ex);
	//
	// for (DispatchPlanBatch batchBean : Batch) {
	// for (DispatchPlan plan : selectByExample) {
	// if (batchBean.getId().equals(plan.getBatchId())) {
	// plan.setBatchName(batchBean.getName());
	// }
	// }
	// }
	// }
	// }

	@Override
	public List<LineConcurrent> outLineinfos(String userId) {
		DispatchPlanExample ex = new DispatchPlanExample();
		DispatchPlan dis = new DispatchPlan();
		dis.setCallHour("12");
		// dis.setStatusPlan(statusPlan);
		List<DispatchPlan> selectByCallHour = dispatchPlanMapper.selectByCallHour(dis);
		// ReturnData<List<LineConcurrent>> outLineinfos =
		// callManagerFeign.getLineInfos(userId);
		// return outLineinfos.getBody();
		return null;
	}

	@Override
	public List<DispatchPlan> queryAvailableSchedules(Integer userId, int requestCount, int lineId,
			DispatchPlan isSuccess, boolean flag) {
		logger.info("----------------------------queryAvailableSchedules-------------------------------------");
		logger.info("-----------------------------------------------------------------------------------------");
		logger.info("---------------------------------------------------------------------------------------- ");
		logger.info("-----------------------------------------------------------------------------------------");
		logger.info("---------------------------------------------------------------------------------------- ");
		logger.info("-----------------------------------------------------------------------------------------");
		logger.info("-----------------------------------------------------------------------------------------");
		logger.info("---------------------------------------------------------------------------------------- ");
		DispatchPlanExample ex = new DispatchPlanExample();
		Date d = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String dateNowStr = sdf.format(d);
		Calendar now = Calendar.getInstance();
		int hour = now.get(Calendar.HOUR_OF_DAY);

		// 根据日期查询号码
		ex.createCriteria().andCallDataEqualTo(Integer.valueOf(dateNowStr)).andIsDelEqualTo(Constant.IS_DEL_0)
				.andStatusPlanEqualTo(Constant.STATUSPLAN_1).andUserIdEqualTo(userId).andLineEqualTo(lineId)
				.andStatusSyncEqualTo(Constant.STATUS_SYNC_0);
		DispatchPlan dis = new DispatchPlan();
		dis.setCallData(Integer.valueOf(dateNowStr));
		dis.setIsDel(Constant.IS_DEL_0);
		dis.setStatusPlan(Constant.STATUSPLAN_1);
		dis.setUserId(userId);
		dis.setLine(lineId);
		dis.setCallHour(String.valueOf(hour));
		dis.setStatusSync(Constant.STATUS_SYNC_0);
		dis.setFlag(Constant.IS_FLAG_2);
		List<String> list = new ArrayList<>();
		Object object2 = redisUtil.get("robotId");
		if (redisUtil.get("robotId") != null) {
			logger.info("当前模板升级中，接口 queryAvailableSchedules 对应模板查不到数据，" + redisUtil.get("robotId"));
			String object = (String) redisUtil.get("robotId");
			String[] split = object.split(",");
			for (String str : split) {
				list.add(str);
			}
		}
		dis.setRobotIds(list);
		if (requestCount != 0) {
			dis.setLimitStart(0);
			dis.setLimitEnd(requestCount);
		}
		List<DispatchPlan> phones = dispatchPlanMapper.selectByCallHour(dis);
		// 同步状态;0未同步1已同步
		List<DispatchPlan> updateStatus = new ArrayList<>();

		for (DispatchPlan dispatchPlan : phones) {
			dispatchPlan.setStatusSync(Constant.STATUS_SYNC_1);
			updateStatus.add(dispatchPlan);
			// dispatchPlanMapper.updateByPrimaryKeySelective(dispatchPlan);
		}
		if (flag) {
			// 批量修改状态
			if (updateStatus.size() > 0) {
				for (DispatchPlan dispatchPlan : phones) {
					dispatchPlan.setStatusSync(Constant.STATUS_SYNC_1);
					DispatchPlanExample ex1 = new DispatchPlanExample();
					// 为了防止并发
					ex1.createCriteria().andPlanUuidEqualTo(dispatchPlan.getPlanUuid())
							.andStatusSyncEqualTo(Constant.STATUS_SYNC_0);
					int result = dispatchPlanMapper.updateByExampleSelective(dispatchPlan, ex1);
					if (result <= 0) {
						return queryAvailableSchedules(userId, requestCount, lineId, isSuccess, true);
					}
				}
				// int res =
				// dispatchPlanMapper.updateDispatchPlanList(updateStatus);
				// 判断当前任务是否查询完毕
				// int count = dispatchPlanMapper.countByExample(ex);
				// if (count <= requestCount) {
				// isSuccess.setSuccess(false);
				// }
			}
		}
		return phones;
	}

	@Override
	public List<DispatchPlan> selectPhoneByDate() {
		Date d = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String dateNowStr = sdf.format(d);
		Calendar now = Calendar.getInstance();
		int hour = now.get(Calendar.HOUR_OF_DAY);

		DispatchPlan dis = new DispatchPlan();
		dis.setCallData(Integer.valueOf(dateNowStr));
		dis.setCallHour(String.valueOf(hour));
		dis.setIsDel(Constant.IS_DEL_0);
		dis.setStatusPlan(Constant.STATUSPLAN_1);
		dis.setStatusSync(Constant.STATUS_SYNC_0);
		// flag
		dis.setFlag(Constant.IS_FLAG_0);
		// 设置分页
		dis.setLimitStart(0);
		dis.setLimitEnd(1000);
		List<DispatchPlan> phones = dispatchPlanMapper.selectByCallHour(dis);
		return phones;
	}

	@Override
	public boolean deleteSchedule(String planUuid) {
		DispatchPlan dis = new DispatchPlan();
		dis.setIsDel(Constant.IS_DEL_1);
		DispatchPlanExample ex = new DispatchPlanExample();
		ex.createCriteria().andPlanUuidEqualTo(planUuid);
		int result = dispatchPlanMapper.updateByExampleSelective(dis, ex);
		return result > 0 ? true : false;
	}

	@Override
	public boolean batchUpdatePlans(IdsDto[] dto) {
		int result = 0;
		for (IdsDto bean : dto) {
			// DispatchPlanExample ex1 = new DispatchPlanExample();
			// ex1.createCriteria().andPlanUuidEqualTo(bean.getPlanuuid());
			// List<DispatchPlan> selectByExample =
			// dispatchPlanMapper.selectByExample(ex1);
			// DispatchPlan dispatchPlan = null;
			// if (selectByExample.size() > 0) {
			// dispatchPlan = selectByExample.get(0);
			// }
			// if (bean.getStatus().equals(Constant.STATUSPLAN_1)
			// && dispatchPlan.getStatusPlan().equals(Constant.STATUSPLAN_4)) {
			// logger.info("当前状态有问题...");
			// continue;
			// }
			//
			// // 停止之后不能暂停
			// if (bean.getStatus().equals(Constant.STATUSPLAN_4)
			// && dispatchPlan.getStatusPlan().equals(Constant.STATUSPLAN_3)) {
			// logger.info("当前状态有问题...");
			// continue;
			// }
			DispatchPlan dis = new DispatchPlan();
			dis.setStatusPlan(bean.getStatus().intValue());
			try {
				dis.setGmtModified(DateUtil.getCurrent4Time());
			} catch (Exception e) {
				logger.error("error", e);
			}

			DispatchPlanExample ex = new DispatchPlanExample();
			ex.createCriteria().andPlanUuidEqualTo(bean.getPlanuuid());
			result = dispatchPlanMapper.updateByExampleSelective(dis, ex);
		}
		return result > 0 ? true : false;
	}

	/**
	 * 停止之后不能暂停 不能恢复
	 */
	@Override
	public MessageDto operationAllPlanByBatchId(Integer batchId, String status, Long userId) {
		Map<Integer, String> map = new HashMap<>();
		map.put(1, "计划中状态");
		map.put(2, "完成状态");
		map.put(3, "暂停状态");
		map.put(4, "停止状态");
		MessageDto result = new MessageDto();
		if (batchId != 0) {
			DispatchPlanBatch dispatchPlanBatch = dispatchPlanBatchMapper.selectByPrimaryKey(batchId);
			DispatchPlan dispatchPlan = new DispatchPlan();
			// dispatchPlan.setBatchId(dispatchPlanBatch.getId());

			// 根据批次号查询一条数据，判断到底是什么
			DispatchPlanExample dis = new DispatchPlanExample();
			dis.createCriteria().andBatchIdEqualTo(dispatchPlanBatch.getId())
					.andStatusPlanNotEqualTo(Constant.STATUSPLAN_2);
			List<DispatchPlan> selectByExample = dispatchPlanMapper.selectByExample(dis);
			DispatchPlan resultPlan = new DispatchPlan();

			if (selectByExample.size() > 0) {
				resultPlan = selectByExample.get(0);
				boolean res = checkStatus(status, resultPlan);
				if (!res) {
					result.setResult(false);
					result.setMsg("当前批次号码状态处于" + map.get(resultPlan.getStatusPlan()) + "不能进行此状态操作");
					return result;
				}
				// 0未计划1计划中2计划完成3暂停计划4停止计划
				dispatchPlan.setStatusPlan(Integer.valueOf(status));
				DispatchPlanExample ex1 = new DispatchPlanExample();
				if (status.equals("1")) {
					ex1.createCriteria().andIsDelEqualTo(Constant.IS_DEL_0).andBatchIdEqualTo(dispatchPlanBatch.getId())
							.andStatusPlanNotEqualTo(Constant.STATUSPLAN_2)
							.andStatusPlanNotEqualTo(Constant.STATUSPLAN_4);
				} else {
					ex1.createCriteria().andIsDelEqualTo(Constant.IS_DEL_0).andBatchIdEqualTo(dispatchPlanBatch.getId())
							.andStatusPlanNotEqualTo(Constant.STATUSPLAN_2);
				}
				dispatchPlanMapper.updateByExampleSelective(dispatchPlan, ex1);
			}

		} else {
			DispatchPlan dis = new DispatchPlan();
			DispatchPlanExample example = new DispatchPlanExample();
			// 根据用户id来查询
			if (status.equals("1")) {
				example.createCriteria().andUserIdEqualTo(userId.intValue()).andIsDelEqualTo(Constant.IS_DEL_0)
						.andStatusPlanNotEqualTo(Constant.STATUSPLAN_2).andStatusPlanNotEqualTo(Constant.STATUSPLAN_4);
			} else {
				example.createCriteria().andUserIdEqualTo(userId.intValue()).andIsDelEqualTo(Constant.IS_DEL_0)
						.andStatusPlanNotEqualTo(Constant.STATUSPLAN_2);
			}
			List<DispatchPlan> selectByExample = dispatchPlanMapper.selectByExample(example);
			List<DispatchPlan> succ = new ArrayList<>();
			List<String> ids = new ArrayList<>();
			for (DispatchPlan dispatchPlan : selectByExample) {
				boolean res = checkStatus(status, dispatchPlan);
				if (!res) {
					continue;
				}
				ids.add(dispatchPlan.getPlanUuid());
				succ.add(dispatchPlan);
			}
			dis.setStatusPlan(Integer.valueOf(status));
			List<List<String>> averageAssign = averageAssign(ids, 10);
			for (List<String> list : averageAssign) {
				if (list.size() > 0) {
					dispatchPlanMapper.updateDispatchPlanListByStatus(list, status);
				}
			}
		}
		return result;
	}

	public static <T> List<List<T>> averageAssign(List<T> source, int n) {
		List<List<T>> result = new ArrayList<List<T>>();
		int remaider = source.size() % n; // (先计算出余数)
		int number = source.size() / n; // 然后是商
		int offset = 0;// 偏移量
		for (int i = 0; i < n; i++) {
			List<T> value = null;
			if (remaider > 0) {
				value = source.subList(i * number + offset, (i + 1) * number + offset + 1);
				remaider--;
				offset++;
			} else {
				value = source.subList(i * number + offset, (i + 1) * number + offset);
			}
			result.add(value);
		}
		return result;
	}

	private boolean checkStatus(String status, DispatchPlan dispatchPlan) {
		// 停止之后不能暂停 不能恢复
		if (Integer.valueOf(status) == Constant.STATUSPLAN_3
				&& dispatchPlan.getStatusPlan().equals(Constant.STATUSPLAN_4)) {
			return false;
		}
		
		if (Integer.valueOf(status) == Constant.STATUSPLAN_4
				&& dispatchPlan.getStatusPlan().equals(Constant.STATUSPLAN_3)) {
			return false;
		}
		
		if (Integer.valueOf(status) == Constant.STATUSPLAN_4
				&& dispatchPlan.getStatusPlan().equals(Constant.STATUSPLAN_2)) {
			return false;
		}

		if (Integer.valueOf(status) == Constant.STATUSPLAN_1
				&& dispatchPlan.getStatusPlan().equals(Constant.STATUSPLAN_4)) {
			return false;
		}
		// if (Integer.valueOf(status) == Constant.STATUSPLAN_1
		// && dispatchPlan.getStatusPlan().equals(Constant.STATUSPLAN_3)) {
		// return false;
		// }
		return true;
	}

	@Override
	public boolean batchDeletePlans(IdsDto[] dto) {
		int result = 0;
		for (IdsDto bean : dto) {
			DispatchPlan dispatchPlan = new DispatchPlan();
			dispatchPlan.setIsDel(Constant.IS_DEL_1);
			DispatchPlanExample ex = new DispatchPlanExample();
			ex.createCriteria().andPlanUuidEqualTo(bean.getPlanuuid());
			result = dispatchPlanMapper.updateByExampleSelective(dispatchPlan, ex);
			DispatchHourExample exHour = new DispatchHourExample();
			exHour.createCriteria().andDispatchIdEqualTo(bean.getPlanuuid());
			result = dispatchHourMapper.deleteByExample(exHour);
		}
		return result > 0 ? true : false;
	}

	@Override
	public List<DispatchPlanBatch> queryDispatchPlanBatch(Long userId, Boolean isSuperAdmin) {
		DispatchPlanBatchExample example = new DispatchPlanBatchExample();
		com.guiji.dispatch.dao.entity.DispatchPlanBatchExample.Criteria createCriteria = example.createCriteria();
		if (!isSuperAdmin) {
			createCriteria.andUserIdEqualTo(userId.intValue());
		}
		return dispatchPlanBatchMapper.selectByExample(example);
	}

	@Override
	public boolean updateReplayDate() {
		Date d = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String dateNowStr = sdf.format(d);

		DispatchPlan dis = new DispatchPlan();
		dis.setCallData(Integer.valueOf(dateNowStr));
		dis.setStatusPlan(Constant.STATUSPLAN_1);
		dis.setStatusSync(Constant.STATUS_SYNC_0);
		dis.setClean(Constant.IS_CLEAN_0);
		try {
			dis.setGmtModified(DateUtil.getCurrent4Time());
		} catch (Exception e) {
			logger.info("error", e);
		}
		DispatchPlanExample ex = new DispatchPlanExample();
		ex.createCriteria().andCleanEqualTo(Constant.IS_CLEAN_1);
		int result = dispatchPlanMapper.updateByExampleSelective(dis, ex);
		return result > 0 ? true : false;
	}

	@Override
	public boolean checkBatchId(String name) {
		DispatchPlanBatchExample ex = new DispatchPlanBatchExample();
		ex.createCriteria().andNameEqualTo(name);
		int count = dispatchPlanBatchMapper.countByExample(ex);
		return count > 0 ? true : false;
	}

	@Override
	public boolean batchUpdateFlag(List<DispatchPlan> list, String flag) {
		List<String> ids = new ArrayList<>();

		for (DispatchPlan dispatchPlan : list) {
			ids.add(dispatchPlan.getPlanUuid());
		}
		int result = -1;
		// for (String id : ids) {
		// DispatchPlan dis = new DispatchPlan();
		// DispatchPlanExample ex = new DispatchPlanExample();
		// ex.createCriteria().andPlanUuidEqualTo(id);
		// dis.setFlag(flag);
		// result = dispatchPlanMapper.updateByExampleSelective(dis, ex);
		// }
		// Map<String, Object> = new
		// map.put("flag",flag);
		// map.put("list", ids);
		result = dispatchPlanMapper.updateDispatchPlanList(ids, flag);
		return result > 0 ? true : false;
	}

	@Override
	public List<DispatchPlan> selectPhoneByDateAndFlag(String flag) {
		Date d = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String dateNowStr = sdf.format(d);
		Calendar now = Calendar.getInstance();
		int hour = now.get(Calendar.HOUR_OF_DAY);

		DispatchPlan dis = new DispatchPlan();
		dis.setCallData(Integer.valueOf(dateNowStr));
		dis.setCallHour(String.valueOf(hour));
		dis.setIsDel(Constant.IS_DEL_0);
		dis.setStatusPlan(Constant.STATUSPLAN_1);
		dis.setStatusSync(Constant.STATUS_SYNC_0);
		dis.setFlag(flag);
		dis.setLimitStart(0);
		dis.setLimitEnd(1000);
		List<DispatchPlan> phones = dispatchPlanMapper.selectByCallHour(dis);
		return phones;
	}

	@Override
	public int getcall4BatchName(String batchName, Integer status) {
		DispatchPlanExample ex = new DispatchPlanExample();
		ex.createCriteria().andBatchNameEqualTo(batchName).andStatusPlanEqualTo(status)
				.andIsDelEqualTo(Constant.IS_DEL_0);
		int result = dispatchPlanMapper.countByExample(ex);
		return result;
	}

	@Override
	public Page<DispatchPlan> queryDispatchPlan(String batchName, int pagenum, int pagesize) {
		Page<DispatchPlan> page = new Page<>();
		page.setPageNo(pagenum);
		page.setPageSize((pagesize));
		DispatchPlanExample example = new DispatchPlanExample();
		example.setLimitStart((pagenum - 1) * pagesize);
		example.setLimitEnd(pagesize);
		// example.setOrderByClause("`gmt_create` DESC");
		example.createCriteria().andBatchNameEqualTo(batchName);
		List<DispatchPlan> selectByExample = dispatchPlanMapper.selectByExample(example);
		int countByExample = dispatchPlanMapper.countByExample(example);

		page.setRecords(selectByExample);
		page.setTotal(countByExample);
		return page;
	}

	@Override
	public JSONObject queryDispatchPlanByPhoens(String phone, String batchName, int pagenum, int pagesize) {
		JSONObject jsonObject = new JSONObject();
		Page<DispatchPlan> page = new Page<>();
		page.setPageNo(pagenum);
		page.setPageSize((pagesize));
		DispatchPlanExample example = new DispatchPlanExample();
		Criteria createCriteria = example.createCriteria();
		if (phone != null && phone != "") {
			createCriteria.andPhoneEqualTo(phone);
		}

		if (batchName != null && batchName != "") {
			createCriteria.andBatchNameEqualTo(batchName);
		}
		example.setLimitStart((pagenum - 1) * pagesize);
		example.setLimitEnd(pagesize);

		List<DispatchPlan> selectByExample = dispatchPlanMapper.selectByExample(example);
		int countByExample = dispatchPlanMapper.countByExample(example);
		page.setRecords(selectByExample);
		page.setTotal(countByExample);

		List<String> ids = new ArrayList<>();
		for (DispatchPlan dis : selectByExample) {
			ids.add(dis.getPlanUuid());
		}
		ReturnData<List<CallPlanDetailRecordVO>> callPlanDetailRecord = callPlanDetail.getCallPlanDetailRecord(ids);
		jsonObject.put("data", callPlanDetailRecord.getBody());
		return jsonObject;
	}

	@Override
	public JSONObject getServiceStatistics(Long userId, Boolean isSuperAdmin) {
		JSONObject jsonObject = new JSONObject();
		// 累计任务号码总数，累计拨打号码总数，最后计划日期，最后拨打日期，累计服务天数
		int countNums = dispatchPlanMapper.countByExample(new DispatchPlanExample());
		DispatchPlanExample ex = new DispatchPlanExample();
		Criteria andStatusPlanEqualTo = ex.createCriteria().andIsDelEqualTo(Constant.IS_DEL_0)
				.andStatusPlanEqualTo(Constant.STATUSPLAN_1);
		if (!isSuperAdmin) {
			andStatusPlanEqualTo.andUserIdEqualTo(userId.intValue());
		}
		int calledNums = dispatchPlanMapper.countByExample(ex);

		DispatchPlanExample ex1 = new DispatchPlanExample();
		Criteria andStatusPlanEqualTo2 = ex1.createCriteria().andIsDelEqualTo(Constant.IS_DEL_0)
				.andStatusPlanEqualTo(Constant.STATUSPLAN_1);
		if (!isSuperAdmin) {
			andStatusPlanEqualTo2.andUserIdEqualTo(userId.intValue());
		}
		ex1.setOrderByClause("`gmt_create` DESC");
		List<DispatchPlan> selectByExample = dispatchPlanMapper.selectByExample(ex1);
		DispatchPlan dis = new DispatchPlan();

		if (selectByExample.size() > 0) {
			dis = selectByExample.get(selectByExample.size() - 1);
		}

		DispatchPlanExample ex2 = new DispatchPlanExample();
		Criteria andStatusPlanEqualTo3 = ex2.createCriteria().andIsDelEqualTo(Constant.IS_DEL_0)
				.andStatusPlanEqualTo(Constant.STATUSPLAN_2);
		if (!isSuperAdmin) {
			andStatusPlanEqualTo3.andUserIdEqualTo(userId.intValue());
		}

		ex2.setOrderByClause("`gmt_create` DESC");
		List<DispatchPlan> selectByExample2 = dispatchPlanMapper.selectByExample(ex2);

		DispatchPlan dis1 = new DispatchPlan();

		if (selectByExample2.size() > 0) {
			dis1 = selectByExample2.get(0);
		}

		ReturnData<SysUser> user = auth.getUserById(userId);
		if (user.getBody() != null) {
			SysUser body = user.getBody();
			long stateTimeLong = new Date().getTime();
			long endTimeLong = body.getCreateTime().getTime();
			// 结束时间-开始时间 = 天数
			long day = (stateTimeLong - endTimeLong) / (24 * 60 * 60 * 1000);
			jsonObject.put("useDay", day);
		}

		jsonObject.put("countNums", countNums);
		jsonObject.put("noCallNums", calledNums);
		jsonObject.put("lastPlanDate", dis.getCallData());
		jsonObject.put("lastCalledDate", dis1.getCallData());
		return jsonObject;
	}

	@Override
	public JSONObject getServiceStatistics(Long userId, String startTime, String endTime, Boolean isSuperAdmin) {
		DispatchPlanExample ex = new DispatchPlanExample();
		Criteria createCriteria = ex.createCriteria();
		if (startTime != null && startTime != "" && endTime != null && endTime != "") {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			try {
				createCriteria.andGmtCreateBetween(new Timestamp(sdf.parse(startTime).getTime()),
						new Timestamp(sdf.parse(endTime).getTime()));
				createCriteria.andIsDelEqualTo(Constant.IS_DEL_0);
			} catch (ParseException e) {
				logger.error("error", e);
			}
		}
		if (!isSuperAdmin) {
			createCriteria.andUserIdEqualTo(userId.intValue());
		}

		DispatchPlanExample ex1 = new DispatchPlanExample();
		Criteria createCriteria1 = ex1.createCriteria();
		ex1.createCriteria().andStatusPlanEqualTo(Constant.STATUSPLAN_2).andUserIdEqualTo(userId.intValue());
		if (startTime != null && startTime != "" && endTime != null && endTime != "") {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			try {
				createCriteria1.andGmtCreateBetween(new Timestamp(sdf.parse(startTime).getTime()),
						new Timestamp(sdf.parse(endTime).getTime()));
				createCriteria1.andIsDelEqualTo(Constant.IS_DEL_0).andStatusPlanEqualTo(Constant.STATUSPLAN_2);
			} catch (ParseException e) {
				logger.error("error", e);
			}
		}
		if (!isSuperAdmin) {
			createCriteria1.andUserIdEqualTo(userId.intValue());
		}

		int taskCount = dispatchPlanMapper.countByExample(ex);
		int calledCount = dispatchPlanMapper.countByExample(ex1);
		int Batchcount = dispatchPlanBatchMapper.countByExample(new DispatchPlanBatchExample());
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("taskCount", taskCount);
		jsonObject.put("calledNums", calledCount);
		jsonObject.put("Batchcount", Batchcount);
		return jsonObject;
	}

	@Override
	public boolean insertDispatchPlanList(List<DispatchPlan> list) {
		int result = dispatchPlanMapper.insertDispatchPlanList(list);
		return result > 0 ? true : false;
	}

	public boolean checkBalckList(DispatchPlan dispatchPlan) {
		if (redisUtil.get("blackList") != null) {
			Map<String, BlackList> base = (Map) redisUtil.get("blackList");
			if (base.containsKey(dispatchPlan.getPhone())) {
				return true;
			} else {
				return false;
			}
		}
		return false;
	}

	// public static void main(String[] args) throws ParseException {
	// String test = "{\"statusCode\": \"0\",\"statusMsg\":
	// \"提交成功\",\"requestId\": \"20181235962383254861905920103\"}";
	//
	// JSONObject parseObject = JSONObject.parseObject(test);
	// Object object = parseObject.get("statusCode");
	// System.out.println(object);
	//
	// String date = getCurrent4Time();
	// // author
	// String str = "f94481a09e1c4c4d9a40887a71dc299d" + "|" + date;
	// String encodedString =
	// Base64.getEncoder().encodeToString(str.getBytes());
	//
	// // String authorization = Base64MD5Util.decodeData();
	// System.out.println(encodedString);
	// // MD5加密（账户Id + 账户授权令牌 +时间戳)
	// String sign = Base64MD5Util
	// .encryption("f94481a09e1c4c4d9a40887a71dc299d" +
	// "c9e869e1fb8c49188f5efb19f9725f57" + date);
	// String upperCase = sign.toUpperCase();
	//
	// UserSmsConfig conf = new UserSmsConfig();
	//
	// String sss = "{\"action\": \"templateSms\",\"mobile\":
	// \"13911281234\",\"appid\":
	// \"ce4063be6e334f0387fa6d987e16a87c\",\"templateId\": \"1\"}";
	// System.out.println(sss.length());
	// }

	@Override
	public void test(DispatchPlan sendSMsDispatchPlan, String label) {
		SendSms(sendSMsDispatchPlan, label);
	}

}
