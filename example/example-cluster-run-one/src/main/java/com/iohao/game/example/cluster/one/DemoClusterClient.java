/*
 * ioGame
 * Copyright (C) 2021 - 2023  渔民小镇 （262610965@qq.com、luoyizhu@gmail.com） . All Rights Reserved.
 * # iohao.com . 渔民小镇
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */
package com.iohao.game.example.cluster.one;

import com.iohao.game.common.kit.InternalKit;
import com.iohao.game.example.cluster.one.action.DemoClusterCmd;
import com.iohao.game.example.common.msg.DemoBroadcastMessage;
import com.iohao.game.example.common.msg.HelloReq;
import com.iohao.game.external.client.AbstractInputCommandRegion;
import com.iohao.game.external.client.InputCommandRegion;
import com.iohao.game.external.client.join.ClientRunOne;
import com.iohao.game.external.client.kit.ClientUserConfigs;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * @author 渔民小镇
 * @date 2023-07-17
 */
@Slf4j
public class DemoClusterClient {
    public static void main(String[] args) {
        ClientUserConfigs.closeLog();

        // 模拟请求数据
        List<InputCommandRegion> inputCommandRegions = List.of(
                new DemoClusterRegion()
        );

        // 启动模拟客户端
        new ClientRunOne()
                .setInputCommandRegions(inputCommandRegions)
                .startup();
    }

    static class DemoClusterRegion extends AbstractInputCommandRegion {
        @Override
        public void initInputCommand() {
            // 模拟请求的主路由
            inputCommandCreate.cmd = DemoClusterCmd.cmd;

            HelloReq helloReq = new HelloReq();
            helloReq.name = "塔姆";

            // 配置一些模拟请求
            ofCommand(DemoClusterCmd.here).callback(HelloReq.class, result -> {
                HelloReq value = result.getValue();
                log.info("value : {}", value);
            }).setDescription("here").setRequestData(helloReq);

            // 一秒后，执行模拟请求;
            InternalKit.newTimeoutSeconds(task -> {
                // 执行 here 请求
                ofRequestCommand(DemoClusterCmd.here).request();
            });
        }
    }
}
