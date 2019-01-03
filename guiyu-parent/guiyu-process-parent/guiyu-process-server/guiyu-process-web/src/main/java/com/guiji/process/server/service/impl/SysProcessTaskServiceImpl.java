package com.guiji.process.server.service.impl;

import com.guiji.common.model.Page;
import com.guiji.process.server.dao.SysProcessTaskMapper;
import com.guiji.process.server.dao.entity.SysProcess;
import com.guiji.process.server.dao.entity.SysProcessExample;
import com.guiji.process.server.dao.entity.SysProcessTask;
import com.guiji.process.server.dao.entity.SysProcessTaskExample;
import com.guiji.process.server.service.ISysProcessTaskService;
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
public class SysProcessTaskServiceImpl implements ISysProcessTaskService {
    private final Logger logger = LoggerFactory.getLogger(SysProcessTaskServiceImpl.class);
    @Autowired
    private SysProcessTaskMapper sysProcessTaskMapper;
    @Override
    public boolean insert(SysProcessTask sysProcessTask) {
        if (sysProcessTask == null) {
            return false;
        }
        // 相同reqKey不重复插入
        SysProcessTask tmp = new SysProcessTask();
        tmp.setReqKey(sysProcessTask.getReqKey());
        SysProcessTaskExample example = this.getExampleByCondition(tmp);
        if(example == null) example = new SysProcessTaskExample();
        List<SysProcessTask> list = sysProcessTaskMapper.selectByExample(example);
        if (list != null && list.size() > 0) {
            return false;
        }

        int result = sysProcessTaskMapper.insert(sysProcessTask);
        return result > 0 ? true : false;
    }

    @Override
    public boolean delete(Integer id) {
        int result = sysProcessTaskMapper.deleteByPrimaryKey(id);
        return result >0 ? true:false;
    }

    @Override
    public boolean update(SysProcessTask sysProcessTask) {
        if (sysProcessTask != null) {
            SysProcessTask sysProcessTaskTmp = new SysProcessTask();
            sysProcessTaskTmp.setReqKey(sysProcessTask.getReqKey());
            SysProcessTaskExample example = this.getExampleByCondition(sysProcessTaskTmp);
            int result = sysProcessTaskMapper.updateByExampleSelective(sysProcessTask,example);
            return result >0 ? true:false;
        } else {
            return false;
        }
    }

    @Override
    public List<SysProcessTask> list(SysProcessTask sysProcessTask) {
        SysProcessTaskExample example = this.getExampleByCondition(sysProcessTask);
        if(example == null) example = new SysProcessTaskExample();
        List<SysProcessTask> list = sysProcessTaskMapper.selectByExample(example);
        return list;
    }

    @Override
    public Page<SysProcessTask> queryProcessTaskPage(int pageNo, int pageSize, SysProcessTask sysProcessTask) {
        Page<SysProcessTask> page = new Page<SysProcessTask>();
        SysProcessTaskExample example = this.getExampleByCondition(sysProcessTask);
        if(example == null) example = new SysProcessTaskExample();
        int totalRecord = sysProcessTaskMapper.countByExample(example); //总数
        example.setLimitStart((pageNo-1)*pageSize);	//起始条数
        example.setLimitEnd(pageSize);	//结束条数
        example.setOrderByClause("create_time desc");//按时间倒序显示
        //分页查询
        List<SysProcessTask> list = sysProcessTaskMapper.selectByExample(example);
        if(list != null && !list.isEmpty()) {
            List<SysProcessTask> rspSysProcessTaskList = new ArrayList<SysProcessTask>();
            for(SysProcessTask processTask : list) {
                rspSysProcessTaskList.add(processTask);
            }
            page.setRecords(rspSysProcessTaskList);
        }
        page.setPageNo(pageNo);
        page.setPageSize(pageSize);
        page.setTotal(totalRecord);
        return page;
    }

    private SysProcessTaskExample getExampleByCondition(SysProcessTask sysProcessTask) {
        logger.info("查询进程操作列表，查询条件="+sysProcessTask);
        if(sysProcessTask != null) {
            Integer id = sysProcessTask.getId();	//主键ID
            String ip = sysProcessTask.getIp();//ip
            String port = sysProcessTask.getPort();	//端口
            Integer cmdType = sysProcessTask.getCmdType();	//动作类型
            String processKey = sysProcessTask.getProcessKey();//扩展字段，资源类型为TTS存模型名称
            String parameters = sysProcessTask.getParameters();//命令参数
            Integer result = sysProcessTask.getResult();//命令执行结果
            String resultContent = sysProcessTask.getResultContent();//命令执行结果描述
            Integer execStatus = sysProcessTask.getExecStatus();
            String reqKey = sysProcessTask.getReqKey();
            Integer processId = sysProcessTask.getProcessId();
            SysProcessTaskExample example = new SysProcessTaskExample();
            SysProcessTaskExample.Criteria criteria = example.createCriteria();
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
            if(execStatus != null) {
                criteria.andExecStatusEqualTo(execStatus);
            }
            if(StrUtils.isNotEmpty(reqKey)) {
                criteria.andReqKeyEqualTo(reqKey);
            }
            if(processId != null) {
                criteria.andProcessIdEqualTo(processId);
            }

            return example;
        }else {
            logger.info("查询进程操作列表");
        }
        return null;
    }
}
