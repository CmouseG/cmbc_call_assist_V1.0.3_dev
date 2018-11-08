package com.guiji.calloutserver.service.impl;

import com.guiji.callcenter.dao.CallOutDetailMapper;
import com.guiji.callcenter.dao.entity.CallOutDetail;
import com.guiji.calloutserver.service.CallOutDetailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Auther: 魏驰
 * @Date: 2018/11/5 15:36
 * @Project：guiyu-parent
 * @Description:
 */
@Slf4j
@Service
public class CallOutDetailServiceImpl implements CallOutDetailService {
    @Autowired
    CallOutDetailMapper callOutDetailMapper;

    @Override
    public void save(CallOutDetail callOutDetail) {
        callOutDetailMapper.insert(callOutDetail);
    }

    @Override
    public void save(CallOutDetail calloutdetail, String recordFile) {

    }
}
