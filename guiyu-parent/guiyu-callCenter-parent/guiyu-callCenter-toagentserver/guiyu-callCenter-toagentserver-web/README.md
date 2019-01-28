
fs获取呼叫中心配置文件
lua configfs.lua http://127.0.0.1:8000/callcenter.conf.xml autoload_configs/callcenter.conf.xml reload+mod_callcenter

fs创建号码
lua number_generator.lua 4000 4002
号码与密码相同


## 接口说明
接口url: rs/prelogin    
返回值：    
    {reuslt:true}  允许登录    
    {result:false, code: 0307003}  对方在忙，禁止登录    

注：如果号码已经在其他地方登录，但是没有处于通话中，则需要向其发送verto消息： {msgtype:logout}，强制其登出    

接口url：agentsum?orgCode=4ea550901e27419e848da39f9f0a626e    
接口描述：获取座席登录数量统计接口    
返回值：    
```json
    {
      "code": "0",
      "msg": "请求成功",
      "success": true,
      "body": {
      "onlineCount": 1,
      "totalCount": 27
      }
    }
```


## 搭建更新内容
2019-01-22:
增加全局变量：verto_wss_port

## freeswitch更新
### 0、加载相关模块
reload mod_verto
reload mod_local_stream
```xml
<configuration name="modules.conf" description="Modules">
  <modules>
    <load module="mod_console"/>
    <load module="mod_logfile"/>
    <load module="mod_event_socket"/>
    <load module="mod_sofia"/>
    <load module="mod_loopback"/>
    <load module="mod_commands"/>
    <load module="mod_db"/>
    <load module="mod_dptools"/>
    <load module="mod_dialplan_xml"/>
    <load module="mod_lua"/>
    <load module="mod_say_en"/>
    <load module="mod_sndfile"/>
    <load module="mod_cdr_csv"/>
    <load module="mod_bcg729"/>
    <load module="mod_asr"/>
    <load module="mod_callcenter"/>
    <load module="mod_verto"/>
    <load module="mod_rtc"/>
    <load module="mod_local_stream"/>
  </modules>
</configuration>
```


### 1、增加https证书
将wss.pem放置到fs的conf/certs目录下

### 2、更新scripts文件
将scripts目录下的文件configfs.lua、number_generator.lua、vchat.lua上传到freeswitch的conf/scripts目录

### 3、增加dialplan
将dialplan/01_toagent.xml拷贝到freeswitch的conf/dialplan/default目录下

### 4、修改配置文件
修改文件：var.xml
增加如下内容：
```xml
<X-PRE-PROCESS cmd="set" data="verto_wss_port=8082"/>
```

替换文件：conf/autoload_configs/verto.conf.xml
```xml
<configuration name="verto.conf" description="HTML5 Verto Endpoint">

  <settings>
    <param name="debug" value="0"/>
  </settings>

  <profiles>
    <profile name="default-v4">
      <param name="bind-local" value="$${local_ip_v4}:$${verto_wss_port}" secure="true"/>
      <param name="force-register-domain" value="$${domain}"/>
      <param name="secure-combined" value="$${conf_dir}/certs/wss.pem"/>
      <param name="secure-chain" value="$${conf_dir}/certs/wss.pem"/>
      <param name="userauth" value="true"/>
      <param name="blind-reg" value="false"/>
      <param name="mcast-ip" value="224.1.1.1"/>
      <param name="mcast-port" value="1337"/>
      <param name="rtp-ip" value="$${local_ip_v4}"/>
      <!--  <param name="ext-rtp-ip" value=""/> -->
      <param name="local-network" value="localnet.auto"/>
      <param name="outbound-codec-string" value="PCMU,opus,vp8"/>
      <param name="inbound-codec-string" value="PCMU,opus,vp8"/>

      <param name="apply-candidate-acl" value="localnet.auto"/>
      <param name="apply-candidate-acl" value="wan_v4.auto"/>
      <param name="apply-candidate-acl" value="rfc1918.auto"/>
      <param name="apply-candidate-acl" value="any_v4.auto"/>
      <param name="timer-name" value="soft"/>

    </profile>
  </profiles>
</configuration>
```

上面修改完成后，执行下面fs命令：
reloadxml
reload mod_verto




