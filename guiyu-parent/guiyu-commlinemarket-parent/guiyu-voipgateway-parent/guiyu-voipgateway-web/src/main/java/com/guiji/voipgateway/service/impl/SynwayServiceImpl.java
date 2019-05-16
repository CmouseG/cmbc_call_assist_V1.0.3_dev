package com.guiji.voipgateway.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.JsonObject;
import com.guiji.utils.StrUtils;
import com.guiji.voipgateway.constants.VoipGatewayConstants;
import com.guiji.voipgateway.model.*;
import com.guiji.voipgateway.service.ThirdGateWayService;
import com.guiji.voipgateway.synway.dao.SynwayMapper;
import com.guiji.voipgateway.synway.dao.entity.ShareTabQuery;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * @version V1.0
 * @Description: 三汇语音网关服务
 * @Author: weiyunbo
 * @date 2019年1月25日 下午6:27:46
 */
@Slf4j
@Service("synwayService")
public class SynwayServiceImpl implements ThirdGateWayService {
    @Autowired
    SynwayMapper synwayMapper;

    /**
     * 根据设备名称查找设备基本信息
     *
     * @param devName
     * @return
     */
    @Override
    public GwDevtbl queryCompanyByDevName(String devName) {
        if (StrUtils.isNotEmpty(devName)) {
            //查询所有设备表
            ShareTabQuery shareTabQuery = new ShareTabQuery();
            shareTabQuery.setTableSchema(VoipGatewayConstants.SYN_WAY_SCHEMA);    //schema
            shareTabQuery.setTableNamePostfix("\\_devtbl");    //要查询表的后缀 _特殊符号转义
            List<String> devtblTabNameList = synwayMapper.queryShareTabNameList(shareTabQuery);
            if (devtblTabNameList != null && !devtblTabNameList.isEmpty()) {
                //遍历设备表,倒着查2个表,还查不到结束
                int i = 1;
                for (String tabName : devtblTabNameList) {
                    if (i > 2) break;
                    log.info("设备名称{}到表{}中查找", devName, tabName);
                    GwDevtbl gwDevtbl = synwayMapper.queryCompanyByDevName(tabName, devName);
                    if (gwDevtbl != null) {
                        return gwDevtbl;
                    }
                    i++;
                }
                log.error("设备名称{}没有查到对应的设备信息..", devName);
            }
        }
        return null;
    }

    /**
     * 根据公司编号查找公司信息
     *
     * @param companyId
     * @return
     */
    public Company queryCompanyById(Integer companyId) {
        if (companyId != null) {
            return synwayMapper.queryCompanyById(companyId);
        }
        return null;
    }

    /**
     * 根据客户编号查询客户下所有设备
     *
     * @param companyId
     * @return
     */
    public List<GwDevtbl> queryGwDevtblListByCompId(Integer companyId) {
        if (companyId != null) {
            String tabName = companyId / 10 + "_devtbl";    //表名：(公司ID/10)_devtbl
            return synwayMapper.queryGwDevtblListByCompId(tabName, companyId);
        }
        return null;
    }

    /**
     * 根据设备编号查询设备基本信息
     *
     * @param companyId
     * @param devId
     * @return
     */
    public GwDevtbl queryGwDevByDevId(Integer companyId, Integer devId) {
        if (companyId != null && devId != null) {
            String tabName = companyId / 10 + "_devtbl";    //表名：(公司ID/10)_devtbl
            return synwayMapper.queryGwDevByDevId(tabName, devId);
        }
        return null;
    }


    /**
     * 根据设备编号查询该设备下所有端口情况
     *
     * @param companyId
     * @param devId
     * @return
     */
    public List<SimPort> querySimPortListByDevId(Integer companyId, Integer devId) {
        if (companyId != null && devId != null) {
            String tabName = companyId + "_porttbl";    //表名：(公司ID)_porttbl
            return synwayMapper.querySimPortListByDevId(tabName, devId);
        }
        return null;
    }

    /**
     * 1	可用
     * 2	不可用
     * 3	停用
     * 4	断线
     * 5	启用
     * 6	锁定
     * 7	正常
     * 8	不同步
     * 9	远端阻断
     * 10	闭塞
     * 11	其它
     * 12	在线
     * 13	离线
     * 14	异常
     * 15	不支持
     *
     * @param ro
     * @return
     */
    @Override
    public PortStatusEnum querySimPortStatus(PortRo ro) {

        if(StringUtils.isEmpty(ro.getIp())) {
            return PortStatusEnum.IDLE;
        }

        List<Integer> statusList = getStatusList(ro.getIp());

        for (int i = 0; i < statusList.size(); i++) {

            int status = statusList.get(i);

            if(i == ro.getPortNo()) {
                switch (status) {
					case 0:
						return PortStatusEnum.IDLE;
					case 1:
					case 2:
					case 3:
					case 4:
					case 5:
					case 6:
					case 7:
					case 9:
						return PortStatusEnum.BUSY;
					case 10:
					case 11:
						return PortStatusEnum.OTHER;
				}
            }
        }
        return PortStatusEnum.OTHER;
    }


    public static List<Integer> getStatusList(String ip) {
        String auth = "ApiUserAdmin:admin";
        byte[] encodedAuth = Base64.encodeBase64(auth.getBytes(Charset.forName("US-ASCII")));
        String authHeader = "Basic " + new String(encodedAuth);

        CloseableHttpClient client = HttpClients.createDefault();


        HttpPost post = new HttpPost("http://"+ip+"/API/QueryPortInfo");

        JSONObject jsonObject = new JSONObject();

        jsonObject.put("event", "getportinfo");

        StringEntity myEntity = new StringEntity(jsonObject.toString(), ContentType.APPLICATION_JSON);
        post.addHeader("Authorization", authHeader);
        RequestConfig config = RequestConfig.custom()
                .setConnectionRequestTimeout(2000)
                .setConnectTimeout(2000)
                .setSocketTimeout(2000)
                .build();

        post.setConfig(config);
        post.setEntity(myEntity); // 设置请求体
        String responseContent = null; // 响应内容
        CloseableHttpResponse response = null;
        String s = "";
        try {
            response = client.execute(post);
            if (response.getStatusLine().getStatusCode() == 200) {
                HttpEntity entity = response.getEntity();
                responseContent = EntityUtils.toString(entity, "UTF-8");

                JSONObject parse = JSONObject.parseObject(responseContent);

                System.out.println(responseContent);

                String content = parse.getString("content");

                if (!"ok".equals(parse.getString("result"))) {
                    return null;
                }

                s = content.substring(content.lastIndexOf(":") + 1, content.length() - 1);

            }

        } catch (IOException e) {

            return null;

        } finally {
            IOUtils.closeQuietly(client);
            IOUtils.closeQuietly(response);
        }

        if (StringUtils.isEmpty(s)) {
            return null;
        } else {
            List<Integer> status = new ArrayList<>();

            for (String str : s.split(",")) {
                status.add(Integer.valueOf(str));
            }

            return status;
        }

    }

//    public static void main(String[] args) {
//        System.out.println(getStatusList("212.129.147.11:30001"));
//    }

}
