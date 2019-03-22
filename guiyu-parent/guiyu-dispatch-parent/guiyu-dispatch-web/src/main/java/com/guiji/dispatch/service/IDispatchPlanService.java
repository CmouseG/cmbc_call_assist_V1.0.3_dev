package com.guiji.dispatch.service;

import com.alibaba.fastjson.JSONObject;
import com.guiji.ccmanager.entity.LineConcurrent;
import com.guiji.ccmanager.vo.CallPlanDetailRecordVO;
import com.guiji.common.model.Page;
import com.guiji.dispatch.bean.BatchDispatchPlanList;
import com.guiji.dispatch.bean.IdsDto;
import com.guiji.dispatch.bean.MessageDto;
import com.guiji.dispatch.dao.entity.DispatchLines;
import com.guiji.dispatch.dao.entity.DispatchPlan;
import com.guiji.dispatch.dao.entity.DispatchPlanBatch;
import com.guiji.dispatch.dto.QueryDownloadPlanListDto;
import com.guiji.dispatch.dto.QueryPlanListDto;
import com.guiji.dispatch.model.PlanCountVO;
import com.guiji.dispatch.sys.ResultPage;
import com.guiji.dispatch.vo.DispatchPlanVo;
import com.guiji.dispatch.vo.DownLoadPlanVo;
import com.guiji.dispatch.vo.TotalPlanCountVo;

import java.util.List;

public interface IDispatchPlanService {

	/**
	 * 写入任务
	 *
	 * @param schedule
	 *            任务
	 * @return 响应报文
	 * @throws Exception
	 */
	MessageDto addSchedule(DispatchPlan dispatchPlan, Long userId, String orgCode) throws Exception;

	/**
	 * 查询任务列表
	 *
	 * @param userId
	 *            用户id
	 * @return 响应报文
	 */
	Page<DispatchPlan> querySchedules(final Integer userId, int pagenum, int pagesize);

	/**
	 * 暂停任务
	 *
	 * @param planUuid
	 *            任务id
	 * @return 响应报文
	 */
	boolean pauseSchedule(final String planUuid);

	/**
	 * 恢复任务
	 *
	 * @param planUuid
	 *            任务id
	 * @return 响应报文
	 */
	boolean resumeSchedule(final String planUuid);

	/**
	 * 删除任务
	 *
	 * @param planUuid
	 *            任务id
	 * @return 响应报文
	 */
	boolean deleteSchedule(final String planUuid);

	/**
	 * 取消
	 *
	 * @param planUuid
	 *            任务id
	 * @return 响应报文
	 */
	boolean cancelSchedule(final String planUuid);

	/**
	 * 根据客户操作所有的批量计划
	 * 
	 * @param dispatchPlanBatch
	 * @return
	 */
	public boolean OperationAllDispatchByUserId(Integer userId, Integer status);

	/**
	 * 完成
	 *
	 * @param planUuid
	 *            任务id
	 * @return 响应报文
	 */
	boolean successSchedule(String planUuid, String label);

	/**
	 * 写入批次
	 * 
	 * @param dispatchPlanBatch
	 * @return
	 */
	public boolean dispatchPlanBatch(DispatchPlanBatch dispatchPlanBatch);

	/**
	 * 根据批次分页查询
	 * 
	 * @param batchId
	 * @param pagenum
	 * @param pagesize
	 * @return
	 */
	public Page<DispatchPlan> queryDispatchPlanByBatchId(Integer batchId, int pagenum, int pagesize);

	/**
	 * 
	 * @param phone
	 * @param planStaus
	 * @param startTime
	 * @param endTime
	 * @param pagenum
	 * @param pagesize
	 * @return
	 */
	public Page<DispatchPlan> queryDispatchPlanByParams(String phone, String planStaus, String startTime,
			String endTime, Integer batchId, String replayType, int pagenum, int pagesize, Long userId,
			boolean isSuperAdmin, Integer selectUserId, String startCallData, String endCallData, String orgCode
			,Integer isDesensitization);


	/**
	 * 获取客户线路列表
	 * 
	 * @param userId
	 * @return
	 */
	List<LineConcurrent> outLineinfos(String userId);

	/**
	 * 根据当前当前时间查询号码
	 * 
	 * @return
	 */
	List<DispatchPlan> selectPhoneByDate();

	List<DispatchPlan> selectPhoneByDateAndFlag(String flag, Integer statusPlan, Integer statusSync);

	/**
	 * 批量修改状态
	 * 
	 * @param dto
	 * @return
	 */
	boolean batchUpdatePlans(IdsDto[] dto);

	/**
	 * 一键操作状态*
	 * 
	 * @param batchId
	 * @param status
	 * @return
	 */
	MessageDto operationAllPlanByBatchId(Integer batchId, String status, Long userId);

	/**
	 * 批量删除
	 * 
	 * @param dto
	 * @return
	 */
	boolean batchDeletePlans(IdsDto[] dto);

	/**
	 * 查询批次
	 * 
	 * @return
	 */
	List<DispatchPlanBatch> queryDispatchPlanBatch(Long userId, Boolean isSuperAdmin, String orgCode);

	/**
	 * 根据当前时间刷新日期
	 * 
	 * @return
	 */
	boolean updateReplayDate(Boolean flag);

	/**
	 * 检查批次是否存在
	 * 
	 * @return
	 */
	boolean checkBatchId(String name);

	/**
	 * 批量修改状态位置
	 * 
	 * @param list
	 * @return
	 */
	boolean batchUpdateFlag(List<DispatchPlan> list, String flag);

	int getcall4BatchName(String batchName, Integer status);

	/**
	 * 
	 * @param phone
	 * @param planStaus
	 * @param startTime
	 * @param endTime
	 * @param batchId
	 * @param replayType
	 * @param pagenum
	 * @param pagesize
	 * @param userId
	 * @param isSuperAdmin
	 * @return
	 */
	Page<DispatchPlan> queryDispatchPlan(String batchName, int pagenum, int pagesize);

	List<CallPlanDetailRecordVO> queryDispatchPlanByPhoens(String phone, String batchName, int pagenum, int pagesize);

	JSONObject getServiceStatistics(Long userId, Boolean isSuperAdmin, String orgCode);

	JSONObject getServiceStatistics(Long userId, String startTime, String endTime, Boolean isSuperAdmin,
			String orgCode);

	/**
	 * 根据用户ID统计计划数据
	 * @param userId
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	TotalPlanCountVo totalPlanCountByUserDate(String userId, String startTime, String endTime);

	/**
	 * 根据批次ID统计计划数据
	 * @param batchId
	 * @return
	 */
	TotalPlanCountVo totalPlanCountByBatch(Integer batchId);

	boolean insertDispatchPlanList(List<DispatchPlan> list);

	List<DispatchPlan> queryAvailableSchedules(Integer userId, int requestCount, int lineId, DispatchPlan isSuccess,
			boolean flag);


	public List<DispatchPlan> selectPhoneByDate4Redis(Integer userId, String flag, Integer limit, Integer lineId);

	public List<DispatchPlan> selectPhoneByDate4UserId(String flag, Integer limit);

	public PlanCountVO getPlanCountByUserId(String orgCode);
	
	boolean stopPlanByorgCode(String orgCode, String type);
	
	public boolean batchInsertDisplanPlan( BatchDispatchPlanList plans,  Long userId,
			 String orgCode);


	//查询任务计划
	DispatchPlan queryDispatchPlanById(String planUuId);

	//查询任务计划备注
	String queryPlanRemarkById(String planUuid);

	//查询计划列表
	ResultPage<DispatchPlan> queryPlanList(QueryPlanListDto queryPlanDto, ResultPage<DispatchPlan> page);

	//查询计划列表
	List<DownLoadPlanVo> queryDownloadPlanList(QueryDownloadPlanListDto queryPlanDto);

	//根据plan_uuid查询线路列表
	List<DispatchLines> queryLineByPlan(String planUuid);

	ResultPage<DispatchPlanVo> queryPlanListByPage(QueryPlanListDto queryPlanDto, ResultPage<DispatchPlanVo> page);
}

