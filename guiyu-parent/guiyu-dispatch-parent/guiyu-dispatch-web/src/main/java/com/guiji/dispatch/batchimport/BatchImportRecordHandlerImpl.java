package com.guiji.dispatch.batchimport;

import com.alibaba.druid.support.json.JSONUtils;
import com.guiji.component.result.Result;
import com.guiji.dispatch.bean.DispatchPlanBatchAddRes;
import com.guiji.dispatch.constant.RedisConstant;
import com.guiji.dispatch.dao.DispatchPlanBatchMapper;
import com.guiji.dispatch.dao.DispatchPlanMapper;
import com.guiji.dispatch.dao.entity.DispatchPlan;
import com.guiji.dispatch.dao.entity.DispatchPlanBatch;
import com.guiji.dispatch.dao.entity.FileErrorRecords;
import com.guiji.dispatch.line.IDispatchBatchLineService;
import com.guiji.dispatch.model.DispatchPlanBatchAddVo;
import com.guiji.dispatch.model.MqNotifyMessage;
import com.guiji.dispatch.model.PlanCallResultVo;
import com.guiji.dispatch.service.IBlackListService;
import com.guiji.dispatch.service.IDispatchPlanService;
import com.guiji.dispatch.service.IPhoneRegionService;
import com.guiji.dispatch.service.ThirdApiNotifyService;
import com.guiji.dispatch.util.Constant;
import com.guiji.dispatch.util.DaoHandler;
import com.guiji.guiyu.message.component.FanoutSender;
import com.guiji.guiyu.message.component.QueueSender;
import com.guiji.robot.api.IRobotRemote;
import com.guiji.robot.model.CheckParamsReq;
import com.guiji.robot.model.CheckResult;
import com.guiji.robot.model.HsParam;
import com.guiji.utils.DateUtil;
import com.guiji.utils.JsonUtils;
import com.guiji.utils.RedisUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

@Service
public class BatchImportRecordHandlerImpl implements IBatchImportRecordHandler {

	private static final Logger logger = LoggerFactory.getLogger(BatchImportRecordHandlerImpl.class);

	@Autowired
	private IRobotRemote robotRemote;

	@Autowired
	private DispatchPlanMapper dispatchPlanMapper;

	@Autowired
	private IBatchImportFieRecordErrorService fileRecordErrorService;

	@Autowired
	private IDispatchBatchLineService lineService;

	@Autowired
	private IPhoneRegionService phoneRegionService;

	@Autowired
	private IBlackListService blackService;

	@Autowired
	private FanoutSender fanoutSender;

	@Autowired
	IDispatchPlanService dispatchPlanService;

	@Autowired
	ThirdApiNotifyService thirdApiNotifyService;

	@Autowired
	DispatchPlanBatchMapper dispatchPlanBatchMapper;

	@Autowired
	QueueSender mqSender;

	@Override
	public void preCheck(DispatchPlan vo) throws Exception
	{

		if (vo == null) {
			return;
		}

		// 校验黑名单逻辑
		if (blackService.checkPhoneInBlackList(vo.getPhone(),vo.getOrgCode())) {
			blackService.setBlackPhoneStatus(vo);

			if(StringUtils.isNotEmpty(vo.getCallbackUrl())) {
				thirdApiNotifyService.singleNotify(vo);
				decrBatchCount(vo);
			}

			return;
		}

		// 检查校验参数
		Result.ReturnData<List<CheckResult>> checkParams = checkParams(vo);
		if (checkParams.success) {
			if (checkParams.getBody() != null) {
				List<CheckResult> body = checkParams.getBody();
				CheckResult checkResult = body.get(0);
				if (!checkResult.isPass()) {
					if(null != vo.getFileRecordId()) {
						saveFileErrorRecords(vo, BatchImportErrorCodeEnum.SELLBOT_CHECK_PARAM);
					}

					if(StringUtils.isNotEmpty(vo.getCallbackUrl())) {
						saveApiErrorRecords(vo, BatchImportErrorCodeEnum.SELLBOT_CHECK_ERROR);
						thirdApiNotifyService.singleNotify(vo);
						decrBatchCount(vo);
					}

					logger.error("机器人合成失败, 电话号码{}, 错误信息为{}", vo.getPhone(), checkResult.getCheckMsg());
					return;
				}
			}
		} else {
			if(null != vo.getFileRecordId()) {
				saveFileErrorRecords(vo, BatchImportErrorCodeEnum.SELLBOT_CHECK_ERROR);
			}

			if(StringUtils.isNotEmpty(vo.getCallbackUrl())) {

				saveApiErrorRecords(vo, BatchImportErrorCodeEnum.SELLBOT_CHECK_ERROR);
				thirdApiNotifyService.singleNotify(vo);
				decrBatchCount(vo);
			}
			logger.error("机器人合成失败, 电话号码{}, 请求校验参数失败,请检查机器人的参数", vo.getPhone());
			return;
		}

		fanoutSender.send("fanout.dispatch.BatchImportSaveDB", JsonUtils.bean2Json(vo));
	}

	@Autowired
	RedisUtil redisUtil;


	@Override
	public void saveDB(DispatchPlan vo)
	{
		try
		{
			//查询号码归属地
			String cityName = phoneRegionService.queryPhoneRegion(vo.getPhone());
			vo.setCityName(cityName);

		} catch (Exception e)
		{
			// doNothing
			logger.info("BatchImportRecordHandlerImpl.saveDB:{}", vo, e);
		}

		vo.setGmtModified(DateUtil.getCurrent4Time());
		vo.setGmtCreate(DateUtil.getCurrent4Time());

		try
		{
			boolean bool = DaoHandler.getMapperBoolRes(dispatchPlanMapper.insert(vo));
		} catch (Exception e)
		{
			// doNothing
			logger.info("BatchImportRecordHandlerImpl.saveDB:{}", vo, e);
		} finally {
			decrBatchCount(vo);
		}
	}

	/**
	 * 计数减1
	 * @param vo
	 */
	void decrBatchCount(DispatchPlan vo) {

		String key = RedisConstant.RedisConstantKey.DISPATCH_ADD_PLAN_COUNT_PRE+vo.getBatchId();

		long decr = redisUtil.decr(key, 1);

		//添加完毕
		if (decr == 0) {
			// TODO: 2019/5/22 批次信息通知

			thirdApiNotifyService.batchNotify(vo);

			redisUtil.del(key);

		}

	}


	private void saveApiErrorRecords(DispatchPlan vo, BatchImportErrorCodeEnum errorCodeEnum) throws Exception {

		if(errorCodeEnum.equals(BatchImportErrorCodeEnum.SELLBOT_CHECK_ERROR)) {
			vo.setResult("tts校验参数错误");
		} else if(errorCodeEnum.equals(BatchImportErrorCodeEnum.SELLBOT_CHECK_PARAM)) {
			vo.setResult("tts校验参数未通过");
		}

		logger.info("saveErrorRecords start");
		FileErrorRecords records = new FileErrorRecords();
		records.setAttach(vo.getAttach());
		records.setCreateTime(DateUtil.getCurrent4Time());
		records.setParams(vo.getParams());
		records.setPhone(vo.getPhone());
		records.setCustName(vo.getCustName());
		records.setCustCompany(vo.getCustCompany());
//		records.setFileRecordsId(Long.valueOf(vo.getFileRecordId()));
		records.setErrorType(errorCodeEnum.getValue());
		records.setDataType(Constant.IMPORT_DATA_TYPE_API);
		records.setBatchId(vo.getBatchId());
		records.setBatchName(vo.getBatchName());
		fileRecordErrorService.save(records);
		logger.info("saveErrorRecords end");
	}

	private void saveFileErrorRecords(DispatchPlan vo, BatchImportErrorCodeEnum errorCodeEnum) throws Exception {
		FileErrorRecords records = new FileErrorRecords();
		records.setAttach(vo.getAttach());
		records.setCreateTime(DateUtil.getCurrent4Time());
		records.setParams(vo.getParams());
		records.setPhone(vo.getPhone());
		records.setCustName(vo.getCustName());
		records.setCustCompany(vo.getCustCompany());
		records.setFileRecordsId(Long.valueOf(vo.getFileRecordId()));
		records.setErrorType(errorCodeEnum.getValue());
		records.setDataType(Constant.IMPORT_DATA_TYPE_PAGE);
		records.setBatchId(vo.getBatchId());
		records.setBatchName(vo.getBatchName());
		fileRecordErrorService.save(records);
	}

	private Result.ReturnData<List<CheckResult>> checkParams(DispatchPlan dispatchPlan) {
		CheckParamsReq req = new CheckParamsReq();
		HsParam hsParam = new HsParam();
		hsParam.setParams(dispatchPlan.getParams());
		hsParam.setSeqid(dispatchPlan.getPlanUuidLong() + "");
		hsParam.setTemplateId(dispatchPlan.getRobot());
		List<HsParam> list = new ArrayList<>();
		list.add(hsParam);
		req.setNeedResourceInit(false);
		req.setCheckers(list);
		Result.ReturnData<List<CheckResult>> checkParams = robotRemote.checkParams(req);
		return checkParams;
	}
}
