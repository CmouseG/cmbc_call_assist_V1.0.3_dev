package com.guiji.billing.controller;

import com.guiji.billing.dto.QueryAcctChargingTotalDto;
import com.guiji.billing.dto.QueryAcctRecDto;
import com.guiji.billing.entity.BillingAcctChargingTotal;
import com.guiji.billing.entity.BillingAcctReconciliation;
import com.guiji.billing.service.BillingTotalAnalysisService;
import com.guiji.billing.sys.ResultPage;
import com.guiji.billing.vo.BillingTotalChargingConsumerVo;
import com.guiji.utils.JsonUtils;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 管理员统计记录
 */
@RestController
@RequestMapping(value = "/billing/totalAnalysis")
public class BillingTotalAnalysisController {

    private Logger logger = LoggerFactory.getLogger(BillingTotalAnalysisController.class);

    @Autowired
    private BillingTotalAnalysisService billingTotalAnalysisService;

    //按日统计查询(系统侧使用)
    @ApiOperation(value="汇总报表按日", notes="汇总报表按日")
    @RequestMapping(value = "/totalChargingByDate", method = {RequestMethod.POST, RequestMethod.GET})
    public ResultPage<BillingTotalChargingConsumerVo> totalAcctChargingByDay(@RequestBody QueryAcctChargingTotalDto queryAcctChargingTotalDto,
                                                                             @RequestHeader String userId, @RequestHeader String orgCode,
                                                                             @RequestHeader Integer authLevel){
        if(null == queryAcctChargingTotalDto){
            queryAcctChargingTotalDto = new QueryAcctChargingTotalDto();
        }
        queryAcctChargingTotalDto.setOperUserId(userId);
        queryAcctChargingTotalDto.setOrgCode(orgCode);
        queryAcctChargingTotalDto.setAuthLevel(authLevel);
        logger.info("/billing/totalAnalysis/totalChargingByDate:{}", JsonUtils.bean2Json(queryAcctChargingTotalDto));
        ResultPage<BillingTotalChargingConsumerVo> page = new ResultPage<BillingTotalChargingConsumerVo>(queryAcctChargingTotalDto);
        List<BillingTotalChargingConsumerVo> list = billingTotalAnalysisService.totalChargingByDate(queryAcctChargingTotalDto, page);
        page.setList(list);
        page.setTotalItemAndPageNumber(billingTotalAnalysisService.totalChargingCountByDate(queryAcctChargingTotalDto));
        return page;
    }

    //按月统计查询(系统侧使用)
    @ApiOperation(value="汇总报表按月", notes="汇总报表按月")
    @RequestMapping(value = "/totalChargingByMonth", method = {RequestMethod.POST, RequestMethod.GET})
    public ResultPage<BillingTotalChargingConsumerVo> totalChargingByMonth(@RequestBody QueryAcctChargingTotalDto queryAcctChargingTotalDto,
                                                                           @RequestHeader String userId, @RequestHeader String orgCode,
                                                                           @RequestHeader Integer authLevel){
        if(null == queryAcctChargingTotalDto){
            queryAcctChargingTotalDto = new QueryAcctChargingTotalDto();
        }
        queryAcctChargingTotalDto.setOperUserId(userId);
        queryAcctChargingTotalDto.setOrgCode(orgCode);
        queryAcctChargingTotalDto.setAuthLevel(authLevel);
        logger.info("/billing/totalAnalysis/totalChargingByMonth:{}", JsonUtils.bean2Json(queryAcctChargingTotalDto));
        ResultPage<BillingTotalChargingConsumerVo> page = new ResultPage<BillingTotalChargingConsumerVo>(queryAcctChargingTotalDto);
        List<BillingTotalChargingConsumerVo> list = billingTotalAnalysisService.totalChargingByMonth(queryAcctChargingTotalDto, page);
        page.setList(list);
        page.setTotalItemAndPageNumber(billingTotalAnalysisService.totalChargingCountByMonth(queryAcctChargingTotalDto));
        return page;
    }

    //查询对账记录(前端暂时没用到)
    @ApiOperation(value="查询对账记录", notes="查询对账记录")
    @RequestMapping(value = "/queryAcctReconciliation", method = {RequestMethod.POST, RequestMethod.GET})
    public ResultPage<BillingAcctReconciliation> queryAcctReconciliation(@RequestBody QueryAcctRecDto queryAcctRecDto){
        ResultPage<BillingAcctReconciliation> page = new ResultPage<BillingAcctReconciliation>(queryAcctRecDto);
        List<BillingAcctReconciliation> list = billingTotalAnalysisService.queryAcctReconciliation(queryAcctRecDto, page);
        page.setList(list);
        page.setTotalItemAndPageNumber(billingTotalAnalysisService.queryAcctReconcCount(queryAcctRecDto));
        return page;
    }


    /*************************************/


}
