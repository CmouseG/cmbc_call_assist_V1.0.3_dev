package com.guiji.dispatch.controller;

import com.alibaba.fastjson.JSONObject;
import com.guiji.auth.api.IAuth;
import com.guiji.auth.api.IOrg;
import com.guiji.botsentence.api.IBotSentenceProcess;
import com.guiji.botsentence.api.entity.BotSentenceProcess;
import com.guiji.botsentence.api.entity.ServerResult;
import com.guiji.ccmanager.vo.CallPlanDetailRecordVO;
import com.guiji.clm.api.LineMarketRemote;
import com.guiji.common.model.Page;
import com.guiji.component.result.Result.ReturnData;
import com.guiji.dispatch.api.IThirdApiOut;
import com.guiji.dispatch.batchimport.BatchImportErrorCodeEnum;
import com.guiji.dispatch.batchimport.IBatchImportFieRecordErrorService;
import com.guiji.dispatch.bean.ThirdCheckParams;
import com.guiji.dispatch.dao.DispatchPlanBatchMapper;
import com.guiji.dispatch.dao.DispatchPlanMapper;
import com.guiji.dispatch.dao.FileErrorRecordsMapper;
import com.guiji.dispatch.dao.ThirdInterfaceRecordsMapper;
import com.guiji.dispatch.dao.entity.FileErrorRecords;
import com.guiji.dispatch.dao.entity.*;
import com.guiji.dispatch.enums.PlanLineTypeEnum;
import com.guiji.dispatch.line.IDispatchBatchLineService;
import com.guiji.dispatch.model.DispatchBatchLine;
import com.guiji.dispatch.model.DispatchPlan;
import com.guiji.dispatch.model.*;
import com.guiji.dispatch.service.IDispatchPlanBatchService;
import com.guiji.dispatch.service.IDispatchPlanService;
import com.guiji.dispatch.service.IPhoneRegionService;
import com.guiji.dispatch.thirdapi.ThirdApiImportQueueHandler;
import com.guiji.dispatch.util.Constant;
import com.guiji.dispatch.util.ResHandler;
import com.guiji.user.dao.entity.SysOrganization;
import com.guiji.user.dao.entity.SysUser;
import com.guiji.utils.DateUtil;
import com.guiji.utils.HttpClientUtil;
import com.guiji.utils.IdGengerator.SnowflakeIdWorker;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * 第三方接口
 * 
 * @author Administrator
 *
 */
@RestController
public class ThirdApiController implements IThirdApiOut {

	static Logger logger = LoggerFactory.getLogger(ThirdApiController.class);

	@Autowired
	private IDispatchPlanService dispatchPlanService;
	@Autowired
	private DispatchPlanMapper dispatchPlanMapper;
	@Autowired
	private IAuth auth;
	@Autowired
	private DispatchPlanBatchMapper batchMapper;
	@Autowired
	private IBotSentenceProcess Process;
	@Autowired
	private ThirdApiImportQueueHandler thirdApiHandler;
	@Autowired
	private IBatchImportFieRecordErrorService fileRecordErrorService;
	@Autowired
	private FileErrorRecordsMapper fileErrorRecordsMapper;
	@Autowired
	private ThirdInterfaceRecordsMapper thirdCallBackMapper;
	@Autowired
	private LineMarketRemote lineMarket;
	@Autowired
	private IDispatchBatchLineService lineService;
	@Autowired
	private IPhoneRegionService phoneRegionService;

	@Override
	@GetMapping(value = "out/getCalldetail")
	public ReturnData<Page<com.guiji.dispatch.model.CallPlanDetailRecordVO>> getCalldetail(long userId, Integer authLevel,
		String orgCode, Integer orgId,
		String phone, String batchNumber, int pagenum, int pagesize) {
		Page<com.guiji.dispatch.model.CallPlanDetailRecordVO> page = new Page<>();
		List<CallPlanDetailRecordVO> queryDispatchPlanByPhoens = dispatchPlanService.queryDispatchPlanByPhoens(userId, authLevel, orgCode, orgId,
				phone, batchNumber, pagenum, pagesize);
		DispatchPlanExample ex = new DispatchPlanExample();
		ex.createCriteria().andPhoneEqualTo(phone).andBatchNameEqualTo(batchNumber);
		int countByExample = dispatchPlanMapper.countByExample(ex);
		List<com.guiji.dispatch.model.CallPlanDetailRecordVO> list = new ArrayList<>();
		for (CallPlanDetailRecordVO vo : queryDispatchPlanByPhoens) {
			com.guiji.dispatch.model.CallPlanDetailRecordVO record = new com.guiji.dispatch.model.CallPlanDetailRecordVO();
			BeanUtils.copyProperties(vo, record);
			list.add(record);
		}
		page.setRecords(list);
		page.setPageNo(pagenum);
		page.setPageSize(pagesize);
		page.setTotal(countByExample);
		ReturnData<Page<com.guiji.dispatch.model.CallPlanDetailRecordVO>> returndata = new ReturnData<>();
		returndata.setBody(page);
		return returndata;
	}

	@Override
	@GetMapping(value = "out/getcall4BatchName")
	public ReturnData<PlanCallInfoCount> getcall4BatchName(long userId, String batchName, int pagenum, int pagesize) {
		int countAlready = dispatchPlanService.getcall4BatchName(userId, batchName, Constant.STATUSPLAN_1);
		int countNo = dispatchPlanService.getcall4BatchName(userId, batchName, Constant.STATUSPLAN_2);
		PlanCallInfoCount info = new PlanCallInfoCount();
		info.setSuccCount(countNo);
		info.setPlanCount(countAlready);
		FileErrorRecords record = new FileErrorRecords();
		record.setBatchName(batchName);
		record.setDataType(Constant.IMPORT_DATA_TYPE_API);
		Page<FileErrorRecords> queryFileErrorRecordsPage = fileRecordErrorService.queryFileErrorRecordsPage(pagenum,
				pagesize, record);
		ReturnData<PlanCallInfoCount> returndata = new ReturnData<>();
		if (queryFileErrorRecordsPage.getRecords() != null) {
			List<com.guiji.dispatch.model.FileErrorRecords> list = new ArrayList<>();
			for (FileErrorRecords vo : queryFileErrorRecordsPage.getRecords()) {
				com.guiji.dispatch.model.FileErrorRecords bean = new com.guiji.dispatch.model.FileErrorRecords();
				BeanUtils.copyProperties(vo, bean);
				list.add(bean);
			}
			Page<com.guiji.dispatch.model.FileErrorRecords> page = new Page<>();
			page.setRecords(list);
			page.setPageNo(queryFileErrorRecordsPage.getPageNo());
			page.setPageSize(queryFileErrorRecordsPage.getPageSize());
			FileErrorRecordsExample errorEx = new FileErrorRecordsExample();
			errorEx.createCriteria().andBatchNameEqualTo(batchName);
			int countByExample = fileErrorRecordsMapper.countByExample(errorEx);
			page.setTotal(countByExample);
			info.setErrorRecordsList(page);
		}
		returndata.setBody(info);
		return returndata;
	}

	@Override
	@GetMapping(value = "out/queryDispatchPlan")
	public ReturnData<Page<DispatchPlanApi>> queryDispatchPlan(String batchName, int pagenum, int pagesize) {
		Page<DispatchPlanApi> page = new Page<>();
		page.setPageNo(pagenum);
		page.setPageSize((pagesize));
		DispatchPlanExample example = new DispatchPlanExample();
		example.setLimitStart((pagenum - 1) * pagesize);
		example.setLimitEnd(pagesize);
		example.createCriteria().andBatchNameEqualTo(batchName).andIsDelEqualTo(Constant.IS_DEL_0);
		List<com.guiji.dispatch.dao.entity.DispatchPlan> list = dispatchPlanMapper.selectByExample(example);
		int countByExample = dispatchPlanMapper.countByExample(example);
		page.setTotal(countByExample);
		List<DispatchPlanApi> copyBean = new ArrayList<>();
		for (com.guiji.dispatch.dao.entity.DispatchPlan plan : list) {
			DispatchPlanApi bean = new DispatchPlanApi();
			BeanUtils.copyProperties(plan, bean);
			copyBean.add(bean);
		}
		page.setRecords(copyBean);
		ReturnData<Page<DispatchPlanApi>> returnData = new ReturnData<>();
		returnData.setBody(page);
		return returnData;
	}

	/**
	 * @param jsonList
	 * @return
	 */
	@Override
	@PostMapping(value = "out/insertDispatchPlanList")
	public ReturnData<PlanResultInfo> insertDispatchPlanList(String jsonList) {
		DispatchPlanList parseObject = JSONObject.parseObject(jsonList, DispatchPlanList.class);
		//检验基本参数
		ThirdCheckParams checkBaseParams = checkBaseParams(parseObject);
		if (!checkBaseParams.isResult()) {
			PlanResultInfo info = new PlanResultInfo();
			info.setMsg(checkBaseParams.getMsg());
			ReturnData<PlanResultInfo> returnData = new ReturnData<>();
			returnData.setBody(info);
			return returnData;
		}

		// 检查批次名字是否存在
		if (dispatchPlanService.checkBatchId(parseObject.getBatchName())) {
			PlanResultInfo info = new PlanResultInfo();
			info.setMsg("批次已经存在");
			ReturnData<PlanResultInfo> returnData = new ReturnData<>();
			returnData.setBody(info);
			return returnData;
		}

		ReturnData<SysUser> user = auth.getUserById(Long.valueOf(parseObject.getUserId()));
		String username = user.getBody().getUsername();
		String lineName = "";
		ServerResult<List<BotSentenceProcess>> templateById = Process.getTemplateById(parseObject.getRobot());

		DispatchPlanBatch batch = new DispatchPlanBatch();
		batch.setName(parseObject.getBatchName());
		batch.setUserId(Integer.valueOf(parseObject.getUserId()));
		batch.setStatusShow(Constant.BATCH_STATUS_SHOW);
		batch.setGmtCreate(DateUtil.getCurrent4Time());
		batch.setGmtModified(DateUtil.getCurrent4Time());
		batch.setOrgCode(user.getBody().getOrgCode());
		batchMapper.insert(batch);

		//路线类型
		Integer lineType = null != parseObject.getLineType()?parseObject.getLineType(): PlanLineTypeEnum.SIP.getType();
		// 加入线路
		List<DispatchBatchLine> lineList = parseObject.getLines();
		for (DispatchBatchLine lines : lineList) {

			com.guiji.dispatch.dao.entity.DispatchBatchLine newLine = new com.guiji.dispatch.dao.entity.DispatchBatchLine();
			BeanUtils.copyProperties(lines,newLine);

			newLine.setBatchId(batch.getId());
			newLine.setLineType(lineType);
			newLine.setOrgId(parseObject.getOrgId());
			newLine.setUserId(Integer.valueOf(batch.getUserId()));
			lineService.insert(newLine);
		}
		List<com.guiji.dispatch.dao.entity.DispatchPlan> fails = new ArrayList<>();
		List<com.guiji.dispatch.dao.entity.DispatchPlan> succ = new ArrayList<>();
		List<String> phones = new ArrayList<>();
		boolean errorMsg = false;
		for (int i = 0; i < parseObject.getMobile().size(); i++) {
			DispatchPlan dispatchPlan = parseObject.getMobile().get(i);
			com.guiji.dispatch.dao.entity.DispatchPlan bean = new com.guiji.dispatch.dao.entity.DispatchPlan();
			BeanUtils.copyProperties(dispatchPlan, bean);
			if (bean.getPhone() == null || bean.getPhone() == "" || !isInteger(bean.getPhone())) {
				// 记录错误信息
				dispatchPlan.setBatchId(batch.getId());
				dispatchPlan.setBatchName(parseObject.getBatchName());
				saveErrorRecords(dispatchPlan, BatchImportErrorCodeEnum.UNKNOWN, i);
				fails.add(bean);
				errorMsg = true;
				continue;
			}
			bean.setPlanUuid(SnowflakeIdWorker.nextId(bean.getOrgId()));
			bean.setBatchId(batch.getId());
			bean.setUserId(Integer.valueOf(parseObject.getUserId()));
//			bean.setLine(Integer.valueOf(parseObject.getLine()));
			bean.setRobot(parseObject.getRobot());
			bean.setClean(Integer.valueOf(parseObject.getIsClean()));
			bean.setCallHour(parseObject.getCallHour());
			bean.setCallData(Integer.valueOf(parseObject.getCallDate()));
			bean.setFlag(Constant.IS_FLAG_0);
			bean.setGmtCreate(DateUtil.getCurrent4Time());
			bean.setGmtModified(DateUtil.getCurrent4Time());
			bean.setUsername(username);
			bean.setLineName(lineName);
			bean.setIsDel(Constant.IS_DEL_0);
			bean.setStatusPlan(Constant.STATUSPLAN_1);
			bean.setStatusSync(Constant.STATUS_SYNC_0);
			bean.setOrgCode(user.getBody().getOrgCode());
			bean.setBatchName(parseObject.getBatchName());
			bean.setIsTts(Constant.IS_TTS_0);
			bean.setReplayType(Constant.REPLAY_TYPE_0);
			bean.setRobotName(templateById.getData().get(0).getTemplateName());
			if (phones.contains(bean.getPhone())) {
				dispatchPlan.setBatchId(batch.getId());
				dispatchPlan.setBatchName(parseObject.getBatchName());
				saveErrorRecords(dispatchPlan, BatchImportErrorCodeEnum.DUPLICATE, i);
				fails.add(bean);
				errorMsg = true;
				continue;
			}
			// 查询号码归属地
			String cityName = phoneRegionService.queryPhoneRegion(dispatchPlan.getPhone());
			bean.setCityName(cityName);
			thirdApiHandler.add(bean);
			logger.info(" thirdApiHandler bean " + bean);
			succ.add(bean);
			phones.add(bean.getPhone());
		}

		PlanResultInfo info = new PlanResultInfo();
		info.setSuccCount(succ.size());
		info.setErrorCount(fails.size());

		if (succ.size() <= 0 && fails.size() >= 0) {
			// 删除批次
			batchMapper.deleteByPrimaryKey(batch.getId());
		}

		ReturnData<PlanResultInfo> returnData = new ReturnData<>();
		returnData.setBody(info);
		if(errorMsg){
			returnData.setMsg("存在重复号码或者号码格式不正确，请检查");
		}
		return returnData;
	}


	/**
	 * 记录第三方的错误信息
	 * 
	 * @param vo
	 * @param i
	 */
	private void saveErrorRecords(DispatchPlan vo, BatchImportErrorCodeEnum errorCodeEnum, int i) {
		FileErrorRecords records = new FileErrorRecords();
		records.setAttach(vo.getAttach());
		records.setCreateTime(DateUtil.getCurrent4Time());
		records.setParams(vo.getParams());
		records.setPhone(vo.getPhone());
		records.setCustName(vo.getCustName());
		records.setCustCompany(vo.getCustCompany());
		// records.setFileRecordsId(Long.valueOf(vo.getFileRecordId()));
		records.setErrorType(errorCodeEnum.getValue());
		records.setDataType(Constant.IMPORT_DATA_TYPE_API);
		records.setBatchId(vo.getBatchId());
		records.setBatchName(vo.getBatchName());
		fileRecordErrorService.save(records);
	}

	/**
	 * 校验基本参数
	 * 
	 * @param dispatchPlanList
	 * @return
	 */
	private ThirdCheckParams checkBaseParams(DispatchPlanList dispatchPlanList) {
		ThirdCheckParams checkResult = new ThirdCheckParams();

		if (dispatchPlanList.getRobot() == null || dispatchPlanList.getRobot() == "") {
			checkResult.setResult(false);
			checkResult.setMsg("robot参数必填,请检查");
			return checkResult;
		}


		if (dispatchPlanList.getIsClean() == null || dispatchPlanList.getIsClean() == "") {
			checkResult.setResult(false);
			checkResult.setMsg("isClean参数必填,请检查");
			return checkResult;
		}

		if (dispatchPlanList.getCallDate() == null || dispatchPlanList.getCallDate() == "") {
			checkResult.setResult(false);
			checkResult.setMsg("callDate参数必填,请检查");
			return checkResult;
		}

		if (dispatchPlanList.getCallHour() == null || dispatchPlanList.getCallHour() == "") {
			checkResult.setResult(false);
			checkResult.setMsg("callHour参数必填,请检查");
			return checkResult;
		}

		if (dispatchPlanList.getBatchName() == null || dispatchPlanList.getBatchName() == "") {
			checkResult.setResult(false);
			checkResult.setMsg("batchName参数必填,请检查");
			return checkResult;
		}
		// 检查用户
		ReturnData<SysUser> user = auth.getUserById(Long.valueOf(dispatchPlanList.getUserId()));
		// 检查话术
		ServerResult<List<BotSentenceProcess>> templateById = Process.getTemplateById(dispatchPlanList.getRobot());
		if (user.getBody() == null) {
			checkResult.setResult(false);
			checkResult.setMsg("用户id不存在");
			return checkResult;
		}

		if (templateById.getData().size() == 0) {
			checkResult.setResult(false);
			checkResult.setMsg("话术模板不存在");
			return checkResult;
		}
		return checkResult;
	}

	public static boolean isInteger(String str) {
		Pattern pattern = Pattern.compile("^(?!11)\\d{11}$");
		return pattern.matcher(str).matches();
	}

	@Override
	public ReturnData<Boolean> reTryThirdApi(Long userId) {
		// 查询出失败的记录
		ThirdInterfaceRecordsExample ex = new ThirdInterfaceRecordsExample();
		ex.createCriteria().andUserIdEqualTo(userId.intValue()).andTypeEqualTo(Constant.THIRDAPIFAILURE);
		List<ThirdInterfaceRecords> selectByExample = thirdCallBackMapper.selectByExample(ex);
		for (ThirdInterfaceRecords record : selectByExample) {
			String url = record.getUrl();
			if(url==null || url.equals("")){
				logger.info("当前reTryThirdApi url为null");
				continue;
			}
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("data", record.getParams());
			try {
				HttpClientUtil.doPostJson(url, jsonObject.toString());
				record.setType(Constant.THIRDAPISUCCESS);
				thirdCallBackMapper.updateByPrimaryKey(record);
			} catch (Exception e) {
				logger.error("reTryThirdApiError", e);
			}
		}
		ReturnData<Boolean> returndata = new ReturnData<>();
		return returndata;
	}

	@Autowired
	IDispatchPlanBatchService dispatchPlanBatchService;

	@Override
	public ReturnData<Page<com.guiji.dispatch.model.CallPlanDetailRecordVO>> getCalldetailForApi(Long userId, String batchNumber, int pagenum, int pagesize) {
		Page<com.guiji.dispatch.model.CallPlanDetailRecordVO> page = new Page<>();

		DispatchPlanBatch dispatchPlanBatch = dispatchPlanBatchService.queryPlanBatchByName(batchNumber, userId.intValue());

		List<CallPlanDetailRecordVO> queryDispatchPlanByPhoens = dispatchPlanService.queryPlanByUserAndBatchId(userId, dispatchPlanBatch.getId(), pagenum, pagesize);
		DispatchPlanExample ex = new DispatchPlanExample();
		ex.createCriteria().andBatchNameEqualTo(batchNumber).andOrgIdIn(getSubOrgIdsByUserId(userId.intValue()));
		int countByExample = dispatchPlanMapper.countByExample(ex);
		List<com.guiji.dispatch.model.CallPlanDetailRecordVO> list = new ArrayList<>();

		if(CollectionUtils.isNotEmpty(queryDispatchPlanByPhoens)) {
			for (CallPlanDetailRecordVO vo : queryDispatchPlanByPhoens) {
				com.guiji.dispatch.model.CallPlanDetailRecordVO record = new com.guiji.dispatch.model.CallPlanDetailRecordVO();
				BeanUtils.copyProperties(vo, record);
				list.add(record);
			}
		}
		page.setRecords(list);
		page.setPageNo(pagenum);
		page.setPageSize(pagesize);
		page.setTotal(countByExample);
		ReturnData<Page<com.guiji.dispatch.model.CallPlanDetailRecordVO>> returndata = new ReturnData<>();
		returndata.setBody(page);
		return returndata;
	}

	@Autowired
	IOrg orgService;

	private List<Integer> getSubOrgIds(Integer orgId)
	{
		ReturnData<List<Integer>>  resp = orgService.getSubOrgIdByOrgId(orgId);
		List<Integer> result = null;
		if (resp != null && resp.getBody() != null) {
			result = resp.getBody();
		}

		if(result == null)
		{
			result = new ArrayList<>();
		}

		result.add(orgId);

		return result;
	}

	private List<Integer> getSubOrgIdsByUserId(Integer userId)
	{
		SysOrganization userOrg = ResHandler.getResObj(auth.getOrgByUserId(Long.valueOf(userId)));

		return getSubOrgIds(userOrg.getId());
	}

}
