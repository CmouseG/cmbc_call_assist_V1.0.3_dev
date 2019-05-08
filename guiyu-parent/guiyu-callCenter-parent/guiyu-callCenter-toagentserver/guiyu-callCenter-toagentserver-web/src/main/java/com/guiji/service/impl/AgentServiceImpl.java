package com.guiji.service.impl;

import com.github.pagehelper.PageInfo;
import com.guiji.callcenter.dao.AgentMapper;
import com.guiji.callcenter.dao.CallOutPlanMapper;
import com.guiji.callcenter.dao.QueueMapper;
import com.guiji.callcenter.dao.TierMapper;
import com.guiji.callcenter.dao.entity.*;
import com.guiji.callcenter.dao.entity.Queue;
import com.guiji.callcenter.helper.PageExample;
import com.guiji.common.exception.GuiyuException;
import com.guiji.config.FsBotConfig;
import com.guiji.config.FsConfig;
import com.guiji.entity.*;
import com.guiji.fs.FreeSWITCH;
import com.guiji.fs.FsManager;
import com.guiji.fs.pojo.AgentStatus;
import com.guiji.fs.pojo.GlobalVar;
import com.guiji.fsmanager.entity.FsLineInfoVO;
import com.guiji.manager.EurekaManager;
import com.guiji.manager.FsLineManager;
import com.guiji.service.AgentService;
import com.guiji.service.CallPlanService;
import com.guiji.service.QueueService;
import com.guiji.toagentserver.entity.AgentMembrVO;
import com.guiji.util.DateUtil;
import com.guiji.util.FileUtil;
import com.guiji.web.request.AgentInfo;
import com.guiji.web.request.AgentRequest;
import com.guiji.web.request.TierInfo;
import com.guiji.web.response.Paging;
import com.guiji.web.response.QueryAgent;
import com.guiji.web.response.QueryCalls;
import com.guiji.web.response.QueryUser;
import com.google.common.base.Strings;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;

/**
 * @Auther: 魏驰
 * @Date: 2018/12/17 14:34
 * @Project：ccserver
 * @Description:
 */
@Slf4j
@Service
public class AgentServiceImpl implements AgentService {
    @Autowired
    FsManager fsManager;

    @Autowired
    AgentMapper agentMapper;

    @Autowired
    AgentService agentService;

    @Autowired
    TierMapper tierMapper;

    @Autowired
    QueueMapper queueMapper;

    @Autowired
    CallOutPlanMapper callOutPlanMapper;

    @Autowired
    CallPlanService callPlanService;

    @Autowired
    QueueService queueService;

    @Autowired
    FsConfig fsConfig;

    @Autowired
    EurekaManager eurekaManager;

    @Autowired
    FsBotConfig fsBotConfig;

    @Autowired
    FsLineManager fsLineManager;

    @Override
    public boolean updateAgent(String agentId, AgentRequest request,Long customerId) throws Exception {
        Agent create = agentService.getAgentByCustomerId(customerId);
        if(create==null){
            throw new Exception("0307014");
        }
        Agent agent = agentMapper.selectByPrimaryKey(Long.parseLong(agentId));//根据坐席ID查询用户信息
        Date date = new Date();
        if (!Strings.isNullOrEmpty(request.getAgentName()))
            agent.setUserName(request.getAgentName());

        if (request.getAnswerType() != null)
            agent.setAnswerType(request.getAnswerType().ordinal());

        if (request.getAgentState() != null)
            agent.setUserState(request.getAgentState().ordinal());

        if (!Strings.isNullOrEmpty(request.getMobile()))
            agent.setMobile(request.getMobile());
        agent.setUpdateUser(create.getUserId());
        agent.setOrgCode(create.getOrgCode());
        agent.setUpdateTime(date);
        //第一步存入数据库
        agentMapper.updateByPrimaryKey(agent);

        //第二步修改freeswitch用户,修改freeswitch坐席
        AgentInfo agentInfo = new AgentInfo();
        agentInfo.setAgentId(agent.getUserId() + "");
        agentInfo.setPassword(request.getAgentPwd());
        if (request.getAgentState() != null) {
            switch (request.getAgentState()) {
                case OFFLINE:
                    agentInfo.setStatus(AgentStatus.Logged_Out);
                    break;
                case ONLINE:
                    agentInfo.setStatus(AgentStatus.Available);
                    break;
            }
        }
        if (agent.getAnswerType() != null) {
            if (agent.getAnswerType() == EAnswerType.WEB.ordinal()) {
                agentInfo.setContact("${verto_contact(" + agent.getUserId() + ")}");
            } else if (agent.getAnswerType() == EAnswerType.MOBILE.ordinal()) {
                if (StringUtils.isBlank(agent.getMobile())) {
                    // throw new GuiyuException(ToagentserverException.EXCP_TOAGENT_ANSWERTYPE_NONEMOBILE);
                    throw new Exception("0307006");
                }
                Queue queue = queueMapper.selectByPrimaryKey(request.getQueueId());
                if (queue.getLineId() == null) {
                    //throw new GuiyuException(ToagentserverException.EXCP_TOAGENT_QUEUE_NO_LINE);
                    throw new Exception("0307011");
                }
                FsLineInfoVO fsLineVO = fsLineManager.getFsLine(queue.getLineId());
                String[] ip = fsLineVO.getFsIp().split(":");
                String contact = String.format("{origination_caller_id_name=%s}sofia/internal/%s@%s", queue.getLineId(), request.getMobile(), ip[0] + ":" + fsLineVO.getFsInPort());
                agentInfo.setContact(contact);
            }
        }
        fsManager.updateAgent(agentInfo);
        //第三步绑定,如果queueId不为空，则代表要切换绑定关系
        if (request.getQueueId() != null) {
            //调用切换绑定关系的方法
            switchTier(request.getQueueId(),agent.getUserId(),create.getUserId(),date);
        }
        return true;
    }

    /**
     * 切换绑定关系
     * 判断两次队列Id是否一样，一样的话就不动，不一样的话就先删除原来的绑定关系，再创建新的绑定关系
     * @param queueId
     * @param agentId
     * @param createAgentId
     * @param date
     */
    public void switchTier(Long queueId,Long agentId,Long createAgentId,Date date){
        TierExample tierExample = new TierExample();
        tierExample.createCriteria().andUserIdEqualTo(agentId);
        List<Tier> tierRes = tierMapper.selectByExample(tierExample);
        if (tierRes != null&&tierRes.size()>0) {
            Tier tier = tierRes.get(0);
            if (queueId != tier.getQueueId()) {   //两次的队列id不一样
                //删除原来的绑定关系
                TierInfo tierInfo = new TierInfo(tier.getQueueId() + "", agentId + "");
                fsManager.deleteTier(tierInfo);
                //创建新的绑定关系
                TierInfo tierInfoNew = new TierInfo();
                tierInfoNew.setAgentId(agentId + "");
                tierInfoNew.setQueueId(queueId + "");
                fsManager.addTier(tierInfoNew);
                //将绑定关系更新到数据库
                tier.setQueueId(queueId);
                tier.setUpdateTime(date);
                tier.setUpdateUser(createAgentId);
                tierMapper.updateByPrimaryKey(tier);
            }
        }
    }

    @Override
    public boolean agentState(AgentRequest request) {
        Agent agent = agentMapper.selectByPrimaryKey(request.getUserId());//根据坐席ID查询用户信息
        AgentInfo agentInfo = new AgentInfo();
        agentInfo.setAgentId(agent.getUserId() + "");
        agentInfo.setPassword(request.getAgentPwd());
        switch (request.getAgentState()) {
            case OFFLINE:
                agentInfo.setStatus(AgentStatus.Logged_Out);
                agent.setUserState(EUserState.OFFLINE.ordinal());
                break;
            case ONLINE:
                agentInfo.setStatus(AgentStatus.Available);
                agent.setUserState(EUserState.ONLINE.ordinal());
                break;
        }
        boolean result = fsManager.updateAgentState(agentInfo);
        if (!result) {
            return false;
        }
        agentMapper.updateByPrimaryKey(agent);
        return true;
    }

    @Override
    public QueryAgent getAgent(String userId) {
        QueryAgent agent = new QueryAgent();
        Agent user = agentMapper.selectByPrimaryKey(Long.parseLong(userId));//根据坐席ID查询用户信息
        BeanUtils.copyProperties(user, agent);
        if (user.getUserState() == 0) {
            agent.setAgentState(EUserState.OFFLINE);
        } else {
            agent.setAgentState(EUserState.ONLINE);
        }
        if (user.getAnswerType() == 0) {
            agent.setAnswerType(EAnswerType.MOBILE);
        } else {
            agent.setAnswerType(EAnswerType.WEB);
        }
        agent.setCreateDate(DateUtil.getStrDate(user.getCreateTime(), DateUtil.FORMAT_YEARMONTHDAY_HOURMINSEC));
        agent.setAgentName(user.getUserName());
        agent.setAgentPwd(user.getUserPwd());
        Agent create = agentMapper.selectByPrimaryKey(user.getCreator());//根据坐席ID查询用户信息
        agent.setCreatorName(create.getUserName());
        TierExample tierExample = new TierExample();
        tierExample.createCriteria().andUserIdEqualTo(Long.parseLong(userId));
        List<Tier> tierRes = tierMapper.selectByExample(tierExample);
        if (tierRes != null) {
            Tier tier = tierRes.get(0);
            agent.setQueueId(tier.getQueueId());
            Queue queue = queueMapper.selectByPrimaryKey(tier.getQueueId());
            if (queue != null) {
                BeanUtils.copyProperties(queue, agent);
            }
        }
        return agent;
    }

    @Override
    public Paging getAllAgent(Long customerId, String crmLoginId, String queueId, Integer page, Integer size,int authLevel,String orgCode) throws Exception{
        Agent user = agentService.getAgentByCustomerId(customerId);
        if(user==null){
            throw new Exception("0307014");
        }
        List<QueryAgent> queryAgentList = new ArrayList<>();
        Paging paging = new Paging();
        List<Agent> list = new ArrayList();
        if(authLevel==1){
            if(StringUtils.isBlank(crmLoginId)||(!StringUtils.isBlank(crmLoginId)&&user.getCrmLoginId().indexOf(crmLoginId)!=-1)){
                list.add(user);
            }
        }else{
            list = getAgents(queueId,authLevel,orgCode, page,  size, crmLoginId);
        }
        PageInfo<Agent> pageInfo = new PageInfo<>(list);
        for (Agent agent : pageInfo.getList()) {
            QueryAgent queryAgent = new QueryAgent();
            BeanUtils.copyProperties(agent, queryAgent);
            if (agent.getUserState() == 0) {
                queryAgent.setAgentState(EUserState.OFFLINE);
            } else {
                if (agent.getAnswerType() == 0) {
                    if (fsManager.getVertoStatus(agent.getUserId() + "")) {
                        queryAgent.setAgentState(EUserState.ONLINE);
                    } else {
                        queryAgent.setAgentState(EUserState.OFFLINE);
                    }
                } else {
                    queryAgent.setAgentState(EUserState.ONLINE);
                }
            }
            if (agent.getAnswerType() == 0) {
                queryAgent.setAnswerType(EAnswerType.WEB);
            } else {
                queryAgent.setAnswerType(EAnswerType.MOBILE);
            }
            queryAgent.setAgentName(agent.getUserName());
            queryAgent.setCrmLoginId(agent.getCrmLoginId());
            queryAgent.setAgentPwd(agent.getUserPwd());
            queryAgent.setCreateDate(DateUtil.getStrDate(agent.getUpdateTime(), DateUtil.FORMAT_YEARMONTHDAY_HOURMINSEC));
            // 修改者这个值
            if(agent.getUpdateUser()!=null){
                Agent create = agentMapper.selectByPrimaryKey(agent.getUpdateUser());
                if(create!=null){
                    queryAgent.setCreatorName(create.getUserName());
                    queryAgent.setCreator(agent.getUpdateUser());
                }
            }
            if (!Strings.isNullOrEmpty(queueId)) {
                Queue queue = queueMapper.selectByPrimaryKey(Long.parseLong(queueId));
                queryAgent.setQueueId(Long.parseLong(queueId));
                queryAgent.setQueueName(queue.getQueueName());
            } else {
                TierExample tierExample = new TierExample();
                tierExample.createCriteria().andUserIdEqualTo(agent.getUserId());
                List<Tier> tierRes = tierMapper.selectByExample(tierExample);
                if (tierRes != null && tierRes.size() > 0) {
                    Tier tier = tierRes.get(0);
                    queryAgent.setQueueId(tier.getQueueId());
                    Queue queue = queueMapper.selectByPrimaryKey(tier.getQueueId());
                    if (queue != null) {
                        queryAgent.setQueueName(queue.getQueueName());
                    }
                }
            }
            queryAgentList.add(queryAgent);
        }
        paging.setPageNo(page);
        paging.setPageSize(size);
        paging.setTotalPage(pageInfo.getPages());
        paging.setTotalRecord(pageInfo.getTotal());
        paging.setRecords((List<Object>) (Object) queryAgentList);
        return paging;
    }

    /**
     * 根据条件查询坐席
     *
     * @param queueId
     * @param authLevel
     * @param orgCode
     * @param page
     * @param size
     * @param crmLoginId
     * @return
     */
    public List<Agent> getAgents(String queueId, int authLevel, String orgCode, Integer page, Integer size, String crmLoginId) {
        PageExample testPage = new PageExample();
        testPage.setPageNum(page);
        testPage.setPageSize(size);
        testPage.enablePaging();
        AgentExample example = new AgentExample();
        if (!Strings.isNullOrEmpty(queueId)) {
            List<Long> userIds = getQueueAgentIds(queueId);
            if(userIds.size()>0){
                if (!Strings.isNullOrEmpty(crmLoginId)) {
                    example.createCriteria().andUserIdIn(userIds).andCrmLoginIdLike("%"+crmLoginId+"%");
                } else {
                    example.createCriteria().andUserIdIn(userIds);
                }
            } else {//坐席组中没有分配坐席
                example.createCriteria().andUserIdEqualTo(0L);
            }
        } else { //如果没有传queueId
            if (authLevel == 2) {
                if (!Strings.isNullOrEmpty(crmLoginId)) {
                    example.createCriteria().andOrgCodeEqualTo(orgCode).andCrmLoginIdLike("%"+crmLoginId+"%");
                }else{
                    example.createCriteria().andOrgCodeEqualTo(orgCode);
                }
            } else if (authLevel == 3) {
                if (!Strings.isNullOrEmpty(crmLoginId)) {
                    example.createCriteria().andOrgCodeLike(orgCode + "%").andCrmLoginIdLike("%"+crmLoginId+"%");
                }else{
                    example.createCriteria().andOrgCodeLike(orgCode + "%");
                }
            }
        }
        example.setOrderByClause("update_time DESC");
        List<Agent> list = agentMapper.selectByExample(example);
        return list;
    }

    /**
     * 根据队列id查询队列中的成员
     * @param queueId
     * @return
     */
    public List<Long> getQueueAgentIds(String queueId) {
        List<Long> userIds = new ArrayList<>();
        TierExample tierExample = new TierExample();
        tierExample.createCriteria().andQueueIdEqualTo(Long.parseLong(queueId));
        List<Tier> tierRes = tierMapper.selectByExample(tierExample);
        if (tierRes != null && tierRes.size() > 0) { //坐席组中有坐席
            for (Tier tier : tierRes) {
                userIds.add(tier.getUserId());
            }
        }
        return userIds;
    }

    @Override
    public QueryCalls agentcalls(String userId) {
        CallOutPlanExample callOutPlanExample = new CallOutPlanExample();
        //  callOutPlanExample.createCriteria().andAgentIdEqualTo(userId).andAgentAnswerTimeGreaterThan();
        List<CallOutPlan> list = callOutPlanMapper.selectByExample(callOutPlanExample);
        QueryCalls queryCalls = new QueryCalls();
        queryCalls.setUserId(userId);
        queryCalls.setAnsweredCount(list.size());
        return queryCalls;
    }

    @Override
    public QueryUser getUser(Long customerId){
        QueryUser queryUser = new QueryUser();
        AgentExample agentExample = new AgentExample();
        agentExample.createCriteria().andCustomerIdEqualTo(customerId);
        List<Agent> agentList = agentMapper.selectByExample(agentExample);
        if(agentList.size()==0){
           return null;
        }
        Agent agent = agentList.get(0);
        BeanUtils.copyProperties(agent, queryUser);
        queryUser.setCreateDate(DateUtil.getStrDate(agent.getCreateTime(), DateUtil.FORMAT_YEARMONTHDAY_HOURMINSEC));
        FreeSWITCH freeswitch = fsManager.getFS();
        GlobalVar globalVar = freeswitch.getGlobalVar();
        queryUser.setWssUrl(fsConfig.getWssUrl() + ":" + globalVar.getVerto_wss_port());
        if (agent.getUserState() == 0) {
            queryUser.setUserState(EUserState.OFFLINE);
        } else {
            queryUser.setUserState(EUserState.ONLINE);
        }
        if (agent.getAnswerType() == 0) {
            queryUser.setAnswerType(EAnswerType.WEB);
        } else {
            queryUser.setAnswerType(EAnswerType.MOBILE);
        }
        TierExample tierExample = new TierExample();
        tierExample.createCriteria().andUserIdEqualTo(agent.getUserId());
        List<Tier> tierRes = tierMapper.selectByExample(tierExample);
        if (tierRes != null && tierRes.size() > 0) {
            Tier tier = tierRes.get(0);
            Queue queue = queueMapper.selectByPrimaryKey(tier.getQueueId());
            if (queue != null) {
                BeanUtils.copyProperties(queue, queryUser);
            }
        }
        return queryUser;
    }

    @Override
    public void update(Agent agent) {
        agentMapper.updateByPrimaryKey(agent);
    }

    /**
     * 根据坐席Id查看该坐席是否已经在verto中登录（已登录返回true）
     *
     * @param customerId
     * @return
     */
    @Override
    public boolean isAgentLogin(Long customerId) throws Exception{
        Agent agent = agentService.getAgentByCustomerId(customerId);
        if(agent==null){
            throw new Exception("0307014");
        }
        boolean state = fsManager.getVertoStatus(agent.getUserId() + "");
        if (!state) {//如果登录了，修改callcenter中坐席的状态为登录
            AgentInfo agentInfo = new AgentInfo();
            agentInfo.setAgentId(agent.getUserId() + "");
            agentInfo.setStatus(AgentStatus.Available);
            fsManager.updateAgentState(agentInfo);
        }
        if (agent.getUserState() == EUserState.OFFLINE.ordinal()) {
            agent.setUserState(EUserState.ONLINE.ordinal());
            agentMapper.updateByPrimaryKey(agent);
        }
        return state;
    }

    @Override
    public boolean agentVertoState(Agent agent) {
        return fsManager.getVertoStatus(agent.getUserId() + "");
    }

    @Override
    public boolean isAgentBusy(String agentId) {
        CallPlan callPlan = callPlanService.findByAgentId(agentId);
        return callPlan != null && callPlan.getCallId() != null;
    }

    @Override
    public void alertToLogout(Agent agent) {
        log.info("开始给座席[{}]发送登出消息", agent.getUserId());
        VChatMsg msg = VChatMsg.logoutInstance();
        fsManager.vchat(agent.getUserId().toString(), msg.toBase64());
    }

    @Override
    public void initCallcenter() {
        List<AgentInfo> agentInfoList = new ArrayList<>();
        List<String> queueIdList = new ArrayList<>();
        List<TierInfo> tierInfoList = new ArrayList<>();
        Map<Long, Integer> queue_line = new HashMap<>();//用于存储坐席组对应的lineId
        Map<Long, Long> agent_queue = new HashMap<>();//用于存储坐席对应的坐席组Id
        //查询所有的群组，组装成queueIdList
        QueueExample queueExample = new QueueExample();
        List<Queue> queueList = queueMapper.selectByExample(queueExample);
        for (Queue queue : queueList) {
            queueIdList.add(queue.getQueueId() + "");
            queue_line.put(queue.getQueueId(), queue.getLineId());
        }
        //查询所有的tier，组装成tierInfoList
        TierExample tierExample = new TierExample();
        List<Tier> tierList = tierMapper.selectByExample(tierExample);
        for (Tier tier : tierList) {
            TierInfo tierInfo = new TierInfo();
            tierInfo.setAgentId(tier.getUserId() + "");
            tierInfo.setQueueId(tier.getQueueId() + "");
            tierInfoList.add(tierInfo);
            agent_queue.put(tier.getUserId(), tier.getQueueId());
        }
        //查询所有的坐席，组装成agentInfoList
        AgentExample agentExample = new AgentExample();
        List<Agent> agentList = agentMapper.selectByExample(agentExample);
        for (Agent agent : agentList) {
            AgentInfo agentInfo = new AgentInfo();
            agentInfo.setAgentId(agent.getUserId() + "");
            if (agent.getUserState() == EUserState.OFFLINE.ordinal()) {
                agentInfo.setStatus(AgentStatus.Logged_Out);
            } else if (agent.getUserState() == EUserState.ONLINE.ordinal()) {
                agentInfo.setStatus(AgentStatus.Available);
            }
            if (agent.getAnswerType() == EAnswerType.WEB.ordinal()) {
                agentInfo.setContact("${verto_contact(" + agent.getUserId() + ")}");
            } else if (agent.getAnswerType() == EAnswerType.MOBILE.ordinal()) {
                Long queueId = agent_queue.get(agent.getUserId());
                if (queueId != null) {
                    Integer lineId = queue_line.get(queueId);
                    if (lineId != null) {
                        String contact ="${verto_contact("+ +agent.getUserId() + ")}";
                        try {
                            FsLineInfoVO fsLineVO = fsLineManager.getFsLine(lineId);
                            String[] ip = fsLineVO.getFsIp().split(":");
                            contact = String.format("{origination_caller_id_name=%s}sofia/internal/%s@%s", lineId, agent.getMobile(), ip[0] + ":" + fsLineVO.getFsInPort());
                        }catch (Exception e){
                        log.warn("调用fsmanager服务根据lineid查询fs信息的接口出错，lineId:[{}],错误:[{}]",lineId,e.toString());
                        }
                        agentInfo.setContact(contact);
                    } else {
                        agentInfo.setContact("${verto_contact(" + agent.getUserId() + ")}");
                    }
                } else {
                    agentInfo.setContact("${verto_contact(" + agent.getUserId() + ")}");
                }
            }
            agentInfoList.add(agentInfo);
        }
        //调用基于模板批量创建坐席、队列和绑定关系的方法，生成xml
        fsManager.initCallcenter(agentInfoList, queueIdList, tierInfoList);
        //调用上传NAS的接口，得到文件下载地址，并调用lua脚本
        String fileUrl = FileUtil.uploadConfig(1L, fsBotConfig.getHomeDir()+"/callcenter.conf.xml",eurekaManager.getAppName());
        String other = "reload+mod_callcenter";
        fsManager.syncCallcenter(fileUrl, other);
        fsManager.syncUser(agentMapper.selectMinUserId() + "", agentMapper.selectMaxUserId() + "");
    }

    @Override
    public boolean agentStateByVerto(EUserState eUserState, Agent agent) {
        AgentInfo agentInfo = new AgentInfo();
        agentInfo.setAgentId(agent.getUserId() + "");
        if (eUserState == EUserState.OFFLINE) {
            agentInfo.setStatus(AgentStatus.Logged_Out);
            agent.setUserState(EUserState.OFFLINE.ordinal());
        } else {
            agentInfo.setStatus(AgentStatus.Available);
            agent.setUserState(EUserState.ONLINE.ordinal());
        }
        fsManager.updateAgentState(agentInfo);
        agentMapper.updateByPrimaryKey(agent);
        return true;
    }

    @Override
    public boolean syncAgentMembers(List<AgentMembrVO> agentMembers) {
        log.info("开始同步用户创建坐席，请求[{}]", agentMembers);
        Date date = new Date();
        for (AgentMembrVO agentMembrVO:agentMembers) {
            AgentExample example = new AgentExample();
            example.createCriteria().andCustomerIdEqualTo(agentMembrVO.getCustomerId());
            List<Agent> agentList = agentMapper.selectByExample(example);
            if(agentList.size()>0){
                Agent agent = agentList.get(0);
                agent.setUserName(agentMembrVO.getCustomerName());
                agentMapper.updateByPrimaryKey(agent);
            }else{
                Queue queue =getQueue(agentMembrVO.getOrgCode(),date);//判断该orgcode有没有默认坐席，没有新创建一个
                if(queue!=null){
                    //4、创建用户和坐席, 并存入数据库
                    Agent user = new Agent();
                    user.setUserName(agentMembrVO.getCustomerName());
                    //第二步创建freeswitch坐席,默认离线，默认网页接听
                    user.setUserState(EUserState.OFFLINE.ordinal());
                    user.setAnswerType(EAnswerType.WEB.ordinal());
                    user.setCreateTime(date);
                    user.setUpdateTime(date);
                    user.setOrgCode(agentMembrVO.getOrgCode());
                    user.setCrmLoginId(agentMembrVO.getLoginAccount());
                    user.setCustomerId(agentMembrVO.getCustomerId());
                    agentMapper.insert(user);

                    AgentInfo agentInfo = new AgentInfo();
                    agentInfo.setStatus(AgentStatus.Logged_Out);
                    agentInfo.setContact("${verto_contact(" + user.getUserId() + ")}");
                    agentInfo.setAgentId(user.getUserId() + "");
                    fsManager.addAgent(agentInfo);
                    //同步fs用户到freeswitch()
                    fsManager.syncUser(agentMapper.selectMinUserId() + "", user.getUserId() + "");
                    //第三步callcenter绑定
                    addTier(user,queue,date);
                }
            }
        }
        return true;
    }

    @Override
    public boolean delAgentMembers(List<Long> customerIds) {
        for (Long customerId:customerIds) {
            AgentExample example = new AgentExample();
            example.createCriteria().andCustomerIdEqualTo(customerId);
            List<Agent> agentList = agentMapper.selectByExample(example);
            if(agentList.size()>0){
                Agent agent = agentList.get(0);
                //第一步删除绑定关系
                //删除绑定关系
                TierExample tierExample = new TierExample();
                tierExample.createCriteria().andUserIdEqualTo(agent.getUserId());
                List<Tier> tierRes = tierMapper.selectByExample(tierExample);
                if (tierRes != null) {
                    Tier tier = tierRes.get(0);
                    TierInfo tierInfo = new TierInfo(tier.getQueueId() + "", agent.getUserId()+"");
                    fsManager.deleteTier(tierInfo);
                    //删除数据库中的绑定关系
                    tierMapper.deleteByPrimaryKey(tier.getTid());
                    //第二步删除坐席
                    fsManager.deleteAgent(agent.getUserId()+"");
                    //删除数据库中的用户信息
                    agentMapper.deleteByPrimaryKey(agent.getUserId());
                }
            }
        }
        return true;
    }

    @Override
    public Agent getAgentByCustomerId(Long customerId) {
        AgentExample agentExample = new AgentExample();
        agentExample.createCriteria().andCustomerIdEqualTo(customerId);
        List<Agent> agentList = agentMapper.selectByExample(agentExample);
        if(agentList.size()>0){
            return  agentList.get(0);
        }
        return null;
    }

    /**
     * 同步坐席的时候得到queue对象
     * @param orgCode
     * @param date
     * @return
     */
    public Queue getQueue(String orgCode,Date date){
        QueueExample queueExample = new QueueExample();
        queueExample.createCriteria().andOrgCodeEqualTo(orgCode).andQueueNameEqualTo("默认坐席组");
        List<Queue> queueList = queueMapper.selectByExample(queueExample);
        Queue queue = new Queue();
        if(queueList.size()==0){//如果该org_code没有默认坐席组，直接创建一个
            log.info("orgcode[{}]下没有默认坐席组，新创建一个",orgCode);
            queue.setQueueName("默认坐席组");
            queue.setCreateTime(date);
            queue.setUpdateTime(date);
            queue.setOrgCode(orgCode);
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
            String fileUrl = FileUtil.uploadConfig(1L, fsBotConfig.getHomeDir()+"/callcenter.conf.xml",eurekaManager.getAppName());
            //创建为坐席组之后，执行新增tier命令
            String other = "reload+mod_callcenter";
            fsManager.syncCallcenter(fileUrl, other);
        }else{
            queue =queueList.get(0);
        }
        return queue;
    }

    /**
     * 配置队列绑定关系
     * @param user
     * @param queue
     * @param date
     */
    public void addTier(Agent user,Queue queue,Date date){
        //第三步callcenter绑定
        TierInfo tierInfo = new TierInfo();
        tierInfo.setAgentId(user.getUserId() + "");
        tierInfo.setQueueId(queue.getQueueId() + "");
        fsManager.addTier(tierInfo);
        //第四步将绑定关系存入数据库
        Tier tier = new Tier();
        tier.setQueueId(queue.getQueueId());
        tier.setUserId(user.getUserId());
        //tier.setCreator(create.getUserId());
        tier.setCreateTime(date);
        tier.setUpdateTime(date);
        tier.setUpdateUser(user.getUserId());
        tier.setOrgCode(user.getOrgCode());
        tierMapper.insert(tier);
    }

    @Override
    public List<Agent> findByOrgCode(String orgCode) {
        AgentExample example = new AgentExample();
        example.createCriteria().andOrgCodeEqualTo(orgCode);
        return agentMapper.selectByExample(example);
    }

    @Override
    public Agent findById(String agentId) {
        return agentMapper.selectByPrimaryKey(Long.valueOf(agentId));
    }
}
