package com.guiji.dispatch.batchimport;

import com.guiji.dispatch.dao.entity.DispatchPlan;

import java.io.File;
import java.io.InputStream;

public interface IBatchImportService {

    void batchImport(File file, int batchId, DispatchPlan dispatchPlanParam, Long userId, String orgCode, Integer orgId);
}
