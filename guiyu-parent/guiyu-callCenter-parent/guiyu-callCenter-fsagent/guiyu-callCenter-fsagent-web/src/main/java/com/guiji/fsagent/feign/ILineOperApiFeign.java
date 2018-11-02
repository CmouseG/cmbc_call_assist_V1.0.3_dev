package com.guiji.fsagent.feign;

import com.guiji.fsmanager.api.ILineOperApi;
import org.springframework.cloud.netflix.feign.FeignClient;

@FeignClient("guiyu-callCenter-fsmanager")
public interface  ILineOperApiFeign extends ILineOperApi {

}
