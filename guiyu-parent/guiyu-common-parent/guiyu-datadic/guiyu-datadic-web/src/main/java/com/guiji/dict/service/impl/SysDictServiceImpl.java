package com.guiji.dict.service.impl;

import com.guiji.common.constant.RedisConstant;
import com.guiji.common.exception.GuiyuException;
import com.guiji.common.model.Page;
import com.guiji.dict.dao.SysDictMapper;
import com.guiji.dict.dao.entity.SysDict;
import com.guiji.dict.dao.entity.SysDictExample;
import com.guiji.dict.exception.GuiyuDatadicExceptionEnum;
import com.guiji.dict.service.SysDictService;
import com.guiji.utils.RedisUtil;
import com.guiji.utils.StrUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by ty on 2018/10/29.
 */
@Service
public class SysDictServiceImpl implements SysDictService {
    private final Logger logger = LoggerFactory.getLogger(SysDictServiceImpl.class);

    @Autowired
    private SysDictMapper sysDictMapper;
    @Autowired
    private RedisUtil redisUtil;
    @Override
    @Transactional
    public SysDict saveOrUpdateDict(SysDict sysDict,Long userId) {
        // 校验参数
        check(sysDict);
        if(sysDict != null) {
            Integer id = sysDict.getId();	//主键ID
            if(StrUtils.isEmpty(id)) {
                logger.info("新增字典");
                sysDict.setDelFlag(0);
                sysDict.setCreateTime(new Date());
                sysDict.setUpdateTime(new Date());
                sysDict.setCreateId(userId);
                sysDict.setUpdateId(userId);
                sysDictMapper.insert(sysDict);

                logger.info("新增字典成功"+sysDict);
            }else {
                logger.info("更新字典");
                sysDict.setUpdateTime(new Date());
                sysDict.setUpdateId(userId);
                sysDictMapper.updateByPrimaryKeySelective(sysDict);
                logger.info("更新字典成功"+sysDict);
            }
            //清除redis缓存
            redisUtil.del(RedisConstant.REDIS_DICT_PREFIX+sysDict.getDictType());
        }
        return sysDict;
    }

    @Override
    public List<SysDict> queryDictList(SysDict sysDict) {
        logger.info("查询字典，查询条件="+sysDict);
        List<SysDict> sysDictList = null;
        //先查缓存
        String redisKey = RedisConstant.REDIS_DICT_PREFIX + sysDict.getDictType();
        sysDictList = (List<SysDict>)redisUtil.get(redisKey);
        if (sysDictList == null || sysDictList.size()<1) {
            //缓存不存在则查询数据库
            SysDictExample example = this.getExampleByCondition(sysDict);
            sysDictList = sysDictMapper.selectByExample(example);
            //查完数据库保存到缓存
            redisUtil.set(redisKey,sysDictList);
        }

        return sysDictList;
    }

    @Override
    public List<SysDict> queryDictListLocal(SysDict sysDict) {
        logger.info("查询字典，查询条件="+sysDict);
        List<SysDict> sysDictList = null;
        //先查缓存
        String redisKey = RedisConstant.REDIS_DICT_PREFIX + sysDict.getDictType();
        sysDictList = (List<SysDict>)redisUtil.get(redisKey);
        if (sysDictList == null || sysDictList.size()<1) {
            //缓存不存在则查询数据库
            SysDictExample example = this.getExampleByConditionLocal(sysDict);
            sysDictList = sysDictMapper.selectByExample(example);
            //查完数据库保存到缓存
            redisUtil.set(redisKey,sysDictList);
        }

        return sysDictList;
    }

    @Override
    public Page<SysDict> queryDictPage(int pageNo, int pageSize, SysDict sysDict) {
        Page<SysDict> page = new Page<SysDict>();
        SysDictExample example = this.getExampleByConditionLocal(sysDict);
        if(example == null) example = new SysDictExample();
        int totalRecord = sysDictMapper.countByExample(example); //总数
        example.setLimitStart((pageNo-1)*pageSize);	//起始条数
        example.setLimitEnd(pageSize);	//结束条数
        example.setOrderByClause("create_time desc");
        //分页查询
        List<SysDict> list = sysDictMapper.selectByExample(example);
        if(list != null && !list.isEmpty()) {
            List<SysDict> rspSysDictList = new ArrayList<SysDict>();
            for(SysDict dict : list) {
                rspSysDictList.add(dict);
            }
            page.setRecords(rspSysDictList);
        }
        page.setPageNo(pageNo);
        page.setPageSize(pageSize);
        page.setTotal(totalRecord);
        return page;
    }

    @Override
    public SysDict get(int id) {
        return sysDictMapper.selectByPrimaryKey(id);
    }

    @Override
    public int delete(int id) {
        SysDict sysDict = sysDictMapper.selectByPrimaryKey(id);
        redisUtil.del(RedisConstant.REDIS_DICT_PREFIX+sysDict.getDictType());
        return sysDictMapper.deleteByPrimaryKey(id);
    }

    private SysDictExample getExampleByCondition(SysDict sysDict) {
        logger.info("查询字典，查询条件="+sysDict);
        if(sysDict != null) {
            Integer id = sysDict.getId();	//主键ID
            String dictKey = sysDict.getDictKey();	//标签名
            String dictValue = sysDict.getDictValue();	//数据值
            String dictType = sysDict.getDictType();	//类型
            String description = sysDict.getDescription();	//描述
            String remarks = sysDict.getRemarks();	//备注信息
            SysDictExample example = new SysDictExample();
            SysDictExample.Criteria criteria = example.createCriteria();
            if(id != null) {
                criteria.andIdEqualTo(id);
            }
            if(StrUtils.isNotEmpty(dictKey)) {
                criteria.andDictKeyEqualTo(dictKey);
            }
            if(StrUtils.isNotEmpty(dictValue)) {
                criteria.andDictValueEqualTo(dictValue);
            }
            if(StrUtils.isNotEmpty(dictType)) {
                criteria.andDictTypeEqualTo(dictType);
            }
            if(StrUtils.isNotEmpty(description)) {
                criteria.andDescriptionLike("%"+description+"%");
            }
            if(StrUtils.isNotEmpty(remarks)) {
                criteria.andRemarksLike("%"+remarks+"%");
            }
            return example;
        }else {
            logger.info("查询全部字典列表");
        }
        return null;
    }

    private SysDictExample getExampleByConditionLocal(SysDict sysDict) {
        logger.info("查询字典，查询条件="+sysDict);
        if(sysDict != null) {
            Integer id = sysDict.getId();	//主键ID
            String dictKey = sysDict.getDictKey();	//标签名
            String dictValue = sysDict.getDictValue();	//数据值
            String dictType = sysDict.getDictType();	//类型
            String description = sysDict.getDescription();	//描述
            String remarks = sysDict.getRemarks();	//备注信息
            SysDictExample example = new SysDictExample();
            SysDictExample.Criteria criteria = example.createCriteria();
            if(id != null) {
                criteria.andIdEqualTo(id);
            }
            if(StrUtils.isNotEmpty(dictKey)) {
                criteria.andDictKeyEqualTo(dictKey);
            }
            if(StrUtils.isNotEmpty(dictValue)) {
                criteria.andDictValueEqualTo(dictValue);
            }
            if(StrUtils.isNotEmpty(dictType)) {
                criteria.andDictTypeLike("%"+dictType+"%");
            }
            if(StrUtils.isNotEmpty(description)) {
                criteria.andDescriptionLike("%"+description+"%");
            }
            if(StrUtils.isNotEmpty(remarks)) {
                criteria.andRemarksLike("%"+remarks+"%");
            }
            return example;
        }else {
            logger.info("查询全部字典列表");
        }
        return null;
    }

    private void check(SysDict sysDict) {
        if (sysDict.getDictType() != null && sysDict.getDictType().length() > 20) {
            throw new GuiyuException(GuiyuDatadicExceptionEnum.DATADIC00020001.getErrorCode(),GuiyuDatadicExceptionEnum.DATADIC00020001.getMsg());
        }

        if (sysDict.getDictKey() != null && sysDict.getDictKey().length() > 20) {
            throw new GuiyuException(GuiyuDatadicExceptionEnum.DATADIC00020002.getErrorCode(),GuiyuDatadicExceptionEnum.DATADIC00020002.getMsg());
        }

        if (sysDict.getDictValue() != null && sysDict.getDictValue().length() > 255) {
            throw new GuiyuException(GuiyuDatadicExceptionEnum.DATADIC00020003.getErrorCode(),GuiyuDatadicExceptionEnum.DATADIC00020003.getMsg());
        }

        if (sysDict.getDescription() != null && sysDict.getDescription().length() > 255) {
            throw new GuiyuException(GuiyuDatadicExceptionEnum.DATADIC00020004.getErrorCode(),GuiyuDatadicExceptionEnum.DATADIC00020004.getMsg());
        }
    }
}
