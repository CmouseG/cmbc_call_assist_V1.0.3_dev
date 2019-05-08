package com.guiji.callcenter.fsmanager.service.impl;

import com.google.common.collect.ArrayListMultimap;
import com.guiji.callcenter.dao.FsBindMapper;
import com.guiji.callcenter.dao.FsConfigMapper;
import com.guiji.callcenter.dao.entity.FsBind;
import com.guiji.callcenter.dao.entity.FsBindExample;
import com.guiji.callcenter.dao.entity.FsConfig;
import com.guiji.callcenter.dao.entity.FsConfigExample;
import com.guiji.callcenter.fsmanager.config.Constant;
import com.guiji.callcenter.fsmanager.manager.EurekaManager;
import com.guiji.callcenter.fsmanager.service.FsService;

import com.guiji.component.result.Result;
import com.guiji.fsagent.api.IFsState;
import com.guiji.fsagent.entity.FsInfoVO;
import com.guiji.fsmanager.entity.FsBindVO;
import com.guiji.fsmanager.entity.FsConfigVO;
import com.guiji.fsmanager.entity.ModuleVO;
import com.guiji.fsmanager.entity.ServiceTypeEnum;
import com.guiji.utils.FeignBuildUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class FsServiceImpl implements FsService {
    private final Logger logger = LoggerFactory.getLogger(FsServiceImpl.class);

    @Autowired
    FsBindMapper fsBindMapper;
    @Autowired
    EurekaManager eurekaManager;
    @Autowired
    FsConfigMapper fsConfigMapper;

    public FsBindVO applyfs(String serviceId, ServiceTypeEnum serviceType) {
        String serviceName = "";
        String role = "";
        switch (serviceType) {
            case calloutserver:
                serviceName = Constant.SERVER_NAME_CALLOUTSERVER;
                role = Constant.FREESWITCH_ROLE_CALLOUT;
                break;
            case callinserver:
                serviceName = Constant.SERVER_NAME_CALLINSERVER;
                role = Constant.FREESWITCH_ROLE_CALLIN;
                break;
            case callcenter:
                serviceName = Constant.SERVER_NAME_CALLCENTER;
                role = Constant.FREESWITCH_ROLE_TOAGENT;
                break;
            case fsline:
                serviceName = Constant.SERVER_NAME_FSLINE;
                role = Constant.FREESWITCH_ROLE_LINE;
                break;
        }

        FsBindExample example = new FsBindExample();
        FsBindExample.Criteria criteria = example.createCriteria();
        criteria.andServiceIdEqualTo(serviceId);
        List<FsBind> fsBinds = fsBindMapper.selectByExample(example);
        if (fsBinds.size() > 0) {//原来就有记录，先检查先前绑定的fsagent状态，状态ok直接返回
            FsBind fsBind = fsBinds.get(0);
            IFsState iFsStateApi = FeignBuildUtil.feignBuilderTarget(IFsState.class, Constant.PROTOCOL + fsBind.getFsAgentId());
            Result.ReturnData<String> result;
            try {
                //调用fsagent健康状态接口
                result = iFsStateApi.ishealthy();
            } catch (Exception e) {
                logger.warn("fsagent服务:[{}]故障-->", fsBind.getFsAgentId(), e);
                //todo --告警某个fagsent服务挂了
                return applyfsSub(serviceName,role, serviceId);
            }
            if (result.getCode().equals("0")) {
                if(!result.getBody().equals(role)){
                    deleteFsBind(serviceId);
                    return applyfsSub(serviceName,role, serviceId);
                }
                //用于接口返回的对象
                FsBindVO fsBindVO = new FsBindVO();
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
            } else {//如果原来绑定的fsagent不可用，则走重新申请资源的方法
                return applyfsSub(serviceName,role, serviceId);
            }
        } else {//如果数据库中没有该绑定信息，直接走申请方法
            return applyfsSub(serviceName,role, serviceId);
        }
    }

    /**
     * 删除绑定关系
     * @param serviceId
     */
    public void deleteFsBind(String serviceId){
        FsBindExample example = new FsBindExample();
        FsBindExample.Criteria criteria = example.createCriteria();
        criteria.andServiceIdEqualTo(serviceId);
        fsBindMapper.deleteByExample(example);
    }
    /**
     * 申请新的fsagent
     * @param serviceName
     * @param role
     * @param serviceId
     * @return
     */
    public synchronized FsBindVO applyfsSub(String serviceName,String role, String serviceId) {
        //得到数据库中所有的记录(fsagent的serviceId)
        List<String> serverUseList = new ArrayList<String>();
        FsBindExample exampleAll = new FsBindExample();
        List<FsBind> fsBindsAll = fsBindMapper.selectByExample(exampleAll);
        for (FsBind fs : fsBindsAll) {
            serverUseList.add(fs.getFsAgentId());
        }
        //注册中心所有在线的fsagent
        List<String> serverList = eurekaManager.getInstances(Constant.SERVER_NAME_FSAGENT);
        //得到所有空闲的（将数据库中的排除掉）
        serverList.removeAll(serverUseList);

        //这个list用于排除第一次遍历发热过程中角色不对的（包括健康状况异常的不包括空角色的）
        List<String> firstExcludeServerList = new ArrayList<String>();

        //第一次遍历：遍历空闲的fsagent，根据角色找出一个能用的，如果有直接返回同时将非该角色的和不健康的fs都放入到
        for (String server : serverList) {
            IFsState iFsStateApi = FeignBuildUtil.feignBuilderTarget(IFsState.class, Constant.PROTOCOL + server);
            //调用fsagent健康状态接口
            Result.ReturnData<String> result;
            try {
                result = iFsStateApi.ishealthy();
            } catch (Exception e) {
                firstExcludeServerList.add(server);
                break;
            }
            if (!result.getCode().equals("0") || (!result.getBody().equals(role) && !result.getBody().equals("norole"))) {
                firstExcludeServerList.add(server);
            } else if (result.getBody().equals(role)) {
                firstExcludeServerList.add(server);
                Result.ReturnData<FsInfoVO> resultInfo = iFsStateApi.fsinfo();
                //得到返回的fsagent对象
                FsInfoVO fsInfoVO = resultInfo.getBody();
                if (fsInfoVO != null) {
                    return buildFsBind(fsInfoVO, serviceId, serviceName);
                }
            }
        }
        serverList.removeAll(firstExcludeServerList);//删除第一次遍历要排除的
        //第二次遍历。遍历出没有角色的第一个fsagent
        for (String server : serverList) {
            IFsState iFsStateApi = FeignBuildUtil.feignBuilderTarget(IFsState.class, Constant.PROTOCOL + server);
            //调用fsagent健康状态接口
            Result.ReturnData<String> result;
            try {
                result = iFsStateApi.ishealthy();
            } catch (Exception e) {
                firstExcludeServerList.add(server);
                break;
            }
            if (result.getBody().equals("norole")) {
                Result.ReturnData<FsInfoVO> resultInfo = iFsStateApi.fsinfo();
                //得到返回的fsagent对象
                FsInfoVO fsInfoVO = resultInfo.getBody();
                if (fsInfoVO != null) { //如果得到的FsInfoVO不为空继续执行，为空则直接继续遍历fsagent 的list
                    return buildFsBind(fsInfoVO, serviceId, serviceName);
                }
            }
        }
        return null;
    }



    /**
     * 拼装FsBindVO
     * @param fsInfoVO
     * @param serviceId
     * @param serviceName
     * @return
     */
    public FsBindVO buildFsBind(FsInfoVO fsInfoVO, String serviceId, String serviceName) {
        //用于接口返回的对象
        FsBindVO fsBindVO = new FsBindVO();
        fsBindVO.setServiceId(serviceId);
        fsBindVO.setFsAgentId(fsInfoVO.getFsAgentId());
        fsBindVO.setFsAgentAddr(fsInfoVO.getFsAgentId());
        fsBindVO.setFsEslPort(fsInfoVO.getFsEslPort());
        fsBindVO.setFsEslPwd(fsInfoVO.getFsEslPwd());
        fsBindVO.setFsInPort(fsInfoVO.getFsInPort());
        fsBindVO.setFsOutPort(fsInfoVO.getFsOutPort());
        //用于入库的对象
        FsBind fsBind = new FsBind();
        //复制对象的属性值
        BeanUtils.copyProperties(fsBindVO, fsBind);
        fsBind.setServiceName(serviceName);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        fsBind.setCreateDate(sdf.format(new Date()));
        FsBindExample example = new FsBindExample();
        FsBindExample.Criteria criteria = example.createCriteria();
        criteria.andServiceIdEqualTo(fsBind.getServiceId());
        List<FsBind> list = fsBindMapper.selectByExample(example);
        if (list != null && list.size() > 0) {
            FsBind fsbindOld = list.get(0);
            fsBind.setId(fsbindOld.getId());
            fsBindMapper.updateByPrimaryKey(fsBind);
        } else {
            fsBindMapper.insertSelective(fsBind);
        }
        return fsBindVO;
    }

    @Override
    public boolean releasefs(String serviceId) {
        FsBindExample example = new FsBindExample();
        FsBindExample.Criteria criteria = example.createCriteria();
        criteria.andServiceIdEqualTo(serviceId);
        fsBindMapper.deleteByExample(example);
        return true;
    }

    @Override
    public List<FsConfigVO> getFsConfig(String role) {
        List<FsConfigVO> fsConfigVOList = new ArrayList<>();
        FsConfigExample example = new FsConfigExample();
        example.createCriteria().andRoleIdEqualTo(role);
        List<FsConfig> list =fsConfigMapper.selectByExample(example);
        //存放数据库中查询出来的config值
        ArrayListMultimap<String,ModuleVO> multimap = ArrayListMultimap.create();
        if(list.size()>0){
            for (FsConfig fsConfig:list) {
               ModuleVO moduleVO = new ModuleVO();
                moduleVO.setKey(fsConfig.getBusKey());
                moduleVO.setValue(fsConfig.getValue());
                multimap.put(fsConfig.getModule(),moduleVO);
            }
        }
        for (String key : multimap.keySet()) {
            FsConfigVO fsConfigVO = new FsConfigVO();
            fsConfigVO.setModule(key);
            fsConfigVO.setConfig(multimap.get(key));
            fsConfigVOList.add(fsConfigVO);
        }
        return fsConfigVOList;
    }
}
