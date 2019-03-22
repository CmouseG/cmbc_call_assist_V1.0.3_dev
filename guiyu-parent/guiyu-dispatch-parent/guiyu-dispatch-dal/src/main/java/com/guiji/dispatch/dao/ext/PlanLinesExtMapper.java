package com.guiji.dispatch.dao.ext;

import com.guiji.dispatch.dao.entity.DispatchLines;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlanLinesExtMapper {

    //根据planUuid查询线路
    List<DispatchLines> queryLinesByPlan(String planUuid);

    //统计lineId线路是否在“计划中”
    int totalLineIsUsed(@Param("lineId") Integer lineId, @Param("userIdList") List<String> userIdList);
}
