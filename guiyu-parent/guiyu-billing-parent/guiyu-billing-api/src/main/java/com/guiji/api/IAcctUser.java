package com.guiji.api;

import com.guiji.component.result.Result;
import com.guiji.vo.ArrearageNotifyVo;
import com.guiji.vo.BillingUserAcctVo;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value="guiyu-billing-web")
public interface IAcctUser {

    //根据企业用户ID查询企业账户
    @RequestMapping(value="/billing/api/acctUser/queryAcctByUserId", method={RequestMethod.POST})
    Result.ReturnData<BillingUserAcctVo> queryAcctByUserId(@RequestParam("userId") String userId);

    //查询欠费企业用户列表
    @RequestMapping(value="/billing/api/acctUser/queryArrearageUserList", method={RequestMethod.POST})
    Result.ReturnData<ArrearageNotifyVo> queryArrearageUserList();
}
