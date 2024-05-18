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
package com.iohao.web2game.client;

import com.iohao.game.action.skeleton.protocol.wrapper.StringValue;
import com.iohao.game.common.kit.concurrent.TaskKit;
import lombok.extern.slf4j.Slf4j;
import com.iohao.game.external.client.AbstractInputCommandRegion;

/**
 * @author 渔民小镇
 * @date 2023-08-21
 */
@Slf4j
public class HelloInputCommandRegion extends AbstractInputCommandRegion {
    @Override
    public void initInputCommand() {
        this.inputCommandCreate.cmd = 1;

        ofCommand(2).setRequestData(() -> StringValue.of("abc")).callback(result -> {
            String value = result.getString();
            log.info("value : {}", value);
        }).setTitle("say");

        TaskKit.runOnceSecond(() -> {
            // 1 秒后主动发起请求
            ofRequestCommand(2).execute();
        });
    }
}