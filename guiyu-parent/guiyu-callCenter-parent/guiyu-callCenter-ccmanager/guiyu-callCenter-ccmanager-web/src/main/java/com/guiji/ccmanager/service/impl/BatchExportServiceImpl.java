package com.guiji.ccmanager.service.impl;

import com.guiji.auth.api.IAuth;
import com.guiji.callcenter.dao.AgentMapper;
import com.guiji.callcenter.dao.CallOutPlanMapper;
import com.guiji.callcenter.dao.CallOutRecordMapper;
import com.guiji.callcenter.dao.MyCallOutPlanMapper;
import com.guiji.callcenter.dao.entity.*;
import com.guiji.callcenter.dao.entityext.CallOutPlanRegistration;
import com.guiji.callcenter.dao.entityext.MyCallOutPlanQueryEntity;
import com.guiji.ccmanager.constant.Constant;
import com.guiji.ccmanager.controller.BatchExportController;
import com.guiji.ccmanager.entity.CallOutPlanQueryEntity;
import com.guiji.ccmanager.service.AuthService;
import com.guiji.ccmanager.service.BatchExportService;
import com.guiji.ccmanager.service.CallDetailService;
import com.guiji.ccmanager.utils.FileUtil;
import com.guiji.ccmanager.utils.HttpDownload;
import com.guiji.ccmanager.utils.ZipUtil;
import com.guiji.ccmanager.vo.CallRecordListReq;
import com.guiji.common.model.SysFileReqVO;
import com.guiji.common.model.SysFileRspVO;
import com.guiji.component.result.Result;
import com.guiji.dispatch.api.IDispatchPlanOut;
import com.guiji.user.dao.entity.SysUser;
import com.guiji.utils.BeanUtil;
import com.guiji.utils.IdGenUtil;
import com.guiji.utils.NasUtil;
import jxl.Workbook;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.VerticalAlignment;
import jxl.write.WritableCellFormat;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.math.BigInteger;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * author:liyang
 * Date:2019/3/15 10:49
 * Description:
 */
@Service
@Slf4j
public class BatchExportServiceImpl implements BatchExportService {

    @Autowired
    CallOutPlanMapper callOutPlanMapper;
    @Autowired
    AuthService authService;
    @Autowired
    AgentMapper agentMapper;
    @Autowired
    CallDetailService callDetailService;
    @Value("${download.path}")
    String downloadPath;
    @Autowired
    IAuth auth;
    @Autowired
    CallOutRecordMapper callOutRecordMapper;
    @Autowired
    IDispatchPlanOut iDispatchPlanOut;
    @Autowired
    MyCallOutPlanMapper myCallOutPlanMapper;


    private static final int batchCount = 30000;
    private static final DateFormat df = new SimpleDateFormat("yyyyMMdd");

    @Override
    public void batchDeleteCallRecord(Date startDate, Date endDate, Integer authLevel, String customerId,
                                      CallRecordListReq callRecordListReq, Integer orgId) {

        MyCallOutPlanQueryEntity myCallOutPlanQueryEntity = callDetailService.prepareQuery(startDate, endDate, authLevel, customerId, callRecordListReq, orgId);

        myCallOutPlanMapper.batchDeleteCallRecord(myCallOutPlanQueryEntity);
    }

    @Override
    public int countTotalNum(Date startDate, Date endDate, Integer authLevel, String customerId, String orgCode,
                             CallRecordListReq callRecordListReq, Integer orgId) {

        MyCallOutPlanQueryEntity myCallOutPlanQueryEntity = callDetailService.prepareQuery(startDate, endDate,authLevel,customerId,callRecordListReq,orgId);

        int result = myCallOutPlanMapper.countCallOutPlan(myCallOutPlanQueryEntity);
        return result;
    }

    @Override
    public void generateExcelFile(Date finalStart, Date finalEnd, Integer authLevel, String userId, Integer orgId,
                                  CallRecordListReq callRecordListReq, Integer isDesensitization, String recordId) {

        String userName = userId;
        try {
            Result.ReturnData<SysUser> returnData = auth.getUserById(Long.valueOf(userId));
            userName = returnData.getBody().getUsername();
        } catch (Exception e) {
            log.error("获取用户名失败！");
        }

        int startCount = Integer.valueOf(callRecordListReq.getStartCount());
        int endCount = Integer.valueOf(callRecordListReq.getEndCount());
        int allCount = endCount - startCount + 1;

        int excelCount = allCount / batchCount + 1;
        int remainder = allCount % batchCount;
        log.info("一共[{}]个excel，余数[{}]", excelCount, remainder);
        BigInteger minId = null;

        String uuidDir = IdGenUtil.uuid();
        File parentDir = new File(downloadPath + File.separator + uuidDir);
        if (!parentDir.exists()) {
            parentDir.mkdirs();
        }

        for (int i = 1; i <= excelCount; i++) {
            log.info("第[{}]个excel开始生产",i);
            List<BigInteger> idList;
            MyCallOutPlanQueryEntity myCallOutPlanQueryEntity = callDetailService.prepareQuery(finalStart, finalEnd,authLevel,userId,callRecordListReq,orgId);
            if (i == excelCount) {
                idList = callrecord(myCallOutPlanQueryEntity, callRecordListReq, minId, remainder);
            } else {
                idList = callrecord(myCallOutPlanQueryEntity, callRecordListReq, minId, batchCount);
            }
            if (idList != null && idList.size() > 0) {
                int listSize = idList.size();
                minId = idList.get(listSize - 1);

                try {
                    writeExcel(myCallOutPlanQueryEntity, idList, isDesensitization, userName, uuidDir);
                } catch (Exception e) {
                    log.error("生成excel文件出现异常", e);
                }
            }else{
                log.info("没有数据了，跳出循环");
                break;
            }
        }

        try {
            log.info("开始压缩文件");
            String zipFilePath = downloadPath + File.separator + uuidDir + ".zip";
            OutputStream zipOut = new FileOutputStream(new File(zipFilePath));
            ZipUtil.zip(parentDir, zipOut);

            String nasUrl = uploadNas(zipFilePath, Long.valueOf(userId), "callRecordZip");
            log.info("文件上传nas后，返回的url[{}],userId[{}]", nasUrl, userId);

            if (StringUtils.isNotEmpty(nasUrl)) {
                //上传成功
                Result.ReturnData<Boolean> result = iDispatchPlanOut.updExportFile(recordId,1,nasUrl);
                log.info("文件上传成功回调结果是 result[{}]",result);

                FileUtil.deleteFile(parentDir);
                FileUtil.delete(zipFilePath);
                log.info("删除文件成功,parentDir[{}]", parentDir);
            } else {
                //上传失败
                log.error("上传文件失败");
                Result.ReturnData<Boolean> result = iDispatchPlanOut.updExportFile(recordId,4,null);
                log.info("失败回调结果是 result[{}]",result);
            }


        } catch (Exception e) {
            log.error("压缩文件，上传文件，出现异常", e);
            //失败
            Result.ReturnData<Boolean> result = iDispatchPlanOut.updExportFile(recordId,4,null);
            log.info("失败回调结果是 result[{}]",result);
        }

    }

    public String uploadNas(String filePath, Long userId, String busiType) {

        SysFileReqVO sysFileReqVO = new SysFileReqVO();
        sysFileReqVO.setBusiId("ccmanger");
        sysFileReqVO.setBusiType(busiType);
        sysFileReqVO.setSysCode("guiyu-ccmanager-web");
        sysFileReqVO.setUserId(userId);
        sysFileReqVO.setThumbImageFlag("0");
        SysFileRspVO sysFileRspVO = new NasUtil().uploadNas(sysFileReqVO, new File(filePath));
        return sysFileRspVO.getSkUrl();
    }


    public void writeExcel(MyCallOutPlanQueryEntity myCallOutPlanQueryEntity, List<BigInteger> idList,
                           Integer isDesensitization, String userName, String uuidDir) throws Exception {


        log.info("idList准备完毕，准备获取对话详情和通话记录,size[{}]", idList.size());
        //对话详情
        Map<String, String> map = callDetailService.getDialogues(idList, myCallOutPlanQueryEntity);
        //通话记录
        List<CallOutPlanRegistration> listPlan = callDetailService.getCallPlanList(idList, isDesensitization, myCallOutPlanQueryEntity);
        log.info("已获取对话详情和通话记录信息");

        int listSize = listPlan.size();
        CallOutPlanRegistration planStart = listPlan.get(listSize - 1);
        CallOutPlanRegistration planEnd = listPlan.get(0);
        String startDate = df.format(planStart.getCreateTime());
        String endDate = df.format(planEnd.getCreateTime());


        String fileName = "data_" + startDate + "_" + endDate + "_" + userName + "_" + System.currentTimeMillis() + ".xls";
        File file = new File(downloadPath + File.separator + uuidDir + File.separator + fileName);

        OutputStream out = new FileOutputStream(file);
        WritableWorkbook wb = Workbook.createWorkbook(out);


        WritableCellFormat format = new WritableCellFormat();
        format.setBorder(Border.ALL, BorderLineStyle.THIN);
        format.setWrap(true);
        format.setVerticalAlignment(VerticalAlignment.CENTRE);

        log.info("数据准备完毕，准备生成excel");

        WritableSheet initSheet = BatchExportController.initSheet(wb, format);

        BatchExportController.insertExcelData(listPlan, map, initSheet, format);

        wb.write();
        wb.close();
        out.flush();
        out.close();

    }

    //    @Override
    public List<BigInteger> callrecord(MyCallOutPlanQueryEntity myCallOutPlanQueryEntity, CallRecordListReq callRecordListReq, BigInteger minId, int pageSize) {

//        MyCallOutPlanQueryEntity myCallOutPlanQueryEntity = callDetailService.prepareQuery(startDate, endDate,authLevel,customerId,callRecordListReq,orgId);

        int limitStart = 0;
        if (minId == null) {
            String startCount = callRecordListReq.getStartCount();
            if (StringUtils.isNotBlank(startCount)) {
                int startInt = Integer.valueOf(startCount);
                limitStart = startInt > 0 ? startInt - 1 : 0;
            }
        }

        myCallOutPlanQueryEntity.setLimitStart(limitStart);
        myCallOutPlanQueryEntity.setLimitEnd(pageSize);

        List<BigInteger> listIds = myCallOutPlanMapper.selectCallIdList(myCallOutPlanQueryEntity);

        return listIds;
    }

    public CallOutPlanExample getExample(CallOutPlanQueryEntity callOutPlanQueryEntity,String queryUser, BigInteger minId) {
        CallOutPlanExample example = new CallOutPlanExample();
        CallOutPlanExample.Criteria criteria = example.createCriteria();
        getCriteria(criteria, callOutPlanQueryEntity, queryUser,minId, false);

        long userId = Long.valueOf(callOutPlanQueryEntity.getCustomerId());
        if(authService.isSeat(userId)){//具有人工坐席权限
            try{
                String userName = authService.getUserName(userId);
                AgentExample agentExample = new AgentExample();
                agentExample.createCriteria().andCrmLoginIdEqualTo(userName);
                List<Agent> listAgent = agentMapper.selectByExample(agentExample);
                Long agentId = listAgent.get(0).getUserId();
                CallOutPlanExample.Criteria criteria2 = example.or();
                criteria2.andAgentIdEqualTo(String.valueOf(agentId));
                getCriteria(criteria2, callOutPlanQueryEntity, queryUser, minId,true);
            }catch (Exception e){
                log.error("人工坐席权限查询出现异常",e);
            }

        }

        return example;
    }

    public CallOutPlanExample.Criteria getCriteria(CallOutPlanExample.Criteria criteria , CallOutPlanQueryEntity callOutPlanQueryEntity,
                                                   String queryUser, BigInteger minId, boolean isSeat) {
        if (callOutPlanQueryEntity.getStartDate() != null) {
            criteria.andCreateTimeGreaterThan(callOutPlanQueryEntity.getStartDate());
        }
        if (callOutPlanQueryEntity.getEndDate() != null) {
            criteria.andCreateTimeLessThan(callOutPlanQueryEntity.getEndDate());
        }
        if (!isSeat && callOutPlanQueryEntity.getAuthLevel() == 1) {
            criteria.andCustomerIdEqualTo(Integer.valueOf(callOutPlanQueryEntity.getCustomerId()));
        } else if (!isSeat && callOutPlanQueryEntity.getAuthLevel() == 2) {
            criteria.andOrgCodeEqualTo(callOutPlanQueryEntity.getOrgCode());
        } else if (!isSeat && callOutPlanQueryEntity.getAuthLevel() == 3) {
            criteria.andOrgCodeLike(callOutPlanQueryEntity.getOrgCode() + "%");
        }
        if (StringUtils.isNotBlank(queryUser)) {
            criteria.andCustomerIdEqualTo(Integer.valueOf(queryUser));
        }
        if (StringUtils.isNotBlank(callOutPlanQueryEntity.getPhoneNum())) {
            criteria.andPhoneNumLike("%" + callOutPlanQueryEntity.getPhoneNum() + "%");
        }
        if (StringUtils.isNotBlank(callOutPlanQueryEntity.getDurationMin())) {
            criteria.andDurationGreaterThan(Integer.valueOf(callOutPlanQueryEntity.getDurationMin()));
        }
        if (StringUtils.isNotBlank(callOutPlanQueryEntity.getDurationMax())) {
            criteria.andDurationLessThanOrEqualTo(Integer.valueOf(callOutPlanQueryEntity.getDurationMax()));
        }
        if (StringUtils.isNotBlank(callOutPlanQueryEntity.getBillSecMin())) {
            criteria.andBillSecGreaterThan(Integer.valueOf(callOutPlanQueryEntity.getBillSecMin()));
        }
        if (StringUtils.isNotBlank(callOutPlanQueryEntity.getBillSecMax())) {
            criteria.andBillSecLessThanOrEqualTo(Integer.valueOf(callOutPlanQueryEntity.getBillSecMax()));
        }
        String accurateIntent = callOutPlanQueryEntity.getAccurateIntent();
        if (StringUtils.isNotBlank(accurateIntent)) {
            if (accurateIntent.contains(",")) {
                String[] arr = accurateIntent.split(",");
                criteria.andAccurateIntentIn(Arrays.asList(arr));
            } else {
                criteria.andAccurateIntentEqualTo(accurateIntent);
            }
        }
        String freason = callOutPlanQueryEntity.getFreason();
        if (StringUtils.isNotBlank(freason)) {
            if (freason.contains(",")) {
                String[] arr = freason.split(",");
                criteria.andReasonIn(Arrays.asList(arr));
            } else {
                criteria.andReasonEqualTo(freason);
            }
        }
        if (StringUtils.isNotBlank(callOutPlanQueryEntity.getCallId())) {
            criteria.andCallIdEqualTo(new BigInteger(callOutPlanQueryEntity.getCallId()));
        }
        if (StringUtils.isNotBlank(callOutPlanQueryEntity.getTempId())) {
            criteria.andTempIdEqualTo(callOutPlanQueryEntity.getTempId());
        }
        if (StringUtils.isNotBlank(callOutPlanQueryEntity.getIsRead())) {
            criteria.andIsreadEqualTo(Integer.valueOf(callOutPlanQueryEntity.getIsRead()));
        }
        if (callOutPlanQueryEntity.getBatchId()!=null) {
            criteria.andBatchIdEqualTo(callOutPlanQueryEntity.getBatchId());
        }
        if (callOutPlanQueryEntity.getIntervened() != null) {
            if (callOutPlanQueryEntity.getIntervened().equals("1")) {
                criteria.andIntervenedEqualTo(true);
            } else if (callOutPlanQueryEntity.getIntervened().equals("0")) {
                criteria.andIntervenedEqualTo(false);
            }
        }
        criteria.andIsdelEqualTo(0);
        criteria.andCallStateGreaterThanOrEqualTo(Constant.CALLSTATE_HANGUP_OK);
        if (minId != null) {
            criteria.andCallIdLessThan(minId);
        }
        if(callOutPlanQueryEntity.getNotInList()!=null && callOutPlanQueryEntity.getNotInList().size()>0){
            criteria.andCallIdNotIn(callOutPlanQueryEntity.getNotInList());
        }
        if(callOutPlanQueryEntity.getInList()!=null && callOutPlanQueryEntity.getInList().size()>0){
            criteria.andCallIdIn(callOutPlanQueryEntity.getInList());
        }
        return criteria;
    }


    @Override
    public void generateAudioFile(Date finalStart, Date finalEnd, Integer authLevel, String userId, Integer orgId,
                                  CallRecordListReq callRecordListReq, Integer isDesensitization, String recordId) {

        int startCount = Integer.valueOf(callRecordListReq.getStartCount());
        int endCount = Integer.valueOf(callRecordListReq.getEndCount());
        int allCount = endCount - startCount + 1;

        int excelCount = allCount / batchCount + 1;
        int remainder = allCount % batchCount;
        log.info("一共[{}]个excel，余数[{}]", excelCount, remainder);
        BigInteger minId = null;

        String uuidDir = IdGenUtil.uuid();
        String savePath = downloadPath + File.separator + uuidDir;
        File parentDir = new File(savePath);
        if (!parentDir.exists()) {
            parentDir.mkdirs();
        }

        for (int i = 1; i <= excelCount; i++) {

            MyCallOutPlanQueryEntity myCallOutPlanQueryEntity = callDetailService.prepareQuery(finalStart, finalEnd,authLevel,userId,callRecordListReq,orgId);

            List<BigInteger> idList;
            if (i == excelCount) {
                idList = callrecord(myCallOutPlanQueryEntity, callRecordListReq, minId, remainder);
            } else {
                idList = callrecord(myCallOutPlanQueryEntity, callRecordListReq, minId, batchCount);
            }
            if (idList != null && idList.size() > 0) {
                int listSize = idList.size();
                minId = idList.get(listSize - 1);

                log.info("开始第[{}]波下载,listSize[{}]", i, listSize);

                List<CallOutRecord> records = selectCallOutRecordList(idList);

                for (CallOutRecord callOutRecord : records) {
                    String recordUrl = callOutRecord.getRecordUrl();
                    if (recordUrl != null) {
                        HttpDownload.downLoadFromUrl(recordUrl, callOutRecord.getCallId() + ".wav", savePath);
                    }
                }

            }
        }

        try {
            log.info("开始压缩文件");
            String zipFilePath = downloadPath + File.separator + uuidDir + ".zip";
            OutputStream zipOut = new FileOutputStream(new File(zipFilePath));
            ZipUtil.zip(parentDir, zipOut);

            String nasUrl = uploadNas(zipFilePath, Long.valueOf(userId), "callAudioZip");
            log.info("文件上传nas后，返回的url[{}],userId[{}]", nasUrl, userId);

            if (StringUtils.isNotEmpty(nasUrl)) {
                //上传成功
                Result.ReturnData<Boolean> result = iDispatchPlanOut.updExportFile(recordId,1,nasUrl);
                log.info("上传文件成功回调结果是 result[{}]",result);
                FileUtil.deleteFile(parentDir);
                FileUtil.delete(zipFilePath);
                log.info("删除文件成功,parentDir[{}]", parentDir);
            } else {
                //上传失败
                log.error("上传文件失败");
                Result.ReturnData<Boolean> result = iDispatchPlanOut.updExportFile(recordId,4,null);
                log.info("失败回调结果是 result[{}]",result);
            }

        } catch (Exception e) {
            log.error("压缩文件，上传文件，出现异常", e);
            //失败
            Result.ReturnData<Boolean> result = iDispatchPlanOut.updExportFile(recordId,4,null);
            log.info("失败回调结果是 result[{}]",result);
        }

    }

    private List<CallOutRecord> selectCallOutRecordList(List<BigInteger> idList) {

        CallOutRecordExample example = new CallOutRecordExample();
        example.createCriteria().andCallIdIn(idList);
        return callOutRecordMapper.selectByExample(example);
    }

}
