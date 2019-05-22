package com.guiji.api.controller;

import com.guiji.api.vo.GroupsVo;
import com.guiji.common.GenericRo;
import com.guiji.component.result.Result;
import com.guiji.exception.ThirdApiException;
import com.guiji.exception.ThirdApiExceptionEnum;
import com.guiji.toagentserver.api.IAgentGroup;
import com.guiji.toagentserver.entity.AgentGroupInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Classname ToagentApiController
 * @Description TODO
 * @Date 2019/4/22 9:10
 * @Created by qinghua
 */
@RestController
@RequestMapping("/thirdApi")
public class ToagentApiController {

    @Autowired
    IAgentGroup iAgentGroup;

    /**
     * 获取坐席组
     * @param ro
     * @return
     */
    @PostMapping("/getGroups")
    public GroupsVo getGroups(@RequestBody GenericRo ro) {

        String orgCode = ro.getOrgCode();

        Result.ReturnData<List<AgentGroupInfo>> groups = iAgentGroup.getGroups(orgCode);

        StringBuilder res = new StringBuilder();

        if(groups != null && groups.success) {
            if(CollectionUtils.isEmpty(groups.getBody())) {
                throw new ThirdApiException(ThirdApiExceptionEnum.QUERY_NO_RESULT);
            }else {
                groups.getBody().forEach(obj -> {
                    res.append(obj.getGroupId());
                    res.append("^");
                    res.append(obj.getGroupName());
                    res.append("|");
                });

                GroupsVo groupsVo = new GroupsVo();

                groupsVo.setGroupList(res.substring(0, res.length()-1));

                return groupsVo;
            }
        }

        throw new ThirdApiException(ThirdApiExceptionEnum.QUERY_NO_RESULT);

    }
}
