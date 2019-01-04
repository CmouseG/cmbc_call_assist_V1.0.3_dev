package com.guiji.da.api;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.guiji.component.result.Result;
import com.guiji.da.model.RobotCallHis;

import io.swagger.annotations.Api;

/** 
* @ClassName: IDaRemote 
* @Description: 量化分析对外服务
* @date 2019年1月2日 上午10:29:45 
* @version V1.0  
*/
@Api(tags="量化分析对外服务")
@FeignClient("guiyu-da-web")
public interface IDaRemote {
	
	/**
	 * 根据会话ID查询通话历史
	 * @param seqId
	 * @return
	 */
    @PostMapping(value = "/remote/queryRobotCallBySeqId")
	Result.ReturnData<RobotCallHis> queryRobotCallBySeqId(@RequestParam(value="seqId",required=true) String seqId);
    
    
    /**
	 * 根据会话ID更新通话历史通话状态
	 * @param seqId
	 * @param callStatus
	 * @return
	 */
    @PostMapping(value = "/remote/updateCallStatus")
	Result.ReturnData updateCallStatus(@RequestParam(value="seqId",required=true) String seqId,@RequestParam(value="callStatus",required=true) int callStatus);
    
    
    /**
     * 保存通话历史
     * @param robotCallHis
     * @return
     */
    @PostMapping(value = "/remote/recordRobotCallHis")
	Result.ReturnData recordRobotCallHis(@RequestBody RobotCallHis robotCallHis);
}
