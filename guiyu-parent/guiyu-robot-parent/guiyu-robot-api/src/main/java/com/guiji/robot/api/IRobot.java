package com.guiji.robot.api;

import com.guiji.robot.vo.RobotVO;
import com.guiji.robot.vo.UserRobotRelationVO;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;


import java.util.List;

/**
 * 机器人能力中心对外服务
 */
@FeignClient("robot")
public interface IRobot {
    /**
     * 根据robotId判断params内容是否正确
     *
     * @param userId 用户ID
     * @param robotId 机器人ID
     * @param params 需要校验的话术变量
     * @return Integer
     */
    @ApiOperation(value = "根据tempId和params判断params是否正确")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户ID", required = true, dataType = "Long", paramType = "query"),
            @ApiImplicitParam(name = "robotId", value = "机器人ID", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "params", value = "需要校验的话术变量", required = true, dataType = "String", paramType = "query")
    })
    @PostMapping(value = "/checkParams")
    Integer checkParams(Long userId, String robotId, String params);

    /**
     * 根据robotId、planUuid和params判断资源是否准备好
     *
     * @param userId 用户ID
     * @param planUuid 任务唯一ID
     * @param robotId 机器人ID
     * @param params 话术变量
     * @return Integer
     */
    @ApiOperation(value = "根据robotId、planUuid和params判断资源是否准备好")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户ID", required = true, dataType = "Long", paramType = "query"),
            @ApiImplicitParam(name = "planUuid", value = "任务唯一ID", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "robotId", value = "机器人ID", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "params", value = "需要校验的话术变量", required = true, dataType = "String", paramType = "query")
    })
    @PostMapping(value = "/checkParamsResource")
    Integer checkParamsResource(Long userId, String planUuid, String robotId, String params);

    /**
     * 获取TTS增量话术语音文件地址
     *
     * @param userId 用户ID
     * @param planUuid 任务唯一ID
     * @param robotId 机器人ID
     * @param params 话术变量
     * @return String
     */
    @ApiOperation(value = "获取TTS增量话术语音文件地址")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户ID", required = true, dataType = "Long", paramType = "query"),
            @ApiImplicitParam(name = "planUuid", value = "任务唯一ID", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "robotId", value = "机器人ID", required = true, dataType = "Long", paramType = "query"),
            @ApiImplicitParam(name = "params", value = "话术变量", required = true, dataType = "String", paramType = "query")
    })
    @PostMapping(value = "/getTTSVoiceResource")
    String getTTSVoiceResource(Long userId, String planUuid, String robotId, String params);

    /**
     * 根据robotId获取话术语音文件地址
     *
     * @param robotId 机器人ID
     * @return String
     */
    @ApiOperation(value = "根据robotId获取话术语音文件地址")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "robotId", value = "机器人ID", required = true, dataType = "Long", paramType = "path")
    })
    @GetMapping(value = "/getVoiceResource/{robotId}")
    String getVoiceResource(@PathVariable(value = "robotId") Long robotId);

    /**
     * 根据robotKey获取话术语音文件地址
     *
     * @param robotKey 机器人代号
     * @return String
     */
    @ApiOperation(value = "根据robotKey获取话术语音文件地址")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "robotKey", value = "机器人代号", required = true, dataType = "String", paramType = "path")
    })
    @GetMapping(value = "/getVoiceResource/{robotKey}")
    String getVoiceResource(@PathVariable(value = "robotKey") String robotKey);

    /**
     * 请求机器人资源接口返回唯一资源标识
     *
     * @param userId 用户ID
     * @param planUuid 任务唯一ID
     * @param robotId 机器人ID
     * @param params 话术变量
     * @return String
     */
    @ApiOperation(value = "请求机器人资源接口返回唯一资源标识")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户ID", required = true, dataType = "Long", paramType = "query"),
            @ApiImplicitParam(name = "planUuid", value = "任务唯一ID", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "robotId", value = "机器人ID", required = true, dataType = "Long", paramType = "query"),
            @ApiImplicitParam(name = "params", value = "话术变量", required = true, dataType = "String", paramType = "query")
    })
    @PostMapping(value = "/getRobotResource")
    String getRobotResource(Long userId, String planUuid, String robotId, String params);

    /**
     * 通知释放机器人资源
     *
     * @param userId 用户ID
     * @param resourceId 资源Id
     * @return Integer
     */
    @ApiOperation(value = "通知释放机器人资源")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户ID", required = true, dataType = "Long", paramType = "query"),
            @ApiImplicitParam(name = "resourceId", value = "资源Id", required = true, dataType = "String", paramType = "query")
    })
    @PostMapping(value = "/releaseRobotResource")
    Integer releaseRobotResource(Long userId, String resourceId);

    /**
     * 查询机器人列表
     *
     * @param robotName 机器人名称
     * @param robotKey 机器人代号
     * @param page 页码
     * @param pageSize 每页条数
     * @return List<RobotVO>
     */
    @ApiOperation(value = "机器人管理")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "robotName", value = "机器人名称", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "robotKey", value = "机器人代号", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "page", value = "页码", required = true, dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "每页条数", required = true, dataType = "Integer", paramType = "query")
    })
    @PostMapping(value = "/getRobotList")
    List<RobotVO> getRobotList(String robotName, String robotKey, Integer page, Integer pageSize);

    /**
     * 查询机器人详情
     *
     * @param id 机器人ID
     * @return RobotVO
     */
    @ApiOperation(value = "查询机器人详情")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "机器人ID", required = true, dataType = "Long", paramType = "path")
    })
    @GetMapping(value = "/robot/{id}")
    RobotVO getRobot(@PathVariable(value="id") Long id);

    /**
     * 新增机器人
     *
     * @param robotName 机器人名称
     * @param robotKey 机器人代号
     * @param isTts 是否有TTS合成
     * @param isVisit 是否回访类型
     * @param robotDesc 机器人描述
     * @return RobotVO
     */
    @ApiOperation(value = "新增机器人")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "robotName", value = "机器人名称", required = true, dataType = "Long", paramType = "query"),
            @ApiImplicitParam(name = "robotKey", value = "机器人代号", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "isTts", value = "是否有TTS合成", required = true, dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(name = "isVisit", value = "是否回访类型", required = true, dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(name = "robotDesc", value = "机器人描述", dataType = "String", paramType = "query")
    })
    @PostMapping(value = "/robot")
    RobotVO addRobot(String robotName, String robotKey, Integer isTts, Integer isVisit, String robotDesc);

    /**
     * 更新机器人信息
     *
     * @param id 机器人ID
     * @param robotName 机器人名称
     * @param robotKey 机器人代号
     * @param isTts 是否有TTS合成
     * @param isVisit 是否回访类型
     * @param robotDesc 机器人描述
     * @return RobotVO
     */
    @ApiOperation(value = "更新机器人信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "机器人ID", required = true, dataType = "Long", paramType = "path"),
            @ApiImplicitParam(name = "robotName", value = "机器人名称", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "robotKey", value = "机器人代号", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "isTts", value = "是否有TTS合成", required = true, dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(name = "isVisit", value = "是否回访类型", required = true, dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(name = "robotDesc", value = "机器人描述", dataType = "String", paramType = "query")
    })
    @PostMapping(value = "/robot/{id}")
    RobotVO updateRobot(@PathVariable(value="id") Long id, String robotName, String robotKey, Integer isTts, Integer isVisit, String robotDesc);

    /**
     * 删除机器人信息
     *
     * @param id 机器人ID
     * @return Integer
     */
    @ApiOperation(value = "删除机器人信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "机器人ID", required = true, dataType = "Long", paramType = "path")
    })
    @DeleteMapping(value = "/robot/{id}")
    Integer deleteRobot(@PathVariable(value="id") Long id);

    /**
     * 用户和机器人的绑定关系列表
     *
     * @param robotName 话术Id
     * @param robotKey 话术变量
     * @param userName 话术变量
     * @param page 页码
     * @param pageSize 每页条数
     * @return List<UserRobotRelationVO>
     */
    @ApiOperation(value = "用户和机器人关系管理")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "robotName", value = "机器人名称", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "robotKey", value = "机器人代号", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "userName", value = "用户名", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "page", value = "页码", required = true, dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "每页条数", required = true, dataType = "Integer", paramType = "query")
    })
    @PostMapping(value = "/getRelationList")
    List<UserRobotRelationVO> getRelationList(String robotName, String robotKey, String userName, Integer page, Integer pageSize);

    /**
     * 用户和机器人的绑定关系详情
     *
     * @param id     关系ID
     * @return UserRobotRelationVO
     */
    @ApiOperation(value = "用户和机器人的绑定关系详情")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "关系ID", required = true, dataType = "Integer", paramType = "path")
    })
    @GetMapping(value = "/getRelation/{id}")
    UserRobotRelationVO getRelation(@PathVariable(value = "id") String id);

    /**
     * 新增用户和机器人的绑定关系
     *
     * @param userId   用户ID
     * @param robotKey 机器人代号
     * @param status   关系状态
     * @return UserRobotRelationVO
     */
    @ApiOperation(value = "新增用户和机器人的绑定关系")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户ID", required = true, dataType = "Long", paramType = "query"),
            @ApiImplicitParam(name = "robotKey", value = "机器人代号", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "status", value = "关系状态", required = true, dataType = "Integer", paramType = "query")
    })
    @PostMapping(value = "/addRelation")
    UserRobotRelationVO addRelation(Long userId, String robotKey, Integer status);

    /**
     * 更新用户和机器人的绑定关系状态
     *
     * @param id     关系ID
     * @param status 关系状态
     * @return UserRobotRelationVO
     */
    @ApiOperation(value = "更新用户和机器人的绑定关系状态")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "关系ID", required = true, dataType = "Long", paramType = "path"),
            @ApiImplicitParam(name = "status", value = "关系状态", required = true, dataType = "Integer", paramType = "path")
    })
    @PostMapping(value = "/updateRelation/{id}/{status}")
    UserRobotRelationVO updateRelation(@PathVariable(value = "id") Long id, @PathVariable(value = "status") Integer status);
}
