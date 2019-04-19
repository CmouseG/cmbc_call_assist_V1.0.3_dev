package com.guiji.billing.service.impl;

import com.guiji.auth.api.IAuth;
import com.guiji.billing.service.GetApiService;
import com.guiji.billing.utils.ResHandler;
import com.guiji.user.dao.entity.SysOrganization;
import com.guiji.utils.JsonUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GetApiServiceImpl implements GetApiService {

    private Logger logger = LoggerFactory.getLogger(GetApiServiceImpl.class);

    @Autowired
    private IAuth iAuth;

    /**
     * 根据用户ID查询企业组织
     * @param userId
     * @return
     */
    @Override
    public SysOrganization getOrgByUserId(String userId) {
        logger.info("根据用户ID:{}查询企业组织",userId);
        if(!StringUtils.isEmpty(userId)) {
            //获取企业组织, code后面不带点.
            SysOrganization org = ResHandler.getResObj(iAuth.getOrgByUserId(Long.valueOf(userId)));
            logger.info("根据用户ID:{}查询企业组织:{}", userId, null !=org?JsonUtils.bean2Json(org):org);
            return org;
        }else{
            return null;
        }
    }
}
