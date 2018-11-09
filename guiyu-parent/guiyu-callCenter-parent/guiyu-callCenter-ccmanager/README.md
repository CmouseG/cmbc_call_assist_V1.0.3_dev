### 接口：查看对接客户线路的FreeSWITCH的ip和端口列表 #ccmanager
* 实现：
    * 从Eureka获取所有的fsline服务列表
    * 调用fsline的获取基本信息接口，从里面提取fslineId、fsIp和fsOutPort，拼装后返回
o接口：查看客户所有线路接口 #ccmanager
URL：GET /lineinfos?customerId=xx
返回：
{
"result":true,
"data":[
{
"lineId":"xx",
"lineName":"nanjing",
"sipIp":"192.168.1.22",
"sipPort":"50601",
"codec":"PCMU",
"callerNum":"",
"calleePrefix":"3",
"maxConcurrentCalls":30,
"remark":""
}
]
}
### 接口：增加线路接口 #ccmanager
URL：POST /lineinfos
参数：{customerId、lineName、sipIp、sipPort、codec(可选: PCMA/PCMU/G729)、callerNum、calleePrefix、maxConcurrentCalls、remark}
返回：{"result":"true/false"}
实现：
本地存储数据库lineinfo
调用fsmanager的增加线路接口
读取所有的calloutserver，将线路并发数平分到各个calloutserver，存储到linecount表中
### 接口：修改线路接口 #ccmanager
URL：PUT /lineinfos/1
参数：{lineId、lineName、sipIp、sipPort、codec、callerNum、calleePrefix、maxConcurrentCalls、remark}
返回：{"result":"true/false"}
实现：
本地更新数据库lineinfo
调用fsmanager的更新线路接口
若并发数有更新，则从linecount表读取线路并发数分配信息，并根据重新计算的并发数进行修改保存。
o功能：启动定时服务，根据外呼服务调整并发数 #ccmanager
说明：检查有新增的calloutserver，则再次平分各个线路的并发数，存到linecount表中
### 接口：删除线路接口 #ccmanager
URL：DELETE /lineinfos/1
返回：{"result":"true/false"}
实现：
本地删除数据库lineinfo记录，同时调用fsmanager的删除线路接口
删除linecount中对应的线路并发数信息

### 接口：获取客户线路列表 #ccmanager @调度中心
说明：该接口用于获取用户所有的线路列表及并发数
URL：GET /out/lineinfos?customerId=1
参数：customerId 客户id
返回：{data:[{"lineId":"xx","concurrent":30},{"lineId":"yy","concurrent":40}]}
实现：
从lineinfos表中读取相应的信息返回即可
### 接口：启动客户呼叫计划 #ccmanager @调度中心
URL：GET /out/startcallplan?customerId=1&tempId=gjdk_en&lineId=nanjingline
参数：
customerId 客户id
tempId 模板id
lineId 线路Id
实现：
根据线路id到lineinfo表中查询线路是否存在，不存在则返回线路不存在错误，并报警
到calloutplan中查询该线路是否存在待呼叫或进行中的计划，存在则返回线路繁忙错误，并报警
调用fsmanager的模板是否存在接口，模板不存在处理如下：
返回模板不存在错误，并报警
调用fsmanager的下载模板接口
调用所有calloutserver的启动客户呼叫计划接口

### 接口：获取客户指定时间内的通话记录列表 #ccmanager
URL：GET /callrecord?startTime="2018-10-24 18:00:00"&endTime="2018-10-24 18:36:00"
返回：
### 接口：查看通话记录详情 #ccmanager