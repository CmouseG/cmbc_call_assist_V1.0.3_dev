package com.guiji.dispatch.dao;

import com.guiji.dispatch.dao.entity.DispatchPlan;
import com.guiji.dispatch.dao.entity.DispatchPlanExample;
import com.guiji.dispatch.vo.DownLoadPlanVo;
import com.guiji.dispatch.vo.TotalBatchPlanCountVo;
import com.guiji.dispatch.vo.TotalPlanCountVo;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface DispatchPlanMapper {
    int countByExample(DispatchPlanExample example);

    int deleteByExample(DispatchPlanExample example);

    int deleteByPrimaryKey(long planUUdi);

    int insert(DispatchPlan record);

    int insertSelective(DispatchPlan record);

    List<DispatchPlan> selectByExample(DispatchPlanExample example);

    DispatchPlan selectByPrimaryKey(long planUUdi);

    int updateByExampleSelective(@Param("record") DispatchPlan record, @Param("example") DispatchPlanExample example);

    int updateByExample(@Param("record") DispatchPlan record, @Param("example") DispatchPlanExample example);

    int updateByPrimaryKeySelective(DispatchPlan record);

    int updateByPrimaryKey(DispatchPlan record);

    //计划完成变更
    int finishPlanById(@Param("planUuid") Long planUuid,
                       @Param("statusPlan") Integer statusPlan,
                       @Param("result") String result,
                       @Param("callbackTime") Date callbackTime,
                       @Param("orgId") Integer orgId);

	List<DispatchPlan> selectByCallHour(@Param("record")DispatchPlan record, @Param("orgId") Integer orgId);//@Param("orgIds")List<Integer> orgIds

    //获取等于当前日期的拨打用户列表
	List<Integer> selectByCallHour4UserId(@Param("record")DispatchPlan record, @Param("orgIds")List<Integer> orgIds);

	//获取大于等于当前日期的拨打用户列表
    List<Integer> selectFutureUserByParam(@Param("record")DispatchPlan record, @Param("orgIds")List<Integer> orgIds);

    //获取大于等于当前日期的拨打任务列表
    List<DispatchPlan> selectFuturePlanByUserId(@Param("record")DispatchPlan record, @Param("orgId") Integer orgId);

	int updateDispatchPlanListByStatusSYNC(@Param("params") List<Long> list , @Param("status")Integer status, @Param("orgIds")List<Integer> orgIds);

	//更新任务计划队列状态
    int updPlanByStatusSync(@Param("params") List<Long> list , @Param("statusSync") Integer statusSync, @Param("orgId") Integer orgId);

	List<DispatchPlan> selectPlanGroupByUserIdLineRobot(@Param("dis")DispatchPlan record, @Param("orgIds")List<Integer> orgIds);

	List<DispatchPlan> selectPlanGroupByUserId(@Param("dis")DispatchPlan record, @Param("orgIds")List<Integer> orgIds);

    //按日期统计计划数量
    TotalPlanCountVo totalPlanCount(@Param("plan") DispatchPlan plan,
                                    @Param("beginDate") String beginDate, @Param("endDate") String endDate);

    //查询任务计划
    DispatchPlan queryDispatchPlanById(@Param("planUuid") long planUuid, @Param("orgId") Integer orgId);

    //查询任务计划备注
    String queryPlanRemarkById(@Param("planUuid") long planUuid, @Param("orgId") Integer orgId);

    //查询下载数据
    List<DownLoadPlanVo> queryDownloadPlanList(DispatchPlanExample example);

    List<DispatchPlan> getPlanUuidList(DispatchPlanExample example);

    //统计计划表中按批次分组统计各种状态数量
    List<TotalBatchPlanCountVo> totalNoNotifyPlanByOrg(@Param("orgId") Integer orgId,
                                               @Param("callData") Integer callData);
}