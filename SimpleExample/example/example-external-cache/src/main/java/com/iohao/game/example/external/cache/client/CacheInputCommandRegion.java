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
package com.iohao.game.example.external.cache.client;

import com.iohao.game.action.skeleton.protocol.wrapper.IntValue;
import com.iohao.game.common.kit.RandomKit;
import com.iohao.game.common.kit.concurrent.TaskKit;
import com.iohao.game.example.common.cmd.CacheCmd;
import com.iohao.game.example.common.msg.HelloMessage;
import com.iohao.game.example.common.msg.login.DemoLoginVerify;
import com.iohao.game.example.common.msg.login.DemoUserInfo;
import com.iohao.game.external.client.AbstractInputCommandRegion;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author 渔民小镇
 * @date 2024-02-02
 */
@Slf4j
public class CacheInputCommandRegion extends AbstractInputCommandRegion {
    @Override
    public void initInputCommand() {
        this.inputCommandCreate.cmd = CacheCmd.cmd;

        TaskKit.runOnceSecond(() -> {
            // 1 秒后登录
            ofRequestCommand(CacheCmd.loginVerify).execute();
        });

        // ---------------- 发送请求 22-0 登录----------------
        ofCommand(CacheCmd.loginVerify).setTitle("登录").setRequestData(() -> {
            DemoLoginVerify loginVerify = new DemoLoginVerify();
            loginVerify.jwt = "99'k-" + RandomKit.randomInt(1, 1000);
            return loginVerify;
        }).callback(result -> {
            var value = result.getValue(DemoUserInfo.class);
            log.info("UserInfo : {}", value);

            ofRequestCommand(CacheCmd.cacheHere).execute();
        });

        // ---------------- 发送请求 22-1 cacheHere ----------------
        ofCommand(CacheCmd.cacheHere).setTitle("cacheHere").setRequestData(() -> {
            // 请求参数
            return IntValue.of(1);
        }).callback(result -> {
            var value = result.getValue(HelloMessage.class);
            log.info("\n cacheHere 缓存 1 小时 : {}", value);
        });

        // ---------------- 发送请求 22-2 cacheCustom ----------------
        ofCommand(CacheCmd.cacheCustom).setTitle("cacheCustom").setRequestData(() -> {
            // 请求参数
            return IntValue.of(2);
        }).callback(result -> {
            var value = result.getValue(HelloMessage.class);
            log.info("\n cacheCustom 缓存 30 秒后过期 : {}", value);
        });

        // ---------------- 发送请求 22-3 cacheList ----------------
        var id = new AtomicInteger();
        ofCommand(CacheCmd.cacheList).setTitle("cacheList").setRequestData(() -> {
            // 请求参数
            return IntValue.of((id.incrementAndGet() % 2));
        }).callback(result -> {
            var value = result.listValue(HelloMessage.class);
            log.info("\n cacheList 缓存 30 秒后过期 : {}", value);
        });
    }
}
