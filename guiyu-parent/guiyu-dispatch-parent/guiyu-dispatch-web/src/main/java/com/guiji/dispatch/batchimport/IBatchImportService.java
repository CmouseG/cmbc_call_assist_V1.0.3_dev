package com.guiji.dispatch.batchimport;

import com.guiji.dispatch.dao.entity.DispatchPlan;
import org.apache.poi.ss.usermodel.Sheet;

public interface IBatchImportService {

    void batchImport(Sheet sheet, int batchId, DispatchPlan dispatchPlanParam, Long userId, String orgCode);
}
