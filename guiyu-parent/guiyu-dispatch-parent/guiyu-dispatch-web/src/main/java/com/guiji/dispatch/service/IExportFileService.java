package com.guiji.dispatch.service;

import com.guiji.dispatch.dto.QueryExportFileRecordDto;
import com.guiji.dispatch.entity.ExportFileRecord;
import com.guiji.dispatch.model.ExportFileDto;
import com.guiji.dispatch.sys.ResultPage;

import java.util.List;

public interface IExportFileService {

    /**
     * 新增导出记录
     * @param exportFileDto
     * @return
     */
    ExportFileRecord addExportFile(ExportFileDto exportFileDto);

    /**
     * 变更记录状态
     * @param recordId
     * @param status
     * @return
     */
    boolean updExportFileStatus(String recordId, Integer status);

    /**
     * 文件导出结束
     * @param recordId
     * @param status
     * @param fileUrl
     * @return
     */
    boolean endExportFile(String recordId, Integer status, String fileUrl);

    /**
     * 查询文件导出记录分页
     * @param queryDto
     * @param page
     * @return
     */
    List<ExportFileRecord> queryExportFileRecordByPage(QueryExportFileRecordDto queryDto, ResultPage<ExportFileRecord> page);

    /**
     * 查询记录数量
     * @param queryDto
     * @return
     */
    int queryExportFileRecordCount(QueryExportFileRecordDto queryDto);

    /**
     * 查询文件导出记录
     * @param recordId
     * @return
     */
    ExportFileRecord queryExpertFileRecordById(String recordId);

    /**
     * 删除文件导入记录数据
     * @param recordIdList
     * @return
     */
    boolean delExpertFileRecord(List<String> recordIdList);
}
