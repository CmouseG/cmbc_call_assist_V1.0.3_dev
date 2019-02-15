package com.guiji.billing.controller;

import com.guiji.billing.dto.ChargingTermDto;
import com.guiji.billing.dto.QueryChargingTermDto;
import com.guiji.billing.entity.BillingChargingTermBean;
import com.guiji.billing.enums.AcctChargingStatusEnum;
import com.guiji.billing.service.BillingChargingTermService;
import com.guiji.billing.sys.ResultPage;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 计费项(计费项现由外部系统通知同步,此controller暂不使用)
 */
@RestController
@RequestMapping(value = "/billing/chargingTeram")
public class BillingChargingTermController {

    private Logger logger = LoggerFactory.getLogger(BillingChargingTermController.class);

    @Autowired
    private BillingChargingTermService billingChargingTermService;

    /**
     * 查询计费项
     * @param queryChargingTermDto
     * @return
     */
    @ApiOperation(value="查询计费项", notes="查询计费项")
    @ApiImplicitParams({
            /*@ApiImplicitParam(name="queryChargingTermDto", value="json格式字符串"),*/
            /*@ApiImplicitParam(name="type", value="类型"),
            @ApiImplicitParam(name="ChargingType", value="计费类型"),
            @ApiImplicitParam(name="status", value="状态"),*/
    })
 //   @GetMapping(value="/queryChargingTermList")
    @RequestMapping(value = "/queryChargingTermList", method = {RequestMethod.POST, RequestMethod.GET})
    public ResultPage<BillingChargingTermBean> queryChargingTermList(@RequestBody QueryChargingTermDto queryChargingTermDto){
        //分页对象
        ResultPage<BillingChargingTermBean> page = new ResultPage<BillingChargingTermBean>(queryChargingTermDto);
        //查询计费项列表
        List<BillingChargingTermBean> list = billingChargingTermService.queryChargingTermList(queryChargingTermDto, page);
        page.setList(list);
        //查询计费项数量
        page.setTotalItemAndPageNumber(billingChargingTermService.queryChargingTermCount(queryChargingTermDto));
        return page;
    }

    //新增计费项
    @ApiOperation(value="新增计费项", notes="新增计费项")
 //   @PostMapping(value="/addChargingTerm")
    @RequestMapping(value = "/addChargingTerm", method = {RequestMethod.POST})
    public BillingChargingTermBean addChargingTerm(@RequestBody ChargingTermDto chargingTermDto){
        BillingChargingTermBean chargingTerm = billingChargingTermService.addChargingTerm(chargingTermDto);
        return chargingTerm;
    }

    //修改计费项
    @ApiOperation(value="修改计费项", notes="修改计费项")
 //   @PostMapping(value="/updChargingTerm")
    @RequestMapping(value = "/updChargingTerm", method = {RequestMethod.POST})
    public boolean updChargingTerm(@RequestBody ChargingTermDto chargingTermDto){
        boolean bool = billingChargingTermService.updChargingTerm(chargingTermDto);
        return bool;
    }

    //删除计费项
    @ApiOperation(value="删除计费项", notes="删除计费项")
//    @PostMapping(value="/delChargingTerm")
    @RequestMapping(value = "/delChargingTerm", method = {RequestMethod.POST})
    public boolean delChargingTerm(@RequestParam(value="chargingItemId",required=true) String chargingItemId){
        boolean bool = billingChargingTermService.delChargingTerm(chargingItemId);
        return bool;
    }

    //变更计费项状态(启用)
    @ApiOperation(value="变更计费项状态(启用)", notes="变更计费项状态(启用)")
    @RequestMapping(value = "/startUpChargingTerm", method = {RequestMethod.POST})
    public boolean startUpChargingTerm(@RequestParam(value="chargingItemId",required=true) String chargingItemId){
        boolean bool = billingChargingTermService.updChargingTermStatus(chargingItemId,
                                                        AcctChargingStatusEnum.START_UP.getStatus());
        return bool;
    }

    //变更计费项状态(停止)
    @ApiOperation(value="变更计费项状态(停止)", notes="变更计费项状态(停止)")
    @RequestMapping(value = "/offChargingTerm", method = {RequestMethod.POST})
    public boolean stopOffChargingTerm(@RequestParam(value="chargingItemId",required=true) String chargingItemId){
        boolean bool = billingChargingTermService.updChargingTermStatus(chargingItemId,
                AcctChargingStatusEnum.OFF.getStatus());
        return bool;
    }

}
