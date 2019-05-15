package com.guiji.voipgateway.api;

import java.util.List;

import com.guiji.voipgateway.model.*;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.guiji.component.result.Result;

import io.swagger.annotations.Api;

/** 
* @ClassName: VoipGatewayRemote
* @Description: 量化分析对外服务
* @date 2019年1月29日 上午10:29:45 
* @version V1.0  
*/
@Api(tags="语音网关外部服务")
@FeignClient("guiyu-voipgateway-web")
public interface VoipGatewayRemote {
	
	/**
	 * 根据公司编号查找公司信息
	 * @param companyId
	 * @param gwBrand 网关品牌：三汇，鼎信
	 * @return
	 */
    @PostMapping(value = "/remote/queryCompanyById")
	Result.ReturnData<Company> queryCompanyById(@RequestParam(value = "brand", required = true)String gwBrand, @RequestParam(value="companyId",required=true) Integer companyId);
    
    
    /**
     * 根据设备名称查找设备基本信息
     * @param devName
     * @return
     */
    @PostMapping(value = "/remote/queryCompanyByDevName")
	Result.ReturnData<GwDevtbl> queryCompanyByDevName(@RequestParam(value = "brand", required = true)String gwBrand, @RequestParam(value="devName",required=true) String devName);
    
    
    /**
     * 根据设备编号查询设备信息
     * @param devId
     * @return
     */
    @PostMapping(value = "/remote/queryGwDevByDevId")
	Result.ReturnData<GwDevtbl> queryGwDevByDevId(
			@RequestParam(value = "brand")String gwBrand,
			@RequestParam(value="companyId",required=true) Integer companyId,
			@RequestParam(value="devId",required=true) Integer devId);
    
    /**
     * 根据客户编号查询客户下所有设备
     * @param devName
     * @return
     */
    @PostMapping(value = "/remote/queryGwDevtblListByCompId")
	Result.ReturnData<List<GwDevtbl>> queryGwDevtblListByCompId(@RequestParam(value = "brand", required = true)String gwBrand, @RequestParam(value="companyId",required=true) Integer companyId);
    
    
    /**
     * 根据设备编号查询该设备下所有端口情况
     * @param companyId
     * @param devId
     * @return
     */
    @PostMapping(value = "/remote/querySimPortListByDevId")
	Result.ReturnData<List<SimPort>> querySimPortListByDevId(
			@RequestParam(value = "brand", required = true)String gwBrand,
			@RequestParam(value="companyId",required=true) Integer companyId,
			@RequestParam(value="devId",required=true) Integer devId);


	/**
	 * 根据设备和端口，查询端口状态
	 * @param portRos
	 * @return
	 */
	@PostMapping(value = "/remote/querySipPortStatus")
	Result.ReturnData<PortStatusEnum> querySipPortStatus(@RequestBody PortRo portRo);
}
