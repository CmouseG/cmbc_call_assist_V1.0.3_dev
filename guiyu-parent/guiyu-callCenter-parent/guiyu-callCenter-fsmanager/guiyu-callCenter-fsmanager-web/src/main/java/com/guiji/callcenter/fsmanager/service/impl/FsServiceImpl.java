package com.guiji.callcenter.fsmanager.service.impl;

import com.guiji.callcenter.dao.FsBindMapper;
import com.guiji.callcenter.dao.entity.FsBind;
import com.guiji.callcenter.dao.entity.FsBindExample;
import com.guiji.callcenter.fsmanager.config.Constant;
import com.guiji.callcenter.fsmanager.manager.EurekaManager;
import com.guiji.callcenter.fsmanager.service.FsService;

import com.guiji.component.result.Result;
import com.guiji.fsagent.api.IFsState;
import com.guiji.fsagent.entity.FsInfoVO;
import com.guiji.fsmanager.entity.FsBindVO;
import com.guiji.fsmanager.entity.ServiceTypeEnum;
import com.guiji.utils.FeignBuildUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class FsServiceImpl implements FsService {
    @Autowired
    FsBindMapper fsBindMapper;
    @Autowired
    EurekaManager eurekaManager;
    public FsBindVO applyfs(String serviceId, ServiceTypeEnum serviceType) {
        FsBindExample example = new FsBindExample();
        FsBindExample.Criteria criteria = example.createCriteria();
        criteria.andServiceIdEqualTo(serviceId);
        List<FsBind> fsBinds = fsBindMapper.selectByExample(example);
        if(fsBinds.size()>0){//原来就有记录，先检查先前绑定的fsagent状态，状态ok直接返回
            FsBind fsBind = fsBinds.get(0);
            IFsState iFsStateApi = FeignBuildUtil.feignBuilderTarget(IFsState.class, Constant.PROTOCOL + fsBind.getFsAgentId());
            //调用fsagent健康状态接口
            Result.ReturnData<Boolean> result = iFsStateApi.ishealthy();
            if (result.body) {
                //用于接口返回的对象
                FsBindVO fsBindVO =new FsBindVO();
                fsBindVO.setServiceId(fsBind.getFsAgentId());
                fsBindVO.setFsAgentId(fsBind.getFsAgentId());
                fsBindVO.setFsAgentAddr(fsBind.getFsAgentId());
                fsBindVO.setFsEslPort(fsBind.getFsEslPort());
                fsBindVO.setFsEslPwd(fsBind.getFsEslPwd());
                fsBindVO.setFsInPort(fsBind.getFsInPort());
                fsBindVO.setFsOutPort(fsBind.getFsOutPort());
                //复制对象的属性值
                BeanUtils.copyProperties(fsBind, fsBindVO);
                return fsBindVO;
            }else {//如果原来绑定的fsagent不可用，则走重新申请资源的方法
              return  applyfsSub(serviceType,1,serviceId);
            }
        }else {//如果数据库中没有该绑定信息，直接走申请方法
          return  applyfsSub(serviceType,0,serviceId);
        }
    }

    /**
     * 申请freeswitch（fsagent）资源的辅助方法
     * @param serviceType
     * @param type
     * @return
     */

    public FsBindVO applyfsSub(ServiceTypeEnum serviceType,int type,String serviceId ){
        //得到数据库中所有的记录(fsagent的serviceId)
        List<String> serverUseList =  new ArrayList<String>();
        FsBindExample exampleAll = new FsBindExample();
        List<FsBind> fsBindsAll = fsBindMapper.selectByExample(exampleAll);
        for (FsBind fs:fsBindsAll) {
            serverUseList.add(fs.getFsAgentId());
        }
        List<String> serverList =  eurekaManager.getInstances(Constant.SERVER_NAME_FSAGENT);
        //得到所有空闲的
        serverList.removeAll(serverUseList);
         //遍历空闲的fsagent，找出一个能用的，如果一个也没有直接返回null
        for(String server:serverList){
            IFsState iFsStateApi = FeignBuildUtil.feignBuilderTarget(IFsState.class,Constant.PROTOCOL +server);
            //调用fsagent健康状态接口
            Result.ReturnData<Boolean> result = iFsStateApi.ishealthy();
            if(result.body){
                Result.ReturnData<FsInfoVO> resultInfo=iFsStateApi.fsinfo();

                //得到返回的fsagent对象
                FsInfoVO fsInfoVO = resultInfo.getBody();
                if(fsInfoVO!=null){//如果得到的FsInfoVO不为空继续执行，为空则直接继续遍历fsagent 的list
                //用于接口返回的对象
                FsBindVO fsBindVO =new FsBindVO();
                fsBindVO.setServiceId(serviceId);
                fsBindVO.setFsAgentId(fsInfoVO.getFsAgentId());
                fsBindVO.setFsAgentAddr(fsInfoVO.getFsAgentId());
                fsBindVO.setFsEslPort(fsInfoVO.getFsEslPort());
                fsBindVO.setFsEslPwd(fsInfoVO.getFsEslPwd());
                fsBindVO.setFsInPort(fsInfoVO.getFsInPort());
                fsBindVO.setFsOutPort(fsInfoVO.getFsOutPort());

                //用于入库的对象
                FsBind fsBind =new FsBind();
                //复制对象的属性值
                BeanUtils.copyProperties(fsBindVO, fsBind);
                switch (serviceType){
                    case calloutserver:
                        fsBind.setServiceName(Constant.SERVER_NAME_CALLOUTSERVER);
                        break;
                    case callinserver:
                        fsBind.setServiceName(Constant.SERVER_NAME_CALLINSERVER);
                        break;
                    case callcenter:
                        fsBind.setServiceName(Constant.SERVER_NAME_CALLCENTER);
                        break;
                    case fsline:
                        fsBind.setServiceName(Constant.SERVER_NAME_FSLINE);
                        break;
                }
                SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                fsBind.setCreateDate(sdf.format(new Date()));
                 if(type==1){ //如果原来数据库中有记录，更新数据库
                     FsBindExample example = new FsBindExample();
                     FsBindExample.Criteria criteria = example.createCriteria();
                     criteria.andServiceIdEqualTo(fsBind.getServiceId());
                     fsBindMapper.updateByExample(fsBind,example);
                 }else{//如果原来数据库中没有，则插入记录
                     fsBindMapper.insertSelective(fsBind);
                 }
                return fsBindVO;
                }
            }
        }
        return null;
    }

    @Override
    public void releasefs(String serviceId) {
        FsBindExample example = new FsBindExample();
        FsBindExample.Criteria criteria = example.createCriteria();
        criteria.andServiceIdEqualTo(serviceId);
        fsBindMapper.deleteByExample(example);
    }
}
