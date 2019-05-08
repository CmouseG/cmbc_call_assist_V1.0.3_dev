package com.guiji.callcenter.fsmanager.service;

import com.guiji.fsmanager.entity.FsBindVO;
import com.guiji.fsmanager.entity.FsConfigVO;
import com.guiji.fsmanager.entity.ServiceTypeEnum;

import java.util.List;

public interface FsService {
     FsBindVO applyfs(String serviceId, ServiceTypeEnum serviceType);

     boolean releasefs(String serviceId);

     List<FsConfigVO> getFsConfig(String role);
}
