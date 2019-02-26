package com.guiji.callcenter.fsmanager.service;

import com.guiji.fsmanager.entity.FsSipVO;
import com.guiji.fsmanager.entity.SimCardVO;

public interface ISimCardService {
     FsSipVO createGateway(SimCardVO simCardVO);

     boolean deleteGateway(String gatewayId) ;
}
