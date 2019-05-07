package com.guiji.billing.controller;

import com.guiji.billing.dto.*;
import com.guiji.billing.entity.*;
import com.guiji.billing.service.BillingUserAcctService;
import com.guiji.billing.sys.ResultPage;
import com.guiji.billing.vo.UserRechargeTotalVo;
import com.guiji.component.jurisdiction.Jurisdiction;
import com.guiji.utils.JsonUtils;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 管理员 企业账户
 */
@RestController
@RequestMapping(value = "/billing/userAcct")
public class BillingUserAcctController {

    private Logger logger = LoggerFactory.getLogger(BillingUserAcctController.class);

    @Autowired
    private BillingUserAcctService billingUserAcctService;

    //用户账户查询列表
    @ApiOperation(value="用户账户查询列表", notes="用户账户查询列表")
    @RequestMapping(value = "/queryUserAcctList", method = {RequestMethod.POST, RequestMethod.GET})
    public ResultPage<BillingUserAcctBean> queryUserAcctList(@RequestBody QueryUserAcctDto queryUserAcctDto){
        ResultPage<BillingUserAcctBean> page = new ResultPage<BillingUserAcctBean>(queryUserAcctDto);
        List<BillingUserAcctBean> list = billingUserAcctService.queryUserAcctList(queryUserAcctDto, page);
        page.setList(list);
        page.setTotalItemAndPageNumber(billingUserAcctService.queryUserAcctCount(queryUserAcctDto));
        return page;
    }

    //根据账户ID查询企业账户
    @ApiOperation(value="企业账户查询", notes="企业账户查询")
    @RequestMapping(value = "/queryUserAcct", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public BillingUserAcctBean queryUserAcct(@RequestBody QueryUserAcctDto queryUserAcctDto){
        return billingUserAcctService.queryUserAcctById(null != queryUserAcctDto?queryUserAcctDto.getAccountId():null);
    }

    //根据企业属下员工查询企业账户
    @ApiOperation(value="根据企业员工ID查询企业账户", notes="根据企业员工ID查询企业账户")
    @RequestMapping(value = "/queryAcctByUserId", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public BillingUserAcctBean queryAcctByUserId(@RequestBody QueryUserAcctDto queryUserAcctDto) {
        return billingUserAcctService.queryUserAcctByUserId(null != queryUserAcctDto?queryUserAcctDto.getUserId():null);
    }

    @ApiOperation(value="用户账户新增", notes="用户账户新增")
    @RequestMapping(value = "/addUserAcct", method = {RequestMethod.POST})
    public BillingUserAcctBean addUserAcct(@RequestBody UserAcctAddDto acctAddDto){
        return billingUserAcctService.addUserAcct(acctAddDto);
    }

    @ApiOperation(value="修改账户企业名称", notes="修改账户企业名称")
    @RequestMapping(value = "/updAcctNameByOrg", method = {RequestMethod.POST})
    public boolean updAcctNameByOrg(@RequestBody UserAcctAddDto acctAddDto){
        return billingUserAcctService.updAcctNameByOrg(acctAddDto);
    }

    /*********充值    begin***********************/
    //管理员充值(系统侧使用)
    @Jurisdiction("financeCenter_rechargeManage_recharge")
    @ApiOperation(value="企业账户充值", notes="企业账户充值")
    @RequestMapping(value = "/recharge", method = {RequestMethod.POST})
    public boolean recharge(@RequestBody RechargeDto rechargeDto,
                            @RequestHeader String userId){
        if(null != rechargeDto){
            rechargeDto.setUserId(userId);
        }
        boolean bool = billingUserAcctService.recharge(rechargeDto);
        return bool;
    }

    //查询充值记录列表(企业侧使用)
    @ApiOperation(value="查询用户账户充值记录列表", notes="查询用户账户充值记录列表")
    @RequestMapping(value = "/queryUserRechargeTotal", method = {RequestMethod.POST})
    public ResultPage<UserRechargeTotalVo> queryUserRechargeTotal(@RequestBody QueryRechargeDto queryRechargeDto,
                                                                  @RequestHeader String userId, @RequestHeader String orgCode,
                                                                  @RequestHeader Integer authLevel){
        if(null == queryRechargeDto){
            queryRechargeDto = new QueryRechargeDto();
        }
        queryRechargeDto.setOperUserId(userId);
        queryRechargeDto.setOrgCode(orgCode);
        queryRechargeDto.setAuthLevel(authLevel);
        logger.info("/billing/userAcct/queryUserRechargeTotal:{}", JsonUtils.bean2Json(queryRechargeDto));
        ResultPage<UserRechargeTotalVo> page = new ResultPage<UserRechargeTotalVo>(queryRechargeDto);
        List<UserRechargeTotalVo> list = billingUserAcctService.queryUserRechargeTotal(queryRechargeDto, page);
        page.setList(list);
        page.setTotalItemAndPageNumber(billingUserAcctService.queryUserRechargeCount(queryRechargeDto));
        return page;
    }

/*
    //管理员编辑查询充值记录
    @ApiOperation(value="查询账户充值记录", notes="查询用户账户充值记录")
    @RequestMapping(value = "/queryRechargeById", method = {RequestMethod.POST, RequestMethod.GET})
    public BillingAcctChargingRecord queryRechargeById(@RequestBody QueryRechargeDto queryRechargeDto){
        return (null != queryRechargeDto)?billingUserAcctService.queryRechargeById(queryRechargeDto.getChargingId()):null;
    }
*/

    //编辑充值附件快照(系统侧使用)
    @Jurisdiction("financeCenter_rechargeManage_edit")
    @ApiOperation(value="编辑变更充值附件快照", notes="编辑变更充值附件快照")
    @RequestMapping(value = "/editRechargeSnapshot", method = {RequestMethod.POST, RequestMethod.GET})
    public boolean editRechargeSnapshot(@RequestBody EditRechargeSnapshotDto editRechargeSnapshotDto){
        return billingUserAcctService.editRechargeSnapshot(editRechargeSnapshotDto);
    }

    /*********充值    end***********************/

    /**************我的计费项 (用户计费项现在改为MQ通知接收处理，计费项controller接口暂不使用)   begin*********/
/*
    //查询账户计费项列表
    @ApiOperation(value="查询账户计费项列表", notes="查询账户计费项列表")
    @RequestMapping(value = "/queryAcctChargingTermList", method = {RequestMethod.POST, RequestMethod.GET})
    public ResultPage<BillingAcctChargingTerm> queryAcctChargingTermList(@RequestBody QueryAcctChargingTermDto queryAcctChargingTermDto){
        ResultPage<BillingAcctChargingTerm> page = new ResultPage<BillingAcctChargingTerm>(queryAcctChargingTermDto);
        List<BillingAcctChargingTerm> list = billingUserAcctService.queryAcctChargingTermList(queryAcctChargingTermDto, page);
        page.setList(list);
        page.setTotalItemAndPageNumber(billingUserAcctService.queryAcctChargingTermCount(queryAcctChargingTermDto));
        return page;
    }

    //新增账户计费项
    @ApiOperation(value="新增账户计费项", notes="新增账户计费项")
    @RequestMapping(value = "/addAcctChargingTerm", method = {RequestMethod.POST})
    public BillingAcctChargingTerm addAcctChargingTerm(@RequestBody AcctChargingTermDto acctChargingTermDto){
        return billingUserAcctService.addAcctChargingTerm(acctChargingTermDto);
    }

    //修改账户计费项
    @ApiOperation(value="修改账户计费项", notes="修改账户计费项")
    @RequestMapping(value = "/updAcctChargingTerm", method = {RequestMethod.POST})
    public boolean updAcctChargingTerm(@RequestBody AcctChargingTermDto acctChargingTermDto){
        return billingUserAcctService.updAcctChargingTerm(acctChargingTermDto);
    }

    //删除账户计费项
    @ApiOperation(value="删除账户计费项", notes="删除账户计费项")
    @RequestMapping(value = "/delAcctChargingTerm", method = {RequestMethod.POST, RequestMethod.GET})
    public boolean delAcctChargingTerm(@RequestParam(value="userChargingId",required=true) String userChargingId){
        boolean bool = billingUserAcctService.delAcctChargingTerm(userChargingId);
        return bool;
    }
*/

    /**************我的计费项    end*********/

/*
    //查询账户计费流水(暂不使用)
    @ApiOperation(value="查询账户计费流水", notes="查询账户计费流水")
    @RequestMapping(value = "/queryAcctChargingRecordList", method = {RequestMethod.POST, RequestMethod.GET})
    public ResultPage<BillingAcctChargingRecord> queryAcctChargingRecordList(@RequestBody QueryChargingRecordDto queryChargingRecordDto){
        ResultPage<BillingAcctChargingRecord> page = new ResultPage<BillingAcctChargingRecord>(queryChargingRecordDto);
        List<BillingAcctChargingRecord> list = billingUserAcctService.queryAcctChargingRecordList(queryChargingRecordDto, page);
        page.setList(list);
        page.setTotalItemAndPageNumber(billingUserAcctService.queryAcctChargingRecordCount(queryChargingRecordDto));
        return page;
    }
*/

    /**************用户账户推送设置 现在只有阈值设置使用到   begin*********/
    //查询账户推送设置列表
    @ApiOperation(value="查询账户推送设置列表", notes="查询账户推送设置列表")
    @RequestMapping(value = "/queryUserAcctSetList", method = {RequestMethod.POST, RequestMethod.GET})
    public ResultPage<BillingUserAcctSetBean> queryUserAcctSetList(@RequestBody QueryUserAcctDto queryUserAcctDto){
        ResultPage<BillingUserAcctSetBean> page = new ResultPage<BillingUserAcctSetBean>(queryUserAcctDto);
        List<BillingUserAcctSetBean> list = billingUserAcctService.queryUserAcctSetList(queryUserAcctDto, page);
        page.setList(list);
        page.setTotalItemAndPageNumber(billingUserAcctService.queryUserAcctSetCount(queryUserAcctDto));
        return page;
    }

    @ApiOperation(value="查询账户推送设置", notes="查询账户推送设置")
    @RequestMapping(value = "/queryUserAcctSet", method = {RequestMethod.POST})
    public BillingUserAcctSetBean queryUserAcctSet(@RequestBody UserAcctSetDto userAcctSetDto){
        return (null != userAcctSetDto)?billingUserAcctService.queryUserAcctSet(userAcctSetDto.getAccountId(), userAcctSetDto.getSetKey()):null;
    }

    //新增账户推送设置
    @ApiOperation(value="新增账户推送设置", notes="新增账户推送设置")
    @RequestMapping(value = "/addUserAcctSet", method = {RequestMethod.POST})
    public BillingUserAcctSetBean addUserAcctSet(@RequestBody UserAcctSetDto userAcctSetDto){
        return billingUserAcctService.addUserAcctSet(userAcctSetDto);
    }

    //修改账户推送设置(企业账户阈值设置)
    @Jurisdiction("financeCenter_accountPandect_edit")
    @ApiOperation(value="修改账户推送设置", notes="修改账户推送设置")
    @RequestMapping(value = "/updUserAcctSet", method = {RequestMethod.POST})
    public boolean updUserAcctSet(@RequestBody UserAcctSetDto userAcctSetDto){
        return billingUserAcctService.updUserAcctSet(userAcctSetDto);
    }

    //删除账户推送设置
    @ApiOperation(value="删除账户推送设置", notes="删除账户推送设置")
    @RequestMapping(value = "/delUserAcctSet", method = {RequestMethod.POST})
    public boolean delUserAcctSet(@RequestParam(value="acctSetId",required=true) String acctSetId){
        boolean bool = billingUserAcctService.delUserAcctSet(acctSetId);
        return bool;
    }
    /**************用户账户推送设置    end*********/
}
