package com.guiji.dict.controller;

import com.guiji.common.model.Page;
import com.guiji.component.aspect.SysOperaLog;
import com.guiji.dict.dao.entity.SysDict;
import com.guiji.dict.service.SysDictService;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by ty on 2018/10/29.
 */
@RestController
public class SysDictLocalController {
    Logger logger = LoggerFactory.getLogger(SysDictLocalController.class);

    @Autowired
    private SysDictService sysDictService;

    @SysOperaLog(operaTarget = "数据字典", operaType = "新增/修改")
    @ApiOperation(value="新增/更新字典信息", notes="根据是否有ID新增或者更新字典信息")
    @RequestMapping(value = "/saveOrUpdateDict", method = RequestMethod.POST)
    public SysDict saveOrUpdateDict(SysDict sysDict,@RequestHeader Long userId) {
        return sysDictService.saveOrUpdateDict(sysDict,userId);
    }

    @SysOperaLog(operaTarget = "数据字典", operaType = "查询")
    @ApiOperation(value="查询字典信息", notes="根据查询条件查询字典信息")
    @RequestMapping(value = "/queryDictList", method = RequestMethod.POST)
    public List<SysDict> queryDictList(SysDict sysDict) {
        return sysDictService.queryDictList(sysDict);
    }

    @SysOperaLog(operaTarget = "数据字典", operaType = "分页查询")
    @ApiOperation(value="分页字典信息", notes="根据查询条件分页查询字典信息")
    @RequestMapping(value = "/queryDictPage", method = RequestMethod.POST)
    public Page<SysDict> queryDictPage(int pageNo, int pageSize, SysDict sysDict) {
        return sysDictService.queryDictPage(pageNo, pageSize, sysDict);
    }

    @SysOperaLog(operaTarget = "数据字典", operaType = "根据ID查询字典信息")
    @ApiOperation(value="获取字典信息", notes="根据ID查询字典信息")
    @RequestMapping(value = "/get", method = RequestMethod.POST)
    public SysDict get(int id) {
        return sysDictService.get(id);
    }

    @SysOperaLog(operaTarget = "数据字典", operaType = "删除")
    @ApiOperation(value="删除字典信息", notes="根据ID删除字典信息")
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public int delete(int id) {
        return sysDictService.delete(id);
    }
}
