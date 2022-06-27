游戏集合

这里是实战示例

示例目录说明
```text
example-game-collect
├── fxgl-tank 游戏引擎坦克客户端 示例目录
├── game-common 一些通用的功能
├── game-common-proto 示例 pb 示例目录
├── game-external 对外服 示例目录
├── game-logic-hall 登录逻辑服 示例目录
├── game-logic-tank 坦克逻辑服 示例目录
└── game-one 一键启动 游戏网关、游戏逻辑服（登录和坦克）、对外服 示例目录

```

> game-external 对外服 【启动类 GameExternalBoot】
> 
> game-logic-hall 登录逻辑服 【启动类 HallClientStartup】
> 
> game-logic-tank 坦克逻辑服 【启动类 TankClientStartup】
> 
> 这几个都是可以单独启动的，启动顺序网关、逻辑服、对外服，但是开发阶段每次分别启动这几个太麻烦了。建议一键启动 game-one
> 
> game-one  【启动类 GameOne.java】
