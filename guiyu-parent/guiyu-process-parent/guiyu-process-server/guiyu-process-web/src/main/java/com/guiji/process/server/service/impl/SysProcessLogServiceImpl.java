package com.guiji.process.server.service.impl;

import com.guiji.common.model.Page;
import com.guiji.process.server.dao.SysProcessLogMapper;
import com.guiji.process.server.dao.SysProcessMapper;
import com.guiji.process.server.dao.entity.SysProcess;
import com.guiji.process.server.dao.entity.SysProcessExample;
import com.guiji.process.server.dao.entity.SysProcessLog;
import com.guiji.process.server.dao.entity.SysProcessLogExample;
import com.guiji.process.server.service.ISysProcessLogService;
import com.guiji.utils.StrUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ty on 2018/11/28.
 */
@Service
public class SysProcessLogServiceImpl implements ISysProcessLogService {
    private final Logger logger = LoggerFactory.getLogger(SysProcessLogServiceImpl.class);
    @Autowired
    private SysProcessLogMapper sysProcessLogMapper;
    @Override
    public boolean insert(SysProcessLog sysProcessLog) {
        if (sysProcessLog == null) {
            return false;
        }
        int result = sysProcessLogMapper.insert(sysProcessLog);
        return result > 0 ? true : false;
    }

    @Override
    public boolean delete(long id) {
        int result = sysProcessLogMapper.deleteByPrimaryKey(id);
        return result >0 ? true:false;
    }

    @Override
    public Page<SysProcessLog> queryProcessLogPage(int pageNo, int pageSize, SysProcessLog sysProcessLog) {
        Page<SysProcessLog> page = new Page<SysProcessLog>();
        SysProcessLogExample example = this.getExampleByCondition(sysProcessLog);
        if(example == null) example = new SysProcessLogExample();
        int totalRecord = sysProcessLogMapper.countByExample(example); //总数
        example.setLimitStart((pageNo-1)*pageSize);	//起始条数
        example.setLimitEnd(pageSize);	//结束条数
        //分页查询
        List<SysProcessLog> list = sysProcessLogMapper.selectByExample(example);
        if(list != null && !list.isEmpty()) {
            List<SysProcessLog> rspSysProcessLogList = new ArrayList<SysProcessLog>();
            for(SysProcessLog processLog : list) {
                rspSysProcessLogList.add(processLog);
            }
            page.setRecords(rspSysProcessLogList);
        }
        page.setPageNo(pageNo);
        page.setPageSize(pageSize);
        page.setTotal(totalRecord);
        return page;
    }

    private SysProcessLogExample getExampleByCondition(SysProcessLog sysProcessLog) {
        logger.info("查询进程操作列表，查询条件="+sysProcessLog);
        if(sysProcessLog != null) {
            Long id = sysProcessLog.getId();	//主键ID
            String ip = sysProcessLog.getIp();//ip
            String port = sysProcessLog.getPort();	//端口
            Integer cmdType = sysProcessLog.getCmdType();	//动作类型
            String processKey = sysProcessLog.getProcessKey();//扩展字段，资源类型为TTS存模型名称
            String parameters = sysProcessLog.getParameters();//命令参数
            String result = sysProcessLog.getResult();//命令执行结果
            String resultContent = sysProcessLog.getResultContent();//命令执行结果描述
            SysProcessLogExample example = new SysProcessLogExample();
            SysProcessLogExample.Criteria criteria = example.createCriteria();
            if(id != null) {
                criteria.andIdEqualTo(id);
            }
            if(StrUtils.isNotEmpty(ip)) {
                criteria.andIpEqualTo(ip);
            }
            if(StrUtils.isNotEmpty(port)) {
                criteria.andPortEqualTo(port);
            }
            if(StrUtils.isNotEmpty(cmdType)) {
                criteria.andCmdTypeEqualTo(cmdType);
            }
            if(StrUtils.isNotEmpty(processKey)) {
                criteria.andProcessKeyLike(processKey);
            }
            if(StrUtils.isNotEmpty(parameters)) {
                criteria.andParametersEqualTo(parameters);
            }
            if(StrUtils.isNotEmpty(result)) {
                criteria.andResultEqualTo(result);
            }
            if(StrUtils.isNotEmpty(resultContent)) {
                criteria.andResultContentEqualTo(resultContent);
            }

            return example;
        }else {
            logger.info("查询进程操作列表");
        }
        return null;
    }
}
