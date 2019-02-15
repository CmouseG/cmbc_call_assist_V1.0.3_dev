package com.guiji.billing.controller;

import com.guiji.billing.dto.QueryTotalChargingItemDto;
import com.guiji.billing.service.BillingCompanyTotalService;
import com.guiji.billing.sys.ResultPage;
import com.guiji.billing.vo.TotalChargingItemVo;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 企业查询统计(企业侧使用)
 */
@RestController
@RequestMapping(value = "/billing/companyTotal")
public class BillingCompanyTotalController {

    @Autowired
    private BillingCompanyTotalService billingCompanyTotalService;

    //企业查询话费分析(企业侧使用)
    @ApiOperation(value="话费分析", notes="话费分析")
    @RequestMapping(value = "/totalCompanyChargingItem", method = {RequestMethod.POST, RequestMethod.GET})
    public ResultPage<TotalChargingItemVo> totalCompanyChargingItem(@RequestBody QueryTotalChargingItemDto queryTotalChargingItemDto,
                                                                    @RequestHeader String userId){
        if(null == queryTotalChargingItemDto){
            queryTotalChargingItemDto = new QueryTotalChargingItemDto();
        }
        queryTotalChargingItemDto.setUserId(userId);
        ResultPage<TotalChargingItemVo> page = new ResultPage<TotalChargingItemVo>(queryTotalChargingItemDto);
        List<TotalChargingItemVo> list = billingCompanyTotalService.totalCompanyChargingItem(queryTotalChargingItemDto, page);
        page.setList(list);
        page.setTotalItemAndPageNumber(billingCompanyTotalService.totalCompanyChargingCount(queryTotalChargingItemDto));
        return page;
    }

    //话费分析详情(企业侧使用)
    @ApiOperation(value="话费分析详情", notes="话费分析详情")
    @RequestMapping(value = "/totalChargingItemList", method = {RequestMethod.POST, RequestMethod.GET})
    public ResultPage<TotalChargingItemVo> totalChargingItemList(@RequestBody QueryTotalChargingItemDto queryTotalChargingItemDto){
        ResultPage<TotalChargingItemVo> page = new ResultPage<TotalChargingItemVo>(queryTotalChargingItemDto);
        List<TotalChargingItemVo> list = billingCompanyTotalService.totalChargingItemList(queryTotalChargingItemDto, page);
        page.setList(list);
        page.setTotalItemAndPageNumber(billingCompanyTotalService.totalChargingItemCount(queryTotalChargingItemDto));
        return page;
    }
}
