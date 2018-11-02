package com.guiji.callcenter.fsmanager.service.impl;

import com.guiji.callcenter.dao.FsBindMapper;
import com.guiji.callcenter.dao.entity.FsBindExample;
import com.guiji.callcenter.fsmanager.service.FsService;
import com.guiji.fsmanager.entity.FsBindVO;
import com.guiji.fsmanager.entity.ServiceTypeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FsServiceImpl implements FsService {
    @Autowired
    FsBindMapper fsBindMapper;

    public FsBindVO applyfs(String serviceId, ServiceTypeEnum serviceType) {

        FsBindExample example = new FsBindExample();
        FsBindExample.Criteria criteria = example.createCriteria();
        criteria.andServiceIdEqualTo(serviceId);
        List<com.guiji.callcenter.dao.entity.FsBind> fsBinds = fsBindMapper.selectByExample(example);

        FsBindVO fs =new FsBindVO();
        fs.setServiceId("nanjing");
        fs.setFsAgentId("xx");
        fs.setFsAgentAddr("192.168.1.12:8088");
        fs.setFsEslPort("18021");
        fs.setFsEslPwd("123456qwert");
        fs.setFsInPort("50601");
        fs.setFsOutPort("50602");
        return fs;
    }

    @Override
    public void releasefs(String serviceId) {
        FsBindExample example = new FsBindExample();
        FsBindExample.Criteria criteria = example.createCriteria();
        criteria.andServiceIdEqualTo(serviceId);
        fsBindMapper.deleteByExample(example);
    }
}
