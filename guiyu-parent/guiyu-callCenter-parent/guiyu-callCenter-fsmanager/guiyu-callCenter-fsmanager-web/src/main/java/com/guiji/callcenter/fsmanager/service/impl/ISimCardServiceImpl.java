package com.guiji.callcenter.fsmanager.service.impl;

import com.guiji.callcenter.dao.SimGatewayMapper;
import com.guiji.callcenter.dao.entity.SimGateway;
import com.guiji.callcenter.dao.entity.SimGatewayExample;
import com.guiji.callcenter.fsmanager.config.Constant;
import com.guiji.callcenter.fsmanager.config.FsmanagerExceptionEnum;
import com.guiji.callcenter.fsmanager.manager.EurekaManager;
import com.guiji.callcenter.fsmanager.service.ISimCardService;
import com.guiji.common.exception.GuiyuException;
import com.guiji.component.result.Result;
import com.guiji.fsmanager.entity.FsSipVO;
import com.guiji.fsmanager.entity.SimCardVO;
import com.guiji.simagent.api.ISimCardOperate;
import com.guiji.simagent.entity.FsSipOprVO;
import com.guiji.simagent.entity.SimCardOprVO;
import com.guiji.utils.FeignBuildUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;
@Slf4j
@Service
public class ISimCardServiceImpl implements ISimCardService {
    @Autowired
    SimGatewayMapper simGatewayMapper;
    @Autowired
    EurekaManager eurekaManager;

    @Override
    public FsSipVO createGateway(SimCardVO simCardVO) {
        int startCount = simCardVO.getStartCount();
        int countsStep = simCardVO.getCountsStep();
        int startPwd = simCardVO.getStartPwd();
        int pwdStep = simCardVO.getPwdStep();
        int countNum = simCardVO.getCountNum();
        String gatewayId = simCardVO.getGatewayId();
        //从注册中心随机找到一个simagent
        List<String> serverList = eurekaManager.getInstances(Constant.SERVER_NAME_SIMAGENT);
        Random random = new Random();
        int n = random.nextInt(serverList.size());
        String simAgentServer =  serverList.get(n);
        //-- todo 判断simagent是否正常

        //数据库留痕
        SimGateway simGateway = new SimGateway();
        simGateway.setStartCount(startCount);
        simGateway.setCountsStep(countsStep);
        simGateway.setStartPwd(startPwd);
        simGateway.setPwdStep(pwdStep);
        simGateway.setCountNum(countNum);
        simGateway.setSimGatewayId(gatewayId);
        simGateway.setSimAgentId(simAgentServer);

        SimGatewayExample simGatewayExample = new SimGatewayExample();
        simGatewayExample.createCriteria().andSimGatewayIdEqualTo(gatewayId);
        List<SimGateway> simGatewayList = simGatewayMapper.selectByExample(simGatewayExample);
        if(simGatewayList.size()>0){
            simGateway.setId(simGatewayList.get(0).getId());
            simGatewayMapper.updateByPrimaryKey(simGateway);
        }else{
            simGatewayMapper.insert(simGateway);
        }

        SimCardOprVO simCardOprVO = new SimCardOprVO();
        //复制对象的属性值
        BeanUtils.copyProperties(simCardVO,simCardOprVO );
        ISimCardOperate ISimCardOperateApi = FeignBuildUtil.feignBuilderTarget(ISimCardOperate.class, Constant.PROTOCOL + simAgentServer);
        Result.ReturnData<FsSipOprVO> result  =  ISimCardOperateApi.createGateway(simCardOprVO);
        FsSipOprVO fsSipOprVO =result.getBody();
        FsSipVO fsSipVO = new FsSipVO();
        BeanUtils.copyProperties(fsSipOprVO,fsSipVO);
        return fsSipVO;
    }

    @Override
    public boolean deleteGateway(String gatewayId) {
        SimGatewayExample simGatewayExample = new SimGatewayExample();
        simGatewayExample.createCriteria().andSimGatewayIdEqualTo(gatewayId);
        List<SimGateway> simGatewayList = simGatewayMapper.selectByExample(simGatewayExample);
        if(simGatewayList.size()>0){
            SimGateway simGateway = simGatewayList.get(0);
            simGatewayMapper.deleteByExample(simGatewayExample);
            ISimCardOperate ISimCardOperateApi = FeignBuildUtil.feignBuilderTarget(ISimCardOperate.class, Constant.PROTOCOL + simGateway.getSimAgentId());
            Result.ReturnData<Boolean> result = null;
            try {
                result = ISimCardOperateApi.deleteGateway(simGateway.getStartCount(),simGateway.getCountsStep(),simGateway.getCountNum());
            } catch (Exception e) {
                log.warn("simagent服务:[{}]故障-->",simGateway, e);
                //todo --告警某个simagent服务挂了
                throw new GuiyuException(FsmanagerExceptionEnum.EXCP_FSMANAGER_SIMAGENT_DOWN);
            }
            if (!result.body) {
              return false;
            }
        }
        return true;
    }
}
