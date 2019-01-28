package com.guiji.ccmanager.controller;

import com.guiji.callcenter.dao.entity.CallOutPlan;
import com.guiji.callcenter.dao.entity.CallOutRecord;
import com.guiji.ccmanager.api.ICallPlanDetail;
import com.guiji.ccmanager.constant.Constant;
import com.guiji.ccmanager.service.AuthService;
import com.guiji.ccmanager.service.CallDetailService;
import com.guiji.ccmanager.utils.HttpDownload;
import com.guiji.ccmanager.utils.ZipUtil;
import com.guiji.ccmanager.vo.CallDetailUpdateReq;
import com.guiji.ccmanager.vo.CallOutPlan4ListSelect;
import com.guiji.ccmanager.vo.CallPlanDetailRecordVO;
import com.guiji.ccmanager.vo.CallRecordReq;
import com.guiji.common.model.Page;
import com.guiji.component.result.Result;
import com.guiji.utils.DateUtil;
import com.guiji.utils.IdGenUtil;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import jxl.Workbook;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
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
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Auther: 黎阳
 * @Date: 2018/10/29 0029 17:16
 * @Description:  通话记录列表，通话记录详情
 */
@RestController
public class CallDetailController implements ICallPlanDetail {

    private final Logger log = LoggerFactory.getLogger(CallManagerOutApiController.class);

    @Value("${download.path}")
    private String downloadPath;

    @Autowired
    private CallDetailService callDetailService;
    @Autowired
    AuthService authService;

    @ApiOperation(value = "通过电话号码，获取通话记录列表")
    @GetMapping(value = "getCallRecordListByPhone")
    public Result.ReturnData<List> getCallRecordListByPhone(@RequestParam(value="phone")  String phone){

        List<CallOutPlan> list = callDetailService.getCallRecordListByPhone(phone);
        List resultList = new ArrayList();
        if(list!=null && list.size()>0){

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            SimpleDateFormat sdf2 = new SimpleDateFormat("MM-dd HH:mm");

            for (CallOutPlan callOutPlan : list) {
                Map map = new HashMap();
                map.put("id", callOutPlan.getCallId().toString());
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


        if(!callRecordReq.getSuperAdmin()){//不是管理员
            if (authService.isAgentOrCompanyAdmin(Long.valueOf(callRecordReq.getUserId())) ) {//代理商 或者企业管理员
                callRecordReq.setUserId(null);
            } else {
                callRecordReq.setOrgCode(null);
            }
        }

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
    @ApiImplicitParams({
            @ApiImplicitParam(name = "startDate", value = "开始时间,yyyy-MM-dd HH:mm:ss格式", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "endDate", value = "结束时间,yyyy-MM-dd HH:mm:ss格式", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "customerId", value = "客户id", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "phoneNum", value = "电话号码", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "durationMin", value = "通话时长，最小值", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "durationMax", value = "通话时长，最大值", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "accurateIntent", value = "意向标签，以逗号分隔", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "freason", value = "直接传名称,以逗号分隔", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "callId", value = "通话ID", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "tempId", value = "话术模板id", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "isRead", value = "是否已读,0表示未读，1表示已读", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "每页数量", dataType = "String", paramType = "query", required = true),
            @ApiImplicitParam(name = "pageNo", value = "第几页，从1开始", dataType = "String", paramType = "query", required = true)
    })
    @GetMapping(value = "getCallRecord")
    public Result.ReturnData<Page<CallOutPlan4ListSelect>> getCallRecord(String startDate, String endDate, String pageSize, String pageNo, String phoneNum, String durationMin,
                                                                         String durationMax, String accurateIntent, String freason, String callId, String tempId, String isRead,
                                                                         @RequestHeader Long userId, @RequestHeader Boolean isSuperAdmin, @RequestHeader String orgCode) {

        log.info("get request getCallRecord，startDate[{}], endDate[{}],userId[{}],pageSize[{}],pageNo[{}], phoneNum[{}], durationMin[{}], durationMax[{}], " +
                        "accurateIntent[{}],  freason[{}], callId[{}],  tempId[{}], isRead[{}]",
                startDate, endDate, userId, pageSize, pageNo, phoneNum, durationMin, durationMax,  accurateIntent,  freason, callId,  tempId, isRead);

        if(StringUtils.isBlank(pageSize) || StringUtils.isBlank(pageNo)){
            return Result.error(Constant.ERROR_PARAM);
        }

        Date end = null;
        Date start = null;
        if(StringUtils.isNotBlank(startDate)){
            start = DateUtil.stringToDate(startDate,"yyyy-MM-dd HH:mm:ss");
        }
        if(StringUtils.isNotBlank(endDate)){
            end = DateUtil.stringToDate(endDate,"yyyy-MM-dd HH:mm:ss");
        }

        int pageSizeInt = Integer.parseInt(pageSize);
        int pageNoInt = Integer.parseInt(pageNo);

        List<CallOutPlan4ListSelect> list = callDetailService.callrecord(start,end,isSuperAdmin,String.valueOf(userId),orgCode, pageSizeInt,pageNoInt,
                phoneNum, durationMin, durationMax,  accurateIntent,  freason, callId,  tempId, isRead);
        int count = callDetailService.callrecordCount(start,end,isSuperAdmin,String.valueOf(userId),orgCode, phoneNum, durationMin, durationMax,
                accurateIntent,  freason, callId,  tempId, isRead);

        Page<CallOutPlan4ListSelect> page = new Page<CallOutPlan4ListSelect>();
        page.setPageNo(pageNoInt);
        page.setPageSize(pageSizeInt);
        page.setTotal(count);
        page.setRecords(list);

        log.info("response success getCallRecord，startDate[{}], endDate[{}],userId[{}],pageSize[{}],pageNo[{}]",
                startDate, endDate, userId, pageSize, pageNo);

        return Result.ok(page);
    }

    @ApiOperation(value = "查找f类说明列表")
    @GetMapping(value="getFtypes")
    public List<String> getFtypes(){
        return callDetailService.getFtypes();
    }

    @ApiOperation(value = "查看通话记录详情，前台页面使用,后台可使用")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "callId", value = "callId", dataType = "String", paramType = "query", required = true)
    })
    @GetMapping(value="getCallDetail")
    public Result.ReturnData<CallPlanDetailRecordVO> getCallDetail(@RequestParam(value="callId") String callId){

        log.info("get request getCallDetail，callId[{}]", callId);

        if(StringUtils.isBlank(callId)){
            return Result.error(Constant.ERROR_PARAM);
        }
        CallPlanDetailRecordVO callOutPlanVO = callDetailService.getCallDetail(new BigInteger(callId));
        //修改状态为已读
        if(callOutPlanVO.getIsread()!=null && callOutPlanVO.getIsread()==0){
            callDetailService.updateIsRead(callId);
        }

        log.info("reponse success getCallDetail，callId[{}]", callId);
        return Result.ok(callOutPlanVO);
    }

    @ApiOperation(value = "查看通话记录详情")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "callId", value = "callId", dataType = "String", paramType = "query", required = true)
    })
    @PostMapping(value="getCallPlanDetailRecord")
    public Result.ReturnData<List<CallPlanDetailRecordVO>> getCallPlanDetailRecord(@RequestBody List<String> callIds){

        log.info("get request getCallPlanDetailRecord，callIds[{}]", callIds);

        List<CallPlanDetailRecordVO> callPlanDetailRecordVO = callDetailService.getCallPlanDetailRecord(callIds);

        log.info("reponse success getCallPlanDetailRecord，callId[{}]", callIds);
        return Result.ok(callPlanDetailRecordVO);
    }

    @ApiOperation(value = "下载通话记录")
    @PostMapping(value="downloadDialogue")
    public void downloadDialogue(@RequestBody Map map,HttpServletResponse resp) throws UnsupportedEncodingException {
        String fileName = "通话记录.xls";
        HttpDownload.setHeader(resp, fileName);

        //生成文件
        String callId = (String) map.get("callId");
        String context = callDetailService.getDialogue(callId);

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
    @PostMapping(value="downloadDialogueZip")
    public String downloadDialogueZip(@RequestBody Map reqMap,HttpServletResponse resp) throws UnsupportedEncodingException {

        log.info("---------------start downloadDialogueZip----------");
        if(reqMap.get("callIds")==null){
            return "Missing Parameters";
        }
        String callIds = (String) reqMap.get("callIds");
        if(StringUtils.isBlank(callIds)){
            return "Missing Parameters";
        }

        //生成文件
        Map<String,String> map = callDetailService.getDialogues(callIds);

        if(map==null || map.size()==0){
            return "无通话记录";
        }

        String batchId = IdGenUtil.uuid();
        String savePath = downloadPath+File.separator+batchId;
        File parentFile = new File(savePath);
        if(!parentFile.exists()){
            parentFile.mkdirs();
        }

        FileOutputStream fout = null;
        try {
            for(Map.Entry<String,String> en : map.entrySet()){
                String callId = en.getKey();
                String context = en.getValue();

                File file = new File(savePath+File.separator+callId+".xls");
                if(!file.exists()){
                    file.createNewFile();
                }
                fout=new FileOutputStream(file,true);

                generateExcel(context, fout);
            }
        } catch (IOException e) {
            log.error("downloadDialogueZip IOException :"+e);
        }  catch (WriteException e) {
            log.error("downloadDialogueZip WriteException :"+e);
        }finally {
            if(fout!=null){
                try {
                    fout.close();
                } catch (IOException e) {
                    log.error("downloadDialogueZip fout.close error:"+e);
                }
            }
        }

        String fileName = "通话记录.zip";
        HttpDownload.setHeader(resp, fileName);

        OutputStream out=null;
        try {
            out=resp.getOutputStream();
            //压缩
            ZipUtil.zip(parentFile, out);
            FileUtils.deleteDirectory(parentFile);
        } catch (IOException e) {
            log.error("downloadDialogueZip IOException error: "+e);
        }
        log.info("---------------end downloadDialogueZip----------");
        return null;

    }


    @ApiOperation(value = "下载通话记录压缩包,callIds以逗号分隔")
    @PostMapping(value="downloadRecordZip")
    public String downloadRecordZip(@RequestBody Map reqMap, HttpServletResponse resp) throws UnsupportedEncodingException {
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

    @ApiOperation(value = "删除通话记录,callIds以逗号分隔")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "callIds", value = "callIds", dataType = "String", paramType = "query", required = true)
    })
    @GetMapping(value="delRecord")
    public Result.ReturnData<Boolean> delRecord(@RequestParam String callIds){
        if(StringUtils.isBlank(callIds)){
            return Result.error(Constant.ERROR_PARAM);
        }
        callDetailService.delRecord(callIds);
        return Result.ok(true);
    }


    @ApiOperation(value = "修改通话记录详情")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "callDetailId", value = "callDetailId", dataType = "String", paramType = "query", required = true),
            @ApiImplicitParam(name = "customerSayText", value = "customerSayText", dataType = "String", paramType = "query", required = true)
    })
    @PostMapping(value="updateCallDetailCustomerSayText")
    public Result.ReturnData<List<CallPlanDetailRecordVO>> updateCallDetailCustomerSayText(@RequestBody @Validated CallDetailUpdateReq callDetailUpdateReq,
                                                                                           @RequestHeader Long userId){

        callDetailService.updateCallDetailCustomerSayText(callDetailUpdateReq,userId);
        return Result.ok();
    }

}
