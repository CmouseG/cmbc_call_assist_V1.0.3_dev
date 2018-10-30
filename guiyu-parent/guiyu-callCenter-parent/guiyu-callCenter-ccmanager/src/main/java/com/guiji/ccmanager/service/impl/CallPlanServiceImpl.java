package com.guiji.ccmanager.service.impl;

import com.guiji.ccmanager.service.CallPlanService;
import org.springframework.stereotype.Service;

/**
 * @Auther: 黎阳
 * @Date: 2018/10/29 0029 17:08
 * @Description:
 */
@Service
public class CallPlanServiceImpl implements CallPlanService {

    @Override
    public void startcallplan() {

        //根据customerId、tempId、lineId到callplan表中查询是否已存在计划中、拨打中的任务，有则退出后续处理
        //收到请求后，调用fsmanager下载模板录音
        //根据线路id获取并发数
        //下载完成后，调用调度中心的获取客户呼叫计划(请求数=并发数)，获取初始呼叫计划
        //发起呼叫，在每通呼叫挂断后请求新的计划（请求数=1）

    }

}
