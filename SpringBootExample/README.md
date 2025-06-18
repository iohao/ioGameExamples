## 综合示例
文档 ： https://iohao.github.io/game/docs/examples/server/example_spring_boot

<br>
.


**示例内容包含**

1. 多服多进程的方式部署
2. 单体应用方式部署
3. springboot 集成
4. JSR380 验证
5. 断言 + 异常机制 = 清晰简洁的代码
6. 请求、无响应
7. 请求、响应
8. 广播指定玩家
9. 广播全服玩家
10. 单个逻辑服与单个逻辑服通信请求 - 有返回值（可跨进程）
11. 单个逻辑服与单个逻辑服通信请求 - 无返回值（可跨进程）
12. 单个逻辑服与同类型多个逻辑服通信请求（可跨进程）
13. 业务参数自动装箱、拆箱基础类型（解决协议碎片问题）
14. 游戏文档生成
15. 业务协议文件 .proto 的生成
16. 登录相关：包含重复登录、顶号相关的业务
17. 启动多个同类型的游戏逻辑服
18. 将项目打成 jar 包
19. 启动 jar 包
20. docker 运行综合示例
21. 纯原生 proto 数据的模拟客户端模块
22. CocosCreator 与综合示例联调
23. 模块的整理与建议（路由相关）
24. k8s
25. 元信息
26. 游戏对外服缓存
27. 分布式事件总线



还有更多，将在后续添加 ... ...



## 阅读前的建议

阅读综合示例这部分时，建议先看[简单介绍](https://iohao.github.io/game/docs/intro)、[快速从零编写服务器完整示例](https://iohao.github.io/game/docs/quick_zero_demo)这两部分；



## 源码目录介绍

```text
.
├── spring-client （模拟客户端）
├── spring-common-pb （通用 PB 相关）
├── spring-game-broker （游戏网关服 - 启动项目）
├── spring-game-external （游戏对外服 - 启动项目）
├── spring-game-logic-classes （游戏逻辑服 - 班级）
├── spring-game-logic-classes-starter （游戏逻辑服 - 班级 - 启动项目）
├── spring-game-logic-common （游戏逻辑服的公共包）
├── spring-game-logic-hall （游戏逻辑服 - 大厅）
├── spring-game-logic-room-interaction-same （游戏逻辑服 - 房间 - 同类型启动多个）
├── spring-game-logic-school （游戏逻辑服 - 学校）
├── spring-game-logic-school-starter （游戏逻辑服 - 学校 - 可单独启动的项目）
└── spring-z-one-game （一键启动所有逻辑服）
```

<br>



## docker 部署

准备工作，确保机器上有 docker 相关环境。以下是在终端执行的，首次使用 docker 部署、运行需要的时间会长一些，因为会下载相关的镜像。



**1、 打 jar 包，在示例目录的根目录执行如下命令**

> mvnd package

执行完打 jar 包的命令后， ./spring-z-one-game/target 目录下会有 spring-z-one-game-1.0-SNAPSHOT.jar 的 jar 文件。



**2、在示例目录的根目录执行如下命令**

不要遗漏命令中的点 “.”;

> docker build -t spring-z-one-game .



**3、查看当前镜像**

> docker images spring-z-one-game

注意，第3步骤不是必须的。执行完这条命令后可以看见镜像是否存在。



**4、启动刚打包好的镜像**

> docker run --name spring-z-one-game -p 10100:10100 spring-z-one-game
