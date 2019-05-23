package com.guiji.dispatch.thirdapi;

import com.guiji.component.result.Result;
import com.guiji.dispatch.batchimport.BatchImportErrorCodeEnum;
import com.guiji.dispatch.batchimport.IBatchImportFieRecordErrorService;
import com.guiji.dispatch.dao.DispatchPlanMapper;
import com.guiji.dispatch.dao.entity.DispatchBatchLine;
import com.guiji.dispatch.dao.entity.DispatchPlan;
import com.guiji.dispatch.dao.entity.FileErrorRecords;
import com.guiji.dispatch.enums.PlanLineTypeEnum;
import com.guiji.dispatch.line.IDispatchBatchLineService;
import com.guiji.dispatch.service.GateWayLineService;
import com.guiji.dispatch.util.Constant;
import com.guiji.robot.api.IRobotRemote;
import com.guiji.robot.model.CheckParamsReq;
import com.guiji.robot.model.CheckResult;
import com.guiji.robot.model.HsParam;
import com.guiji.utils.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ThirdApiImportRecordHandlerImpl implements IThirdApiImportRecordHandler {

	private static final Logger logger = LoggerFactory.getLogger(ThirdApiImportRecordHandlerImpl.class);

	@Autowired
	private IRobotRemote robotRemote;

	@Autowired
	private DispatchPlanMapper dispatchPlanMapper;

	@Autowired
	private IBatchImportFieRecordErrorService fileRecordErrorService;
	
	@Autowired
	private IDispatchBatchLineService lineService;

	@Autowired
	private GateWayLineService gateWayLineService;

	public void excute(DispatchPlan vo) throws Exception {
		logger.info("ThirdApiImportRecordHandlerImpl: "+ vo);
		if (vo == null) {
			return;
		}

		// 检查校验参数
		Result.ReturnData<List<CheckResult>> checkParams = checkParams(vo);
		logger.info("checkparam : " + checkParams);
		if (checkParams.success) {
			if (checkParams.getBody() != null) {
				List<CheckResult> body = checkParams.getBody();
				CheckResult checkResult = body.get(0);
				if (!checkResult.isPass()) {
					saveErrorRecords(vo, BatchImportErrorCodeEnum.SELLBOT_CHECK_PARAM);
					logger.info("机器人合成失败, 电话号码{}, 错误信息为{}", vo.getPhone(), checkResult.getCheckMsg());
					return;
				}else{
					//路线类型
					Integer lineType = null != vo.getLineType()?vo.getLineType():PlanLineTypeEnum.SIP.getType();
					vo.setLineType(lineType);
					// 加入线路
					List<DispatchBatchLine> lineList = vo.getLines();
					//mod by xujin
					for(DispatchBatchLine line : lineList){
						line.setBatchId(vo.getBatchId());
						line.setLineType(vo.getLineType());
						line.setOrgId(vo.getOrgId());
						line.setUserId(vo.getUserId().intValue());
						line.setLineType(lineType);
						lineService.insert(line);
					}
					int insert = dispatchPlanMapper.insert(vo);
					if(insert>0){
						//判断是否是路由网关路线
						if(null != lineType && PlanLineTypeEnum.GATEWAY.getType() == lineType) {
							//设置加入路由网关路线redis及状态
							gateWayLineService.setGatewayLineRedis(lineList);
						}
					}
				}
			}
		} else {
			logger.info("机器人合成失败, 电话号码{}, 请求校验参数失败,请检查机器人的参数", vo.getPhone());
			saveErrorRecords(vo, BatchImportErrorCodeEnum.SELLBOT_CHECK_ERROR);
			logger.info("机器人合成失败, 电话号码{}, 请求校验参数失败,请检查机器人的参数", vo.getPhone());
			return;
		}

	}

	private void saveErrorRecords(DispatchPlan vo, BatchImportErrorCodeEnum errorCodeEnum) throws Exception {
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
