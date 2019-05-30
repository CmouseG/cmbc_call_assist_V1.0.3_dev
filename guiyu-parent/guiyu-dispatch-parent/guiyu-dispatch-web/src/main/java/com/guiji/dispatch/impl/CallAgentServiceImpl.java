package com.guiji.dispatch.impl;

import com.guiji.component.result.Result;
import com.guiji.dispatch.exception.BaseException;
import com.guiji.dispatch.exception.DispatchCodeExceptionEnum;
import com.guiji.dispatch.service.CallAgentService;
import com.guiji.toagentserver.api.IAgentGroup;
import com.guiji.toagentserver.entity.AgentGroupInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @Classname CallAgentServiceImpl
 * @Description TODO
 * @Date 2019/5/29 14:48
 * @Created by qinghua
 */
@Service
public class CallAgentServiceImpl implements CallAgentService {

    @Autowired
    IAgentGroup agentGroup;

    /**
     * 查询某个用户所拥有的坐席组
     * @param orgCode
     * @return
     */
    @Override
    public List<String> getAgent(String orgCode) {

        Result.ReturnData<List<AgentGroupInfo>> groups = agentGroup.getGroups(orgCode);

        if(groups == null || CollectionUtils.isEmpty(groups.getBody())) {
            throw new BaseException(DispatchCodeExceptionEnum.IN_DATA_EXCEPTION.getErrorMsg(), DispatchCodeExceptionEnum.IN_DATA_EXCEPTION.getErrorCode());
        }

        List<String> lineIds = new ArrayList<>();

        groups.getBody().forEach(obj -> {
            lineIds.add(obj.getGroupId());
        });

        return lineIds;
    }
}
