package com.guiji.billing.controller;

import com.guiji.billing.dto.QueryTotalChargingItemDto;
import com.guiji.billing.service.BillingCompanyTotalService;
import com.guiji.billing.sys.ResultPage;
import com.guiji.billing.utils.HttpDownload;
import com.guiji.billing.vo.TotalChargingItemDetailVo;
import com.guiji.billing.vo.TotalChargingItemVo;
import com.guiji.common.exception.GuiyuException;
import com.guiji.component.result.Result;
import com.guiji.utils.JsonUtils;
import io.swagger.annotations.ApiOperation;
import jxl.Workbook;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 企业查询统计(企业侧使用)
 */
@RestController
@RequestMapping(value = "/billing/companyTotal")
public class BillingCompanyTotalController {

    private Logger logger = LoggerFactory.getLogger(BillingCompanyTotalController.class);

    @Autowired
    private BillingCompanyTotalService billingCompanyTotalService;

    //企业查询话费分析(企业侧使用)
    @ApiOperation(value="话费分析", notes="话费分析")
    @RequestMapping(value = "/totalCompanyChargingItem", method = {RequestMethod.POST, RequestMethod.GET})
    public ResultPage<TotalChargingItemVo> totalCompanyChargingItem(@RequestBody QueryTotalChargingItemDto queryTotalChargingItemDto,
                                                                    @RequestHeader String userId, @RequestHeader String orgCode,
                                                                    @RequestHeader Integer authLevel){
        if(null == queryTotalChargingItemDto){
            queryTotalChargingItemDto = new QueryTotalChargingItemDto();
        }
        queryTotalChargingItemDto.setOperUserId(userId);
        queryTotalChargingItemDto.setOrgCode(orgCode);
        queryTotalChargingItemDto.setAuthLevel(authLevel);
        logger.info("/billing/companyTotal/totalCompanyChargingItem:{}", JsonUtils.bean2Json(queryTotalChargingItemDto));
        ResultPage<TotalChargingItemVo> page = new ResultPage<TotalChargingItemVo>(queryTotalChargingItemDto);
        List<TotalChargingItemVo> list = billingCompanyTotalService.totalCompanyChargingItem(queryTotalChargingItemDto, page);
        page.setList(list);
        page.setTotalItemAndPageNumber(billingCompanyTotalService.totalCompanyChargingCount(queryTotalChargingItemDto));
        return page;
    }

    //话费分析详情(企业侧使用)
    @ApiOperation(value="话费分析详情", notes="话费分析详情")
    @RequestMapping(value = "/totalChargingItemList", method = {RequestMethod.POST, RequestMethod.GET})
    public ResultPage<TotalChargingItemDetailVo> totalChargingItemList(@RequestBody QueryTotalChargingItemDto queryTotalChargingItemDto){
        ResultPage<TotalChargingItemDetailVo> page = new ResultPage<TotalChargingItemDetailVo>(queryTotalChargingItemDto);
        List<TotalChargingItemDetailVo> list = billingCompanyTotalService.totalChargingItemList(queryTotalChargingItemDto, page);
        page.setList(list);
        page.setTotalItemAndPageNumber(billingCompanyTotalService.totalChargingItemCount(queryTotalChargingItemDto));
        return page;
    }

    //导出话费分析
    @ApiOperation(value="导出话费分析", notes="导出话费分析")
    @RequestMapping(value = "/expertTotalCharging", method = {RequestMethod.POST})
    public Result.ReturnData<Object> expertTotalCharging(@RequestBody QueryTotalChargingItemDto queryTotalChargingItemDto,
                                                      @RequestHeader String userId, @RequestHeader String orgCode,
                                                      @RequestHeader Integer authLevel,
                                                      HttpServletResponse resp)
            throws Exception {

        if(null == queryTotalChargingItemDto){
            queryTotalChargingItemDto = new QueryTotalChargingItemDto();
        }
        queryTotalChargingItemDto.setOperUserId(userId);
        queryTotalChargingItemDto.setOrgCode(orgCode);
        queryTotalChargingItemDto.setAuthLevel(authLevel);
        logger.info("/billing/companyTotal/expertTotalCharging:{}", JsonUtils.bean2Json(queryTotalChargingItemDto));
        List<TotalChargingItemVo> list = billingCompanyTotalService.totalCompanyChargingItem(queryTotalChargingItemDto, null);

        String fileName = "导出话费分析.xls";
        HttpDownload.setHeader(resp, fileName);

        OutputStream out = null;
        try {
            out = resp.getOutputStream();
            this.generateDownloadExcel(list, out);
        } catch (IOException e) {
            logger.error("downloadDialogue IOException :" + e);
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    logger.error("out.close error:" + e);
                }
            }
        }
        return null;
    }

    private void generateDownloadExcel(List<TotalChargingItemVo> list, OutputStream out)
            throws Exception {

        WritableWorkbook wb = Workbook.createWorkbook(out);
        WritableSheet sheet = wb.createSheet("sheet1", 0);
        WritableCellFormat format = new WritableCellFormat();
        format.setBorder(Border.ALL, BorderLineStyle.THIN);
        format.setWrap(true);

        sheet.setColumnView(0, 12);
        sheet.setColumnView(1, 12);

        sheet.addCell(new Label(0, 0, "日期"));
        sheet.addCell(new Label(1, 0, "线路名称"));
        sheet.addCell(new Label(2, 0, "接通数"));
        sheet.addCell(new Label(3, 0, "计费时长"));
        sheet.addCell(new Label(4, 0, "话费"));

        Map<Integer, String> batchLineMap = new HashMap<>();
        for (int i = 0; i < list.size(); i++) {
            TotalChargingItemVo chargingItem = list.get(i);
            int k = 0;

            sheet.addCell(new Label(k, i + 1, chargingItem.getTotalDate()));
            k++;
            sheet.addCell(new Label(k, i + 1, chargingItem.getChargingItemName()));
            k++;
            sheet.addCell(new Label(k, i + 1, chargingItem.getCallCount()+""));
            k++;
            sheet.addCell(new Label(k, i + 1, chargingItem.getDuration()+""));
            k++;
            sheet.addCell(new Label(k, i + 1, (null != chargingItem.getTotalChargingAmout()?
                    chargingItem.getTotalChargingAmout().divide(new BigDecimal(100)):BigDecimal.ZERO)+"元"
            ));
        }

        wb.write();
        wb.close();

    }
}
