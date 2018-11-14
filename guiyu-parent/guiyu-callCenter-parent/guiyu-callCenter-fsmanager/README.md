### 申请freeswitch资源接口 ###
```
接口描述： 该接口主要用于申请freeswitch资源接口     
接口类型： HTTP/GET    
接口URL：  /applyfs?serviceId=192.168.1.11:18011&serviceType=calloutserver
请求参数： serviceType：主要有calloutserver、callinserver、callcenter、fsline四种
           serviceId：服务Id 
           
	"result":true,
	"data":{
			"fsAgentId":"192.168.1.11:18022",
			"fsAgentAddr":"192.168.1.11:18011",
			"fsEslPort":18021,
			"fsEslPwd":"123456qwert",
			"fsInPort":50601,
			"fsOutPort":50602
	}
}

•	实现：
•	查询fsbind表，检查该服务是否已申请过FreeSWITCH资源，若申请过，则处理如下：
•	通过Eureka检查之前绑定的fsagent服务是否存活
•	未存活，继续申请新的实例
•	若存活，则调用fsagent服务的健康检查接口，检查服务是否正常
•	正常，则return相关信息
•	不正常，继续申请新的实例
•	查询Eukera服务，获取所有的FsAgent列表，从中查出未在fsbind中登记过的fsagent实例
•	调用该fsagent的健康检查接口，检查服务是否正常
•	正常，将查出的fsagent实例与请求服务的绑定信息存入fsbind，并return
•	不正常，则继续检查新的空闲fsagent实例
```
### 释放freeswitch资源接口 ###
   ```
   接口描述： 该接口主要用于释放freeswitch资源     
   接口类型： HTTP/GET    
   接口URL：  //releasefs?serviceId=xxx
   请求参数： serviceId：服务Id 
   
   
   •	实现：
   •	从fsbind中将绑定信息清理掉
   ```
   
 ### 增加线路接口 ###
   ```
    接口描述： 该接口主要用于增加线路     
    接口类型： HTTP/POST    
    接口URL：  /lineinfos
    请求参数： 
      {
      	"lineId":"xx",
      	"sipIp":"192.168.1.22",
      	"sipPort":"5060",
      	"codec":"G729",
      	"callerNum":"",
      	"calleePrefix":"3"
      }
     
    •	实现：
    •	生成网关配置文件，将文件存储到数据库lineconfig表中
    •	在生成拨号方案时，lineId作为主叫名称，存储到数据库
    •	调用所有fsagent的配置文件更新接口
   ```
  
### 修改线路接口 ###
   ```
    接口描述： 该接口主要用于修改线路     
    接口类型： HTTP/PUT    
    接口URL：  /lineinfos
    请求参数： 
       {
       	"lineId":"xx",
       	"sipIp":"192.168.1.22",
       	"sipPort":"5060",
       	"codec":"G729",
       	"callerNum":"",
       	"calleePrefix":"3"
      }
      
     •	实现：
     •	生成网关配置文件，将文件存储到数据库lineconfig表中
     •	在生成拨号方案时，lineId作为主叫名称，存储到数据库
     •	调用所有fsagent的配置文件更新接口
   ```  
### 删除线路接口 ###
   ```
     接口描述： 该接口主要用于删除线路     
     接口类型： HTTP/DELETE    
     接口URL：  /lineinfos/xxx
     请求参数： lineId
    
     •	实现：
     •	删除lineconfig中对应的线路配置文件
     •	调用所有fsagent的删除线路接口
   ``` 
### 获取线路配置文件接口 ###
   ```
     接口描述： 该接口主要用于获取线路配置文件    
     接口类型： HTTP/GET    
     接口URL：  /linexmlinfos/xxx
     请求参数： lineId
     返回：
    {
    	"result":true,
    	"code":0,
    	"data":[
    			{"configType":"dialplan","fileName":"01_jingdong.xml","fileData":"5L2g5aW977yM5L2g5Lus5p2l6Ieq5LqO5ZOq6YeM"},
    			{"configType":"gateway","fileName":"gw_jingdong.xml","fileData":"5L2g5aW977yM5L2g5Lus5p2l6Ieq5LqO5ZOq6YeM"}
    		]
    }
     •	实现：
     •	根据lineId从数据库中读取后，封装成base64编码，组装成json格式返回
   ```   
### 获取所有配置文件接口 ###
   ```
     接口描述： 该接口主要用于获取所有配置文件    
     接口类型： HTTP/GET    
     接口URL：  /linexmlinfos
     请求参数： lineId
     返回：
    {
  "result":true,
  "code":0,
  "data":[
  {"configType":"dialplan","fileName":"01_jingdong.xml","fileData":"5L2g5aW977yM5L2g5Lus5p2l6Ieq5LqO5ZOq6YeM"},
  {"configType":"gateway","fileName":"gw_jingdong.xml","fileData":"5L2g5aW977yM5L2g5Lus5p2l6Ieq5LqO5ZOq6YeM"}
  ]
  }
     •	实现：
     •	将lineconfig表中所有的配置文件读取出来，封装成base64编码返回
     
   ```
### 模板是否存在接口 ###
   ```
      接口描述： 该接口主要用于模板是否存在    
      接口类型： HTTP/GET    
      接口URL：  /istempexist?tempId=gjdk_en
      请求参数： tempId
              
      • 实现：
      • 调用所有fsagent的模板存在接口，如果有一个fsagent录音不存在，则报警，并返回false  
   ```   
            
### 下载模板录音 ###
   ```
       接口描述： 该接口主要用于下载模板录音    
       接口类型： HTTP/GET    
       接口URL： /downloadtempwav?tempId=1
       请求参数： tempId
     
       • 实现：
       • 获取所有fsagent服务列表，并调用下载模板录音接口
   ```               