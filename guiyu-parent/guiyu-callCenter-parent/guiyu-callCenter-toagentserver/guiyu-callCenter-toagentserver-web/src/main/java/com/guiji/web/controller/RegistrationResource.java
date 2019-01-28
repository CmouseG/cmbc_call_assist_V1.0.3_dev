package com.guiji.web.controller;

import com.guiji.callcenter.dao.entity.Agent;
import com.guiji.component.result.Result;
import com.guiji.config.ErrorConstant;
import com.guiji.entity.CustomSessionVar;
import com.guiji.service.RegistrationService;
import com.guiji.web.request.RegistrationRequest;
import com.guiji.web.response.Paging;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Slf4j
@RestController
@RequestMapping(value = "/rs")
public class RegistrationResource {
    @Autowired
    RegistrationService registrationService;

    /**
     * 获取所有的登记信息(根据userId)
     *
     * @return
     */
    @RequestMapping(path = "/registrations", method = RequestMethod.GET)
    public Result.ReturnData<Paging> getRegistrations(HttpSession session,
                                                      @RequestParam(value = "pageNo", defaultValue = "0") Integer page,
                                                      @RequestParam(value = "pageSize", defaultValue = "10") Integer size) {
        Agent agent = (Agent) session.getAttribute(CustomSessionVar.LOGIN_USER);
        log.info("收到获取所有登记信息的请求pageNo:[{}],pageSize:[{}]",page,size);
        Paging paging =  registrationService.getRegistrations(agent,page,size);
        return Result.ok(paging);
    }

    /**
     * 删除登记信息
     *
     * @param regId
     * @return
     */
    @RequestMapping(path = "/registrations/{regId}", method = RequestMethod.DELETE)
    public Result.ReturnData deleteRegistration(@PathVariable String regId) {
        log.info("收到删除登记信息请求regId:[{}]", regId);
        if(StringUtils.isBlank(regId)){
            return Result.error(ErrorConstant.ERROR_CODE_PARAM);
        }
        registrationService.deleteRegistration(regId);
        return Result.ok();
    }

    /**
     * 新增登记信息
     *
     * @param request
     * @return
     */
    @RequestMapping(path = "/registrations", method = RequestMethod.POST)
    public Result.ReturnData addRegistration(@RequestBody RegistrationRequest request, HttpSession session) {
        log.info("收到新增登记信息请求:[{}]", request.toString());
        Agent agent = (Agent) session.getAttribute(CustomSessionVar.LOGIN_USER);
        registrationService.addRegistration(request, agent);
        return Result.ok();
    }

    /**
     * 修改登记信息
     *
     * @param regId
     * @param request
     * @return
     */
    @RequestMapping(path = "/registrations/{regId}", method = RequestMethod.PUT)
    public Result.ReturnData updateRegistration(@PathVariable String regId, @RequestBody RegistrationRequest request, HttpSession session) {
        log.info("收到更新登记信息请求regId:[{}],RegistrationRequest:[{}]",regId, request.toString());
        Agent agent = (Agent) session.getAttribute(CustomSessionVar.LOGIN_USER);
        registrationService.updateRegistration(regId, request, agent);
        return Result.ok();
    }
}
