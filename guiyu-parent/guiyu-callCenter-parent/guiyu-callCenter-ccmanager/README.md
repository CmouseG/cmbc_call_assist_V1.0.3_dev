o接口：查看对接客户线路的FreeSWITCH的ip和端口列表 #ccmanager
URL：GET /fsoutlines
返回：
{
"result":true,
"data":[
{"fslineId":"xx","fsIpPort":"192.168.1.22:50602"},
{"fslineId":"yy","fsIpPort":"192.168.1.33:50602"}
]
}​​
实现：
从Eureka获取所有的fsline服务列表
调用fsline的获取基本信息接口，从里面提取fslineId、fsIp和fsOutPort，拼装后返回