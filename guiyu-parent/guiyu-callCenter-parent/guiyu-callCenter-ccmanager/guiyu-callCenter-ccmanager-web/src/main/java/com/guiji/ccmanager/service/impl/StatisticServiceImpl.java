package com.guiji.ccmanager.service.impl;

import com.guiji.auth.api.IAuth;
import com.guiji.callcenter.dao.StatisticMapper;
import com.guiji.callcenter.dao.entity.ErrorMatch;
import com.guiji.callcenter.dao.entityext.CallCountHour;
import com.guiji.callcenter.dao.entityext.DashboardOverView;
import com.guiji.callcenter.dao.entityext.IntentCount;
import com.guiji.callcenter.dao.entityext.ReasonCount;
import com.guiji.ccmanager.service.AuthService;
import com.guiji.ccmanager.service.StatisticService;
import com.guiji.component.result.Result;
import com.guiji.user.dao.entity.SysUser;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.guiji.ccmanager.controller.StatisticController.differentDaysByMillisecond;

/**
 * @Auther: 黎阳
 * @Date: 2018/12/5 0005 15:42
 * @Description:
 */
@Slf4j
@Service
public class StatisticServiceImpl implements StatisticService {

    @Autowired
    StatisticMapper statisticMapper;
    @Autowired
    IAuth iAuth;
    @Autowired
    AuthService authService;

    @Override
    public List<DashboardOverView> getDashboardOverView(int authLevel,long userId,String orgCode, String startDate, String endDate, String tempId) {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String today = sdf.format(new Date());

        List<DashboardOverView> listResult = new ArrayList<DashboardOverView>();


        //非今天
        if(!startDate.equals(today)){
            List<DashboardOverView> listAgoDurationAll = statisticMapper.getDashboardOverViewAgoDurationAll(startDate,endDate,orgCode, tempId,authLevel,userId);
            List<DashboardOverView> listAgoNotConnect = statisticMapper.getDashboardOverViewAgoNotConnect(startDate,endDate,orgCode, tempId,authLevel,userId);
            List<DashboardOverView> listAgoConnect = statisticMapper.getDashboardOverViewAgoConnect(startDate,endDate,orgCode, tempId,authLevel,userId);
            List<DashboardOverView> listAgoDuration5 = statisticMapper.getDashboardOverViewAgoDuration5(startDate,endDate,orgCode, tempId,authLevel,userId);
            List<DashboardOverView> listAgoDuration10 = statisticMapper.getDashboardOverViewAgoDuration10(startDate,endDate,orgCode, tempId,authLevel,userId);
            List<DashboardOverView> listAgoDuration30 = statisticMapper.getDashboardOverViewAgoDuration30(startDate,endDate,orgCode, tempId,authLevel,userId);
            if(listAgoDurationAll!=null && listAgoDurationAll.size()>0){
                for(DashboardOverView durationAll:listAgoDurationAll){
                    if(listAgoNotConnect!=null && listAgoNotConnect.size()>0) {
                        for (DashboardOverView notConnect:listAgoNotConnect) {
                            if(durationAll.getCallDate().equals(notConnect.getCallDate())){
                                durationAll.setNotConnect(notConnect.getNotConnect());
                            }
                        }
                    }
                    if(listAgoConnect!=null && listAgoConnect.size()>0) {
                        for (DashboardOverView connect:listAgoConnect) {
                            if(durationAll.getCallDate().equals(connect.getCallDate())){
                                durationAll.setConnect(connect.getConnect());
                            }
                        }
                    }
                    if(listAgoDuration5!=null && listAgoDuration5.size()>0) {
                        for (DashboardOverView duration5:listAgoDuration5) {
                            if(durationAll.getCallDate().equals(duration5.getCallDate())){
                                durationAll.setDuration5(duration5.getDuration5());
                            }
                        }
                    }
                    if(listAgoDuration10!=null && listAgoDuration10.size()>0) {
                        for (DashboardOverView duration10:listAgoDuration10) {
                            if(durationAll.getCallDate().equals(duration10.getCallDate())){
                                durationAll.setDuration10(duration10.getDuration10());
                            }
                        }
                    }
                    if(listAgoDuration30!=null && listAgoDuration30.size()>0) {
                        for (DashboardOverView duration30:listAgoDuration30) {
                            if(durationAll.getCallDate().equals(duration30.getCallDate())){
                                durationAll.setDuration30(duration30.getDuration30());
                            }
                        }
                    }
                }
            }
            listResult.addAll(listAgoDurationAll);
        }

        //今天
        if(endDate.equals(today)){
            List<DashboardOverView> listTodayDurationAll = statisticMapper.getDashboardOverViewTodayDurationAll(orgCode, tempId,authLevel,userId);
            List<DashboardOverView> listTodayNotConnect = statisticMapper.getDashboardOverViewTodayNotConnect(orgCode, tempId,authLevel,userId);
            List<DashboardOverView> listTodayConnect = statisticMapper.getDashboardOverViewTodayConnect(orgCode, tempId,authLevel,userId);
            List<DashboardOverView> listTodayDuration5 = statisticMapper.getDashboardOverViewTodayDuration5(orgCode, tempId,authLevel,userId);
            List<DashboardOverView> listTodayDuration10 = statisticMapper.getDashboardOverViewTodayDuration10(orgCode, tempId,authLevel,userId);
            List<DashboardOverView> listTodayDuration30 = statisticMapper.getDashboardOverViewTodayDuration30(orgCode, tempId,authLevel,userId);

            if(listTodayDurationAll!=null && listTodayDurationAll.size()>0){
                for(DashboardOverView durationAll:listTodayDurationAll){
                    if(listTodayNotConnect!=null && listTodayNotConnect.size()>0) {
                        for (DashboardOverView notConnect:listTodayNotConnect) {
                            if(durationAll.getCallDate().equals(notConnect.getCallDate())){
                                durationAll.setNotConnect(notConnect.getNotConnect());
                            }
                        }
                    }
                    if(listTodayConnect!=null && listTodayConnect.size()>0) {
                        for (DashboardOverView connect:listTodayConnect) {
                            if(durationAll.getCallDate().equals(connect.getCallDate())){
                                durationAll.setConnect(connect.getConnect());
                            }
                        }
                    }
                    if(listTodayDuration5!=null && listTodayDuration5.size()>0) {
                        for (DashboardOverView duration5:listTodayDuration5) {
                            if(durationAll.getCallDate().equals(duration5.getCallDate())){
                                durationAll.setDuration5(duration5.getDuration5());
                            }
                        }
                    }
                    if(listTodayDuration10!=null && listTodayDuration10.size()>0) {
                        for (DashboardOverView duration10:listTodayDuration10) {
                            if(durationAll.getCallDate().equals(duration10.getCallDate())){
                                durationAll.setDuration10(duration10.getDuration10());
                            }
                        }
                    }
                    if(listTodayDuration30!=null && listTodayDuration30.size()>0) {
                        for (DashboardOverView duration30:listTodayDuration30) {
                            if(durationAll.getCallDate().equals(duration30.getCallDate())){
                                durationAll.setDuration30(duration30.getDuration30());
                            }
                        }
                    }
                }
            }
            listResult.addAll(listTodayDurationAll);
        }

        return listResult;
    }

    @Override
    public List<Map<String, Object>> getIntentCount(int authLevel,long userId,String orgCode, String startDate,
                                                    String endDate, String tempId, Integer queryUser) throws ParseException {

        String[] arr = {"A","B","C","D","E","F","W","N"};
        try{
            Result.ReturnData<SysUser> result =  iAuth.getUserById(userId);
            String intent = result.getBody().getIntenLabel();
            if(StringUtils.isNotBlank(intent)){
                arr = intent.split(",");
            }
        }catch (Exception e){
            log.error("iAuth.getUserById userId[{}] has error :"+e,userId);
        }
        List<String> typeList = new ArrayList<>(Arrays.asList(arr));
        if(!typeList.contains("W")){
            typeList.add("W");
        }
        if(!typeList.contains("N")){
            typeList.add("N");
        }

        List<IntentCount> list = getIntentCountList(authLevel,userId,orgCode, startDate, endDate, tempId, queryUser);

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

    public List<IntentCount> getIntentCountList(int authLevel, long userId, String orgCode, String startDate, String endDate, String tempId, Integer queryUser) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String today = sdf.format(new Date());

        List<IntentCount> listResult = new ArrayList<IntentCount>();

        //非今天
        if(!startDate.equals(today)){
            List<IntentCount> listAgo = statisticMapper.getIntentCountAgo(startDate,endDate,orgCode,tempId,authLevel,userId,queryUser);
            listResult.addAll(listAgo);
        }

        //今天
        if(endDate.equals(today)){
            List<IntentCount> listToday = statisticMapper.getIntentCountToday(orgCode,tempId,authLevel,userId,queryUser);
            listResult.addAll(listToday);
        }

        return listResult;
    }

    @Override
    public List<CallCountHour> getConnectDataHour(int authLevel,long userId,String orgCode, Date startDate, Date endDate, String tempId){
        List<CallCountHour> list = statisticMapper.getConnectDataHour(startDate,endDate,orgCode,tempId,authLevel,userId);
        return list;
    }

    @Override
    public List<ReasonCount> getConnectReasonDay(int authLevel,long userId,String orgCode, String startDate, String endDate, String tempId){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String today = sdf.format(new Date());

        List<ReasonCount> listResult = new ArrayList<ReasonCount>();

        //非今天
        if(!startDate.equals(today)){
            List<ReasonCount> listAgo = statisticMapper.getReasonCountAgo(startDate,endDate,orgCode,tempId,authLevel,userId);
            listResult.addAll(listAgo);
        }

        //今天
        if(endDate.equals(today)){
            List<ReasonCount> listToday = statisticMapper.getReasonCountToday(orgCode,tempId,authLevel,userId);
            listResult.addAll(listToday);
        }

        return listResult;
    }

    @Override
    public List<ErrorMatch> getErrorMaths() {

        return statisticMapper.getErrorMaths();
    }

//    @Override
//    public Map getLineCountAndConcurrent(Long userId, Boolean isSuperAdmin, String orgCode) {
//
//        if(authService.isAgentOrCompanyAdmin(userId) ){//企业管理员 或者是代理商
//            return statisticMapper.getLineCountAndConcurrent(null,orgCode+"%");
//        }else{
//            return statisticMapper.getLineCountAndConcurrent(isSuperAdmin ? null:String.valueOf(userId),null);
//        }
//    }

}
