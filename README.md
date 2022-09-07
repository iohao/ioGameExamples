

<h2 align="center" style="text-align:center;">
  ioGame
</h2>
<p align="center">
	<strong>国内首个基于蚂蚁金服 SOFABolt 的 java 网络游戏服务器框架；无锁异步化、事件驱动的架构设计；</strong>
	<br>
	<strong>通过 ioGame 可以很容易的搭建出一个集群无中心节点、有状态的分步式网络游戏服务器 </strong>
  <br>
	<strong>无中间件依赖、代码即文档、JSR380、断言 + 异常机制 = 更少的维护与开发成本</strong>
	<br>
	<strong>轻量级、启动快、更节约、更简单、开箱即用、无配置文件、超高性能</strong>
	<br>
	<strong>近原生、业务框架平均每秒可以执行 1152 万次业务逻辑</strong>
	<br>
	<strong>神级特性：业务代码访问定位与跳转</strong>
  <br>
	<strong>对webMVC开发者友好</strong>
  <br>
	<strong>可跨进程通信</strong>
</p>
<p align="center">
	<a href="https://www.yuque.com/iohao/game">https://www.yuque.com/iohao/game</a>
</p>

<p align="center">
	<a target="_blank" href="https://www.oracle.com/java/technologies/downloads/#java17">
		<img src="https://img.shields.io/badge/JDK-17-green.svg" alt="JDK 17" />
	</a>
	<br>
	<a target="_blank" href="https://license.coscl.org.cn/Apache2/">
		<img src="https://img.shields.io/:license-Apache2-blue.svg" alt="Apache 2" />
	</a>
	<br />
	<a target="_blank" href='https://gitee.com/iohao/iogame'>
		<img src='https://gitee.com/iohao/iogame/badge/star.svg' alt='gitee star'/>
	</a>
	<a target="_blank" href='https://github.com/iohao/iogame'>
		<img src="https://img.shields.io/github/stars/iohao/iogame.svg?logo=github" alt="github star"/>
	</a>
</p>
<hr />

<br/>

过去、现在、将来都不会有商业版本，所有功能全部开源！

只做真的完全式开源，拒绝虚假开源，售卖商业版，不搞短暂维护！

承诺项目的维护周期是十年起步， 2022-03-01起，至少十年维护期！

提供高质量的使用文档！

如果您觉得还不错，帮忙给个 star 关注!

<br>

## maven 配置指南 - 阿里云公共仓库配置
https://developer.aliyun.com/mvn/guide

```xml
<!-- 
打开 maven 的配置文件（ windows 机器一般在 maven 安装目录的 conf/settings.xml ），在<mirrors></mirrors>标签中添加 mirror 子节点:
-->
<mirror>
  <id>aliyunmaven</id>
  <mirrorOf>*</mirrorOf>
  <name>阿里云公共仓库</name>
  <url>https://maven.aliyun.com/repository/public</url>
</mirror>
```



## 综合示例请看

这个仓库用于存放 ioGame 相关的使用示例

https://www.yuque.com/iohao/game/ruaqza

**示例内容包含**

1. 多服多进程的方式部署
2. 单体应用方式部署
3. [springboot 集成](https://www.yuque.com/iohao/game/evkgnz)
4. [JSR380验证](https://www.yuque.com/iohao/game/ghng6g)
5. [断言 + 异常机制 = 清晰简洁的代码](https://www.yuque.com/iohao/game/avlo99)
6. [请求、无响应](https://www.yuque.com/iohao/game/nelwuz#qs7yJ)
7. [请求、响应](https://www.yuque.com/iohao/game/nelwuz#UAUE4)
8. [广播指定玩家](https://www.yuque.com/iohao/game/nelwuz#EY6Ln)
9. [广播全服玩家](https://www.yuque.com/iohao/game/nelwuz#mStIA)
10. [单个逻辑服与单个逻辑服通信请求 - 有返回值（可跨进程）](https://www.yuque.com/iohao/game/nelwuz#L9TAJ)
11. [单个逻辑服与单个逻辑服通信请求 - 无返回值（可跨进程）](https://www.yuque.com/iohao/game/nelwuz#gtdrv)
12. [单个逻辑服与同类型多个逻辑服通信请求（可跨进程）](https://www.yuque.com/iohao/game/nelwuz#gSdya)
13. [业务参数自动装箱、拆箱基础类型（解决协议碎片问题）](https://www.yuque.com/iohao/game/ieimzn)
14. [游戏文档生成](https://www.yuque.com/iohao/game/irth38)
15. [业务协议文件 .proto 的生成](https://www.yuque.com/iohao/game/vpe2t6#R5w5m)
16. [登录相关：包含重复登录、顶号相关的业务](https://www.yuque.com/iohao/game/tywkqv)
17. 启动多个同类型的游戏逻辑服
18. 将项目打成 jar 包
19. 启动 jar 包
20. docker 运行综合示例
21. 纯原生 proto 数据的模拟客户端模块
22. CocosCreator 与综合示例联调
23. 模块的整理与建议（路由相关）



还有更多，将在后续添加 ... ...



## 阅读前的建议

阅读综合示例这部分时，建议先看[简单介绍](https://www.yuque.com/iohao/game/wwvg7z)、[快速从零编写服务器完整示例](https://www.yuque.com/iohao/game/zm6qg2)这两部分；



## 源码目录介绍
```text
.
├── example （示例）
│   ├── example-broadcast （广播示例）
│   ├── example-cluster-run-one （集群示例）
│   ├── example-endpoint 示例目录 玩家动态绑定逻辑服节点
│   ├── example-for-spring （spring集成示例）
│   ├── example-for-tcp-socket （对外服使用tcp协议示例）
│   ├── example-hook 示例目录 钩子相关(心跳，用户上线、下线)
│   ├── example-interaction （逻辑服与逻辑服之间的交互，可跨进程通信）
│   ├── example-interaction-same 示例目录 逻辑服间的相互通信；请求同类型多个逻辑服的结果集（可跨进程）
│   ├── example-parent
│   ├── example-redisson-lock （分步式锁）
│   ├── example-redisson-lock-spring-boot-starter （分步式锁 for springBootStarter）
│   └── example-run-one （快速启动示例）
├── example-game-collect （实战示例、坦克）
│   ├── fxgl-tank （游戏引擎-坦克游戏启动端）
│   ├── game-common （一些通用的功能）
│   ├── game-common-proto （示例 pb ）
│   ├── game-external （对外服）
│   ├── game-logic-hall （登录逻辑服）
│   ├── game-logic-tank （坦克逻辑服）
│   └── game-one （一键启动 游戏网关、游戏逻辑服（登录和坦克）、对外服）
└── example-springboot #### 综合示例 #### 
    ├── spring-common-pb （通用PB相关）
    ├── spring-game-broker # 游戏网关服 - 启动项目
    ├── spring-game-external # 游戏对外服 - 启动项目
    ├── spring-game-logic-classes （游戏逻辑服 - 班级）
    ├── spring-game-logic-classes-starter # 游戏逻辑服 - 班级 - 启动项目
    ├── spring-game-logic-school （游戏逻辑服 - 学校）
    ├── spring-game-logic-school-starter # 游戏逻辑服 - 学校 - 启动项目
    ├── spring-websocket-client #游戏客户端模拟 - 启动项目
    └── spring-z-one-game （一键启动）
```


<br>
