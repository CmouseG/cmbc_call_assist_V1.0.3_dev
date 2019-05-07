package com.guiji.billing.controller;

import com.guiji.billing.dto.QueryRechargeDto;
import com.guiji.billing.service.BillingSysRechargeService;
import com.guiji.billing.sys.ResultPage;
import com.guiji.billing.vo.SysRechargeTotalVo;
import com.guiji.billing.vo.UserRechargeTotalVo;
import com.guiji.utils.JsonUtils;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 管理员查询充值记录
 */
@RestController
@RequestMapping(value = "/billing/sysRecharge")
public class BillingSysRechargeController {

    private Logger logger = LoggerFactory.getLogger(BillingSysRechargeController.class);

    @Autowired
    private BillingSysRechargeService billingSysRechargeService;

    //查询公司账户充值记录(系统侧使用)
    @ApiOperation(value="查询公司账户充值记录", notes="查询公司账户充值记录")
    @RequestMapping(value = "/queryCompanyRechargeTotal", method = {RequestMethod.POST})
    public ResultPage<SysRechargeTotalVo> queryCompanyRechargeTotal(@RequestBody QueryRechargeDto queryRechargeDto,
                                                                    @RequestHeader String userId, @RequestHeader String orgCode,
                                                                    @RequestHeader Integer authLevel){
        if(null == queryRechargeDto){
            queryRechargeDto = new QueryRechargeDto();
        }
        queryRechargeDto.setOperUserId(userId);
        queryRechargeDto.setOrgCode(orgCode);
        queryRechargeDto.setAuthLevel(authLevel);
        logger.info("/billing/sysRecharge/queryCompanyRechargeTotal:{}", JsonUtils.bean2Json(queryRechargeDto));
        ResultPage<SysRechargeTotalVo> page = new ResultPage<SysRechargeTotalVo>(queryRechargeDto);
        List<SysRechargeTotalVo> list = billingSysRechargeService.queryCompanyRechargeTotal(queryRechargeDto, page);
        page.setList(list);
        page.setTotalItemAndPageNumber(billingSysRechargeService.queryCompanyRechargeCount(queryRechargeDto));
        return page;
    }
}
