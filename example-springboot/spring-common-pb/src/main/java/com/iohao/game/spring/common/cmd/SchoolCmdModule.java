/*
 * # iohao.com . 渔民小镇
 * Copyright (C) 2021 - present double joker （262610965@qq.com） . All Rights Reserved.
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
package com.iohao.game.spring.common.cmd;

/**
 * 学校模块
 *
 * @author 渔民小镇
 * @date 2022-08-12
 */
public interface SchoolCmdModule {
    int cmd = SpringCmdModule.schoolCmd;

    /** 请求、响应 */
    int here = 1;
    /** 请求、无响应 */
    int hereVoid = 2;

    /** JSR380 */
    int jsr380 = 3;

    /** 断言 + 异常机制 = 清晰简洁的代码 */
    int assertWithException = 4;
    /** 广播相关 */
    int broadcast = 5;

    /** 广播业务数据 */
    int broadcastData = 6;

    /** 3.1 单个逻辑服与单个逻辑服通信请求 - 有返回值（可跨进程） */
    int communication31 = 7;
    /** 3.2 单个逻辑服与单个逻辑服通信请求 - 无返回值（可跨进程） */
    int communication32 = 8;
    /** 3.3 单个逻辑服与同类型多个逻辑服通信请求（可跨进程） - 统计房间 */
    int communication33 = 9;
    /** 业务参数自动装箱、拆箱基础类型 */
    int intValueWrapper = 10;

    /** 分组校验 */
    int group = 11;
    int here2 = 12;
}
