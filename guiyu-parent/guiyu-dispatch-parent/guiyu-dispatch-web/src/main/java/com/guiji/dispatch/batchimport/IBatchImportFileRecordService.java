package com.guiji.dispatch.batchimport;

import com.guiji.common.model.Page;
import com.guiji.dispatch.dao.entity.FileErrorRecords;
import com.guiji.dispatch.dao.entity.FileRecords;

import java.util.List;

public interface IBatchImportFileRecordService {
    boolean save(FileRecords fileRecords);

    boolean batchSave(List<FileRecords> fileRecords);

    boolean update(FileRecords fileRecords);

    List<FileRecords> list(FileRecords fileRecords);

    Page<FileRecords> queryFileRecordsPage(int pageNo, int pageSize, FileRecords fileRecords);

    boolean delete(Long id);
}
