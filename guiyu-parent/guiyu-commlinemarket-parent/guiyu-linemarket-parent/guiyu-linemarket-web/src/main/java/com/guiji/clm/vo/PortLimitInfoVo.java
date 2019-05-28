package com.guiji.clm.vo;

import lombok.Data;

/**
 * @Classname PortLimitVo
 * @Description TODO
 * @Date 2019/5/28 17:01
 * @Created by qinghua
 */
@Data
public class PortLimitInfoVo {

    private Integer id;

    /**
     * 周期，单位s
     */
    private Integer timeLength;

    /**
     * 上限值
     *     (1, "拨打次数"),
     *     (2, "接通次数"),
     *     (3, "接通时长分钟");
     */
    private Integer maxLimit;

    /**
     * 限制类型
     *     (1, "拨打次数"),
     *     (2, "接通次数"),
     *     (3, "接通时长分钟");
     */
    private Integer limitType;

}
