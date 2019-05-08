package com.guiji.service.impl;

import com.github.pagehelper.PageInfo;
import com.guiji.auth.api.IOrg;
import com.guiji.callcenter.dao.AgentMapper;
import com.guiji.callcenter.dao.LineInfoMapper;
import com.guiji.callcenter.dao.QueueMapper;
import com.guiji.callcenter.dao.TierMapper;
import com.guiji.callcenter.dao.entity.*;
import com.guiji.callcenter.helper.PageExample;
import com.guiji.clm.api.LineMarketRemote;
import com.guiji.clm.model.SipLineVO;
import com.guiji.component.result.Result;
import com.guiji.config.FsBotConfig;
import com.guiji.entity.EAnswerType;
import com.guiji.entity.EUserState;
import com.guiji.fs.FsManager;
import com.guiji.fs.pojo.AgentStatus;
import com.guiji.fsmanager.entity.FsLineInfoVO;
import com.guiji.manager.EurekaManager;
import com.guiji.manager.FsLineManager;
import com.guiji.service.AgentService;
import com.guiji.service.QueueService;
import com.guiji.user.dao.entity.SysOrganization;
import com.guiji.util.DateUtil;
import com.guiji.util.FileUtil;
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
    @Autowired
    LineMarketRemote lineMarketRemote;
    @Autowired
    AgentService agentService;
    @Autowired
    IOrg iOrg;

    @Override
    public boolean addQueue(QueueInfo queueInfo, String orgCode, Long customerId) throws Exception {
        QueueExample queueExample = new QueueExample();
        queueExample.createCriteria().andOrgCodeEqualTo(orgCode).andQueueNameEqualTo(queueInfo.getQueueName());
        List<Queue> queueList = queueMapper.selectByExample(queueExample);
        if (queueList != null && queueList.size() > 0) {
            log.info("不能创建同名坐席[{}]", queueInfo.getQueueName());
            //  throw new GuiyuException(ToagentserverException.EXCP_TOAGENT_QUEUE_ISIN);
            throw new Exception("0307007");
        }
        Date date = new Date();
        Queue queue = new Queue();
        //得到创建者
        AgentExample example = new AgentExample();
        example.createCriteria().andCustomerIdEqualTo(customerId);
        List<Agent> agentList = agentMapper.selectByExample(example);
        if (agentList.size() > 0) {
            Agent agent = agentList.get(0);
            queue.setCreator(agent.getUserId());
            queue.setUpdateUser(agent.getUserId());
        }
        queue.setQueueName(queueInfo.getQueueName());
        queue.setCreateTime(date);
        queue.setUpdateTime(date);
        queue.setOrgCode(orgCode);
        queue.setLineId(queueInfo.getLineId());
        queueMapper.insert(queue);
        List<String> queueIdList = new ArrayList<>();
        QueueExample queueExample1 = new QueueExample();
        List<Queue> queueList1 = queueMapper.selectByExample(queueExample1);
        for (Queue queues : queueList1) {
            queueIdList.add(queues.getQueueId() + "");
        }
        //调用基于模板批量创建坐席、队列和绑定关系的方法，生成xml
        fsManager.initCallcenter(null, queueIdList, null);
        // 调用上传NAS的接口，得到文件下载地址，并调用lua脚本
        String fileUrl = FileUtil.uploadConfig(1L, fsBotConfig.getHomeDir() + "/callcenter.conf.xml", eurekaManager.getAppName());
        String other = "reload+mod_callcenter";
        fsManager.syncCallcenter(fileUrl, other);
        return true;
    }

    @Override
    public boolean deleteQueue(String queueId) {
        TierExample tierExample = new TierExample();
        tierExample.createCriteria().andQueueIdEqualTo(Long.parseLong(queueId));
        List<Tier> tierList = tierMapper.selectByExample(tierExample);
        //第一步删除callcenter中该队列的绑定关系
        for (int i = 0; i < tierList.size(); i++) {
            TierInfo tierInfo = new TierInfo(queueId, tierList.get(i).getUserId() + "");
            fsManager.deleteTier(tierInfo);
        }
        //第二步删除数据库中该队列的绑定关系
        tierMapper.deleteByExample(tierExample);
        //第三步：删除数据库中该队列
        queueMapper.deleteByPrimaryKey(Long.parseLong(queueId));
        //第四步删除callcenter中的该队列
        List<String> queueIdList = new ArrayList<>();
        QueueExample queueExample = new QueueExample();
        List<Queue> queueList = queueMapper.selectByExample(queueExample);
        for (Queue queues : queueList) {
            queueIdList.add(queues.getQueueId() + "");
        }
        //调用基于模板批量创建坐席、队列和绑定关系的方法，生成xml
        fsManager.initCallcenter(null, queueIdList, null);
        // 调用上传NAS的接口，得到文件下载地址，并调用lua脚本
        String fileUrl = FileUtil.uploadConfig(1L, fsBotConfig.getHomeDir() + "/callcenter.conf.xml", eurekaManager.getAppName());
        String other = "reload+mod_callcenter";
        fsManager.syncCallcenter(fileUrl, other);
        return true;
    }

    @Override
    public void updateQueue(String queueId, QueueInfo queueInfo, Long customerId, String orgCode) throws Exception {
        QueueExample queueExample = new QueueExample();
        queueExample.createCriteria().andOrgCodeEqualTo(orgCode).andQueueNameEqualTo(queueInfo.getQueueName()).andQueueIdNotEqualTo(Long.parseLong(queueId));
        List<Queue> queueList = queueMapper.selectByExample(queueExample);
        if (queueList != null && queueList.size() > 0) {
            log.info("不能修改为同名坐席[{}]", queueInfo.getQueueName());
            //throw new GuiyuException(ToagentserverException.EXCP_TOAGENT_QUEUE_ISIN);
            throw new Exception("0307007");
        }
        Queue queue = queueMapper.selectByPrimaryKey(Long.parseLong(queueId));
        Date date = new Date();
        queue.setQueueName(queueInfo.getQueueName());
        queue.setUpdateTime(date);

        //得到创建者
        AgentExample example = new AgentExample();
        example.createCriteria().andCustomerIdEqualTo(customerId);
        List<Agent> agentList = agentMapper.selectByExample(example);
        if (agentList.size() > 0) {
            Agent agent = agentList.get(0);
            queue.setUpdateUser(agent.getUserId());
        }

        if (queue.getLineId() == null || queue.getLineId() != queueInfo.getLineId()) {
            FsLineInfoVO fsLineVO = fsLineManager.getFsLine(queueInfo.getLineId());
            queue.setLineId(queueInfo.getLineId());
            TierExample tierExample = new TierExample();
            tierExample.createCriteria().andQueueIdEqualTo(queue.getQueueId());
            List<Tier> tierList = tierMapper.selectByExample(tierExample);
            for (Tier tier : tierList) {
                Agent user = agentMapper.selectByPrimaryKey(tier.getUserId());
                if (user.getAnswerType() == EAnswerType.MOBILE.ordinal()) {
                    AgentInfo agentInfo = new AgentInfo();
                    agentInfo.setAgentId(user.getUserId() + "");
                    String[] ip = fsLineVO.getFsIp().split(":");
                    String contact = String.format("{origination_caller_id_name=%s}sofia/internal/%s@%s", queueInfo.getLineId(), user.getMobile(), ip[0] + ":" + fsLineVO.getFsInPort());
                    agentInfo.setContact(contact);
                    fsManager.updateAgent(agentInfo);
                }
            }
        }
        queueMapper.updateByPrimaryKey(queue);
    }

    @Override
    public Paging queryQueues(String queueName, Integer page, Integer size, String systemUserId, String orgCode, int authLevel, Long customerId) throws Exception {
        Agent agent = agentService.getAgentByCustomerId(customerId);
        if (agent == null) {
            throw new Exception("0307014");
        }
        Paging paging = new Paging();
        List<QueryQueue> list = new ArrayList<QueryQueue>();
        if (authLevel == 1) {
            //只能查自己的
            TierExample tierExample = new TierExample();
            tierExample.createCriteria().andUserIdEqualTo(agent.getUserId());
            List<Tier> tierList = tierMapper.selectByExample(tierExample);
            Tier tier = tierList.get(0);
            Queue queue = queueMapper.selectByPrimaryKey(tier.getQueueId());
            QueryQueue queryQueue = new QueryQueue();
            BeanUtils.copyProperties(queue, queryQueue);
            //查询得到坐席组对应的企业名
            queryQueue.setOrgName(orgName(queue.getOrgCode()));
            if (queue.getUpdateUser() != null) {
                Agent create = agentMapper.selectByPrimaryKey(queue.getUpdateUser());
                if (create != null) {
                    queryQueue.setUserName(create.getUserName());
                }
            }
            queryQueue.setUpdateTime(DateUtil.getStrDate(queue.getUpdateTime(), DateUtil.FORMAT_YEARMONTHDAY_HOURMINSEC));
            if (queryQueue.getLineId() != null) {
                queryQueue.setLineName(getLineName(queryQueue.getLineId(), systemUserId));
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
        } else {// authLevel大于1的
            //调用根据坐席组名，角色，orgcode查询坐席组的方法
            List<Queue> queueListDb = queryQueuesSub(page, size, queueName, orgCode, authLevel);
            PageInfo<Queue> pageInfo = new PageInfo<>(queueListDb);
            List<Queue> queueList = pageInfo.getList();
            for (Queue queue : queueList) {
                QueryQueue queryQueue = new QueryQueue();
                BeanUtils.copyProperties(queue, queryQueue);
                //查询得到坐席组对应的企业名
                queryQueue.setOrgName(orgName(queue.getOrgCode()));
                if (queue.getUpdateUser() != null) {
                    Agent create = agentMapper.selectByPrimaryKey(queue.getUpdateUser());
                    if (create != null) {
                        queryQueue.setUserName(create.getUserName());
                    }
                }
                queryQueue.setUpdateTime(DateUtil.getStrDate(queue.getUpdateTime(), DateUtil.FORMAT_YEARMONTHDAY_HOURMINSEC));
                if (queryQueue.getLineId() != null) {
                    queryQueue.setLineName(getLineName(queryQueue.getLineId(), systemUserId));
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
        }
        return paging;
    }

    /**
     * 根据坐席组名，角色，orgcode查询坐席组
     *
     * @param queueName
     * @param orgCode
     * @param authLevel
     * @return
     */
    public List<Queue> queryQueuesSub(Integer page, Integer size, String queueName, String orgCode, int authLevel) {
        PageExample testPage = new PageExample();
        testPage.setPageNum(page);
        testPage.setPageSize(size);
        testPage.enablePaging();
        QueueExample queueExample = new QueueExample();
        if (authLevel == 2) { //查询本组织
            if (StringUtils.isBlank(queueName)) {
                queueExample.createCriteria().andOrgCodeEqualTo(orgCode);
            } else {
                queueExample.createCriteria().andOrgCodeEqualTo(orgCode).andQueueNameLike(queueName);
            }
        } else if (authLevel == 3) {  //查询本组织及下级组织
            if (StringUtils.isBlank(queueName)) {
                queueExample.createCriteria().andOrgCodeLike(orgCode + "%");
            } else {
                queueExample.createCriteria().andOrgCodeLike(orgCode + "%").andQueueNameLike(queueName);
            }
        }
        queueExample.setOrderByClause("update_time DESC");
        List<Queue> queueListDb = queueMapper.selectByExample(queueExample);
        return queueListDb;
    }


    @Override
    public QueryQueue getQueue(String queueId) {
        Queue queue = queueMapper.selectByPrimaryKey(Long.parseLong(queueId));
        QueryQueue queryQueue = new QueryQueue();
        BeanUtils.copyProperties(queue, queryQueue);
        Agent agent = agentMapper.selectByPrimaryKey(queue.getUpdateUser());
        queryQueue.setUserName(agent.getUserName());
        queryQueue.setUpdateTime(DateUtil.getStrDate(queue.getUpdateTime(), DateUtil.FORMAT_YEARMONTHDAY_HOURMINSEC));
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
        List<Queue> queues = queueMapper.selectByExample(queueExample);
        if (queues != null && queues.size() > 0) {
            for (Queue queue : queues) {
                //遍历队列，解绑线路
                queue.setLineId(null);
                queueMapper.updateByPrimaryKey(queue);
                //遍历队列，查询绑定关系
                TierExample tierExample = new TierExample();
                tierExample.createCriteria().andQueueIdEqualTo(queue.getQueueId());
                List<Tier> tierList = tierMapper.selectByExample(tierExample);
                //遍历绑定关系，查看坐席是否为手机接听，如果是手机接听改为网页接听并置成离线
                if (tierList != null && tierList.size() > 0) {
                    for (Tier tier : tierList) {
                        Agent agent = agentMapper.selectByPrimaryKey(tier.getUserId());
                        if (agent.getAnswerType() == 1) {
                            AgentInfo agentInfo = new AgentInfo();
                            agentInfo.setContact("${verto_contact(" + agent.getUserId() + ")}");
                            agentInfo.setStatus(AgentStatus.Logged_Out);
                            agentInfo.setAgentId(agent.getUserId() + "");
                            fsManager.updateAgent(agentInfo);
                            agent.setAnswerType(EAnswerType.WEB.ordinal());
                            agent.setUserState(EUserState.OFFLINE.ordinal());
                            agentMapper.updateByPrimaryKey(agent);
                        }
                    }
                }
            }
        }
    }

    @Override
    public void switchLineinfos(String lineId) {
        //1、根据lineId查询所有的坐席组
        QueueExample queueExample = new QueueExample();
        queueExample.createCriteria().andLineIdEqualTo(Integer.parseInt(lineId));
        List<Queue> queues = queueMapper.selectByExample(queueExample);
        if (queues != null && queues.size() > 0) {
            FsLineInfoVO fsLineVO = fsLineManager.getFsLine(Integer.parseInt(lineId));//根据lineId获取fsline消息
            for (Queue queue : queues) {
                //遍历队列，查询绑定关系
                TierExample tierExample = new TierExample();
                tierExample.createCriteria().andQueueIdEqualTo(queue.getQueueId());
                List<Tier> tierList = tierMapper.selectByExample(tierExample);
                //遍历绑定关系，查看坐席是否为手机接听，如果是手机接听则修改contact
                if (tierList != null && tierList.size() > 0) {
                    for (Tier tier : tierList) {
                        Agent agent = agentMapper.selectByPrimaryKey(tier.getUserId());
                        if (agent.getAnswerType() == 1) {
                            AgentInfo agentInfo = new AgentInfo();
                            String[] ip = fsLineVO.getFsIp().split(":");
                            String contact = String.format("{origination_caller_id_name=%s}sofia/internal/%s@%s", lineId, agent.getMobile(), ip[0] + ":" + fsLineVO.getFsInPort());
                            agentInfo.setContact(contact);
                            if (agent.getUserState() == EUserState.OFFLINE.ordinal()) {
                                agentInfo.setStatus(AgentStatus.Logged_Out);
                            } else if (agent.getUserState() == EUserState.ONLINE.ordinal()) {
                                agentInfo.setStatus(AgentStatus.Available);
                            }
                            agentInfo.setAgentId(agent.getUserId() + "");
                            fsManager.updateAgent(agentInfo);
                        }
                    }
                }
            }
        }
    }

    /**
     * 查询得到线路的名称
     *
     * @param lineId
     * @param systemUserId
     * @return
     */
    public String getLineName(int lineId, String systemUserId) {
        Result.ReturnData<SipLineVO> result;
        try {
            result = lineMarketRemote.queryUserSipLineByLineId(systemUserId, lineId);
            if (result.getCode().equals("0") && result.getBody() != null) {
                log.info("根据userid:[{}]和lineId:[{}]从线路市场获取线路名称结果：[{}]", systemUserId, lineId, result.getBody().getLineName());
                return result.getBody().getLineName();
            } else {
                LineInfo lineInfo = lineInfoMapper.selectByPrimaryKey(lineId);
                if (lineInfo != null) {
                    return lineInfo.getLineName();
                }
            }
        } catch (Exception e) {
            log.info("调用线路市场获取线路名称失败");
            LineInfo lineInfo = lineInfoMapper.selectByPrimaryKey(lineId);
            if (lineInfo != null) {
                return lineInfo.getLineName();
            }
        }
        return "";
    }

    /**
     * 根据orgCode查询orgName
     *
     * @param orgCode
     * @return
     */
    public String orgName(String orgCode) {
        try {
            Result.ReturnData<SysOrganization> result = iOrg.getOrgByCode(orgCode);
            if (result.getCode().equals("0") && result.getBody() != null) {
                return result.getBody().getName();
            }
        } catch (Exception e) {
            log.error("调用auth接口获取企业名接口失败[{}]", e.toString());
            return "";
        }
        return "";
    }
}
