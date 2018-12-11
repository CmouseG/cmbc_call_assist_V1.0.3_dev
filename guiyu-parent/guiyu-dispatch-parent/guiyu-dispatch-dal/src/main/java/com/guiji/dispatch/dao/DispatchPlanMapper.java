package com.guiji.dispatch.dao;

import com.guiji.dispatch.dao.entity.DispatchPlan;
import com.guiji.dispatch.dao.entity.DispatchPlanExample;
import java.util.List;
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
	
	int updateDispatchPlanList(List<DispatchPlan> list);

}