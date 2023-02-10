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
 * 大厅模块
 *
 * @author 渔民小镇
 * @date 2022-08-12
 */
public interface HallCmdModule {
    int cmd = SpringCmdModule.hallCmd;
    /** 登录 */
    int loginVerify = 1;
    /** 元信息 */
    int attachment = 2;
    /** 打印元信息 */
    int attachmentPrint = 3;
    int intPb = 4;
    int intPb1 = 5;
    int intListPb1 = 6;
    int defaultValue = 7;

    int booleanPb = 8;
    int booleanPb1 = 9;
    int booleanListPb1 = 10;
}
