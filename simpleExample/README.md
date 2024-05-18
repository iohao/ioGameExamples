<h2 align="center" style="text-align:center;">
  ioGame
</h2>
<p align="center">
  <strong>无锁异步化、事件驱动的架构设计；轻量级，无需依赖任何第三方中间件或数据库就能支持集群、分布式</strong>
  <br>
  <strong>通过 ioGame 可以很容易的搭建出一个集群无中心节点、集群自动化、多进程的分布式游戏服务器</strong>
  <br>
  <strong>包体小、启动快、内存占用少、更加的节约、无需配置文件、提供了优雅的路由访问权限控制</strong>
  <br>
  <strong>让开发者使用一套业务代码，无需改动，支持多种连接方式：WebSocket、TCP、UDP</strong>
  <br>
  <strong>让开发者用一套业务代码，能轻松切换和扩展不同的通信协议：Protobuf、JSON</strong>
  <br>
  <strong>近原生的性能；业务框架在单线程中平均每秒可以执行 1152 万次业务逻辑</strong>
  <br>
  <strong>代码即联调文档、JSR380验证、断言 + 异常机制 = 更少的维护成本</strong>
  <br>
  <strong>框架具备智能的同进程亲和性；开发中，业务代码可定位与跳转</strong>
  <br>
  <strong>架构部署灵活性与多样性：既可相互独立，又可相互融合</strong>
  <br>
  <strong>可同时与同类型的多个游戏逻辑服通信并得到数据</strong>
  <br>
  <strong>逻辑服之间可相互跨进程、跨机器进行通信</strong>
  <br>
  <strong>支持玩家对游戏逻辑服进行动态绑定</strong>
  <br>
  <strong>能与任何其他框架做融合共存</strong>
  <br>
  <strong>对 webMVC 开发者友好</strong>
  <br>
  <strong>无 spring 强依赖</strong>
  <br>
  <strong>零学习成本</strong>
  <br>
  <strong>javaSE</strong>
</p>
<p align="center">
	<a href="http://game.iohao.com">http://game.iohao.com</a>
</p>
<p align="center">
	<a target="_blank" href="https://www.oracle.com/java/technologies/downloads/#java17">
		<img src="https://img.shields.io/badge/JDK-21-green.svg" alt="JDK 21" />
	</a>
	<br>
	<a target="_blank" href="https://www.gnu.org/licenses/agpl-3.0.txt">
		<img src="https://img.shields.io/:license-AGPL3.0-blue.svg" alt="AGPL3.0" />
	</a>
	<br />
	<a target="_blank" href='https://gitee.com/iohao/ioGame'>
		<img src='https://gitee.com/iohao/ioGame/badge/star.svg' alt='gitee star'/>
	</a>
	<a target="_blank" href='https://github.com/iohao/ioGame'>
		<img src="https://img.shields.io/github/stars/iohao/ioGame.svg?logo=github" alt="github star"/>
	</a>
  <br />
  <a href="https://www.murphysec.com/dr/O8oMcWWWVoU9hV4M9z" alt="OSCS Status"><img src="https://www.oscs1024.com/platform/badge/iohao/ioGame.git.svg?size=small"/></a>
</p>
<hr />

<br/>



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
│   ├── example-redisson-lock （分布式锁）
│   ├── example-redisson-lock-spring-boot-starter （分布式锁 for springBootStarter）
│   └── example-run-one （快速启动示例）
├── example-game-collect （实战示例、坦克）
│   ├── fxgl-tank （游戏引擎-坦克游戏启动端）
│   ├── game-common （一些通用的功能）
│   ├── game-common-proto （示例 pb ）
│   ├── game-external （对外服）
│   ├── game-logic-hall （登录逻辑服）
│   ├── game-logic-tank （坦克逻辑服）
│   └── game-one （一键启动 游戏网关、游戏逻辑服（登录和坦克）、对外服）
```

<br>



## jmeter 压测

[https://gitee.com/iohao/iogame-example/tree/main/example/example-meter](https://gitee.com/iohao/iogame-example/tree/main/example/example-meter)