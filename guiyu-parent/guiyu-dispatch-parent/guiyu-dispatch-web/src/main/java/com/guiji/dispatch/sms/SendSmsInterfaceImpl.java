package com.guiji.dispatch.sms;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.guiji.dispatch.bean.MQSuccPhoneDto;
import com.guiji.dispatch.bean.sendMsgDto;
import com.guiji.dispatch.dao.SmsTunnelMapper;
import com.guiji.dispatch.dao.UserSmsConfigMapper;
import com.guiji.dispatch.dao.entity.DispatchPlan;
import com.guiji.dispatch.dao.entity.SmsTunnel;
import com.guiji.dispatch.dao.entity.UserSmsConfig;
import com.guiji.dispatch.dao.entity.UserSmsConfigExample;
import com.guiji.dispatch.util.Base64MD5Util;
import com.guiji.utils.JsonUtils;

@Service
public class SendSmsInterfaceImpl implements SendSmsInterface {

	private static Logger logger = LoggerFactory.getLogger(SendSmsInterfaceImpl.class);

	@Autowired
	private SmsTunnelMapper smsTunnerMapper;
	
	@Autowired
	private IMessageService msgService;

	
	@Autowired
	private UserSmsConfigMapper userSmsConfigMapper;

	@Override
	public void execute(DispatchPlan dis,MQSuccPhoneDto mqSuccPhoneDto) {
		logger.info("---------------------发送短信------------------------");
		logger.info("---------------------发送短信------------------------");
		logger.info("---------------------发送短信------------------------");
		logger.info("---------------------发送短信------------------------");
		boolean resultmsg = SendSms(dis, mqSuccPhoneDto.getLabel());
		logger.info("---------------------发送短信------------------------");
		logger.info("---------------------发送短信------------------------");
		logger.info("---------------------发送短信------------------------");
		logger.info("---------------------发送短信------------------------");
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
		} else {
			logger.info("当前用户没信息");
		}

		return true;
	}
	
	public static String getCurrent4Time() throws ParseException {
		SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyyMMddHHmmss");
		return dateFormatter.format(new Date());
	}

}
