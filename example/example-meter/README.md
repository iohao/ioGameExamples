

### 请求数据

下面代码转换十六进制结果为

> 00000012080110001881803c200032060a0461626331

```java
    private static ExternalMessage getExternalMessage() {
        // 路由
        int cmd = 15;
        int subCmd = 1;
        // 业务数据
        HelloReq helloReq = new HelloReq();
        helloReq.name = "abc1";

        ExternalMessage request = ExternalKit.createExternalMessage(cmd, subCmd, helloReq);

        return request;
    }
```



### 启动为类

> MeterTcpSocketApplication.java



