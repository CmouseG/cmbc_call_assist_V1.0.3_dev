package com.guiji.service.impl;

import com.github.pagehelper.PageInfo;
import com.guiji.callcenter.dao.AgentMapper;
import com.guiji.callcenter.dao.RegistrationMapper;
import com.guiji.callcenter.dao.entity.Agent;
import com.guiji.callcenter.dao.entity.AgentExample;
import com.guiji.callcenter.dao.entity.Registration;
import com.guiji.callcenter.dao.entity.RegistrationExample;
import com.guiji.callcenter.helper.PageExample;
import com.guiji.entity.EUserRole;
import com.guiji.entity.ExcelData;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

    @Override
    public Paging getRegistrations(Agent agent, Integer page, Integer size) {

        PageInfo<Registration> pageInfo=null;
        List<QueryRegistration> queryRegistrationList = new ArrayList<QueryRegistration>();
        if (agent.getUserRole() == EUserRole.ADMIN.ordinal()) {//如果是admin用户，根据orgCode查询
            AgentExample agentExample = new AgentExample();
            agentExample.createCriteria().andOrgCodeEqualTo(agent.getOrgCode());
            List<Agent> agentList = agentMapper.selectByExample(agentExample);
            List<Long> listUser = new ArrayList<>();
            for (Agent agents:agentList) {
                listUser.add(agents.getUserId());
            }
            PageExample testPage2 = new PageExample();
            testPage2.setPageNum(page);
            testPage2.setPageSize(size);
            testPage2.enablePaging();
            RegistrationExample registrationExample = new RegistrationExample();
            registrationExample.createCriteria().andCreatorIn(listUser);
            List<Registration> registrationList = registrationMapper.selectByExample(registrationExample);
            pageInfo = new PageInfo<>(registrationList);
            List<Registration> list = pageInfo.getList();
            for (Registration registration : list) {
                QueryRegistration queryRegistration = new QueryRegistration();
                BeanUtils.copyProperties(registration, queryRegistration);
                Agent creater = agentMapper.selectByPrimaryKey(registration.getCreator());//根据creator查询用户信息
                queryRegistration.setUserId(registration.getCreator());
                queryRegistration.setUserName(creater.getUserName());
                queryRegistration.setUpdateTime(DateUtil.getStrDate(registration.getUpdateTime(), DateUtil.FORMAT_YEARMONTHDAY_HOURMINSEC));
                queryRegistrationList.add(queryRegistration);
            }
        } else { //如果是普通坐席，根据creator去查询
            PageExample testPage = new PageExample();
            testPage.setPageNum(page);
            testPage.setPageSize(size);
            testPage.enablePaging();
            RegistrationExample registrationExample = new RegistrationExample();
            registrationExample.createCriteria().andCreatorEqualTo(agent.getUserId());
            List<Registration> registrationList = registrationMapper.selectByExample(registrationExample);

           pageInfo = new PageInfo<>(registrationList);
            List<Registration> list = pageInfo.getList();
            for (Registration registration : list) {
                QueryRegistration queryRegistration = new QueryRegistration();
                BeanUtils.copyProperties(registration, queryRegistration);
                queryRegistration.setUserId(agent.getUserId());
                queryRegistration.setUserName(agent.getUserName());
                queryRegistration.setUpdateTime(DateUtil.getStrDate(registration.getUpdateTime(), DateUtil.FORMAT_YEARMONTHDAY_HOURMINSEC));
                queryRegistrationList.add(queryRegistration);
            }
        }
        Paging paging = new Paging();
        paging.setPageNo(page);
        paging.setPageSize(size);
        paging.setTotalPage(pageInfo.getPages());
        paging.setTotalRecord(pageInfo.getTotal());
        paging.setRecords((List<Object>) (Object) queryRegistrationList);
        return paging;
    }

    @Override
    public void deleteRegistration(String regId) {
        registrationMapper.deleteByPrimaryKey(Long.parseLong(regId));
    }

    @Override
    public void updateRegistration(String regId, RegistrationRequest request,Agent agent) {
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
    public void addRegistration(RegistrationRequest request,Agent agent) {
        Date date = new Date();
        RegistrationExample registrationExample = new RegistrationExample();
        registrationExample.createCriteria().andPlanUuidEqualTo(request.getRecordId());
        List<Registration> list = registrationMapper.selectByExample(registrationExample);
        if(list!=null&&list.size()>0){
            Registration registration =list.get(0);
            BeanUtils.copyProperties(request,registration);
            registration.setUpdateTime(date);
            registration.setUpdateUser(agent.getUserId());
            registrationMapper.updateByPrimaryKey(registration);
        }else{
            Registration registration =new Registration();
            BeanUtils.copyProperties(request,registration);
            registration.setCreateTime(date);
            registration.setCreator(agent.getUserId());
            registration.setUpdateTime(date);
            registration.setUpdateUser(agent.getUserId());
            registration.setPlanUuid(request.getRecordId());
            registrationMapper.insert(registration);
        }

    }

    @Override
    public void getExportRegistrations(String regIds,Long userId, HttpServletResponse response) {
      List<Registration> list = new ArrayList<>();
      if(!StringUtils.isBlank(regIds)){
          String id[] = regIds.split(",");
          List<Long> ids = new ArrayList<Long>();
          for (int i = 0; i < id.length; i++) {
              ids.add(Long.parseLong(id[i]));
          }
          RegistrationExample registrationExample = new RegistrationExample();
          registrationExample.createCriteria().andRegIdIn(ids);
          list = registrationMapper.selectByExample(registrationExample);
      }else {
          if(userId!=null){
              Agent agent =  agentMapper.selectByPrimaryKey(userId);



              RegistrationExample registrationExample = new RegistrationExample();
              if(agent !=null&& agent.getUserRole()==EUserRole.ADMIN.ordinal()){
                  AgentExample agentExample = new AgentExample();
                  agentExample.createCriteria().andOrgCodeEqualTo(agent.getOrgCode());
                  List<Agent> agentList = agentMapper.selectByExample(agentExample);
                  List<Long> listUser = new ArrayList<>();
                  for (Agent agents:agentList) {
                      listUser.add(agents.getUserId());
                  }
                  PageExample pageExample = new PageExample();
                  pageExample.setPageNum(1);
                  pageExample.setPageSize(30000);
                  pageExample.enablePaging();
                  registrationExample.createCriteria().andCreatorIn(listUser);
                  list = registrationMapper.selectByExample(registrationExample);//todo 30000条
              }else if(agent !=null&& agent.getUserRole()==EUserRole.AGENT.ordinal()){
                  PageExample pageExample = new PageExample();
                  pageExample.setPageNum(1);
                  pageExample.setPageSize(30000);
                  pageExample.enablePaging();
                  registrationExample.createCriteria().andCreatorEqualTo(userId);
                  list = registrationMapper.selectByExample(registrationExample);//todo 30000条
              }
          }
      }

        List<QueryRegistration> queryRegistrationList = new ArrayList<QueryRegistration>();
        for (Registration registration : list) {
            QueryRegistration queryRegistration = new QueryRegistration();
            BeanUtils.copyProperties(registration, queryRegistration);
            queryRegistration.setUpdateTime(DateUtil.getStrDate(registration.getUpdateTime(), DateUtil.FORMAT_YEARMONTHDAY_HOURMINSEC));
            Agent creater = agentMapper.selectByPrimaryKey(registration.getCreator());//根据creator查询用户信息
            queryRegistration.setUserId(registration.getCreator());
            queryRegistration.setUserName(creater.getUserName());
            queryRegistrationList.add(queryRegistration);
        }

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
        int i=1;
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
        String fileName = "登记消息"+DateUtil.getCurrentDateTimeChina()+".xlsx";
        ExportExcelUtils.exportExcel(response, fileName, data);
    }
}
