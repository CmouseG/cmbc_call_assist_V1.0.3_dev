package com.guiji.fs.pojo;

import lombok.Data;

/**
 * FreeSWITCH全局变量
 */
@Data
public class GlobalVar {
    private String domain;
    private String internal_sip_port;
    private String internal_sip_port1;
    private String external_sip_port;
    private String gc_docker_ip;
    private String verto_wss_port;
    private String default_password;
}
