package com.guiji.api.controller;

import com.guiji.api.ro.*;
import com.guiji.api.vo.*;
import com.guiji.cfg.ThirdApiMqConfig;
import com.guiji.clm.api.LineMarketRemote;
import com.guiji.clm.api.VoipMarketRemote;
import com.guiji.clm.model.SimLineVo;
import com.guiji.clm.model.SipLineVO;
import com.guiji.common.GenericRo;
import com.guiji.common.SuccessBody;
import com.guiji.component.result.Result;
import com.guiji.dispatch.api.DispatchThirdApi;
import com.guiji.dispatch.api.IThirdApiOut;
import com.guiji.dispatch.model.*;
import com.guiji.exception.ThirdApiException;
import com.guiji.exception.ThirdApiExceptionEnum;
import com.guiji.guiyu.message.component.QueueSender;
import com.guiji.utils.JsonUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

/**
 * dispatch相关的api
 */
@RestController
@RequestMapping("/thirdApi")
public class DispatchApiController {

    /**
     * logger
     */
    private final static Logger LOGGER = LoggerFactory.getLogger(DispatchApiController.class);

    @Autowired
    DispatchThirdApi dispatchThirdApi;

    @Autowired
    IThirdApiOut iThirdApiOut;

    @Autowired
    QueueSender queueSender;

    public static final int MAX_PAGE_SIZE = 5000;

    public static final int RULE_NAME_LEN = 32;

//    /**
//     * 添加拨打策略
//     *
//     * @param callStrategyRo
//     * @return
//     */
//    @PostMapping("/addCallRule")
//    public SuccessBody addCallStrategy(@RequestBody CallStrategyRo callStrategyRo) {
//
//        //校验参数
//        if (StringUtils.isEmpty(callStrategyRo.getCallHour())
//                || StringUtils.isEmpty(callStrategyRo.getBotenceName())
//                || StringUtils.isEmpty(callStrategyRo.getBotenceId())
//                || StringUtils.isEmpty(callStrategyRo.getLineId())
//                || StringUtils.isEmpty(callStrategyRo.getRuleName()) || callStrategyRo.getRuleName().length() > RULE_NAME_LEN
//                || callStrategyRo.getIsClear() == null || (callStrategyRo.getIsClear() != 0 && callStrategyRo.getIsClear() != 1 )
//                || callStrategyRo.getIsSms() == null || (callStrategyRo.getIsSms() != 0 && callStrategyRo.getIsSms() != 1 )
//                || callStrategyRo.getLineType() == null || (callStrategyRo.getLineType() != 1 && callStrategyRo.getLineType() != 2 )) {
//            throw new ThirdApiException(ThirdApiExceptionEnum.ILLEGAL_ARG);
//        }
//
//        IStrategyDialRel strategy = new IStrategyDialRel();
//
//        strategy.setRobotName(callStrategyRo.getBotenceName());
//        strategy.setCallHour(callStrategyRo.getCallHour());
//        strategy.setRobot(callStrategyRo.getBotenceId());
//        strategy.setIsSms(Integer.valueOf(callStrategyRo.getIsSms()));
//        strategy.setLineType(callStrategyRo.getLineType());
//        strategy.setStrategyName(callStrategyRo.getRuleName());
//        strategy.setUserId(callStrategyRo.getUserId());
//        strategy.setClean(Integer.valueOf(callStrategyRo.getIsClear()));
//        if(callStrategyRo.getGroupId() != null) {
//            strategy.setCallAgent(callStrategyRo.getGroupId().toString());
//        }
//
//        List<IStrategyLine> lineList = new ArrayList<>();
//
//        for (String str : callStrategyRo.getLineId().split("\\|")) {
//
//            if (!StringUtils.isNumeric(str)) {
//                throw new ThirdApiException(ThirdApiExceptionEnum.ILLEGAL_ARG);
//            }
//
//            IStrategyLine iStrategyLine = new IStrategyLine();
//
//            iStrategyLine.setLineId(Integer.valueOf(str));
//
//            lineList.add(iStrategyLine);
//        }
//
//        strategy.setLineList(lineList);
//
//        try {
//            CallStrategyVO callStrategyVO = new CallStrategyVO();
//
//            String timeStrategy = callStrategyRo.getTimeStrategy();
//            String[] timeStrategys = timeStrategy.split("\\^");
//
//            //第一位，禁拨星期
//            String timeWeek = timeStrategys[0];
//
//            String[] timeWeeks = timeWeek.split("");
//
//            List<Short> rejectWeek = new ArrayList<>();
//
//            for (String week : timeWeeks) {
//                rejectWeek.add(Short.valueOf(week));
//            }
//
//            callStrategyVO.setRejectWeek(rejectWeek);
//
//            //禁拨日期
//            callStrategyVO.setRejectDateList("["+timeStrategys[1]+"]");
//
//            //是否启用节假日策略
//            callStrategyVO.setHolidayFlag(Integer.valueOf(timeStrategys[2]));
//
//            strategy.setCallStrategyVO(callStrategyVO);
//        } catch (Exception ex) {
//            LOGGER.error("解析拨打策略中的时间参数出错：{}", callStrategyRo.getTimeStrategy());
//
//            throw new ThirdApiException(ThirdApiExceptionEnum.ILLEGAL_ARG);
//        }
//
//        Result.ReturnData<IStrategyDialRel> iStrategyReturnData = iThirdApiOut.addStrategy(strategy);
//
//        if (checkRes(iStrategyReturnData)) {
//            return new SuccessBody();
//        } else {
//            throw new ThirdApiException(ThirdApiExceptionEnum.ADD_STRATEGY_ERROR);
//        }
//    }

    boolean checkRes(Result.ReturnData data) {
        return data != null && data.success && "0".equals(data.getCode());
    }

//    /**
//     * 获取拨打策略详情
//     *
//     * @param strategySearchInfo
//     * @return
//     */
//    @PostMapping("/getCallRuleByName")
//    public StrategyDetail getDetail(@RequestBody StrategySearchInfo strategySearchInfo) {
//
//        if (StringUtils.isEmpty(strategySearchInfo.getRuleName())) {
//            throw new ThirdApiException(ThirdApiExceptionEnum.ILLEGAL_ARG);
//        }
//
//        Long userId = strategySearchInfo.getUserId();
//        String ruleName = strategySearchInfo.getRuleName();
//
//        Result.ReturnData<IStrategy> iStrategyReturnData = iThirdApiOut.queryStrategyByName(ruleName, userId.toString());
//
//
//        if (checkRes(iStrategyReturnData)) {
//
//            IStrategy strategy = iStrategyReturnData.getBody();
//
//            if (null == strategy) {
//                throw new ThirdApiException(ThirdApiExceptionEnum.QUERY_NO_RESULT);
//            }
//
//            StrategyDetail detail = new StrategyDetail();
//
//            detail.setBotenceId(strategy.getRobot());
//            detail.setBotenceName(strategy.getRobotName());
//            detail.setCallHour(strategy.getCallHour());
//            detail.setCreateTime(String.valueOf(strategy.getAddTime().getTime()));
//            detail.setIsSms(strategy.getIsSms());
//            detail.setIsClear(strategy.getClean().toString());
//            StringBuilder lineIds = new StringBuilder();
//            for (IStrategyLine line : strategy.getLineList()) {
//                lineIds.append(line.getLineId().toString());
////                lineIds.append("^");
////                lineIds.append(line.getLineName());
////                lineIds.append("^");
////                lineIds.append(line.getLineType());
//                lineIds.append("|");
//            }
//            detail.setLineId(lineIds.length() > 0 ? lineIds.substring(0, lineIds.length() - 1) : "");
//            detail.setRuleName(strategy.getStrategyName());
//            detail.setSmsLabel(strategy.getSmsLabel());
//            return detail;
//
//        } else {
//            throw new ThirdApiException(ThirdApiExceptionEnum.QUERY_STRATEGY_ERROR);
//        }
//
//    }

    /**
     * 添加计划
     * 清除错误的手机号/重复的手机号
     * @param ro
     * @return
     */
    @RequestMapping("/addPlan")
    public AddPlanRes addPlan(@RequestBody PlanRo ro){
        //校验参数
        if (StringUtils.isEmpty(ro.getCallHour())
                || StringUtils.isEmpty(ro.getBotenceId())
                || StringUtils.isEmpty(ro.getLineIds())
                || StringUtils.isEmpty(ro.getMobileList())
                || ro.getIsClear() == null || (ro.getIsClear() != 0 && ro.getIsClear() != 1 )) {
            throw new ThirdApiException(ThirdApiExceptionEnum.ILLEGAL_ARG);
        }

        String mobileList = ro.getMobileList();

        String[] mobiles = mobileList.split("\\|");

        List<PhoneRo> lists = new ArrayList<>();

        List<String> mobileNos = new ArrayList<>();

        StringBuilder err = new StringBuilder();

        int rightCount = 0;
        int failCount = 0;

        for (String mobileInfo : mobiles) {
            String[] infos = mobileInfo.split("\\^", 5);

            String mobile = infos[0];

            if(StringUtils.isNotEmpty(mobile) && StringUtils.isNumeric(mobile) && (mobile.length() == 11 || mobile.length() == 12)) {
                if (mobileNos.contains(mobile)) {
                    err.append(mobileInfo);
                    err.append("|");
                    failCount++;
                    continue;
                } else {
                    mobileNos.add(mobile);
                    rightCount++;
                }
            } else {
                err.append(mobileInfo);
                err.append("|");
                failCount++;
                continue;
            }

            String attach = infos[1];
            String params = infos[2];
            String custName = infos[3];
            String custCompany = infos[4];

            PhoneRo phoneRo = new PhoneRo();

            phoneRo.setAttach(attach);
            phoneRo.setParams(params);
            phoneRo.setPhoneNo(mobile);
            phoneRo.setCustName(custName);
            phoneRo.setCustCompany(custCompany);

            lists.add(phoneRo);
        }

        DispatchPlanForApiRo apiRo = new DispatchPlanForApiRo();

        if(ro.getGroupId() != null) {
            apiRo.setCallAgent(ro.getGroupId().toString());
        }
        apiRo.setBatchName(ro.getBatchName());
        apiRo.setCallData(Integer.valueOf(ro.getCallDate()));
        apiRo.setCallHour(ro.getCallHour());
        apiRo.setClean(ro.getIsClear());

        checkCallHour(ro.getCallHour());

        List<Integer> lineIds = new ArrayList<>();

        for(String lineId : ro.getLineIds().split(",")) {
            lineIds.add(Integer.valueOf(lineId));
        }

        checkLineIds(lineIds, ro.getUserId().intValue());

        apiRo.setLineIds(lineIds);
        apiRo.setPhoneRoList(lists);
        apiRo.setUserId(ro.getUserId().intValue());
        apiRo.setRobot(ro.getBotenceId());
        apiRo.setBatchName(ro.getBatchName());
        apiRo.setBatchCallBackUrl(ro.getNotifyBatchUrl());
        apiRo.setSingleCallBackUrl(ro.getNotifyUrl());

        Result.ReturnData<Boolean> booleanReturnData = dispatchThirdApi.addBatchPlan(apiRo);

        if(checkRes(booleanReturnData)) {
            AddPlanRes addPlanRes = new AddPlanRes();

            addPlanRes.setFailCount(failCount);
            addPlanRes.setRightCount(rightCount);
            addPlanRes.setFailMobileInfo(err.length() > 0 ? err.deleteCharAt(err.length()-1).toString() : "");

            return addPlanRes;
        } else {
            if(StringUtils.equals(booleanReturnData.getCode(),"2000000008")) {
                throw new ThirdApiException(ThirdApiExceptionEnum.DUPLICATE_BATCH_NAME);
            } else {
                throw new ThirdApiException(ThirdApiExceptionEnum.ADD_BATCH_PLAN_ERROR);
            }
        }
    }

    private void checkCallHour(String callHour) {

        for (String str : callHour.split(",")) {

            if(!StringUtils.isNumeric(str)) {
                throw new ThirdApiException(ThirdApiExceptionEnum.ILLEGAL_ARG);
            }

            if(Integer.valueOf(str).intValue() < 9 || Integer.valueOf(str).intValue() > 20) {
                throw new ThirdApiException(ThirdApiExceptionEnum.ILLEGAL_ARG);
            }
        }

    }

    private void checkLineIds(List<Integer> lineIds, Integer userId) {

        List<SipLineVO> lineList = getLineList(userId);

        Map<Integer, SipLineVO> map = new HashMap<>();

        lineList.forEach(obj -> {
            map.put(obj.getLineId(), obj);
        });

        Set<Integer> lineTypeSet = new HashSet<>();

        for (Integer lineId : lineIds) {

            if(!map.containsKey(lineId)) {
                throw new ThirdApiException(ThirdApiExceptionEnum.ILLEGAL_ARG);
            } else {
                lineTypeSet.add(map.get(lineId).getLineType());
            }

        }

        if(lineTypeSet.size() > 1) {
            throw new ThirdApiException(ThirdApiExceptionEnum.ILLEGAL_ARG);
        }

    }

    @Autowired
    LineMarketRemote lineMarketRemote;

    @Autowired
    VoipMarketRemote voipMarketRemote;

    public List<SipLineVO> getLineList(Integer userId) {

        Result.ReturnData<List<SipLineVO>> sipData = lineMarketRemote.queryUserSipLineList(userId.toString());

        List<SipLineVO> sipLineVOS = new ArrayList<>();

        if (sipData != null && sipData.success) {
            if (!CollectionUtils.isEmpty(sipData.getBody())) {
                sipData.getBody().forEach(obj -> {

                    SipLineVO vo = new SipLineVO();

                    vo.setLineId(obj.getLineId());
                    vo.setLineName(obj.getLineName());
                    vo.setLineType(1);

                    sipLineVOS.add(vo);
                });
            }
        }

        Result.ReturnData<List<SimLineVo>> simData = voipMarketRemote.querySimLineInfo((long) userId.intValue());

        if (simData != null && simData.success) {
            if (!CollectionUtils.isEmpty(simData.getBody())) {
                simData.getBody().forEach(obj -> {
                    SipLineVO vo = new SipLineVO();

                    vo.setLineId(obj.getLineId().intValue());
                    vo.setLineName(obj.getLineName());
                    vo.setLineType(2);

                    sipLineVOS.add(vo);
                });
            }
        }

        if (sipLineVOS.size() > 0) {

           return sipLineVOS;

        } else {
            throw new ThirdApiException(ThirdApiExceptionEnum.ILLEGAL_ARG);
        }

    }


//    /**
//     * 添加计划
//     * 清除错误的手机号/重复的手机号
//     * @param addPlanRo
//     * @return
//     */
//    @RequestMapping("/addBatchPlan")
//    public AddPlanRes addPlan(@RequestBody AddPlanRo addPlanRo) {
//
//        if (StringUtils.isEmpty(addPlanRo.getBatchName())
//                || StringUtils.isEmpty(addPlanRo.getCallDate())
//                || StringUtils.isEmpty(addPlanRo.getMobileList())
//                || StringUtils.isEmpty(addPlanRo.getNotifyBatchUrl())
//                || StringUtils.isEmpty(addPlanRo.getNotifyUrl())
//                || StringUtils.isEmpty(addPlanRo.getRuleName())) {
//            throw new ThirdApiException(ThirdApiExceptionEnum.ILLEGAL_ARG);
//        }
//
//        String mobileList = addPlanRo.getMobileList();
//
//        String[] mobiles = mobileList.split("\\|");
//
//        List<IPlanThirdPhoneDto> lists = new ArrayList<>();
//
//        List<String> mobileNos = new ArrayList<>();
//
//        StringBuilder err = new StringBuilder();
//
//        int rightCount = 0;
//        int failCount = 0;
//
//        for (String mobileInfo : mobiles) {
//            String[] infos = mobileInfo.split("\\^", 5);
//
//            String mobile = infos[0];
//
//            if(StringUtils.isNotEmpty(mobile) && StringUtils.isNumeric(mobile) && (mobile.length() == 11 || mobile.length() == 12)) {
//                if (mobileNos.contains(mobile)) {
//                    err.append(mobileInfo);
//                    err.append("|");
//                    failCount++;
//                    continue;
//                } else {
//                    mobileNos.add(mobile);
//                    rightCount++;
//                }
//            } else {
//                err.append(mobileInfo);
//                err.append("|");
//                failCount++;
//                continue;
//            }
//
//            String attach = infos[1];
//            String params = infos[2];
//            String custName = infos[3];
//            String custCompany = infos[4];
//
//            IPlanThirdPhoneDto iPlanThirdPhoneDto = new IPlanThirdPhoneDto();
//
//            iPlanThirdPhoneDto.setAttach(attach);
//            iPlanThirdPhoneDto.setParams(params);
//            iPlanThirdPhoneDto.setPhone(mobile);
//            iPlanThirdPhoneDto.setCustName(custName);
//            iPlanThirdPhoneDto.setCustCompany(custCompany);
//
//            lists.add(iPlanThirdPhoneDto);
//        }
//
//        IPlanThirdBatchDto iPlanThirdBatchDto = new IPlanThirdBatchDto();
//
//        iPlanThirdBatchDto.setStrategyName(addPlanRo.getRuleName());
//        iPlanThirdBatchDto.setBatchName(addPlanRo.getBatchName());
//        iPlanThirdBatchDto.setUserId(addPlanRo.getUserId().intValue());
//        iPlanThirdBatchDto.setBatchCount(addPlanRo.getBatchCount());
//        iPlanThirdBatchDto.setCallData(Integer.valueOf(addPlanRo.getCallDate()));
//        iPlanThirdBatchDto.setNotifyBatchUrl(addPlanRo.getNotifyBatchUrl());
//        iPlanThirdBatchDto.setNotifyUrl(addPlanRo.getNotifyUrl());
//        iPlanThirdBatchDto.setPhoneList(lists);
//
//        Result.ReturnData<IPlanThirdBatchVo> iPlanThirdBatchVoReturnData = iThirdApiOut.addPlanThirdBatch(iPlanThirdBatchDto);
//
//        if (checkRes(iPlanThirdBatchVoReturnData)) {
//
//            AddPlanRes addPlanRes = new AddPlanRes();
//
//            addPlanRes.setFailCount(failCount);
//            addPlanRes.setRightCount(rightCount);
//            addPlanRes.setFailMobileInfo(err.length() > 0 ? err.deleteCharAt(err.length()-1).toString() : "");
//
//            return addPlanRes;
//        } else {
//            throw new ThirdApiException(ThirdApiExceptionEnum.ADD_BATCH_PLAN_ERROR);
//        }
//
//        return null;
//    }

    /**
     * 批量任务暂停
     *
     * @param changePlanActionRo
     * @return
     */
    @RequestMapping("/batchPlanPause")
    public SuccessBody pause(@RequestBody ChangePlanActionRo changePlanActionRo) {

        OperDispatchRo ro = new OperDispatchRo();

        ro.setUserId(changePlanActionRo.getUserId().intValue());
        ro.setBatchName(changePlanActionRo.getBatchName());

        Result.ReturnData<Boolean> booleanReturnData = dispatchThirdApi.suspendPlanThirdBatch(ro);

        if(checkRes(booleanReturnData)) {
            return new SuccessBody();
        } else {
            throw new ThirdApiException(ThirdApiExceptionEnum.PAUSE_ERROR);
        }
    }

    /**
     * 批量任务停止
     *
     * @param changePlanActionRo
     * @return
     */
    @RequestMapping("/batchPlanStop")
    public SuccessBody stop(@RequestBody ChangePlanActionRo changePlanActionRo) {

        OperDispatchRo ro = new OperDispatchRo();

        ro.setUserId(changePlanActionRo.getUserId().intValue());
        ro.setBatchName(changePlanActionRo.getBatchName());

        Result.ReturnData<Boolean> booleanReturnData = dispatchThirdApi.stopPlanThirdBatch(ro);

        if(checkRes(booleanReturnData)) {
            return new SuccessBody();
        } else {
            throw new ThirdApiException(ThirdApiExceptionEnum.STOP_ERROR);
        }
    }

    /**
     * 批量任务恢复
     *
     * @param changePlanActionRo
     * @return
     */
    @RequestMapping("/batchPlanRestart")
    public SuccessBody restart(@RequestBody ChangePlanActionRo changePlanActionRo) {

        OperDispatchRo ro = new OperDispatchRo();

        ro.setUserId(changePlanActionRo.getUserId().intValue());
        ro.setBatchName(changePlanActionRo.getBatchName());

        Result.ReturnData<Boolean> booleanReturnData = dispatchThirdApi.recoveryPlanThirdBatch(ro);

        if(checkRes(booleanReturnData)) {
            return new SuccessBody();
        } else {
            throw new ThirdApiException(ThirdApiExceptionEnum.RESTART_ERROR);
        }
    }

    /**
     * 获取拨打任务详情
     *
     * @param queryPlanRo
     * @return
     */
    @RequestMapping("/batchPlanDetails")
    public PlanExecResultVo details(@RequestBody QueryPlanRo queryPlanRo) {

        if (StringUtils.isEmpty(queryPlanRo.getBatchName())) {
            throw new ThirdApiException(ThirdApiExceptionEnum.ILLEGAL_ARG);
        }

        Long userId = queryPlanRo.getUserId();
        String batchName = queryPlanRo.getBatchName();

        OperDispatchRo ro = new OperDispatchRo();

        ro.setUserId(userId.intValue());
        ro.setBatchName(batchName);

        Result.ReturnData<DispatchPlanBatchAddVo> planThirdBatchDetail = dispatchThirdApi.getPlanThirdBatchDetail(ro);

        if (checkRes(planThirdBatchDetail)) {

            DispatchPlanBatchAddVo body = planThirdBatchDetail.getBody();

            if (body != null) {
                PlanExecResultVo planExecResultVo = new PlanExecResultVo();

                planExecResultVo.setAcceptCount(body.getAcceptCount());
                planExecResultVo.setBatchName(body.getBatchName());
                planExecResultVo.setFailCount(body.getFailCount() == null ? 0 : body.getFailCount());
                planExecResultVo.setSuccessCount(body.getSuccessCount() == null ? 0 : body.getSuccessCount());


                List<PhoneVo> failMobilelist = body.getFailList();

                StringBuilder res = new StringBuilder();

                failMobilelist.forEach(obj -> {
                    res.append(obj.getPhoneNo());
                    res.append("^");
                    res.append(StringUtils.isEmpty(obj.getAttach()) ? "" : obj.getAttach());
                    res.append("^");
                    res.append(StringUtils.isEmpty(obj.getParams()) ? "" : obj.getParams());
                    res.append("^");
                    res.append(StringUtils.isEmpty(obj.getCustName()) ? "" : obj.getCustName());
                    res.append("^");
                    res.append(StringUtils.isEmpty(obj.getCustCompany()) ? "" : obj.getCustCompany());
                    res.append("|");
                });

                planExecResultVo.setFailMobilelist(res.length() > 0 ? res.deleteCharAt(res.length() - 1).toString() : "");

                return planExecResultVo;
            }
        }


        throw new ThirdApiException(ThirdApiExceptionEnum.QUERY_NO_RESULT);

    }

    /**
     * 通过批次号查询该批次的拨打情况
     *
     * @param queryPlanRo
     * @return
     */
    @RequestMapping("/getBatchPlanCallSummary")
    public BatchPlanCallSumary planResult(@RequestBody QueryPlanRo queryPlanRo) {

        if (StringUtils.isEmpty(queryPlanRo.getBatchName())) {
            throw new ThirdApiException(ThirdApiExceptionEnum.ILLEGAL_ARG);
        }

        iThirdApiOut.getcall4BatchName(queryPlanRo.getUserId(), queryPlanRo.getBatchName(), 0, 0);

        Long userId = queryPlanRo.getUserId();
        String batchName = queryPlanRo.getBatchName();

        OperDispatchRo ro = new OperDispatchRo();

        ro.setBatchName(batchName);
        ro.setUserId(userId.intValue());

        Result.ReturnData<IPlanThirdBatchDialVo> planThirdBatchDetail = dispatchThirdApi.getPlanThirdBatchDial(ro);

        if (checkRes(planThirdBatchDetail)) {

            IPlanThirdBatchDialVo body = planThirdBatchDetail.getBody();

            if (body != null) {
                BatchPlanCallSumary batchPlanCallSumary = new BatchPlanCallSumary();

                batchPlanCallSumary.setAcceptCount(body.getAcceptCount());
                batchPlanCallSumary.setBatchName(body.getBatchName());
                batchPlanCallSumary.setEndCount(body.getEndCount());
                batchPlanCallSumary.setPlanCount(body.getPlanCount());

                return batchPlanCallSumary;
            }
        }
        throw new ThirdApiException(ThirdApiExceptionEnum.QUERY_NO_RESULT);
    }


    /**
     * 通过批次号查询该批次的拨打情况
     *
     * @param batchPlanListRo
     * @return
     */
    @RequestMapping("/getBatchPlanList")
    public BatchPlanListVo planPhoneList(@RequestBody BatchPlanListRo batchPlanListRo) {

        if (StringUtils.isEmpty(batchPlanListRo.getBatchName())
                || batchPlanListRo.getPage() == null
                || batchPlanListRo.getPageNum() == null
                || batchPlanListRo.getPage() < 0) {
            throw new ThirdApiException(ThirdApiExceptionEnum.ILLEGAL_ARG);
        }

        if (batchPlanListRo.getPage() > MAX_PAGE_SIZE) {
            throw new ThirdApiException(ThirdApiExceptionEnum.QUEYR_OVER_NUM);
        }

        QueryPlanThirdRo ro = new QueryPlanThirdRo();

        ro.setUserId(batchPlanListRo.getUserId().intValue());
        ro.setBatchName(batchPlanListRo.getBatchName());
        ro.setPage(batchPlanListRo.getPage());
        ro.setPageNum(batchPlanListRo.getPageNum());

        Result.ReturnData<IPlanThirdBatchPhoneVo> planThirdBatchDetail = dispatchThirdApi.queryPlanThirdBatchPage(ro);

        if (checkRes(planThirdBatchDetail)) {

            IPlanThirdBatchPhoneVo body = planThirdBatchDetail.getBody();

            if (body != null) {
                BatchPlanListVo batchPlanListVo = new BatchPlanListVo();

                batchPlanListVo.setBatchName(body.getBatchName());
                batchPlanListVo.setTotalPage(body.getTotalPage());
                batchPlanListVo.setPageNum(body.getPageNum());
                batchPlanListVo.setPage(body.getPageNo());
                batchPlanListVo.setBatchCount(body.getBatchCount());

                List<PhoneVo> failMobilelist = body.getMobileList();

                StringBuilder res = new StringBuilder();

                failMobilelist.forEach(obj -> {
                    res.append(obj.getPhoneNo());
                    res.append("^");
                    res.append(StringUtils.isEmpty(obj.getAttach()) ? "" : obj.getAttach());
                    res.append("^");
                    res.append(StringUtils.isEmpty(obj.getParams()) ? "" : obj.getParams());
                    res.append("^");
                    res.append(StringUtils.isEmpty(obj.getCustName()) ? "" : obj.getCustName());
                    res.append("^");
                    res.append(StringUtils.isEmpty(obj.getCustCompany()) ? "" : obj.getCustCompany());
                    res.append("|");
                });

                batchPlanListVo.setMobileList(res.length() > 0 ? res.deleteCharAt(res.length() - 1).toString() : "");

                return batchPlanListVo;
            }
        }
        throw new ThirdApiException(ThirdApiExceptionEnum.QUERY_NO_RESULT);

    }

    /**
     * 通过批次号查询该批次的通话列表
     * 将数据放入mq中
     * mq消费直接扔到redis的延时队列中
     * 由一个线程监听队列并进行回调
     *
     * @param planCallListRo
     * @return
     */
    @RequestMapping("/getBatchPlanCallList")
    public SuccessBody planCallList(@RequestBody PlanCallListRo planCallListRo) {

        if (StringUtils.isEmpty(planCallListRo.getBatchName())
                || planCallListRo.getPage() == null
                || planCallListRo.getPageNum() == null
                || planCallListRo.getPage() < 0) {
            throw new ThirdApiException(ThirdApiExceptionEnum.ILLEGAL_ARG);
        }

        if (planCallListRo.getPage() > MAX_PAGE_SIZE) {
            throw new ThirdApiException(ThirdApiExceptionEnum.QUEYR_OVER_NUM);
        }

        queueSender.send(ThirdApiMqConfig.BATCH_PLAN_TASK, JsonUtils.bean2Json(planCallListRo));

        return new SuccessBody();

    }


}
