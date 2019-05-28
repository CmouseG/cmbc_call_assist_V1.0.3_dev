package com.guiji.clm.ro;

import lombok.Data;

/**
 * @Classname PortLimitInfoRo
 * @Description TODO
 * @Date 2019/5/28 17:23
 * @Created by qinghua
 */
@Data
public class PortLimitInfoRo {

    /**
     * 编辑时用的id
     */
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
