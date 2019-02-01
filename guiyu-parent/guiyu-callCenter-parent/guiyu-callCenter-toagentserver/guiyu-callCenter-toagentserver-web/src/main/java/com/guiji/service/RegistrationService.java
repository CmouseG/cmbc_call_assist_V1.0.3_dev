package com.guiji.service;

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
    Paging getRegistrations(Agent agent, Integer page, Integer size);

    void deleteRegistration(String regId);

    void updateRegistration(String regId, RegistrationRequest request, Agent agent);

    void addRegistration(RegistrationRequest request, Agent agent) throws Exception;

    void getExportRegistrations(String regIds, Long userId, HttpServletResponse response);
}
