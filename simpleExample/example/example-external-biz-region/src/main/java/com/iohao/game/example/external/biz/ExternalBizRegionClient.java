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
package com.iohao.game.example.external.biz;

import com.iohao.game.common.kit.concurrent.TaskKit;
import com.iohao.game.example.common.cmd.ExternalBizRegionCmd;
import com.iohao.game.external.client.AbstractInputCommandRegion;
import com.iohao.game.external.client.join.ClientRunOne;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * @author 渔民小镇
 * @date 2024-07-19
 */
@Slf4j
public class ExternalBizRegionClient {
    public static void main(String[] args) {
        new ClientRunOne()
                // 请求消息编排
                .setInputCommandRegions(List.of(new ExternalBizRegionInputCommandRegion()))
                .startup();
    }

    static class ExternalBizRegionInputCommandRegion extends AbstractInputCommandRegion {

        @Override
        public void initInputCommand() {
            this.inputCommandCreate.cmd = ExternalBizRegionCmd.cmd;

            TaskKit.runOnceSecond(() -> {
                // 1 秒后登录
                ofRequestCommand(ExternalBizRegionCmd.loginVerify).execute();
            });

            // ---------------- 登录----------------
            ofCommand(ExternalBizRegionCmd.loginVerify).setTitle("登录").callback(result -> {
                var value = result.getBoolean();
                log.info("登录成功：{}", value);

                ofRequestCommand(ExternalBizRegionCmd.listOnlineUser).execute();
            });

            // ---------------- listOnlineUser ----------------
            ofCommand(ExternalBizRegionCmd.listOnlineUser).setTitle("listOnlineUser").callback(result -> {
                var value = result.listLong();
                log.info("userId list: {}", value);
            });
        }
    }
}
