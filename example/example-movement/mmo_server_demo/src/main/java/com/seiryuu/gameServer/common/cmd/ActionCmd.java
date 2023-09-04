/*
 * # iohao.com . 渔民小镇
 * Copyright (C) 2021 - 2022 double joker （262610965@qq.com） . All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.seiryuu.gameServer.common.cmd;

import com.seiryuu.gameServer.common.MmoModuleCmd;

/**
 * @author Seiryuu
 * @date 2022-03-23
 */
public interface ActionCmd {
    /**
     * 主模块
     */
    int cmd = MmoModuleCmd.player_action;

    /**
     * 进入游戏
     */
    int enterGame = 0;

    /**
     * 离开游戏
     */
    int leaveGame = 1;

    /**
     * 进入地图
     */
    int enterMap = 2;

    /**
     * 离开地图
     */
    int leaveMap = 3;

    /**
     * 移动
     */
    int move = 4;

    /**
     * 同步玩家
     */
    int syncPlayer = 5;
}
