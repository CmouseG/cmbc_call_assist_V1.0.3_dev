package com.guiji.process.server.service.impl;

import com.guiji.common.model.Page;
import com.guiji.common.model.process.ProcessTypeEnum;
import com.guiji.process.core.ProcessMsgHandler;
import com.guiji.process.core.message.CmdMessageVO;
import com.guiji.process.core.vo.CmdTypeEnum;
import com.guiji.common.model.process.ProcessInstanceVO;
import com.guiji.process.server.dao.SysProcessMapper;
import com.guiji.process.server.dao.entity.SysProcess;
import com.guiji.process.server.dao.entity.SysProcessExample;
import com.guiji.process.server.service.ISysProcessService;
import com.guiji.utils.StrUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by ty on 2018/11/24.
 */
public class SysProcessServiceImpl implements ISysProcessService {
    private final Logger logger = LoggerFactory.getLogger(SysProcessServiceImpl.class);
    @Autowired
    private SysProcessMapper sysProcessMapper;
    @Override
    public boolean insert(SysProcess sysProcess) {
        if (sysProcess == null) {
            return false;
        }

        SysProcessExample sysProcessExample = this.getExampleByCondition(sysProcess);
        List<SysProcess> sysProcessList =  sysProcessMapper.selectByExample(sysProcessExample);
        if (sysProcessList != null && sysProcessList.size() > 0) {
            return false;
        } else {
            int result = sysProcessMapper.insert(sysProcess);
            return result > 0 ? true : false;
        }
    }

    @Override
    public boolean update(SysProcess sysProcess) {
        if (sysProcess != null) {
            sysProcess.setUpdateTime(new Date());
            int result = sysProcessMapper.updateByExampleSelective(sysProcess,new SysProcessExample());
            return result >0 ? true:false;
        } else {
            return false;
        }
    }

    @Override
    public boolean delete(long id) {
        int result = sysProcessMapper.deleteByPrimaryKey(id);
        return result >0 ? true:false;
    }

    @Override
    public Page<SysProcess> queryProcessPage(int pageNo, int pageSize, SysProcess sysProcess) {
        Page<SysProcess> page = new Page<SysProcess>();
        SysProcessExample example = this.getExampleByCondition(sysProcess);
        if(example == null) example = new SysProcessExample();
        int totalRecord = sysProcessMapper.countByExample(example); //总数
        example.setLimitStart((pageNo-1)*pageSize);	//起始条数
        example.setLimitEnd(pageSize);	//结束条数
        //分页查询
        List<SysProcess> list = sysProcessMapper.selectByExample(example);
        if(list != null && !list.isEmpty()) {
            List<SysProcess> rspSysDictList = new ArrayList<SysProcess>();
            for(SysProcess dict : list) {
                rspSysDictList.add(dict);
            }
            page.setRecords(rspSysDictList);
        }
        page.setPageNo(pageNo);
        page.setPageSize(pageSize);
        page.setTotal(totalRecord);
        return page;
    }

    @Override
    public void executeCmd(List<SysProcess> sysProcessList, CmdTypeEnum cmdTypeEnum) {
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
                cmdMessageVO.setCmdType(CmdTypeEnum.START);
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
            Long id = sysProcess.getId();	//主键ID
            String ip = sysProcess.getIp();//ip
            String port = sysProcess.getPort();	//端口
            String name = sysProcess.getName();	//资源名称
            int type = sysProcess.getType();	//资源类型
            String processKey = sysProcess.getProcessKey();//扩展字段，资源类型为TTS存模型名称
            int status = sysProcess.getStatus();//状态
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
            if(StrUtils.isNotEmpty(type)) {
                criteria.andTypeEqualTo(type);
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
