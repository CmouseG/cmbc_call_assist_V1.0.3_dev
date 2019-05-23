package com.guiji.dispatch.batchimport;

import com.guiji.component.result.Result;
import com.guiji.dispatch.dao.DispatchPlanMapper;
import com.guiji.dispatch.dao.entity.DispatchBatchLine;
import com.guiji.dispatch.dao.entity.DispatchPlan;
import com.guiji.dispatch.dao.entity.FileErrorRecords;
import com.guiji.dispatch.enums.PlanLineTypeEnum;
import com.guiji.dispatch.line.IDispatchBatchLineService;
import com.guiji.dispatch.service.GateWayLineService;
import com.guiji.dispatch.service.IBlackListService;
import com.guiji.dispatch.service.IPhoneRegionService;
import com.guiji.dispatch.util.Constant;
import com.guiji.dispatch.util.DaoHandler;
import com.guiji.guiyu.message.component.FanoutSender;
import com.guiji.robot.api.IRobotRemote;
import com.guiji.robot.model.CheckParamsReq;
import com.guiji.robot.model.CheckResult;
import com.guiji.robot.model.HsParam;
import com.guiji.utils.DateUtil;
import com.guiji.utils.JsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

	@Override
	public void preCheck(DispatchPlan vo) throws Exception
	{

		if (vo == null) {
			return;
		}

		// 校验黑名单逻辑
		if (blackService.checkPhoneInBlackList(vo.getPhone(),vo.getOrgCode())) {
			blackService.setBlackPhoneStatus(vo);
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
					logger.error("机器人合成失败, 电话号码{}, 错误信息为{}", vo.getPhone(), checkResult.getCheckMsg());
					return;
				}
			}
		} else {
			if(null != vo.getFileRecordId()) {
				saveFileErrorRecords(vo, BatchImportErrorCodeEnum.SELLBOT_CHECK_ERROR);
			}
			logger.error("机器人合成失败, 电话号码{}, 请求校验参数失败,请检查机器人的参数", vo.getPhone());
			return;
		}

		fanoutSender.send("fanout.dispatch.BatchImportSaveDB", JsonUtils.bean2Json(vo));
	}

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
		}
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
