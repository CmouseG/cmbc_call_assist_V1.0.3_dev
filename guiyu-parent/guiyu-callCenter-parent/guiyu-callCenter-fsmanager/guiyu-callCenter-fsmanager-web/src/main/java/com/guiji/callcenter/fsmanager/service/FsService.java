package com.guiji.callcenter.fsmanager.service;

import com.guiji.fsmanager.entity.FsBind;
import com.guiji.fsmanager.entity.ServiceTypeEnum;

public interface FsService {
    public FsBind applyfs(String serviceId, ServiceTypeEnum serviceType);

    public void releasefs(String serviceId);
}
