package com.guiji.ccmanager.controller;

import com.guiji.callcenter.dao.entityext.CallOutPlanRegistration;
import com.guiji.ccmanager.service.BatchExportService;
import com.guiji.ccmanager.service.CallDetailService;
import com.guiji.ccmanager.utils.HttpDownload;
import com.guiji.ccmanager.vo.CallRecordListReq;
import com.guiji.utils.DateUtil;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import java.util.Map;


@RestController
public class BatchExportController {

    private final Logger log = LoggerFactory.getLogger(BatchExportController.class);

    @Autowired
    BatchExportService batchExportService;
    @Autowired
    CallDetailService callDetailService;


    @ApiOperation(value = "批量导出通话记录，从第几条开始，到多少条")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "startDate", value = "开始时间,yyyy-MM-dd HH:mm:ss格式", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "endDate", value = "结束时间,yyyy-MM-dd HH:mm:ss格式", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "customerId", value = "客户id", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "phoneNum", value = "电话号码", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "durationMin", value = "拨打时长，最小值", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "durationMax", value = "拨打时长，最大值", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "billSecMin", value = "接听时长，最小值", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "billSecMax", value = "接听时长，最大值", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "accurateIntent", value = "意向标签，以逗号分隔", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "freason", value = "直接传名称,以逗号分隔", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "callId", value = "通话ID", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "tempId", value = "话术模板id", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "customerId", value = "用户id", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "isRead", value = "是否已读,0表示未读，1表示已读", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "startCount", value = "从第几条记录开始，默认从第一条开始", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "endCount", value = "到多少条", dataType = "String", paramType = "query", required = true),
            @ApiImplicitParam(name = "intervened", value = "是否已介入，0:未介入,1:已介入,不传或其他值则是全部", dataType = "String", paramType = "query")
    })
    @GetMapping(value = "batchExportCallRecord")
    public String batchExportCallRecord(CallRecordListReq callRecordListReq, HttpServletResponse resp,
                                        @RequestHeader Long userId, @RequestHeader Boolean isSuperAdmin,
                                        @RequestHeader String orgCode, @RequestHeader Integer isDesensitization) throws UnsupportedEncodingException {

        log.info("get request 批量导出 getCallRecord，callRecordListReq[{}]",callRecordListReq);

        if(StringUtils.isBlank(callRecordListReq.getEndCount()) || StringUtils.isBlank(callRecordListReq.getStartCount()) ){
            return "缺少参数!";
        }else if(Integer.valueOf(callRecordListReq.getEndCount()).intValue()-Integer.valueOf(callRecordListReq.getStartCount()).intValue()>30000){
            return "不能超过30000条!";
        }else if(Integer.valueOf(callRecordListReq.getEndCount()).intValue()-Integer.valueOf(callRecordListReq.getStartCount()).intValue()<=0){
            return "起始值必须小于结束值!";
        }

        Date end = null;
        Date start = null;
        if(StringUtils.isNotBlank(callRecordListReq.getStartDate())){
            start = DateUtil.stringToDate(callRecordListReq.getStartDate(),"yyyy-MM-dd HH:mm:ss");
        }
        if(StringUtils.isNotBlank(callRecordListReq.getEndDate())){
            end = DateUtil.stringToDate(callRecordListReq.getEndDate(),"yyyy-MM-dd HH:mm:ss");
        }

        List<BigInteger> idList = batchExportService.callrecord(start,end,isSuperAdmin,String.valueOf(userId),orgCode,
                callRecordListReq, isDesensitization);

        //生成文件
        Map<String,String> map = callDetailService.getDialogues(idList);
        List<CallOutPlanRegistration> listPlan = callDetailService.getCallPlanList(idList,userId,isSuperAdmin,isDesensitization);

        if(listPlan==null || listPlan.size()==0){
            return "无通话记录";
        }

        String fileName = "通话记录.xls";
        HttpDownload.setHeader(resp, fileName);

        OutputStream out=null;
        try {
            out=resp.getOutputStream();
            CallDetailController.generateExcelList(out, listPlan, map);

        } catch (IOException e) {
            log.error("downloadDialogue IOException :"+e);
        } catch (RowsExceededException e) {
            log.error("downloadDialogue RowsExceededException :"+e);
        } catch (WriteException e) {
            log.error("downloadDialogue WriteException :"+e);
        } finally{
            if(out!=null){
                try {
                    out.close();
                } catch (IOException e) {
                    log.error("out.close error:"+e);
                }
            }
        }
        return  null;

    }

}
