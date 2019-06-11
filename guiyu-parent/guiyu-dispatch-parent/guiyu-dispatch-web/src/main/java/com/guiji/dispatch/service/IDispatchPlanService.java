package com.guiji.dispatch.service;

import com.alibaba.fastjson.JSONObject;
import com.guiji.ccmanager.vo.CallPlanDetailRecordVO;
import com.guiji.dispatch.bean.AddPlanAsyncEntity;
import com.guiji.dispatch.bean.BatchDispatchPlanList;
import com.guiji.dispatch.bean.IdsDto;
import com.guiji.dispatch.bean.MessageDto;
import com.guiji.dispatch.dao.entity.DispatchBatchLine;
import com.guiji.dispatch.dao.entity.DispatchPlan;
import com.guiji.dispatch.dao.entity.DispatchPlanBatch;
import com.guiji.dispatch.dto.QueryDownloadPlanListDto;
import com.guiji.dispatch.dto.QueryPlanListDto;
import com.guiji.dispatch.model.DispatchPlanBatchAddVo;
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
     * @param schedule 任务
     * @return 响应报文
     * @throws Exception
     */
    MessageDto addSchedule(DispatchPlan dispatchPlan, Long userId, String orgCode, Integer orgId)
            throws Exception;

    /**
     * 完成
     *
     * @param planUuid 任务id
     * @return 响应报文
     */
    boolean successSchedule(String planUuid, String label);

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
    MessageDto operationAllPlanByBatchId(Integer batchId, String status, Long userId, Integer orgId);

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
    List<DispatchPlanBatch> queryDispatchPlanBatch(
            String org_code, Long userId, Boolean isSuperAdmin, String orgCode, Integer orgId, Integer authLevel);

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

    int getcall4BatchName(Long userId, String batchName, Integer status);

    List<CallPlanDetailRecordVO> queryDispatchPlanByPhoens(
            Long userId, Integer authLevel, String orgCode, Integer orgId, String phone, String batchName, int pagenum, int pagesize);

    JSONObject getServiceStatistics(Long userId, Boolean isSuperAdmin, Integer authLevel, String orgCode, Integer orgId);

    JSONObject getServiceStatistics(
            Long userId,
            String startTime,
            String endTime,
            Boolean isSuperAdmin,
            String orgCode,
            Integer orgId,
            Integer authLevel);

    /**
     * 根据用户ID统计计划数据
     *
     * @param userId
     * @param startTime
     * @param endTime
     * @return
     */
    TotalPlanCountVo totalPlanCountByUserDate(
            String userId, String startTime, String endTime, Integer orgId);

    /**
     * 根据批次ID统计计划数据
     *
     * @param batchId
     * @return
     */
    TotalPlanCountVo totalPlanCountByBatch(Integer batchId, Integer orgId);

    List<DispatchPlan> queryAvailableSchedules(
            Integer userId, int requestCount, int lineId, DispatchPlan isSuccess, boolean flag);

    PlanCountVO getPlanCountByUserId(String orgCode, Integer orgId);

    boolean stopPlanByorgCode(String orgCode, Integer orgId, String type);

    boolean batchInsertDisplanPlan(
            BatchDispatchPlanList plans, Long userId, String orgCode, Integer orgId);

    // 查询任务计划
    DispatchPlan queryDispatchPlanById(long planUuId);

    // 查询任务计划备注
    String queryPlanRemarkById(long planUuid);

    // 查询计划列表
    ResultPage<DispatchPlan> queryPlanList(
            QueryPlanListDto queryPlanDto, ResultPage<DispatchPlan> page);

    // 查询计划列表
    List<DownLoadPlanVo> queryDownloadPlanList(QueryDownloadPlanListDto queryPlanDto);

    //查询计划线路
    List<DispatchBatchLine> queryLineByPlan(Integer batchId);

    //分页查询计划
    ResultPage<DispatchPlanVo> queryPlanListByPage(QueryPlanListDto queryPlanDto, ResultPage<DispatchPlanVo> page);

    //查询批次数量
    int queryPlanCountByBatch(Integer batchId);

    //更新
    boolean updPlanStatusSyncById(Long planUuid, Integer statusSync);

    void addPlan(DispatchPlan dispatchPlan);

    void addPlanAsync(AddPlanAsyncEntity entity);

    void saveError(DispatchPlan dispatchPlan);

    Integer getRightCount(DispatchPlan plan);

    List<DispatchPlan> getFailCount(DispatchPlan plan);

    List<CallPlanDetailRecordVO> queryPlanByUserAndBatchId(Long userId, Integer batchId, int pagenum, int pagesize);

    DispatchPlanBatchAddVo getPlanResult(DispatchPlan vo);
}
