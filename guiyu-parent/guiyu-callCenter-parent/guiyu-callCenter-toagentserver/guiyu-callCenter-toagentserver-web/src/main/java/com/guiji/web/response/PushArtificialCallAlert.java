package com.guiji.web.response;

import lombok.Data;

import java.util.List;

@Data
public class PushArtificialCallAlert {
    private String phoneNum;
    private List<String> agentNames;
}
