package com.guiji.dispatch.service;

import java.util.List;

/**
 * @Classname CallAgentService
 * @Description TODO
 * @Date 2019/5/29 14:43
 * @Created by qinghua
 */
public interface CallAgentService {

    List<String> getAgent(String orgCode);

}
