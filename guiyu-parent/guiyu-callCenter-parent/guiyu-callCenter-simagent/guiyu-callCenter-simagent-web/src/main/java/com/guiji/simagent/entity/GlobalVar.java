package com.guiji.simagent.entity;


/**
 * FreeSWITCH全局变量
 */

public class GlobalVar {
    private String domain;
    private String internal_sip_port;
    private String internal_sip_port1;
    private String external_sip_port;
    private String gc_docker_ip;

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getInternal_sip_port() {
        return internal_sip_port;
    }

    public void setInternal_sip_port(String internal_sip_port) {
        this.internal_sip_port = internal_sip_port;
    }

    public String getInternal_sip_port1() {
        return internal_sip_port1;
    }

    public void setInternal_sip_port1(String internal_sip_port1) {
        this.internal_sip_port1 = internal_sip_port1;
    }

    public String getExternal_sip_port() {
        return external_sip_port;
    }

    public void setExternal_sip_port(String external_sip_port) {
        this.external_sip_port = external_sip_port;
    }

    public String getGc_docker_ip() {
        return gc_docker_ip;
    }

    public void setGc_docker_ip(String gc_docker_ip) {
        this.gc_docker_ip = gc_docker_ip;
    }
}
