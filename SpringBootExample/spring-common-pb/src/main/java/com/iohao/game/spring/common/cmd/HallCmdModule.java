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
    int intValue = 4;
    int intValue1 = 5;
    int intValueList = 6;
    int defaultValue = 7;

    int testEnum = 8;
    int testList = 9;
    int acceptList = 10;

    /** 元信息更新后跨服请求 */
    int issue301 = 11;
}
