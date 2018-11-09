### 检查服务健康状态 ###
```
接口描述： 该接口主要用于检查服务健康状态     
接口类型： HTTP/GET    
接口URL：  /ishealthy

•	实现：
•	检查FreeSWITCH的esl端口是否处于开启状态
•	使用fs_cli -x执行status命令，并获取结果
•	上面流程可以走完，则返回true

```
### 获取freeswitch基本信息，包括freeswitch的ip和端口列表 ###
   ```
   接口描述： 该接口主要用于获取freeswitch基本信息，包括freeswitch的ip和端口列表     
   接口类型： HTTP/GET    
   接口URL：  /fsinfo
   请求参数： serviceId：服务Id 
   返回：
   {
   	"result":true,
   	"data":{
   		"fslineId":"xx",
   		"fsIp":"192.168.2.44",
   		"fsInPort":50601,
   		"fsOutPort":50602
   		"fsEslPort":18032,
   		"fsEslPwd":"xxxxx"
   	}
   }
   
   •	实现：
   •	读取event_socket.conf.xml配置文件，获取esl连接密码
   •通过调用fs_cli -x "sofia status"来获取命令结果，提取出需要的相关端口信息
   ```
   
 ### 配置文件更新通知接口 ###
   ```
    接口描述： 该接口主要用于配置文件更新通知     
    接口类型： HTTP/GET    
    接口URL：  /updatenotify?type=line&lineId=xxx
  
   
    •	实现：
    •	如果type=line，则做如下处理
    •	如果type!=line，报错返回
   ```
  
### 删除线路接口 ###
   ```
    接口描述： 该接口主要用于删除线路接口     
    接口类型： HTTP/DELETE    
    接口URL：  /lineinfos/xxx
    参数：lineId  （线路id）
     •	实现：
     •	将线路对应的配置文件从本地删除
     •	将网关从freeswitch中卸载
     •	重载配置文件
   ```  
### 模板是否存在接口 ###
   ```
     接口描述： 该接口主要用于模板是否存在接口     
     接口类型： HTTP/GET    
     接口URL：  /istempexist?tempId=gjdk_en
     请求参数： tempId
    
     •	实现：
     •	检查本地模板目录，是否存在指定模板
    
   ``` 
### 下载模板录音 ###
   ```
     接口描述： 该接口主要用于下载模板录音    
     接口类型： HTTP/GET    
     接口URL：  /downloadbotwav?tempId=xxx
     请求参数： tempId
   
     •	实现：
     •	判断模板录音是否已存在，存在则返回{result=true，msg=already exist}
     •从Eureka获取sellbot服务器地址，根据模板id从该服务器下载相应的模板录音文件
     •将模板录音放到/home/aiwav目录下
   ```   
### 下载tts话术录音 ###
   ```
     接口描述： 该接口主要用于获取所有配置文件    
    
     •	实现：
     •	使用tempId和callId到机器人中请求话术录音
     使用应答结果中的地址，下载话术录音
     
   ```
### 上传录音 ###
   ```
      接口描述： 该接口主要用于上传录音    
      接口类型： HTTP/POST    
      接口URL：  /uploadrecord
      请求参数： 
           {
           sysCode:xx,
           busiId:xx,
           busiType:xx,
           fileName:xxx.wav
           }   
      • 实现：
      • 将本地录音上传到影像服务，并返回获取到的录音地址  
   ```   
            
### fsagent启动时从fsmanager获取所有配置文件更新到本地 ###
### 调用定时服务，定期清理超过3天的录音文件 ###
  