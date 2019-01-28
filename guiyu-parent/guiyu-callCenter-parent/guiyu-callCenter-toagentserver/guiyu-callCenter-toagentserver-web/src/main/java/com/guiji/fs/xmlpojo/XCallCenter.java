package com.guiji.fs.xmlpojo;

import lombok.Data;

import javax.xml.bind.annotation.*;

@Data
@XmlRootElement(name = "configuration")
@XmlAccessorType(XmlAccessType.FIELD)
public class XCallCenter {
    @XmlAttribute
    private String name;
    @XmlAttribute
    private String description;

    @XmlElement
    private String settings;

    @XmlElement
    private XAgents agents;

    @XmlElement
    private XQueues queues;

    @XmlElement
    private XTiers tiers;
}
