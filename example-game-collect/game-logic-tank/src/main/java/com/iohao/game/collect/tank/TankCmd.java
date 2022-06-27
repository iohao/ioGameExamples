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
package com.iohao.game.collect.tank;

import com.iohao.game.collect.common.ActionModuleCmd;

/**
 * 游戏 - 坦克模块
 *
 * @author 渔民小镇
 * @date 2022-01-14
 */
public interface TankCmd extends ActionModuleCmd.Info {
    /** 模块 - 主 cmd : 2 */
    int cmd = ActionModuleCmd.tankModuleCmd;

    /** 创建房间 */
    int createRoom = 1;
    /** 进入房间 */
    int enterRoom = 2;
    /** 游戏开始 */
    int gameStart = 3;
    /** 坦克移动 */
    int tankMove = 5;
    /** 坦克射击(发射子弹) */
    int shooting = 6;
    int testShooting = 7;

    int testUserInfo = 11;
    int testBroadcasts = 12;
}