package com.guiji.ccmanager.feign;

import com.guiji.fsmanager.api.LineOperApi;
import org.springframework.cloud.netflix.feign.FeignClient;

/**
 * @Auther: 黎阳
 * @Date: 2018/10/26 0026 15:22
 * @Description:
 */
@FeignClient("GUIYU-CALLCENTER-FSMANAGER-SERVICE")
public interface LineOperApiFeign extends LineOperApi {

}
