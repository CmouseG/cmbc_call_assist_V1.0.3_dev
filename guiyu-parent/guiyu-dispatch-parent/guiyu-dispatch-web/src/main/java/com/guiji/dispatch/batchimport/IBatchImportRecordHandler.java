package com.guiji.dispatch.batchimport;


import com.guiji.dispatch.dao.entity.DispatchPlan;

public interface IBatchImportRecordHandler {

        void preCheck(DispatchPlan vo) throws Exception;

        void saveDB(DispatchPlan vo);
}
