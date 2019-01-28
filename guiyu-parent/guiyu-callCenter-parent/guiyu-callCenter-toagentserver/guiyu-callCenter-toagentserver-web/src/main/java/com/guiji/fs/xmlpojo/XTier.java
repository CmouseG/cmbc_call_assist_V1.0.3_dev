package com.guiji.fs.xmlpojo;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@Data
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "tier")
public class XTier {
    @XmlAttribute
    private String agent;

    @XmlAttribute
    private String queue;

    @XmlAttribute
    private String level = "1";

    @XmlAttribute
    private String position = "1";
}
