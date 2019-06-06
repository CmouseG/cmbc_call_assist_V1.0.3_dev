package com.guiji.ccmanager.controller;

import com.guiji.auth.api.IAuth;
import com.guiji.callcenter.dao.entity.ErrorMatch;
import com.guiji.callcenter.dao.entityext.CallCountHour;
import com.guiji.callcenter.dao.entityext.DashboardOverView;
import com.guiji.callcenter.dao.entityext.IntentCount;
import com.guiji.callcenter.dao.entityext.ReasonCount;
import com.guiji.ccmanager.constant.Constant;
import com.guiji.ccmanager.service.StatisticService;
import com.guiji.ccmanager.vo.DashboardOverViewRes;
import com.guiji.component.result.Result;
import com.guiji.user.dao.entity.SysUser;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import jxl.Workbook;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.write.*;
import jxl.write.biff.RowsExceededException;
import lombok.val;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.lang.Boolean;
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
    private final Logger log = LoggerFactory.getLogger(StatisticController.class);
    @Autowired
    StatisticService statisticService;
    @Autowired
    CallDetailController callDetailService;
    @Autowired
    IAuth iAuth;

/*    @ApiOperation(value = "几条通话线路,总共几路并发")
    @GetMapping(value = "getLineCountAndConcurrent")
    public Map getLineCountAndConcurrent(@RequestHeader Long userId, @RequestHeader Boolean isSuperAdmin, @RequestHeader String orgCode){
        return  statisticService.getLineCountAndConcurrent(userId,isSuperAdmin,orgCode);
    }*/


    @ApiOperation(value = "首页Dashboard,通话记录总数，接通数，未接通数，接通率,总通话时长，通话30秒以上数量，通话10-30秒数量，通话5-10秒数量")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "startDate", value = "开始时间,yyyy-MM-dd格式", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "endDate", value = "结束时间,yyyy-MM-dd格式", dataType = "String", paramType = "query")
    })
    @GetMapping(value = "getDashboardOverView")
    public DashboardOverViewRes getDashboardOverView(
            @NotNull(message = "startDate不能为空") @Pattern(regexp = "(^\\d{4}-\\d{2}-\\d{2}$)", message = "日期格式错误") String startDate,
            @NotNull(message = "endDate不能为空") @Pattern(regexp = "(^\\d{4}-\\d{2}-\\d{2}$)", message = "日期格式错误") String endDate,String tempId,
            @RequestHeader Integer authLevel,@RequestHeader String orgCode,@RequestHeader Long userId) throws ParseException {

        List<DashboardOverView> list = statisticService.getDashboardOverView(authLevel,userId,orgCode,
                startDate, endDate, StringUtils.isNotBlank(tempId)? tempId: null);
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
        int allConnect = notConnect+connect;
        result.setAllCalls(allConnect);
        if(allConnect == 0 || connect ==0){
            result.setConnectRate(0);
        }else {
            result.setConnectRate(getFloat4Precision((float) connect / (notConnect + connect)));
        }
        return result;
    }

    @ApiOperation(value = "首页饼图和折线图,量化分析-拨打结果统计。按标签统计数量。")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "startDate", value = "开始时间,yyyy-MM-dd格式", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "endDate", value = "结束时间,yyyy-MM-dd格式", dataType = "String", paramType = "query")
    })
    @GetMapping(value = "getIntentCount")
    public Result.ReturnData<Object> getIntentCount(String startDate, String endDate, String tempId,String queryUserId,@RequestHeader Integer authLevel,
                                                    @RequestHeader String orgCode,@RequestHeader Long userId) throws ParseException {

        if(StringUtils.isBlank(endDate)){
            endDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        }
        if(StringUtils.isBlank(startDate)){
            startDate = endDate;
        }

        String regEx = "^\\d{4}-\\d{2}-\\d{2}$";
        if(!startDate.matches(regEx) || !endDate.matches(regEx)){
            return Result.error(Constant.ERROR_DATEFORMAT);
        }
        Integer queryUser = null;
        if(StringUtils.isNotEmpty(queryUserId)){
            queryUser = Integer.valueOf(queryUserId);
        }

        List<Map<String,Object>> list = statisticService.getIntentCount(authLevel,userId,orgCode, startDate, endDate,
                StringUtils.isNotBlank(tempId)? tempId: null,queryUser);

        return Result.ok(list);

    }

    @ApiOperation(value = "量化分析-接通分析，按小时接通率、接通数、通话时长")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "startDate", value = "开始时间,yyyy-MM-dd格式", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "endDate", value = "结束时间,yyyy-MM-dd格式", dataType = "String", paramType = "query")
    })
    @GetMapping(value = "getConnectDataHour")
    public Result.ReturnData<Object> getConnectDataHour(String startDate, String endDate, String tempId,@RequestHeader Integer authLevel,
                                                        @RequestHeader String orgCode,@RequestHeader Long userId) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        if(StringUtils.isBlank(endDate)){
            endDate = sdf.format(new Date());
        }
        if(StringUtils.isBlank(startDate)){
            startDate = endDate;
        }
        String regEx = "^\\d{4}-\\d{2}-\\d{2}$";
        if(!startDate.matches(regEx) || !endDate.matches(regEx)){
            return Result.error(Constant.ERROR_DATEFORMAT);
        }
        List<CallCountHour> list = statisticService.getConnectDataHour(authLevel,userId,orgCode, sdf.parse(startDate), sdf.parse(endDate),
                StringUtils.isNotBlank(tempId)? tempId: null);
        return Result.ok(list);
    }


    @ApiOperation(value = "量化分析-通话状态分析")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "startDate", value = "开始时间,yyyy-MM-dd格式", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "endDate", value = "结束时间,yyyy-MM-dd格式", dataType = "String", paramType = "query")
    })
    @GetMapping(value = "getConnectReasonDay")
    public Result.ReturnData<Object> getConnectReasonDay(String startDate, String endDate, String tempId,@RequestHeader Integer authLevel,
                                                         @RequestHeader String orgCode,@RequestHeader Long userId) throws ParseException {

        if(StringUtils.isBlank(endDate)){
            endDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        }
        if(StringUtils.isBlank(startDate)){
            startDate = endDate;
        }
        String regEx = "^\\d{4}-\\d{2}-\\d{2}$";
        if(!startDate.matches(regEx) || !endDate.matches(regEx)){
            return Result.error(Constant.ERROR_DATEFORMAT);
        }
        List<ReasonCount> list = statisticService.getConnectReasonDay(authLevel,userId,orgCode, startDate, endDate, StringUtils.isNotBlank(tempId)? tempId: null);

        List<String> typeList = callDetailService.getFtypes();
        if(!typeList.contains("已接通")){
            typeList.add("已接通");
        }
        if(!typeList.contains("其他")){
            typeList.add("其他");
        }

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date sDate = sdf.parse(startDate);
        Date eDate = sdf.parse(endDate);

        int days = differentDaysByMillisecond(sDate, eDate);

        Calendar cal = new GregorianCalendar();
        cal.setTime(sDate);

        List<Map<String,Object>> resList = new ArrayList<>();

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
                        if (typeList.contains(reason) && !reason.equals("其他")) {
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

        List<ErrorMatch> listErrorMatch = statisticService.getErrorMaths();

        //对中文的结果，进行转换，转成英文的
        List<Map<String,List<Map<String,ReasonDetail>>>> returnList = new ArrayList();
        for(Map<String,Object> map:resList){
            String callDate = (String) map.get("callDate");
            Map<String,List<Map<String,ReasonDetail>>> dateMap = new HashMap();
            List<Map<String,ReasonDetail>> listEnnameMap = new ArrayList();
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                String key = entry.getKey();
                Map<String,ReasonDetail> mapEnName = new HashMap();
                if(!key.equals("callDate") && !key.equals("allCount") && !key.equals("其他")){
                    ReasonDetail reasonDetail = new ReasonDetail();
                    reasonDetail.setName(key);
                    reasonDetail.setCount((Integer) entry.getValue());
                    for(ErrorMatch errorMatch:listErrorMatch){
                        if(errorMatch.getErrorName().equals(key)){
                            mapEnName.put(errorMatch.getEnName(),reasonDetail);
                        }
                    }
                }else if(key.equals("allCount")){
                    mapEnName.put("allCount",new ReasonDetail("allCount",(Integer) entry.getValue()));
                }else if(key.equals("其他")){
                    mapEnName.put("other",new ReasonDetail("其他",(Integer) entry.getValue()));
                }
                if(mapEnName.size()>0){
                    listEnnameMap.add(mapEnName);
                }
            }
            dateMap.put(callDate,listEnnameMap);
            returnList.add(dateMap);
        }

        return Result.ok(returnList);
    }

    public static int differentDaysByMillisecond(Date date1, Date date2) {
        int days = (int) ((date2.getTime() - date1.getTime()) / (1000 * 3600 * 24));
        return days;
    }
    public static float getFloat4Precision(float f){
        return Float.valueOf(new DecimalFormat("#.0000").format(f));
    }


    class ReasonDetail{
        String name;
        int count;
        public ReasonDetail() {
        }
        public ReasonDetail(String name, int count) {
            this.name = name;
            this.count = count;
        }
        public String getName() {
            return name;
        }
        public void setName(String name) {
            this.name = name;
        }
        public int getCount() {
            return count;
        }
        public void setCount(int count) {
            this.count = count;
        }
    }

}
