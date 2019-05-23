package com.guiji.ccmanager.controller;

import com.guiji.ccmanager.constant.Constant;
import com.guiji.ccmanager.manager.CacheManager;
import com.guiji.ccmanager.service.StatisticService;
import com.guiji.ccmanager.utils.HttpDownload;
import com.guiji.component.jurisdiction.Jurisdiction;
import com.guiji.component.result.Result;
import io.swagger.annotations.ApiOperation;
import jxl.Workbook;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.write.*;
import jxl.write.biff.RowsExceededException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.lang.Boolean;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Auther: 黎阳
 * @Date: 2018/12/17 0017 15:57
 * @Description:
 */
@Slf4j
@RestController
public class DownloadStatisticController {

    @Autowired
    StatisticService statisticService;
    @Autowired
    CacheManager cacheManager;

    @ApiOperation(value = "量化分析，拨打结果统计，导出excel")
    @Jurisdiction("countAnalysis_callResultCount_exportData")
    @GetMapping(value = "downloadIntentCount")
    public Result.ReturnData<Object> downloadIntentCount(String startDate, String endDate, String tempId,String queryUserId, @RequestHeader Integer authLevel,
                                                         @RequestHeader String orgCode,@RequestHeader Long userId,
                                                         HttpServletResponse resp) throws ParseException, UnsupportedEncodingException {

        if (StringUtils.isBlank(endDate)) {
            endDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        }
        if (StringUtils.isBlank(startDate)) {
            startDate = endDate;
        }

        String regEx = "^\\d{4}-\\d{2}-\\d{2}$";
        if (!startDate.matches(regEx) || !endDate.matches(regEx)) {
            return Result.error(Constant.ERROR_DATEFORMAT);
        }
        String fileName = startDate+"_"+endDate+"拨打结果统计.xls";
        HttpDownload.setHeader(resp, fileName);

        Integer queryUser = null;
        if(StringUtils.isNotEmpty(queryUserId)){
            queryUser = Integer.valueOf(queryUserId);
        }

        List<Map<String, Object>> list = statisticService.getIntentCount(authLevel,userId,orgCode, startDate, endDate, StringUtils.isNotBlank(tempId)? tempId: null,queryUser);


        OutputStream out = null;
        try {
            out = resp.getOutputStream();
            generateIntentCountExcel(list, out, startDate, endDate, tempId, queryUser);

        } catch (IOException e) {
            log.error("downloadDialogue IOException :" + e);
        } catch (RowsExceededException e) {
            log.error("downloadDialogue RowsExceededException :" + e);
        } catch (WriteException e) {
            log.error("downloadDialogue WriteException :" + e);
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    log.error("out.close error:" + e);
                }
            }
        }
        return null;
    }

    public void generateIntentCountExcel(List<Map<String, Object>> list, OutputStream out, String startDate, String endDate,
                                         String tempId, Integer queryUser) throws IOException, WriteException, ParseException {
        WritableWorkbook wb = Workbook.createWorkbook(out);

        WritableSheet sheet = wb.createSheet("sheet1", 0);
        WritableCellFormat format = new WritableCellFormat();
        format.setBorder(Border.ALL, BorderLineStyle.THIN);
        format.setWrap(true);

        sheet.setColumnView(0, 12);
        sheet.setColumnView(1, 12);

        sheet.addCell(new Label(0, 0, "话术模板："));
        if (StringUtils.isBlank(tempId)) {
            sheet.addCell(new Label(1, 0, "全部话术模版"));
        } else {
            String tempName = cacheManager.getTempName(tempId);
            sheet.addCell(new Label(1, 0, tempName == null ? "" : tempName));
        }

        if (queryUser!=null) {
            String username = cacheManager.getUserName(queryUser);
            sheet.addCell(new Label(2, 0, "用户："));
            sheet.addCell(new Label(3, 0, username == null ? "" : username));
            sheet.addCell(new Label(4, 0, "起止时间："));
            sheet.addCell(new Label(5, 0, startDate + "-" + endDate));
        }else{
            sheet.addCell(new Label(2, 0, "起止时间："));
            sheet.addCell(new Label(3, 0, startDate + "-" + endDate));
        }

        List<String> metaDatas = new ArrayList<>();
        Map<String, Object> map0 = list.get(0);
        for (Map.Entry<String, Object> entry : map0.entrySet()) {
            String intentName = entry.getKey();
            if (!intentName.equals("notConnectCount") && !intentName.equals("callDate") && !intentName.equals("connectCount") && !intentName.equals("allCallsCount")) {
                metaDatas.add(intentName);
            }
        }

        Map<String, Integer> sumMap = new HashMap<>();
        int sumnAllCallsCount = 0;
        int sumConnectCount = 0;
        int sumACount = 0;
        int jlength = list.size();

        for (int j = 1; j <= jlength; j++) { //j是行号，i是列号，从0开始
            Map<String, Object> map = list.get(j - 1);
            //日期
            String callDate = (String) map.get("callDate");
            sheet.addCell(new Label(0, j + 1, callDate));
            if (j == 1) {
                sheet.addCell(new Label(0, 1, "日期"));
            }
            //星期
            sheet.addCell(new Label(1, j + 1, getDayOfWeekByDate(callDate)));
            if (j == 1) {
                sheet.addCell(new Label(1, 1, "星期"));
            }

            int i = 2;
            for (String metaIntent : metaDatas) {
                if (j == 1) {
                    sheet.addCell(new Label(i, j, metaIntent));
                }
                sheet.addCell(new Label(i, j + 1, map.get(metaIntent)==null ? "0" : map.get(metaIntent).toString()));
                if (sumMap.get(metaIntent) == null) {
                    sumMap.put(metaIntent, (int) map.get(metaIntent));
                } else {
                    int old = sumMap.get(metaIntent);
                    sumMap.put(metaIntent, (int) (map.get(metaIntent)== null ? 0 : map.get(metaIntent)) + old);
                }
                i++;
            }

            //总拨打量
            int allCallsCount = (int) map.get("allCallsCount");
            sheet.addCell(new Label(i, j + 1, String.valueOf(allCallsCount)));
            sumnAllCallsCount = sumnAllCallsCount + allCallsCount;
            if (j == 1) {
                sheet.addCell(new Label(i, 1, "总拨打量"));
            }
            i++;
            //接通数
            int connectCount = (int) map.get("connectCount");
            sheet.addCell(new Label(i, j + 1, String.valueOf(connectCount)));
            sumConnectCount = sumConnectCount + connectCount;
            if (j == 1) {
                sheet.addCell(new Label(i, 1, "接通数"));
            }
            i++;
            //计算接通率
            sheet.addCell(new Label(i, j + 1, getPercentValue(connectCount, allCallsCount)));
            if (j == 1) {
                sheet.addCell(new Label(i, 1, "接通率"));
            }
            i++;
            //计算A转化率
            int aCount = (int) map.get("A");
            sheet.addCell(new Label(i, j + 1, getPercentValue(aCount, allCallsCount)));
            sumACount = sumACount + aCount;
            if (j == 1) {
                sheet.addCell(new Label(i, 1, "A转化率"));
            }

        }

        sheet.addCell(new Label(0, jlength + 2, "合计"));
        int k = 2;
        for (String metaIntent : metaDatas) {
            sheet.addCell(new Label(k, jlength + 2, sumMap.get(metaIntent).toString()));
            k++;
        }
        sheet.addCell(new Label(k, jlength + 2, String.valueOf(sumnAllCallsCount)));
        sheet.addCell(new Label(k + 1, jlength + 2, String.valueOf(sumConnectCount)));
        sheet.addCell(new Label(k + 2, jlength + 2, getPercentValue(sumConnectCount, sumnAllCallsCount)));
        sheet.addCell(new Label(k + 3, jlength + 2, getPercentValue(sumACount, sumnAllCallsCount)));

        sheet.addCell(new Label(0, jlength + 3, "类别"));
        sheet.addCell(new Label(1, jlength + 3, "数量"));
        sheet.addCell(new Label(2, jlength + 3, "占比"));

        int l = 4;
        for (String metaIntent : metaDatas) {
            sheet.addCell(new Label(0, jlength + l, metaIntent + "类"));
            sheet.addCell(new Label(1, jlength + l, sumMap.get(metaIntent).toString()));
            sheet.addCell(new Label(2, jlength + l, getPercentValue(sumMap.get(metaIntent), sumnAllCallsCount)));
            l++;
        }


        wb.write();
        wb.close();
    }

    /**
     * 计算百分比
     *
     * @param top
     * @param down
     * @return
     */
    public String getPercentValue(int top, int down) {

        if (down == 0) {
            return "0%";
        }
//        DecimalFormat df1 = new DecimalFormat("##.00%");    //##.00%   百分比格式，后面不足2位的用0补齐
//        return df1.format((float) top / down);

        java.text.NumberFormat nf = java.text.NumberFormat.getPercentInstance();
        nf.setMaximumIntegerDigits(5);//小数点前保留几位
        nf.setMinimumFractionDigits(2);// 小数点后保留几位
        String str = nf.format((float) top / down);
        return str;
    }

    /**
     * 根据日期 找到对应日期的 星期
     */
    public static String getDayOfWeekByDate(String date) throws ParseException {

        SimpleDateFormat myFormatter = new SimpleDateFormat("yyyy-MM-dd");
        Date myDate = myFormatter.parse(date);
        SimpleDateFormat formatter = new SimpleDateFormat("EEEE",Locale.CHINA);
        return formatter.format(myDate);
    }

}
