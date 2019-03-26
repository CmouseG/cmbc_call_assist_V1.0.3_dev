package com.guiji.service.impl;

import com.github.pagehelper.PageInfo;
import com.guiji.callcenter.dao.AgentMapper;
import com.guiji.callcenter.dao.RegistrationMapper;
import com.guiji.callcenter.dao.entity.Agent;
import com.guiji.callcenter.dao.entity.AgentExample;
import com.guiji.callcenter.dao.entity.Registration;
import com.guiji.callcenter.dao.entity.RegistrationExample;
import com.guiji.callcenter.helper.PageExample;
import com.guiji.entity.ExcelData;
import com.guiji.service.AgentService;
import com.guiji.service.RegistrationService;
import com.guiji.util.DateUtil;
import com.guiji.util.ExportExcelUtils;
import com.guiji.web.request.RegistrationRequest;
import com.guiji.web.response.Paging;
import com.guiji.web.response.QueryRegistration;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * @Auther: 魏驰
 * @Date: 2018/12/17 14:33
 * @Project：ccserver
 * @Description:
 */
@Service
public class RegistrationServiceImpl implements RegistrationService {
    @Autowired
    AgentMapper agentMapper;
    @Autowired
    RegistrationMapper registrationMapper;
    @Autowired
    AgentService agentService;
    @Override
    public Paging getRegistrations(Integer page, Integer size, int authLevel, Long customerId, String orgCode) throws Exception {
        Agent agent = agentService.getAgentByCustomerId(customerId);
        if(agent==null){
            throw new Exception("0307014");
        }
        List<QueryRegistration> queryRegistrationList = new ArrayList();
        List<Registration> registrationList = queryRegistrationsSub(agent, page, size, authLevel, orgCode);
        PageInfo<Registration> pageInfo = new PageInfo<>(registrationList);
        List<Registration> list = pageInfo.getList();
        for (Registration registration : list) {
            QueryRegistration queryRegistration = new QueryRegistration();
            BeanUtils.copyProperties(registration, queryRegistration);
            Agent create = agentMapper.selectByPrimaryKey(registration.getCreator());//根据creator查询用户信息
            queryRegistration.setUserId(registration.getCreator());
            queryRegistration.setUserName(create.getUserName());
            queryRegistration.setUpdateTime(DateUtil.getStrDate(registration.getUpdateTime(), DateUtil.FORMAT_YEARMONTHDAY_HOURMINSEC));
            queryRegistrationList.add(queryRegistration);
        }
        Paging paging = new Paging();
        paging.setPageNo(page);
        paging.setPageSize(size);
        paging.setTotalPage(pageInfo.getPages());
        paging.setTotalRecord(pageInfo.getTotal());
        paging.setRecords((List<Object>) (Object) queryRegistrationList);
        return paging;
    }

    /**
     * 根据权限，角色等查询登记历史记录
     *
     * @param agent
     * @param page
     * @param size
     * @param authLevel
     * @param orgCode
     * @return
     */
    public List<Registration> queryRegistrationsSub(Agent agent, Integer page, Integer size, int authLevel, String orgCode) {
        PageExample testPage = new PageExample();
        RegistrationExample registrationExample = new RegistrationExample();
        if (authLevel == 1) {
            registrationExample.createCriteria().andCreatorEqualTo(agent.getUserId());
        } else {
            registrationExample.createCriteria().andCreatorIn(queryRegistrationsSubToGetAgents(authLevel,orgCode));
        }
        testPage.setPageNum(page);
        testPage.setPageSize(size);
        testPage.enablePaging();
        registrationExample.setOrderByClause("update_time DESC");
        List<Registration> registrationList = registrationMapper.selectByExample(registrationExample);
        return registrationList;
    }

    /**
     * 用于查询登记历史记录时的创建者范围
     *
     * @param authLevel
     * @param orgCode
     * @return
     */
    public List<Long> queryRegistrationsSubToGetAgents(int authLevel, String orgCode) {
        AgentExample agentExample = new AgentExample();
        if (authLevel == 2) {
            agentExample.createCriteria().andOrgCodeEqualTo(orgCode);
        } else if (authLevel == 3) {
            agentExample.createCriteria().andOrgCodeLike(orgCode + "%");
        }
        List<Agent> agentList = agentMapper.selectByExample(agentExample);
        List<Long> listUser = new ArrayList<>();
        for (Agent agents : agentList) {
            listUser.add(agents.getUserId());
        }
        return listUser;
    }


    @Override
    public void deleteRegistration(String regId) {
        registrationMapper.deleteByPrimaryKey(Long.parseLong(regId));
    }

    @Override
    public void updateRegistration(String regId, RegistrationRequest request, Long customerId) throws Exception {
        Agent agent = agentService.getAgentByCustomerId(customerId);
        if(agent==null){
            throw new Exception("0307014");
        }
        Date date = new Date();
        Registration registration = registrationMapper.selectByPrimaryKey(Long.parseLong(regId));
        registration.setCustomerName(request.getCustomerName());
        registration.setCustomerMobile(request.getCustomerMobile());
        registration.setCustomerAddr(request.getCustomerAddr());
        registration.setRemark(request.getRemark());
        registration.setUpdateTime(date);
        registration.setCreator(agent.getUserId());
        registrationMapper.updateByPrimaryKey(registration);
    }

    @Override
    public void addRegistration(RegistrationRequest request, Long customerId) throws Exception {
        Agent agent = agentService.getAgentByCustomerId(customerId);
        if(agent==null){
            throw new Exception("0307014");
        }
        Date date = new Date();
        RegistrationExample registrationExample = new RegistrationExample();
        registrationExample.createCriteria().andPlanUuidEqualTo(request.getRecordId());
        List<Registration> list = registrationMapper.selectByExample(registrationExample);
        if (list != null && list.size() > 0) {
            throw new Exception("0307013");
        } else {
            Registration registration = new Registration();
            BeanUtils.copyProperties(request, registration);
            registration.setCreateTime(date);
            registration.setCreator(agent.getUserId());
            registration.setUpdateTime(date);
            registration.setUpdateUser(agent.getUserId());
            registration.setPlanUuid(request.getRecordId());
            registrationMapper.insert(registration);
        }
    }

    @Override
    public void getExportRegistrations(String regIds, Long agentId, int authLevel, String orgCode, HttpServletResponse response) {
        List<QueryRegistration> queryRegistrationList = new ArrayList<QueryRegistration>();
        List<Registration> list;
        if (!StringUtils.isBlank(regIds)) { //如果有登记历史的id
            String id[] = regIds.split(",");
            List<Long> ids = new ArrayList<Long>();
            for (int i = 0; i < id.length; i++) {
                ids.add(Long.parseLong(id[i]));
            }
            RegistrationExample registrationExample = new RegistrationExample();
            registrationExample.createCriteria().andRegIdIn(ids);
            list = registrationMapper.selectByExample(registrationExample);
            for (Registration registration : list) {
                QueryRegistration queryRegistration = new QueryRegistration();
                BeanUtils.copyProperties(registration, queryRegistration);
                queryRegistration.setUpdateTime(DateUtil.getStrDate(registration.getUpdateTime(), DateUtil.FORMAT_YEARMONTHDAY_HOURMINSEC));
                Agent creater = agentMapper.selectByPrimaryKey(registration.getCreator());//根据creator查询用户信息
                queryRegistration.setUserId(registration.getCreator());
                queryRegistration.setUserName(creater.getUserName());
                queryRegistrationList.add(queryRegistration);
            }
        } else {//如果没有登记历史的id，则判断有没有用户id
            if (agentId != null) {
                List<Agent> agentsList = new ArrayList();
                Agent agent = agentMapper.selectByPrimaryKey(agentId);
                if (agent != null) {
                    RegistrationExample registrationExample = new RegistrationExample();
                    if (authLevel == 1) {
                        PageExample pageExample = new PageExample();
                        pageExample.setPageNum(1);
                        pageExample.setPageSize(30000);
                        pageExample.enablePaging();
                        registrationExample.createCriteria().andCreatorEqualTo(agentId);
                        list = registrationMapper.selectByExample(registrationExample);//todo 30000条
                        agentsList.add(agent);
                    } else {
                        AgentExample agentExample = new AgentExample();
                        if (authLevel == 2) {
                            agentExample.createCriteria().andOrgCodeEqualTo(orgCode);
                        } else if (authLevel == 3) {
                            agentExample.createCriteria().andOrgCodeLike(orgCode + "%");
                        }
                        List<Agent> agentList = agentMapper.selectByExample(agentExample);
                        agentsList.addAll(agentList);
                        List<Long> listUser = new ArrayList<>();
                        for (Agent agents : agentList) {
                            listUser.add(agents.getUserId());
                        }
                        PageExample pageExample = new PageExample();
                        pageExample.setPageNum(1);
                        pageExample.setPageSize(30000);
                        pageExample.enablePaging();
                        registrationExample.createCriteria().andCreatorIn(listUser);
                        list = registrationMapper.selectByExample(registrationExample);//todo 30000条
                    }
                    //得到本次查询到的所有相关坐席
                    Map<Long, Agent> agentMap = new HashMap();
                    for (Agent agent1 : agentsList) {
                        agentMap.put(agent1.getUserId(), agent1);
                    }
                    for (Registration registration : list) {
                        QueryRegistration queryRegistration = new QueryRegistration();
                        BeanUtils.copyProperties(registration, queryRegistration);
                        queryRegistration.setUpdateTime(DateUtil.getStrDate(registration.getUpdateTime(), DateUtil.FORMAT_YEARMONTHDAY_HOURMINSEC));
                        Agent create = agentMap.get(registration.getCreator());//根据creator查询用户信息
                        queryRegistration.setUserId(registration.getCreator());
                        queryRegistration.setUserName(create.getUserName());
                        queryRegistrationList.add(queryRegistration);
                    }
                }
            }
        }
        getExportRegistrationsSub(queryRegistrationList, response);
    }

    /**
     * 导出历史记录
     *
     * @param queryRegistrationList
     * @param response
     */
    public void getExportRegistrationsSub(List<QueryRegistration> queryRegistrationList, HttpServletResponse response) {
        ExcelData data = new ExcelData();
        data.setName("登记信息");
        List<String> titles = new ArrayList();
        titles.add("编号");
        titles.add("客户姓名");
        titles.add("客户电话");
        titles.add("客户地址");
        titles.add("更新日期");
        titles.add("备注");
        titles.add("所属人");
        data.setTitles(titles);
        List<List<Object>> rows = new ArrayList();
        int i = 1;
        for (QueryRegistration queryRegistration : queryRegistrationList) {
            List<Object> row = new ArrayList();
            row.add(i);
            row.add(queryRegistration.getCustomerName());
            row.add(queryRegistration.getCustomerMobile());
            row.add(queryRegistration.getCustomerAddr());
            row.add(queryRegistration.getUpdateTime());
            row.add(queryRegistration.getRemark());
            row.add(queryRegistration.getUserName());
            rows.add(row);
            i++;
        }
        data.setRows(rows);
        String fileName = "登记消息" + DateUtil.getCurrentDateTimeChina() + ".xlsx";
        ExportExcelUtils.exportExcel(response, fileName, data);
    }
}
