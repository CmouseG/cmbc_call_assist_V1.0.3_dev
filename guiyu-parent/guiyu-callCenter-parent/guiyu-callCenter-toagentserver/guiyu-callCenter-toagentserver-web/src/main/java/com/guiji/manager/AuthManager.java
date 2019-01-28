package com.guiji.manager;

import com.guiji.auth.api.IAuth;
import com.guiji.common.exception.GuiyuException;
import com.guiji.component.result.Result;
import com.guiji.config.ToagentserverException;
import com.guiji.entity.EUserRole;
import com.guiji.helper.RequestHelper;
import com.guiji.user.dao.entity.SysRole;
import com.guiji.user.dao.entity.SysUser;
import com.guiji.user.vo.SysUserVo;
import com.guiji.web.request.AgentRequest;
import com.guiji.web.request.CrmUserVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Auther: 魏驰
 * @Date: 2019/1/21 14:12
 * @Project：guiyu-parent
 * @Description:
 */
@Slf4j
@Component
public class AuthManager {
    @Autowired
    IAuth iAuth;

    /**
     * 判断用户名称是否存在
     * @param userName
     * @return
     */
    public boolean isUserNameExist(String userName){
        Result.ReturnData<Boolean> result = iAuth.checkUsernameIsExist(userName);
        return  result!=null && result.success && result.getBody();
    }

    /**
     * 同步用户,并返回生成的用户id
     * @param userName
     * @param password
     * @param creatorId
     * @return
     */
    public Long syncUser(String userName, String password, Long creatorId){
        log.info("开始调用auth进行用户同步，userName[{}], password[{}], creatorId[{}]", userName, password, creatorId);
        try {
            Result.ReturnData<SysUser> response= RequestHelper.loopRequest(new RequestHelper.RequestApi() {
                @Override
                public Result.ReturnData execute() {
                    return iAuth.insertCustmomService(userName, password, creatorId);
                }

                @Override
                public void onErrorResult(Result.ReturnData result) {
                    log.warn("同步用户出现异常", result);
                }

                @Override
                public boolean trueBreakOnCode(String code) {
                    return false;
                }
            }, -1, 1, 1, 60, true);

            if(response.success){
                log.info("同步用户[{}]成功，返回id为[{}]", userName, response.getBody().getId());
                return response.getBody().getId();
            }else{
                log.info("同步用户[{}]失败，错误信息为[{}]", userName, response);
                return null;
            }
        } catch (Exception e) {
            log.warn("同步用户出现异常，结束", e);
        }

        return null;
    }

    /**
     * 获取CRM用户信息
     * @param userId
     * @return
     */
    public CrmUserVO getUser(String userId){
        CrmUserVO crmUserVO = new CrmUserVO();
        Result.ReturnData<SysUser> result = iAuth.getUserById(Long.parseLong(userId));
        if(result==null||result.getBody()==null){
            throw new GuiyuException(ToagentserverException.EXCP_TOAGENT__NONE_CRMUSER);
        }
        SysUser sysUser = result.getBody();
        crmUserVO.setUserId(sysUser.getId());
        crmUserVO.setAgentName(sysUser.getUsername());
        crmUserVO.setAgentPwd(sysUser.getPassword());
        crmUserVO.setCrmLoginId(sysUser.getUsername());
        crmUserVO.setOrgCode(sysUser.getOrgCode());

        Result.ReturnData<List<SysRole>> listReturnData = iAuth.getRoleByUserId(Long.parseLong(userId));
       if(listReturnData==null||listReturnData.getBody()==null){
           throw new GuiyuException(ToagentserverException.EXCP_TOAGENT_NOT_AGENTANDADMIN);
       }
        List<SysRole> roleList =  listReturnData.getBody();
        for (SysRole sysRole:roleList) {
            if(sysRole.getId()==3){
                crmUserVO.setUserRole(EUserRole.ADMIN);
            }
        }
        if(crmUserVO.getUserRole()==null){
            throw new GuiyuException(ToagentserverException.EXCP_TOAGENT_NOT_AGENTANDADMIN);
        }
       return crmUserVO;
    }
}
