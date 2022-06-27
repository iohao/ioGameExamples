package com.iohao.game.tank.net;

import cn.hutool.core.util.RandomUtil;
import com.iohao.game.action.skeleton.core.CmdKit;
import com.iohao.game.bolt.broker.client.external.bootstrap.message.ExternalMessage;
import com.iohao.game.collect.common.GameConfig;
import com.iohao.game.collect.proto.common.LoginVerify;
import com.iohao.game.common.kit.ProtoKit;
import com.iohao.game.common.kit.StrKit;
import com.iohao.game.tank.net.onmessage.TankEnterRoomOnMessage;
import com.iohao.game.tank.net.onmessage.TankLoginVerifyOnMessage;
import com.iohao.game.tank.net.onmessage.TankShootOnMessage;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft_6455;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.nio.ByteBuffer;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author 渔民小镇
 * @date 2022-03-06
 */
@Slf4j
@Getter
public class TankWebsocketClient {

    WebSocketClient webSocketClient;
    final Map<Integer, TankOnMessage> onMessageMap = new ConcurrentHashMap<>();

    final Map<Integer, Runnable> actionMap = new ConcurrentHashMap<>();


    private void initOnMessage() {
        put(TankEnterRoomOnMessage.me());
        put(TankLoginVerifyOnMessage.me());
        put(TankShootOnMessage.me());
    }

    private void put(TankOnMessage onMessage) {
        onMessageMap.put(onMessage.getCmdMerge(), onMessage);
    }

    public void request(ExternalMessage externalMessage) {
        byte[] bytes = ProtoKit.toBytes(externalMessage);

        webSocketClient.send(bytes);
    }

    public void start() throws Exception {
        this.initOnMessage();

        this.init();
        // 开始连接服务器
        webSocketClient.connect();
        int externalPort = FxlgTankConfig.externalPort;
        String ip = FxlgTankConfig.externalIp;
        log.info("start ws://{}:{}/websocket", ip, externalPort);
    }

    private void init() throws Exception {
        // 这里模拟游戏客户端
        int externalPort = FxlgTankConfig.externalPort;
        String ip = FxlgTankConfig.externalIp;

        var url = "ws://{}:{}" + GameConfig.websocketPath;

        var wsUrl = StrKit.format(url, ip, externalPort);
        log.info("ws url : {}", wsUrl);

        webSocketClient = new WebSocketClient(new URI(wsUrl), new Draft_6455()) {
            @Override
            public void onOpen(ServerHandshake handshakedata) {
                LoginVerify loginVerify = new LoginVerify();
                loginVerify.jwt = "jwt-" + RandomUtil.randomInt(10000);

                TankLoginVerifyOnMessage.me().request(loginVerify);
            }

            @Override
            public void onMessage(ByteBuffer byteBuffer) {
                // 接收消息
                byte[] dataContent = byteBuffer.array();

                ExternalMessage message = ProtoKit.parseProtoByte(dataContent, ExternalMessage.class);
                int cmdMerge = message.getCmdMerge();
                byte[] data = message.getData();

                TankOnMessage onMessage = onMessageMap.get(cmdMerge);

                if (Objects.nonNull(onMessage)) {
                    Runnable runnable = actionMap.remove(cmdMerge);
                    if (runnable != null) {
                        runnable.run();
                    } else {
                        Object bizData = onMessage.response(message, data);
                        int cmd = CmdKit.getCmd(cmdMerge);
                        int subCmd = CmdKit.getSubCmd(cmdMerge);
                        String onMessageName = onMessage.getClass().getSimpleName();
                        log.info("client 收到消息{}\n {}-{} {}  {}", message, cmd, subCmd, onMessageName, bizData);
                    }
                } else {
                    log.info("不存在处理类 onMessage: ");
                }
            }

            @Override
            public void onMessage(String message) {

            }

            @Override
            public void onClose(int code, String reason, boolean remote) {

            }

            @Override
            public void onError(Exception ex) {

            }
        };
    }

    private TankWebsocketClient() {

    }

    public static TankWebsocketClient me() {
        return Holder.ME;
    }

    /** 通过 JVM 的类加载机制, 保证只加载一次 (singleton) */
    private static class Holder {
        static final TankWebsocketClient ME = new TankWebsocketClient();
    }
}
