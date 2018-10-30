package com.guiji.dict.controller;

import com.guiji.common.result.Result;
import com.guiji.dict.api.IDictControllerRemote;
import com.guiji.dict.dao.entity.SysDict;
import com.guiji.dict.service.SysDictService;
import com.guiji.dict.service.impl.SysDictServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by ty on 2018/10/29.
 */
@RestController
public class DictControllerRemote implements IDictControllerRemote {
    Logger logger = LoggerFactory.getLogger(IDictControllerRemote.class);

    @Autowired
    private SysDictService sysDictService;
    @Override
    public Result.ReturnData<List<SysDict>> getDictByType(String dictType) {
        SysDict sysDict = new SysDict();
        sysDict.setDictType(dictType);
        List<SysDict> data = sysDictService.queryDictList(sysDict);
        return Result.ok(data);
    }

    @Override
    public Result.ReturnData<List<SysDict>> getDictValue(String dictType, String dictKey) {
        SysDict sysDict = new SysDict();
        sysDict.setDictKey(dictKey);
        sysDict.setDictType(dictType);
        List<SysDict> data = sysDictService.queryDictList(sysDict);
        return Result.ok(data);
    }
}
