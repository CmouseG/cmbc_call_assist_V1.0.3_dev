package com.guiji.dispatch.impl;

import com.alibaba.excel.util.StringUtils;
import com.guiji.common.exception.GuiyuException;
import com.guiji.dispatch.dao.ext.ExportFileRecordMapper;
import com.guiji.dispatch.dto.QueryExportFileRecordDto;
import com.guiji.dispatch.entity.ExportFileRecord;
import com.guiji.dispatch.enums.ByteSizeUnitEnum;
import com.guiji.dispatch.enums.SysDefaultExceptionEnum;
import com.guiji.dispatch.enums.SysDelEnum;
import com.guiji.dispatch.model.ExportFileDto;
import com.guiji.dispatch.service.GetAuthUtil;
import com.guiji.dispatch.service.IExportFileService;
import com.guiji.dispatch.sys.ResultPage;
import com.guiji.dispatch.util.DaoHandler;
import com.guiji.utils.IdGenUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;
import java.util.List;

@Service
public class ExportFileServiceImpl implements IExportFileService {

    private Logger logger = LoggerFactory.getLogger(ExportFileServiceImpl.class);

    @Autowired
    private ExportFileRecordMapper exportFileRecordMapper;

    @Autowired
    private GetAuthUtil getAuthUtil;

    @Override
    public boolean updExportFileStatus(String recordId, Integer status) {
        if(!StringUtils.isEmpty(recordId) && !StringUtils.isEmpty(status)){
            return DaoHandler.getMapperBoolRes(exportFileRecordMapper.updExportFileRecordState(recordId, status, new Date()));//
        }else{
            throw new GuiyuException(SysDefaultExceptionEnum.NULL_DATA_EXCEPTION.getErrorCode(),
                    SysDefaultExceptionEnum.NULL_DATA_EXCEPTION.getErrorMsg());
        }
    }

    @Override
    public ExportFileRecord addExportFile(ExportFileDto exportFileDto) {
        if(null != exportFileDto && !StringUtils.isEmpty(exportFileDto.getBusiType())){
            String busiType = exportFileDto.getBusiType();
            String busiId = exportFileDto.getBusiId();
            Integer fileType = exportFileDto.getFileType();
            String foUrl = exportFileDto.getFileOriginalUrl();

            ExportFileRecord record = new ExportFileRecord();
            record.setRecordId(IdGenUtil.uuid());
            record.setBusiId(busiId);
            record.setBusiType(busiType);
            record.setFileType(fileType);
            record.setFileOriginalUrl(foUrl);
            record.setFileGenerateUrl(foUrl);
            record.setTotalNum(exportFileDto.getTotalNum());
            record.setUserId(exportFileDto.getUserId());            //操作者ID
            record.setUserName(exportFileDto.getCreateName());    //所属者
            record.setOrgCode(exportFileDto.getOrgCode());          //操作者组织
            record.setCreateName(exportFileDto.getCreateName());    //所属者
            record.setCreateTime(exportFileDto.getCreateTime());    //创建日期
            record.setAddTime(new Date());
            record.setDelFlag(SysDelEnum.NORMAL.getState());

            boolean bool = DaoHandler.getMapperBoolRes(exportFileRecordMapper.addExportFileRecord(record));
            return bool?record:null;
        }else{
            throw new GuiyuException(SysDefaultExceptionEnum.NULL_DATA_EXCEPTION.getErrorCode(),
                    SysDefaultExceptionEnum.NULL_DATA_EXCEPTION.getErrorMsg());
        }

    }

    @Override
    public List<ExportFileRecord> queryExportFileRecordByPage(QueryExportFileRecordDto queryDto, ResultPage<ExportFileRecord> page) {
    //    ExportFileRecord queryRecordDto = null;
        if(null != queryDto){
            /*queryRecordDto = new ExportFileRecord();
            BeanUtils.copyProperties(queryDto, queryRecordDto, ExportFileRecord.class);*/
        }
        return exportFileRecordMapper.queryExportFileRecordByPage(queryDto, page);
    }

    @Override
    public int queryExportFileRecordCount(QueryExportFileRecordDto queryDto) {
    //    ExportFileRecord queryRecordDto = null;
        if(null != queryDto){
            /*queryRecordDto = new ExportFileRecord();
            BeanUtils.copyProperties(queryDto, queryRecordDto, ExportFileRecord.class);*/
        }
        return exportFileRecordMapper.queryExportFileRecordCount(queryDto);
    }

    @Override
    public ExportFileRecord queryExpertFileRecordById(String recordId) {
        if(!StringUtils.isEmpty(recordId)) {
            return exportFileRecordMapper.queryExpertFileRecordById(recordId);
        }else{
            throw new GuiyuException(SysDefaultExceptionEnum.NULL_DATA_EXCEPTION.getErrorCode(),
                    SysDefaultExceptionEnum.NULL_DATA_EXCEPTION.getErrorMsg());
        }
    }

    @Override
    public boolean delExpertFileRecord(List<String> recordIdList) {
        if(null != recordIdList && recordIdList.size()>0) {
            boolean bool = DaoHandler.getMapperBoolRes(exportFileRecordMapper.delExpertFileRecord(recordIdList, new Date()));
            return bool;
        }else{
            throw new GuiyuException(SysDefaultExceptionEnum.NULL_DATA_EXCEPTION.getErrorCode(),
                    SysDefaultExceptionEnum.NULL_DATA_EXCEPTION.getErrorMsg());
        }
    }

    @Override
    public boolean endExportFile(String recordId, Integer status, String fileUrl) {
        if(!StringUtils.isEmpty(recordId) && null != status) {
            //获取文件大小
            String fileSize = this.getFileSize(fileUrl, ByteSizeUnitEnum.KB.getUnit()) + " KB";
            boolean bool = DaoHandler.getMapperBoolRes(exportFileRecordMapper.endExportFile(recordId, status, fileUrl, fileSize, new Date()));
            return bool;
        }else{
            throw new GuiyuException(SysDefaultExceptionEnum.NULL_DATA_EXCEPTION.getErrorCode(),
                    SysDefaultExceptionEnum.NULL_DATA_EXCEPTION.getErrorMsg());
        }
    }

    /**
     * 获取文件大小， unit为单位
     * @param fileUrl
     * @return
     */
    private String getFileSize(String fileUrl, Integer unit){
        double len = 0.00d;
        if(!StringUtils.isEmpty(fileUrl)) {
            //创建url连接;
            File file = new File(fileUrl);
            InputStream is = null;
            OutputStream os = null;
            //创建url连接;
            HttpURLConnection urlconn = null;
            try {
                URL url = new URL(fileUrl);
                urlconn = (HttpURLConnection) url.openConnection();
                //链接远程服务器;
                urlconn.connect();
                is = new BufferedInputStream(urlconn.getInputStream());
                //    byte[] buffer = new byte[is.available()];
                double divi = Math.pow(1024, unit);
                len = (double) (is.available() / divi);
            } catch (Exception e) {
                logger.error("", e);
            } finally {
                if (null != is) {
                    try {
                        is.close();
                    } catch (IOException e) {
                        logger.error("is.close error:" + e);
                        e.printStackTrace();
                    }
                }

                if (null != urlconn) {
                    urlconn.disconnect();
                }
            }
        }
        return String.format("%.2f", len);//两位四舍五入
    }

}
