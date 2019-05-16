package com.guiji.ccmanager.controller;

import com.guiji.callcenter.dao.entity.CallOutPlan;
import com.guiji.callcenter.dao.entity.CallOutRecord;
import com.guiji.callcenter.dao.entityext.CallOutPlanRegistration;
import com.guiji.callcenter.dao.entityext.MyCallOutPlanQueryEntity;
import com.guiji.ccmanager.api.ICallPlanDetail;
import com.guiji.ccmanager.constant.Constant;
import com.guiji.ccmanager.entity.CallPlanUuidQuery;
import com.guiji.ccmanager.service.AuthService;
import com.guiji.ccmanager.service.CallDetailService;
import com.guiji.ccmanager.utils.DateUtils;
import com.guiji.ccmanager.utils.HttpDownload;
import com.guiji.ccmanager.utils.ZipUtil;
import com.guiji.ccmanager.vo.*;
import com.guiji.common.model.Page;
import com.guiji.component.jurisdiction.Jurisdiction;
import com.guiji.component.result.Result;
import com.guiji.dispatch.api.IDispatchPlanOut;
import com.guiji.utils.DateUtil;
import com.guiji.utils.IdGenUtil;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import jxl.Workbook;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.VerticalAlignment;
import jxl.write.*;
import jxl.write.biff.RowsExceededException;
import org.apache.commons.lang.StringUtils;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.lang.Boolean;
import java.math.BigInteger;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Auther: 黎阳
 * @Date: 2018/10/29 0029 17:16
 * @Description:  通话记录列表，通话记录详情
 */
@RestController
public class CallDetailController implements ICallPlanDetail {

    private final Logger log = LoggerFactory.getLogger(CallDetailController.class);

    @Value("${download.path}")
    private String downloadPath;

    @Autowired
    IDispatchPlanOut dispatchPlanOut;

    @Autowired
    private CallDetailService callDetailService;
    @Autowired
    AuthService authService;

    @ApiOperation(value = "通过电话号码，获取通话记录列表")
    @GetMapping(value = "getCallRecordListByPhone")
    public Result.ReturnData<List> getCallRecordListByPhone(@RequestParam(value="phone")  String phone,
                                                            @RequestParam(value="authLevel")  Integer authLevel,
                                                            @RequestParam(value="orgId")  Integer orgId){

        List<Integer> orgIdList = authService.getOrgIdsByAuthlevel(authLevel,orgId);

        List<CallOutPlan> list = callDetailService.getCallRecordListByPhone(phone, orgIdList);
        List resultList = new ArrayList();
        if(list!=null && list.size()>0){

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            SimpleDateFormat sdf2 = new SimpleDateFormat("MM-dd HH:mm");

            for (CallOutPlan callOutPlan : list) {
                Map map = new HashMap();
                map.put("id", callOutPlan.getCallId().toString());
                map.put("orgId", callOutPlan.getOrgId());
                map.put("phone", callOutPlan.getPhoneNum());
                map.put("label", callOutPlan.getAccurateIntent() != null ? callOutPlan.getAccurateIntent() : "");

                Date callStartTime = callOutPlan.getCreateTime();
                map.put("starttime", sdf.format(callStartTime));
                map.put("show_time", sdf2.format(callStartTime));

                resultList.add(map);
            }
        }
        return Result.ok(resultList);
    }


    @PostMapping(value = "getCallRecordList")
    public Result.ReturnData<Map> getCallRecordList(@RequestBody CallRecordReq callRecordReq){

        Map numMap = new HashMap();

        List<Map> list = callDetailService.getCallRecordList(callRecordReq);
        int label = callDetailService.countCallRecordList(callRecordReq);
        numMap.put(callRecordReq.getAccurateIntent(),label);

        callRecordReq.setAccurateIntent(null);
        int num = callDetailService.countCallRecordList(callRecordReq);

        numMap.put("num",num);

        Map resultMap = new HashMap();
        resultMap.put("data",list);
        resultMap.put("num",numMap);

        return Result.ok(resultMap);
    }

    @ApiOperation(value = "获取客户指定时间内的通话记录列表")
    @Jurisdiction("callCenter_callHistory_defquery")
    @PostMapping(value = "getCallRecord")
    public Result.ReturnData<Page<CallOutPlan4ListSelect>> getCallRecord(@RequestBody CallRecordListReq callRecordListReq, @RequestHeader Long userId,
                                                                         @RequestHeader String orgCode,@RequestHeader Integer orgId,
                                                                         @RequestHeader Integer isDesensitization,
                                                                         @RequestHeader Integer authLevel) {

        log.info("get request getCallRecord，callRecordListReq[{}]",callRecordListReq);

        if(StringUtils.isBlank(callRecordListReq.getPageSize()) || StringUtils.isBlank(callRecordListReq.getPageNo())){
            return Result.error(Constant.ERROR_PARAM);
        }

        Date end = null;
        Date start = null;
        if(StringUtils.isNotBlank(callRecordListReq.getStartDate())){
            start = DateUtil.stringToDate(callRecordListReq.getStartDate(),"yyyy-MM-dd HH:mm:ss");
        }
        if(StringUtils.isNotBlank(callRecordListReq.getEndDate())){
            end = DateUtil.stringToDate(callRecordListReq.getEndDate(),"yyyy-MM-dd HH:mm:ss");
        }

        int pageSizeInt = Integer.parseInt(callRecordListReq.getPageSize());
        int pageNoInt = Integer.parseInt(callRecordListReq.getPageNo());


        List<CallOutPlan4ListSelect> list = callDetailService.callrecord(start,end,authLevel,String.valueOf(userId),orgCode, pageSizeInt,pageNoInt,
                callRecordListReq, isDesensitization, orgId);
        int count = callDetailService.callrecordCount(start,end,authLevel,String.valueOf(userId),orgCode, callRecordListReq, orgId);

        Page<CallOutPlan4ListSelect> page = new Page<CallOutPlan4ListSelect>();
        page.setPageNo(pageNoInt);
        page.setPageSize(pageSizeInt);
        page.setTotal(count);
        page.setRecords(list);

        log.info("response success getCallRecord，callRecordListReq[{}]",callRecordListReq);

        return Result.ok(page);
    }

    @ApiOperation(value = "查找f类说明列表")
    @GetMapping(value="getFtypes")
    public List<String> getFtypes(){
        return callDetailService.getFtypes();
    }

    @ApiOperation(value = "查看通话记录详情，前台页面使用")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "callId", value = "callId", dataType = "String", paramType = "query", required = true)
    })
    @Jurisdiction("callCenter_callHistory_detail")
    @GetMapping(value="getCallDetail")
    public Result.ReturnData<CallPlanDetailRecordVO> getCallDetail(@RequestParam(value="callId") String callId,@RequestParam(value="orgId") Integer orgId,
                                                                   @RequestHeader Integer isDesensitization,
                                                                   @RequestHeader Integer authLevel,
                                                                   @RequestHeader Boolean isSuperAdmin){

        log.info("get request getCallDetail，callId[{}],orgId[{}]", callId, orgId);

        if(StringUtils.isBlank(callId) || null ==orgId){
            return Result.error(Constant.ERROR_PARAM);
        }

        CallPlanDetailRecordVO callOutPlanVO = callDetailService.getCallDetail(new BigInteger(callId), orgId);
        //修改状态为已读
        if(!isSuperAdmin){
            if(callOutPlanVO.getIsread()!=null && callOutPlanVO.getIsread()==0){
                callDetailService.updateIsRead(callId,orgId);
            }
        }
        if(isDesensitization==0){ // 需要脱敏显示
            String phone = callOutPlanVO.getPhoneNum();
            callOutPlanVO.setPhoneNum(phone.substring(0, 3)+"****"+phone.substring(7));
        }

        //请求调度中心，获取attach字段
        /*try{
            String planUuid = callOutPlanVO.getPlanUuid();
            Result.ReturnData<String> result = dispatchPlanOut.queryPlanRemarkById(planUuid);
            if(result.success){
                callOutPlanVO.setAttach(result.getBody());
            }
        } catch (Exception e){
            log.error("queryPlanRemarkById请求调度中心异常，callId[{}]", callId);
        }*/
        callOutPlanVO.setAttach(callOutPlanVO.getRemarks());

        log.info("reponse success getCallDetail，callId[{}]", callId);
        return Result.ok(callOutPlanVO);
    }

    @Override
    public Result.ReturnData<CallPlanDetailRecordVO> getCallDetailApi(@RequestParam(value="callId") String callId,@RequestParam(value="orgId") Integer orgId) {
        log.info("get request getCallDetailApi，callId[{}]", callId);

        if(StringUtils.isBlank(callId) || orgId == null){
            return Result.error(Constant.ERROR_PARAM);
        }
        CallPlanDetailRecordVO callOutPlanVO = callDetailService.getCallDetail(new BigInteger(callId), orgId);
        //修改状态为已读
        if(callOutPlanVO.getIsread()!=null && callOutPlanVO.getIsread()==0){
            callDetailService.updateIsRead(callId, orgId);
        }
        //获取attach字段
        callOutPlanVO.setAttach(callOutPlanVO.getRemarks());

        log.info("reponse success getCallDetailApi，callId[{}]", callId);
        return Result.ok(callOutPlanVO);
    }

    @ApiOperation(value = "查看通话记录详情")
    @PostMapping(value="getCallPlanDetailRecord")
    public Result.ReturnData<List<CallPlanDetailRecordVO>> getCallPlanDetailRecord(@RequestBody CallPlanUuidQuery callPlanUuidQuery){

        log.info("get request getCallPlanDetailRecord，callPlanUuidQuery[{}]", callPlanUuidQuery);

        List<CallPlanDetailRecordVO> callPlanDetailRecordVO = callDetailService.getCallPlanDetailRecord(callPlanUuidQuery);

        return Result.ok(callPlanDetailRecordVO);
    }

    @ApiOperation(value = "下载通话记录")
    @PostMapping(value="downloadDialogue")
    public void downloadDialogue(@RequestBody Map map,HttpServletResponse resp,
                                 @RequestHeader Integer authLevel, @RequestHeader Integer orgId) throws UnsupportedEncodingException {
        String fileName = "通话记录.xls";
        HttpDownload.setHeader(resp, fileName);

        //生成文件
        String callId = (String) map.get("callId");
        List<Integer> orgIdList = authService.getOrgIdsByAuthlevel(authLevel,orgId);
        String context = callDetailService.getDialogue(callId, orgIdList);

        OutputStream out=null;
        try {
            out=resp.getOutputStream();
            generateExcel(context, out);

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
    }

    public void generateExcel(String context, OutputStream out) throws IOException, WriteException {
        WritableWorkbook wb = Workbook.createWorkbook(out);

        WritableSheet sheet =  wb.createSheet("sheet1",0);
        WritableCellFormat format = new WritableCellFormat();
        format.setBorder(Border.ALL,BorderLineStyle.THIN);
        format.setWrap(true);

        Label label = new Label(0, 0 , "通话记录",format);
        sheet.addCell(label);
        sheet.setColumnView(0, 100);
        sheet.addCell(new Label(0,1, context,format));

        wb.write();
        wb.close();
    }



    @ApiOperation(value = "下载通话记录Excel压缩包,callIds以逗号分隔")
    @Jurisdiction("callCenter_callHistory_exportOne")
    @PostMapping(value="downloadDialogueZip")
    public String downloadDialogueZip(@RequestBody Map reqMap,HttpServletResponse resp,@RequestHeader Long userId,
                                      @RequestHeader Integer authLevel, @RequestHeader Integer orgId, @RequestHeader Integer isDesensitization) throws UnsupportedEncodingException {

        log.info("---------------start downloadDialogueZip----------");
        if(reqMap.get("callIds")==null){
            return "Missing Parameters";
        }
        String callIds = (String) reqMap.get("callIds");
        if(StringUtils.isBlank(callIds)){
            return "Missing Parameters";
        }

        String[] callidArr = callIds.split(",");

        List<BigInteger> idList = new ArrayList();
        for(String callId: callidArr){
            idList.add(new BigInteger(callId));
        }
        //生成文件
        List<Integer> orgIdList = authService.getOrgIdsByAuthlevel(authLevel,orgId);
        MyCallOutPlanQueryEntity myCallOutPlanQueryEntity = new MyCallOutPlanQueryEntity();
        myCallOutPlanQueryEntity.setOrgIdList(orgIdList);
        Map<String,String> map = callDetailService.getDialogues(idList, myCallOutPlanQueryEntity);
        List<CallOutPlanRegistration> listPlan = callDetailService.getCallPlanList(idList,isDesensitization, myCallOutPlanQueryEntity);

        if(listPlan==null || listPlan.size()==0){
            return "无通话记录";
        }

        String fileName = "通话记录.xls";
        HttpDownload.setHeader(resp, fileName);

        OutputStream out=null;
        try {
            out=resp.getOutputStream();
            generateExcelList(out, listPlan, map);

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

    public static void generateExcelList(OutputStream out, List<CallOutPlanRegistration> listPlan ,Map<String,String> map) throws IOException, WriteException {
        WritableWorkbook wb = Workbook.createWorkbook(out);

        WritableSheet sheet =  wb.createSheet("sheet1",0);
        WritableCellFormat format = new WritableCellFormat();
        format.setBorder(Border.ALL,BorderLineStyle.THIN);
        format.setWrap(true);
        format.setVerticalAlignment(VerticalAlignment.CENTRE);

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
        sheet.setColumnView(10, 100);
        sheet.setColumnView(11, 20);
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

        wb.write();
        wb.close();
    }


    @ApiOperation(value = "下载通话记录压缩包,callIds以逗号分隔")
    @Jurisdiction("callCenter_callHistory_exportVideos")
    @PostMapping(value="downloadRecordZip")
    public String downloadRecordZip(@RequestBody Map reqMap,HttpServletResponse resp) throws UnsupportedEncodingException {
        log.info("---------------start downloadRecordZip----------");
        if(reqMap.get("callIds")==null){
            return "Missing Parameters";
        }
        String callIds = (String) reqMap.get("callIds");
        if(StringUtils.isBlank(callIds)){
            return "Missing Parameters";
        }
        boolean hasRecordUrl = false;
        String batchId = IdGenUtil.uuid();
        String savePath = downloadPath+File.separator+batchId;
        //生成文件
        List<CallOutRecord> records = callDetailService.getRecords(callIds);
        for(CallOutRecord callOutRecord:records){
            String recordUrl = callOutRecord.getRecordUrl();
            if(recordUrl!=null){
                hasRecordUrl =true;
                HttpDownload.downLoadFromUrl(recordUrl,callOutRecord.getCallId()+".wav",savePath);
            }
        }
        if(!hasRecordUrl){
            return "无录音文件";
        }

        String fileName = "record.zip";
        HttpDownload.setHeader(resp, fileName);

        OutputStream out=null;
        try {
            out=resp.getOutputStream();
            //压缩
            ZipUtil.zip(new File(savePath),out);
            FileUtils.deleteDirectory(new File(savePath));
        } catch (IOException e) {
            log.error("downloadRecordZip IOException error: "+e);
        }
        log.info("---------------end downloadRecordZip----------");
        return null;
    }

    @ApiOperation(value = "获取整段通话录音url")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "callId", value = "callId", dataType = "String", paramType = "query", required = true)
    })
    @GetMapping(value="getRecordFileUrl")
    public Result.ReturnData<String> getRecordFileUrl(String callId){
        if(StringUtils.isBlank(callId)){
            return Result.error(Constant.ERROR_PARAM);
        }
        String url = callDetailService.getRecordFileUrl(callId);
        return Result.ok(url);
    }

    @ApiOperation(value = "下载全局录音")
    @PostMapping(value = "downloadRecordWavFile")
    public String downloadRecordWavFile(@RequestBody Map map, HttpServletResponse resp) {

        String callId = String.valueOf(map.get("callId"));

        if (StringUtils.isBlank(callId)) {
            return "callId不能为空";
        }
        String sourceUrl = callDetailService.getRecordFileUrl(callId);

        OutputStream out = null;
        InputStream inputStream = null;
        try {
            if (StringUtils.isNotEmpty(sourceUrl)) {
                out = resp.getOutputStream();
                inputStream = new URL(sourceUrl).openStream();
                // 将输入流is写入文件输出流fos中
                int ch = 0;
                while ((ch = inputStream.read()) != -1) {
                    out.write(ch);
                }
            }else{
                return "无录音文件";
            }
            out.flush();
        } catch (IOException e) {
            log.error("downloadRecordWavFile IOException :" + e);
        } finally {
            if (out != null) {
                try {
                    out.close();
                    inputStream.close();
                } catch (IOException e) {
                    log.error("downloadRecordWavFile.close error:" + e);
                }
            }
        }
        return null;
    }

    @ApiOperation(value = "删除通话记录,callIds以逗号分隔")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "callIds", value = "callIds", dataType = "String", paramType = "query", required = true)
    })
    @Jurisdiction("callCenter_callHistory_delete,callCenter_callHistory_batchDeletes")
    @GetMapping(value="delRecord")
    public Result.ReturnData<Boolean> delRecord(@RequestParam String callIds,@RequestHeader Integer authLevel,
                                                @RequestHeader Integer orgId){
        if(StringUtils.isBlank(callIds)){
            return Result.error(Constant.ERROR_PARAM);
        }
        List<Integer> orgIdList = authService.getOrgIdsByAuthlevel(authLevel,orgId);
        callDetailService.delRecord(callIds, orgIdList);
        return Result.ok(true);
    }


    @ApiOperation(value = "修改通话记录详情")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "callDetailId", value = "callDetailId", dataType = "String", paramType = "query", required = true),
            @ApiImplicitParam(name = "customerSayText", value = "customerSayText", dataType = "String", paramType = "query", required = true)
    })
    @PostMapping(value="updateCallDetailCustomerSayText")
    public Result.ReturnData<List<CallPlanDetailRecordVO>> updateCallDetailCustomerSayText(@RequestBody @Validated CallDetailUpdateReq callDetailUpdateReq,
                                                                                           @RequestHeader Long userId,@RequestHeader Integer authLevel,
                                                                                           @RequestHeader Integer orgId){
        List<Integer> orgIdList = authService.getOrgIdsByAuthlevel(authLevel,orgId);
        callDetailService.updateCallDetailCustomerSayText(callDetailUpdateReq,userId,orgIdList);
        return Result.ok();
    }

}
