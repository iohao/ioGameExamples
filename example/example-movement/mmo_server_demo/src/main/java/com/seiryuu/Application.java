package com.seiryuu;

import com.iohao.game.bolt.broker.client.AbstractBrokerClientStartup;
import com.iohao.game.bolt.broker.core.client.BrokerAddress;
import com.iohao.game.bolt.broker.core.common.IoGameGlobalConfig;
import com.iohao.game.bolt.broker.server.BrokerServer;
import com.iohao.game.bolt.broker.server.BrokerServerBuilder;
import com.iohao.game.external.core.ExternalServer;
import com.iohao.game.external.core.config.ExternalGlobalConfig;
import com.iohao.game.external.core.config.ExternalJoinEnum;
import com.iohao.game.external.core.hook.internal.IdleProcessSetting;
import com.iohao.game.external.core.micro.PipelineContext;
import com.iohao.game.external.core.netty.DefaultExternalCoreSetting;
import com.iohao.game.external.core.netty.DefaultExternalServer;
import com.iohao.game.external.core.netty.DefaultExternalServerBuilder;
import com.iohao.game.external.core.netty.micro.TcpMicroBootstrapFlow;
import com.iohao.game.external.core.netty.simple.NettyRunOne;
import com.seiryuu.gameServer.common.cmd.ActionCmd;
import com.seiryuu.gameServer.common.cmd.LoginCmd;
import com.seiryuu.gameServer.common.codec.MyTcpExternalCodec;
import com.seiryuu.gameServer.hooks.MySocketIdleHook;
import com.seiryuu.gameServer.hooks.MyUserHook;
import com.seiryuu.gameServer.logicServer.DemoLogicServer;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;

import java.util.List;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@ComponentScans(value = {
        @ComponentScan(basePackages = "com.seiryuu.gameServer")
})
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);

        // 游戏对外服端口
        int port = 16888;

        // 逻辑服列表
        List<AbstractBrokerClientStartup> logicServers = List.of(
                // 匹配 - 逻辑服
                new DemoLogicServer()
        );

        // 游戏对外服
        ExternalServer externalServer = createExternalServer(ExternalGlobalConfig.externalPort);

        BrokerServer brokerServer = createBrokerServer(IoGameGlobalConfig.brokerPort);

        new NettyRunOne()
                // 游戏对外服
                .setExternalServer(externalServer)
                // Broker（游戏网关服）
                .setBrokerServer(brokerServer)
                // 游戏逻辑服列表
                .setLogicServerList(logicServers)
                // 启动游戏服务器
                .startup();
    }

    /**
     * 创建网关
     *
     * @return
     */
    private static BrokerServer createBrokerServer(int port) {
        // Broker Server （游戏网关服） 构建器
        BrokerServerBuilder brokerServerBuilder = com.iohao.game.bolt.broker.server.BrokerServer.newBuilder()
                // broker （游戏网关）端口
                .port(port);

        // 构建游戏网关
        return brokerServerBuilder.build();
    }

    /**
     * 构建对外服
     *
     * @param externalPort
     * @return
     */
    private static ExternalServer createExternalServer(int externalPort) {
        var accessAuthenticationHook = ExternalGlobalConfig.accessAuthenticationHook;

        // 表示登录才能访问业务方法
        accessAuthenticationHook.setVerifyIdentity(true);

        // 添加不需要登录（身份验证）也能访问的业务方法 (action)
        accessAuthenticationHook.addIgnoreAuthCmd(LoginCmd.cmd, LoginCmd.loginVerify);
        accessAuthenticationHook.addIgnoreAuthCmd(LoginCmd.cmd, LoginCmd.registerVerify);

        // 测试用的
        accessAuthenticationHook.addIgnoreAuthCmd(ActionCmd.cmd, ActionCmd.enterGame);
        accessAuthenticationHook.addIgnoreAuthCmd(ActionCmd.cmd, ActionCmd.enterMap);
        accessAuthenticationHook.addIgnoreAuthCmd(ActionCmd.cmd, ActionCmd.move);

        // 游戏对外服 - 构建器
        DefaultExternalServerBuilder builder = DefaultExternalServer.newBuilder(externalPort)
                // websocket 方式连接；如果不设置，默认也是这个配置
                .externalJoinEnum(ExternalJoinEnum.TCP)
                // Broker （游戏网关）的连接地址；如果不设置，默认也是这个配置
                .brokerAddress(new BrokerAddress("127.0.0.1", IoGameGlobalConfig.brokerPort));

        // 设置 MicroBootstrapFlow 类，并重写 createVerifyHandler 方法
        builder.setting().setMicroBootstrapFlow(new TcpMicroBootstrapFlow() {
            @Override
            public void pipelineCodec(PipelineContext context) {
                context.addLast(new LengthFieldBasedFrameDecoder(ExternalGlobalConfig.CoreOption.packageMaxSize, 0, 4, 0, 0));
                context.addLast("codec", MyTcpExternalCodec.me());
            }
        });

        // 设置心跳
        idle(builder.setting());

        // 设置用户上线下线钩子
        userHook(builder.setting());

        // 构建游戏对外服
        return builder.build();
    }

    private static void idle(DefaultExternalCoreSetting setting) {
        // 创建 IdleProcessSetting，用于心跳相关的设置
        IdleProcessSetting idleProcessSetting = new IdleProcessSetting()
                .setIdleTime(5)
                // 添加心跳事件回调
                .setIdleHook(new MySocketIdleHook());

        // 设置心跳
        setting.setIdleProcessSetting(idleProcessSetting);
    }

    private static void userHook(DefaultExternalCoreSetting setting) {
        // 设置用户上线下线钩子
        setting.setUserHook(new MyUserHook());
    }
}
