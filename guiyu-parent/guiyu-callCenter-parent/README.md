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
10.0307 toagentserver

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



















