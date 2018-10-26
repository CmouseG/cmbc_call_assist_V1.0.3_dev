package com.guiji.dict.service.impl;

import com.guiji.common.model.Page;
import com.guiji.dict.dao.SysDictMapper;
import com.guiji.dict.dao.entity.SysDict;
import com.guiji.dict.model.DictQueryCondition;
import com.guiji.dict.model.DictVo;
import com.guiji.dict.service.DictService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ty on 2018/10/26.
 */
@Service
public class DictServiceImpl implements DictService {
    @Autowired
    private SysDictMapper sysDictMapper;


    /**
     * 查询字段类型列表
     * @return
     */
    public List<String> findTypeList(){
        return sysDictMapper.findTypeList(new SysDict());
    }

    public String findTypeValue(SysDict dict){
        return sysDictMapper.findTypeLabel(dict);
    }

    @Transactional(readOnly = false)
    public void save(SysDict dict) {
        sysDictMapper.insert(dict);
    }

    @Transactional(readOnly = false)
    public void delete(Long id) {
        sysDictMapper.deleteByPrimaryKey(id);
    }

    public String getLabel(SysDict dict){
        return sysDictMapper.getLabel(dict);
    }
}
