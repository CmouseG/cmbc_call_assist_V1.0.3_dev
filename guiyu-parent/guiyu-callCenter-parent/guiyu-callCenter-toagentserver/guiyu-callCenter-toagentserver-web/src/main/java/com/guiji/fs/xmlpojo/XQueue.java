package com.guiji.fs.xmlpojo;

import lombok.Data;

import javax.xml.bind.annotation.*;

@Data
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "queue")
public class XQueue {
    @XmlAttribute
    private String name;

    @XmlElement
    private XParam[] param;
}
