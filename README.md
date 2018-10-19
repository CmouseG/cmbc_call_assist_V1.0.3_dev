目录

    1 系统架构
    2 技术架构
    3 技术选型
    4 代码仓库
    5 Maven 私服
    6 日志规范
    7 配置规范

系统架构

系统架构图.png
技术架构

整体技术架构设计.png
技术选型

后端使用Spring Cloud技术体系

Java基于1.8版本

Spring Cloud 选择Edgware

Spring Boot 选择1.5.17

前端框架使用Vue 2.0, UI库使用Element 2.0
代码仓库

http://192.168.1.68/si-talk/si-talk

http://192.168.1.68/si-talk/si-talk-web
Maven 私服

http://192.168.1.187:8081/
日志规范

路径: /home/apps/logs/服务/子服务/类型_描述[日期].log
配置规范
路径：/home/apps/configs/服务/子服务/application.yml 