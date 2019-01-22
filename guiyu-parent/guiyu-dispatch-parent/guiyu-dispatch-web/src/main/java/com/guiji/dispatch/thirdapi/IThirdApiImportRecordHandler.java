package com.guiji.dispatch.thirdapi;


import com.guiji.dispatch.dao.entity.DispatchPlan;

public interface IThirdApiImportRecordHandler {
        void excute(DispatchPlan vo) throws  Exception;
}
