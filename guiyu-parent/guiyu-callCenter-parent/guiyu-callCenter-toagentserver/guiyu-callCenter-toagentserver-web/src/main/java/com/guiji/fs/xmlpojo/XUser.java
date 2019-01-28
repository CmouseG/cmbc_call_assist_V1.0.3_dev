package com.guiji.fs.xmlpojo;

import lombok.Data;

import javax.xml.bind.annotation.*;

@Data
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "user")
public class XUser {
    @XmlAttribute
    private String id;

    @XmlElement
    private XParams params;

    @XmlElement
    private XVariables variables;
}
