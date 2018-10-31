package com.guiji.ccmanager.feign;

import com.guiji.fsmanager.api.TempApi;
import org.springframework.cloud.netflix.feign.FeignClient;

/**
 * @Auther: 黎阳
 * @Date: 2018/10/30 0030 14:01
 * @Description:
 */
@FeignClient("guiyu-callcenter-fsmanager")
public interface TempApiFeign extends TempApi {

}
