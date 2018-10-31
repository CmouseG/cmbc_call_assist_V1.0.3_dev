package com.guiji.ccmanager.controller;

import com.guiji.callcenter.dao.entity.LineInfo;
import com.guiji.ccmanager.api.ICallManagerOutApi;
import com.guiji.ccmanager.constant.Constant;
import com.guiji.ccmanager.entity.LineConcurrent;
import com.guiji.ccmanager.service.CallManagerOutService;
import com.guiji.ccmanager.service.LineInfoService;
import com.guiji.common.result.Result;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @Auther: 黎阳
 * @Date: 2018/10/30 0030 09:37
 * @Description:
 */
@RestController
public class CallManagerOutApiController implements ICallManagerOutApi {

    @Autowired
    private LineInfoService lineInfoService;

    @Autowired
    private CallManagerOutService callManagerOutService;

    @Override
    @GetMapping(value="out/lineinfos")
    public Result.ReturnData<List<LineConcurrent>> outLineinfos(String customerId){

        if(StringUtils.isBlank(customerId)){
            return Result.error(Constant.ERROR_PARAM);
        }
        List<LineInfo> lineInfos = lineInfoService.outLineinfos(customerId);
        List<LineConcurrent> resList = new ArrayList<LineConcurrent>();
        for(LineInfo lineInfo:lineInfos){
            LineConcurrent target = new LineConcurrent();
            target.setLineId(String.valueOf(lineInfo.getLineId()));
            target.setConcurrent(lineInfo.getMaxConcurrentCalls());
            resList.add(target);
        }
        return Result.ok(resList);
    }

    @Override
    @GetMapping(value="out/startcallplan")
    public Result.ReturnData<Boolean> startcallplan(String customerId, String tempId, String lineId) {

        if(StringUtils.isBlank(customerId) || StringUtils.isBlank(tempId) || StringUtils.isBlank(lineId) ){
            return Result.error(Constant.ERROR_PARAM);
        }

        callManagerOutService.startcallplan(customerId,tempId,lineId);

        return Result.ok(true);
    }
}
