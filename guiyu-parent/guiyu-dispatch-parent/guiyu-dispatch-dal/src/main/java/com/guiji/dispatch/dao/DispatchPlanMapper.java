package com.guiji.dispatch.dao;

import com.guiji.dispatch.dao.entity.DispatchPlan;
import com.guiji.dispatch.dao.entity.DispatchPlanExample;
import com.guiji.dispatch.vo.DownLoadPlanVo;
import com.guiji.dispatch.vo.TotalPlanCountVo;
import io.swagger.models.auth.In;
import org.apache.ibatis.annotations.Param;

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

	List<DispatchPlan> selectByCallHour(@Param("record")DispatchPlan record, @Param("orgIds")List<Integer> orgIds);

	List<DispatchPlan> selectByCallHour4UserId(@Param("record")DispatchPlan record, @Param("orgIds")List<Integer> orgIds);

	int updateDispatchPlanListByStatusSYNC(@Param("params") List<Long> list , @Param("status")Integer status, @Param("orgIds")List<Integer> orgIds);

	List<DispatchPlan> selectPlanGroupByUserIdLineRobot(@Param("dis")DispatchPlan record, @Param("orgIds")List<Integer> orgIds);
	
	List<DispatchPlan> selectPlanGroupByUserId(@Param("dis")DispatchPlan record, @Param("orgIds")List<Integer> orgIds);

    //按日期统计计划数量
    TotalPlanCountVo totalPlanCount(@Param("plan") DispatchPlan plan,
                                    @Param("beginDate") String beginDate, @Param("endDate") String endDate);

    //查询任务计划
    DispatchPlan queryDispatchPlanById(@Param("planUuId") long planUuId, @Param("orgId") Integer orgId);

    //查询任务计划备注
    String queryPlanRemarkById(@Param("planUuId") long planUuId, @Param("orgId") Integer orgId);

    //查询下载数据
    List<DownLoadPlanVo> queryDownloadPlanList(DispatchPlanExample example);
}