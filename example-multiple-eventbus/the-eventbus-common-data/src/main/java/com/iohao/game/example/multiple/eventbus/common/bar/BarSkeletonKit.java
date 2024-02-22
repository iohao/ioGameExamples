/*
 * ioGame
 * Copyright (C) 2021 - 2023  渔民小镇 （262610965@qq.com、luoyizhu@gmail.com） . All Rights Reserved.
 * # iohao.com . 渔民小镇
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
package com.iohao.game.example.multiple.eventbus.common.bar;

import com.iohao.game.action.skeleton.core.BarSkeleton;
import com.iohao.game.action.skeleton.core.BarSkeletonBuilder;
import com.iohao.game.action.skeleton.core.InOutManager;
import com.iohao.game.action.skeleton.core.flow.internal.DebugInOut;
import com.iohao.game.action.skeleton.core.flow.internal.StatActionInOut;
import com.iohao.game.action.skeleton.core.flow.internal.TraceIdInOut;
import com.iohao.game.action.skeleton.eventbus.EventBus;
import com.iohao.game.common.kit.concurrent.TaskKit;
import com.iohao.game.example.multiple.eventbus.common.event.ActionStatEventMessage;
import com.iohao.game.example.multiple.eventbus.common.event.ActionStatEventMessageRegion;
import lombok.experimental.UtilityClass;

import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @author 渔民小镇
 * @date 2023-12-24
 */
@UtilityClass
public class BarSkeletonKit {
    public void inOut(BarSkeletonBuilder builder) {
        // traceId
        TraceIdInOut traceIdInOut = new TraceIdInOut();
        // action 调用统计插件
        StatActionInOut statActionInOut = new StatActionInOut();

        builder
//                .addInOut(statActionInOut)
                .addInOut(new DebugInOut())
                .addInOut(traceIdInOut);
    }

    public void eventStatActionInOut(BarSkeleton skeleton, EventBus eventBus, String appId, String appName) {
        InOutManager inOutManager = skeleton.getInOutManager();

        inOutManager.getOptional(StatActionInOut.class).ifPresent(statActionInOut -> {
            // 任务调度
            TaskKit.runInterval(() -> {

                StatActionInOut.StatActionRegion statActionRegion = statActionInOut.getRegion();
                // 收集统计数据
                var messageList = statActionRegion.stream().map(statAction -> {
                    ActionStatEventMessage message = new ActionStatEventMessage();

                    message.setCmdMerge(statAction.getCmdInfo().getCmdMerge());
                    // action 执行次数统计
                    message.setExecuteCount(statAction.getExecuteCount().sum());
                    // action 异常触发次数
                    message.setErrorCount(statAction.getErrorCount().sum());

                    // 单次调用中的最大耗时
                    message.setMaxTime(statAction.getMaxTime());
                    // 平均耗时
                    message.setAvgTime(statAction.getAvgTime());
                    // 总耗时
                    message.setTotalTime(statAction.getTotalTime().sum());

                    return message;
                }).collect(Collectors.toList());

                // 统计数据 region
                ActionStatEventMessageRegion messageRegion = new ActionStatEventMessageRegion();
                messageRegion.setAppId(appId);
                messageRegion.setAppName(appName);
                messageRegion.setMessageList(messageList);

                // 发布事件
                eventBus.fire(messageRegion);

            }, 10, TimeUnit.SECONDS);
        });
    }
}
