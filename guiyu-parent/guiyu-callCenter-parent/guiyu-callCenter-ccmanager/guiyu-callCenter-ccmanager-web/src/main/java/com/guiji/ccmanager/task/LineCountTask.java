package com.guiji.ccmanager.task;

import com.guiji.callcenter.dao.LineCountMapper;
import com.guiji.callcenter.dao.entity.LineCount;
import com.guiji.callcenter.dao.entity.LineCountExample;
import com.guiji.ccmanager.constant.Constant;
import com.guiji.utils.ServerUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Auther: 黎阳
 * @Date: 2018/11/1 0001 11:00
 * @Description:
 */
@Component
public class LineCountTask {

    private final Logger log = LoggerFactory.getLogger(LineCountTask.class);

    @Autowired
    private DiscoveryClient discoveryClient;
    @Autowired
    private LineCountMapper lineCountMapper;

    public boolean checkCount(){

        //eureka注册的服务列表
        List<String> serverEurekaList = ServerUtil.getInstances(discoveryClient,Constant.SERVER_NAME_CALLOUTSERVER);
        int count = serverEurekaList.size();

        LineCountExample example = new LineCountExample();
        List<LineCount> listSelect = lineCountMapper.selectByExample(example);

        // <lineid,List<calloutserver_id>>
        Map<Integer,List<String>> mapAll = new HashMap<Integer,List<String>>();

        // <linid，最大并发数>
        Map<Integer,Integer> mapConMax = new HashMap<Integer,Integer>();

        for(LineCount lineCount:listSelect){
            int lineId = lineCount.getLineId();
            if(mapAll.get(lineId)==null){
                List<String> list = new ArrayList<String>();
                list.add(lineCount.getCalloutserverId());
                mapAll.put(lineId,list);

                mapConMax.put(lineId,lineCount.getMaxConcurrentCalls());
            }else{
                List<String> list = mapAll.get(lineId);
                list.add(lineCount.getCalloutserverId());

                int maxCon = mapConMax.get(lineId);
                mapConMax.put(lineId,maxCon+lineCount.getMaxConcurrentCalls());
            }
        }

        for(Map.Entry<Integer,List<String>> entry:mapAll.entrySet()){

            //新增的calloutServer
            List<String> newServers = new ArrayList<String>();
            //丢掉的calloutServer
            List<String> deadServers = new ArrayList<String>();

            int lineId = entry.getKey();
            List<String> list = entry.getValue();
            for(String serverid : list){
                if(!serverEurekaList.contains(serverid)){//注册中心的calloutserver少了
                    deadServers.add(serverid);
                }
            }
            for(String serverEureka : serverEurekaList){
                if(!list.contains(serverEureka)){//注册中心的calloutserver多了
                    newServers.add(serverEureka);
                }
            }

            if(deadServers.size()>0 || newServers.size()>0 ) {
                log.info("lineId:"+lineId+" deadServers:"+deadServers.toString()+"  newServers:"+newServers.toString());
                if (deadServers.size() > 0) {
                    //delete
                    for (String deadServer : deadServers) {
                        LineCountExample delexample = new LineCountExample();
                        LineCountExample.Criteria criteria = delexample.createCriteria();
                        criteria.andCalloutserverIdEqualTo(deadServer);
                        criteria.andLineIdEqualTo(lineId);
                        lineCountMapper.deleteByExample(delexample);
                    }

                }

                int maxCon = mapConMax.get(lineId);
                int con = maxCon / count;
                int remain = maxCon % count;

                for (int i = 0; i < serverEurekaList.size(); i++) {

                    String calloutserver_id = serverEurekaList.get(i);
                    if (newServers != null && newServers.contains(calloutserver_id)) {//新增的insert

                        LineCount insertRecord = new LineCount();
                        insertRecord.setLineId(lineId);
                        insertRecord.setCalloutserverId(calloutserver_id);
                        if (i < remain) {
                            insertRecord.setMaxConcurrentCalls(con + 1);
                        } else {
                            insertRecord.setMaxConcurrentCalls(con);
                        }

                        lineCountMapper.insertSelective(insertRecord);

                    } else {//其余update
                        LineCount updateRecord = new LineCount();
                        updateRecord.setLineId(lineId);
                        updateRecord.setCalloutserverId(calloutserver_id);
                        if (i < remain) {
                            updateRecord.setMaxConcurrentCalls(con + 1);
                        } else {
                            updateRecord.setMaxConcurrentCalls(con);
                        }

                        LineCountExample updateExample = new LineCountExample();
                        LineCountExample.Criteria criteria = updateExample.createCriteria();
                        criteria.andCalloutserverIdEqualTo(calloutserver_id);
                        criteria.andLineIdEqualTo(lineId);
                        lineCountMapper.updateByExampleSelective(updateRecord, updateExample);
                    }
                }
            }
        }
        return true;

    }

}
