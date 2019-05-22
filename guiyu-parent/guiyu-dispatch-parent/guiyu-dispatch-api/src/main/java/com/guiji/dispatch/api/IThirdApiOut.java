package com.guiji.dispatch.api;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.*;

import com.alibaba.fastjson.JSONObject;
import com.guiji.common.model.Page;
import com.guiji.component.result.Result.ReturnData;
import com.guiji.dispatch.model.CallPlanDetailRecordVO;
import com.guiji.dispatch.model.DispatchPlan;
import com.guiji.dispatch.model.DispatchPlanApi;
import com.guiji.dispatch.model.DispatchPlanList;
import com.guiji.dispatch.model.PlanCallInfoCount;
import com.guiji.dispatch.model.PlanResultInfo;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * 第三方对外接口
 * 
 * @author Administrator
 *
 */
@FeignClient("GUIYU-DISPATCH-WEB")
public interface IThirdApiOut {

	@ApiOperation(value = "分页查询通话记录")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "userId", value = "用户id", dataType = "Long", paramType = "query"),
			@ApiImplicitParam(name = "phone", value = "手机号码", dataType = "String", paramType = "query"),
			@ApiImplicitParam(name = "batchNumber", value = "批次标识", dataType = "String", paramType = "query"),
			@ApiImplicitParam(name = "pagenum", value = "分页字段", dataType = "int", paramType = "query"),
			@ApiImplicitParam(name = "pagesize", value = "分页字段", dataType = "int", paramType = "query") })
	@GetMapping(value = "out/getCalldetail")
	ReturnData<Page<CallPlanDetailRecordVO>> getCalldetail(@RequestParam("userId") long userId,@RequestParam("authLevel") Integer authLevel,
														   @RequestParam("orgCode") String orgCode, @RequestParam("orgId") Integer orgId,
														   @RequestParam("phone") String phone,
														   @RequestParam("batchNumber") String batchNumber, @RequestParam("pagenum") int pagenum,
														   @RequestParam("pagesize") int pagesize);


	@ApiOperation(value = "通过批次号查询该批次的拨打情况")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "userId", value = "用户id", dataType = "Long", paramType = "query"),
			@ApiImplicitParam(name = "batchName", value = "批量名称", dataType = "String", paramType = "query"),
			@ApiImplicitParam(name = "pagenum", value = "分页字段", dataType = "int", paramType = "query"),
			@ApiImplicitParam(name = "pagesize", value = "分页字段", dataType = "int", paramType = "query")})
	@GetMapping(value = "out/getcall4BatchName")
	ReturnData<PlanCallInfoCount> getcall4BatchName(@RequestParam("userId") long userId,@RequestParam("batchName") String batchName,@RequestParam("pagenum") int pagenum, @RequestParam("pagesize") int pagesize);

	@ApiOperation(value = "通过批次号分页查询该批次任务的号码列表")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "batchName", value = "批次标识", dataType = "String", paramType = "query"),
			@ApiImplicitParam(name = "pagenum", value = "分页字段", dataType = "int", paramType = "query"),
			@ApiImplicitParam(name = "pagesize", value = "分页字段", dataType = "int", paramType = "query") })
	@GetMapping(value = "out/queryDispatchPlan")
	ReturnData<Page<DispatchPlanApi>> queryDispatchPlan(@RequestParam("batchName") String batchName,
			@RequestParam("pagenum") int pagenum, @RequestParam("pagesize") int pagesize);

	@ApiOperation(value = "添加任务号码")
	@PostMapping(value = "out/insertDispatchPlanList")
	ReturnData<PlanResultInfo> insertDispatchPlanList(@RequestParam("jsonList") String jsonList);
	
	
	
	@ApiOperation(value = "通过用户触发重新回调机制")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "userId", value = "用户id", dataType = "Long", paramType = "query") })
	@GetMapping(value = "out/reTryThirdApi")
	ReturnData<Boolean>  reTryThirdApi(@RequestParam("userId") Long userId);

	@PostMapping(value = "out/getCalldetailForApi")
	ReturnData<Page<CallPlanDetailRecordVO>> getCalldetailForApi(@RequestParam("userId") Long userId, @RequestParam("batchNumber") String batchNumber, @RequestParam("pagenum") int pagenum,
																 @RequestParam("pagesize") int pagesize);


}
