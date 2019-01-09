package com.guiji.ccmanager.service.impl;

import com.guiji.callcenter.dao.CallOutPlanMapper;
import com.guiji.callcenter.dao.LineInfoMapper;
import com.guiji.callcenter.dao.entity.CallOutPlan;
import com.guiji.callcenter.dao.entity.CallOutPlanExample;
import com.guiji.callcenter.dao.entity.LineInfo;
import com.guiji.calloutserver.api.ICallPlan;
import com.guiji.ccmanager.constant.CcmanagerExceptionEnum;
import com.guiji.ccmanager.constant.Constant;
import com.guiji.ccmanager.service.CallManagerOutService;
import com.guiji.ccmanager.service.CallStateService;
import com.guiji.ccmanager.vo.TempIsOkResult;
import com.guiji.ccmanager.vo.TempServerList;
import com.guiji.common.exception.GuiyuException;
import com.guiji.component.result.Result;
import com.guiji.fsmanager.api.ITemp;
import com.guiji.utils.FeignBuildUtil;
import com.guiji.utils.ServerUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.*;

/**
 * @Auther: 黎阳
 * @Date: 2018/10/30 0030 13:46
 * @Description:
 */
@Service
public class CallManagerOutServiceImpl implements CallManagerOutService {

    private final Logger log = LoggerFactory.getLogger(CallManagerOutServiceImpl.class);

    @Autowired
    private LineInfoMapper lineInfoMapper;
    @Autowired
    private CallOutPlanMapper callOutPlanMapper;
    @Autowired
    private ITemp tempApiFeign;
    @Autowired
    private ICallPlan callPlanApi;
    @Autowired
    private DiscoveryClient discoveryClient;
    @Autowired
    CallStateService callStateService;

    //    @Override
    public void startcallplan(String customerId, String tempId, String lineId) {

        //根据线路id到lineinfo表中查询线路是否存在，不存在则返回线路不存在错误，并报警
        LineInfo lineInfoDB = lineInfoMapper.selectByPrimaryKey(Integer.parseInt(lineId));
        if (lineInfoDB == null) {
            // todo 报警
            log.warn("line not exist,lineId:" + lineId);
            throw new GuiyuException(CcmanagerExceptionEnum.EXCP_CCMANAGER_LINE_NOTEXIST);
        }
        //到calloutplan中查询该线路是否存在待呼叫或进行中的计划，存在则返回线路繁忙错误，并报警
        CallOutPlanExample example = new CallOutPlanExample();
        CallOutPlanExample.Criteria criteria = example.createCriteria();
        criteria.andLineIdEqualTo(Integer.valueOf(lineId));
        criteria.andCallStateBetween(Constant.CALLSTATE_INIT, Constant.CALLSTATE_AGENT_ANSWER);

        //对于电话都打了1.5个小时，还没有打完的要排除掉，认为任务没有正常回掉调度中心。当然也有可能是makecall的时候异常了，所有设大一点。
        Calendar c = Calendar.getInstance();
        c.add(Calendar.MINUTE, -5);
        criteria.andCreateTimeGreaterThan(c.getTime());

//        callStateService.updateCallState();//将5分钟前的没有回调的状态修改一下
        log.info("---<<<<<<<<<<<<<<<<<<<<----");
        List<CallOutPlan> existList = callOutPlanMapper.selectByExample(example);
        if (existList != null && existList.size() > 0) {
            // todo 报警
            log.warn("line is running,lineId:" + lineId);
            throw new GuiyuException(CcmanagerExceptionEnum.EXCP_CCMANAGER_LINE_RUNNING);
        }

        //调用fsmanager的模板是否存在接口，模板不存在处理如下
        Result.ReturnData<Boolean> resultTemp = tempApiFeign.istempexist(tempId.replace("_en", "_rec"));
        if (resultTemp.getCode().equals(Constant.SUCCESS_COMMON) && resultTemp.getBody() == false) {
            //返回模板不存在错误，并报警
            // todo 报警
            log.warn("temp is not exist,tempId:" + tempId);
            throw new GuiyuException(CcmanagerExceptionEnum.EXCP_CCMANAGER_TEMP_NOTEXIST);
        }

        //调用所有calloutserver的启动客户呼叫计划接口
        List<String> serverEurekaList = ServerUtil.getInstances(discoveryClient, Constant.SERVER_NAME_CALLOUTSERVER);

        //没有calloutserver服务
        if (serverEurekaList == null || serverEurekaList.size() == 0) {
            log.warn("calloutserver list is null,tempId:");
            throw new GuiyuException(CcmanagerExceptionEnum.EXCP_CCMANAGER_CALLOUTSERVER_ERROR);
        }

        //只要起动了一个calloutserver，计划就已经在跑了
//        boolean isPlanStrat = false;
//        for(String server:serverEurekaList) {
//            try {
//                ICallPlan callPlanApi = FeignBuildUtil.feignBuilderTarget(ICallPlan.class, Constant.PROTOCOL + server);
//                Result.ReturnData result = callPlanApi.startCallPlan(customerId, tempId, Integer.valueOf(lineId));
//                if(result!=null  && result.getCode().equals(Constant.SUCCESS_COMMON)){
//                    isPlanStrat = true;
//                }else{
//                    log.error("calloutserver startCallPlan return ReturnData[{}]:",result);
//                }
//            }catch (Exception e){
//                log.error("calloutserver startCallPlan error:"+e);
//            }
//        }
//        if(!isPlanStrat){
//            log.warn("calloutserver has error ,not start plan");
//            throw new GuiyuException(CcmanagerExceptionEnum.EXCP_CCMANAGER_CALLOUTSERVER_ERROR);
//        }
        log.info("---end  startcallplan----");
    }


    @Override
    public CallOutPlan getCallRecordById(BigInteger callId) {
        return callOutPlanMapper.selectByPrimaryKey(callId);
    }


/*    @Override
    public TempIsOkResult isTempOk(List<String> tempIdList) {

        //调用所有calloutserver的启动客户呼叫计划接口
        List<String> serverEurekaList = ServerUtil.getInstances(discoveryClient, Constant.SERVER_NAME_CALLOUTSERVER);

        TempIsOkResult tempIsOkResult = new TempIsOkResult();

        //没有calloutserver服务
        if (serverEurekaList == null || serverEurekaList.size() == 0) {
            log.error("没有可用的calloutserver服务");
            tempIsOkResult.setAllTempOk(false);
            return tempIsOkResult;
        }

        Map<String,TempServerList> resultMap = new HashMap();
        for(String tempId:tempIdList){
            resultMap.put(tempId,new TempServerList(tempId,new ArrayList<String>(),new ArrayList<String>()));
        }
        boolean isAllOk = true;
        for (String server : serverEurekaList) {

                ICallPlan callPlanApi = FeignBuildUtil.feignBuilderTarget(ICallPlan.class, Constant.PROTOCOL + server);
                Result.ReturnData<Map<String, Boolean>> result = callPlanApi.isTempOk(tempIdList);
                Map<String, Boolean> returnMap = result.getBody();

                for(String tempId:tempIdList){
                    TempServerList tempServerList = resultMap.get(tempId);
                    if(returnMap.get(tempId)){
                        tempServerList.getOkServerList().add(server);
                    }else{
                        isAllOk=false;
                        tempServerList.getNotOkServerList().add(server);
                    }
                }
        }
        List<TempServerList> list = new ArrayList<>();
        for(Map.Entry<String,TempServerList> en:resultMap.entrySet()){
            list.add(en.getValue());
        }

        tempIsOkResult.setAllTempOk(isAllOk);
        tempIsOkResult.setTempServerList(list);
        return tempIsOkResult;

    }*/


}
