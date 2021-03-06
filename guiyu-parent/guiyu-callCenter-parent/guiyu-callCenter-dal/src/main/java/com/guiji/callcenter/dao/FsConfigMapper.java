package com.guiji.callcenter.dao;


import com.guiji.callcenter.dao.entity.FsConfig;
import com.guiji.callcenter.dao.entity.FsConfigExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface FsConfigMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table fs_config
     *
     * @mbggenerated
     */
    int countByExample(FsConfigExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table fs_config
     *
     * @mbggenerated
     */
    int deleteByExample(FsConfigExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table fs_config
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table fs_config
     *
     * @mbggenerated
     */
    int insert(FsConfig record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table fs_config
     *
     * @mbggenerated
     */
    int insertSelective(FsConfig record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table fs_config
     *
     * @mbggenerated
     */
    List<FsConfig> selectByExample(FsConfigExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table fs_config
     *
     * @mbggenerated
     */
    FsConfig selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table fs_config
     *
     * @mbggenerated
     */
    int updateByExampleSelective(@Param("record") FsConfig record, @Param("example") FsConfigExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table fs_config
     *
     * @mbggenerated
     */
    int updateByExample(@Param("record") FsConfig record, @Param("example") FsConfigExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table fs_config
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(FsConfig record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table fs_config
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(FsConfig record);
}