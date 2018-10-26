package com.guiji.dict.model;

import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

/**
 * Created by ty on 2018/10/26.
 */
public class DictVo {
    @ApiModelProperty(value="id")
    private Long id;
    @ApiModelProperty(value="字典key")
    private String dictKey;
    @ApiModelProperty(value="字典值")
    private String dictValue;
    @ApiModelProperty(value="字典类型")
    private String dictType;
    @ApiModelProperty(value="描述")
    private String description;
    @ApiModelProperty(value="父id")
    private Long pid;
    @ApiModelProperty(value="备注")
    private String remarks;
    @ApiModelProperty(value="创建时间")
    private Date createTime;
    @ApiModelProperty(value="最后更新时间")
    private Date updateTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDictKey() {
        return dictKey;
    }

    public void setDictKey(String dictKey) {
        this.dictKey = dictKey;
    }

    public String getDictValue() {
        return dictValue;
    }

    public void setDictValue(String dictValue) {
        this.dictValue = dictValue;
    }

    public String getDictType() {
        return dictType;
    }

    public void setDictType(String dictType) {
        this.dictType = dictType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getPid() {
        return pid;
    }

    public void setPid(Long pid) {
        this.pid = pid;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        return "DictVo{" +
                "id=" + id +
                ", dictKey='" + dictKey + '\'' +
                ", dictValue='" + dictValue + '\'' +
                ", dictType='" + dictType + '\'' +
                ", description='" + description + '\'' +
                ", pid=" + pid +
                ", remarks='" + remarks + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }
}
