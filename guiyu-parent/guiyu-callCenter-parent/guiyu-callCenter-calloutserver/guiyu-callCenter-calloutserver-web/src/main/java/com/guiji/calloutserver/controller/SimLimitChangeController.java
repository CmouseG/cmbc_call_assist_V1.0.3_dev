package com.guiji.calloutserver.controller;

import com.guiji.calloutserver.api.ISimLimitChange;
import com.guiji.calloutserver.service.SimLimitService;
import com.guiji.component.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

/**
 * author:liyang
 * Date:2019/6/1 18:19
 * Description:
 */
@RestController
@Slf4j
public class SimLimitChangeController implements ISimLimitChange {

    @Autowired
    SimLimitService simLimitService;

    @Override
    public Result.ReturnData changeSimLineConfig(Integer lineId, Boolean callCountPeriodChange,
                                                 Boolean connectCountPeriodChange, Boolean connectTimePeriodChange) {

        log.info("修改sim限制配置信息,lineId[{}],callCountPeriodChange[{}],connectCountPeriodChange[{}],connectTimePeriodChange[{}]",
                lineId, callCountPeriodChange, connectCountPeriodChange, connectTimePeriodChange);
        simLimitService.changeSimLineConfig(lineId, callCountPeriodChange,connectCountPeriodChange, connectTimePeriodChange);

        return Result.ok();
    }
}
