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
package com.iohao.game.example.common;

/**
 * 逻辑服 主 cmd
 * <pre>
 *     主 cmd 放在一个文件管理，可以使得模块的主 cmd 更加的清晰
 * </pre>
 *
 * @author 渔民小镇
 * @date 2022-03-23
 */
public interface DemoModuleCmd {
    /** 逻辑服 示例 cmd 模块1 */
    int demoModule_1_cmd = 1;

    /** 逻辑服 示例 cmd 模块2 (spring 相关模块) */
    int demoModule_2_spring_cmd = 2;

    /** 逻辑服 示例 cmd 模块3 (战斗服 相关模块) */
    int demoModule_3_fight_cmd = 3;

    /** 逻辑服 示例 cmd 模块4 (天气预报服 相关模块) */
    int demoModule_4_weather_cmd = 4;

    int demoModule_5_boot_cmd = 5;

    /** 逻辑服 示例 cmd 模块6 */
    int demoModule_6_cmd = 6;

    /** 逻辑服 示例 cmd 模块7 - 广播示例 */
    int demoModule_7_cmd = 7;

    /** 逻辑服 示例 cmd 模块8 (大厅 相关模块) */
    int demoModule_8_hall_cmd = 8;
    /** 逻辑服 示例 cmd 模块9 (房间 相关模块) */
    int demoModule_9_room_cmd = 9;
    /** 逻辑服 示例 cmd 模块10 （动态服务器绑定 - 房间） */
    int demoModule_10_endpoint_room_cmd = 10;
    /** 逻辑服 示例 cmd 模块11 (动态服务器绑定 - 匹配) */
    int demoModule_11_endpoint_match_cmd = 11;
    /** 逻辑服 示例 cmd 模块12 （钩子使用） */
    int demoModule_12_hook_cmd = 12;
    int demoModule_13_ext_cmd = 13;
    /** 基础类型包装类 */
    int demoModule_14_wrapper_cmd = 14;
}
