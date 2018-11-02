package com.guiji.callcenter.fsmanager.service;

import com.guiji.fsmanager.entity.FsBindVO;
import com.guiji.fsmanager.entity.ServiceTypeEnum;

public interface FsService {
    public FsBindVO applyfs(String serviceId, ServiceTypeEnum serviceType);

    public void releasefs(String serviceId);
}
