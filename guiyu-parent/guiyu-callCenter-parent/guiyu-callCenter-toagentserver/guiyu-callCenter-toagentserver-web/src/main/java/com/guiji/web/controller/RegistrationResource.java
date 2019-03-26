package com.guiji.web.controller;

import com.guiji.component.jurisdiction.Jurisdiction;
import com.guiji.component.result.Result;
import com.guiji.config.ErrorConstant;
import com.guiji.service.RegistrationService;
import com.guiji.web.request.RegistrationRequest;
import com.guiji.web.response.Paging;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    @Jurisdiction("callCenter_checklist_defquery")
    @RequestMapping(path = "/registrations", method = RequestMethod.GET)
    public Result.ReturnData<Paging> getRegistrations(@RequestParam(value = "pageNo", defaultValue = "0") Integer page,
                                                      @RequestParam(value = "pageSize", defaultValue = "10") Integer size,
                                                      @RequestHeader int authLevel,
                                                      @RequestHeader Long userId,
                                                      @RequestHeader String orgCode) {
        log.info("收到获取所有登记信息的请求pageNo:[{}],pageSize:[{}]，authLevel[{}],userId[{}],orgCode[{}]",page,size,authLevel,userId,orgCode);
        Paging paging = null;
        try {
            paging = registrationService.getRegistrations(page,size,authLevel,userId,orgCode);
        } catch (Exception e) {
            log.warn("查询登记信息出现异常", e);
            if(e.getMessage().equals("0307014")){
                return Result.error("0307014");
            }
        }
        return Result.ok(paging);
    }

    /**
     * 删除登记信息
     *
     * @param regId
     * @return
     */
    @Jurisdiction("callCenter_checklist_delete")
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
    @Jurisdiction("callCenter_workPlatform_submit")
    @RequestMapping(path = "/registrations", method = RequestMethod.POST)
    public Result.ReturnData addRegistration(@RequestBody RegistrationRequest request,@RequestHeader Long userId) {
        log.info("收到新增登记信息请求:[{}]", request.toString());
        if(StringUtils.isBlank(request.getRecordId())){
            return Result.error("0307010");
        }
        try {
            registrationService.addRegistration(request, userId);
        } catch (Exception e) {
            log.warn("新增登记信息出现异常", e);
            if(e.getMessage().equals("0307013")){
                return Result.error("0307013");
            }else if(e.getMessage().equals("0307014")){
                return Result.error("0307014");
            }
        }
        return Result.ok();
    }

    /**
     * 修改登记信息
     *
     * @param regId
     * @param request
     * @return
     */
    @Jurisdiction("callCenter_checklist_edit")
    @RequestMapping(path = "/registrations/{regId}", method = RequestMethod.PUT)
    public Result.ReturnData updateRegistration(@PathVariable String regId, @RequestBody RegistrationRequest request,@RequestHeader Long userId) {
        log.info("收到更新登记信息请求regId:[{}],RegistrationRequest:[{}]",regId, request.toString());
        try {
            registrationService.updateRegistration(regId, request, userId);
        } catch (Exception e) {
            log.warn("更新登记信息出现异常", e);
            if(e.getMessage().equals("0307014")){
                return Result.error("0307014");
            }
        }
        return Result.ok();
    }
}
