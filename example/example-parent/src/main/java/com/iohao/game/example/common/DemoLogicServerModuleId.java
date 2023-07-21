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
