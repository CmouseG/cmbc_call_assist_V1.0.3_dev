package com.guiji.dict.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * Created by ty on 2018/10/26.
 */
@ApiModel(value="DictQueryCondition",description="字典查询条件")
public class DictQueryCondition implements Serializable{
    private static final long serialVersionUID = -1L;

    @ApiModelProperty(value="字典ID")
    private Long id;

    @ApiModelProperty(value="字典key")
    private String dictKey;

    @ApiModelProperty(value="字典value")
    private String dictValue;

    @ApiModelProperty(value="字典type")
    private String dictType;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

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

    @Override
    public String toString() {
        return "DictQueryCondition{" +
                "id=" + id +
                ", dictKey='" + dictKey + '\'' +
                ", dictValue='" + dictValue + '\'' +
                ", dictType='" + dictType + '\'' +
                '}';
    }
}
