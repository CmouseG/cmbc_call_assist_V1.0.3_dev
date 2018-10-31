package com.guiji.ccmanager.service.impl;

import com.guiji.callcenter.dao.CallOutPlanMapper;
import com.guiji.callcenter.dao.LineInfoMapper;
import com.guiji.callcenter.dao.entity.CallOutPlan;
import com.guiji.callcenter.dao.entity.CallOutPlanExample;
import com.guiji.callcenter.dao.entity.LineInfo;
import com.guiji.calloutserver.api.ICallPlanApi;
import com.guiji.ccmanager.constant.Constant;
import com.guiji.ccmanager.feign.TempApiFeign;
import com.guiji.ccmanager.service.CallManagerOutService;
import com.guiji.common.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Auther: 黎阳
 * @Date: 2018/10/30 0030 13:46
 * @Description:
 */
@Service
public class CallManagerOutServiceImpl implements CallManagerOutService {

    @Autowired
    private LineInfoMapper lineInfoMapper;
    @Autowired
    private CallOutPlanMapper callOutPlanMapper;
    @Autowired
    private TempApiFeign tempApiFeign;
    @Autowired
    private ICallPlanApi callPlanApi;


    @Override
    public Result.ReturnData<Object> startcallplan(String customerId, String tempId, String lineId) {

        //根据线路id到lineinfo表中查询线路是否存在，不存在则返回线路不存在错误，并报警
        LineInfo lineInfoDB = lineInfoMapper.selectByPrimaryKey(Integer.parseInt(lineId));
        if(lineInfoDB==null){
            return Result.error(Constant.ERROR_LINE_NOTEXIST);
        }
        //到calloutplan中查询该线路是否存在待呼叫或进行中的计划，存在则返回线路繁忙错误，并报警
        CallOutPlanExample example = new CallOutPlanExample();
        CallOutPlanExample.Criteria criteria = example.createCriteria();
        criteria.andLineIdEqualTo(Integer.valueOf(lineId));
        criteria.andCallStateBetween(Constant.CALLSTATE_INIT,Constant.CALLSTATE_AGENT_ANSWER);
        List<CallOutPlan> existList = callOutPlanMapper.selectByExample(example);
        if(existList!=null && existList.size()>0){
            return Result.error(Constant.ERROR_LINE_RUNNING);
        }

        //调用fsmanager的模板是否存在接口，模板不存在处理如下
        Result.ReturnData<Boolean> resultTemp = tempApiFeign.istempexist(tempId);
        if(resultTemp.getCode().equals(Constant.SUCCESS_COMMON) && resultTemp.getBody() == false){
            //返回模板不存在错误，并报警
//            return Result.error(Constant.ERROR_TEMP_NOTEXIST);
            //调用fsmanager的下载模板接口
            Result.ReturnData result = tempApiFeign.downloadtempwav(tempId);
        }
        //调用所有calloutserver的启动客户呼叫计划接口
        Result.ReturnData result = callPlanApi.startCallPlan( customerId,tempId, Integer.valueOf(lineId));
        return result;
    }
}
