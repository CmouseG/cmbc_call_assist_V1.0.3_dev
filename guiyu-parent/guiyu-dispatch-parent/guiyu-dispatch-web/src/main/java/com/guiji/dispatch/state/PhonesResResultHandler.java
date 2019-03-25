package com.guiji.dispatch.state;

import com.guiji.component.result.Result.ReturnData;
import com.guiji.dispatch.dao.entity.DispatchPlan;
import com.guiji.dispatch.model.ModularLogs;
import com.guiji.dispatch.service.IDispatchPlanService;
import com.guiji.dispatch.service.IModularLogsService;
import com.guiji.dispatch.util.Constant;
import com.guiji.robot.api.IRobotRemote;
import com.guiji.robot.model.TtsComposeCheckRsp;
import com.guiji.robot.model.TtsVoiceReq;
import com.guiji.utils.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 请求号码资源
 * 
 * @author Administrator
 *
 */
@Service
public class PhonesResResultHandler implements IPhonesResResult {

	static Logger logger = LoggerFactory.getLogger(PhonesInitHandler.class);

	@Autowired
	private IModularLogsService modularLogs;

	@Autowired
	private IDispatchPlanService dispatchPlanService;

	@Autowired
	private IRobotRemote robotRemote;

	@Override
	public void Handler(List<DispatchPlan> list) {
		logger.info("*******************************PhonesResResultHandler***********************************");
		logger.info("*******************************PhonesResResultHandler***********************************");
		logger.info("*******************************PhonesResResultHandler***********************************");

//		List<DispatchPlan> selectPhoneByDateAndFlag = dispatchPlanService.selectPhoneByDateAndFlag(Constant.IS_FLAG_1,
//				Constant.STATUSPLAN_1, Constant.STATUS_SYNC_0);
//		logger.info("当前号码请求资源结果" + selectPhoneByDateAndFlag.size());

		List<TtsVoiceReq> ttsVoiceReqList = new ArrayList<>();
		List<ModularLogs> beforeLogs = new ArrayList<>();
		for (DispatchPlan dis : list) {
			TtsVoiceReq req = new TtsVoiceReq();
			req.setSeqid(dis.getPlanUuidLong() + "");
			req.setTemplateId(dis.getRobot());
			ttsVoiceReqList.add(req);

			ModularLogs log = new ModularLogs();
			try {
				log.setCreateTime(DateUtil.getCurrent4Time());
			} catch (Exception e) {
				logger.error("error", e);
			}
			log.setModularName(Constant.MODULAR_NAME_DISPATCH);
			log.setStatus(Constant.MODULAR_STATUS_START);
			log.setPlanUuid(dis.getPlanUuidLong() + "");
			log.setPhone(dis.getPhone());
			log.setBatchName(dis.getBatchName());
			beforeLogs.add(log);
		}
		modularLogs.notifyLogsList(beforeLogs);

		ReturnData<List<TtsComposeCheckRsp>> ttsComposeCheck = robotRemote.ttsComposeCheck(ttsVoiceReqList);
		List<DispatchPlan> successList = new ArrayList<>();

		List<ModularLogs> afterlogs = new ArrayList<>();
		List<String> error = new ArrayList<>();

		if (ttsComposeCheck.success) {
			if (ttsComposeCheck.getBody() != null) {
				List<TtsComposeCheckRsp> body = ttsComposeCheck.getBody();
				for (TtsComposeCheckRsp tts : body) {
					// 记录logs
					ModularLogs log = new ModularLogs();
					try {
						log.setCreateTime(DateUtil.getCurrent4Time());
					} catch (Exception e) {
						logger.error("error", e);
					}
					log.setModularName(Constant.MODULAR_NAME_DISPATCH);
					log.setStatus(Constant.MODULAR_STATUS_END);
					log.setPlanUuid(tts.getSeqId());
					// TTS资源成功之后才可以修改
					if (tts.getStatus() == 1) {
						DispatchPlan dis = new DispatchPlan();
						dis.setFlag(Constant.IS_FLAG_2);
						dis.setPlanUuid(Long.valueOf(tts.getSeqId()));
						successList.add(dis);
					} else {
						logger.info("tts校验参数返回结果...." +tts.getStatus());
						log.setStatus(Constant.MODULAR_STATUS_ERROR);
						log.setMsg("校验资源状态返回结果不为0");
					}
					afterlogs.add(log);
				}
				modularLogs.notifyLogsList(afterlogs);
			}
		} else {
			logger.info("获取当前初始化号码的请求资源结果失败了");
		}
		logger.info("当前资源准备好的资源号码有:" + successList.size());
		boolean batchUpdateFlag = false;
		if (successList.size() > 0) {
			batchUpdateFlag = dispatchPlanService.batchUpdateFlag(list, Constant.IS_FLAG_2);
		}
		logger.info("获取当前初始化号码的请求资源结果修改当结果" + batchUpdateFlag);
	}

}
