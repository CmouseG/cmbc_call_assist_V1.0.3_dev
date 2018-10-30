package com.guiji.dict.controller;

import com.guiji.common.model.Page;
import com.guiji.dict.dao.entity.SysDict;
import com.guiji.dict.service.SysDictService;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by ty on 2018/10/29.
 */
@RestController
public class DictControllerLocal {
    Logger logger = LoggerFactory.getLogger(DictControllerLocal.class);

    @Autowired
    private SysDictService sysDictService;

    @ApiOperation(value="新增/更新字典信息", notes="根据是否有ID新增或者更新字典信息")
    @RequestMapping(value = "/saveOrUpdateDict", method = RequestMethod.POST)
    public SysDict saveOrUpdateDict(SysDict sysDict) {
        return sysDictService.saveOrUpdateDict(sysDict);
    }

    @ApiOperation(value="查询字典信息", notes="根据查询条件查询字典信息")
    @RequestMapping(value = "/queryDictList", method = RequestMethod.POST)
    public List<SysDict> queryDictList(SysDict sysDict) {
        return sysDictService.queryDictList(sysDict);
    }

    @ApiOperation(value="分页字典信息", notes="根据查询条件分页查询字典信息")
    @RequestMapping(value = "/queryDictPage", method = RequestMethod.POST)
    public Page<SysDict> queryDictPage(int pageNo, int pageSize, SysDict sysDict) {
        return sysDictService.queryDictPage(pageNo, pageSize, sysDict);
    }

    @ApiOperation(value="获取字典信息", notes="根据ID查询字典信息")
    @RequestMapping(value = "/get", method = RequestMethod.POST)
    public SysDict get(Long id) {
        return sysDictService.get(id);
    }

    @ApiOperation(value="删除字典信息", notes="根据ID删除字典信息")
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public int delete(Long id) {
        return sysDictService.delete(id);
    }
}
