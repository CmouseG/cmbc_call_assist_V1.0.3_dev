package com.guiji.callcenter.fsmanager.entity;

import lombok.Data;

import javax.xml.bind.annotation.*;
import java.util.LinkedHashSet;

@Data
@XmlRootElement(name = "include")
@XmlAccessorType(XmlAccessType.FIELD)
public class GatewayVO {
    @XmlElement(name = "gateway")
    Gateway gateway;

    @Data
    @XmlAccessorType(XmlAccessType.FIELD)
    public static class Gateway{
        @XmlAttribute(name = "name")
        String name;
        @XmlElement(name = "param")
        LinkedHashSet<Param> param;
    }

    @Data
    @XmlAccessorType(XmlAccessType.FIELD)
    public static class Param {
        @XmlAttribute(name = "name")
        String name;
        @XmlAttribute(name = "value")
        String value;
    }
}
