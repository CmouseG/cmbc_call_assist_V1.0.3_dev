package com.guiji.service.impl;

import com.guiji.callcenter.dao.entity.CallInDetail;
import com.guiji.service.CallInDetailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Auther: 魏驰
 * @Date: 2019/1/16 19:45
 * @Project：guiyu-parent
 * @Description:
 */
@Slf4j
@Service
public class CallInDetailServiceImpl implements CallInDetailService {
    @Override
    public void saveAll(List<CallInDetail> callInDetails) {
        
    }

    @Override
    public List<CallInDetail> findByCallPlanId(String callPlanId) {
        return null;
    }

    @Override
    public CallInDetail insert(CallInDetail callInDetail) {
        return callInDetail;
    }
}
