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
package com.iohao.game.example.interaction.weather.action;

import com.iohao.game.example.common.DemoModuleCmd;

/**
 * 天气预报服的 cmd
 *
 * @author 渔民小镇
 * @date 2022-03-24
 */
public interface DemoCmdForWeather {

    /** 模块 - 主 cmd : 4 */
    int cmd = DemoModuleCmd.demoModule_4_weather_cmd;

    /** 示例 今天天气 方法 */
    int todayWeather = 0;
    int createRoom = 1;
}
