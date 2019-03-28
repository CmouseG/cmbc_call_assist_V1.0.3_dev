package com.guiji.dispatch.dao.ext;

import com.guiji.dispatch.dao.entity.DispatchPlanExample;
import com.guiji.dispatch.vo.DispatchPlanVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlanExtMapper {

    List<DispatchPlanVo> queryPlanListByPage(@Param("example") DispatchPlanExample example,
                                             @Param("isDesensitization") Integer isDesensitization);

    int queryPlanCountByBatch(@Param("batchId") Integer batchId);
}
