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
    int intPb2intPb = 11;

    int int2intList = 12;
    int int2intListPb = 13;

    int intListPb2intList = 14;
    int intList2intListPb = 15;


    int long2long = 20;
    int longPb2longPb = 21;

    int long2longList = 22;
    int long2longListPb = 23;

    int longListPb2longList = 24;
    int longList2longListPb = 25;
}
