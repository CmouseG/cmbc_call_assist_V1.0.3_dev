package com.guiji.clm.ro;

import lombok.Data;

import java.util.List;

/**
 * @Classname OperPortLimitRo
 * @Description TODO
 * @Date 2019/5/28 17:47
 * @Created by qinghua
 */

@Data
public class OperPortLimitRo {

    private Integer userId;

    private Integer portId;

    private List<PortLimitInfoRo> roList;

}
