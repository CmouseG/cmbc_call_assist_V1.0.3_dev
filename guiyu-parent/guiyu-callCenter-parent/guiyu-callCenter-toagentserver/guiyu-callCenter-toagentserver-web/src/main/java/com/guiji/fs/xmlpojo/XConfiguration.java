package com.guiji.fs.xmlpojo;

import lombok.Data;

import javax.xml.bind.annotation.*;

@Data
@XmlRootElement(name = "configuration")
@XmlAccessorType(XmlAccessType.FIELD)
public class XConfiguration {
    @XmlAttribute(name = "name")
    private String name;

    @XmlAttribute(name = "description")
    private String description;

    @XmlElement(name = "settings")
    private XSettings settings;
}
