package com.guiji.dispatch.controller;

import com.guiji.dispatch.dto.DispatchRobotOpDto;
import com.guiji.dispatch.dto.QueryExportFileRecordDto;
import com.guiji.dispatch.entity.ExportFileRecord;
import com.guiji.dispatch.impl.ExportFileServiceImpl;
import com.guiji.dispatch.model.ExportFileDto;
import com.guiji.dispatch.pushcallcenter.CallBack4MQListener;
import com.guiji.dispatch.pushcallcenter.IPushPhonesHandler;
import com.guiji.dispatch.service.IExportFileService;
import com.guiji.dispatch.service.IResourcePoolService;
import com.guiji.dispatch.sys.ResultPage;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping(value="/dispatch/test")
public class TestController {

    private Logger logger = LoggerFactory.getLogger(TestController.class);

    @Autowired
    private IResourcePoolService resourcePoolService;

    @ApiOperation(value="", notes="")
    @RequestMapping(value = "/testDistributeByUser", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public boolean testDistributeByUser(@RequestBody DispatchRobotOpDto opRobotDto){
        boolean bool =  false;
        try {
            bool = resourcePoolService.distributeByUser();
        }catch(Exception e){
            logger.error("testDistributeByUser>>>>>>>>>>>>>",e);
        }
        return bool;
    }

    @Autowired
    private IPushPhonesHandler iPushPhonesHandler;

    @ApiOperation(value="", notes="")
    @RequestMapping(value = "/pushHandler", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public void pushHandler(@RequestBody DispatchRobotOpDto opRobotDto){
        boolean bool =  false;
        try {
            iPushPhonesHandler.pushHandler();
        }catch(Exception e){
            logger.error("pushHandler>>>>>>>>>>>>>",e);
        }
    }

    @Autowired
    private CallBack4MQListener callBack4MQListener;

    @ApiOperation(value="", notes="")
    @RequestMapping(value = "/callBack4MQListener", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public void callBack4MQListener(@RequestBody DispatchRobotOpDto opRobotDto){
        boolean bool =  false;
        try {
            String str = "{\"lineId\":731,\"planuuid\":\"bfd2a388cf49445ba7073689347b3f00\",\"tempId\":\"111_35227_en\",\"userId\":64}";
            callBack4MQListener.process(str, null, null);
        }catch(Exception e){
            logger.error("pushHandler>>>>>>>>>>>>>",e);
        }
    }


    @Autowired
    private IExportFileService exportFileService;

    @ApiOperation(value="新增导出记录", notes="新增导出记录")
    @RequestMapping(value = "/addExportFile", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public ExportFileRecord addExportFile(@RequestBody ExportFileDto exportFileDto){
        ExportFileRecord record = exportFileService.addExportFile(exportFileDto);
        return record;
    }


    @ApiOperation(value="", notes="")
    @RequestMapping(value = "/updExportFileStatus", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public boolean updExportFileStatus(@RequestParam(name = "recordId") String recordId,
                                                @RequestParam(name = "status") Integer status){
        boolean bool = exportFileService.updExportFileStatus(recordId, status);
        return bool;
    }

    @ApiOperation(value="", notes="")
    @RequestMapping(value = "/queryExportFileRecordByPage", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public ResultPage<ExportFileRecord> queryExportFileRecordByPage(@RequestBody QueryExportFileRecordDto queryDto){
        ResultPage<ExportFileRecord> page = new ResultPage<ExportFileRecord>(queryDto);
        List<ExportFileRecord> list = exportFileService.queryExportFileRecordByPage(queryDto, page);
        page.setList(list);
        page.setTotalItemAndPageNumber(exportFileService.queryExportFileRecordCount(queryDto));
        return page;
    }
}
