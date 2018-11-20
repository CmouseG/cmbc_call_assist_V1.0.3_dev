## 测试服务器 ##
部署路径：/home/apps/callcenter

### 端口分配 ###    
fsmanager：18021    
fsline：18022   
ccmanager：18023    
calloutserver：18024    

fsagent2：18026    
freeswitch esl2：18027    
fsagent1：18028    
freeswitch esl1：18029    
    
## 错误码 ##
### 错误码格式 ###
1. 格式：0000000
2. 从左到右错误码含义：03(主服务标识，代表呼叫中心) 00(子服务标识，范围00-99) 000(错误码,范围000-999,000表示成功)
3. 0300 fsagent
4. 0301 fsmanager
5. 0302 fsline
6. 0303 ccmanager
7. 0304 ccweb
8. 0305 calloutserver
9. 0306 callinserver

### 错误码列表 ###
#### 0303001：线路繁忙 ####
线路已经处于拨打状态
#### 0303002：模板缺失 ####
#### 0303003：线路不存在 ####
#### 0305001：下载tts话术录音失败 ####
#### 0305002：请求不到sellbot资源 ####
#### 0305003：sellbot无响应 ####

## 技术点 ##
###  通过serviceid获取该服务实例列表 ###
```java
    //使用guiyu-utils下的ServerUtil类
    ServerUtil.getInstances(discoveryClient,"guiyu-callcenter-ccmanager");
```

### 获取服务自身的ip和端口 ###
使用ServerUtil的方法getUrlSelf(Registration registration)

### 请求动态url的接口 ###
使用FeignBuildUtil的方法feignBuilderTarget(Class<T> apiType, String url)


### 测试打电话 ###
```sql

/*47.97.179.12:3306/guiyu_callcenter
  username: callcenter
    password: callcenter@1234
*/
DELETE FROM  call_out_plan_0 WHERE call_id = '1e3d9806da3345b581781ee68e39kz06';
DELETE FROM  call_out_plan_1 WHERE call_id = '1e3d9806da3345b581781ee68e39kz06';
DELETE FROM  call_out_detail_0 WHERE call_id = '1e3d9806da3345b581781ee68e39kz06';
DELETE FROM  call_out_detail_1 WHERE call_id = '1e3d9806da3345b581781ee68e39kz06';
DELETE FROM  call_out_detail_record WHERE call_id = '1e3d9806da3345b581781ee68e39kz06';
DELETE FROM  call_out_record WHERE call_id = '1e3d9806da3345b581781ee68e39kz06';


/*192.168.1.81:3306/guiyu_callcenter
  username: callcenter
    password: callcenter@1234
*/
UPDATE  `dispatch_hour`  SET `hour` =  HOUR(NOW()) +19  WHERE dispatch_id  = '1e3d9806da3345b581781ee68e39kz06';
DELETE FROM dispatch_plan_0 WHERE plan_uuid = '1e3d9806da3345b581781ee68e39kz06';
DELETE FROM dispatch_plan_1 WHERE plan_uuid = '1e3d9806da3345b581781ee68e39kz06';
DELETE FROM dispatch_plan_2 WHERE plan_uuid = '1e3d9806da3345b581781ee68e39kz06';

INSERT INTO `dispatch_plan_1` (
  `id`,
  `plan_uuid`,
  `user_id`,
  `batch_id`,
  `phone`,
  `attach`,
  `params`,
  `status_plan`,
  `status_sync`,
  `recall`,
  `recall_params`,
  `robot`,
  `line`,
  `result`,
  `call_agent`,
  `clean`,
  `call_data`,
  `call_hour`,
  `gmt_create`,
  `gmt_modified`,
  `is_tts`,
  `replay_type`,
  `is_del`,
  `username`
) 
VALUES
  (
    '106',
    '1e3d9806da3345b581781ee68e39kz06',
    '16',
    '110',
    '018600397859',
    '111',
    NULL,
    '1',
    '0',
    '0',
    NULL,
    'xtw_en',
    '114',
    NULL,
    NULL,
    '0',
    '20181120',
    '11,12,13,14',
    '2018-11-18 11:14:09',
    '2018-11-18 11:14:09',
    '1',
    '0',
    '0',
    'admin'
  ) ;
```
















