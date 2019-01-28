package com.guiji.fs.xmlpojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@Data
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "param")
@AllArgsConstructor
@NoArgsConstructor
public class XParam
{
    @XmlAttribute
    private String name;

    @XmlAttribute
    private String value;
}
