package com.guiji.dispatch.batchimport;

import com.guiji.common.model.Page;
import com.guiji.dispatch.dao.entity.BlackList;
import com.guiji.dispatch.dao.entity.FileErrorRecords;

import java.util.List;

public interface IBatchImportFieRecordErrorService {
    boolean save(FileErrorRecords fileErrorRecords);

    boolean batchSave(List<FileErrorRecords> fileErrorRecords);

    boolean update(FileErrorRecords fileErrorRecords);

    List<FileErrorRecords> list(FileErrorRecords fileErrorRecords);

    Page<FileErrorRecords> queryFileErrorRecordsPage(int pageNo, int pageSize, FileErrorRecords fileErrorRecords);

    boolean delete(Long id);
}
