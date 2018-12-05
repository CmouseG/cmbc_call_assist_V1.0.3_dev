package com.guiji.guiyu.sysoperalog.dao;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.guiji.guiyu.sysoperalog.dao.entity.SysUserAction;
import com.guiji.guiyu.sysoperalog.dao.entity.SysUserActionExample;

public interface SysUserActionMapper {
    int countByExample(SysUserActionExample example);

    int deleteByExample(SysUserActionExample example);

    int deleteByPrimaryKey(Long id);

    int insert(SysUserAction record);

    int insertSelective(SysUserAction record);

    List<SysUserAction> selectByExampleWithBLOBs(SysUserActionExample example);

    List<SysUserAction> selectByExample(SysUserActionExample example);

    SysUserAction selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") SysUserAction record, @Param("example") SysUserActionExample example);

    int updateByExampleWithBLOBs(@Param("record") SysUserAction record, @Param("example") SysUserActionExample example);

    int updateByExample(@Param("record") SysUserAction record, @Param("example") SysUserActionExample example);

    int updateByPrimaryKeySelective(SysUserAction record);

    int updateByPrimaryKeyWithBLOBs(SysUserAction record);

    int updateByPrimaryKey(SysUserAction record);

	// 根据条件查询
	List<SysUserAction> getSysUserActionByCondition(@Param("startTime") Date startTime, @Param("endTime") Date endTime,
			@Param("limitStart") Integer limitStart, @Param("limitEnd") Integer limitEnd);

}