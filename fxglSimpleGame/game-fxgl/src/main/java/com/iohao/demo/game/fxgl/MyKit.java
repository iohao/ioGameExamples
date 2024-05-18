/*
 * ioGame
 * Copyright (C) 2021 - 2023  渔民小镇 （262610965@qq.com、luoyizhu@gmail.com） . All Rights Reserved.
 * # iohao.com . 渔民小镇
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */
package com.iohao.demo.game.fxgl;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.SpawnData;
import com.iohao.demo.game.common.MyCmd;
import com.iohao.demo.game.common.PlayerInfo;
import com.iohao.demo.game.common.PlayerMove;
import com.iohao.fx.game.component.control.Dir;
import com.iohao.fx.game.component.control.PlayerMoveComponent;
import com.iohao.game.sdk.command.CommandResult;
import com.iohao.game.sdk.command.DefaultCommandResult;
import com.iohao.game.sdk.command.ListenCommand;
import com.iohao.game.sdk.command.RequestCommand;
import com.iohao.game.sdk.kit.CmdKit;
import com.iohao.game.sdk.net.NetCoreSetting;
import com.iohao.game.sdk.net.NetJoinEnum;
import com.iohao.game.sdk.net.internal.DefaultCoreSetting;
import com.iohao.game.sdk.net.internal.DefaultNetServer;
import com.iohao.game.sdk.net.internal.DefaultNetServerHook;
import com.iohao.game.sdk.net.internal.config.ClientUserConfigs;
import com.iohao.game.sdk.net.message.wrapper.LongValue;
import com.iohao.game.common.kit.concurrent.TaskKit;
import javafx.application.Platform;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.jctools.maps.NonBlockingHashMap;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;

import static com.almasb.fxgl.dsl.FXGL.*;

/**
 * @author 渔民小镇
 * @date 2023-11-24
 */
@Slf4j
@UtilityClass
public class MyKit {
    final Map<Long, FightPlayer> entityMap = new NonBlockingHashMap<>();
    public final FightPlayer myFightPlayer = new FightPlayer();
    public long playerId;
    public AtomicBoolean joinRoom = new AtomicBoolean();

    public class FightPlayer {
        public long playerId;
        public PlayerInfo playerInfo;
        public Entity player;
        public PlayerMoveComponent playerMoveComponent;

        void setting(FightPlayer fightPlayer) {
            this.playerId = fightPlayer.playerId;
            this.player = fightPlayer.player;
            this.playerMoveComponent = fightPlayer.playerMoveComponent;
        }
    }

    private FightPlayer createPlayer(PlayerInfo playerInfo) {
        long playerId = playerInfo.userId;

        if (MyKit.entityMap.containsKey(playerId)) {
            return null;
        }

        SpawnData spawnData = new SpawnData(playerInfo.x, playerInfo.y);
        spawnData.put("id", playerId).put("name", playerInfo.name);

        var player = spawn("Player", spawnData);
        FightPlayer fightPlayer = new FightPlayer();
        fightPlayer.playerId = playerId;
        fightPlayer.playerInfo = playerInfo;
        fightPlayer.player = player;
        fightPlayer.playerMoveComponent = player.getComponent(PlayerMoveComponent.class);

        MyKit.entityMap.put(fightPlayer.playerId, fightPlayer);

        return fightPlayer;
    }

    private void listenerNet() {
        ListenCommand.of(CmdKit.merge(MyCmd.cmd, MyCmd.enterRoom)).setTitle("joinRoom").setCallback(result -> {

            List<PlayerInfo> playerInfoList = result.listValue(PlayerInfo.class);

            playerInfoList.forEach(playerInfo -> {
                // 有新玩家加入房间
                FightPlayer fightPlayer = createPlayer(playerInfo);

                if (playerInfo.userId == MyKit.myFightPlayer.playerInfo.userId && fightPlayer != null) {
                    MyKit.myFightPlayer.setting(fightPlayer);
                    PlayerMoveComponent playerMoveComponent = fightPlayer.playerMoveComponent;
                    playerMoveComponent.setMoveChangeListener(MyKit::move);
                }

            });
        }).listen();

        ListenCommand.of(CmdKit.merge(MyCmd.cmd, MyCmd.move)).setTitle("move").setCallback(result -> {
            var playerMove = result.getValue(PlayerMove.class);
            // 找到对应的玩家
            long playerId = playerMove.playerId;

            if (playerId == MyKit.playerId) {
                return;
            }

            Dir dir = Dir.values()[playerMove.dir];

            FightPlayer fightPlayer = entityMap.get(playerId);
            PlayerMoveComponent playerMoveComponent = fightPlayer.playerMoveComponent;

            switch (dir) {
                case LEFT -> playerMoveComponent.left();
                case RIGHT -> playerMoveComponent.right();
                case UP -> playerMoveComponent.up();
                case DOWN -> playerMoveComponent.down();
            }

        }).listen();

        ListenCommand.of(CmdKit.merge(MyCmd.cmd, MyCmd.quitRoom)).setTitle("quitRoom").setCallback(result -> {
            var playerInfo = result.getValue(PlayerInfo.class);

            FightPlayer fightPlayer = entityMap.remove(playerInfo.userId);
            if (Objects.nonNull(fightPlayer)) {
                fightPlayer.player.removeFromWorld();
            }

        }).listen();

    }

    PlayerMove[] moves = new PlayerMove[4];

    private void initMove(Dir dir) {
        PlayerMove playerMove = new PlayerMove();
        playerMove.dir = dir.getValue();
        moves[dir.getValue()] = playerMove;
    }

    public void startup() {
        // 关闭网络请求日志
        ClientUserConfigs.closeLog();

        initMove(Dir.UP);
        initMove(Dir.DOWN);
        initMove(Dir.LEFT);
        initMove(Dir.RIGHT);

        // 设置
        DefaultCoreSetting setting = new DefaultCoreSetting()
                // 设置连接方式，默认提供了 WEBSOCKET 、TCP 相关实现
                .setJoinEnum(NetJoinEnum.WEBSOCKET)
                /*
                 * 向服务器发送心跳，心跳周期（秒）。
                 * 0 表示不开启心跳。当不为 0 时，会向服务器发送心跳消息。
                 */
                .setIdlePeriod(0)
                // 远程服务器主机名或 IP 地址
                .setHost("127.0.0.1")
                // 远程服务器端口号
                .setPort(10100)
                // 钩子接口
                .setNetServerHook(new DefaultNetServerHook() {
                    @Override
                    public void success(NetCoreSetting coreSetting) {
                        MyKit.listenerNet();
                        MyKit.login();
                    }
                });

        // 设置处理服务器所响应的消息时所使用的线程
        setting.setAcceptMessageRunLater(Platform::runLater);

        setting.setErrorCallback((message, requestCommand) -> {
            CommandResult commandResult = new DefaultCommandResult();
            commandResult.setNetMessage(message);

            if (Objects.isNull(requestCommand) || Objects.isNull(requestCommand.getErrorDelegate())) {
                FXGL.getNotificationService().pushNotification(commandResult.getErrorInfo());
                return;
            }

            requestCommand.getErrorDelegate().onError(commandResult);
        });

        TaskKit.execute(() -> new DefaultNetServer(setting).startup());
    }


    private void login() {

        int cmdMerge = CmdKit.merge(MyCmd.cmd, MyCmd.login);
        RequestCommand.of(cmdMerge).setTitle("login").setRequestData(() -> {
            // user id
            return LongValue.of(System.currentTimeMillis());
        }).setCallback(result -> {
            PlayerInfo playerInfo = result.getValue(PlayerInfo.class);
            MyKit.myFightPlayer.playerInfo = playerInfo;
            MyKit.myFightPlayer.playerId = playerInfo.userId;
            MyKit.playerId = playerInfo.userId;
            playerInfo.x = getAppWidth() / 2.0;
            playerInfo.y = getAppHeight() / 2.0;

            log.info("playerInfo : {}", MyKit.myFightPlayer.playerInfo);
        }).execute();
    }

    public void move(Dir dir) {
        int cmdMerge = CmdKit.merge(MyCmd.cmd, MyCmd.move);
        RequestCommand.of(cmdMerge).setTitle("move").setRequestData(() -> {
            // player move
            Entity player = MyKit.myFightPlayer.player;
            PlayerMove playerMove = moves[dir.getValue()];
            playerMove.x = player.getX();
            playerMove.y = player.getY();

            return playerMove;
        }).execute();
    }
}
