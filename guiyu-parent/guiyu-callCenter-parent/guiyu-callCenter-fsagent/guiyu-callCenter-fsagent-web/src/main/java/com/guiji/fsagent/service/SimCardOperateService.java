package com.guiji.fsagent.service;

import com.guiji.fsagent.entity.FsSipOprVO;
import com.guiji.fsagent.entity.SimCardOprVO;

public interface SimCardOperateService {

    FsSipOprVO createGateway(SimCardOprVO simCardVO);

    boolean deleteGateway(int startCount, int countsStep, int countNum);

}
