package com.guiji.fs.xmlpojo;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@Data
@XmlRootElement(name = "include")
@XmlAccessorType(XmlAccessType.FIELD)
public class XUserInclude
{
    @XmlElement
    private XUser user;
}
