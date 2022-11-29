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
package com.iohao.game.example.interaction.part;

import com.iohao.game.bolt.broker.client.BrokerClientApplication;
import com.iohao.game.example.interaction.weather.DemoWeatherLogicServer;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

/**
 * @author 渔民小镇
 * @date 2022-11-05
 */
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DemoInteractionWeatherClient {
    public static void main(String[] args) {
        DemoWeatherLogicServer demoWeatherLogicServer = new DemoWeatherLogicServer();
        BrokerClientApplication.start(demoWeatherLogicServer);
    }
}