package com.guiji.dispatch.impl;

import com.guiji.dispatch.dao.ext.PlanTableMapper;
import com.guiji.dispatch.service.PlanTableService;
import com.guiji.dispatch.util.DaoHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PlanTableServiceImpl implements PlanTableService {

    private Logger logger = LoggerFactory.getLogger(PlanTableServiceImpl.class);

    @Autowired
    private PlanTableMapper dispatchPlanTableMapper;

    /**
     * 创建plan表
     * @param orgId
     * @return
     */
    @Override
    public boolean createPlanTable(Integer orgId) {
        if(null != orgId){
            return DaoHandler.getMapperBoolRes(dispatchPlanTableMapper.createPlanTable(orgId));
        }else {
            return false;
        }
    }

}
