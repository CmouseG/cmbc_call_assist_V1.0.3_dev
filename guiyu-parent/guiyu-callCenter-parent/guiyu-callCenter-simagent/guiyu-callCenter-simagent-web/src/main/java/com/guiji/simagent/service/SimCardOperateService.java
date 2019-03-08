package com.guiji.simagent.service;

import com.guiji.simagent.entity.FsSipOprVO;
import com.guiji.simagent.entity.SimCardOprVO;

public interface SimCardOperateService {

    FsSipOprVO createGateway(SimCardOprVO simCardVO);

    boolean deleteGateway(int startCount, int countsStep, int countNum);

}
