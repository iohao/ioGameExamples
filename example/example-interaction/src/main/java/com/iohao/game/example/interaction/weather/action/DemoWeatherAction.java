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
package com.iohao.game.example.interaction.weather.action;

import com.iohao.game.action.skeleton.core.CmdInfo;
import com.iohao.game.action.skeleton.core.commumication.BroadcastContext;
import com.iohao.game.bolt.broker.core.client.BrokerClientHelper;
import com.iohao.game.example.interaction.msg.DemoWeatherMsg;
import com.iohao.game.action.skeleton.annotation.ActionController;
import com.iohao.game.action.skeleton.annotation.ActionMethod;
import com.iohao.game.example.interaction.msg.MatchMsg;

import java.util.Calendar;

/**
 * 天气 action
 *
 * @author 渔民小镇
 * @date 2022-03-24
 */
@ActionController(DemoCmdForWeather.cmd)
public class DemoWeatherAction {
    /**
     * 今天天气情况
     *
     * @return 天气响应
     */
    @ActionMethod(DemoCmdForWeather.todayWeather)
    public DemoWeatherMsg todayWeather() {
        // 当前时间分钟
        int minute = Calendar.getInstance().get(Calendar.MINUTE);

        DemoWeatherMsg demoWeatherMsg = new DemoWeatherMsg();
        // 根据当前时间来增加战斗力
        demoWeatherMsg.attack = minute;
        return demoWeatherMsg;
    }

    @ActionMethod(DemoCmdForWeather.createRoom)
    public void createRoom(MatchMsg matchMsg) {
        CmdInfo createRoomCmd = CmdInfo.getCmdInfo(DemoCmdForWeather.cmd, DemoCmdForWeather.createRoom);

        BroadcastContext broadcastContext = BrokerClientHelper.me().getBroadcastContext();
        // 全服广播：路由、业务数据
        broadcastContext.broadcast(createRoomCmd, matchMsg);
    }
}
