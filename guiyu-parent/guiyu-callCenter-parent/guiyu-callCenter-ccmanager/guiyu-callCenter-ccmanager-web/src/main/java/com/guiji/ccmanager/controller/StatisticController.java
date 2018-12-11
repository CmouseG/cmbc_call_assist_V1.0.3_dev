package com.guiji.ccmanager.controller;

import com.guiji.callcenter.dao.entityext.DashboardOverView;
import com.guiji.ccmanager.service.StatisticService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.text.ParseException;
import java.util.List;
import java.util.Map;

/**
 * @Auther: 黎阳
 * @Date: 2018/12/5 0005 14:09
 * @Description: 量化分析，统计
 */
@Validated
@RestController
public class StatisticController {

    @Autowired
    StatisticService statisticService;

    @ApiOperation(value = "折线图表示每天每个意向标签的数量")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "startDate", value = "开始时间,yyyy-MM-dd格式", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "endDate", value = "结束时间,yyyy-MM-dd格式", dataType = "String", paramType = "query")
    })
    @GetMapping(value = "getIntentCountChart")
    public List<Map> getIntentCountChart(@NotNull(message = "startDate不能为空") @Pattern(regexp = "(^\\d{4}-\\d{2}-\\d{2}$)", message = "日期格式错误") String startDate,
                                          @NotNull(message = "endDate不能为空") @Pattern(regexp = "(^\\d{4}-\\d{2}-\\d{2}$)", message = "日期格式错误") String endDate,
                                          @RequestHeader Long userId, @RequestHeader Boolean isSuperAdmin) throws ParseException {

        return statisticService.getIntentCountOnTime(isSuperAdmin ? null : userId, startDate, endDate);
    }

    @ApiOperation(value = "首页Dashboard,通话记录总数，接通数，未接通数，接通率,总通话时长，通话30秒以上数量，通话10-30秒数量，通话5-10秒数量")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "startDate", value = "开始时间,yyyy-MM-dd格式", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "endDate", value = "结束时间,yyyy-MM-dd格式", dataType = "String", paramType = "query")
    })
    @GetMapping(value = "getDashboardOverView")
    public List<DashboardOverView> getDashboardOverView(@NotNull(message = "startDate不能为空") @Pattern(regexp = "(^\\d{4}-\\d{2}-\\d{2}$)", message = "日期格式错误") String startDate,
                                                        @NotNull(message = "endDate不能为空") @Pattern(regexp = "(^\\d{4}-\\d{2}-\\d{2}$)", message = "日期格式错误") String endDate,
                                                        @RequestHeader Long userId, @RequestHeader Boolean isSuperAdmin) throws ParseException {

        return statisticService.getDashboardOverView(isSuperAdmin ? null : userId, startDate, endDate);
    }

}
