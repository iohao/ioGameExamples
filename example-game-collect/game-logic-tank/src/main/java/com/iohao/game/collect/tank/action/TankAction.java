/*
 * # iohao.com . 渔民小镇
 * Copyright (C) 2021 - 2022 double joker （262610965@qq.com） . All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License..
 */
package com.iohao.game.collect.tank.action;

import com.iohao.game.action.skeleton.annotation.ActionController;
import com.iohao.game.action.skeleton.annotation.ActionMethod;
import com.iohao.game.action.skeleton.core.flow.FlowContext;
import com.iohao.game.collect.proto.tank.TankBullet;
import com.iohao.game.collect.proto.tank.TankEnterRoom;
import com.iohao.game.collect.proto.tank.TankLocation;
import com.iohao.game.collect.tank.TankCmd;
import com.iohao.game.collect.tank.mapstruct.TankMapstruct;
import com.iohao.game.collect.tank.room.TankPlayerEntity;
import com.iohao.game.collect.tank.room.TankRoomEntity;
import com.iohao.game.collect.tank.room.flow.*;
import com.iohao.game.widget.light.room.GameFlow;
import com.iohao.game.widget.light.room.RoomService;
import lombok.extern.slf4j.Slf4j;

import java.util.Collection;
import java.util.Objects;
import java.util.concurrent.atomic.LongAdder;

/**
 * 坦克相关
 *
 * @author 渔民小镇
 * @date 2022-01-14
 */
@Slf4j
@ActionController(TankCmd.cmd)
public class TankAction {
    /** 游戏流程 */
    static GameFlow gameFlow = GameFlow.me();
    RoomService roomService = RoomService.me();
    /** 开发阶段，只用一个房间 */
    public static long tempRoomId = 10000;
    LongAdder shootAdder = new LongAdder();

    static {
        gameFlow
                // 游戏规则
                .setRoomRuleInfoCustom(new TankRoomRuleInfoCustom())
                // 游戏开始
                .setRoomGameStartCustom(new TankRoomGameStartCustom())
                // 创建玩家
                .setRoomPlayerCreateCustom(new TankRoomPlayerCreateCustom())
                // 房间创建
                .setRoomCreateCustom(new TankRoomCreateCustom())
                // 进入房间
                .setRoomEnterCustom(new TankRoomEnterCustom());

        // TankRoomBroadcast.me().start();
    }


    /**
     * 坦克射击(发射子弹)
     *
     * @param flowContext flowContext
     * @param tankBullet  tankBullet
     */
    @ActionMethod(TankCmd.shooting)
    public void shooting(FlowContext flowContext, TankBullet tankBullet) {

        shootAdder.increment();
        log.info("------------ shootAdder : {} ", shootAdder);

        long userId = flowContext.getUserId();

        // 广播这颗子弹的消息
        TankRoomEntity room = roomService.getRoomByUserId(userId);

        // 广播业务数据给房间内的所有玩家
        room.broadcast(flowContext, tankBullet);
    }

    /**
     * 测试
     *
     * @param flowContext flowContext
     * @param tankBullet  tankBullet
     */
    @ActionMethod(TankCmd.testShooting)
    public void testShooting(FlowContext flowContext, TankBullet tankBullet) {
        shootAdder.increment();
        log.info("------------ shootAdder : {} ", shootAdder);
    }

    /**
     * 坦克移动
     *
     * @param flowContext  flowContext
     * @param tankLocation tankLocation
     */
    @ActionMethod(TankCmd.tankMove)
    public void tankMove(FlowContext flowContext, TankLocation tankLocation) {
        long userId = flowContext.getUserId();
        tankLocation.playerId = userId;

        TankRoomEntity room = roomService.getRoomByUserId(userId);

        TankPlayerEntity player = room.getPlayerById(userId);

        player.setTankLocation(tankLocation);

        //  广播坦克移动
        room.broadcast(flowContext, tankLocation);
    }

    /**
     * 玩家进入房间
     *
     * @param flowContext flowContext
     * @param enterRoom   enterRoom
     * @return TankEnterRoom
     */
    @ActionMethod(TankCmd.enterRoom)
    public TankEnterRoom enterRoom(FlowContext flowContext, TankEnterRoom enterRoom) {

        // TODO: 2022/1/14 开发阶段，只用一个房间
        enterRoom.roomId = tempRoomId;

        long roomId = enterRoom.roomId;

        // 房间
        TankRoomEntity room = roomService.getRoom(roomId);

        // 房间不存在，创建一个房间
        if (Objects.isNull(room)) {
            room = gameFlow.createRoom(null);
            // TODO: 2022/1/14 开发阶段，只用一个房间
            room.setRoomId(tempRoomId);

            roomService.addRoom(room);
        }

        long userId = flowContext.getUserId();
        TankPlayerEntity player = room.getPlayerById(userId);

        // 如果检查是否在房间内
        if (Objects.isNull(player)) {
            // 如果不在房间内先加入房间
            player = gameFlow.createPlayer();
            player.setId(userId);
            player.setRoomId(roomId);

            roomService.addPlayer(room, player);
        }

        // 进入房间
        Collection<TankPlayerEntity> players = room.listPlayer();
        enterRoom.tankPlayerList = TankMapstruct.ME.convertList(players);

        return enterRoom;
    }

}
