package com.guiji.web.controller;

import com.guiji.component.jurisdiction.Jurisdiction;
import com.guiji.service.*;
import com.guiji.web.request.ExportcalldetailsVO;
import com.guiji.web.request.ExportregistrationsVO;
import com.guiji.web.response.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@Slf4j
@RestController
public class ExportController {
    @Autowired
    RegistrationService registrationService;
    @Autowired
    CallOutPlanService callOutPlanService;
    @Autowired
    AgentService agentService;
    @Autowired
    PhoneService phoneService;
    @Autowired
    CallDetailService callDetailService;

    /**
     * 批量导出指定的登记历史
     *
     * @return
     */
    @Jurisdiction("callCenter_checklist_exportTable")
    @RequestMapping(path = "/exportregistrations", method = RequestMethod.POST)
    public ApiResponse getExportRegistrations(@RequestBody ExportregistrationsVO vo, HttpServletResponse response) {
        log.info("收到批量导出指定的登记历史的请求");
        registrationService.getExportRegistrations(vo.getRegIds(), vo.getAgentId(),vo.getAuthLevel(),vo.getOrgCode(), response);
        return null;
    }

    /**
     * 根据callId获取对话详情
     *
     * @return
     */
    @RequestMapping(path = "/exportcalldetails", method = RequestMethod.POST)
    public ApiResponse getExportCalldetails(@RequestBody ExportcalldetailsVO vo, HttpServletResponse response) {
        log.info("收到根据callId获取对话详情的请求");
        callDetailService.getExportCalldetails(vo.getCallId(), response);
        return null;
    }

    /**
     * 初始化测试
     *
     * @return
     */
    @RequestMapping(path = "/init", method = RequestMethod.GET)
    public ApiResponse vertoStatus() {
        agentService.initCallcenter();
        return null;
    }
}
