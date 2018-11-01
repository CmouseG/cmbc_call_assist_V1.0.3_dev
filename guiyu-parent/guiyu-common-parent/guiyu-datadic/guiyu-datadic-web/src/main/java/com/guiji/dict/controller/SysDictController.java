package com.guiji.dict.controller;

import com.guiji.common.result.Result;
import com.guiji.common.result.Result.ReturnData;
import com.guiji.dict.api.ISysDict;
import com.guiji.dict.dao.entity.SysDict;
import com.guiji.dict.service.SysDictService;
import com.guiji.dict.vo.SysDictVO;
import com.guiji.utils.BeanUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Override
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
}
