package com.guiji.service.impl;

import com.github.pagehelper.PageInfo;
import com.guiji.callcenter.dao.AgentMapper;
import com.guiji.callcenter.dao.CallOutPlanMapper;
import com.guiji.callcenter.dao.QueueMapper;
import com.guiji.callcenter.dao.TierMapper;
import com.guiji.callcenter.dao.entity.*;
import com.guiji.callcenter.helper.PageExample;
import com.guiji.common.exception.GuiyuException;
import com.guiji.common.model.SysFileReqVO;
import com.guiji.common.model.SysFileRspVO;
import com.guiji.config.FsBotConfig;
import com.guiji.config.FsConfig;
import com.guiji.entity.*;
import com.guiji.fs.FreeSWITCH;
import com.guiji.fs.FsManager;
import com.guiji.fs.pojo.AgentStatus;
import com.guiji.fs.pojo.GlobalVar;
import com.guiji.fsline.entity.FsLineVO;
import com.guiji.manager.AuthManager;
import com.guiji.manager.EurekaManager;
import com.guiji.manager.FsLineManager;
import com.guiji.service.AgentService;
import com.guiji.service.CallPlanService;
import com.guiji.service.QueueService;
import com.guiji.util.DateUtil;
import com.guiji.utils.NasUtil;
import com.guiji.web.request.AgentInfo;
import com.guiji.web.request.AgentRequest;
import com.guiji.web.request.CrmUserVO;
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

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
    AuthManager authManager;

    @Autowired
    FsBotConfig fsBotConfig;

    @Autowired
    FsLineManager fsLineManager;

    @Override
    public boolean createAgent(AgentRequest request, Agent create, Long crmUserid) throws Exception {
        log.info("开始创建坐席，请求[{}], crmUserId[{}]", request, crmUserid);
        AgentExample example = new AgentExample();
        example.createCriteria().andCrmLoginIdEqualTo(request.getCrmLoginId());
        List<Agent> agentList = agentMapper.selectByExample(example);
        if (agentList.size() > 0) {
            throw new Exception("0307002");
        }

        if (!fsBotConfig.isNoAuth()) {
            log.info("开始将用户同步到auth模块，用户名[{}],crmUserId[{}]", request.getAgentName(), crmUserid);
            Long syncrs = authManager.syncUser(request.getCrmLoginId(), request.getAgentPwd(), crmUserid);
            if(syncrs==null){
                throw new Exception("0307015");
            }
        }


        //4、创建用户和坐席, 并存入数据库
        Date date = new Date();
        Agent user = new Agent();
        user.setUserName(request.getAgentName());
        user.setUserRole(EUserRole.AGENT.ordinal());
        user.setMobile(request.getMobile());
        user.setUserPwd(request.getAgentPwd());

        //第二步创建freeswitch用户,创建freeswitch坐席
        AgentInfo agentInfo = new AgentInfo();

        if (request.getAgentState() != null) {
            if (request.getAgentState() == EUserState.OFFLINE) {
                agentInfo.setStatus(AgentStatus.Logged_Out);
                user.setUserState(EUserState.OFFLINE.ordinal());
            } else if (request.getAgentState() == EUserState.ONLINE) {
                agentInfo.setStatus(AgentStatus.Available);
                user.setUserState(EUserState.ONLINE.ordinal());
            }
        } else {
            agentInfo.setStatus(AgentStatus.Logged_Out);
            user.setUserState(EUserState.OFFLINE.ordinal());
        }
        user.setAnswerType(EAnswerType.WEB.ordinal());
        user.setCreator(create.getUserId());
        user.setCreateTime(date);
        user.setUpdateUser(create.getUserId());
        user.setUpdateTime(date);
        user.setOrgCode(create.getOrgCode());
        user.setCrmLoginId(request.getCrmLoginId());
        agentMapper.insert(user);

        if (request.getAnswerType() == EAnswerType.WEB) {
            agentInfo.setContact("${verto_contact(" + user.getUserId() + ")}");
        } else if (request.getAnswerType() == EAnswerType.MOBILE) {
            Queue queue = queueMapper.selectByPrimaryKey(request.getQueueId());
            FsLineVO fsLineVO = fsLineManager.getFsLine();
            String[] ip = fsLineVO.getFsIp().split(":");
            String contact = String.format("{origination_caller_id_name=%s}sofia/internal/%s@%s", queue.getLineId(), user.getMobile(), ip[0] + ":" + fsLineVO.getFsInPort());
            agentInfo.setContact(contact);
            user.setAnswerType(EAnswerType.MOBILE.ordinal());
            agentMapper.updateByPrimaryKey(user);
        }
        agentInfo.setAgentId(user.getUserId() + "");
        fsManager.addAgent(agentInfo);
        //同步fs用户到freeswitch()
        fsManager.syncUser(agentMapper.selectMinUserId() + "", user.getUserId() + "");
        //第三步callcenter绑定
        TierInfo tierInfo = new TierInfo();
        tierInfo.setAgentId(user.getUserId() + "");
        tierInfo.setQueueId(request.getQueueId() + "");
        fsManager.addTier(tierInfo);

        //第四步将绑定关系存入数据库
        Tier tier = new Tier();
        tier.setQueueId(request.getQueueId());
        tier.setUserId(user.getUserId());
        tier.setCreator(create.getUserId());
        tier.setCreateTime(date);
        tier.setUpdateTime(date);
        tier.setUpdateUser(user.getUserId());
        tier.setOrgCode(create.getOrgCode());
        tierMapper.insert(tier);

        //调用上传NAS的接口，得到文件下载地址，并调用lua脚本
        String fileUrl = uploadConfig(create.getUserId(), fsBotConfig.getHomeDir() + "/callcenter.conf.xml");

        fsManager.syncCallcenter(fileUrl, null);
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
    public boolean updateAgent(String userId, AgentRequest request, Agent create) throws Exception{
        Agent agent = agentMapper.selectByPrimaryKey(Long.parseLong(userId));//根据坐席ID查询用户信息
        if (create.getUserRole() == EUserRole.AGENT.ordinal()) {
            if (!create.getUserId().toString().equals(userId)) {//是坐席请求，改的又不是自己直接返回错误
               // throw new GuiyuException(ToagentserverException.EXCP_TOAGENT_NOT_OWNER);
                 throw new Exception("0307005");
            }
        }
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
                if(queue.getLineId()==null){
                    //throw new GuiyuException(ToagentserverException.EXCP_TOAGENT_QUEUE_NO_LINE);
                    throw new Exception("0307011");
                }
                FsLineVO fsLineVO = fsLineManager.getFsLine();
                String[] ip = fsLineVO.getFsIp().split(":");
                String contact = String.format("{origination_caller_id_name=%s}sofia/internal/%s@%s",queue.getLineId(),request.getMobile(),ip[0]+":"+fsLineVO.getFsInPort());
                agentInfo.setContact(contact);
                //agentInfo.setContact("loopback/" + request.getMobile());
            }
        }

        fsManager.updateAgent(agentInfo);
        //第三步绑定,如果queueId不为空，则代表要修改绑定关系：判断两次队列Id是否一样，一样的话就不动，不一样的话就先删除原来的绑定关系，再创建新的绑定关系
        if (request.getQueueId() != null) {
            TierExample tierExample = new TierExample();
            tierExample.createCriteria().andUserIdEqualTo(agent.getUserId());
            List<Tier> tierRes = tierMapper.selectByExample(tierExample);
            if (tierRes != null) {
                Tier tier = tierRes.get(0);
                if (request.getQueueId() != tier.getQueueId()) {//两次的队列id不一样
                    //删除原来的绑定关系
                    TierInfo tierInfo = new TierInfo(tier.getQueueId() + "", agent.getUserId() + "");
                    fsManager.deleteTier(tierInfo);
                    //创建新的绑定关系
                    TierInfo tierInfoNew = new TierInfo();
                    tierInfoNew.setAgentId(agent.getUserId() + "");
                    tierInfoNew.setQueueId(request.getQueueId() + "");
                    fsManager.addTier(tierInfoNew);
                    //将绑定关系更新到数据库
                    tier.setQueueId(request.getQueueId());
                    tier.setUpdateTime(date);
                    tier.setUpdateUser(create.getUserId());
                    tierMapper.updateByPrimaryKey(tier);
                } else {
                    //创建新的绑定关系
                    TierInfo tierInfoNew = new TierInfo();
                    tierInfoNew.setAgentId(agent.getUserId() + "");
                    tierInfoNew.setQueueId(request.getQueueId() + "");
                    fsManager.addTier(tierInfoNew);
                    //第四步将绑定关系存入数据库
                    Tier tiernew = new Tier();
                    tiernew.setQueueId(agent.getUserId());
                    tiernew.setUserId(request.getQueueId());
                    tiernew.setCreator(agent.getUserId());
                    tiernew.setUpdateUser(agent.getUserId());
                    tiernew.setCreateTime(date);
                    tiernew.setUpdateTime(date);
                    tierMapper.insert(tiernew);
                }
            }
        }

        //todo -- 调用上传NAS的接口，得到文件下载地址，并调用lua脚本
        String fileUrl = uploadConfig(1L, fsBotConfig.getHomeDir()+"/callcenter.conf.xml");
        fsManager.syncCallcenter(fileUrl,null);
        return true;
    }

    @Override
    public boolean deleteAgent(String userId) {
        //第一步删除绑定关系
        //删除绑定关系
        TierExample tierExample = new TierExample();
        tierExample.createCriteria().andUserIdEqualTo(Long.parseLong(userId));
        List<Tier> tierRes = tierMapper.selectByExample(tierExample);
        if (tierRes != null) {
            Tier tier = tierRes.get(0);
            TierInfo tierInfo = new TierInfo(tier.getQueueId() + "", userId);
            fsManager.deleteTier(tierInfo);
            //删除数据库中的绑定关系
            tierMapper.deleteByPrimaryKey(tier.getTid());
            //第二步删除坐席
            fsManager.deleteAgent(userId);
//            //第三步删除用户
//            fsManager.deleteUser(userId);
            //删除数据库中的用户信息
            agentMapper.deleteByPrimaryKey(Long.parseLong(userId));
        }
        //todo -- 调用上传NAS的接口，得到文件下载地址，并调用lua脚本
        String fileUrl = uploadConfig(1L, fsBotConfig.getHomeDir()+"/callcenter.conf.xml");
        fsManager.syncCallcenter(fileUrl,null);
        return true;
    }

    @Override
    public boolean agentState(AgentRequest request, Agent user) throws Exception{
        Agent agent = agentMapper.selectByPrimaryKey(request.getUserId());//根据坐席ID查询用户信息
        if (user.getUserRole() == EUserRole.AGENT.ordinal()) {
            if (!user.getUserId().toString().equals(request.getUserId())) {//是坐席请求，改的又不是自己直接返回错误
                //throw new GuiyuException(ToagentserverException.EXCP_TOAGENT_NOT_OWNER);
                throw new Exception("0307005");
            }
        }
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

        //todo -- 调用上传NAS的接口，得到文件下载地址，并调用lua脚本
        String fileUrl = uploadConfig(1L, fsBotConfig.getHomeDir()+"/callcenter.conf.xml");
        fsManager.syncCallcenter(fileUrl,null);
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
    public Paging getAllAgent(Agent user,String crmLoginId, String queueId, Integer page, Integer size) {
        List<QueryAgent> queryAgentList = new ArrayList<QueryAgent>();
        Paging paging = new Paging();
        AgentExample example = new AgentExample();
        List<Agent> list = new ArrayList<>();
        if (user.getUserRole() == EUserRole.ADMIN.ordinal()) {
            if (!Strings.isNullOrEmpty(queueId)) {
                TierExample tierExample = new TierExample();
                tierExample.createCriteria().andQueueIdEqualTo(Long.parseLong(queueId));
                List<Tier> tierRes = tierMapper.selectByExample(tierExample);
                if (tierRes != null && tierRes.size() > 0) { //坐席组中有坐席
                    List<Long> userIds = new ArrayList<>();
                    for (Tier tier : tierRes) {
                        userIds.add(tier.getUserId());
                    }
                    if(!Strings.isNullOrEmpty(crmLoginId)){
                        example.createCriteria().andUserIdIn(userIds).andCrmLoginIdLike(crmLoginId);
                    }else{
                        example.createCriteria().andUserIdIn(userIds);
                    }

                } else {//坐席组中没有分配坐席
                    example.createCriteria().andUserIdEqualTo(0L);
                }
            } else { //如果没有传queueId
                if(!Strings.isNullOrEmpty(crmLoginId)){
                    example.createCriteria().andOrgCodeEqualTo(user.getOrgCode()).andCrmLoginIdLike(crmLoginId);
                }else{
                    example.createCriteria().andOrgCodeEqualTo(user.getOrgCode());
                }
            }
            PageExample testPage = new PageExample();
            testPage.setPageNum(page);
            testPage.setPageSize(size);
            testPage.enablePaging();
            example.setOrderByClause("update_time DESC");
            list = agentMapper.selectByExample(example);
        } else {//如果未普通坐席，只查询自己
            list.add(user);
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
            Agent create = agentMapper.selectByPrimaryKey(user.getCreator());//根据坐席ID查询用户信息
            queryAgent.setCreatorName(create.getUserName());
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
    public QueryUser getUser(Agent agent) {
        QueryUser queryUser = new QueryUser();
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
        if (agent.getUserRole() == 0) {
            queryUser.setUserRole(EUserRole.ADMIN);
        } else {
            queryUser.setUserRole(EUserRole.AGENT);
        }
        TierExample tierExample = new TierExample();
        tierExample.createCriteria().andUserIdEqualTo(agent.getUserId());
        List<Tier> tierRes = tierMapper.selectByExample(tierExample);
        if (tierRes != null && tierRes.size()>0) {
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
     * @param agent
     * @return
     */
    @Override
    public boolean isAgentLogin(Agent agent) {
        boolean state = fsManager.getVertoStatus(agent.getUserId() + "");
        if (!state) {//如果
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
        return callPlan!=null && callPlan.getCallId()!=null;
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
        AgentExample agentExample = new AgentExample();
        List<Agent> agentList =agentMapper.selectByExample(agentExample);
        for (Agent agent:agentList) {
            AgentInfo agentInfo = new AgentInfo();
            agentInfo.setAgentId(agent.getUserId() + "");
            if(agent.getUserState()==EUserState.OFFLINE.ordinal()){
                agentInfo.setStatus(AgentStatus.Logged_Out);
            }else if(agent.getUserState()==EUserState.ONLINE.ordinal()){
                agentInfo.setStatus(AgentStatus.Available);
            }
            if(agent.getAnswerType()==EAnswerType.WEB.ordinal()){
                agentInfo.setContact("${verto_contact(" + agent.getUserId() + ")");
            }else if(agent.getAnswerType()==EAnswerType.MOBILE.ordinal()){
                agentInfo.setContact("loopback/" + agent.getMobile());
            }
            agentInfoList.add(agentInfo);
        }
        QueueExample queueExample = new QueueExample();
        List<Queue> queueList = queueMapper.selectByExample(queueExample);
        for (Queue queue:queueList) {
            queueIdList.add(queue.getQueueId()+"");
        }
        TierExample tierExample = new TierExample();
        List<Tier> tierList =tierMapper.selectByExample(tierExample);
        for (Tier tier:tierList) {
            TierInfo tierInfo = new TierInfo();
            tierInfo.setAgentId(tier.getUserId() + "");
            tierInfo.setQueueId(tier.getQueueId() + "");
            tierInfoList.add(tierInfo);
        }
        fsManager.initCallcenter(agentInfoList,queueIdList,tierInfoList);

        //调用上传NAS的接口，得到文件下载地址，并调用lua脚本
        String fileUrl = uploadConfig(1L, fsBotConfig.getHomeDir()+"/callcenter.conf.xml");
        String other = "reload mod_callcenter";
        fsManager.syncCallcenter(fileUrl,other);
        fsManager.syncUser(agentMapper.selectMinUserId()+"",agentMapper.selectMaxUserId()+"");
    }

    @Override
    public Agent initUser(CrmUserVO crmUserVO) {
        //4、创建用户和坐席, 并存入数据库
        Date date = new Date();
        Agent user = new Agent();
        user.setUserName(crmUserVO.getAgentName());
        user.setUserRole(crmUserVO.getUserRole().ordinal());
        user.setUserPwd(crmUserVO.getAgentPwd());
        user.setAnswerType(EAnswerType.WEB.ordinal());
        user.setUserState(EUserState.OFFLINE.ordinal());
        user.setCreateTime(date);
        user.setUpdateTime(date);
        user.setOrgCode(crmUserVO.getOrgCode());
        user.setCrmLoginId(crmUserVO.getCrmLoginId());
        agentMapper.insert(user);
        user.setCreator(user.getUserId());
        user.setUpdateUser(user.getUserId());
        agentMapper.updateByPrimaryKey(user);

        //第二步创建freeswitch用户,创建freeswitch坐席
        AgentInfo agentInfo = new AgentInfo();
        agentInfo.setAgentId(user.getUserId() + "");
        agentInfo.setPassword("555666");
        agentInfo.setStatus(AgentStatus.Available);
        agentInfo.setContact("${verto_contact(" + user.getUserId() + ")");
        fsManager.addAgent(agentInfo);
        //同步fs用户到freeswitch()
        fsManager.syncUser(agentMapper.selectMinUserId()+"",user.getUserId() + "");

        Queue queue = new Queue();
        QueueExample queueExample = new QueueExample();
        queueExample.createCriteria().andOrgCodeEqualTo(user.getOrgCode()).andQueueNameEqualTo("默认坐席组");
        List<Queue> queueList = queueMapper.selectByExample(queueExample);
        if(queueList.size()>0){
            queue=queueList.get(0);
        }else{
            queue.setQueueName("默认坐席组");
            queue.setCreator(user.getUserId());
            queue.setCreateTime(date);
            queue.setUpdateUser(user.getUserId());
            queue.setUpdateTime(date);
            queue.setOrgCode(user.getOrgCode());
            queueMapper.insert(queue);
            fsManager.createQueue(queue.getQueueId() + "");
        }

        //第三步callcenter绑定
        TierInfo tierInfo = new TierInfo();
        tierInfo.setAgentId(user.getUserId() + "");
        tierInfo.setQueueId(queue.getQueueId() + "");
        fsManager.addTier(tierInfo);

        //第四步将绑定关系存入数据库
        Tier tier = new Tier();
        tier.setQueueId(queue.getQueueId());
        tier.setUserId(user.getUserId());
        tier.setCreator(user.getUserId());
        tier.setCreateTime(date);
        tier.setUpdateTime(date);
        tier.setUpdateUser(user.getUserId());
        tier.setOrgCode(user.getOrgCode());
        tierMapper.insert(tier);
        String fileUrl = uploadConfig(user.getUserId(), fsBotConfig.getHomeDir()+"/callcenter.conf.xml");
        String other = "callcenter_config+queue+load+"+queue.getQueueId()+"|callcenter_config+tier+add+"+queue.getQueueId()+"+"+user.getUserId()+"+1+1";
        fsManager.syncCallcenter(fileUrl,other);
        return user;
    }

    @Override
    public boolean agentStateByVerto(EUserState eUserState,Agent agent) {
        AgentInfo agentInfo = new AgentInfo();
        agentInfo.setAgentId(agent.getUserId() + "");
        if(eUserState==EUserState.OFFLINE){
            agentInfo.setStatus(AgentStatus.Logged_Out);
            agent.setUserState(EUserState.OFFLINE.ordinal());
        }else{
            agentInfo.setStatus(AgentStatus.Available);
            agent.setUserState(EUserState.ONLINE.ordinal());
        }
        fsManager.updateAgentState(agentInfo);
        agentMapper.updateByPrimaryKey(agent);
        //todo -- 调用上传NAS的接口，得到文件下载地址，并调用lua脚本
        String fileUrl = uploadConfig(1L, fsBotConfig.getHomeDir()+"/callcenter.conf.xml");
        fsManager.syncCallcenter(fileUrl,null);
        return true;
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
