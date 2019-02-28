package com.guiji.dispatch.dao;

import com.guiji.dispatch.dao.entity.DispatchPlan;
import com.guiji.dispatch.dao.entity.DispatchPlanExample;

import java.util.List;
import java.util.Map;

import com.guiji.dispatch.vo.TotalPlanCountVo;
import org.apache.ibatis.annotations.Param;

public interface DispatchPlanMapper {
    int countByExample(DispatchPlanExample example);

    int deleteByExample(DispatchPlanExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(DispatchPlan record);

    int insertSelective(DispatchPlan record);

    List<DispatchPlan> selectByExample(DispatchPlanExample example);

    DispatchPlan selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") DispatchPlan record, @Param("example") DispatchPlanExample example);

    int updateByExample(@Param("record") DispatchPlan record, @Param("example") DispatchPlanExample example);

    int updateByPrimaryKeySelective(DispatchPlan record);

    int updateByPrimaryKey(DispatchPlan record);

	List<DispatchPlan> selectByCallHour(DispatchPlan record);

	int insertDispatchPlanList(List<DispatchPlan> list);
	
	int updateDispatchPlanList(@Param("params") List<String> list , @Param("flag")String flag);
	
	int updateDispatchPlanListByStatus(@Param("params") List<String> list , @Param("status")String status);
	
	int updateDispatchPlanListByStatus4Redis(@Param("params") List<String> list , @Param("status")Integer status);
	
	List<DispatchPlan> selectByCallHour4UserId(DispatchPlan record);

	List<DispatchPlan> selectByCallHour4LineId(DispatchPlan record);
	
	int updateDispatchPlanListByStatusSYNC(@Param("params") List<String> list , @Param("status")Integer status);

	int getCountByUserId(DispatchPlan record);
	
	List<DispatchPlan> selectPlanGroupByUserIdLineRobot(DispatchPlan record);
	
	List<DispatchPlan> selectPlanGroupByUserId(DispatchPlan record);


    TotalPlanCountVo totalPlanCount(@Param("tableNum") Integer tableNum,
                                    @Param("plan") DispatchPlan plan,
                                    @Param("beginDate") String beginDate, @Param("endDate") String endDate);
}