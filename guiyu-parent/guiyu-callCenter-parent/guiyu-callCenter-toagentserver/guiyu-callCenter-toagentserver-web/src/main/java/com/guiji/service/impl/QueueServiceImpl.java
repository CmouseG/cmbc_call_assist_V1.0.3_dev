package com.guiji.service.impl;

import com.github.pagehelper.PageInfo;
import com.guiji.callcenter.dao.AgentMapper;
import com.guiji.callcenter.dao.LineInfoMapper;
import com.guiji.callcenter.dao.QueueMapper;
import com.guiji.callcenter.dao.TierMapper;
import com.guiji.callcenter.dao.entity.*;
import com.guiji.callcenter.helper.PageExample;
import com.guiji.common.exception.GuiyuException;
import com.guiji.common.model.SysFileReqVO;
import com.guiji.common.model.SysFileRspVO;
import com.guiji.config.FsBotConfig;
import com.guiji.config.ToagentserverException;
import com.guiji.entity.EAnswerType;
import com.guiji.entity.EUserRole;
import com.guiji.entity.EUserState;
import com.guiji.fs.FsManager;
import com.guiji.fs.pojo.AgentStatus;
import com.guiji.fsline.entity.FsLineVO;
import com.guiji.manager.EurekaManager;
import com.guiji.manager.FsLineManager;
import com.guiji.service.QueueService;
import com.guiji.util.DateUtil;
import com.guiji.utils.NasUtil;
import com.guiji.web.request.AgentInfo;
import com.guiji.web.request.QueueInfo;
import com.guiji.web.request.TierInfo;
import com.guiji.web.response.Paging;
import com.guiji.web.response.QueryQueue;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Auther: 魏驰
 * @Date: 2018/12/17 14:33
 * @Project：ccserver
 * @Description:
 */
@Slf4j
@Service
public class QueueServiceImpl implements QueueService {
    @Autowired
    FsManager fsManager;
    @Autowired
    QueueMapper queueMapper;
    @Autowired
    AgentMapper agentMapper;
    @Autowired
    TierMapper tierMapper;
    @Autowired
    FsBotConfig fsBotConfig;
    @Autowired
    EurekaManager eurekaManager;
    @Autowired
    FsLineManager fsLineManager;
    @Autowired
    LineInfoMapper lineInfoMapper;

    @Override
    public boolean addQueue(QueueInfo queueInfo, Agent agent) throws Exception{
        QueueExample queueExample = new QueueExample();
        queueExample.createCriteria().andOrgCodeEqualTo(agent.getOrgCode()).andQueueNameEqualTo(queueInfo.getQueueName());
        List<Queue> queueList = queueMapper.selectByExample(queueExample);
        if (queueList != null&&queueList.size()>0) {
            log.info("不能创建同名坐席[{}]",queueInfo.getQueueName());
          //  throw new GuiyuException(ToagentserverException.EXCP_TOAGENT_QUEUE_ISIN);
            throw new Exception("0307007");

        }
        Date date = new Date();
        Queue queue = new Queue();
        queue.setQueueName(queueInfo.getQueueName());
        queue.setCreator(agent.getUserId());
        queue.setCreateTime(date);
        queue.setUpdateUser(agent.getUserId());
        queue.setUpdateTime(date);
        queue.setOrgCode(agent.getOrgCode());
        queue.setLineId(queueInfo.getLineId());
        queueMapper.insert(queue);
       // Boolean result = fsManager.createQueue(queue.getQueueId() + "");
        List<String> queueIdList = new ArrayList<>();
        queueIdList.add(queue.getQueueId()+"");

        QueueExample queueExample1 = new QueueExample();
        List<Queue> queueList1 = queueMapper.selectByExample(queueExample1);
        for (Queue queues:queueList1) {
            queueIdList.add(queues.getQueueId()+"");
        }
        //调用基于模板批量创建坐席、队列和绑定关系的方法，生成xml
        fsManager.initCallcenter(null,queueIdList,null);

        // 调用上传NAS的接口，得到文件下载地址，并调用lua脚本
        String fileUrl = uploadConfig(1L, fsBotConfig.getHomeDir()+"/callcenter.conf.xml");
      //  String other = "callcenter_config+queue+load+"+queue.getQueueId();
        String other = "reload+mod_callcenter";
        fsManager.syncCallcenter(fileUrl,other);

        return true;
    }
    //上传配置文件，并保存到nas中
    private String uploadConfig(Long userId, String configPath){
        NasUtil nasUtil = new NasUtil();
        SysFileReqVO reqVO = new SysFileReqVO();
        reqVO.setThumbImageFlag("0");
        reqVO.setUserId(userId);
        reqVO.setBusiType("freeswitch_config");
        reqVO.setSysCode(eurekaManager.getAppName());
        reqVO.setBusiId("callcenter.conf.xml");

        SysFileRspVO fileRspVO = nasUtil.uploadNas(reqVO, new File(configPath));
        return fileRspVO.getSkUrl();
    }
    @Override
    public boolean deleteQueue(String queueId){
        TierExample tierExample = new TierExample();
        tierExample.createCriteria().andQueueIdEqualTo(Long.parseLong(queueId));
        List<Tier> tierList = tierMapper.selectByExample(tierExample);
        //第一步删除callcenter中该队列的绑定关系
        for(int i=0;i<tierList.size();i++){
            TierInfo tierInfo = new TierInfo(queueId, tierList.get(i).getUserId()+"");
            fsManager.deleteTier(tierInfo);
        }
        //第二步删除数据库中该队列的绑定关系
        tierMapper.deleteByExample(tierExample);
        //第三步：删除数据库中该队列
        queueMapper.deleteByPrimaryKey(Long.parseLong(queueId));
        //第四步删除callcenter中的该队列
       // fsManager.deleteQueue(queueId);

        List<String> queueIdList = new ArrayList<>();
        QueueExample queueExample = new QueueExample();
        List<Queue> queueList = queueMapper.selectByExample(queueExample);
        for (Queue queues:queueList) {
            queueIdList.add(queues.getQueueId()+"");
        }
        //调用基于模板批量创建坐席、队列和绑定关系的方法，生成xml
        fsManager.initCallcenter(null,queueIdList,null);

        // 调用上传NAS的接口，得到文件下载地址，并调用lua脚本
        String fileUrl = uploadConfig(1L, fsBotConfig.getHomeDir()+"/callcenter.conf.xml");
        String other = "reload+mod_callcenter";
        fsManager.syncCallcenter(fileUrl,other);


        /**
         * 不再每次操作同步更新xml文件了，改为定时刷新xml文件
         */
//        // 调用上传NAS的接口，得到文件下载地址，并调用lua脚本
//        String fileUrl = uploadConfig(1L, fsBotConfig.getHomeDir()+"/callcenter.conf.xml");
//        fsManager.syncCallcenter(fileUrl,null);
        return true;
    }

    @Override
    public void updateQueue(String queueId, QueueInfo queueInfo,Agent agent)throws Exception{
        QueueExample queueExample = new QueueExample();
        queueExample.createCriteria().andOrgCodeEqualTo(agent.getOrgCode()).andQueueNameEqualTo(queueInfo.getQueueName()).andQueueIdNotEqualTo(Long.parseLong(queueId));
        List<Queue> queueList = queueMapper.selectByExample(queueExample);
        if (queueList != null&&queueList.size()>0) {
            log.info("不能修改为同名坐席[{}]",queueInfo.getQueueName());
            //throw new GuiyuException(ToagentserverException.EXCP_TOAGENT_QUEUE_ISIN);
            throw new Exception("0307007");
        }

        Queue queue = queueMapper.selectByPrimaryKey(Long.parseLong(queueId));
        Date date = new Date();
        queue.setQueueName(queueInfo.getQueueName());
        queue.setUpdateTime(date);
        queue.setUpdateUser(agent.getUserId());
        if(queue.getLineId()==null||queue.getLineId()!=queueInfo.getLineId()){
            FsLineVO fsLineVO = fsLineManager.getFsLine();
            queue.setLineId(queueInfo.getLineId());
            TierExample tierExample = new TierExample();
            tierExample.createCriteria().andQueueIdEqualTo(queue.getQueueId());
            List<Tier> tierList = tierMapper.selectByExample(tierExample);
            for (Tier tier:tierList) {
               Agent user = agentMapper.selectByPrimaryKey(tier.getUserId());
               if(user.getAnswerType()== EAnswerType.MOBILE.ordinal()){
                   AgentInfo agentInfo = new AgentInfo();
                   agentInfo.setAgentId(agent.getUserId() + "");
                  String[] ip = fsLineVO.getFsIp().split(":");
                  String contact = String.format("{origination_caller_id_name=%s}sofia/internal/%s@%s",queueInfo.getLineId(),user.getMobile(),ip[0]+":"+fsLineVO.getFsInPort());
                   agentInfo.setContact(contact);
                   fsManager.updateAgent(agentInfo);
               }
            }
        }
        queueMapper.updateByPrimaryKey(queue);
        /**
         * 不再每次操作同步更新xml文件了，改为定时刷新xml文件
         */
//        //调用上传NAS的接口，得到文件下载地址，并调用lua脚本
//        String fileUrl = uploadConfig(1L, fsBotConfig.getHomeDir()+"/callcenter.conf.xml");
//        fsManager.syncCallcenter(fileUrl,null);
    }

    @Override
    public Paging queryQueues(Agent agent, String queueName, Integer page, Integer size) {
        List<QueryQueue> list = new ArrayList<QueryQueue>();
        PageExample testPage = new PageExample();
        testPage.setPageNum(page);
        testPage.setPageSize(size);
        testPage.enablePaging();
        Paging paging = new Paging();
        if (agent.getUserRole() == EUserRole.ADMIN.ordinal()) {  //如果是admin用户，则根据队列的创建者来查询
            QueueExample queueExample = new QueueExample();
            if(StringUtils.isBlank(queueName)){
                queueExample.createCriteria().andOrgCodeEqualTo(agent.getOrgCode());
            }else{
                queueExample.createCriteria().andCreatorEqualTo(agent.getUserId()).andQueueNameLike(queueName);
            }
            queueExample.setOrderByClause("update_time DESC");
            List<Queue> queueListDb = queueMapper.selectByExample(queueExample);
            PageInfo<Queue> pageInfo = new PageInfo<>(queueListDb);
            List<Queue> queueList = pageInfo.getList();
            for (Queue queue : queueList) {
                QueryQueue queryQueue = new QueryQueue();
                BeanUtils.copyProperties(queue, queryQueue);
                queryQueue.setUserName(agent.getUserName());
                queryQueue.setUpdateTime(DateUtil.getStrDate(queue.getUpdateTime(),DateUtil.FORMAT_YEARMONTHDAY_HOURMINSEC));
                if (queryQueue.getLineId() != null) {
                    LineInfo lineInfo = lineInfoMapper.selectByPrimaryKey(queryQueue.getLineId());
                    if(lineInfo!=null){
                        queryQueue.setLineName(lineInfo.getLineName());
                    }
                }
                TierExample tierExample = new TierExample();
                tierExample.createCriteria().andQueueIdEqualTo(queue.getQueueId());
                queryQueue.setAgentCount(tierMapper.countByExample(tierExample));
                list.add(queryQueue);
            }
            paging.setPageNo(page);
            paging.setPageSize(size);
            paging.setTotalPage(pageInfo.getPages());
            paging.setTotalRecord(pageInfo.getTotal());
            paging.setRecords((List<Object>) (Object) list);
        } else {
            TierExample tierExample = new TierExample();
            tierExample.createCriteria().andUserIdEqualTo(agent.getUserId());
            List<Tier> tierList = tierMapper.selectByExample(tierExample);
            Tier tier = tierList.get(0);
            Queue queue = queueMapper.selectByPrimaryKey(tier.getQueueId());
            QueryQueue queryQueue = new QueryQueue();
            BeanUtils.copyProperties(queue, queryQueue);
            queryQueue.setUserName(agent.getUserName());
            queryQueue.setUpdateTime(DateUtil.getStrDate(queue.getUpdateTime(),DateUtil.FORMAT_YEARMONTHDAY_HOURMINSEC));
            if (queryQueue.getLineId() != null) {
                LineInfo lineInfo = lineInfoMapper.selectByPrimaryKey(queryQueue.getLineId());
                queryQueue.setLineName(lineInfo.getLineName());
            }
            TierExample tierExample1 = new TierExample();
            tierExample1.createCriteria().andQueueIdEqualTo(queue.getQueueId());
            queryQueue.setAgentCount(tierMapper.countByExample(tierExample1));
            list.add(queryQueue);
            paging.setPageNo(1);
            paging.setPageSize(size);
            paging.setTotalPage(1);
            paging.setTotalRecord(1L);
            paging.setRecords((List<Object>) (Object) list);
        }
        return paging;
    }


    @Override
    public QueryQueue getQueue(String queueId) {
        Queue queue = queueMapper.selectByPrimaryKey(Long.parseLong(queueId));
        QueryQueue queryQueue = new QueryQueue();
        BeanUtils.copyProperties(queue,queryQueue);
        Agent agent = agentMapper.selectByPrimaryKey(queue.getUpdateUser());
        queryQueue.setUserName(agent.getUserName());
        queryQueue.setUpdateTime(DateUtil.getStrDate(queue.getUpdateTime(),DateUtil.FORMAT_YEARMONTHDAY_HOURMINSEC));
        TierExample tierExample = new TierExample();
        tierExample.createCriteria().andQueueIdEqualTo(queue.getQueueId());
        queryQueue.setAgentCount(tierMapper.countByExample(tierExample));
        return queryQueue;
    }

    @Override
    public Queue findByQueueId(Long queueId) {
        return queueMapper.selectByPrimaryKey(queueId);
    }

    @Override
    public List<Queue> findByOrgCode(String orgCode) {
        QueueExample queueExample = new QueueExample();
        queueExample.createCriteria().andOrgCodeEqualTo(orgCode);
        return queueMapper.selectByExample(queueExample);
    }

    @Override
    public void untyingLineinfos(String lineId) {
        //1、根据lineId查询所有的坐席组
        QueueExample queueExample = new QueueExample();
        queueExample.createCriteria().andLineIdEqualTo(Integer.parseInt(lineId));
         List<Queue> queues =queueMapper.selectByExample(queueExample);
        for (Queue queue:queues) {
            //遍历队列，解绑线路
            queue.setLineId(null);
            queueMapper.updateByPrimaryKey(queue);
            //遍历队列，查询绑定关系
            TierExample tierExample = new TierExample();
            tierExample.createCriteria().andQueueIdEqualTo(queue.getQueueId());
            List<Tier> tierList =tierMapper.selectByExample(tierExample);
            //遍历绑定关系，查看坐席是否为手机接听，如果是手机接听改为网页接听并置成离线
            for (Tier tier:tierList) {
                 Agent agent = agentMapper.selectByPrimaryKey(tier.getUserId());
                if(agent.getAnswerType()==1){
                    AgentInfo agentInfo = new AgentInfo();
                    agentInfo.setContact("${verto_contact(" + agent.getUserId() + ")}");
                    agentInfo.setStatus(AgentStatus.Logged_Out);
                    agentInfo.setAgentId(agent.getUserId() + "");
                    fsManager.addAgent(agentInfo);
                    agent.setAnswerType(EAnswerType.WEB.ordinal());
                    agent.setUserState(EUserState.OFFLINE.ordinal());
                    agentMapper.updateByPrimaryKey(agent);
                }
            }
        }
    }
}
