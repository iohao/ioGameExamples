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
 * demo 模块信息，逻辑服的 id
 * <pre>
 *     逻辑服的模块 id 放在一个文件管理，可以使得逻辑服的模块更加的清晰
 * </pre>
 *
 * @author 渔民小镇
 * @date 2022-03-24
 */
public interface DemoLogicServerModuleId {
    /** 示例 - 快速从零编写服务器完整示例 - https://www.yuque.com/iohao/game/zm6qg2 */
    int moduleIdDemoLogicServer = 1;
    /** 示例 - spring 集成 - https://www.yuque.com/iohao/game/evkgnz */
    int moduleIdDemoSpringLogicServer = 2;
    /** 示例 - 两个逻辑服相互交互 （战斗服） - https://www.yuque.com/iohao/game/anguu6 */
    int moduleIdDemoFightLogicServer = 3;
    /** 示例 - 两个逻辑服相互交互 （天气预报服） - https://www.yuque.com/iohao/game/anguu6 */
    int moduleIdDemoWeatherLogicServer = 4;
}
