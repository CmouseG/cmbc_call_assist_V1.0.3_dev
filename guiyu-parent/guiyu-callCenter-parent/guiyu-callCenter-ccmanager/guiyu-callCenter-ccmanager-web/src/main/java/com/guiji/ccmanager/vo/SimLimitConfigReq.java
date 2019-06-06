package com.guiji.ccmanager.vo;

import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;

/**
 * author:liyang
 * Date:2019/5/30 11:03
 * Description:
 */
@Data
public class SimLimitConfigReq {

    @NotNull(message = "lineId不能为空")
    private Integer lineId;

    @NotNull(message = "callCountTop不能为空")
    private Integer callCountTop;

    @NotNull(message = "callCountPeriod不能为空")
    private Integer callCountPeriod;

    @NotNull(message = "connectCountTop不能为空")
    private Integer connectCountTop;

    @NotNull(message = "connectCountPeriod不能为空")
    private Integer connectCountPeriod;

    @NotNull(message = "connectTimeTop不能为空")
    private Integer connectTimeTop;

    @NotNull(message = "connectTimePeriod不能为空")
    private Integer connectTimePeriod;

}
