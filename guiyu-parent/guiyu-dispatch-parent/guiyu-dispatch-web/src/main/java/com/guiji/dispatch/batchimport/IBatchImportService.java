package com.guiji.dispatch.batchimport;

import java.io.InputStream;

import com.guiji.dispatch.dao.entity.DispatchPlan;

public interface IBatchImportService {

    void batchImport(InputStream inputStream, int batchId, DispatchPlan dispatchPlanParam, Long userId, String orgCode);
}
