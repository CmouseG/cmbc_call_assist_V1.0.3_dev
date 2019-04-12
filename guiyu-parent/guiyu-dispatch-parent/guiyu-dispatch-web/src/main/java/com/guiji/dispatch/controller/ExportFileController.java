package com.guiji.dispatch.controller;

import com.guiji.component.jurisdiction.Jurisdiction;
import com.guiji.dispatch.dto.DelExportFileRecordDto;
import com.guiji.dispatch.dto.QueryExportFileRecordDto;
import com.guiji.dispatch.entity.ExportFileRecord;
import com.guiji.dispatch.service.GetAuthUtil;
import com.guiji.dispatch.service.IExportFileService;
import com.guiji.dispatch.sys.ResultPage;
import com.guiji.dispatch.util.HttpDownload;
import com.guiji.utils.JsonUtils;
import io.swagger.annotations.ApiOperation;
import jxl.write.WriteException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

@RestController
@RequestMapping("/dispatch/exportFileController")
public class ExportFileController {

    private Logger logger = LoggerFactory.getLogger(ExportFileController.class);

    @Autowired
    private IExportFileService exportFileService;

    @Autowired
    private GetAuthUtil getAuthUtil;

    @ApiOperation(value="查询文件导出记录", notes="查询文件导出记录")
    @RequestMapping(value = "/queryExportFileRecordByPage", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public ResultPage<ExportFileRecord> queryExportFileRecordByPage(@RequestHeader String userId, @RequestHeader String orgCode, @RequestHeader Integer authLevel,
                                                                    @RequestBody QueryExportFileRecordDto queryDto){
        if(null == queryDto){
            queryDto = new QueryExportFileRecordDto();
        }
        //权限过滤
        userId = getAuthUtil.getUserIdByAuthLevel(authLevel, userId);//获取用户ID
        orgCode = getAuthUtil.getOrgCodeByAuthLevel(authLevel, orgCode);//获取企业组织编码
        queryDto.setUserId(userId);
        queryDto.setOrgCode(orgCode);
        queryDto.setAuthLevel(authLevel);
        logger.info("/queryExportFileRecordByPage:{}", JsonUtils.bean2Json(queryDto));
        ResultPage<ExportFileRecord> page = new ResultPage<ExportFileRecord>(queryDto);
        List<ExportFileRecord> list = exportFileService.queryExportFileRecordByPage(queryDto, page);
        page.setList(list);
        page.setTotalItemAndPageNumber(exportFileService.queryExportFileRecordCount(queryDto));
        return page;
    }

    @ApiOperation(value="删除文件导出记录", notes="删除文件导出记录")
    @Jurisdiction("taskCenter_batchExportList_delete")
    @RequestMapping(value = "/delExpertFileRecord", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public boolean delExpertFileRecord(@RequestBody DelExportFileRecordDto delExportFileRecordDto){
        boolean bool = exportFileService.delExpertFileRecord(null != delExportFileRecordDto?delExportFileRecordDto.getRecordIdList():null);
        return bool;
    }


    //下载导出记录文件
    @ApiOperation(value="下载导出记录文件", notes="下载导出记录文件")
    @Jurisdiction("taskCenter_batchExportList_download")
    @RequestMapping(value = "/downloadExportRecord", method = {RequestMethod.POST, RequestMethod.GET})
    public void downloadImportRecord(HttpServletRequest request, HttpServletResponse response,
                                     @RequestParam(required = false, name = "recordId") String recordId)
            throws UnsupportedEncodingException, WriteException {
        ExportFileRecord fileRecords = exportFileService.queryExpertFileRecordById(recordId);
        if(null != fileRecords && !StringUtils.isEmpty(fileRecords.getFileGenerateUrl())){
            String fileUrl = fileRecords.getFileGenerateUrl();
            String fileType = fileUrl.substring(fileUrl.lastIndexOf("."), fileUrl.length());
            response.reset();
            HttpDownload.setHeader(response, "导出记录" + fileType);
            //创建url连接;
            File file = new File(fileUrl);
            InputStream is = null;
            OutputStream os = null;
            //创建url连接;
            HttpURLConnection urlconn = null;
            try {
                URL url = new URL(fileUrl);
                urlconn = (HttpURLConnection)url.openConnection();
                //链接远程服务器;
                urlconn.connect();

                //	is = new BufferedInputStream(new FileInputStream(file));
                is = new BufferedInputStream(urlconn.getInputStream());
                os = new BufferedOutputStream(response.getOutputStream());

                byte[] buffer = new byte[is.available()];
                int len;
                while((len =is.read(buffer))>0){
                    os.write(buffer, 0, len);
                    logger.info(">>>>>>>>" + len+"");
                    logger.info(">>>>>>>>" + buffer+"");
                }
                os.flush();
            }catch(Exception e){
                logger.error("", e);
            }finally{
                if(null != is){
                    try {
                        is.close();
                    } catch (IOException e) {
                        logger.error("is.close error:" + e);
                        e.printStackTrace();
                    }
                }

                if(null != os){
                    try {
                        os.close();
                    } catch (IOException e) {
                        logger.error("os.close error:" + e);
                        e.printStackTrace();
                    }
                }

                if(null != urlconn){
                    urlconn.disconnect();
                }
            }
        }
    }
}
