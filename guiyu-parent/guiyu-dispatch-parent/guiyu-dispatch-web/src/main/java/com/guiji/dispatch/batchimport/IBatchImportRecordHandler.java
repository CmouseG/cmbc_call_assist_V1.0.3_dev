package com.guiji.dispatch.batchimport;


import com.guiji.dispatch.dao.entity.DispatchPlan;

public interface IBatchImportRecordHandler {
        void excute(DispatchPlan vo) throws  Exception;
}
