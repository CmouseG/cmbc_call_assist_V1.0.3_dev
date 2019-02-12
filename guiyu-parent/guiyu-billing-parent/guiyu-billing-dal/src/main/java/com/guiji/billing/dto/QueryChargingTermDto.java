package com.guiji.billing.dto;

import com.guiji.billing.sys.PageDto;

public class QueryChargingTermDto extends PageDto {

    private Integer id;

    private Integer type;

    private Integer chargingType;

    private Integer status;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getChargingType() {
        return chargingType;
    }

    public void setChargingType(Integer chargingType) {
        this.chargingType = chargingType;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
