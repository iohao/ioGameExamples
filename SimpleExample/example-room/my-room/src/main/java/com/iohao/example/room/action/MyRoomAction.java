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
package com.iohao.example.room.action;

import com.iohao.example.room.MyRoomService;
import com.iohao.example.room.data.*;
import com.iohao.example.room.message.MyOperationCommandMessage;
import com.iohao.example.room.message.RoomCmd;
import com.iohao.game.action.skeleton.annotation.ActionController;
import com.iohao.game.action.skeleton.annotation.ActionMethod;
import com.iohao.game.action.skeleton.core.flow.FlowContext;
import com.iohao.game.widget.light.room.Room;
import com.iohao.game.widget.light.room.flow.RoomCreateContext;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author 渔民小镇
 * @date 2025-06-02
 * @since 21.28
 */
@Slf4j
@ActionController(RoomCmd.cmd)
public final class MyRoomAction {
    final MyRoomService roomService = MyRoomService.me();

    @ActionMethod(RoomCmd.login)
    public boolean login(String jwt, FlowContext flowContext) {
        int userId = Math.abs(jwt.hashCode());
        return flowContext.bindingUserId(userId);
    }

    @ActionMethod(RoomCmd.createRoom)
    public void createRoom(FlowContext flowContext) {

        long userId = flowContext.getUserId();
        Room roomByUserId = roomService.getRoomByUserId(userId);
        GameCode.illegalOperation.assertTrueThrows(Objects.nonNull(roomByUserId));

        RoomCreateContext roomCreateContext = RoomCreateContext.of(flowContext)
                .setSpaceSize(2);

        var room = roomService.createRoom(roomCreateContext);
        roomService.addRoom(room);

        room.operation(InternalOperationEnum.enterRoom, flowContext);
    }

    @ActionMethod(RoomCmd.enterRoom)
    public void enterRoom(long roomId, FlowContext flowContext) {
        long userId = flowContext.getUserId();
        var room = this.roomService.optionalRoomByUserId(userId)
                .orElseGet(() -> this.roomService.getRoom(roomId));

        GameCode.roomNotExist.assertNonNull(room);

        room.operation(InternalOperationEnum.enterRoom, flowContext);
    }

    @ActionMethod(RoomCmd.ready)
    public void ready(boolean ready, FlowContext flowContext) {
        long userId = flowContext.getUserId();
        var room = getRoomByUserId(userId);

        room.operation(InternalOperationEnum.ready, flowContext, ready);
    }

    @ActionMethod(RoomCmd.quitRoom)
    public void quitRoom(FlowContext flowContext) {
        long userId = flowContext.getUserId();
        var room = getRoomByUserId(userId);

        room.operation(InternalOperationEnum.quitRoom, flowContext);
    }

    @ActionMethod(RoomCmd.operation)
    public void operation(MyOperationCommandMessage command, FlowContext flowContext) {
        var operationHandler = roomService.getUserOperationHandler(command.operation);
        GameCode.illegalOperation.assertNonNull(operationHandler);

        long userId = flowContext.getUserId();
        Room room = getRoomByUserId(userId);
        room.operation(operationHandler, flowContext, command);
    }

    @ActionMethod(RoomCmd.listRoomId)
    public List<Long> listRoomId() {
        return roomService.listRoom().stream()
                .map(Room::getRoomId)
                .collect(Collectors.toList());
    }

    private Room getRoomByUserId(long userId) {
        Room room = this.roomService.getRoomByUserId(userId);
        GameCode.roomNotExist.assertNullThrows(room);
        return room;
    }
}