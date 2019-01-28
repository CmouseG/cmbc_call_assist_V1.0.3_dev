package com.guiji.fs.xmlpojo;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@Data
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "agent")
public class XAgent {
    @XmlAttribute
    private String name;

    @XmlAttribute
    private String type;

    @XmlAttribute
    private String contact;

    @XmlAttribute
    private String status;

    @XmlAttribute(name = "max-no-answer")
    private String max_no_answer;

    @XmlAttribute(name = "wrap-up-time")
    private String wrap_up_time;

    @XmlAttribute(name = "reject-delay-time")
    private String reject_delay_time;

    @XmlAttribute(name = "busy-delay-time")
    private String busy_delay_time;
}
