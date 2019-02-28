package com.guiji.dict.controller;

import com.guiji.component.aspect.SysOperaLog;
import com.guiji.component.result.Result;
import com.guiji.component.result.Result.ReturnData;
import com.guiji.dict.api.ISysDict;
import com.guiji.dict.dao.entity.SysDict;
import com.guiji.dict.service.SysDictService;
import com.guiji.dict.vo.SysDictVO;
import com.guiji.utils.BeanUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ty on 2018/10/29.
 */
@RestController
public class SysDictController implements ISysDict {
    Logger logger = LoggerFactory.getLogger(ISysDict.class);

    @Autowired
    private SysDictService sysDictService;
    @Override
    @SysOperaLog(operaTarget = "数据字典", operaType = "根据名称查询数据字典")
    public ReturnData<List<SysDictVO>> getDictByType(String dictType) {
        List<SysDictVO> result = new ArrayList<SysDictVO>();
        SysDict sysDictQ = new SysDict();
        sysDictQ.setDictType(dictType);
        List<SysDict> list = sysDictService.queryDictList(sysDictQ);
        for (SysDict sysDict : list) {
            SysDictVO sysDictVO = new SysDictVO();
            BeanUtil.copyProperties(sysDict,sysDictVO);
            result.add(sysDictVO);
        }
        return Result.ok(result);
    }

    @SysOperaLog(operaTarget = "数据字典", operaType = "根据名称和key查询数据字典")
    public ReturnData<List<SysDictVO>> getDictValue(String dictType, String dictKey) {
        List<SysDictVO> result = new ArrayList<SysDictVO>();
        SysDict sysDictQ = new SysDict();
        sysDictQ.setDictType(dictType);
        sysDictQ.setDictKey(dictKey);
        List<SysDict> list = sysDictService.queryDictList(sysDictQ);
        for (SysDict sysDict : list) {
            SysDictVO sysDictVO = new SysDictVO();
            BeanUtil.copyProperties(sysDict,sysDictVO);
            result.add(sysDictVO);
        }
        return Result.ok(result);
    }

    @Override
    @SysOperaLog(operaTarget = "数据字典", operaType = "根据名称和key查询数据字典")
    public ReturnData<List<SysDictVO>> getDictValueByTypeKey(@RequestParam("dictType")String dictType, @RequestParam("dictKey")String dictKey) {
        List<SysDictVO> result = new ArrayList<SysDictVO>();
        SysDict sysDictQ = new SysDict();
        sysDictQ.setDictType(dictType);
        sysDictQ.setDictKey(dictKey);
        List<SysDict> list = sysDictService.queryDictList(sysDictQ);
        for (SysDict sysDict : list) {
            SysDictVO sysDictVO = new SysDictVO();
            BeanUtil.copyProperties(sysDict,sysDictVO);
            result.add(sysDictVO);
        }
        return Result.ok(result);
    }
}
