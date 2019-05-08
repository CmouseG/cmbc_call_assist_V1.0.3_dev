package com.guiji.callcenter.fsmanager.controller;

import com.guiji.callcenter.fsmanager.entity.FsConfigWebVO;
import com.guiji.callcenter.fsmanager.entity.PagingVO;
import com.guiji.callcenter.fsmanager.service.FsConfigService;
import com.guiji.component.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
public class FsConfigController {
@Autowired
    FsConfigService fsConfigService;

    @RequestMapping(path = "/fsconfig", method = RequestMethod.POST)
    public Result.ReturnData addFsConfig(@RequestBody FsConfigWebVO fsConfigWebVO){
        log.info("收到新增配置文件请求FsConfigWebVO:[{}]",fsConfigWebVO);
        fsConfigService.addFsConfig(fsConfigWebVO);
        return Result.ok();
    }
    @RequestMapping(path = "/fsconfig/{id}", method = RequestMethod.DELETE)
    public Result.ReturnData delFsConfig(@PathVariable int id){
        log.info("收到删除配置文件请求id:[{}]",id);
        fsConfigService.delFsConfig(id);
        return Result.ok();
    }
    @RequestMapping(path = "/fsconfig/{id}", method = RequestMethod.PUT)
    public Result.ReturnData updateFsConfig(@PathVariable int id, @RequestBody FsConfigWebVO fsConfigWebVO){
        log.info("收到修改配置文件请求id[{}],FsConfigWebVO:[{}]",id,fsConfigWebVO);
        fsConfigService.updateFsConfig(id,fsConfigWebVO);
        return Result.ok();
    }
    @RequestMapping(path = "/fsconfig", method = RequestMethod.GET)
    public Result.ReturnData<PagingVO> getFsConfig(@RequestParam(value = "module", defaultValue = "") String module,
                                                   @RequestParam(value = "roleId", defaultValue = "") String roleId,
                                                   @RequestParam(value = "busKey", defaultValue = "") String busKey,
                                                   @RequestParam(value = "pageNo", defaultValue = "0") Integer page,
                                                   @RequestParam(value = "pageSize", defaultValue = "10") Integer size){
        log.info("收到查询配置文件列表请求module:[{}],roleId[{}],busKey[{}],pageNo:[{}],pageSize:[{}]",module,roleId,busKey,page,size);
        PagingVO paging =  fsConfigService.getFsConfig(module,roleId,busKey,page,size);
        return Result.ok(paging);
    }
}
