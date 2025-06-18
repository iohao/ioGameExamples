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
package com.iohao.game.example.spring.action;

import com.iohao.game.example.common.msg.HelloSpringMessage;
import org.springframework.stereotype.Component;

/**
 * 这是一个 spring 管理的 service 层
 *
 * @author 渔民小镇
 * @date 2022-03-22
 */
@Component
public class DemoSpringService {
    public HelloSpringMessage here(HelloSpringMessage message) {
        HelloSpringMessage springMessage = new HelloSpringMessage();
        springMessage.name = message.name + ", I'm spring ";
        return springMessage;
    }
}
