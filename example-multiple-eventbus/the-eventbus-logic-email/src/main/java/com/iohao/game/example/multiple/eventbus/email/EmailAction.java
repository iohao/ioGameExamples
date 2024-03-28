/*
 * ioGame
 * Copyright (C) 2021 - present  渔民小镇 （262610965@qq.com、luoyizhu@gmail.com） . All Rights Reserved.
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
package com.iohao.game.example.multiple.eventbus.email;

import com.iohao.game.action.skeleton.annotation.ActionController;
import com.iohao.game.action.skeleton.annotation.ActionMethod;
import com.iohao.game.action.skeleton.core.flow.FlowContext;
import com.iohao.game.common.kit.concurrent.TaskKit;
import com.iohao.game.example.multiple.eventbus.common.EmailCmd;
import com.iohao.game.example.multiple.eventbus.common.event.EmailMethodEventMessage;
import com.iohao.game.example.multiple.eventbus.common.event.EmailOrderEventMessage;
import lombok.extern.slf4j.Slf4j;

/**
 * @author 渔民小镇
 * @date 2023-12-24
 */
@Slf4j
@ActionController(EmailCmd.cmd)
public class EmailAction {

    @ActionMethod(EmailCmd.fireEventMethod)
    public String fireEventMethod(FlowContext flowContext) {
        long userId = flowContext.getUserId();
        log.info("fireEventMethod : {}", userId);

        for (int i = 0; i < 2; i++) {
            TaskKit.executeVirtual(() -> {
                var message = new EmailMethodEventMessage(userId);
                flowContext.fire(message);
            });
        }

        return "fireEventMethod";
    }

    @ActionMethod(EmailCmd.fireEventOrder)
    public String fireEventOrder(FlowContext flowContext) {
        long userId = flowContext.getUserId();
        log.info("fireEventOrder : {}", userId);

        var message = new EmailOrderEventMessage(userId);
        flowContext.fire(message);

        return "fireEventOrder";
    }
}
