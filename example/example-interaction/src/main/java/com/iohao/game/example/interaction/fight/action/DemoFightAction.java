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
package com.iohao.game.example.interaction.fight.action;

import com.iohao.game.action.skeleton.annotation.ActionController;
import com.iohao.game.action.skeleton.annotation.ActionMethod;
import com.iohao.game.action.skeleton.core.CmdInfo;
import com.iohao.game.action.skeleton.core.flow.FlowContext;
import com.iohao.game.action.skeleton.protocol.ResponseMessage;
import com.iohao.game.example.interaction.msg.DemoFightMsg;
import com.iohao.game.example.interaction.msg.DemoWeatherMsg;
import com.iohao.game.example.interaction.msg.MatchMsg;
import com.iohao.game.example.interaction.weather.action.DemoCmdForWeather;
import lombok.extern.slf4j.Slf4j;

/**
 * 战斗 action
 *
 * @author 渔民小镇
 * @date 2022-03-24
 */
@Slf4j
@ActionController(DemoCmdForFight.cmd)
public class DemoFightAction {

    /**
     * 战斗
     *
     * @return current demoFightReq
     */
    @ActionMethod(DemoCmdForFight.fight)
    public DemoFightMsg fight(FlowContext flowContext) {
        /*
         * 单个逻辑服与单个逻辑服通信请求 - 有返回值（可跨进程）
         * https://www.yuque.com/iohao/game/nelwuz#L9TAJ
         */

        // 路由：这个路由是将要访问逻辑服的路由（表示你将要去的地方）
        CmdInfo todayWeatherCmd = CmdInfo.of(DemoCmdForWeather.cmd, DemoCmdForWeather.todayWeather);
        // 根据路由信息来请求其他子服务器（其他逻辑服）的数据
        ResponseMessage responseMessage = flowContext.invokeModuleMessage(todayWeatherCmd);
        DemoWeatherMsg demoWeatherMsg = responseMessage.getData(DemoWeatherMsg.class);

        DemoFightMsg demoFightMsg = new DemoFightMsg();
        // 把天气预报逻辑服的数据 增强给自己
        demoFightMsg.attack = demoWeatherMsg.attack;

        // 加一点点描述，以 30 为描述的临界点
        int criticalPoint = 30;
        if (demoFightMsg.attack > criticalPoint) {
            demoFightMsg.description = "英雄攻击得到了 极大的增强";
        } else {
            demoFightMsg.description = "英雄攻击得到了 小部分加成";
        }

        return demoFightMsg;
    }

    @ActionMethod(DemoCmdForFight.testMatch)
    public void testMatch(FlowContext flowContext) {
        /*
         * 单个逻辑服与单个逻辑服通信请求 - 无返回值（可跨进程）
         * https://www.yuque.com/iohao/game/nelwuz#gtdrv
         * 这个示例
         * 1 由玩家发起匹配请求
         * 2 匹配完成后交给房间逻辑服来处理
         * 当然，这里不会真的用匹配，只是一个模拟
         */

        MatchMsg matchMsg = new MatchMsg();
        matchMsg.description = "hello invokeModuleVoidMessage";

        CmdInfo createRoomCmd = CmdInfo.of(DemoCmdForWeather.cmd, DemoCmdForWeather.createRoom);

        // 路由、业务数据
        flowContext.invokeModuleVoidMessage(createRoomCmd, matchMsg);
    }

    @ActionMethod(DemoCmdForFight.async)
    public DemoFightMsg async(FlowContext flowContext) {
        // 路由：这个路由是将要访问逻辑服的路由（表示你将要去的地方）
        CmdInfo todayWeatherCmd = CmdInfo.of(DemoCmdForWeather.cmd, DemoCmdForWeather.todayWeather);
        flowContext.invokeModuleMessageAsync(todayWeatherCmd, responseMessage -> {
            DemoWeatherMsg demoWeatherMsg = responseMessage.getData(DemoWeatherMsg.class);
            // 回调写法
            log.info("demoWeatherMsg : {}", demoWeatherMsg);
        });

        DemoFightMsg demoFightMsg = new DemoFightMsg();
        // 把天气预报逻辑服的数据 增强给自己
        demoFightMsg.attack = 1;
        // 加一点点描述，以 30 为描述的临界点
        demoFightMsg.description = "英雄攻击得到了 小部分加成";

        return demoFightMsg;
    }
}
