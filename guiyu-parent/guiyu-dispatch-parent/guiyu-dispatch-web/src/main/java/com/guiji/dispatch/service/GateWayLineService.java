package com.guiji.dispatch.service;

import com.guiji.dispatch.dao.entity.DispatchBatchLine;

import java.util.List;

public interface GateWayLineService {

    //设置redis加入路由网关路线
    void setGatewayLineRedis(List<DispatchBatchLine> lineList);
}
