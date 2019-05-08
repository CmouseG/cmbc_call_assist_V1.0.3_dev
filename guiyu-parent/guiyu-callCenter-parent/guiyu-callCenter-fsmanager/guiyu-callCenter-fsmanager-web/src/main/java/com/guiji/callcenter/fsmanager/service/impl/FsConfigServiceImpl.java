package com.guiji.callcenter.fsmanager.service.impl;

import com.github.pagehelper.PageInfo;
import com.guiji.callcenter.dao.FsConfigMapper;
import com.guiji.callcenter.dao.entity.FsConfig;
import com.guiji.callcenter.dao.entity.FsConfigExample;
import com.guiji.callcenter.fsmanager.entity.FsConfigWebVO;
import com.guiji.callcenter.fsmanager.entity.PagingVO;
import com.guiji.callcenter.fsmanager.service.FsConfigService;
import com.guiji.callcenter.helper.PageExample;
import com.guiji.utils.BeanUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class FsConfigServiceImpl implements FsConfigService {
    @Autowired
    FsConfigMapper fsConfigMapper;
    @Override
    public void addFsConfig(FsConfigWebVO fsConfigWebVO) {
        FsConfig fsConfig = new FsConfig();
        BeanUtil.copyProperties(fsConfigWebVO,fsConfig);
        fsConfigMapper.insert(fsConfig);
    }

    @Override
    public void delFsConfig(int id) {
        fsConfigMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void updateFsConfig(int id, FsConfigWebVO fsConfigWebVO) {
        FsConfig fsConfig =fsConfigMapper.selectByPrimaryKey(id);
        if(fsConfig!=null){
            fsConfig.setRoleId(fsConfigWebVO.getRoleId());
            fsConfig.setModule(fsConfigWebVO.getModule());
            fsConfig.setBusKey(fsConfigWebVO.getBusKey());
            fsConfig.setValue(fsConfigWebVO.getValue());
            fsConfigMapper.updateByPrimaryKey(fsConfig);
        }
    }

    @Override
    public PagingVO getFsConfig(String module, String roleId, String busKey, Integer page, Integer size) {
        PagingVO paging = new PagingVO();
        List<FsConfig> queueList = queryFsConfigSub(page,size,module,roleId,busKey);
        PageInfo<FsConfig> pageInfo = new PageInfo<>(queueList);
        paging.setPageNo(page);
        paging.setPageSize(size);
        paging.setTotalPage(pageInfo.getPages());
        paging.setTotalRecord(pageInfo.getTotal());
        paging.setRecords((List<Object>) (Object) queueList);
        return paging;
    }

    /**
     *
     * @param page
     * @param size
     * @param module
     * @param roleId
     * @param busKey
     * @return
     */
    public List<FsConfig> queryFsConfigSub(Integer page, Integer size,String module, String roleId, String busKey) {
        PageExample testPage = new PageExample();
        testPage.setPageNum(page);
        testPage.setPageSize(size);
        testPage.enablePaging();
        FsConfigExample fsConfigExample = new FsConfigExample();
        FsConfigExample.Criteria criteria = fsConfigExample.createCriteria();
        if(!StringUtils.isBlank(module)){
            criteria.andModuleLike("%"+module+"%");
        }
        if(!StringUtils.isBlank(roleId)){
            criteria.andRoleIdLike("%"+roleId+"%");
        }
        if(!StringUtils.isBlank(busKey)){
            criteria.andBusKeyLike("%"+busKey+"%");
        }
        List<FsConfig> configsListDb = fsConfigMapper.selectByExample(fsConfigExample);
        return configsListDb;
    }

}
