package com.guiji.dispatch.controller;

import com.alibaba.fastjson.JSONObject;
import com.guiji.common.model.Page;
import com.guiji.component.jurisdiction.Jurisdiction;
import com.guiji.dispatch.batchimport.AsynFileService;
import com.guiji.dispatch.bean.BatchDispatchPlanList;
import com.guiji.dispatch.bean.IdsDto;
import com.guiji.dispatch.bean.MessageDto;
import com.guiji.dispatch.dao.entity.DispatchBatchLine;
import com.guiji.dispatch.dao.entity.DispatchPlan;
import com.guiji.dispatch.dao.entity.DispatchPlanBatch;
import com.guiji.dispatch.dto.QueryPlanListDto;
import com.guiji.dispatch.service.IDispatchPlanService;
import com.guiji.dispatch.sys.ResultPage;
import com.guiji.dispatch.util.DateTimeUtils;
import com.guiji.dispatch.util.Log;
import com.guiji.dispatch.vo.DispatchPlanVo;
import com.guiji.dispatch.vo.TotalPlanCountVo;
import com.guiji.utils.JsonUtils;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
public class DispatchPlanController {
	static Logger logger = LoggerFactory.getLogger(DispatchPlanController.class);
	@Autowired
	private IDispatchPlanService dispatchPlanService;
	@Autowired
	private AsynFileService asynFileService;

	/**
	 * 单个导入任务
	 * @param dispatchPlan
	 * @param userId
	 * @param orgCode
	 * @param orgId
	 * @return
	 */
	@Jurisdiction("taskCenter_phonelist_addPlan")
	@PostMapping("addSchedule")
	public MessageDto addSchedule(@RequestBody DispatchPlan dispatchPlan, @RequestHeader Long userId,
			@RequestHeader String orgCode, @RequestHeader Integer orgId) {
		logger.info("单个任务导入begin,dispatchPaln:{}:", (null != dispatchPlan)? JsonUtils.bean2Json(dispatchPlan):null);
		MessageDto dto = new MessageDto();
		try {
			dto = dispatchPlanService.addSchedule(dispatchPlan, userId, orgCode, orgId);
		} catch (Exception e) {
			logger.error("error", e);
		}
		logger.info("单个任务导入end,dispatchPaln:{}:", (null != dto)? JsonUtils.bean2Json(dto):null);
		return dto;
	}

	/**
	 * 查询任务
	 * 
	 * @return
	 */
	@PostMapping("queryDispatchPlanBatch")
	@Log(info = "查询批量信息")
	public List<DispatchPlanBatch> queryDispatchPlanBatch(@RequestHeader Long userId,
			@RequestHeader Boolean isSuperAdmin, @RequestHeader String orgCode, @RequestHeader Integer orgId,
			@RequestHeader Integer authLevel) {
		return dispatchPlanService.queryDispatchPlanBatch(userId, isSuperAdmin, orgCode, orgId, authLevel);
	}

	/**
	 * 文件上传
	 * 
	 * @param fileName
	 * @param file
	 * @return
	 */
	@Jurisdiction("taskCenter_phonelist_addBatchPlan")
	@Log(info = "文件上传")
	@PostMapping("batchImport")
	public MessageDto batchImport( @RequestParam("file") MultipartFile file, @RequestHeader Long userId,
			@RequestParam(required = true, name = "dispatchPlan") String dispatchPlan,
								   @RequestHeader String orgCode, @RequestHeader Integer orgId,
			@RequestParam(required = true, name = "fileName") String fileName) {
		logger.info("batchImport start");
		MessageDto batchImport = new MessageDto();
		try {
			asynFileService.batchPlanImport(fileName, userId, file, dispatchPlan, orgCode, orgId);
		} catch (Exception e) {
			batchImport.setResult(false);
			batchImport.setMsg(e.getMessage());
			logger.error("error", e);
		}

		return batchImport;

	}


	/**
	 * 批量修改任务状态
	 * 
	 * @param dto
	 * @return
	 */
	@Jurisdiction("taskCenter_phonelist_batchRecover,taskCenter_phonelist_batchStop,taskCenter_phonelist_batchPause")
	@PostMapping("batchUpdatePlans")
	public boolean batchUpdatePlans(@RequestBody IdsDto[] dto) {
		return dispatchPlanService.batchUpdatePlans(dto);
	}

	/**
	 * 一键修改状态
	 * 
	 * @param batchId
	 * @return
	 */
	@Jurisdiction("taskCenter_phonelist_oneKeyStop,taskCenter_phonelist_oneKeyPause,taskCenter_phonelist_oneKeyRecover")
	@PostMapping("operationAllPlanByBatchId")
	@Log(info = "一键修改状态")
	public MessageDto operationAllPlanByBatchId(@RequestParam(required = true, name = "batchId") Integer batchId,
			@RequestParam(required = true, name = "status") String status, @RequestHeader Long userId, @RequestHeader Integer orgId) {
		return dispatchPlanService.operationAllPlanByBatchId(batchId, status, userId, orgId);
	}

	/**
	 * 一键删除状态
	 * @param dto
	 * @return
	 */
	@Jurisdiction("taskCenter_phonelist_batchDelete,taskCenter_phonelist_delete")
	@PostMapping("deleteAllPlanByBatchId")
	public boolean deleteAllPlanByBatchId(@RequestBody IdsDto[] dto) {
		logger.info("deleteAllPlanByBatchId:{}:", (null != dto) ? JsonUtils.bean2Json(dto) : null);
		return dispatchPlanService.batchDeletePlans(dto);
	}

	/**
	 * 批量加入
	 * @param plans
	 * @param userId
	 * @param orgCode
	 * @param orgId
	 * @return
	 */
	@Jurisdiction("taskCenter_phonelist_batchJoin")
	@PostMapping("batchInsertDisplanPlan")
	public boolean batchInsertDisplanPlan(@RequestBody BatchDispatchPlanList plans, @RequestHeader Long userId,
			@RequestHeader String orgCode, @RequestHeader Integer orgId) {

		return dispatchPlanService.batchInsertDisplanPlan(plans, userId, orgCode, orgId);
	}

	@PostMapping("checkBatchName")
	public boolean checkBatchName(@RequestParam(required = true, name = "batchName") String batchName) {
		return dispatchPlanService.checkBatchId(batchName);
	}


	/**
	 * 累计任务号码总数，累计拨打号码总数，最后计划日期，最后拨打日期，累计服务天数
	 * 
	 * @param userId
	 * @param isSuperAdmin
	 * @return
	 */
	@PostMapping("getServiceStatistics")
	public JSONObject getServiceStatistics(@RequestHeader Long userId, @RequestHeader Integer authLevel,
                                           @RequestHeader String orgCode, @RequestHeader Integer orgId,
                                           @RequestHeader Boolean isSuperAdmin) {
		return dispatchPlanService.getServiceStatistics(userId, isSuperAdmin, authLevel, orgCode, orgId);
	}

	/**
	 * 任务概览：批次数，任务数，拨打数
	 * 
	 * @param userId
	 * @param isSuperAdmin
	 * @return
	 */
	@PostMapping("getData")
	public JSONObject getData(@RequestParam(required = false, name = "startTime") String startTime,
			@RequestParam(required = false, name = "endTime") String endTime,
            @RequestHeader Long userId,@RequestHeader Integer authLevel,
			@RequestHeader String orgCode, @RequestHeader Integer orgId,
			@RequestHeader Boolean isSuperAdmin) {
		return dispatchPlanService.getServiceStatistics(userId, startTime, endTime, isSuperAdmin, orgCode, orgId, authLevel);
	}


	//根据用户统计当天计划数量
	@Log(info ="根据用户统计当天计划数量")
	@PostMapping("totalPlanCountByUserDate")
	public TotalPlanCountVo totalPlanCountByUserDate(@RequestHeader String userId, @RequestHeader Integer orgId){
		String dateStr = new SimpleDateFormat(DateTimeUtils.DEFAULT_DATE_FORMAT_PATTERN_SHORT).format(new Date());
	//	beginDate = dateStr + " " + DateTimeUtils.DEFAULT_DATE_START_TIME;
	//	endDate = dateStr + " " + DateTimeUtils.DEFAULT_DATE_END_TIME;
		return dispatchPlanService.totalPlanCountByUserDate(userId, dateStr, dateStr, orgId);
	}

	//查询计划列表
	@ApiOperation(value="查询计划列表", notes="查询计划列表")
	@RequestMapping(value = "/dispatch/plan/queryPlanList", method = {RequestMethod.POST, RequestMethod.GET})
	public Page<DispatchPlan> queryPlanList(@RequestHeader Long userId, @RequestHeader Integer authLevel,
                                            @RequestHeader String orgCode, @RequestHeader Integer orgId,
											@RequestHeader Boolean isSuperAdmin, @RequestHeader Integer isDesensitization,
											@RequestBody QueryPlanListDto queryPlanDto) {
		if(null == queryPlanDto){
			queryPlanDto = new QueryPlanListDto();
			queryPlanDto.setPageNo(1);
		}else{
			queryPlanDto.setPageNo(queryPlanDto.getPageNo()>0?queryPlanDto.getPageNo():1);
		}

		queryPlanDto.setOperUserId(userId+"");
		queryPlanDto.setOperOrgCode(orgCode);
		queryPlanDto.setSuperAdmin(isSuperAdmin);
		queryPlanDto.setIsDesensitization(isDesensitization);
		queryPlanDto.setAuthLevel(authLevel);
        queryPlanDto.setOperOrgId(orgId);
		logger.info("/dispatch/plan/queryPlanList:{}", JsonUtils.bean2Json(queryPlanDto));
		ResultPage<DispatchPlan> resPage = new ResultPage<DispatchPlan>(queryPlanDto);
		resPage = dispatchPlanService.queryPlanList(queryPlanDto, resPage);
		Page<DispatchPlan> page = new Page<>();
		Integer pageNo = queryPlanDto.getPageNo();
		Integer pageSize = queryPlanDto.getPageSize();
		page.setPageNo(pageNo);
		page.setPageSize(pageSize);
		page.setRecords(resPage.getList());
		page.setTotal(Long.valueOf(resPage.getTotalItemNumber()).intValue());

		return page;
	}

	@ApiOperation(value="查询计划列表", notes="查询计划列表")
	@RequestMapping(value = "/dispatch/plan/queryLineByPlan", method = {RequestMethod.POST, RequestMethod.GET})
	public List<DispatchBatchLine> queryLineByPlan(@RequestParam(required = true, name = "batchId") Integer batchId) {
		return dispatchPlanService.queryLineByPlan(batchId);
	}

	@ApiOperation(value = "查询计划列表", notes = "查询计划列表")
	@RequestMapping(value = "/dispatch/plan/queryPlanListByPage", method = {RequestMethod.POST, RequestMethod.GET})
	public Page<DispatchPlanVo> queryPlanListByPage(@RequestHeader Long userId, @RequestHeader Integer authLevel,
                                                    @RequestHeader String orgCode, @RequestHeader Integer orgId,
													@RequestHeader Boolean isSuperAdmin, @RequestHeader Integer isDesensitization,
													@RequestBody QueryPlanListDto queryPlanDto) {
		if (null == queryPlanDto) {
			queryPlanDto = new QueryPlanListDto();
			queryPlanDto.setPageNo(1);
		} else {
			queryPlanDto.setPageNo(queryPlanDto.getPageNo() > 0 ? queryPlanDto.getPageNo() : 1);
		}

		queryPlanDto.setOperUserId(userId + "");
		queryPlanDto.setOperOrgCode(orgCode);
		queryPlanDto.setSuperAdmin(isSuperAdmin);
		queryPlanDto.setIsDesensitization(isDesensitization);
		queryPlanDto.setAuthLevel(authLevel);
        queryPlanDto.setOperOrgId(orgId);
		logger.info("/dispatch/plan/queryPlanListByPage:{}", JsonUtils.bean2Json(queryPlanDto));
		ResultPage<DispatchPlanVo> resPage = new ResultPage<DispatchPlanVo>(queryPlanDto);
		resPage = dispatchPlanService.queryPlanListByPage(queryPlanDto, resPage);
		Page<DispatchPlanVo> page = new Page<>();
		Integer pageNo = queryPlanDto.getPageNo();
		Integer pageSize = queryPlanDto.getPageSize();
		page.setPageNo(pageNo);
		page.setPageSize(pageSize);
		page.setRecords(resPage.getList());
		page.setTotal(Long.valueOf(resPage.getTotalItemNumber()).intValue());
		return page;
	}
}
