package com.guiji.ccmanager.controller;

import com.guiji.callcenter.dao.entity.LineInfo;
import com.guiji.ccmanager.api.CallManagerOutApi;
import com.guiji.ccmanager.entity.LineConcurrent;
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
public class CallManagerOutApiController implements CallManagerOutApi {

    @Autowired
    private LineInfoService lineInfoService;

    @Override
    @GetMapping(value="out/lineinfos")
    public Result.ReturnData<List<LineConcurrent>> outLineinfos(String customerId){

        List<LineInfo> lineInfos = lineInfoService.outLineinfos(customerId);
        List<LineConcurrent> resList = new ArrayList<LineConcurrent>();
        for(LineInfo lineInfo:lineInfos){
            LineConcurrent target = new LineConcurrent();
            target.setLineId(String.valueOf(lineInfo.getLineId()));
            target.setConcurrent(lineInfo.getMaxConcurrentCalls());
            resList.add(target);
        }
        if(resList!=null && resList.size()>0){
            return Result.ok(resList);
        }
//test
        LineConcurrent lineConcurrent1 = new LineConcurrent();
        lineConcurrent1.setConcurrent(30);
        lineConcurrent1.setLineId("111");
        LineConcurrent lineConcurrent2 = new LineConcurrent();
        lineConcurrent2.setConcurrent(32);
        lineConcurrent2.setLineId("222");
        resList.add(lineConcurrent1);
        resList.add(lineConcurrent2);

        return Result.ok(resList);
    }

    @Override
    @GetMapping(value="out/startcallplan")
    public Result.ReturnData<Boolean> startcallplan(String customerId, String tempId, String lineId) {

        if(StringUtils.isBlank(customerId)){
            return Result.ok(false);
        }
        if(StringUtils.isBlank(tempId)){
            return Result.ok(false);
        }
        if(StringUtils.isBlank(tempId)){
            return Result.ok(false);
        }

        //根据customerId、tempId、lineId到callplan表中查询是否已存在计划中、拨打中的任务，有则退出后续处理
        //收到请求后，调用fsmanager下载模板录音
        //根据线路id获取并发数
        //下载完成后，调用调度中心的获取客户呼叫计划(请求数=并发数)，获取初始呼叫计划
        //发起呼叫，在每通呼叫挂断后请求新的计划（请求数=1）

        return Result.ok(true);
    }
}
