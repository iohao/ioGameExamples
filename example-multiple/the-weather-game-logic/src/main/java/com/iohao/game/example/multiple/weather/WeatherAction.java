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
package com.iohao.game.example.multiple.weather;

import com.iohao.game.action.skeleton.annotation.ActionController;
import com.iohao.game.action.skeleton.annotation.ActionMethod;
import com.iohao.game.example.multiple.common.cmd.internal.WeatherCmd;
import com.iohao.game.example.multiple.common.data.Weather;

/**
 * @author 渔民小镇
 * @date 2023-03-30
 */
@ActionController(WeatherCmd.cmd)
public class WeatherAction {
    @ActionMethod(WeatherCmd.hello)
    public Weather hello(Weather weather) {
        Weather newWeather = new Weather();
        newWeather.id = weather.name.hashCode();
        newWeather.name = weather.name + ", I'm here ";
        return newWeather;
    }
}
