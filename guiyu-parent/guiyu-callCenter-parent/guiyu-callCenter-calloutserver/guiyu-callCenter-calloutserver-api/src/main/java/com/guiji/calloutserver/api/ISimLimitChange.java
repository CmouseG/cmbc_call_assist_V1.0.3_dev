package com.guiji.calloutserver.api;

import com.guiji.component.result.Result;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * author:liyang
 * Date:2019/6/1 18:16
 * Description:
 */
@FeignClient("guiyu-callcenter-calloutserver")
public interface ISimLimitChange {

    @ApiOperation(value = "sim卡限制信息修改")
    @GetMapping("/changeSimLineConfig")
    Result.ReturnData changeSimLineConfig(@RequestParam("lineId") Integer lineId,
                                          @RequestParam("callCountPeriodChange") Boolean callCountPeriodChange,
                                          @RequestParam("connectCountPeriodChange")  Boolean connectCountPeriodChange,
                                          @RequestParam("connectTimePeriodChange") Boolean connectTimePeriodChange);

}
