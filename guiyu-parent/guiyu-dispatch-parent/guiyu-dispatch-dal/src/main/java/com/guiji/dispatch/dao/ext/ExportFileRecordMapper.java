package com.guiji.dispatch.dao.ext;

import com.guiji.dispatch.dto.QueryExportFileRecordDto;
import com.guiji.dispatch.entity.ExportFileRecord;
import com.guiji.dispatch.sys.ResultPage;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface ExportFileRecordMapper {

    //新增文件导出记录
    int addExportFileRecord(ExportFileRecord record);

    //更新记录状态
    int updExportFileRecordState(@Param("recordId") String recordId, @Param("status") Integer status, @Param("updTime") Date updTime);

    //查询记录列表(分页)
    List<ExportFileRecord> queryExportFileRecordByPage(@Param("queryRecord") QueryExportFileRecordDto queryRecord,
                                                       @Param("page") ResultPage<ExportFileRecord> page);

    //查询记录数量
    int queryExportFileRecordCount(@Param("queryRecord") QueryExportFileRecordDto queryRecord);

    //查询查询记录
    ExportFileRecord queryExpertFileRecordById(String recordId);

    //删除记录
    int delExpertFileRecord(@Param("recordIdList") List<String> recordIdList,@Param("delTime") Date delTime);

    int endExportFile(@Param("recordId") String recordId, @Param("status") Integer status,
                      @Param("fileUrl") String fileUrl, @Param("fileSize") String fileSize,
                      @Param("delTime") Date delTime);
}
