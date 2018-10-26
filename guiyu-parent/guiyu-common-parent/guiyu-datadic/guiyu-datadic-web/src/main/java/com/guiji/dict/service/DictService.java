package com.guiji.dict.service;

import com.guiji.common.model.Page;
import com.guiji.dict.dao.entity.SysDict;
import com.guiji.dict.model.DictQueryCondition;

import java.util.List;

/**
 * Created by ty on 2018/10/26.
 */
public interface DictService {
    public List<String> findTypeList();

    public String findTypeValue(SysDict sysDict);

    public void save(SysDict sysDict);

    public void delete(Long id);

    public String getLabel(SysDict sysDict);
}
