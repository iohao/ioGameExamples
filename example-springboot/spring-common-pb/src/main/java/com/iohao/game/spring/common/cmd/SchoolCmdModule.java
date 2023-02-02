/*
 * # iohao.com . 渔民小镇
 * Copyright (C) 2021 - 2023 double joker （262610965@qq.com） . All Rights Reserved.
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
    int intPbWrapper = 10;

    /** 分组校验 */
    int group = 11;
}
