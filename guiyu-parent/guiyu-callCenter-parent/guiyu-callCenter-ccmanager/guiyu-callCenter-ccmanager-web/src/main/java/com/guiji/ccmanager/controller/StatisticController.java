package com.guiji.ccmanager.controller;

import com.guiji.auth.api.IAuth;
import com.guiji.callcenter.dao.entityext.CallCountHour;
import com.guiji.callcenter.dao.entityext.DashboardOverView;
import com.guiji.callcenter.dao.entityext.IntentCount;
import com.guiji.callcenter.dao.entityext.ReasonCount;
import com.guiji.ccmanager.service.StatisticService;
import com.guiji.ccmanager.vo.DashboardOverViewRes;
import com.guiji.component.result.Result;
import com.guiji.user.dao.entity.SysUser;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

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
    @Autowired
    CallDetailController callDetailService;
    @Autowired
    IAuth iAuth;


    @ApiOperation(value = "首页Dashboard,通话记录总数，接通数，未接通数，接通率,总通话时长，通话30秒以上数量，通话10-30秒数量，通话5-10秒数量")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "startDate", value = "开始时间,yyyy-MM-dd格式", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "endDate", value = "结束时间,yyyy-MM-dd格式", dataType = "String", paramType = "query")
    })
    @GetMapping(value = "getDashboardOverView")
    public DashboardOverViewRes getDashboardOverView(@NotNull(message = "startDate不能为空") @Pattern(regexp = "(^\\d{4}-\\d{2}-\\d{2}$)", message = "日期格式错误") String startDate,
                                                  @NotNull(message = "endDate不能为空") @Pattern(regexp = "(^\\d{4}-\\d{2}-\\d{2}$)", message = "日期格式错误") String endDate,
                                                  @RequestHeader Long userId, @RequestHeader Boolean isSuperAdmin) throws ParseException {

        List<DashboardOverView> list = statisticService.getDashboardOverView(isSuperAdmin ? null : String.valueOf(userId), startDate, endDate);
        DashboardOverViewRes result = new DashboardOverViewRes();

        int connect =0;
        int notConnect =0;
        int duration5 =0;
        int duration10 =0;
        int duration30 =0;
        int durationAll =0;
        if(list!=null && list.size()>0){
            for(DashboardOverView dashboardOverView :list){
                connect += dashboardOverView.getConnect();
                notConnect += dashboardOverView.getNotConnect();
                duration5 += dashboardOverView.getDuration5();
                duration10 += dashboardOverView.getDuration10();
                duration30 += dashboardOverView.getDuration30();
                durationAll += dashboardOverView.getDurationAll();
            }
        }

        result.setDuration5(duration5);
        result.setNotConnect(notConnect);
        result.setConnect(connect);
        result.setDuration10(duration10);
        result.setDuration5(duration5);
        result.setDuration30(duration30);
        result.setDurationAll(durationAll/60);
        result.setAllCalls(notConnect+connect);
        result.setConnectRate(getFloat4Precision((float)connect/(notConnect+connect)));

        return result;
    }

    @ApiOperation(value = "首页饼图和折线图,量化分析-拨打结果统计。按标签统计数量。")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "startDate", value = "开始时间,yyyy-MM-dd格式", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "endDate", value = "结束时间,yyyy-MM-dd格式", dataType = "String", paramType = "query")
    })
    @GetMapping(value = "getIntentCount")
    public List<Map> getIntentCount(@Pattern(regexp = "(^\\d{4}-\\d{2}-\\d{2}$)", message = "日期格式错误") String startDate,
                                    @Pattern(regexp = "(^\\d{4}-\\d{2}-\\d{2}$)", message = "日期格式错误") String endDate,
                                    @RequestHeader Long userId, @RequestHeader Boolean isSuperAdmin) throws ParseException {

        if(StringUtils.isBlank(endDate)){
            endDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        }
        if(StringUtils.isBlank(startDate)){
            startDate = endDate;
        }

        String[] arr = {"A","B","C","D","E","F","W"};
        Result.ReturnData<SysUser> result =  iAuth.getUserById(userId);
        String intent = result.getBody().getIntenLabel();
        if(StringUtils.isNotBlank(intent)){
            arr = intent.split(",");
        }
        List<String> typeList = Arrays.asList(arr);

        List<IntentCount> list = statisticService.getIntentCount(isSuperAdmin ? null : String.valueOf(userId), startDate, endDate);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date sDate = sdf.parse(startDate);
        Date eDate = sdf.parse(endDate);

        int days = differentDaysByMillisecond(sDate, eDate);

        Calendar cal = new GregorianCalendar();
        cal.setTime(sDate);

        List<Map> resList = new ArrayList<>();

        for (int i = 0; i <= days; i++) {
            String startDateStr = sdf.format(sDate);
            Map map = new HashMap();
            map.put("callDate", startDateStr);

            for (String type : typeList) {
                map.put(type, 0);
            }

            int connectCount =0;
            int notConnectCount =0;

            if (list != null && list.size() > 0) {
                for (IntentCount intentCount : list) {
                    String callDate = intentCount.getCallDate();
                    if (callDate.equals(startDateStr)) {
                        String intentIn = intentCount.getIntent();
                        map.put(intentIn, intentCount.getCallCount());
                        if(intentIn.equals("F") || intentIn.equals("W") ){
                            notConnectCount+=intentCount.getCallCount();
                        }else{
                            connectCount+=intentCount.getCallCount();
                        }
                    }
                }
            }
            int allCallsCount = connectCount+notConnectCount;
            map.put("connectCount", connectCount);
            map.put("notConnectCount", notConnectCount);
            map.put("allCallsCount", allCallsCount);

            resList.add(map);
            cal.add(Calendar.DATE, 1);
            sDate = cal.getTime();
        }
        return resList;

    }

    @ApiOperation(value = "量化分析-接通分析，按小时接通率、接通数、通话时长")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "startDate", value = "开始时间,yyyy-MM-dd格式", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "endDate", value = "结束时间,yyyy-MM-dd格式", dataType = "String", paramType = "query")
    })
    @GetMapping(value = "getConnectDataHour")
    public List<CallCountHour> getConnectDataHour(@Pattern(regexp = "(^\\d{4}-\\d{2}-\\d{2}$)", message = "日期格式错误") String startDate,
                                                  @Pattern(regexp = "(^\\d{4}-\\d{2}-\\d{2}$)", message = "日期格式错误") String endDate,
                                                  @RequestHeader Long userId, @RequestHeader Boolean isSuperAdmin) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        if(StringUtils.isBlank(endDate)){
            endDate = sdf.format(new Date());
        }
        if(StringUtils.isBlank(startDate)){
            startDate = endDate;
        }
        return statisticService.getConnectDataHour(isSuperAdmin ? null : String.valueOf(userId), sdf.parse(startDate), sdf.parse(endDate));
    }


    @ApiOperation(value = "量化分析-通话状态分析")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "startDate", value = "开始时间,yyyy-MM-dd格式", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "endDate", value = "结束时间,yyyy-MM-dd格式", dataType = "String", paramType = "query")
    })
    @GetMapping(value = "getConnectReasonDay")
    public List<Map> getConnectReasonDay(@Pattern(regexp = "(^\\d{4}-\\d{2}-\\d{2}$)", message = "日期格式错误") String startDate,
                                         @Pattern(regexp = "(^\\d{4}-\\d{2}-\\d{2}$)", message = "日期格式错误") String endDate,
                                         @RequestHeader Long userId, @RequestHeader Boolean isSuperAdmin) throws ParseException {

        if(StringUtils.isBlank(endDate)){
            endDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        }
        if(StringUtils.isBlank(startDate)){
            startDate = endDate;
        }
        List<ReasonCount> list = statisticService.getConnectReasonDay(isSuperAdmin ? null : String.valueOf(userId), startDate, endDate);

        List<String> typeList = callDetailService.getFtypes();
        typeList.add("已接通");
        typeList.add("其他");

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date sDate = sdf.parse(startDate);
        Date eDate = sdf.parse(endDate);

        int days = differentDaysByMillisecond(sDate, eDate);

        Calendar cal = new GregorianCalendar();
        cal.setTime(sDate);

        List<Map> resList = new ArrayList<>();

        for (int i = 0; i <= days; i++) {
            String startDateStr = sdf.format(sDate);
            Map map = new HashMap();
            map.put("callDate", startDateStr);

            for (String type : typeList) {
                map.put(type, 0);
            }

            int allCount = 0;

            if (list != null && list.size() > 0) {
                for (ReasonCount reasonCount : list) {
                    String callDate = reasonCount.getCallDate();
                    if (callDate.equals(startDateStr)) {
                        String reason = reasonCount.getReason();
                        if (typeList.contains(reason)) {
                            map.put(reason, reasonCount.getCallCount());
                        } else {
                            int other = (int) map.get("其他");
                            map.put("其他", other + reasonCount.getCallCount());
                        }
                        allCount+=reasonCount.getCallCount();
                    }
                }
            }
            map.put("allCount",allCount);
            resList.add(map);
            cal.add(Calendar.DATE, 1);
            sDate = cal.getTime();
        }
        return resList;
    }

    public static int differentDaysByMillisecond(Date date1, Date date2) {
        int days = (int) ((date2.getTime() - date1.getTime()) / (1000 * 3600 * 24));
        return days;
    }
    public static float getFloat4Precision(float f){
        return Float.valueOf(new DecimalFormat("#.0000").format(f));
    }

}
