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
package com.iohao.example.room.client;

import com.alibaba.fastjson2.JSONObject;
import com.alibaba.fastjson2.JSONWriter;
import com.iohao.example.room.message.*;
import com.iohao.game.common.kit.RandomKit;
import com.iohao.game.common.kit.concurrent.TaskKit;
import com.iohao.game.external.client.AbstractInputCommandRegion;
import com.iohao.game.external.client.kit.ScannerKit;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.UUID;

/**
 * @author 渔民小镇
 * @date 2025-06-03
 * @since 21.28
 */
@Slf4j
public final class MyRoomInputCommandRegion extends AbstractInputCommandRegion {

    @Override
    public void initInputCommand() {
        this.inputCommandCreate.cmd = RoomCmd.cmd;

        extractedRequest();

        extractedBroadcast();

        TaskKit.runOnceSecond(() -> {
            // auto login
            ofRequestCommand(RoomCmd.login).execute();
        });
    }

    private void extractedBroadcast() {
        ofListen(result -> {
            ReadyMessage message = result.getValue(ReadyMessage.class);
            log.info("\nreceived ready message: {}", message);
        }, RoomCmd.readyBroadcast, "readyBroadcast");

        ofListen(result -> {
            long quitUserId = result.getLong();
            log.info("\nreceived quit userId: {}", quitUserId);
        }, RoomCmd.quitRoomBroadcast, "quitRoomBroadcast");

        ofListen(result -> {
            EnterRoomMessage message = result.getValue(EnterRoomMessage.class);
            var s = JSONObject.toJSONString(message, JSONWriter.Feature.PrettyFormat);
            log.info("\nreceived enterRoom message: {}", s);
        }, RoomCmd.enterRoomBroadcast, "enterRoomBroadcast");

        ofListen(result -> {
            MyOperationCommandMessage message = result.getValue(MyOperationCommandMessage.class);
            log.info("\nreceived MyOperationCommandMessage message: {}", message);
        }, RoomCmd.operationBroadcast, "operationBroadcast");
    }

    private void extractedRequest() {
        ofCommand(RoomCmd.login).setTitle("login").setRequestData(() -> {
            // jwt
            return UUID.randomUUID().toString();
        }).callback(result -> {
            log.info("\nlogin result: {}", result.getBoolean());
        });

        ofCommand(RoomCmd.createRoom).setTitle("createRoom");
        ofCommand(RoomCmd.quitRoom).setTitle("quitRoom");

        ofCommand(RoomCmd.enterRoom).setTitle("enterRoom").setRequestData(() -> {
            ScannerKit.log(() -> log.info("\nPlease enter the roomId"));
            return ScannerKit.nextLong(1);
        });

        ofCommand(RoomCmd.ready).setTitle("ready").setRequestData(() -> {
            // true or false
            return true;
        });

        ofCommand(RoomCmd.listRoomId).setTitle("listRoom").callback(result -> {
            List<Long> roomIdList = result.listLong();
            log.info("\nlistRoomId: {}", roomIdList);
        });


        ofCommand(RoomCmd.operation).setTitle("operation").setRequestData(() -> {
            MyOperationCommandMessage message = new MyOperationCommandMessage();

            if (RandomKit.randomBoolean()) {
                message.operation = MyOperationEnum.attack;
            } else {
                message.operation = MyOperationEnum.defense;
            }

            return message;
        });
    }
}