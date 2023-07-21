/*
 * # iohao.com . 渔民小镇
 * Copyright (C) 2021 - 2023 double joker （262610965@qq.com） . All Rights Reserved.
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
package com.iohao.game.example.wrapper.action;

import com.iohao.game.example.common.DemoModuleCmd;

/**
 * @author 渔民小镇
 * @date 2022-06-26
 */
public interface WrapperCmd {

    /** 模块 - 主 cmd : 6 */
    int cmd = DemoModuleCmd.demoModule_14_wrapper_cmd;

    int int2int = 10;
    int intValue2intValue = 11;

    int intList2intList = 12;
    int intValueList2intValueList = 13;

    int long2long = 20;
    int longValue2longValue = 21;

    int longList2longList = 22;
    int longValueList2longValueList = 23;

    int string2string = 30;
    int stringValue2stringValue = 31;

    int stringList2stringList = 32;
    int stringValueList2stringValueList = 33;

    int bool2bool = 40;
    int boolValue2boolValue = 41;

    int boolList2boolList = 42;
    int boolValueList2boolValueList = 43;

}
