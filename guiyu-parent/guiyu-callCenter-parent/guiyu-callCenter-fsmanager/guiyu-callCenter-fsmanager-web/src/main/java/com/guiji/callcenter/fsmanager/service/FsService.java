package com.guiji.callcenter.fsmanager.service;

import com.guiji.fsmanager.entity.FsBindVO;
import com.guiji.fsmanager.entity.ServiceTypeEnum;

public interface FsService {
     FsBindVO applyfs(String serviceId, ServiceTypeEnum serviceType);

     boolean releasefs(String serviceId);
}
