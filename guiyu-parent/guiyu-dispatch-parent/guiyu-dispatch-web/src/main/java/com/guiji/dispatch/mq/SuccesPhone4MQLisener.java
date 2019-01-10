//package com.guiji.dispatch.mq;
//
//import java.text.ParseException;
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.Base64;
//import java.util.Date;
//import java.util.List;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.amqp.core.Message;
//import org.springframework.amqp.rabbit.annotation.RabbitHandler;
//import org.springframework.amqp.rabbit.annotation.RabbitListener;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//import com.alibaba.fastjson.JSONObject;
//import com.guiji.auth.api.IAuth;
//import com.guiji.ccmanager.api.ICallPlanDetail;
//import com.guiji.ccmanager.vo.CallPlanDetailRecordVO;
//import com.guiji.component.result.Result.ReturnData;
//import com.guiji.dispatch.bean.MQSuccPhoneDto;
//import com.guiji.dispatch.bean.sendMsgDto;
//import com.guiji.dispatch.dao.DispatchPlanMapper;
//import com.guiji.dispatch.dao.SmsTunnelMapper;
//import com.guiji.dispatch.dao.ThirdInterfaceRecordsMapper;
//import com.guiji.dispatch.dao.UserSmsConfigMapper;
//import com.guiji.dispatch.dao.entity.DispatchPlan;
//import com.guiji.dispatch.dao.entity.DispatchPlanExample;
//import com.guiji.dispatch.dao.entity.SmsTunnel;
//import com.guiji.dispatch.dao.entity.ThirdInterfaceRecords;
//import com.guiji.dispatch.dao.entity.UserSmsConfig;
//import com.guiji.dispatch.dao.entity.UserSmsConfigExample;
//import com.guiji.dispatch.service.IMessageService;
//import com.guiji.dispatch.util.Base64MD5Util;
//import com.guiji.dispatch.util.Constant;
//import com.guiji.user.dao.entity.SysUser;
//import com.guiji.utils.DateUtil;
//import com.guiji.utils.HttpClientUtil;
//import com.guiji.utils.JsonUtils;
//import com.rabbitmq.client.Channel;
//
//@Component
//@RabbitListener(queues = "dispatch.SuccessPhoneMQ")
//public class SuccesPhone4MQLisener {
//	private static Logger logger = LoggerFactory.getLogger(ModularMqListener.class);
//
//	@Autowired
//	private DispatchPlanMapper dispatchPlanMapper;
//
//	@Autowired
//	private IAuth auth;
//
//	@Autowired
//	private ThirdInterfaceRecordsMapper thirdInterfaceRecordsMapper;
//
//	@Autowired
//	private SmsTunnelMapper smsTunnerMapper;
//
//	@Autowired
//	private ICallPlanDetail callPlanDetail;
//
//	@Autowired
//	private UserSmsConfigMapper userSmsConfigMapper;
//
//	@Autowired
//	private IMessageService msgService;
//
//	@RabbitHandler
//	public void process(String message, Channel channel, Message message2) {
//		MQSuccPhoneDto mqSuccPhoneDto = JsonUtils.json2Bean(message, MQSuccPhoneDto.class);
//		logger.info("当前队列任务接受的uuid："+ mqSuccPhoneDto.getPlanuuid());
//		DispatchPlanExample ex = new DispatchPlanExample();
//		ex.createCriteria().andPlanUuidEqualTo(mqSuccPhoneDto.getPlanuuid());
//		List<DispatchPlan> list = dispatchPlanMapper.selectByExample(ex);
//		DispatchPlan sendSMsDispatchPlan = null;
//		if (list.size() <= 0) {
//			logger.info("当前队列任务回调 uuid错误！");
//			return;
//		} else {
//			sendSMsDispatchPlan = list.get(0);
//		}
//		// 回调批次拨打结束通知。
//		ReturnData<SysUser> user = auth.getUserById(list.get(0).getUserId().longValue());
//		if (user.getBody() != null) {
//			String batchRecordUrl = user.getBody().getBatchRecordUrl();
//			if (batchRecordUrl != null && batchRecordUrl != "") {
//				JSONObject jsonObject = new JSONObject();
//				jsonObject.put("batch_number", list.get(0).getBatchName());
//				jsonObject.put("operate", user.getBody().getUsername());
//				String sendHttpPost = "";
//				try {
//					sendHttpPost = HttpClientUtil.doPostJson(batchRecordUrl, jsonObject.toString());
//				} catch (Exception e) {
//					if (insertThirdInterface(batchRecordUrl, jsonObject)) {
//						logger.info("回调错误记录新增成功...");
//					}
//					logger.error("error", e);
//				}
//				logger.info("回调批次拨打结束通知结果 :" + sendHttpPost);
//			}
//
//			if (user.getBody().getCallRecordUrl() != null && user.getBody().getCallRecordUrl() != "") {
//				logger.info("通话记录通知开始");
//				JSONObject jsonObject = new JSONObject();
//				List<String> ids = new ArrayList<>();
//				ids.add(list.get(0).getPlanUuid());
//				ReturnData<List<CallPlanDetailRecordVO>> callPlanDetailRecord = callPlanDetail
//						.getCallPlanDetailRecord(ids);
//				logger.info("当前回调通话记录调用接口结果 "+ callPlanDetailRecord.success);
//				logger.info("当前回调通话记录调用接口结果 "+ callPlanDetailRecord.msg);
//				jsonObject.put("data", callPlanDetailRecord.getBody());
//				boolean insertThirdInterface = insertThirdInterface(user.getBody().getCallRecordUrl(), jsonObject);
//				logger.info("当前回调通话记录调用接口通话记录通知结果 :" + insertThirdInterface);
//			}
//
//		} else {
//			logger.info("当前队列任务回调  用户不存在");
//		}
//
//		if (list.size() > 0) {
//			DispatchPlan dispatchPlan = list.get(0);
//			dispatchPlan.setStatusPlan(Constant.STATUSPLAN_2);// 2计划完成
//			// 0不重播非0表示重播次数
//			// if (dispatchPlan.getRecall() > 0) {
//			// // 获取重播条件
//			// String recallParams = dispatchPlan.getRecallParams();
//			// ReplayDto replayDto = JSONObject.parseObject(recallParams,
//			// ReplayDto.class);
//			// // 查询语音记录
//			// ReturnData<CallOutPlan> callRecordById =
//			// callManagerFeign.getCallRecordById(planUuid);
//			//
//			// if (callRecordById.getBody() != null) {
//			// // 意图
//			// if
//			// (replayDto.getLabel().contains(callRecordById.getBody().getAccurateIntent()))
//			// {
//			// if (callRecordById.getBody().getAccurateIntent().equals("F")) {
//			// // F类判断挂断类型
//			// if
//			// (replayDto.getLabelType().contains(callRecordById.getBody().getReason()))
//			// {
//			// // 创建
//			// }
//			// } else {
//			// // 创建
//			// }
//			// }
//			// }
//			// }
//			int result = dispatchPlanMapper.updateByExampleSelective(dispatchPlan, ex);
//			logger.info("当前队列任务回调修改结果" + result);
//
//			logger.info("---------------------发送短信------------------------");
//			logger.info("---------------------发送短信------------------------");
//			logger.info("---------------------发送短信------------------------");
//			logger.info("---------------------发送短信------------------------");
//			boolean resultmsg = SendSms(sendSMsDispatchPlan, mqSuccPhoneDto.getLabel());
//			logger.info("---------------------发送短信------------------------");
//			logger.info("---------------------发送短信------------------------");
//			logger.info("---------------------发送短信------------------------");
//			logger.info("---------------------发送短信------------------------");
//
//			// 判断下一批计算是否还有相同的推送任务，如果没有则设置redis失效时间为0
//			// List<DispatchPlan> queryAvailableSchedules =
//			// queryAvailableSchedules(dispatchPlan.getUserId(), 1,
//			// dispatchPlan.getLine(), new DispatchPlan(), false);
//			// if (queryAvailableSchedules.size() <= 0) {
//			// String key = dispatchPlan.getUserId() + "-" +
//			// dispatchPlan.getRobot() + "-" + dispatchPlan.getLine();
//			// redisUtil.del(key);
//			// logger.info("当前计划没有下一批任务:" + key + "删除了redis缓存");
//			// }
//
//		}
//	}
//
//	/**
//	 * 记录第三方接口记录详情
//	 * 
//	 * @param url
//	 * @param jsonObject
//	 * @return
//	 */
//	private boolean insertThirdInterface(String url, JSONObject jsonObject) {
//		ThirdInterfaceRecords record = new ThirdInterfaceRecords();
//		try {
//			record.setCreateTime(DateUtil.getCurrent4Time());
//		} catch (Exception e2) {
//			logger.error("error", e2);
//		}
//		record.setUrl(url);
//		record.setParams(jsonObject.toJSONString());
//		record.setTimes(Constant.THIRD_INTERFACE_RETRYTIMES);
//		logger.info("调用第三方接口异常，记录失败记录");
//		int res = thirdInterfaceRecordsMapper.insert(record);
//		return res > 0 ? true : false;
//	}
//
//	/**
//	 * 发送短信
//	 * 
//	 * @param sendSMsDispatchPlan
//	 * @param label
//	 * @return
//	 */
//	private boolean SendSms(DispatchPlan sendSMsDispatchPlan, String label) {
//		UserSmsConfigExample ex = new UserSmsConfigExample();
//		ex.createCriteria().andUserIdEqualTo(sendSMsDispatchPlan.getUserId()).andCallResultEqualTo(label);
//		List<UserSmsConfig> selectByExample = userSmsConfigMapper.selectByExample(ex);
//		UserSmsConfig userConf = null;
//		if (selectByExample.size() > 0) {
//			sendMsgDto msg = new sendMsgDto();
//			userConf = selectByExample.get(0);
//			// 手机
//			msg.setMobile(sendSMsDispatchPlan.getPhone());
//			SmsTunnel tunnel = smsTunnerMapper.selectByPrimaryKey(userConf.getSmsTunnelId());
//			// 短信模板
//			msg.setTemplateId(userConf.getTemplateId());
//			// json 授权信息
//			sendMsgDto copyBean = JsonUtils.json2Bean(tunnel.getPlatformConfig(), sendMsgDto.class);
//
//			String date = null;
//			try {
//				date = getCurrent4Time();
//			} catch (ParseException e) {
//				logger.error("error", e);
//			}
//			// author
//			// String authorization =
//			// Base64MD5Util.decodeData(copyBean.getAccountSID() + "|" + date);
//			String auth = copyBean.getAccountSID() + "|" + date;
//			String authorization = Base64.getEncoder().encodeToString(auth.getBytes());
//			// MD5加密（账户Id + 账户授权令牌 +时间戳)
//			String sign = Base64MD5Util.encryption(copyBean.getAccountSID() + copyBean.getAuthToken() + date);
//			copyBean.setSign(sign);
//			copyBean.setAuthorization(authorization);
//			copyBean.setPhone(sendSMsDispatchPlan.getPhone());
//			copyBean.setSmsTemplateId(userConf.getSmsTemplateId());
//			// 发送到mq
//			boolean insertMessMq = msgService.insertMessMq(copyBean);
//
//			// String url = "http://api.ytx.net/" + "201512/sid/" +
//			// copyBean.getAccountSID() + "/"
//			// + "sms/TemplateSMS.wx?Sign=" + sign;
//			// // + "sms/TemplateSMS.wx";
//			// JSONObject json = new JSONObject();
//			// json.put("action", "templateSms");
//			// json.put("mobile", sendSMsDispatchPlan.getPhone());
//			// json.put("appid", copyBean.getAppid());
//			// json.put("templateId", userConf.getSmsTemplateId());
//			// String doPostJson = doPostJson(url, json.toJSONString(),
//			// authorization);
//			// JSONObject msgRes = JSONObject.parseObject(doPostJson);
//			// // String test = "{\"statusCode\": \"0\",\"statusMsg\":
//			// // \"提交成功\",\"requestId\": \"20181235962383254861905920103\"}";
//			// String statusCode = (String) msgRes.get("statusCode");
//			// String statusMsg = (String) msgRes.get("statusMsg");
//			// String requestId = (String) msgRes.get("requestId");
//			//
//			// if (statusCode.equals("0")) {
//			// // 发送状态
//			// logger.info("发送成功:statusCode:{},statusMsg:{},requestId:{}",
//			// statusCode, statusMsg, requestId);
//			// // 数据库记录数据
//			// } else {
//			// logger.info("发送失败:statusCode:{},statusMsg:{},requestId:{}",
//			// statusCode, statusMsg, requestId);
//			// }
//			// SendMsgRecords msgRe = new SendMsgRecords();
//			// try {
//			// msgRe.setCreateTime(DateUtil.getCurrent4Time());
//			// } catch (Exception e) {
//			// logger.info("error", e);
//			// }
//			// msgRe.setPhone(sendSMsDispatchPlan.getPhone());
//			// msgRe.setRequestid(requestId);
//			// msgRe.setStatuscode(statusCode);
//			// msgRe.setStatusmsg(statusMsg);
//			// int insert = sendMsgMapper.insert(msgRe);
//			// logger.info("-----------------记录短信记录-------------------");
//
//		} else {
//			logger.info("当前用户没信息");
//		}
//
//		return true;
//	}
//
//	public static String getCurrent4Time() throws ParseException {
//		SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyyMMddHHmmss");
//		return dateFormatter.format(new Date());
//	}
//}
