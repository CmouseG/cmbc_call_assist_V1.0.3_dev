package com.guiji.ccmanager.service.impl;

import com.guiji.callcenter.dao.LineInfoMapper;
import com.guiji.callcenter.dao.ReportLineStatusMapper;
import com.guiji.callcenter.dao.StastisticReportLineShardingMapper;
import com.guiji.callcenter.dao.entity.*;
import com.guiji.callcenter.dao.entityext.LineMonitorRreport;
import com.guiji.callcenter.daoNoSharing.StastisticReportLineMapper;
import com.guiji.ccmanager.service.AuthService;
import com.guiji.ccmanager.service.LineReportService;
import com.guiji.ccmanager.utils.DateUtils;
import com.guiji.clm.api.LineMarketRemote;
import com.guiji.clm.model.SipLineVO;
import com.guiji.component.result.Result;
import com.guiji.utils.BeanUtil;
import io.swagger.models.auth.In;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class LineReportServiceImpl implements LineReportService {

    private final Logger logger = LoggerFactory.getLogger(LineReportServiceImpl.class);

    @Autowired
    StastisticReportLineMapper stastisticReportLineMapper;
    @Autowired
    StastisticReportLineShardingMapper stastisticReportLineShardingMapper;
    @Autowired
    ReportLineStatusMapper reportLineStatusMapper;
    @Autowired
    LineInfoMapper lineInfoMapper;
    @Autowired
    LineMarketRemote lineMarketRemote;
    @Autowired
    AuthService authService;

    @Override
    @Transactional
    public void statisticsReportLineCode() throws ParseException {

        //获取当前时间，分钟跟5取模
        Date date = new Date();
        String dateStr = new SimpleDateFormat("yyyy-MM-dd HH:mm").format(date);
        int minInt = Integer.valueOf(dateStr.substring(15));
        int moMin = minInt%5;
        String before = dateStr.substring(0,15);

        String endDateStr = before +(minInt-moMin);

        Date date5 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(endDateStr+":00");
        Date startTime = getDateMinutesAgo(date5,15);
        Date endTime = getDateMinutesAgo(date5,10);

        logger.info("开始统计report_line_code,startTime[{}],endTime[{}]",startTime,endTime);

        List<Integer> orgIds = authService.getAllOrgIds();
        //先删除，防止重跑
        stastisticReportLineMapper.deleteReportLineCode(endTime);
        List<ReportLineCode> list = stastisticReportLineShardingMapper.selectLineHangupCodeReport(startTime,endTime,orgIds);

        if(list!=null && list.size()>0){

            //todo 需要优化
            for(ReportLineCode reportLineCode:list){
                reportLineCode.setCreateTime(endTime);
            }

            stastisticReportLineMapper.insertReportLineCodeBatch(list);
        }

        logger.info("结束统计report_line_code,startTime[{}],endTime[{}]",startTime,endTime);

        logger.info("开始统计report_line_status,startTime[{}],endTime[{}]",startTime,endTime);
        //先删除，防止重跑
        stastisticReportLineMapper.deleteReportLineStatus(endTime);
        List<ReportLineStatus> listStatus = stastisticReportLineMapper.selectReportLineStatusFromCode(endTime);
        if(listStatus!=null && listStatus.size()>0){
            stastisticReportLineMapper.insertReportLineStatusBatch(listStatus);
        }
        logger.info("结束统计report_line_status,startTime[{}],endTime[{}]",startTime,endTime);
    }

    /**
     * 获取几分钟之前的时间日期
     */
    public Date getDateMinutesAgo(Date date,int minutesAgo){
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.MINUTE,-minutesAgo);
        return c.getTime();
    }


    public List<LineMonitorRreport> getLineMonitorReport(Integer lineId, Long userId, Date startTime, String orgCode, Integer authLevel) {

        if (lineId != null) {

            LineInfo lineInfo = lineInfoMapper.selectByPrimaryKey(lineId);
            LineMonitorRreport resultLineMonitorRreport = new LineMonitorRreport();
            resultLineMonitorRreport.setLineId(lineInfo.getLineId());
            resultLineMonitorRreport.setLineName(lineInfo.getLineName());
            resultLineMonitorRreport.setSip_ip(lineInfo.getSipIp());
            resultLineMonitorRreport.setSip_port(lineInfo.getSipPort());
            resultLineMonitorRreport.setAnswerNum(0);
            resultLineMonitorRreport.setTotalNum(0);
            resultLineMonitorRreport.setHigh(0f);
            resultLineMonitorRreport.setLow(0f);
            resultLineMonitorRreport.setRate(0f);
            resultLineMonitorRreport.setHistory(0f);

            List<LineMonitorRreport> reportList = stastisticReportLineMapper.getLineMonitorReportByLineId(lineId, startTime, orgCode, authLevel, userId);
            //统计半年的数据
            List<LineMonitorRreport> halfYearList = stastisticReportLineMapper.getLineMonitorReportByLineId(lineId, DateUtils.getHalfYearDate(), orgCode, authLevel, userId);
            if (reportList != null && reportList.size() > 0) {
                LineMonitorRreport report = reportList.get(0);
                resultLineMonitorRreport.setAnswerNum(report.getAnswerNum());
                resultLineMonitorRreport.setTotalNum(report.getTotalNum());
                resultLineMonitorRreport.setRate(report.getRate());
            }
            if (halfYearList != null && halfYearList.size() > 0) {
                LineMonitorRreport report = halfYearList.get(0);
                resultLineMonitorRreport.setHistory(report.getRate());
            }
            List<LineMonitorRreport> resultList = new ArrayList<>();
            resultList.add(resultLineMonitorRreport);

            return resultList;
        } else {

            Result.ReturnData<List<SipLineVO>> returnData = lineMarketRemote.queryUserSipLineList(String.valueOf(userId));
            List<SipLineVO> listLine = returnData.getBody();
            if(listLine!=null && listLine.size()>0){

                List<LineMonitorRreport> reportList = stastisticReportLineMapper.getLineMonitorReportByUserId(orgCode, authLevel, userId, startTime);
                List<LineMonitorRreport> halfYearList = stastisticReportLineMapper.getLineMonitorReportByUserId(orgCode, authLevel, userId, DateUtils.getHalfYearDate());

                List<LineMonitorRreport> retrunList = new ArrayList();

                for(SipLineVO sipLineVO:listLine){
                    LineMonitorRreport lineMonitorRreportReturn = new LineMonitorRreport();
                    lineMonitorRreportReturn.setLineName(sipLineVO.getLineName());
                    lineMonitorRreportReturn.setLineId(sipLineVO.getLineId());
                    lineMonitorRreportReturn.setHistory(0f);
                    lineMonitorRreportReturn.setRate(0f);
                    lineMonitorRreportReturn.setLow(0f);
                    lineMonitorRreportReturn.setHigh(0f);
                    lineMonitorRreportReturn.setAnswerNum(0);
                    lineMonitorRreportReturn.setTotalNum(0);

                    if (reportList != null && reportList.size() > 0) {
                        for (LineMonitorRreport lineMonitorRreport : reportList) {
                            if(lineMonitorRreportReturn.getLineId().intValue()== lineMonitorRreport.getLineId().intValue()){
                                BeanUtil.copyProperties(lineMonitorRreport,lineMonitorRreportReturn);
                            }
                        }

                    }
                    if (halfYearList != null && halfYearList.size() > 0) {
                        for (LineMonitorRreport halfReport : halfYearList) {
                            if (halfReport.getLineId().intValue() == lineMonitorRreportReturn.getLineId().intValue()) {
                                lineMonitorRreportReturn.setHistory(halfReport.getRate());
                            }
                        }
                    }
                    retrunList.add(lineMonitorRreportReturn);
                }
                return retrunList;
            }
            return null;

        }

    }

    @Override
    public Map getLineHangupDetail(Integer lineId, Date startTime, Date enTime, String orgCode,Integer userId, Integer authLevel) {

        List<Map> overViewMapList = stastisticReportLineMapper.getLineHangupCodeOverView(lineId, startTime, enTime, orgCode, userId, authLevel);
        if (overViewMapList != null && overViewMapList.size() > 0) {
            Map overViewMap = overViewMapList.get(0);

            List<Map> errorSumMapList = stastisticReportLineMapper.getLineHangupCodeErrorSum(lineId, startTime, enTime, orgCode, userId, authLevel);
            if(errorSumMapList!=null && errorSumMapList.size()>0){
                Map errorSumMap = new HashMap();
                for(Map map :errorSumMapList){
                    errorSumMap.put(map.get("hangup_code"),map.get("totalCalls"));
                }

                List<Map> errorNumsMapList = subPhoneNum(stastisticReportLineMapper.getLineHangupCodeErrorNums(lineId, startTime, enTime, orgCode, userId, authLevel));
                List<Map> errorNumsMapListCancel = subPhoneNum(stastisticReportLineMapper.getLineHangupCodeErrorNumsCancel(lineId, startTime, enTime, orgCode, userId, authLevel));
                if(errorNumsMapList!=null && errorNumsMapListCancel != null && errorNumsMapListCancel.size()>0){
                    errorNumsMapList.addAll(errorNumsMapListCancel);
                }

                overViewMap.put("errorSum",errorSumMap);
                overViewMap.put("errorNums",errorNumsMapList);
            }

            Map resultMap = new HashMap();
            resultMap.put("callRecordSum",overViewMap);
            return resultMap;
        }

        return null;
    }


    /**
     * 将列表中的电话号码，截取9个，最多显示9个
     * @param numsMapLis
     * @return
     */
    private List<Map> subPhoneNum(List<Map> numsMapLis){

        if(numsMapLis == null || numsMapLis.size() ==0){
            return numsMapLis;
        }
        List<Map> resultList = new ArrayList<>();
        for (Map map : numsMapLis) {
            String phoneNums = (String) map.get("num");
            String[] arr = phoneNums.split(",");
            if (arr.length > 9) {
                String num = arr[0] + "," + arr[1] + "," + arr[2] + "," + arr[3] + "," + arr[4] + "," + arr[5] + "," + arr[6] + "," + arr[7] + "," + arr[8];
                map.put("num", num);
            }
            resultList.add(map);
        }
        return resultList;
    }


}
