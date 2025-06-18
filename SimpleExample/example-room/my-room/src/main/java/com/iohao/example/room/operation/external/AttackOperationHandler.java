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
package com.iohao.example.room.operation.external;

import com.iohao.example.room.data.MyBroadcastKit;
import com.iohao.example.room.data.MyPlayer;
import com.iohao.example.room.message.MyOperationCommandMessage;
import com.iohao.game.widget.light.room.Room;
import com.iohao.game.widget.light.room.operation.OperationHandler;
import com.iohao.game.widget.light.room.operation.PlayerOperationContext;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author 渔民小镇
 * @date 2025-06-03
 * @since 21.28
 */
@Slf4j
public final class AttackOperationHandler implements OperationHandler {
    AtomicInteger counter = new AtomicInteger(0);

    @Override
    public void process(PlayerOperationContext context) {
        long userId = context.getUserId();
        log.info("userId : {} attack!", userId);

        MyOperationCommandMessage message = context.getCommand();
        MyPlayer player = context.getPlayer();
        message.data = "[%s:%s] attack(%s)!"
                .formatted(player.getUserId(), player.getNickname(), counter.incrementAndGet());

        Room room = context.getRoom();
        MyBroadcastKit.broadcastOperation(message, room);
    }
}