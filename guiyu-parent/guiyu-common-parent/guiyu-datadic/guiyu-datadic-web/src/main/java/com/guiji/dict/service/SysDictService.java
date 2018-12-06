package com.guiji.dict.service;

import com.guiji.common.model.Page;
import com.guiji.dict.dao.entity.SysDict;

import java.util.List;

/**
 * Created by ty on 2018/10/29.
 */
public interface SysDictService {
    public SysDict saveOrUpdateDict(SysDict sysDict,Long userId);

    public List<SysDict> queryDictList(SysDict sysDict);

    public Page<SysDict> queryDictPage(int pageNo, int pageSize, SysDict sysDict);

    public SysDict get(Long id);

    public int delete(Long id);
}
