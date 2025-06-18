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

import com.iohao.example.room.data.MyPlayer;
import com.iohao.example.room.data.MyRoom;
import com.iohao.example.room.data.MyRoomGameStatus;
import com.iohao.game.widget.light.room.Player;
import com.iohao.game.widget.light.room.operation.OperationHandler;
import com.iohao.game.widget.light.room.operation.PlayerOperationContext;
import lombok.extern.slf4j.Slf4j;

import java.util.Collection;

/**
 * @author 渔民小镇
 * @date 2025-06-02
 * @since 21.28
 */
@Slf4j
public final class StartGameOperationHandler implements OperationHandler {
    @Override
    public boolean processVerify(PlayerOperationContext context) {
        MyRoom room = context.getRoom();

        var gameStatus = room.getGameStatus();

        if (gameStatus != MyRoomGameStatus.NONE) {
            return false;
        }

        Collection<MyPlayer> players = room.listPlayer();

        if (players.size() != room.getSpaceSize()) {
            return false;
        }

        return players.stream().allMatch(Player::isReady);
    }

    @Override
    public void process(PlayerOperationContext context) {
        log.info("start game");

        MyRoom room = context.getRoom();
        room.changeGameStatus(MyRoomGameStatus.START);
    }
}