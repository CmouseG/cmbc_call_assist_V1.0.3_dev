package com.guiji.ccmanager.service.impl;

import com.guiji.callcenter.dao.CallOutPlanMapper;
import com.guiji.callcenter.dao.entity.CallOutPlan;
import com.guiji.callcenter.dao.entity.CallOutPlanExample;
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
    private CallOutPlanMapper callOutPlanMapper;
    @Autowired
    private TempApiFeign tempApiFeign;

    @Override
    public void startcallplan(String customerId, String tempId, String lineId) {

        //根据customerId、tempId、lineId到callplan表中查询是否已存在计划中、拨打中的任务，有则退出后续处理
        CallOutPlanExample example = new CallOutPlanExample();
        CallOutPlanExample.Criteria criteria = example.createCriteria();
        criteria.andCustomerIdEqualTo(customerId);
        criteria.andTempIdEqualTo(tempId);
        criteria.andLineIdEqualTo(Integer.valueOf(lineId));
        List<CallOutPlan> existList = callOutPlanMapper.selectByExample(example);
        if(existList==null || existList.size()==0){
            return;
        }

        //收到请求后，调用fsmanager下载模板录音
        Result.ReturnData result = tempApiFeign.downloadtempwav(tempId);

        //根据线路id获取并发数
        //下载完成后，调用调度中心的获取客户呼叫计划(请求数=并发数)，获取初始呼叫计划
        //发起呼叫，在每通呼叫挂断后请求新的计划（请求数=1）
    }
}
