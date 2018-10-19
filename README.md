## 目录 ##
    1 [系统架构](#id1)
    2 [技术架构](#技术架构)
    3 [技术选型](#技术选型)
    4 [代码仓库](#代码仓库)
    5 [Maven 私服](#Maven 私服)
    6 [日志规范](#日志规范)
    7 [配置规范](#配置规范)

## 系统架构  <a name="id1"></a>
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

## 日志规范 ##
路径: /home/apps/logs/服务/子服务/类型_描述[日期].log

## 配置规范 ##
路径：/home/apps/configs/服务/子服务/application.yml 