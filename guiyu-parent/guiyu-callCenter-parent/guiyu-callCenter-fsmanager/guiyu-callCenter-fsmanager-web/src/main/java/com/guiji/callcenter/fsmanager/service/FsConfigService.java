package com.guiji.callcenter.fsmanager.service;

import com.guiji.callcenter.fsmanager.entity.FsConfigWebVO;
import com.guiji.callcenter.fsmanager.entity.PagingVO;


public interface FsConfigService {

   void addFsConfig(FsConfigWebVO fsConfigWebVO);

   void delFsConfig(int id);

   void updateFsConfig(int id,FsConfigWebVO fsConfigWebVO);

   PagingVO getFsConfig(String module, String roleId, String busKey, Integer page, Integer size);
}
