package com.guiji.service;

import com.github.pagehelper.PageInfo;
import com.guiji.callcenter.dao.entity.Agent;
import com.guiji.web.request.RegistrationRequest;
import com.guiji.web.response.Paging;

import javax.servlet.http.HttpServletResponse;

/**
 * @Auther: 魏驰
 * @Date: 2018/12/17 14:32
 * @Project：ccserver
 * @Description:
 */
public interface RegistrationService {
    Paging getRegistrations(Integer page, Integer size,int authLevel,Long customerId,String orgCode)throws Exception;

    void deleteRegistration(String regId);

    void updateRegistration(String regId, RegistrationRequest request, Long customerId)throws Exception;

    void addRegistration(RegistrationRequest request, Long customerId) throws Exception;

    void getExportRegistrations(String regIds, Long userId, int authLevel ,String orgCode,HttpServletResponse response);
}
