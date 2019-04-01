package com.guiji.ccmanager.controller;

import com.guiji.callcenter.dao.entityext.CallOutPlanRegistration;
import com.guiji.ccmanager.service.AuthService;
import com.guiji.ccmanager.service.BatchExportService;
import com.guiji.ccmanager.service.CallDetailService;
import com.guiji.ccmanager.utils.DateUtils;
import com.guiji.ccmanager.vo.CallRecordListReq;
import com.guiji.component.jurisdiction.Jurisdiction;
import com.guiji.component.result.Result;
import com.guiji.dispatch.api.IDispatchPlanOut;
import com.guiji.dispatch.model.ExportFileDto;
import com.guiji.dispatch.model.ExportFileRecordVo;
import com.guiji.utils.DateUtil;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import jxl.write.*;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.lang.Boolean;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


@RestController
public class BatchExportController {

    private final Logger log = LoggerFactory.getLogger(BatchExportController.class);

    public static ExecutorService executor = Executors.newFixedThreadPool(5) ;

    @Autowired
    BatchExportService batchExportService;
    @Autowired
    CallDetailService callDetailService;
    @Autowired
    IDispatchPlanOut iDispatchPlanOut;
    @Autowired
    AuthService authService;


    @ApiOperation(value = "批量导出通话记录，从第几条开始，到多少条")
    @Jurisdiction("callCenter_callHistory_batcnExportData")
    @PostMapping(value = "batchExportCallRecord")
    public String batchExportCallRecord(@RequestBody CallRecordListReq callRecordListReq,
                                        @RequestHeader Long userId, @RequestHeader Integer authLevel,
                                        @RequestHeader String orgCode, @RequestHeader Integer isDesensitization) {

        log.info("get request 批量导出 batchExportCallRecord，callRecordListReq[{}]",callRecordListReq);

        Boolean checkAll = callRecordListReq.getCheckAll();

        Date end = null;
        Date start = null;
        if(StringUtils.isNotBlank(callRecordListReq.getStartDate())){
            start = DateUtil.stringToDate(callRecordListReq.getStartDate(),"yyyy-MM-dd HH:mm:ss");
        }
        if(StringUtils.isNotBlank(callRecordListReq.getEndDate())){
            end = DateUtil.stringToDate(callRecordListReq.getEndDate(),"yyyy-MM-dd HH:mm:ss");
        }

        int totalNum = 0;
        if (checkAll == null || !checkAll) {//不是全选
            List<String> callIds = callRecordListReq.getIncludeList();
            if (callIds!=null || callIds.size()>0) {
                totalNum = callIds.size();
                callRecordListReq.setStartCount("1");
                callRecordListReq.setEndCount(String.valueOf(totalNum));

            }else{
                return "缺少参数!";
            }

        }else{//全选
            totalNum = batchExportService.countTotalNum(start, end,authLevel,String.valueOf(userId),orgCode, callRecordListReq);
            callRecordListReq.setStartCount("1");
            if(totalNum>1000000){
                totalNum=1000000;
            }
            callRecordListReq.setEndCount(String.valueOf(totalNum));
        }

        ExportFileDto exportFileDto = new ExportFileDto();
        exportFileDto.setBusiId("batchExportCallRecord");
        exportFileDto.setBusiType("03");
        exportFileDto.setFileType(1);
        exportFileDto.setTotalNum(totalNum);
        exportFileDto.setUserId(String.valueOf(userId));
        exportFileDto.setOrgCode(orgCode);
        exportFileDto.setCreateName(authService.getUserName(userId));
        exportFileDto.setCreateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        Result.ReturnData<ExportFileRecordVo> returnData = iDispatchPlanOut.addExportFile(exportFileDto);
        log.info("请求iDispatchPlanOut.addExportFile返回returnData[{}]",returnData);
        if(returnData.success){

            String recordId = returnData.getBody().getRecordId();

            Date finalStart = start;
            Date finalEnd = end;
            executor.submit(new Runnable() {
                @Override
                public void run() {
                    log.info("导出通话记录excel，线程开始啦,callRecordListReq[{}]",callRecordListReq);
                    batchExportService.generateExcelFile(finalStart, finalEnd,authLevel,String.valueOf(userId),orgCode,
                            callRecordListReq, isDesensitization, recordId);
                }
            });
            return  "已创建导出任务！";

        }
        return  "系统繁忙，请稍后重试！";
    }

    public static WritableSheet initSheet(WritableWorkbook wb, WritableCellFormat format) throws WriteException {

        WritableSheet sheet =  wb.createSheet("sheet1",0);
        sheet.setColumnView(0, 15);
        sheet.setColumnView(1, 15);
        sheet.setColumnView(2, 20);
        sheet.setColumnView(3, 20);
        sheet.setColumnView(4, 20);
        sheet.setColumnView(5, 20);
        sheet.setColumnView(6, 20);
        sheet.setColumnView(7, 10);
        sheet.setColumnView(8, 10);
        sheet.setColumnView(9, 10);
        sheet.setColumnView(10, 10);
        sheet.setColumnView(11, 100);
        sheet.setColumnView(12, 20);
        sheet.addCell(new Label(0, 0 , "被叫电话",format));
        sheet.addCell(new Label(1, 0 , "意向标签",format));
        sheet.addCell(new Label(2, 0 , "意向备注",format));
        sheet.addCell(new Label(3, 0 , "话术名称",format));
        sheet.addCell(new Label(4, 0 , "拨打时间",format));
        sheet.addCell(new Label(5, 0 , "接听时间",format));
        sheet.addCell(new Label(6, 0 , "挂断时间",format));
        sheet.addCell(new Label(7, 0 , "所属者",format));
        sheet.addCell(new Label(8, 0 , "拨打时长",format));
        sheet.addCell(new Label(9, 0 , "接听时长",format));
        sheet.addCell(new Label(10, 0 , "变量参数",format));
        sheet.addCell(new Label(11, 0 , "通话记录",format));
        sheet.addCell(new Label(12, 0 , "客户信息",format));
        sheet.addCell(new Label(13, 0 , "登记历史",format));

        return sheet;

    }


    public static WritableSheet insertExcelData(List<CallOutPlanRegistration> listPlan, Map<String, String> map,
                                                WritableSheet sheet, WritableCellFormat format) throws WriteException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for(int i=1;i<=listPlan.size();i++){
            CallOutPlanRegistration callPlan = listPlan.get(i-1);
            sheet.addCell(new Label(0, i , callPlan.getPhoneNum(),format));
            sheet.addCell(new Label(1, i , callPlan.getAccurateIntent(),format));
            sheet.addCell(new Label(2, i , callPlan.getReason(),format));
            sheet.addCell(new Label(3, i , callPlan.getTempId(),format));
            sheet.addCell(new Label(4, i , callPlan.getCallStartTime()!=null? sdf.format(callPlan.getCallStartTime()) : "",format));
            sheet.addCell(new Label(5, i , callPlan.getAnswerTime()!=null? sdf.format(callPlan.getAnswerTime()) : "",format));
            sheet.addCell(new Label(6, i , callPlan.getHangupTime()!=null? sdf.format(callPlan.getHangupTime()) : "",format));
            sheet.addCell(new Label(7, i , callPlan.getUserName(),format));
            sheet.addCell(new Label(8, i , callPlan.getDuration()!=null? DateUtils.secondToTime(callPlan.getDuration()): "",format));
            sheet.addCell(new Label(9, i , callPlan.getBillSec()!=null? DateUtils.secondToTime(callPlan.getBillSec()): "",format));
            sheet.addCell(new Label(10, i , callPlan.getParams()!=null? callPlan.getParams(): "",format));
            sheet.addCell(new Label(11, i , map.get(callPlan.getCallId().toString()),format));

            sheet.addCell(new Label(12, i , callPlan.getRemarks()!=null ? callPlan.getRemarks() : "暂无信息",format));

            String registration = "";
            if(callPlan.getCustomerName()!=null){
                registration += "客户姓名："+callPlan.getCustomerName() +"\r\n";
            }
            if(callPlan.getCustomerMobile()!=null){
                registration += "客户电话："+callPlan.getCustomerMobile()+"\r\n";
            }
            if(callPlan.getCustomerAddr()!=null){
                registration += "客户地址："+callPlan.getCustomerAddr()+"\r\n";
            }
            if(callPlan.getRemark()!=null){
                registration += "备注信息："+callPlan.getRemark();
            }
            if(registration.equals("")){
                registration = "暂无信息";
            }
            sheet.addCell(new Label(13, i , registration,format));
        }
        return sheet;
    }


    @ApiOperation(value = "批量导出通话语音文件，从第几条开始，到多少条")
    @PostMapping(value = "batchExportCallAudio")
    public String batchExportCallAudio(@RequestBody CallRecordListReq callRecordListReq,
                                        @RequestHeader Long userId, @RequestHeader Integer authLevel,
                                        @RequestHeader String orgCode, @RequestHeader Integer isDesensitization) {

        log.info("get request 批量导出音频文件 batchExportCallAudio，callRecordListReq[{}]",callRecordListReq);

        int exportCount = 0;
        Boolean checkAll = callRecordListReq.getCheckAll();

        Date end = null;
        Date start = null;
        if(StringUtils.isNotBlank(callRecordListReq.getStartDate())){
            start = DateUtil.stringToDate(callRecordListReq.getStartDate(),"yyyy-MM-dd HH:mm:ss");
        }
        if(StringUtils.isNotBlank(callRecordListReq.getEndDate())){
            end = DateUtil.stringToDate(callRecordListReq.getEndDate(),"yyyy-MM-dd HH:mm:ss");
        }

        int totalNum = 0;
        if (checkAll == null || !checkAll) {//不是全选
            List<String> callIds = callRecordListReq.getIncludeList();
            if (callIds!=null || callIds.size()>0) {
                totalNum = callIds.size();
                callRecordListReq.setStartCount("1");
                callRecordListReq.setEndCount(String.valueOf(totalNum));

            }else{
                return "缺少参数!";
            }

        }else{//全选
            totalNum = batchExportService.countTotalNum(start, end,authLevel,String.valueOf(userId),orgCode, callRecordListReq);
            callRecordListReq.setStartCount("1");
            if(totalNum>1000000){
                totalNum=1000000;
            }
            callRecordListReq.setEndCount(String.valueOf(totalNum));
        }

        ExportFileDto exportFileDto = new ExportFileDto();
        exportFileDto.setBusiId("batchExportCallRecord");
        exportFileDto.setBusiType("03");
        exportFileDto.setFileType(1);
        exportFileDto.setTotalNum(totalNum);
        exportFileDto.setUserId(String.valueOf(userId));
        exportFileDto.setOrgCode(orgCode);
        exportFileDto.setCreateName(authService.getUserName(userId));
        exportFileDto.setCreateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        Result.ReturnData<ExportFileRecordVo> returnData = iDispatchPlanOut.addExportFile(exportFileDto);
        log.info("请求iDispatchPlanOut.addExportFile返回returnData[{}]",returnData);
        if(returnData.success){

            String recordId = returnData.getBody().getRecordId();

            Date finalStart = start;
            Date finalEnd = end;
            executor.submit(new Runnable() {
                @Override
                public void run() {
                    log.info("导出录音文件，线程开始啦,callRecordListReq[{}]",callRecordListReq);
                    batchExportService.generateAudioFile(finalStart, finalEnd,authLevel,String.valueOf(userId),orgCode,
                            callRecordListReq, isDesensitization, recordId);
                }
            });
            return  "已创建导出任务！";

        }
        return  "系统繁忙，请稍后重试！";
    }


}