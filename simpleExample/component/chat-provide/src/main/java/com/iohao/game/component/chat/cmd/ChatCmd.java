/*
 * ioGame
 * Copyright (C) 2021 - present  渔民小镇 （221095@qq.com、luoyizhu@gmail.com） . All Rights Reserved.
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
package com.iohao.game.component.chat.cmd;


import com.iohao.game.action.skeleton.core.CmdInfo;

/**
 * 1 ~ 30
 * @author 渔民小镇
 * @date 2023-0-28
 */
public interface ChatCmd {
    int cmd = 125;
    /** 玩家与玩家的私聊 */
    int c_2_c = 1;
    /** 未读消息的发送者列表 */
    int listUnreadUserId = 2;
    /** 读取某个玩家的私有消息 */
    int readPrivateMessage = 3;

    /** 私聊消息通知 */
    int notifyPrivate = 11;

    static CmdInfo of(int subCmd) {
        return CmdInfo.of(cmd, subCmd);
    }
}
