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
package com.iohao.game.spring.logic.hall.event;

import com.iohao.game.widget.light.domain.event.message.DomainEventHandler;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author 渔民小镇
 * @date 2025-08-04
 * @since 21.30
 */
@Slf4j
@Component
public final class HelloEventHandler implements DomainEventHandler<HelloEo> {
    @Resource
    HelloService helloService;

    @Override
    public void onEvent(HelloEo event, boolean endOfBatch) {
        var message = helloService.sayHello();

        log.info("{} {}", event.message(), message);
    }
}
