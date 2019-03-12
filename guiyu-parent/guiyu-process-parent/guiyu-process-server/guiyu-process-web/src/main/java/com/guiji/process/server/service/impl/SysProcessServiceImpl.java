package com.guiji.process.server.service.impl;

import com.guiji.common.constant.RedisConstant;
import com.guiji.common.exception.GuiyuException;
import com.guiji.common.model.Page;
import com.guiji.common.model.process.ProcessTypeEnum;
import com.guiji.process.core.ProcessMsgHandler;
import com.guiji.process.core.message.CmdMessageVO;
import com.guiji.process.core.vo.CmdTypeEnum;
import com.guiji.common.model.process.ProcessInstanceVO;
import com.guiji.process.server.dao.SysProcessMapper;
import com.guiji.process.server.dao.SysProcessTaskMapper;
import com.guiji.process.server.dao.entity.SysProcess;
import com.guiji.process.server.dao.entity.SysProcessExample;
import com.guiji.process.server.dao.entity.SysProcessTask;
import com.guiji.process.server.exception.GuiyuProcessExceptionEnum;
import com.guiji.process.server.service.ISysProcessService;
import com.guiji.process.server.service.ISysProcessTaskService;
import com.guiji.utils.RedisUtil;
import com.guiji.utils.StrUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by ty on 2018/11/24.
 */
@Service
public class SysProcessServiceImpl implements ISysProcessService {
    private final Logger logger = LoggerFactory.getLogger(SysProcessServiceImpl.class);
    @Autowired
    private SysProcessMapper sysProcessMapper;
    @Autowired
    private ISysProcessTaskService sysProcessTaskService;
    @Autowired
    private RedisUtil redisUtil;
    @Override
    public boolean insert(SysProcess sysProcess) {
        if (sysProcess == null) {
            return false;
        }

        SysProcess sysProcessTmp = new SysProcess();
        sysProcessTmp.setIp(sysProcess.getIp());
        sysProcessTmp.setPort(sysProcess.getPort());
        sysProcessTmp.setType(sysProcess.getType());
        SysProcessExample sysProcessExample = this.getExampleByCondition(sysProcessTmp);
        List<SysProcess> sysProcessList =  sysProcessMapper.selectByExample(sysProcessExample);
        if (sysProcessList != null && sysProcessList.size() > 0) {
            return this.update(sysProcess);
        } else {
            int result = sysProcessMapper.insert(sysProcess);
            return result > 0 ? true : false;
        }
    }

    @Override
    public boolean update(SysProcess sysProcess) {
        if (sysProcess != null) {
            SysProcess sysProcessTmp = new SysProcess();
            sysProcessTmp.setIp(sysProcess.getIp());
            sysProcessTmp.setPort(sysProcess.getPort());
            SysProcessExample example = this.getExampleByCondition(sysProcessTmp);
            int result = sysProcessMapper.updateByExampleSelective(sysProcess,example);
            return result >0 ? true:false;
        } else {
            return false;
        }
    }

    @Override
    public List<SysProcess> list(SysProcess sysProcess) {
        SysProcessExample example = this.getExampleByCondition(sysProcess);
        if(example == null) example = new SysProcessExample();
        List<SysProcess> list = sysProcessMapper.selectByExample(example);
        return list;
    }

    @Override
    public boolean delete(int id) {
        int result = sysProcessMapper.deleteByPrimaryKey(id);
        return result >0 ? true:false;
    }

    @Override
    public Page<SysProcess> queryProcessPage(int pageNo, int pageSize, SysProcess sysProcess) {
        Page<SysProcess> page = new Page<SysProcess>();
        SysProcessExample example = this.getNoAgentExampleByCondition(sysProcess);
        if(example == null) example = new SysProcessExample();
        int totalRecord = sysProcessMapper.countByExample(example); //总数
        example.setLimitStart((pageNo-1)*pageSize);	//起始条数
        example.setLimitEnd(pageSize);	//结束条数
        example.setOrderByClause("id desc");
        //分页查询
        List<SysProcess> list = sysProcessMapper.selectByExample(example);
        if(list != null && !list.isEmpty()) {
            List<SysProcess> rspSysProcessList = new ArrayList<SysProcess>();
            for(SysProcess process : list) {
                SysProcessTask sysProcessTaskTmp = new SysProcessTask();
                sysProcessTaskTmp.setIp(process.getIp());
                sysProcessTaskTmp.setPort(process.getPort());
                sysProcessTaskTmp.setExecStatus(1);
                List<SysProcessTask> isExist = sysProcessTaskService.list(sysProcessTaskTmp);
                if (isExist != null && isExist.size() > 0){
                    process.setExecStatus(1);
                }
                rspSysProcessList.add(process);
            }
            page.setRecords(rspSysProcessList);
        }
        page.setPageNo(pageNo);
        page.setPageSize(pageSize);
        page.setTotal(totalRecord);
        return page;
    }

    @Override
    public Page<SysProcess> queryAgentPage(int pageNo, int pageSize, SysProcess sysProcess) {
        Page<SysProcess> page = new Page<SysProcess>();
        sysProcess.setType(ProcessTypeEnum.AGENT.getValue());
        SysProcessExample example = this.getExampleByCondition(sysProcess);
        if(example == null) example = new SysProcessExample();
        int totalRecord = sysProcessMapper.countByExample(example); //总数
        example.setLimitStart((pageNo-1)*pageSize);	//起始条数
        example.setLimitEnd(pageSize);	//结束条数
        example.setOrderByClause("create_time desc");
        //分页查询
        List<SysProcess> list = sysProcessMapper.selectByExample(example);
        if(list != null && !list.isEmpty()) {
            List<SysProcess> rspSysProcessList = new ArrayList<SysProcess>();
            for(SysProcess process : list) {
                SysProcessTask sysProcessTaskTmp = new SysProcessTask();
                sysProcessTaskTmp.setIp(process.getIp());
                sysProcessTaskTmp.setPort(process.getPort());
                sysProcessTaskTmp.setExecStatus(1);
                List<SysProcessTask> isExist = sysProcessTaskService.list(sysProcessTaskTmp);
                if (isExist != null && isExist.size() > 0){
                    process.setExecStatus(1);
                }
                rspSysProcessList.add(process);
            }
            page.setRecords(rspSysProcessList);
        }
        page.setPageNo(pageNo);
        page.setPageSize(pageSize);
        page.setTotal(totalRecord);
        return page;
    }

    @Override
    public void executeCmd(List<SysProcess> sysProcessList, CmdTypeEnum cmdTypeEnum,Long userId) {
        List<CmdMessageVO> cmdMessageVOs = new ArrayList<CmdMessageVO>();
        if (sysProcessList != null) {
            for(SysProcess sysProcess : sysProcessList) {
                ProcessInstanceVO processInstanceVO = new ProcessInstanceVO();
                processInstanceVO.setIp(sysProcess.getIp());
                processInstanceVO.setPort(Integer.parseInt(sysProcess.getPort()));
                processInstanceVO.setProcessKey(sysProcess.getProcessKey());
                processInstanceVO.setType(ProcessTypeEnum.valueOf(sysProcess.getType()));
                CmdMessageVO cmdMessageVO = new CmdMessageVO();
                cmdMessageVO.setProcessInstanceVO(processInstanceVO);
                cmdMessageVO.setCmdType(cmdTypeEnum);
                cmdMessageVO.setUserId(userId);
                cmdMessageVOs.add(cmdMessageVO);

            }
        }
        if(!cmdMessageVOs.isEmpty()) {
            ProcessMsgHandler.getInstance().add(cmdMessageVOs);
        }

    }

    private SysProcessExample getExampleByCondition(SysProcess sysProcess) {
        logger.info("查询进程，查询条件="+sysProcess);
        if(sysProcess != null) {
            Integer id = sysProcess.getId();	//主键ID
            String ip = sysProcess.getIp();//ip
            String port = sysProcess.getPort();	//端口
            String name = sysProcess.getName();	//资源名称
            Integer type = sysProcess.getType();	//资源类型
            Integer status = sysProcess.getStatus();
            String processKey = sysProcess.getProcessKey();//扩展字段，资源类型为TTS存模型名称
            SysProcessExample example = new SysProcessExample();
            SysProcessExample.Criteria criteria = example.createCriteria();
            if(id != null) {
                criteria.andIdEqualTo(id);
            }
            if(StrUtils.isNotEmpty(ip)) {
                criteria.andIpEqualTo(ip);
            }
            if(StrUtils.isNotEmpty(port)) {
                criteria.andPortEqualTo(port);
            }
            if(StrUtils.isNotEmpty(name)) {
                criteria.andNameEqualTo(name);
            }
            if(type != null) {
                criteria.andTypeEqualTo(type);
            }
            if(status != null) {
                criteria.andStatusEqualTo(status);
            }
            if(StrUtils.isNotEmpty(processKey)) {
                criteria.andProcessKeyLike(processKey);
            }
            return example;
        }else {
            logger.info("查询进程列表");
        }
        return null;
    }

    private SysProcessExample getNoAgentExampleByCondition(SysProcess sysProcess) {
        logger.info("查询进程，查询条件="+sysProcess);
        if(sysProcess != null) {
            Integer id = sysProcess.getId();	//主键ID
            String ip = sysProcess.getIp();//ip
            String port = sysProcess.getPort();	//端口
            String name = sysProcess.getName();	//资源名称
            Integer type = sysProcess.getType();	//资源类型
            Integer status = sysProcess.getStatus();
            String processKey = sysProcess.getProcessKey();//扩展字段，资源类型为TTS存模型名称
            SysProcessExample example = new SysProcessExample();
            SysProcessExample.Criteria criteria = example.createCriteria();
            criteria.andTypeNotEqualTo(ProcessTypeEnum.AGENT.getValue());
            criteria.andTypeNotEqualTo(ProcessTypeEnum.FREESWITCH.getValue());
            criteria.andTypeNotEqualTo(ProcessTypeEnum.ROBOT.getValue());
            if(id != null) {
                criteria.andIdEqualTo(id);
            }
            if(StrUtils.isNotEmpty(ip)) {
                criteria.andIpEqualTo(ip);
            }
            if(StrUtils.isNotEmpty(port)) {
                criteria.andPortEqualTo(port);
            }
            if(StrUtils.isNotEmpty(name)) {
                criteria.andNameEqualTo(name);
            }
            if(type != null) {
                criteria.andTypeEqualTo(type);
            }
            if(status != null) {
                criteria.andStatusEqualTo(status);
            }
            if(StrUtils.isNotEmpty(processKey)) {
                criteria.andProcessKeyLike(processKey);
            }
            return example;
        }else {
            logger.info("查询进程列表");
        }
        return null;
    }
}
