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
package com.iohao.demo.game.server;

import com.iohao.demo.game.common.MyCmd;
import com.iohao.demo.game.common.PlayerInfo;
import com.iohao.demo.game.common.PlayerMove;
import com.iohao.game.action.skeleton.annotation.ActionController;
import com.iohao.game.action.skeleton.annotation.ActionMethod;
import com.iohao.game.action.skeleton.core.CmdInfo;
import com.iohao.game.action.skeleton.core.exception.ActionErrorEnum;
import com.iohao.game.action.skeleton.core.flow.FlowContext;
import com.iohao.game.action.skeleton.protocol.wrapper.ByteValueList;
import com.iohao.game.bolt.broker.core.client.BrokerClientHelper;
import com.iohao.game.widget.light.room.Room;
import com.iohao.game.widget.light.room.RoomService;
import com.iohao.game.widget.light.room.SimplePlayer;
import com.iohao.game.widget.light.room.SimpleRoom;
import lombok.extern.slf4j.Slf4j;
import net.datafaker.Faker;

import java.util.List;
import java.util.Locale;

/**
 * @author 渔民小镇
 * @date 2023-11-23
 */
@Slf4j
@ActionController(MyCmd.cmd)
public class GameAction {

    RoomService roomService = RoomService.of();
    static Faker faker = new Faker(Locale.CHINA);

    @ActionMethod(MyCmd.login)
    public PlayerInfo login(long userId, FlowContext flowContext) {
        flowContext.bindingUserId(userId);

        PlayerInfo playerInfo = new PlayerInfo();
        playerInfo.userId = userId;
        playerInfo.name = faker.name().fullName();

        return playerInfo;
    }

    @ActionMethod(MyCmd.enterRoom)
    public void enterRoom(PlayerInfo playerInfo, FlowContext flowContext) {
        ActionErrorEnum.dataNotExist.assertNonNull(playerInfo.name);

        long userId = flowContext.getUserId();

        Room room = getRoom();
        // 如果玩家不存在，就将玩家添加到房间里
        room.ifPlayerNotExist(userId, () -> {
            MyPlayer player = new MyPlayer();
            player.setUserId(userId);
            player.playerInfo = playerInfo;

            // 将玩家加入房间
            roomService.addPlayer(room, player);
        });

        // 新玩家加入房间，给房间内的其他玩家广播
        List<PlayerInfo> playerInfoList = room.streamPlayer()
                .map(p -> ((MyPlayer) p).playerInfo)
                .toList();

        room.ofRangeBroadcast()
                .setResponseMessage(CmdInfo.of(MyCmd.cmd, MyCmd.enterRoom), ByteValueList.ofList(playerInfoList))
                .execute();
    }

    @ActionMethod(MyCmd.quitRoom)
    public void quitRoom(FlowContext flowContext) {
        long userId = flowContext.getUserId();

        Room room = getRoom();

        room.ifPlayerExist(userId, (MyPlayer player) -> {

            this.roomService.removePlayer(room, player);

            // 有玩家退出房间，给房间内的其他玩家广播
            room.ofRangeBroadcast()
                    .setResponseMessage(CmdInfo.of(MyCmd.cmd, MyCmd.quitRoom), player.playerInfo)
                    .execute();
        });
    }

    @ActionMethod(MyCmd.move)
    public void move(PlayerMove playerMove, FlowContext flowContext) {
        long userId = flowContext.getUserId();
        Room room = getRoom();

        room.ifPlayerExist(userId, (MyPlayer player) -> {

            PlayerInfo playerInfo = player.playerInfo;
            playerInfo.x = playerMove.x;
            playerInfo.y = playerMove.y;

            playerMove.playerId = userId;

            // 玩家移动，给房间内的其他玩家广播
            CmdInfo cmdInfo = CmdInfo.of(MyCmd.cmd, MyCmd.move);
            room.ofRangeBroadcast()
                    .setResponseMessage(cmdInfo, playerMove)
                    .execute();
        });
    }

    Room getRoom() {
        long roomId = 1L;

        return roomService.optionalRoom(roomId).orElseGet(() -> {
            var aggregationContext = BrokerClientHelper
                    .getBrokerClient()
                    .getCommunicationAggregationContext();

            Room room = new SimpleRoom();
            room.setRoomId(roomId);
            // 设置通讯上下文
            room.setAggregationContext(aggregationContext);

            roomService.addRoom(room);

            return room;
        });
    }

    static class MyPlayer extends SimplePlayer {
        PlayerInfo playerInfo;
    }
}