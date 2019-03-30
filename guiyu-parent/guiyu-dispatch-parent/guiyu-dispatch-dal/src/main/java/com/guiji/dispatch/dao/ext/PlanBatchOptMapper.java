package com.guiji.dispatch.dao.ext;

import com.guiji.dispatch.dto.OptPlanDto;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface PlanBatchOptMapper {

    int delPlanBatchById(@Param("planUuidList") List<Long> planUuidList, @Param("delTime") Date delTime);

    int delPlanBatchByParam(@Param("optPlanDto") OptPlanDto optPlanDto, @Param("delTime") Date delTime);
}
