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
package com.iohao.example.room;

import com.iohao.example.room.data.InternalOperationEnum;
import com.iohao.example.room.message.MyOperationEnum;
import com.iohao.example.room.operation.EnterRoomOperationHandler;
import com.iohao.example.room.operation.QuitRoomOperationHandler;
import com.iohao.example.room.operation.ReadyOperationHandler;
import com.iohao.example.room.operation.StartGameOperationHandler;
import com.iohao.example.room.operation.external.AttackOperationHandler;
import com.iohao.example.room.operation.external.DefenseOperationHandler;
import com.iohao.game.action.skeleton.core.BarSkeleton;
import com.iohao.game.action.skeleton.core.runner.Runner;

/**
 * @author 渔民小镇
 * @date 2025-06-03
 * @since 21.28
 */
public final class MyOperationConfigRunner implements Runner {
    @Override
    public void onStart(BarSkeleton skeleton) {
        var factory = MyRoomService.me().getOperationFactory();

        // ------ mapping internal operation ------
        factory.mapping(InternalOperationEnum.enterRoom, new EnterRoomOperationHandler());
        factory.mapping(InternalOperationEnum.ready, new ReadyOperationHandler());
        factory.mapping(InternalOperationEnum.quitRoom, new QuitRoomOperationHandler());
        factory.mapping(InternalOperationEnum.startGame, new StartGameOperationHandler());

        // ------ mapping user operation ------
        factory.mappingUser(MyOperationEnum.attack, new AttackOperationHandler());
        factory.mappingUser(MyOperationEnum.defense, new DefenseOperationHandler());
    }
}
