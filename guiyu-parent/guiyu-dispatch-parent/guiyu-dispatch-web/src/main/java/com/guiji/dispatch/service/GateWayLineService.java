package com.guiji.dispatch.service;

import com.guiji.dispatch.dao.entity.DispatchLines;

import java.util.List;

public interface GateWayLineService {

    //设置redis加入路由网关路线
    void setGatewayLineRedis(List<DispatchLines> lineList);
}
