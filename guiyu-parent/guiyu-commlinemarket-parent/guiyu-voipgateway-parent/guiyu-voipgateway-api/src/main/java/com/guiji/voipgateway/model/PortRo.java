package com.guiji.voipgateway.model;

import lombok.Data;

/**
 * @Classname PortRo
 * @Description 端口信息
 * @Date 2019/5/13 15:33
 * @Created by qinghua
 */
@Data
public class PortRo {

    /**
     * companyId
     */
    private Integer companyId;

    /**
     * 网关端口
     */
    private Integer portNo;

    /**
     * 网关设备id
     */
    private Integer devId;

    /**
     * 网关设备品牌
     */
    private String gwBrand;


    private String ip;

}
