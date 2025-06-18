### 简介

gm 后台管理 与 ioGame 交互示例。

see https://iohao.github.io/game/docs/manual_high/gm


当我们将项目启动后，在浏览器输入
- http://localhost:8080/gm/recharge 可触发 gm - recharge 方法。
- http://localhost:8080/gm/notice 可触发 gm - notice 方法。该示例在请求游戏逻辑服时，不会携带上玩家的元信息。
- http://localhost:8080/other/notice 可触发 other - notice 方法，会从游戏对外服获取一些玩家的数据，如玩家的元信息、所绑定的游戏逻辑服 ...等。该示例在请求游戏逻辑服时，会携带上玩家的元信息。


