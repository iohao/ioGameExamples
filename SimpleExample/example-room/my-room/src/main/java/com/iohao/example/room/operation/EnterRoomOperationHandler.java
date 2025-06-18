/*
 * ioGame
 * Copyright (C) 2021 - present  渔民小镇 （262610965@qq.com、luoyizhu@gmail.com） . All Rights Reserved.
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
package com.iohao.example.room.operation;

import com.iohao.example.room.MyRoomService;
import com.iohao.example.room.data.*;
import com.iohao.game.action.skeleton.core.flow.FlowContext;
import com.iohao.game.widget.light.room.flow.GameFlowContext;
import com.iohao.game.widget.light.room.operation.OperationHandler;
import com.iohao.game.widget.light.room.operation.PlayerOperationContext;
import lombok.extern.slf4j.Slf4j;
import net.datafaker.Faker;
import net.datafaker.providers.base.Name;

import java.util.Locale;

/**
 * @author 渔民小镇
 * @date 2025-06-02
 * @since 21.28
 */
@Slf4j
public final class EnterRoomOperationHandler implements OperationHandler {
    final Name name = new Faker(Locale.getDefault()).name();
    final MyRoomService roomService = MyRoomService.me();

    @Override
    public boolean processVerify(PlayerOperationContext context) {
        MyRoom room = context.getRoom();
        GameCode.roomSpaceSizeNotEnough.assertTrue(room.hasSeat());
        return true;
    }

    @Override
    public void process(PlayerOperationContext context) {
        long userId = context.getUserId();
        log.info("enterRoom : {}", userId);

        MyRoom room = context.getRoom();
        room.ifPlayerNotExist(userId, () -> {
            var player = new MyPlayer();
            player.setUserId(userId);
            player.setNickname(name.fullName());

            roomService.addPlayer(room, player);
        });

        MyBroadcastKit.broadcastEnterRoom(room);
    }
}