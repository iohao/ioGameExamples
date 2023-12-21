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
package com.iohao.game.example.interaction.same;

import com.iohao.game.action.skeleton.core.BarSkeletonBuilder;
import com.iohao.game.action.skeleton.core.flow.interal.StatActionInOut;
import com.iohao.game.action.skeleton.core.flow.interal.ThreadMonitorInOut;
import com.iohao.game.common.kit.concurrent.TaskKit;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.LongAdder;

/**
 * @author 渔民小镇
 * @date 2023-12-18
 */
@Slf4j
@UtilityClass
public class InteractionSameKit {
    public static final LongAdder count = new LongAdder();
    public static final LongAdder countRoom = new LongAdder();
    public static int roomCount;

    public static void inOut(BarSkeletonBuilder builder, String name) {
        ThreadMonitorInOut threadMonitorInOut = new ThreadMonitorInOut();
        builder.addInOut(threadMonitorInOut);
        var threadMonitorRegion = threadMonitorInOut.getRegion();

        StatActionInOut statActionInOut = new StatActionInOut();
        builder.addInOut(statActionInOut);
        var statActionRegion = statActionInOut.getRegion();

        TaskKit.runInterval(() -> {
            System.out.printf("================[%s]================%n", name);
            log.info("count:{} - countRoom:{} - roomCount:{}", count, countRoom, roomCount);
            log.info("threadMonitorRegion : {}", threadMonitorRegion);
            log.info("statActionRegion : {}", statActionRegion);
        }, 60, TimeUnit.SECONDS);
    }
}
