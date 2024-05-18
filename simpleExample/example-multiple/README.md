## 简介
演示：多服单进程启动、多服多进程启动、代码组织与约定

关于[代码组织与约定-文档](https://www.yuque.com/iohao/game/keyrxn)

```text
.
├── the-client             【模拟游戏客户端的请求】
├── the-common-broker      【Broker（游戏网关）】
├── the-common-data        【公共的模块包】
├── the-common-external    【游戏对外服】
├── the-one-application    【多服单进程启动演示】
└── the-weather-game-logic 【游戏逻辑服】
```

## 多服单进程的启动演示
> 启动类 MyOneApplication.java；在 the-one-application 模块中。

## 多服多进程的启动演示
多服多进程启动时，需要先启动 Broker（游戏网关），其他逻辑服随意；

1 启动 Broker（游戏网关）
> 启动类 MyBrokerServer.java；在 the-common-broker 模块中。

2 启动游戏逻辑服
> 启动类 WeatherLogicStartupApplication.java；在 the-weather-game-logic 模块中。

3 启动游戏对外服
> 启动类 MyExternalServer.java；在 the-common-external 模块中。


## 启动模拟客户端，发起请求
当游戏服务器相关的启动完成后，可以向服务器发送请求；
> 启动类 MyOneWebsocketClient.java；在 the-client 模块中。

