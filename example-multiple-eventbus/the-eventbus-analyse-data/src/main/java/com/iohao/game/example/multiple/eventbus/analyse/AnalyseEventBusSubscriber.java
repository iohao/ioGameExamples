/*
 * ioGame
 * Copyright (C) 2021 - 2024  渔民小镇 （262610965@qq.com、luoyizhu@gmail.com） . All Rights Reserved.
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
package com.iohao.game.example.multiple.eventbus.analyse;

import com.iohao.game.action.skeleton.eventbus.EventBusSubscriber;
import com.iohao.game.action.skeleton.eventbus.EventSubscribe;
import com.iohao.game.action.skeleton.eventbus.ExecutorSelector;
import com.iohao.game.example.multiple.eventbus.common.event.ActionStatEventMessage;
import com.iohao.game.example.multiple.eventbus.common.event.ActionStatEventMessageRegion;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

/**
 * @author 渔民小镇
 * @date 2024-01-07
 */
@Slf4j
@EventBusSubscriber
public class AnalyseEventBusSubscriber {
    @EventSubscribe(ExecutorSelector.userVirtualExecutor)
    public void analyseAction(ActionStatEventMessageRegion messageRegion) {
        log.info("messageRegion : {}", messageRegion);
        // DB 入库
    }
}
