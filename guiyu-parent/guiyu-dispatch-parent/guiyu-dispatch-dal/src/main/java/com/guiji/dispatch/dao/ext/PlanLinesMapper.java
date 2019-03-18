package com.guiji.dispatch.dao.ext;

import com.guiji.dispatch.dao.entity.DispatchLines;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlanLinesMapper {

    //根据planUuid查询线路
    List<DispatchLines> queryLinesByPlan(String planUuid);
}
