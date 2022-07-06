
## 这个仓库用于存放 ioGame 相关的使用示例

<h1 align="center" style="text-align:center;">
  ioGame
</h1>
<p align="center">
	<strong>国内首个基于蚂蚁金服 SOFABolt 的 java 网络游戏服务器框架；无锁异步化、事件驱动的架构设计；</strong>
	<br>
	<strong>通过 ioGame 可以很容易的搭建出一个集群无中心节点、分步式的网络游戏服务器！</strong>
	<br>
	<strong>轻量级、更节约、更简单、开箱即用、无配置文件、启动快、超高性能</strong>
	<br>
	<strong>业务框架平均每秒可以执行 1152 万次业务逻辑</strong>
	<br>
	<strong>对webMVC开发者友好</strong>
    <br>
	<strong>代码即文档</strong>
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

如果您觉得还不错，帮忙给个 start 关注!

<br>

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
└── example-game-collect （实战示例、坦克）
    ├── fxgl-tank （游戏引擎-坦克游戏启动端）
    ├── game-common （一些通用的功能）
    ├── game-common-proto （示例 pb ）
    ├── game-external （对外服）
    ├── game-logic-hall （登录逻辑服）
    ├── game-logic-tank （坦克逻辑服）
    └── game-one （一键启动 游戏网关、游戏逻辑服（登录和坦克）、对外服）

```


<br>
