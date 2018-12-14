## 系统简介 ##
新一代硅语系统，采用微服务架构设计。
整个系统通过底层的公共服务，上层的AI服务，机器人服务构建人工智能服务平台。
在人工智能服务平台的基础上，我们构建了包括任务中心，策略中心，呼叫中心，接入中心等中台服务能力。
基于中台服务能力，在应用层可以根据不同客户，不同业务，不同行业定制开发不同的产品。
同时，本系统也设计并建设了完善的运维监控体系以保障整个平台的健康运行，强大的运营管理系统来统计和量化系统的能力和效果，并实施正向的优化，形成持续改进的闭环。

## 系统架构 ##
![系统架构图](http://192.168.1.5/mediawiki/images/4/46/%E7%B3%BB%E7%BB%9F%E6%9E%B6%E6%9E%84%E5%9B%BE.png)

## 技术架构 ##
![整体技术架构](http://192.168.1.5/mediawiki/images/e/e4/%E6%95%B4%E4%BD%93%E6%8A%80%E6%9C%AF%E6%9E%B6%E6%9E%84%E8%AE%BE%E8%AE%A1.png)

## 技术选型 ##
后端使用Spring Cloud技术体系

Java基于1.8版本

Spring Cloud 选择Edgware

Spring Boot 选择1.5.17

前端框架使用Vue 2.0, UI库使用Element 2.0


## 代码仓库 ##
http://192.168.1.68/si-talk/si-talk

http://192.168.1.68/si-talk/si-talk-web

## Maven 私服 ##
http://192.168.1.187:8081/

本地maven配置私服参考config/setting.xml

## 日志规范 ##
路径: /home/apps/logs/服务/子服务/类型_描述[日期].log

## 配置规范 ##
路径：/home/apps/configs/服务/子服务/application.yml

## 错误码规范 ##
1. 格式：0000000
2. 从左到右错误码含义：00(主服务标识，范围00-99) 00(子服务标识，范围00-99) 000(错误码,范围000-999,000表示成功)
3. 00 公共组件
4. 01 用户中心
5. 02 调度中心
6. 03 呼叫中心
7. 04 机器人能力中心
8. 05 AI能力中心
9. 06 运营中心
10. 07 影像服务
11. 08 进程管理
12. 09 数据分析服务



## 内部异常码规范 ##
1. 内部异常统一抛GuiyuException
2. 公共异常码统一定义在GuiyuExceptionEnum中。其他模块的异常码定义在各自的枚举中，各自的枚举类统一实现接口ExceptionEnum
3. 内部异常码采用8位(00000000)，从左到右含义：00(主服务标识，范围00-99) 00(子服务标识，范围00-99) 000(异常码,范围0000-9999)
4. 00 公共组件
5. 01 用户中心
6. 02 调度中心
7. 03 呼叫中心
8. 04 机器人能力中心
9. 05 AI能力中心
10. 06 运营中心
11. 07 影像服务
12. 08 进程管理
13. 09 数据分析服务
12. 内部统一抛异常码，记录日志。在接口层对异常码进行转码，转成对外的错误码返回。
13. 具体方法参照nas服务

## 需要提前安装的软件 ##
1. Docker
2. Git
3. JDk1.8
4. FastDFS
5. Mysql5.7版本，并配置主从
6. FreeSwitch
7. Redis5.0

## 记录用户操作日志方法 ##
1. 引入component模块
2. 在接口上加上注解，@SysOperaLog(operaTarget = "操作对象", operaType = "操作类型") 例如：数据字典增加接口，操作对象填“数据字典”，操作类型填“增加”
3. 请求头中塞入userId就可以记录用户操作日志
