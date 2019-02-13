package com.guiji.ccmanager.service;

import com.guiji.ccmanager.entity.OutLineInfoAddReq;
import com.guiji.ccmanager.entity.OutLineInfoUpdateReq;

public interface LineOperationService {
    Integer addLineInfo(OutLineInfoAddReq outLineInfoAddReq);

    void updateLineInfo(OutLineInfoUpdateReq outLineInfoUpdateReq);

    void deleteLineInfo(Integer lineId);
}
